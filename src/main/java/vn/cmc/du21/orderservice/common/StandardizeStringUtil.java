package vn.cmc.du21.orderservice.common;

import java.util.Locale;

public class StandardizeStringUtil {
    private StandardizeStringUtil(){
        super();
    }
    public static String standardizeString(String s){
        return s.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }
}