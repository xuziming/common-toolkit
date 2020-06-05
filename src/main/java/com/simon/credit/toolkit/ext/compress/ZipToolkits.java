package com.simon.credit.toolkit.ext.compress;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;

/**
 * 压缩工具类
 * @author XUZIMING 2020-06-06
 */
public class ZipToolkits {

    /**
     * 加密压缩打包
     */
    public static void encryptZip(String sourceFilePath, String zipFilePath, String password) {
        try {
            // 设置压缩文件参数
            ZipParameters parameters = new ZipParameters();
            // 设置压缩方法
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            // 设置压缩级别
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            // 设置压缩文件是否加密
            parameters.setEncryptFiles(true);
            // 设置aes加密强度
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            // 设置加密方法
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            // 设置密码
            parameters.setPassword(password.toCharArray());

            // 压缩文件,并生成压缩文件
            ArrayList<File> filesToAdd = new ArrayList<File>();
            File file = new File(sourceFilePath);
            filesToAdd.add(file);

            ZipFile zipFile = new ZipFile(zipFilePath);
            System.out.println(zipFile.getFile().getPath());
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sourceFilePath = "/Users/xuziming/Projects/touna/common-toolkit-core.zip";
        String zipFilePath = "/Users/xuziming/Projects/touna/test.zip";
        encryptZip(sourceFilePath, zipFilePath, "123456");
        System.out.println("zip over.");
    }

}