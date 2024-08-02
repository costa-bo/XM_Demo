package demo.utils.config;

import java.nio.file.Paths;

public class Resources {

    /**
     * This method returns the path of resources folder
     *
     * @return
     */
    public static String getResourcesPath() {
        return getAbsolutePath() + "/src/test/resources";
    }

    /**
     * This method returns the path of download folder
     *
     * @return
     */
    public static String getDownloadPath() {
        String downloadPath = "";
        if (null != SerenityConfig.getConfiguration("download.default_directory")) {
            if (SerenityConfig.getConfiguration("download.default_directory").isEmpty()) {
                downloadPath = getResourcesPath() + "/data/download";
            } else {
                downloadPath = SerenityConfig.getConfiguration("download.default_directory");
            }
        } else {
            downloadPath = getResourcesPath() + "/data/download";
        }
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            return downloadPath.replace("/", "\\");
        else
            return downloadPath;
    }

    /**
     * This method returns the relative path of Template File as requested by MergeFrom method of common-test-utils
     *
     * @return
     */
    public static String getTemplate(String templateFilename) {
        return "data/templates/message/" + templateFilename;
    }
    public static String getSchema(String schemaFilename) {
        return getResourcesPath() + "/data/schema/" + schemaFilename;
    }

    /**
     * This method returns the absolute path of the test application (.jar file)
     *
     * @return
     */
    private static String getAbsolutePath() {
        String absPath = Paths.get(".").toAbsolutePath().normalize().toString();
        return absPath.replace("\\", "/");
    }

}
