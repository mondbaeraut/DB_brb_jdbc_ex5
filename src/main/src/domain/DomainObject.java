package domain;

/**
 * Created by mond on 10.05.2016.
 */
public abstract class DomainObject {
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
