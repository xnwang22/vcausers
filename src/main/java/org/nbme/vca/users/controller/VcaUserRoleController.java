package org.nbme.vca.users.controller;

import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.repo.VcaUserRepo;
import org.nbme.vca.users.services.VcaUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rwang on 8/12/2016.
 */
@RestController
//@RequestMapping(value = "/userRole")
public class VcaUserRoleController {
    private static final Logger logger = LoggerFactory.getLogger(VcaUserRoleController.class);

    @Autowired
    private VcaUserRoleService vcaUserRoleService;
    @Autowired
    private VcaUserRepo vcaUserRepo;

    @RequestMapping(value = "/userRole/{userName}/{groupName}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addUserrole(@PathVariable String userName, @PathVariable String groupName, HttpServletResponse response, Model model) throws Exception {
        {

            vcaUserRoleService.addUserRole(userName, groupName);
            VcaUser vcaUser = vcaUserRepo.findByUName(userName);
            if (vcaUser == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
            vcaUser.getRoles().add(groupName);
            vcaUserRepo.save(vcaUser);
            logger.info("User {} role {} added ", userName, groupName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/userRole/{userName}/{groupName}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteUserRole(@PathVariable String userName, @PathVariable String groupName, HttpServletResponse response, Model model) throws Exception {
        {
           VcaUserRole.valueOf(groupName.toUpperCase());
            vcaUserRoleService.deleteUserRole(userName, groupName);
            VcaUser vcaUser = vcaUserRepo.findByUName(userName);
            if (vcaUser == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
            vcaUser.getRoles().remove(groupName);
            vcaUserRepo.save(vcaUser);
            logger.info("User {} role {} removed ", userName, groupName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

    // update user role
    @RequestMapping(value = "/userRole/{userName}/{fromGroupName}/{toGroupName}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity changeUserRole(@PathVariable String userName, @PathVariable String fromGroupName, @PathVariable String toGroupName, HttpServletResponse response, Model model) throws Exception {
        {
            VcaUserRole.valueOf(fromGroupName.toUpperCase());
            VcaUserRole.valueOf(toGroupName.toUpperCase());
            vcaUserRoleService.updateUserRole(userName, fromGroupName, toGroupName);
            VcaUser vcaUser = vcaUserRepo.findByUName(userName);
            if (vcaUser == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
            vcaUser.getRoles().add(toGroupName);
            vcaUser.getRoles().remove(fromGroupName);
            vcaUserRepo.save(vcaUser);
            logger.info("User {} role {} changed to {} ", userName, fromGroupName, toGroupName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/userRole/{userName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserRole(@PathVariable String userName, HttpServletRequest request, Model model) throws Exception {
        {

            VcaUserRole vcaUserRole = vcaUserRoleService.getUserRole(userName);
            return new ResponseEntity(vcaUserRole, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/userRoles/{userName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserRoles(@PathVariable String userName, HttpServletRequest request, Model model) throws Exception {
        {

            List<VcaUserRole> vcaUserRoles = vcaUserRoleService.getUserRoles(userName);
            return new ResponseEntity(vcaUserRoles, HttpStatus.OK);
        }
    }

}
