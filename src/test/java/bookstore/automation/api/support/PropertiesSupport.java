package bookstore.automation.api.support;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.out;

public class PropertiesSupport {
    private static final String pathProp = "/src/test/resources/secrets.properties";
    private static final String pathSystem = System.getProperty("user.dir");
    private static Properties prop;

    public static String getSecret(String secret) {
        prop = new Properties();
        try {
            InputStream input = new FileInputStream(pathSystem + pathProp);
            prop.load(input);
        } catch (Exception e) {
            out.print("Error loading property: " + e);
        }
        return prop.getProperty(secret);
    }

}