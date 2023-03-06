package org.anystub;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ConfigFileUtil {

    private static final ConcurrentHashMap<String, TestSettings> configs = new ConcurrentHashMap<>();


    public ConfigFileUtil() {

    }
    /**
     * returns configuration from a given file
     * @param filename - filename inside resources/anystub/ ; missing yml extension and starting dot will be added
     * @return
     */
    public static TestSettings get(String filename) {
        Objects.requireNonNull(filename);
        return configs.computeIfAbsent(filename, s -> {
            if (!s.startsWith(".")) {
                s = "."+s;
            }
            if (!s.endsWith(".yml")) {
                s = s+".yml";
            }
            AnystubCfg load;
            try (InputStream resourceAsStream = ConfigFileUtil.class.getClassLoader().getResourceAsStream("anystub/" + s)) {

                load = load(resourceAsStream);
            } catch (IOException ex) {
                Logger.getLogger(ConfigFileUtil.class.getName())
                        .finest(() -> String.format("can't load default properties from '%s': %s", filename, ex.getMessage()));

                load = missingConfig();
            } catch (YAMLException ex) {
                java.util.function.Supplier<String> runnable = () -> String.format("can't load default properties from '%s': %s", filename, ex.getMessage());

                if (ex.getCause() instanceof IOException) {
                    Logger.getLogger(ConfigFileUtil.class.getName())
                            .finest(runnable);
                } else {
                    Logger.getLogger(ConfigFileUtil.class.getName())
                            .warning(runnable);
                }

                load = missingConfig();
            }
            return TestSettings
                    .builder()
                    .setHeaders(load.headers.get())
                    .setRequestMask(load.requestMask.get())
                    .setBodyTrigger(load.bodyTrigger.get())
                    .setBodyMethods(load.bodyMethods.get())
                    .setTestFilePrefix(load.testFilePrefix)
                    .build();
        });
    }

    /**
     * loads configuration from given yml file
     * @param s - full path
     * @return
     */
    public static AnystubCfg load(String s) {
        try (InputStream input = new FileInputStream(s)) {
            return load(input);
        }catch (IOException ex) {
            Logger.getLogger(ConfigFileUtil.class.getName())
                    .finest(() -> String.format("can't load default properties from %s: %s", s, ex.getMessage()));
        } catch (YAMLException ex) {
            Logger.getLogger(ConfigFileUtil.class.getName())
                    .warning(()->String.format("can't load default properties from %s: %s", s, ex.getMessage()));
        }


        return missingConfig();
    }

    private static AnystubCfg missingConfig() {
        AnystubCfg missingConfig = new AnystubCfg();
        missingConfig.testFilePrefix = true;
        missingConfig.bodyMethods = new StringOrArray("POST", "PUT", "DELETE");
        return missingConfig;
    }
    public static AnystubCfg load(InputStream input) {
            LoaderOptions loaderOptions = new LoaderOptions();
            Constructor constructor = new Constructor(loaderOptions);
            PropertyUtils propertyUtils = new PropertyUtils();
            propertyUtils.setSkipMissingProperties(true);
            constructor.setPropertyUtils(propertyUtils);
            Yaml yaml = new Yaml(constructor);
            AnystubCfg anystubCfg = yaml.loadAs(input, AnystubCfg.class);
            if (anystubCfg == null) {
                return new AnystubCfg();
            }

            if(anystubCfg.headers == null) {
                anystubCfg.headers = new StringOrArray();
            }
            if(anystubCfg.bodyTrigger == null) {
                anystubCfg.bodyTrigger = new StringOrArray();
            }
            if(anystubCfg.requestMask == null) {
                anystubCfg.requestMask = new StringOrArray();
            }
            if(anystubCfg.bodyMethods == null) {
                anystubCfg.bodyMethods = new StringOrArray();
            }

            return anystubCfg;
    }



    public static class AnystubCfg {
        public StringOrArray headers = new StringOrArray();
        public StringOrArray bodyTrigger = new StringOrArray();
        public StringOrArray requestMask = new StringOrArray();
        public StringOrArray bodyMethods = new StringOrArray();

        /**
         * whatever add test-class-name as prefix to a sub file
         */
        public boolean testFilePrefix = true;

        /**
         * reserved for server storage-mode
         */
        public boolean packagePrefix = false;
        /**
         * reserved for server storage-mode
         */
        public String stubServer = "";

        @Override
        public String toString() {
            return "AnystubCfg{" +
                    ", headers=" + Arrays.toString(headers.get()) +
                    ", bodyTrigger=" + Arrays.toString(bodyTrigger.get()) +
                    ", requestMask=" + Arrays.toString(requestMask.get()) +
                    ", bodyMethods=" + Arrays.toString(bodyMethods.get()) +
                    ", testFilePrefix=" + testFilePrefix +
                    ", packagePrefix=" + packagePrefix +
                    ", stubServer='" + stubServer + '\'' +
                    '}';
        }
    }

    public static class StringOrArray {
        String[] data;

        public String[] get() {
            return data;
        }

        public StringOrArray() {
            this.data = new String[0];
        }
        public StringOrArray(String data) {
            this.data = new String[]{data};
        }
        public StringOrArray(String data,
                             String data1) {
            this.data = new String[]{data,
                    data1};
        }
        public StringOrArray(String data,
                             String data1,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String data4,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    data4,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String data4,
                             String data5,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    data4,
                    data5,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String data4,
                             String data5,
                             String data6,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    data4,
                    data5,
                    data6,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String data4,
                             String data5,
                             String data6,
                             String data7,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    data4,
                    data5,
                    data6,
                    data7,
                    dataN};
        }
        public StringOrArray(String data,
                             String data1,
                             String data2,
                             String data3,
                             String data4,
                             String data5,
                             String data6,
                             String data7,
                             String data8,
                             String dataN) {
            this.data = new String[]{data,
                    data1,
                    data2,
                    data3,
                    data4,
                    data5,
                    data6,
                    data7,
                    data8,
                    dataN};
        }
    }
}
