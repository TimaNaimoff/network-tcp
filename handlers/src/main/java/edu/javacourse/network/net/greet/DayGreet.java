package edu.javacourse.network.net.greet;

import edu.javacourse.network.net.Greatable;

public class DayGreet extends Greatable {

    @Override
    public String buildResponse(String userName) {
        return "Good day, "+userName;
    }
}
