package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {
    @SerializedName("imgCategories")
    @Expose
    private String imageCategories;
    @SerializedName("nameCategories")
    @Expose
    private String nameCategories;

    public String getImageCategories() {
        return imageCategories;
    }

    public void setImageCategories(String imageCategories) {
        this.imageCategories = imageCategories;
    }

    public String getNameCategories() {
        return nameCategories;
    }

    public void setNameCategories(String nameCategories) {
        this.nameCategories = nameCategories;
    }
}
