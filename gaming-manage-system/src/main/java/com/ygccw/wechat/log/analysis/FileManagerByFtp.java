package com.ygccw.wechat.log.analysis;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author soldier
 */
public class FileManagerByFtp {
    /**
     * FTP下载单个文件测试
     */
    public static void fileDownloadByFtp() {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;

        try {
            ftpClient.connect("123.207.235.25");
            ftpClient.login("root", "Qwert!2345");

            String remoteFileName = "/var/log/nginx/www_access_2017-08-05.log";
            // fos = new FileOutputStream("E:/test/test_back_081901.sql");
            fos = new FileOutputStream("D:/log/www_access_2017-08-05.log");
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFileName, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    public static void main(String[] args) {
        fileDownloadByFtp();
    }
}
