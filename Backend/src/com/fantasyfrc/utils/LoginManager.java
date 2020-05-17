package com.fantasyfrc.utils;

import com.fantasyfrc.user.UserDatabaseManager;
import com.fantasyfrc.utils.PasswordStorage;

public class LoginManager {

    public static boolean authUser(final String username, final String password){
        String databasePass = UserDatabaseManager.getInstance().readUser(username);
        boolean auth = false;
        try {
            auth = PasswordStorage.verifyPassword(password, databasePass);
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException e) {
            e.printStackTrace();
        }
        return auth;
    }
}
