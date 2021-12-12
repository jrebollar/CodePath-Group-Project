package com.example.test;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Submission extends ParseObject {
    public static final float KEY_RATING = 3;
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_STATUS = "status";
    public static final String KEY_USER = "user";

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

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }


}