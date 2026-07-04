package Data;

public class DataFileResolver {

    public static String getFilePath(TestDataType type) {

        switch (type) {

            case PURCHASE:
                return System.getProperty("user.dir")
                        + "/src/test/java/Data/purchaseOrder.json";

            case ERROR:
                return System.getProperty("user.dir")
                        + "/src/test/java/Data/loginData.json";

            /*case CHECKOUT:
                return System.getProperty("user.dir")
                        + "/src/test/java/Data/checkoutData.json"; */

            default:
                throw new RuntimeException("Invalid TestDataType: " + type);
        }
    }
}