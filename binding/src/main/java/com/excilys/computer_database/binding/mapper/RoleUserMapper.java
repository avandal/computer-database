package com.excilys.computer_database.binding.mapper;

import com.excilys.computer_database.binding.dto.RoleUserDTO;
import com.excilys.computer_database.binding.dto.RoleUserDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.RoleUser;

public class RoleUserMapper {
	public static RoleUserDTO userRoleToDTO(RoleUser user) {
		RoleUserDTOBuilder builder = new RoleUserDTOBuilder();
		
		builder.username(Util.accordingTo(s -> s != null, user.getUser().getUsername(), ""));
		builder.password(Util.accordingTo(s -> s != null, user.getUser().getPassword(), ""));
		
		builder.enabled(Boolean.toString(user.getUser().isEnabled()));
		
		builder.password(Util.accordingTo(s -> s != null, user.getRole(), "guest"));
		
		return builder.build();
	}
}
