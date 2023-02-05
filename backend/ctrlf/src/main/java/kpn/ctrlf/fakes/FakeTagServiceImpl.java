package kpn.ctrlf.fakes;

import kpn.ctrlf.data.domain.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// TODO: 05.02.2023 it is temporary variant
@Component
public final class FakeTagServiceImpl implements FakeTagService {
	private final Map<Long, Tag> storage;
	private final Set<String> names = new HashSet<>();

	public FakeTagServiceImpl() {
		storage = new HashMap<>();
	}

	public FakeTagServiceImpl(Map<Long, Tag> storage) {
		this.storage = storage;
	}

	public Result<Tag> save(Tag tag) {
		String name = tag.getName();
		if (names.contains(name)){
			return ImmutableResult.<Tag>bFail("domain.tag.saving.fail").arg(name).build();
		}

		long id = storage.size();
		Tag newTag = new Tag(id, name);
		storage.put(id, newTag);

		return ImmutableResult.<Tag>ok(newTag);
	}
}
