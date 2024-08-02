package demo.utilsCommon.ws.rest;

import demo.utilsCommon.ws.WebServiceValidation;
import demo.utilsCommon.ws.rest.pojo.FilteredData;
import demo.utilsCommon.ws.rest.pojo.FilteredDataCombi;
import net.serenitybdd.rest.Ensure;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasToString;

public class RestValidations extends WebServiceValidation {

    private final Rest pageInstance;

    RestValidations(Rest instance) {
        pageInstance = instance;
    }

    /**
     * Method to verify that the returned Status Code is equal to an expected.
     * @param expectedStatusCode
     */
    public void verifyStatusCode(int expectedStatusCode) {
        super.verifyStatusCode(expectedStatusCode);
    }

    public void verifyStatusCodes(List<Integer> expectedStatusCodes) {
        for (int validStatusCode : expectedStatusCodes) {
            try {
                super.verifyStatusCode(validStatusCode);
                return;
            }catch (AssertionError e){
                if (validStatusCode == expectedStatusCodes.get(expectedStatusCodes.size() -1)){
                    throw e;
                }
            }
        }
        //restAssuredThat(response -> equals(anyOf(
        //        is("201"),
        //        is("202"))));
    }

    /**
     * Method to verify that the returned Text contains an expected content.
     * @param expectedText
     */
    public void verifyResponseText(String expectedText) {
        super.verifyResponseText(expectedText);
    }

    /**
     * Method to verify that a single element from the response is equal to an expected.
     * @param testData Properties pulled by getTestData() method with the element as the variable's name and its expected value.
     */
    public void verifyResponseBodySingleElement(Properties testData) {
        for (Object key : testData.keySet()) {
            Ensure.that("Response details should be correct", response -> response
                    .body(key.toString(), hasToString(testData.getProperty(key.toString())))
            );
        }
    }

    /**
     * Method to verify that the response contains the expected count of the parent group of elements.
     * @param expectedCount that defines expectedCount
     */
    public void verifyResponseCount(int expectedCount) {
        int actualCount = 0;
        if (pageInstance.getLastResponse().startsWith("[")) {
            JSONArray jsonArray = new JSONArray(pageInstance.getLastResponse());
            actualCount = jsonArray.length();
        } else {
            actualCount = 1;
        }
        Assert.assertEquals(expectedCount, actualCount);
    }

    /**
     * Method to verify that the response body contains a list of elements with the proper value
     * @param testData Properties pulled by getTestData() method with the element as the variable's name and its expected value.
     */
    public void verifyResponseBodyMultipleElements(Properties testData) {
        String response = pageInstance.getLastResponse();

        // Revert the delimiter parts (name.1 > 1.name). Make number prefix instead of suffix
        Properties props = new Properties();
        testData.forEach((key, value) -> {
            String[] prop = key.toString().split("\\.");
            String newKey = prop[1] + "." + prop[0];
            props.setProperty(newKey, value.toString());
        });

        //Sort properties
        Map<String, String> elements = new HashMap<>();
        Map<String, String> finalElements = elements;
        props.forEach((key, value) -> {
            finalElements.put(key.toString(), value.toString());
        });
        // Sort map by keys in Java 8 and above
        elements = elements.entrySet()        // Set<Entry<String, String>>
                .stream()           // Stream<Entry<String, String>>
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        //Read sorted map with prefixed properties
        JSONArray jsonArray = new JSONArray(response);
        Iterator<Map.Entry<String, String>> itr = elements.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            String[] prop = entry.getKey().split("\\.");

            int item = Integer.parseInt(prop[0]) - 1;
            String entryKey = prop[1];
            JSONObject jsonObject = jsonArray.getJSONObject(item);
            System.out.println("JSON Object: " + jsonObject);
            System.out.println("Expected: " + entryKey + " = " + entry.getValue());
            System.out.println("Actual  : " + jsonObject.get(entryKey).toString());
            Assert.assertEquals(entry.getValue(), jsonObject.get(entryKey).toString());
        }
    }

    /**
     * Method to verify that the response body contains a list of elements with the proper value in Shuffled order.
     * @param testData Properties pulled by getTestData() method with the element as the variable's name and its expected value.
     */
    public void verifyResponseBodyMultipleElementsShuffled(Properties testData) {
        String response = pageInstance.getLastResponse();

        ArrayList<FilteredData> data = new ArrayList<>();
        testData.forEach((key, value) -> {
            String[] prop = key.toString().split("\\.");
            data.add(new FilteredData(Integer.parseInt(prop[1]), key.toString(), value.toString()));
        });
        Map<Integer, List<FilteredData>> dataByGroup
                = data.stream()
                .collect(Collectors.groupingBy(FilteredData::getId));

        // Create an array of filters
        ArrayList<FilteredDataCombi> dataCombi = new ArrayList<FilteredDataCombi>();
        int z = 0;
        for (Map.Entry<Integer, List<FilteredData>> entry : dataByGroup.entrySet()) {
            String dataCombination = "";
            for (int i = 0; i < entry.getValue().size(); i++) {
                String[] prop = entry.getValue().get(i).toString().split("\\.");
                String element = prop[0];
                String expectedValue = testData.getProperty(entry.getValue().get(i).toString());
                dataCombination += element + "=" + expectedValue + " | ";
            }
            dataCombi.add(new FilteredDataCombi(z, dataCombination, false));
            z++;
        }

        // Print the array of filters
        for (int i = 0; i < dataCombi.size(); i++) {
            System.out.println(dataCombi.get(i).getId() + " [-] " + dataCombi.get(i).getDescription() + " [-] " + dataCombi.get(i).getIsFound());
        }

        JSONArray jsonArray = new JSONArray(response);
        int m = 0;
        for (Object jsonObject : jsonArray) {
            System.out.println(jsonObject);
            int n = 0;
            for (Map.Entry<Integer, List<FilteredData>> entry : dataByGroup.entrySet()) {
                if (!dataCombi.get(n).getIsFound()) {
                    boolean isFound = true;
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        String[] prop = entry.getValue().get(i).toString().split("\\.");
                        String element = prop[0];
                        String expectedValue = testData.getProperty(entry.getValue().get(i).toString());
                        String actualValue = jsonArray.getJSONObject(m).get(element).toString();
                        System.out.println("Expected " + element + "=" + expectedValue);
                        System.out.println("Actual   " + element + "=" + actualValue);
                        if (!actualValue.equals(expectedValue)) {
                            isFound = false;
                            break;
                        }
                    }
                    if (isFound) {
                        dataCombi.get(n).setIsFound(true);
                        break;
                    }
                }
                n++;
            }
            m++;
        }

        String message = "Content not found in response: \n";
        boolean isAllFound = true;
        for (int i = 0; i < dataCombi.size(); i++) {
            System.out.println(dataCombi.get(i).getId() + " [-] " + dataCombi.get(i).getDescription() + " [-] " + dataCombi.get(i).getIsFound());
            if (!dataCombi.get(i).getIsFound()) {
                isAllFound = false;
                message += dataCombi.get(i).getDescription() + '\n';
            }
        }
        Assert.assertTrue(message, isAllFound);
    }
}
