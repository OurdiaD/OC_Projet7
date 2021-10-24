package com.openclassrooms.go4lunch.models.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("html_attributions")
    @Expose
    public List<String> html_attributions;
    @SerializedName("photo_reference")
    @Expose
    public String photo_reference;
    @SerializedName("width")
    @Expose
    public Integer width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
