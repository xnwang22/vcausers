package org.nbme.vca.users.model.builder;

import java.util.ArrayList;

import org.nbme.vca.users.model.PasswordProfile;
import org.nbme.vca.users.model.SignInName;
import org.nbme.vca.users.model.Vca2AdUser;
import org.nbme.vca.users.model.VcaUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
/**
 * This class will help to build the vca2AdUser.
 * @author jreim
 *
 */
public class Vca2AdUserBuilder {
	

	@Value("${vca.ad.user.accountEnabled}")
	private boolean accountEnabled;
	
	@Value("${vca.ad.user.creationType}")
	private String creationType;
	
	public Vca2AdUserBuilder(){
		
	}
	
	public Vca2AdUser buildVca2AdUser(VcaUser user){
		Vca2AdUser adUser = new Vca2AdUser();
		
		adUser.setAccountEnabled(this.accountEnabled);
		adUser.setCreationType(this.creationType);
		adUser.setDisplayName(user.getuName());

		adUser.setGivenName(user.getfName());
		adUser.setSurName(user.getlName());
		
		PasswordProfile pp = new PasswordProfile();
		pp.setForceChangePasswordNextLogin(false);
		pp.setPassword(user.getPassword());
		
		adUser.setPasswordProfile(pp);
		ArrayList<SignInName> names = new ArrayList<>();
		SignInName userName = new SignInName("userName", user.getuName());
		SignInName email = new SignInName("emailAddress", user.getEmail());
		
		names.add(email);
		names.add(userName);
		adUser.setSignInNames(names);
		
		return adUser;
	}
	
	public Vca2AdUser buildVca2AdUser(VcaUser user, Vca2AdUser transUser){
		
		transUser.setAccountEnabled(this.accountEnabled);
		transUser.setCreationType(this.creationType);
		transUser.setDisplayName(user.getuName());
		PasswordProfile pp = new PasswordProfile();
		pp.setForceChangePasswordNextLogin(false);
		pp.setPassword(user.getPassword());
		
		transUser.setPasswordProfile(pp);
		ArrayList<SignInName> names = new ArrayList<>();
		SignInName userName = new SignInName("userName", user.getuName());
		SignInName email = new SignInName("email", user.getEmail());
		names.add(email);
		names.add(userName);
		transUser.setSignInNames(names);
		
		return transUser;
	}
	
	
	  
	  
}
