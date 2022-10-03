package org.gift.Utiliy;

public class ManyUtil {
    public static Boolean LengthComparison(String str, Integer low, Integer up){
        LogUtil.LOGGING.info(String.valueOf(str.length()));
        return str.length() <= up && str.length() >= low;
    }
}
