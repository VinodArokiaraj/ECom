package Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {

    public static List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

        // Read JSON file to String
        String jsonContent = FileUtils.readFileToString(
                new File(filePath),
                StandardCharsets.UTF_8
        );

        // Convert String → List<HashMap>
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {}
        );
    }
}