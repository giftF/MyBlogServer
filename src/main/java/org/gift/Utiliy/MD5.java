package org.gift.Utiliy;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    public static String md5(String str) throws Exception{
        return DigestUtils.md5Hex(str);
    }
}
