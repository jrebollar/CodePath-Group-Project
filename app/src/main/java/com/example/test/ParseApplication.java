package com.example.test;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Submission.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("D3a51A7tjMOdS3vr7AlilT2SN8cGnA6tjYeZkjoB")
                .clientKey("4b4bNaDXBAYgxkFJz0cDioo8GzGTkGbwMOqCrovQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
