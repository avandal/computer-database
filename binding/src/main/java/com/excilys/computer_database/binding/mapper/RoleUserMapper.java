package com.excilys.computer_database.binding.mapper;

import com.excilys.computer_database.binding.dto.RoleUserDTO;
import com.excilys.computer_database.binding.dto.RoleUserDTOBuilder;
import com.excilys.computer_database.core.model.RoleUser;

public class RoleUserMapper {
	public static RoleUserDTO userRoleToDTO(RoleUser user) {
		RoleUserDTOBuilder builder = new RoleUserDTOBuilder();
		
		if (user.getUser().getUsername() != null) {
			builder.username(user.getUser().getUsername());
		} else {
			builder.username("");
		}
		
		if (user.getUser().getPassword() != null) {
			builder.password(user.getUser().getPassword());
		} else {
			builder.password("");
		}
		
		builder.enabled(Boolean.toString(user.getUser().isEnabled()));
		
		if (user.getRole() != null) {
			builder.role(user.getRole());
		} else {
			builder.username("guest");
		}
		
		return builder.build();
	}
}
