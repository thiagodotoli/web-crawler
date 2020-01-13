package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private static String urlServer = "http://2tti.com.br";
    private static String urlPath = "/";
    private static Set<String> listaURLGlobal = new HashSet<>();
    private static Pattern urlPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        System.out.println("Start => " + start);

        countURL(urlServer + urlPath);

        // FIM PROCESSAMENTO
        final long delay = System.currentTimeMillis() - start;
        System.out.println("App.total => " + listaURLGlobal.size());
        System.out.println("\nFinalizou => " + delay + " milissegundos" );
    }

    private static void countURL(String urlBase) {
        //System.out.println("Start => " + urlBase);
        listaURLGlobal.add(urlBase);

        URL website;
        URLConnection connection;
        BufferedReader in = null;
        try {
            website = new URL(urlBase);

            connection = website.openConnection();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine();
      
            inputLine = in.readLine();

            Set<String> listaURL = new HashSet<>();
            int totalUrls = 0;
            while (inputLine != null) {
                final String url = getURL(inputLine.toString());
                if (url != null && !listaURLGlobal.contains(url)) {
                   listaURL.add(url);
                    totalUrls += 1;
                }
                inputLine = in.readLine();
            }
            in.close();
            System.out.printf("\nurl: %s - watch: %d - total: %d \n", urlBase, totalUrls, listaURL.size());

            for (String suburl : listaURL) {
                countURL(suburl);
            }

            listaURL.clear();

        } catch (IOException ex) {
            System.err.printf("\ncountURL => error: %s", ex.getLocalizedMessage());
        } finally {
            if(in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    private static String getURL(String html) {
        Matcher matcher = urlPattern.matcher(html);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            // now you have the offsets of a URL match
            String href = null;
            try {
                href = html.substring(matchStart+1,matchEnd-1);
            } catch (StringIndexOutOfBoundsException e) {
                return null;
            }

            try {
                if(href != null &&
                    !href.isEmpty() &&
                    !href.equals("#") && 
                    href.indexOf("instagram")<0 && 
                    href.indexOf("google")<0 && 
                    href.indexOf("facebook")<0 && 
                    href.indexOf("linkedin")<0 && 
                    href.indexOf(".n3")<0 && 
                    href.indexOf(".cfm")<0 && 
                    href.indexOf(".css")<0 && 
                    href.indexOf(".js")<0 && 
                    href.indexOf(".pdf")<0 && 
                    href.indexOf(".n3")<0 && 
                    href.indexOf("javascript:")<0 && 
                    href.indexOf("data:")<0 && 
                    href.indexOf("./")<0) {
                
                    if(href.substring(0,1).equals("/")) {  // abosolute path without server
                        href = urlServer + href;
                    } else if(!href.substring(0,4).toLowerCase().equals("http")) {  // relative path
                        href = urlServer + urlPath + href;
                    }
                    return href;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.err.printf("\ngetURL => linha: %s - error: %s", html, e.getMessage());
            }
        }
        return null;
    }
}