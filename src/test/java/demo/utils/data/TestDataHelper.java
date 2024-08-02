package demo.utils.data;

import demo.utils.config.Resources;
import org.junit.Assert;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class TestDataHelper {

    /**
     * This method returns a list of properties with prefix the test case ID and with a suffix the test step number
     *
     * @return
     */
    public Properties getTestData(int step) {
        File f = new File(Resources.getResourcesPath() + "/data/" + getPackageName() + ".properties");
        if (f.exists())
            return getProperties(
                loadProperties(Resources.getResourcesPath() + "/data/" + getPackageName() + ".properties"),
                getClassName(), step);
        else
            return getProperties(
                    loadProperties(Resources.getResourcesPath() + "/data/" + getClassName() + ".properties"),
                    getClassName(), step);
    }

    /**
     * This method returns the Class Name of the Test Case (Test Case ID)
     *
     * @return
     */
    private String getClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String testCaseID = stackTraceElements[4].getClassName();
        return testCaseID.substring(testCaseID.lastIndexOf('.') + 1).replaceAll("__", "-");
    }

    /**
     * This method returns the Package Name of the Test Case (Test Suite ID)
     *
     * @return
     */
    private String getPackageName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String testCaseIDWithPackage = stackTraceElements[4].getClassName();
        String testCaseID = testCaseIDWithPackage.substring(testCaseIDWithPackage.lastIndexOf('.'));
        String testSuiteIDWithPackage = testCaseIDWithPackage.replaceAll(testCaseID, "");
        return testSuiteIDWithPackage.substring(testSuiteIDWithPackage.lastIndexOf('.') + 1);
    }

    /**
     * This method is used by method 'getTestData' and is used to load properties (test data) from external property files
     *
     * @param testDataFile is the path of the property test data file
     * @return
     */
    private Properties loadProperties(String testDataFile) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = new FileInputStream(testDataFile);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            prop.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * This method is used from the 'getTestData' method and returns specific subset based on prefix (test case id) and suffix (test step number)
     *
     * @param params properties as were returned by method "loadProperties"
     * @param prefix test case id prefix to get subset of properties
     * @param step   test step number suffix to get subset of properties
     * @return
     */
    private Properties getProperties(Properties params, String prefix, int step) {
        Properties result = new Properties();

        Enumeration<?> names = params.propertyNames();

        if (step > 0) {
            prefix += "." + step;
        }
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            if (name.indexOf(prefix) == 0) {
                result.put(name.substring(prefix.length() + 1), params.get(name));
            }
        }

        return result;
    }

    public static Properties getParameters(Properties params, String prefix) {
        Properties targetParameters = new Properties();

        Enumeration allParameters = params.propertyNames();
        while (allParameters.hasMoreElements()) {
            String parameter = (String) allParameters.nextElement();
            if (parameter.startsWith(prefix)) {
                if (parameter.length() == prefix.length()) {
                    targetParameters.put(parameter, params.get(parameter));
                } else if (parameter.substring(prefix.length()).startsWith(".")) {
                    targetParameters.put(parameter.substring(prefix.length() + 1), params.get(parameter));
                }
            }
        }
        return targetParameters;
    }


    public static List<String> getMultipleStringParameters(Properties testData, String parameterPrefix) {
        Properties targetProperties = TestDataHelper.getParameters(testData, parameterPrefix);

        List<Integer> keys = targetProperties.keySet().stream()
                .filter(k -> k.toString().matches("[0-9]+"))
                .map(k -> Integer.parseInt(k.toString()))
                .sorted()
                .collect(Collectors.toList());

        return keys.size() > 0 ? keys.stream()
                .map(k -> targetProperties.getProperty(String.valueOf(k)))
                .collect(Collectors.toList()) : Collections.singletonList(testData.getProperty(parameterPrefix));


    }

    /**
     * This method returns the value of a String parameter from properties file
     *
     * @param testData the target dataset [Properties file]
     * @param parameterName the name of the parameter as appears in the properties file [String]
     *
     * @return the parameter value as String
     */
    public static String getStringParameter(Properties testData, String parameterName) {
        if (!testData.containsKey(parameterName)) {
            Assert.fail("Parameter '" + parameterName + "' does not exist in Test Data!");
        }

        return testData.getProperty(parameterName);
    }

}
