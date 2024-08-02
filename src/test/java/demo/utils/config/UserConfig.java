package demo.utils.config;

import java.util.Base64;

public class UserConfig {

    public static String adminUsername;
    public static String adminPassword;
    public static String invalidUsername;
    public static String invalidPassword;

    public static String getUsername(String user) {
        switch (user) {
            case "admin": {
                return adminUsername;
            }
            case "invalid": {
                return invalidUsername;
            }
            default: {
                return null;
            }
        }
    }

    public static String getPassword(String user) {
        switch (user) {
            case "admin": {
                return adminPassword;
                //return new String(Base64.getDecoder().decode(adminPassword));
            }
            case "invalid": {
                return new String(Base64.getDecoder().decode(invalidPassword));
            }
            default: {
                return null;
            }
        }
    }


}
