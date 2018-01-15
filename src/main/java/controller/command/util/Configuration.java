package controller.command.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private String factoryClass;
    private String secretKey;

    private Configuration() {
        load();
    }

    private void load() {
        try(InputStream inputStream = this.getClass().getResourceAsStream("/configuration.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            factoryClass = properties.getProperty("dao.factory.class");
            secretKey = properties.getProperty("secret.key");
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Holder{
        private static Configuration INSTANCE = new Configuration();
    }

    public static Configuration getInstance() {
        return Holder.INSTANCE;
    }

    public String getFactoryClass(){
        return factoryClass;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
