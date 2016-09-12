package org.nbme.vca.users.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.util.Collections;
import java.util.List;
/**
 * This class will be used to hold user data for the vca user. The user will be authenticated spereratly of this user and should
 * have a corresponding user in Azure ad.
 * @author jreim
 *
 */
@Entity
public class VcaUser {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String email;
	private String fName;
	private String lName;
	private String uName;
	private String institution;
	
	@Transient
	private List<String> roles;
	@Lob
	private String rolesList;

	private Boolean active = Boolean.TRUE;
	
	@Transient
	private String password;
	
	private String adUser;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getAdUser() {
		return adUser;
	}
	public void setAdUser(String adUser) {
		this.adUser = adUser;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<String> getRoles() {
		if(roles == null){
			return Collections.emptyList();
		}
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return this.password;
	}
	
	public String getRolesList() {
		return rolesList;
	}
	public void setRolesList(String rolesList) {
		this.rolesList = rolesList;
	}

	
	
	
}
