package kpn.financecontroller.initialization.generators.seed;

import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;

import java.util.Optional;

public class TagSeedGenerator implements Generator {

    // TODO: 23.03.2022 del 
    /*
    protected final Valued<String> key;
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;
    private final String path;

     */

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        return Optional.empty();
    }
}
