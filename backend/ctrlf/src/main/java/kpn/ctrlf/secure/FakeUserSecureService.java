package kpn.ctrlf.secure;

import kpn.ctrlf.data.domain.User;
import org.springframework.stereotype.Service;

// TODO: 22.01.2023 del it
@Service
public final class FakeUserSecureService implements UserSecureService<User> {

	@Override
	public boolean checkCredential(User user) {
		return user.getUsername().equals("admin") && user.getPassword().equals("admin");
	}
}
