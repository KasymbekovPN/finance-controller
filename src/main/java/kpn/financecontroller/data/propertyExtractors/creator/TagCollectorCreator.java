package kpn.financecontroller.data.propertyExtractors.creator;

import com.google.gson.Gson;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.data.propertyExtractors.IECollectorImpl;
import kpn.financecontroller.data.propertyExtractors.entities.TagIE;

public class TagCollectorCreator extends AbstractCollectorCreator<Long, TagIE> {

    public TagCollectorCreator(String id) {
        super(id);
    }

    @Override
    protected IECollector<Long, TagIE> createCollector(String source) {
        return new Gson().fromJson(source, TagIECollector.class);
    }

    private static class TagIECollector extends IECollectorImpl<Long, TagIE>{}
}
