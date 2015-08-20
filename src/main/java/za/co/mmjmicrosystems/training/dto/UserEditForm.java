package za.co.mmjmicrosystems.training.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import za.co.mmjmicrosystems.training.entities.User;
import za.co.mmjmicrosystems.training.entities.User.Role;

public class UserEditForm {
	
	@NotNull
	@Size(min=1, max=User.NAME_MAX, message="{nameSizeError}")
	private String name = "";
	
	
	private Set<Role> roles;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
