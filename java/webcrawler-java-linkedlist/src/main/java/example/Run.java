package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import example.model.RequestClass;
import example.model.ResponseClass;

public class Run implements RequestHandler<RequestClass, ResponseClass> {

    // private static List<String> listaURLGlobal = new LinkedList<>();
    private static Crawler crawler;

    public ResponseClass handleRequest(RequestClass request, Context context) {

        final long start = System.currentTimeMillis();

        try {
            Class<?> c = Class.forName(request.getCollectionNameClass());
            crawler = new Crawler((Class<? extends Collection>) c);
            run(request.getSite());
            long delay = System.currentTimeMillis() - start;
            return new ResponseClass(crawler.getLista().size(), delay);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return null;
    }

    private static void run(String websiteUrl) {
        crawler.add(websiteUrl);

        URLConnection connection;
        BufferedReader in = null;
        try {
            URL website = new URL(websiteUrl);
            connection = website.openConnection();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine();

            Set<String> listaURL = new HashSet<String>(); // garantir somente 1 independente da ordem
            while (inputLine != null) {
                String url = Helper.getCorrectURL(website, inputLine.toString());
                if (url != null) {
                    listaURL.add(url);
                }
                inputLine = in.readLine();
            }
            in.close();

            for (String suburl : listaURL) {
                if (!crawler.getLista().contains(suburl)) {
                    run(suburl);
                }
            }

            listaURL.clear();

        } catch (IOException ex) {
            //System.err.printf("\nRun => %s - error: %s", websiteUrl, ex.getLocalizedMessage());
        } finally {
            if(in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
        }
    }
}