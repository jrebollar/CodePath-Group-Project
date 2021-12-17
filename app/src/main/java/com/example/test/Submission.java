package com.example.test;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("restrooms")
public class Submission extends ParseObject {
    public static final String KEY_RATING = "Rating";
    public static final String KEY_COMMENT = "Comment";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_IMAGE = "image"; // relic from the past
    public static final String KEY_PLACE = "PlacePointer";
    public static final String KEY_USER = "user"; // relic from the past

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

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
