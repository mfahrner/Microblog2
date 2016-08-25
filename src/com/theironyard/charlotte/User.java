package com.theironyard.charlotte;

import java.util.ArrayList;

/**
 * Created by mfahrner on 8/24/16.
 */
public class User {
    String name;
    String password;
    ArrayList <String> messageKeeper = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
