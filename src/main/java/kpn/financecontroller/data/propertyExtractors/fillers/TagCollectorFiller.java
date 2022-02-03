package kpn.financecontroller.data.propertyExtractors.fillers;

import com.google.gson.Gson;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.data.propertyExtractors.IECollectorImpl;
import lombok.Getter;
import lombok.Setter;

public class TagCollectorFiller extends AbstractCollectorFiller<Long, TagCollectorFiller.IETagEntity>{

    public TagCollectorFiller(String name) {
        super(name);
    }

    @Override
    protected IECollector<Long, IETagEntity> createCollector(String jsonContent) {
        return new Gson().fromJson(jsonContent, IETagCollector.class);
    }

    @Getter
    @Setter
    public static class IETagEntity extends IEAbstractEntity<Long> {
        private String name;
    }

    public static class IETagCollector extends IECollectorImpl<Long, IETagEntity>{}
}
