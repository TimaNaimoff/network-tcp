package edu.javacourse.network.net.greet;

import edu.javacourse.network.net.Greatable;

public class MorningGreet extends Greatable {

    @Override
    public String buildResponse(String userName) {
        return "Good morning, "+userName;
    }
}
