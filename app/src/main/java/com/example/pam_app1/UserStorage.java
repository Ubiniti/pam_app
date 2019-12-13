package com.example.pam_app1;

import com.example.pam_app1.Entity.User;

public class UserStorage {
    private static User user;

    public static User getLoggedInUser() {
        return user;
    }

    public static void setUser(User user) {
        UserStorage.user = user;
    }
}
