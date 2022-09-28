package dev.tr7zw.leise.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

    private static final Pattern namedPattern = Pattern.compile("-([0-9]+)\\.html");
    private static final Pattern idPattern = Pattern.compile("^([0-9]+)$");
    
    public static int getId(String url) {
        Matcher matcher = idPattern.matcher(url);
        if(matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        matcher = namedPattern.matcher(url);
        if(!matcher.find())return -1;
        return Integer.parseInt(matcher.group(1));
    }
    
}
