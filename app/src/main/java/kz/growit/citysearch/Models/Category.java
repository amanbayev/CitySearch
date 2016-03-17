package kz.growit.citysearch.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Талгат on 10.03.2016.
 */
public class Category extends RealmObject {
    private String name;
    private String id;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
