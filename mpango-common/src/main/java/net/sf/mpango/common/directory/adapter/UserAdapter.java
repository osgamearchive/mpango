package net.sf.mpango.common.directory.adapter;

import net.sf.mpango.common.adapter.BaseAdapter;
import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;

public class UserAdapter extends BaseAdapter<User, UserDTO>{

	private UserAdapter() {}
	
	public static UserAdapter getInstance() {
		return new UserAdapter();
	}
	
	@Override
	public User fromDTO(UserDTO dto) {
		if (dto == null) {
			return null;
		}
		User user = new User();
		user.setIdentifier(dto.getId());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setDateOfBirth(dto.getDateOfBirth());
		user.setGender(dto.getGender());
		user.setPassword(dto.getPassword());
		user.setState(dto.getState());
		return user;
	}

	@Override
	public UserDTO toDTO(User user) {
		if (null == user) {
			return null;
		}
		UserDTO dto = new UserDTO();
		dto.setId(user.getIdentifier());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setDateOfBirth(user.getDateOfBirth());
		dto.setGender(user.getGender());
		dto.setState(user.getState());
		return dto;
	}

}
