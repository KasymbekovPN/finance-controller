package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeTagSavingChecker extends AbstractBeforeSavingChecker<Tag> {
    @Override
    public Result<List<Tag>> apply(Tag value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<List<Tag>>fail("checking.domain.tag.name.isEmpty")
                : ImmutableResult.<List<Tag>>ok(List.of(value));
    }
}
