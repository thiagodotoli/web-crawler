package example.model;

/**
 * RequestClass
 */
public class RequestClass {
    
    String site;
    String collectionNameClass;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCollectionNameClass() {
        return collectionNameClass;
    }

    public void setCollectionNameClass(String collectionNameClass) {
        this.collectionNameClass = collectionNameClass;
    }

    public RequestClass(String site, String collectionNameClass) {
        this.site = site;
        this.collectionNameClass = collectionNameClass;
    }

    public RequestClass() {
    }
    
}