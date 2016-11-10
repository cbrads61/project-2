package com.colinbradley.fwc.DatabaseAndData;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colinbradley on 11/5/16.
 */

public class FWCGear {

    private int id;
    private String name;
    private String description;
    private String type;
    private int price;
    private int imagePath;

    public FWCGear(int id, String name, int imagePath, String description, String type, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return imagePath;
    }

    public void setImage(int imagePath) {
        this.imagePath = imagePath;
    }
}
