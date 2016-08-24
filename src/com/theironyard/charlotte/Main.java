package com.theironyard.charlotte;


import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {
	// write your code here
        User mike = new User("mike", "1234");
        users.put("mike", mike);

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {

                    Session session = request.session();

                    String name = session.attribute("loginName");

                    User user = users.get(name);

                    HashMap m = new HashMap<>();


                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    }
                    else {
                        m.put("name", user.name);
                        m.put("messageList", user.messageKeeper);
                        return new ModelAndView(m, "message.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("password");
                    User user = users.get(name);
                    if (user != null) {
                        response.redirect("/");

                    }
                    if (user == null) {
                        user = new User(name, password);
                        users.put(name, user);
                    }
                    // if users.get(name) is not null
                    // compare passwords
                    // if passwords are samsies pass go
                    // if passwords are not the same do not pass go do not collect 200 dollars
                    Session session = request.session();
                    session.attribute("loginName", name);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);
                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }
                    user.messageKeeper.add(request.queryParams("message"));
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );


    }
}
