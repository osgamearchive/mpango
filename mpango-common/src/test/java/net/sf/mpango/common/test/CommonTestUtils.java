package net.sf.mpango.common.test;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.common.directory.adapter.UserAdapter;
import net.sf.mpango.common.directory.dto.UserDTO;

public class CommonTestUtils {

	public static User getUser() {
		User user = new User();
		user.setEmail("email@domain.com");
		user.setGender("F");
		user.setIdentifier(RandomUtils.nextLong());
		user.setNonceToken(RandomStringUtils.random(16));
		user.setPassword(RandomStringUtils.random(8));
		user.setResetKey(RandomStringUtils.random(16));
		user.setState(RandomUtils.nextBoolean() ? StateEnum.ACTIVE : StateEnum.DELETED);
		user.setUsername(RandomStringUtils.random(8));
		return user;
	}
	
	public static UserDTO getUserDTO() {
		return UserAdapter.getInstance().toDTO(getUser());
	}
}
