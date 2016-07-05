package com.ygccw.wechat.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author nick.guo
 */
public class Files {
    public static final String SUFFIX = "/";

    /**
     * 用于拼凑路径,去掉多余的斜杠'/'
     *
     * @param head
     * @param paths
     * @return
     */
    public static String contact(String head, String... paths) {
        StringBuilder sb = new StringBuilder(removeTail(head));
        for (String path : paths) {
            if (StringUtils.isEmpty(path)) continue;
            sb.append(removeTail(addHead(path)));
        }
        return sb.toString();
    }


    public static String addHead(String path) {
        if (StringUtils.isEmpty(path)) return path;
        if (!path.startsWith(SUFFIX)) return SUFFIX + path;
        return path;
    }

    public static String removeTail(String path) {
        if (StringUtils.isEmpty(path)) return path;
        if (path.endsWith(SUFFIX)) {
            return removeTail(path.substring(0, path.length() - 1));
        }
        return path;
    }

   /* public static void main(String[] args) {
        System.out.println(contact("http://hahah.dsdfads/", "", "/hehe/"));
    }*/
}
