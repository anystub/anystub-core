package org.anystub;

import java.util.Arrays;

public class AnystubCfg {
    public boolean allHeaders = false;
    public StringOrArray headers = new StringOrArray();
    public StringOrArray bodyTrigger = new StringOrArray();
    public StringOrArray bodyMask = new StringOrArray();

    public boolean packagePrefix = false;
    public String stubServer = "";

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
        public StringOrArray(String data, String data1) {
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
                    dataN};
        }
    }

    @Override
    public String toString() {
        return "AnystubCfg{" +
                "allHeaders=" + allHeaders +
                ", headers=" + Arrays.toString(headers.get()) +
                ", bodyTrigger=" + Arrays.toString(bodyTrigger.get()) +
                ", bodyMask=" + Arrays.toString(bodyMask.get()) +
                ", prefixPackage=" + packagePrefix +
                ", stubServer='" + stubServer + '\'' +
                '}';
    }
}
