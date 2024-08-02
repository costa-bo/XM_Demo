package demo.utilsCommon.ws.template;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MergeFrom {
    private String templateFile;
    private Map<String, String> defaultValues;

    private static FreemarkerTemplate freemarkerTemplate = new FreemarkerTemplate();

    /**
     * Method to build a request body based on a template.
     * Templates reside within path 'src/test/resources/data/templates'
     * @param template path of the template (after resources directory) e.g. data/templates/rest/CreateContact.json
     * @return a request body as an object based on the given template
     */
    public static MergeFrom template(String template) {
        return new MergeFrom(template);
    }

    /**
     * Method to set default values based on a property file.
     * @param defaultValues E.g. FieldValues.in("data/templates/defaults/CreateContact.properties")
     * @return a request body as an object based on the given template and the default values
     */
    public MergeFrom withDefaultValuesFrom(Map<String, String> defaultValues) {
        this.defaultValues = defaultValues;
        return this;
    }

    /**
     * Method to set values for a specific test based on a property file
     * @param testData e.g. Variable testData that is a Property variable based on getTestData() method
     * @return a request body as an object based on the given template, the default values
     * and the values for the specific test case
     */
    public String withFieldsFrom(Properties testData) {
        Map<String, String> map;
        if (null == defaultValues) {
            map = new HashMap<>();
        } else {
            map = new HashMap<>(defaultValues);
        }
        if (null != testData) {
            for (Object key : testData.keySet()) {
                map.put(key.toString(), testData.getProperty(key.toString()));
            }
        }

        Template template = freemarkerTemplate.getTemplate(templateFile);

        Writer writer = new StringWriter();

        try {
            template.process(map, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException("Failed to merge test data template", e);
        }

        return writer.toString();
    }

    private MergeFrom(String templateFile) {
        this.templateFile = templateFile;
    }

}
