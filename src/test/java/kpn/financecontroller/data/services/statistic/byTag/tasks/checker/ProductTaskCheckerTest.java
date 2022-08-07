package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class ProductTaskCheckerTest {

    @ParameterizedTest
    @CsvFileSource(resources = "productTaskChecker_shouldCheckChecking.csv")
    void shouldCheckChecking(boolean allTags, String rawTags, boolean expectedSuccess, String expectedCode) {
        Set<Tag> tags = createTags(rawTags);
        ProductTask task = createTask(allTags, tags);
        Result<ProductTask> expectedResult = createExpectedResult(expectedSuccess, task, expectedCode);

        Result<ProductTask> result = new ProductTaskChecker().check(task);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    private Set<Tag> createTags(String rawTags) {
        return rawTags == null
                ? null
                : Arrays.stream(rawTags.split("-")).map(id -> {
                    Tag tag = new Tag();
                    tag.setId(Long.valueOf(id));
                    tag.setName("tag" + id);
                    return tag;
                }).collect(Collectors.toSet());
    }

    private ProductTask createTask(boolean allTags, Set<Tag> tags) {
        ProductTask task = new ProductTask();
        task.setAllTags(allTags);
        task.setTags(tags);
        return task;
    }

    private Result<ProductTask> createExpectedResult(boolean success, ProductTask task, String code) {
        ImmutableResult.Builder<ProductTask> builder = ImmutableResult.<ProductTask>builder()
                .success(success)
                .value(task);
        if (code != null){
            builder.code(code);
        }
        return builder.build();
    }
}