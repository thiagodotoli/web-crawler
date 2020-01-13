package br.com.tbento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    private static String urlServer = "http://2tti.com.br";
    private static String urlPath = "/";
    private static Set<String> listaURLGlobal = new TreeSet<String>();
    private static Pattern urlPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",
        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        System.out.println("Start => " + start);

        countURL(urlServer + urlPath);

        // FIM PROCESSAMENTO
        long delay = System.currentTimeMillis() - start;
        System.out.println("\nApp.total => " + listaURLGlobal.size());
        System.out.println("\nFinalizou processamento => " + delay + " milissegundos" );

        for (String url : listaURLGlobal) {
            System.out.println(url);
        }
        delay = System.currentTimeMillis() - start;
        System.out.println("\nFinalizou listagem => " + delay + " milissegundos" );

    }

    private static void countURL(String urlBase) {
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
            while (inputLine != null) {
                final String url = getURL(inputLine.toString());
                if (url != null && !listaURLGlobal.contains(url)) {
                   listaURL.add(url);
                }
                inputLine = in.readLine();
            }
            in.close();
            
            for (String suburl : listaURL) {
                    countURL(suburl);
            }

            listaURL.clear();

        } catch (IOException ex) {
            System.err.printf("\ncountURL => %s - error: %s", urlBase, ex.getLocalizedMessage());
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
                    href.toLowerCase().indexOf("instagram")<0 && 
                    href.toLowerCase().indexOf("google")<0 && 
                    href.toLowerCase().indexOf("facebook")<0 && 
                    href.toLowerCase().indexOf("linkedin")<0 && 
                    href.toLowerCase().indexOf(".n3")<0 && 
                    href.toLowerCase().indexOf(".cfm")<0 && 
                    href.toLowerCase().indexOf(".css")<0 && 
                    href.toLowerCase().indexOf(".ico")<0 && 
                    href.toLowerCase().indexOf(".js")<0 && 
                    href.toLowerCase().indexOf(".pdf")<0 && 
                    href.toLowerCase().indexOf(".png")<0 && 
                    href.toLowerCase().indexOf(".jpg")<0 && 
                    href.toLowerCase().indexOf(".jpeg")<0 && 
                    href.toLowerCase().indexOf(".n3")<0 && 
                    href.toLowerCase().indexOf("javascript:")<0 && 
                    href.toLowerCase().indexOf("data:")<0 && 
                    href.indexOf("./")<0) {
                
                    if(href.substring(0,1).equals("/")) {  // abosolute path without server
                        href = urlServer + href;
                    } else if(!href.substring(0,4).toLowerCase().equals("http")) {  // relative path
                        href = urlServer + urlPath + href;
                    }

                    if(href.substring(href.length()-1).equals("/"))
                        href = href.substring(0, href.length()-1);

                    return href;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.err.printf("\ngetURL => linha: %s - error: %s", html, e.getMessage());
            }
        }
        return null;
    }
}