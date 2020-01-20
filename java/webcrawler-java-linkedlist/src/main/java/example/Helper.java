package example;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper
 */
public class Helper {

    private static Pattern urlPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static String getCorrectURL(URL website, String value) {
        Matcher matcher = urlPattern.matcher(value);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            // now you have the offsets of a URL match
            String href = null;
            try {
                href = value.substring(matchStart + 1, matchEnd - 1);
            } catch (StringIndexOutOfBoundsException e) {
                return null;
            }

            try {
                if (href != null && !href.isEmpty() && !href.equals("#") && href.toLowerCase().indexOf("instagram") < 0
                        && href.toLowerCase().indexOf("google") < 0 && href.toLowerCase().indexOf("facebook") < 0
                        && href.toLowerCase().indexOf("linkedin") < 0 && href.toLowerCase().indexOf(".n3") < 0
                        && href.toLowerCase().indexOf(".cfm") < 0 && href.toLowerCase().indexOf(".css") < 0
                        && href.toLowerCase().indexOf(".ico") < 0 && href.toLowerCase().indexOf(".js") < 0
                        && href.toLowerCase().indexOf(".pdf") < 0 && href.toLowerCase().indexOf(".png") < 0
                        && href.toLowerCase().indexOf(".jpg") < 0 && href.toLowerCase().indexOf(".jpeg") < 0
                        && href.toLowerCase().indexOf(".n3") < 0 && href.toLowerCase().indexOf("javascript:") < 0
                        && href.toLowerCase().indexOf("data:") < 0 && href.indexOf("./") < 0) {

                    if (href.substring(href.length() - 1).equals("/"))
                        href = href.substring(0, href.length() - 1);

                    URL correctURL;
                    if (href.substring(0, 1).equals("/")) { // abosolute path without server
                        correctURL = new URL(website.getProtocol(), website.getHost(), -1, href);
                    } else if (!href.substring(0, 4).toLowerCase().equals("http")) { // relative path
                        correctURL = new URL(website.getProtocol(), website.getHost(), -1, website.getPath() + href);
                    } else {
                        correctURL = new URL(href);
                    }

                    return correctURL.toURI().toString();
                }
            } catch (StringIndexOutOfBoundsException e) {
                //System.err.printf("\ngetURL(1) => linha: %s - error: %s", value, e.getMessage());
            } catch (MalformedURLException e) {
                //System.err.printf("\ngetURL(2) => linha: %s - error: %s", value, e.getMessage());
            } catch (URISyntaxException e) {
                //System.err.printf("\ngetURL(3) => linha: %s - error: %s", value, e.getMessage());
            }
        }
        return null;
    }
}