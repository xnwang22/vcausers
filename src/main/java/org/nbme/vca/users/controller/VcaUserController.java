package org.nbme.vca.users.controller;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.Vca2AdUser;
import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.model.builder.Vca2AdUserBuilder;
import org.nbme.vca.users.repo.VcaUserRepo;
import org.nbme.vca.users.services.VcaUserRoleService;
import org.nbme.vca.users.services.VcaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/vca")
@CrossOrigin(origins = "*")
/**
 * This class will interface with the front end of the VCA to CRUD users. This will also create a user in 
 * Azure AD 
 * 
 * @author jreim
 *
 */
public class VcaUserController {
    private static final Logger logger = LoggerFactory.getLogger(VcaUserController.class);


	private Logger log = LoggerFactory.getLogger(VcaUserController.class);
	@Autowired
	private VcaUserRepo ar;
	@Autowired
	private Vca2AdUserBuilder adBuilder;
	@Autowired
    private VcaUserService vcaUserService;
	@Autowired
	private VcaUserRoleService vcaUserRoleService;
	
	private Gson gson = new Gson();
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllVCAUsers() {
		
		List<VcaUser> allUsers = ar.findByActiveTrue();
		for(VcaUser u : allUsers){
			this.explodeRoles(u);
		}
		return new ResponseEntity(allUsers, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity postVCAUser(@RequestBody VcaUser user){
		log.debug("got a new user to save");

		ResponseEntity<String> userExsists =  this.checkIfExsists(user);
		
		if(userExsists != null){
			return userExsists;
		}
		
		Vca2AdUser transitionUser = adBuilder.buildVca2AdUser(user);
		log.debug("translated to transition user");
		AdUser adUser = vcaUserService.addUser(transitionUser);
		log.debug("user created in AD");
		user.setAdUser(adUser.getObjectId());
		user.setPassword(null);

		this.flattenRoles(user);
		user = ar.save(user);
		return new ResponseEntity(user,HttpStatus.OK);
	}
	// expect a json here,
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity patchVCAUser(@PathVariable Long id,@RequestBody VcaUser user){
		log.debug("got a patch for id:{} ",id);

		VcaUser vcaUser = ar.findOne(id);
		String updateJson = diff(vcaUser, user);
		if (updateJson.length() > 3)
		vcaUserService.patchUser(vcaUser.getuName(), updateJson);
		List<VcaUserRole> adUserRoles = vcaUserRoleService.getUserRoles(vcaUser.getuName());
		List<String> adUserRolesString = adUserRoles.stream().map(i -> i.toString()).collect(Collectors.toList());
		Preconditions.checkArgument(new HashSet<>(adUserRolesString).equals(vcaUser.getRoles()));
		Map<String, List<String>> roleChangeMap = diffUserRole(adUserRolesString, user.getRoles());
		roleChangeMap.get("delete").stream().forEach(i->vcaUserRoleService.deleteUserRole(vcaUser.getuName(), i));
		roleChangeMap.get("add").stream().forEach(i->vcaUserRoleService.addUserRole(vcaUser.getuName(), i));

		this.flattenRoles(user);
		user = ar.save(user);
		this.explodeRoles(user);
		return new ResponseEntity(user,HttpStatus.OK);
	}

	String diff(VcaUser vcaUser, VcaUser updatedUser) {
		if(vcaUser.equals(updatedUser))
			return null;
		StringBuilder sb = new StringBuilder("{");
		Boolean added = false;
		 if(vcaUser.getActive() != updatedUser.getActive()) {
			 sb.append("\"accountEnabled\" : " + updatedUser.getActive());
			 added = true;
		 }
		if(! vcaUser.getfName().equals( updatedUser.getfName())) {
			if(added)
				sb.append(", ");
			added = true;
			sb.append("\"givenName\" : \"" + updatedUser.getfName() + "\"");
		}
		if(!vcaUser.getlName().equals( updatedUser.getlName())) {
			if (added)
				sb.append(", ");
			added = true;
			sb.append("\"surname\" : \"" + updatedUser.getlName() + "\"");
		}
		sb.append("}");


		return sb.toString();
	}

	Map<String, List<String>> diffUserRole(List<String> userRole1, List<String> userRole2) {

		Preconditions.checkNotNull(userRole1);
		Preconditions.checkNotNull(userRole2);
		Set<String> roleSet1 = new HashSet<>(userRole1);
		Set<String> roleSet2 = new HashSet<>(userRole2);
		Set<String> roleSet3 = new HashSet<>(userRole1);

		Map<String, List<String>> returnMap = new HashMap<>(userRole1.size());
		List<String> delRoles = new ArrayList<>();

		roleSet1.removeAll(roleSet2);
		delRoles.addAll(roleSet1);
		returnMap.put("delete", delRoles);
		List<String> addRoles = new ArrayList<>();

		roleSet2.removeAll(roleSet3);
		addRoles.addAll(roleSet2);
		returnMap.put("add", addRoles);

		return returnMap;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET , produces=MediaType.APPLICATION_JSON_VALUE)
	public VcaUser getVCAUserById(@PathVariable Long id, HttpServletResponse response) {
		
		VcaUser user =  ar.findOne(id);
		if(user == null){
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return null;
		}
		this.explodeRoles(user);
		return user;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void removeAssignment(@PathVariable Long id, HttpServletResponse response) {
		
		VcaUser u =  ar.findOne(id);
		if(u == null){
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}
		u.setActive(Boolean.FALSE);
		ar.save(u);
		response.setStatus(HttpStatus.OK.value());
	}
	
	/**
	 * This method will check to see if a user already had a user name of email in our system
	 * @param user
	 * @return
	 */
	private ResponseEntity checkIfExsists(VcaUser user){

		if(user.getuName() == null || "".equals(user.getuName())){
			log.warn("Attempted to create a user with no user name ");
			return new ResponseEntity("",HttpStatus.BAD_REQUEST);
		}
		VcaUser exsistingUser = ar.findByUName(user.getuName());
		if(exsistingUser != null){

			log.warn("Attempted to create a user with an existing user name of : {} , already exsists on vcaUser id :{}", exsistingUser.getuName(),exsistingUser.getId());
			return new ResponseEntity("User Name already exisist",HttpStatus.BAD_REQUEST);
		}
		if(user.getEmail() == null || "".equals(user.getEmail())){
			log.warn("Attempted to create a user with no email ");
			return new ResponseEntity("",HttpStatus.BAD_REQUEST);
		}
		exsistingUser = ar.findByUName(user.getuName());
		if(exsistingUser != null){

			log.warn("Attempted to create a user with an existing email of : {} , already exsists on vcaUser id :{}", exsistingUser.getEmail(),exsistingUser.getId());
			return new ResponseEntity("Email already exisist",HttpStatus.BAD_REQUEST);
		}
		return null;
	}
	
	//terrible hack for getting around users list
	private void flattenRoles(VcaUser user){
		
		String roles = gson.toJson(user.getRoles());
		user.setRolesList(roles);
	}
	
	private void explodeRoles(VcaUser user){
		String data = user.getRolesList();
		
		if(data == null || "".equals(data)){
			return;
		}
		String[] roleArray = gson.fromJson(data, String[].class);
		user.setRoles(Arrays.asList(roleArray));
	}
}