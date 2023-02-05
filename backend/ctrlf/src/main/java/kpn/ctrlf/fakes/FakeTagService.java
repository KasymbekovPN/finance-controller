package kpn.ctrlf.fakes;

import kpn.ctrlf.data.domain.Tag;
import kpn.lib.result.Result;

// TODO: 05.02.2023 it is temporary variant
public interface FakeTagService {
	Result<Tag> save(Tag tag);
}
