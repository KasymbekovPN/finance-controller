package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

// TODO: 21.07.2022 remake
@Component
final public class BeforeTagSavingCheckerOld extends AbstractBeforeSavingCheckerOld<Tag> {

    @Override
    public Result<Tag> apply(Tag value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<Tag>fail("checking.domain.tag.name.isEmpty")
                : ImmutableResult.<Tag>ok(value);
    }
}
