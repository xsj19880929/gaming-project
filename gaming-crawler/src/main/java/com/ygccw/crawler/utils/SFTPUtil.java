package com.ygccw.crawler.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import core.framework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author soldier
 */
public class SFTPUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String host = "123.207.235.25";
    private String username = "root";
    private String password = "Qwert!2345";
    private int port = 22;
    private ChannelSftp sftp = null;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SFTPUtil ftp = new SFTPUtil();
        ftp.connect();
        ftp.download("/var/log/nginx", "www_access_2017-08-05.log", "D://logs//www_access_2017-08-05.log");
        ftp.disconnect();

    }

    /**
     * connect server via sftp
     */
    public void connect() {
        try {
            if (sftp != null) {
                System.out.println("sftp is not null");
            }
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            logger.info("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("Session connected.");
            logger.info("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect with server
     */
    public void disconnect() {
        try {
            if (this.sftp != null) {
                if (this.sftp.isConnected()) {
                    this.sftp.disconnect();
                    this.sftp.getSession().disconnect();

                } else if (this.sftp.isClosed()) {
                    logger.info("sftp is closed already");
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }

    }

    /**
     * sftp下载文件
     *
     * @param directory
     * @param downloadFile
     * @param saveFile
     */
    public void download(String directory, String downloadFile, String saveFile) {
        StopWatch stopWatch = new StopWatch();
        logger.info("download:" + downloadFile + "starting");
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("download over,time:" + stopWatch.elapsedTime());
    }


}
