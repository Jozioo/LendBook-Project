package service;

import model.Member;
import model.User;
import java.util.*;

public class AuthenticationService {

    private final List<User> users = new ArrayList<>();

    public AuthenticationService() {
        users.add(new Member("M1", "leo", "1234"));
        users.add(new Member("M2", "jozio", "1111"));
        users.add(new Member("M3", "shane", "2233"));
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
