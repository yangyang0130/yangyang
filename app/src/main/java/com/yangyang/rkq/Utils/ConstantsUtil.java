package com.yangyang.rkq.Utils;

public final class ConstantsUtil {

    public ConstantsUtil() {
    }
    private static class SingletonHolder {
        private static final ConstantsUtil INSTANCE = new ConstantsUtil();
    }

    public static ConstantsUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }



    public final String INTENT_KEY_URL = "url";
}
