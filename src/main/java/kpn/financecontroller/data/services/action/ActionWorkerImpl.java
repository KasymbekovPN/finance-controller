package kpn.financecontroller.data.services.action;

//@Component // TODO: 05.09.2022 uncomment
public class ActionWorkerImpl implements ActionWorker {
    // TODO: 05.09.2022 how set it
    // TODO: 05.09.2022 mark this classes special annotation
    private static final String IMPORT = "";
//            = "import kpn.financecontroller.gui.external.builder.MultilineComponentBuilder;\n" +
//            "import kpn.financecontroller.gui.external.service.AddressServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.CityServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.CountryServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.PaymentServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.ProductServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.RegionServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.SellerServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.StreetServiceWrapper\n" +
//            "import kpn.financecontroller.gui.external.service.TagServiceWrapper\n";

    @Override
    public Object execute(String algorithm) {
        return null;
    }
}


// TODO: 05.09.2022 use it
//public class AccessingAllClassesInPackage {
//
//    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
//        InputStream stream = ClassLoader.getSystemClassLoader()
//                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        return reader.lines()
//                .filter(line -> line.endsWith(".class"))
//                .map(line -> getClass(line, packageName))
//                .collect(Collectors.toSet());
//    }
//
//    private Class getClass(String className, String packageName) {
//        try {
//            return Class.forName(packageName + "."
//                    + className.substring(0, className.lastIndexOf('.')));
//        } catch (ClassNotFoundException e) {
//            // handle the exception
//        }
//        return null;
//    }
//}
