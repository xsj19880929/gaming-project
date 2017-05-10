package com.ygccw.msite.utils;

import com.google.common.io.BaseEncoding;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public abstract class HMAC {
//    public static void main(String[] arg) {
//        System.out.println(hash("12", "123456"));
//    }

    public static String hash(String key, String message) {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacMD5");
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            return BaseEncoding.base64().encode(mac.doFinal(message.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
