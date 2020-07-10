package com.fantasyfrc.utils;

import com.fantasyfrc.draft.Draft;
import com.fantasyfrc.user.UserAccount;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class AppUtils {

    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> id_uri_map = new HashMap<>();
    private static final Map<String, Integer> uri_id_map = new HashMap<>();

    // Store user info in Session.
    public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
        // On the JSP can access via ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
        session.setAttribute("activeDraft", new Draft("testDraft"));
//        session.setAttribute("activeDraft", new Draft("testDraft", new String[]{
//                "Jakob", "Jon", "Jill", "Jack", "Bill", "Steve", "Jane", "Jim"
//        }));
    }

    // Get the user information stored in the session.
    public static UserAccount getLoginedUser(HttpSession session) {
        return (UserAccount) session.getAttribute("loginedUser");
    }

    public static int storeRedirectAfterLoginUrl(HttpSession session, String requestUri) {
        Integer id = uri_id_map.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            uri_id_map.put(requestUri, id);
            id_uri_map.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(HttpSession session, int redirectId) {
        return id_uri_map.get(redirectId);
    }

}
