package eu.xgp.staffreport.commons.utils;

public interface Utils extends FUtils {

    boolean isTitleEnabled();

    boolean isSubtitleEnabled();

    String getTitle(String staff);

    String getSubtitle(String staff);

    int getTitleStay();

    int getTitleFadeIn();

    int getTitleFadeOut();

    int getSubtitleStay();

    int getSubtitleFadeIn();

    int getSubtitleFadeOut();
}
