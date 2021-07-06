package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    //Initializes Parse SKD as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse models
        ParseObject.registerSubclass(Post.class);

        //Register your parse models
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yqkNPbWO3kCP6M6APNLcMZEwwFJryFqMM5yxCEXm")
                .clientKey("c9TlOdnjwi8cifIYtCxliH23hDcSqJhWYRr17PH2")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
