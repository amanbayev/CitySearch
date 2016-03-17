package kz.growit.citysearch.Models;

import io.realm.RealmObject;

/**
 * Created by Талгат on 10.03.2016.
 */
public class Subcategory extends RealmObject{
    private String name;
    private String imageUrl;
    private String id;
    private String parentId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
