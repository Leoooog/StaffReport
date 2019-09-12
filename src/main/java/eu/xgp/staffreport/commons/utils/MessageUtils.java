package eu.xgp.staffreport.commons.utils;

public interface MessageUtils extends FUtils {

    String noPermMessage();

    String playerNotOnlineMessage(String notOnline);

    String onlyPlayerMessage();

    String ssUsageMessage();

    String reportUsageMessage();

    String ssStatusMessage(String staff);

    String youReportedMessage(String reported, String reason);
}
