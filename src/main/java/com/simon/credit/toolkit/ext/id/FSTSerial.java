package com.simon.credit.toolkit.ext.id;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FSTSerial {

    private static void serialize(FSTSerializer fst, String fileName) {
        try {
            FSTBean fstBean = new FSTBean();
            fstBean.setV0int(1);
            fstBean.setV0str("v0str");
//            fstBean.setAaa("1213");
            byte[] v1 = fst.serialize(fstBean);

            FileOutputStream fos = new FileOutputStream(new File("byte.bin"));
            fos.write(v1, 0, v1.length);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deserilize(FSTSerializer fst, String fileName) {
        try {
            FileInputStream fis = new FileInputStream(new File("byte.bin"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            int length = 0;
            while ((length = fis.read(buf)) > 0) {
                baos.write(buf, 0, length);
            }
            fis.close();
            buf = baos.toByteArray();
            FSTBean deserial = (FSTBean) fst.deserialize(buf);
            System.out.println(deserial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FSTSerializer fst = new FSTSerializer();
//      serialize(fst, "byte.bin");
        deserilize(fst, "byte.bin");
    }
}