package com.raf.server.user.repo;

import com.raf.server.response.core.Response;
import com.raf.server.user.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class UserRepository {

    private static UserRepository instance = null;

    private static Hashtable<String, User> users = new Hashtable<String, User>();

    private static Integer guestCounter = 0;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    public static User userByUsername(String username) {
        return users.get(username);
    }

    public static User userByUsernameAndPassword(String username, String password) {
        User user = userByUsername(username);

        if (user.password.equals(password))
            return user;
        return null;
    }

    public static User userBySock(Socket sock) {
        Set<String> usernames = users.keySet();
        for (String username : usernames) {
            User user = users.get(username);
            if (user.sock == sock)
                return user;
        }
        return null;
    }

    public static User createUser(Socket sock, String username, String password) {
        User user = new User(sock, username, password);

        users.put(username, user);
        return user;
    }

    public static User createGuestUser(Socket sock){
        return createUser(sock, "Guest#" + ++guestCounter, null);
    }

    public static void createResponse(User user, Response response) {
        user.queueResponse(response);
    }

    public static void createResponse(Response response) {
        Set<String> usernames = users.keySet();
        for (String username : usernames) {
            users.get(username).queueResponse(response);
        }
    }

    public static Integer createNewGuestUser() {

        return users.size();
    }

    public void authenticateUser(User user, String newUsername, String password) {
        users.remove(user.username);
        user.username = newUsername;
        user.password = password;
        user.isAuthenticated = true;

        users.put(newUsername, user);
    }

    public ArrayList<User> usersList() {
        return new ArrayList<User>(users.values());
    }
}
