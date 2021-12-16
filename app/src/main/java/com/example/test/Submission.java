package com.example.test;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("restrooms")
public class Submission extends ParseObject {
    public static final float KEY_RATING = 3;
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_STATUS = "status";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PLACE = "place";

    public int getRating() {
        return getInt(String.valueOf(KEY_RATING));
    }

    public void setRating(float rating) {
        put(String.valueOf(KEY_RATING), rating);
    }

    public String getComment() {
        return getString(KEY_COMMENT);
    }

    public void setComment(String comment) {
        put(KEY_COMMENT, comment);
    }

    public String getStatus() {
        return getString(KEY_STATUS);
    }

    public void setStatus(String status) {
        put(KEY_STATUS, status);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    // check for these
    public ParseObject getObject() {
        return getParseObject(KEY_PLACE);
    }

    public void setPlace(ParseObject place) {
        put(KEY_PLACE, place);
    }


}
