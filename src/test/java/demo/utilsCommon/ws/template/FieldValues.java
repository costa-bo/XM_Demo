package demo.utilsCommon.ws.template;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;

public class FieldValues {

    /**
     * Method to be used by "MergeFrom.withDefaultValuesFrom" to get values from a property file and place them in the request
     * @param propertiesFile
     * @return a Map of String,String with variables and values to be set on a template request as default values.
     */
    public static Map<String, String> in(String propertiesFile) {
        Properties properties = new Properties();
        try {
            properties.load(FieldValues.class.getResourceAsStream("/" + propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.entrySet()
                .stream()
                .collect(toMap(entry -> entry.getKey().toString(),
                        entry -> entry.getValue().toString()));
    }

}
