package com.openclassrooms.go4lunch.models.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlusCode {
    @SerializedName("compound_code")
    @Expose
    public String compound_code;
    @SerializedName("global_code")
    @Expose
    public String global_code;

    public String getCompound_code() {
        return compound_code;
    }

    public void setCompound_code(String compound_code) {
        this.compound_code = compound_code;
    }

    public String getGlobal_code() {
        return global_code;
    }

    public void setGlobal_code(String global_code) {
        this.global_code = global_code;
    }
}
