package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class App {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        URL website = new URL("http://www8.receita.fazenda.gov.br/SimplesNacional/");
        URLConnection connection = website.openConnection();

        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
            );

        StringBuilder response = new StringBuilder();
        String inputLine = in.readLine();

        inputLine = in.readLine();
        while (inputLine != null) {  
            response.append(inputLine);
            inputLine = in.readLine();
        }

        in.close();
        
        // FIM PROCESSAMENTO 
        long delay = System.currentTimeMillis() - start;
        
        System.out.println(response.toString());

        System.out.println("Demorou " + delay + " milissegundos");
    }
}