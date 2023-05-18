package com.example.myrecyclerviewexample.model;

import java.io.Serializable;


public class Imagen implements Serializable {
    private String image;
    private String encoding;

    public Imagen(String image, String encoding) {
        this.image = image;
        this.encoding = encoding;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
