package com.ygccw.crawler.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class ConLinux {
//    public static void main(String[] args) throws Exception {
//        ConLinux conLinux = new ConLinux();
//        conLinux.restartMSite();
//    }

    /**
     * 重启移动端服务
     *
     * @throws Exception
     */
    public void restartMSite() {
        try {
            String userName = "root"; // 用户名
            String password = "Qwert!2345"; // 密码
            String host = "118.89.59.38"; // 服务器地址
            int port = 22; // 端口号
            Session session = getSession(userName, password, host, port);
            commit(session, "rm -rf /opt/app/logs/gaming-msite/catalina.out"); //关闭移动端服务
            commit(session, "/opt/app/bin/tomcat-gaming-msite-stop.sh"); //关闭移动端服务
            commit(session, "/opt/app/bin/tomcat-gaming-msite-start.sh"); //启动移动端服务
            if (null != session) {
                session.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启pc端服务
     *
     * @throws Exception
     */
    public void restartPcSite() {
        try {
            String userName = "root"; // 用户名
            String password = "Qwert!2345"; // 密码
            String host = "118.89.59.38"; // 服务器地址
            int port = 22; // 端口号
            Session session = getSession(userName, password, host, port);
            commit(session, "rm -rf /opt/app/logs/gaming-website/catalina.out"); //关闭pc端服务
            commit(session, "/opt/app/bin/tomcat-gaming-manage-system-stop.sh"); //关闭pc端服务
            commit(session, "/opt/app/bin/tomcat-gaming-manage-system-start.sh"); //启动pc端服务
            if (null != session) {
                session.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启pc端服务
     *
     * @throws Exception
     */
    public void restartFileSystem() {
        try {
            String userName = "root"; // 用户名
            String password = "Qwert!2345"; // 密码
            String host = "123.207.235.25"; // 服务器地址
            int port = 22; // 端口号
            Session session = getSession(userName, password, host, port);
            commit(session, "/opt/app/bin/tomcat-file-service-stop.sh"); //关闭pc端服务
            commit(session, "/opt/app/bin/tomcat-file-service-start.sh"); //启动pc端服务
            if (null != session) {
                session.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Session getSession(String userName, String password, String host, int port) throws Exception {
        JSch jsch = new JSch(); // 创建JSch对象
// String userName = "root";// 用户名
// String password = "Qwert!2345";// 密码
// String host = "118.89.59.38";// 服务器地址
// int port = 22;// 端口号
        Session session = jsch.getSession(userName, host, port); // 根据用户名，主机ip，端口获取一个Session对象
        session.setPassword(password); // 设置密码
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        int timeout = 60000000;
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        return session;
    }

    private void commit(Session session, String cmd) throws Exception {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(cmd);
        channelExec.setInputStream(null);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
        String buf = null;
        StringBuffer sb = new StringBuffer();
        while ((buf = reader.readLine()) != null) {
            sb.append(buf);
            System.out.println(buf); // 打印控制台输出
        }
        reader.close();
        channelExec.disconnect();
    }
}
