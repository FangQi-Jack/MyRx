package com.jackfangqi.commonutil.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Compiler: Android Studio
 * Project: CommonUtil
 * Author: Jack Fang
 * Email: fangqi@xywy.com
 * Date: 2016/8/19 9:06
 */
public final class FileUtil {
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtil() {
        throw new AssertionError();
    }

    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.exists())
            return null;

        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals(""))
                    fileContent.append("\n");
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException: " + e);
        } finally {
            IOUtil.close(reader);
        }
    }

//    public static boolean writeFile(String filePath, String content, boolean append) {
//
//    }
}
