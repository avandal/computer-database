package com.excilys.computer_database.binding.mapper;

import java.util.Objects;
import java.util.function.Predicate;

import com.excilys.computer_database.binding.dto.RoleUserDTO;
import com.excilys.computer_database.binding.dto.RoleUserDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.core.model.RoleUser;

public interface RoleUserMapper {
	public static RoleUserDTO userRoleToDTO(RoleUser user) {
		RoleUserDTOBuilder builder = new RoleUserDTOBuilder();
		
		Predicate<String> condition = Objects::nonNull;
		
		builder.username(Util.accordingTo(condition, user.getUser().getUsername(), ""));
		builder.password(Util.accordingTo(condition, user.getUser().getPassword(), ""));
		
		builder.enabled(Boolean.toString(user.getUser().isEnabled()));
		
		builder.role(Util.accordingTo(condition, user.getRole(), "guest"));
		
		return builder.build();
	}
}
