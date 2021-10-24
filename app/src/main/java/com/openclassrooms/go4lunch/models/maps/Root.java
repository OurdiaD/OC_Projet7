package com.openclassrooms.go4lunch.models.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root {
    @SerializedName("html_attributions")
    @Expose
    public List<Object> html_attributions = null;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("status")
    @Expose
    public String status;

    public List<Object> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<Object> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
