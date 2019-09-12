package eu.xgp.staffreport.commons.utils;

public interface FUtils {
    default String format(String arg0) {
        return arg0.replaceAll("&", "§").replaceAll("%n", "\n");
    }
}
