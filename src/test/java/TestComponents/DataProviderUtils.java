package TestComponents;

import Data.DataReader;
import Data.FilePaths;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DataProviderUtils {

    @DataProvider(name = "purchaseData")
    public Object[][] purchaseData() throws IOException {

        List<HashMap<String, String>> data =
                DataReader.getJsonDataToMap(FilePaths.PURCHASE);

        Object[][] testData = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }

        return testData;
    }

    @DataProvider(name = "errorValidationData")
    public Object[][] errorValidationData() throws IOException {

        List<HashMap<String, String>> data =
                DataReader.getJsonDataToMap(FilePaths.ERROR_VALIDATION);

        Object[][] testData = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }

        return testData;
    }
}