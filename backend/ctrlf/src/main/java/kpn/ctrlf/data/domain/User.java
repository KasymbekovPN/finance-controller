package kpn.ctrlf.data.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public final class User {
	@Getter
	private final String username;
	@Getter
	private final String password;
}
