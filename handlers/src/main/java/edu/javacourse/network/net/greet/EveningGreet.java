package edu.javacourse.network.net.greet;

import edu.javacourse.network.net.Greatable;

public class EveningGreet extends Greatable {

    @Override
    public String buildResponse(String userName) {
        return "Good evening, "+userName;
    }
}
