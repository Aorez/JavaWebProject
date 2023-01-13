package com.aorez.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqlFileReader {
    public static String readSqlFile(String className, String methodName) {
        String sqlFilePath = "src/main/resources/sql/" + className + "-" + methodName + ".sql";
        return readSqlFileAsString(sqlFilePath);
    }

    public static String readSqlFileAsString(String sqlFilePath) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFilePath), "UTF-8"));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = bufferedReader.readLine()) != null) {
                // 显示行号
                // System.out.println("line " + line + ": " + tempString);

                stringBuffer = stringBuffer.append(" " + tempString);
                line++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                }
            }
        }

        return stringBuffer.toString();
    }
}
