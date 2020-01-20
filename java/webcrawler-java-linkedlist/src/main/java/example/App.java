package example;

import example.model.RequestClass;
import example.model.ResponseClass;

/**
 * App
 */
public class App {

    public static void main(String[] args) throws ClassNotFoundException {
        Run r = new Run();
        ResponseClass response = r.handleRequest(
            new RequestClass("http://www.2tti.com.br/", "java.util.TreeSet"), 
        null);
        System.out.println(response.toString());
    }
}