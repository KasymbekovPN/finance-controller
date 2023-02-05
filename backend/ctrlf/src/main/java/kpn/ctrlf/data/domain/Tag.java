package kpn.ctrlf.data.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO: 05.02.2023 it is temporary variant
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Tag {
	private final Long id;
	private final String name;
}
