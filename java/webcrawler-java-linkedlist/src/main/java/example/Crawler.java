package example;

import java.util.Collection;

/**
 * Crawler
 */
public class Crawler {

    private Collection<String> lista;
    
    public <T extends Collection<String>> Crawler(final Class<T> c)
            throws InstantiationException, IllegalAccessException {
        lista = c.newInstance();
    }

    public void add(String url) {
        lista.add(url);
    }

     /**
      * @return the lista
      */
     public Collection<String> getLista() {
         return lista;
     }

}