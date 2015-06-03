package com.raf.server.user.repo;

import com.raf.server.response.core.Response;
import com.raf.server.user.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class UserRepository {

    private static UserRepository instance = null;

    private static ArrayList<User> users = new ArrayList<User>();

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
        for(User user : users) {
            if(user.username.equals(username))
                return user;
        }
        return null;
    }

    public static User userByUsernameAndPassword(String username, String password) {
        User user = userByUsername(username);

        if (user.password.equals(password))
            return user;
        return null;
    }

    public static User userBySock(Socket sock) {
        for(User user : users) {
            if(user.sock == sock)
                return user;
        }
        return null;
    }

    public static User createUser(Socket sock, String username, String password) {
        User user = new User(sock, username, password);

        users.add(user);
        return user;
    }

    public static User createGuestUser(Socket sock){
        return createUser(sock, "Guest#" + ++guestCounter, null);
    }

    public static void createResponse(User user, Response response) {
        user.queueResponse(response);
    }

    public static void createResponse(Response response) {
        for(User user : users) {
            user.queueResponse(response);
        }
    }

    public static Integer createNewGuestUser() {
        return users.size();
    }

    public void authenticateUser(User user, String username, String password) {
        for(User listUser : users) {
            if(listUser == user) {
                listUser.username = user.username = username;
                listUser.password = user.password = password;
                listUser.isAuthenticated = user.isAuthenticated = true;
            }
        }
    }

    public synchronized void loginUser(User user, String username, String password) {
        for(User listUser: users) {
            if(listUser.username.equals(username))
                users.remove(listUser);
        }
        authenticateUser(user, username, password);
    }

    public ArrayList<User> usersList() {
        return users;
    }
}
