package com.example.ium_gb;

import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    private static Map<String, User> userMap = new HashMap<>();

    public static void registerUser(User user) {
        userMap.put(user.getEmail(), user);
    }

    public static boolean authenticate(String email, String password) {
        User user = userMap.get(email);
        return user != null && user.getPassword().equals(password);
    }

    public static User getUserByEmail(String email) {
        return userMap.get(email);
    }
}
