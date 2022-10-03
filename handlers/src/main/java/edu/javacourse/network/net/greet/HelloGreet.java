package edu.javacourse.network.net.greet;

import edu.javacourse.network.net.Greatable;

public class HelloGreet extends Greatable {
    @Override
    public String buildResponse(String userName) {
        return "Hello, "+userName;
    }
}
