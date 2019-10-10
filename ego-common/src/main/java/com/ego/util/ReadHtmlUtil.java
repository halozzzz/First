package com.ego.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @see(功能介绍) : 读取HTML模板工具类
 * @version(版本号) : 1.0
 * @author(创建人) : Dylan
 * @since : JDK 1.8
 */
public class ReadHtmlUtil {

    /**
     * 根据文件名称读取指定文件
     *
     * @return
     */
    public static String getMailString(String html) {
        StringBuffer buff = new StringBuffer();
        InputStreamReader in = null;
        BufferedReader br = null;
        // 获取html模板
        String path = ReadHtmlUtil.class.getClassLoader()
                .getResource("html/" + html + ".html").getPath();
        File file = new File(path);
        try {
            in = new InputStreamReader(new FileInputStream(file));
            br = new BufferedReader(in);
            String line = null;
            // 逐行读取
            while ((line = br.readLine()) != null) {
                buff.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buff.toString();
    }

    public static void main(String[] args) {
        // 加"/"
        System.out.println(ReadHtmlUtil.class
                .getResource("/html/registerEmail.html").getPath());
        // 不加"/"
        System.out.println(ReadHtmlUtil.class.getClassLoader()
                .getResource("html/registerEmail.html").getPath());
        System.out.println(ReadHtmlUtil.getMailString("registerEmail"));
    }

}
