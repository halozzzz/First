package com.ego.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * FTP工具类
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * 文件上传
     *
     * @param host
     * @param port
     * @param hostname
     * @param password
     * @param path
     * @param fileName
     * @param is
     * @return
     */
    public static String fileUpload(String host, Integer port,
                                    String hostname, String password, String path,
                                    String fileName, InputStream is) {
        // 1.初始化FTPClient
        FTPClient ftpClient = new FTPClient();
        try {
            // 2.设置编码字符集
            ftpClient.setControlEncoding("UTF-8");
            // 3.连接ftp服务器
            ftpClient.connect(host, port);
            // 4.设置用户名和密码
            ftpClient.login(hostname, password);
            // 5.获取连接状态码
            int result = ftpClient.getReplyCode();
            //System.out.println("连接状态码：" + result);
            // 具体的搜索“FTP上传常见错误详解”
            if (!FTPReply.isPositiveCompletion(result)) {
                // 断开连接
                ftpClient.disconnect();
                // 记录日志
                logger.error("文件上传失败，失败原因：" + result);
                return null;
            }
            // 6.判断是否存在路径
            // path是/home/ftpuser/ego/年/月/日
            boolean flag = ftpClient.changeWorkingDirectory(path);
            //System.out.println("路径是否存在：" + flag);
            // 7.存在，选择路径，不存在，创建路径并选择
            if (!flag) {
                String pathArr[] = path.split("/");
                String temp = "";
                // 循环判断创建路径
                for (int i = 0; i < pathArr.length; i++) {
                    temp += pathArr[i] + "/";
                    ftpClient.makeDirectory(temp);
                }
            }
            // 选择路径
            ftpClient.changeWorkingDirectory(path);
            // 8.指定上传方式为二进制方式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 9.文件上传
            // 获取文件名后缀
            String remote = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = UUIDUtil.getUUID() + remote;
            // 文件上传
            flag = ftpClient.storeFile(newFileName, is);
            if (flag)
                return newFileName;
            logger.error("文件上传失败！失败原文件名为：" + fileName);
        } catch (IOException e) {
            logger.error("文件上传失败！失败原因：" + e.getMessage());
        } finally {
            try {
                // 关闭连接
                if (null != ftpClient && ftpClient.isConnected())
                    ftpClient.disconnect();

                // 关闭文件流
                if (null != is)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 文件删除
     *
     * @param host
     * @param port
     * @param hostname
     * @param password
     * @param path
     * @param fileName
     * @return
     */
    public static boolean fileDelete(String host, Integer port,
                                     String hostname, String password,
                                     String path, String fileName) {
        boolean flag = false;
        // 1.初始化FTPClient
        FTPClient ftpClient = new FTPClient();
        try {
            // 2.设置编码字符集
            ftpClient.setControlEncoding("UTF-8");
            // 3.连接ftp服务器
            ftpClient.connect(host, port);
            // 4.设置用户名和密码
            ftpClient.login(hostname, password);
            // 5.获取连接状态码
            int result = ftpClient.getReplyCode();
            //System.out.println("连接状态码：" + result);
            // 具体的搜索“FTP上传常见错误详解”
            if (!FTPReply.isPositiveCompletion(result)) {
                // 断开连接
                ftpClient.disconnect();
                // 记录日志
                logger.error("文件删除失败，失败原因：" + result);
                return flag;
            }
            // 6.判断是否存在路径
            // path是/home/ftpuser/ego/年/月/日
//            String path = "/home/ftpuser/ego/2019/05/06";
            flag = ftpClient.changeWorkingDirectory(path);
            //System.out.println("路径是否存在：" + flag);
            // 7.存在，删除，不存在，记录日志
            if (!flag) {
                logger.error("文件删除失败，该路径不存在！");
                return flag;
            }
            // 9.文件删除
            flag = ftpClient.deleteFile(path + "/" + fileName);
            if (!flag) {
                logger.error("文件删除失败，该文件不存在！");
                return flag;
            }
//            System.out.println("删除成功：" + flag);
            flag = true;
        } catch (IOException e) {
            logger.error("文件删除失败！失败原因：" + e.getMessage());
        } finally {
            try {
                // 关闭连接
                if (null != ftpClient && ftpClient.isConnected())
                    ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
