package com.ygccw.wechat.common.utils;

import java.util.UUID;

/**
 * @author nick.guo
 */
public final class UUIDUtil {
    private UUIDUtil() {
    }

    public static String generate() {
        UUID uuid = UUID.randomUUID();
        long leastSigBits = uuid.getLeastSignificantBits();
        long mostSigBits = uuid.getMostSignificantBits();
        return digits(mostSigBits >> 32, 8)
            + digits(mostSigBits >> 16, 4)
            + digits(mostSigBits, 4)
            + digits(leastSigBits >> 48, 4)
            + digits(leastSigBits, 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

}
