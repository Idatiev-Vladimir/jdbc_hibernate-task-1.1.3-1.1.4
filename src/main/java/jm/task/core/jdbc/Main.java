package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Vladimir", "Idatiev", (byte) 21);
        userService.saveUser("Zaur", "Tregulov", (byte) 35);
        userService.saveUser("Nail", "Alishev", (byte) 29);
        userService.saveUser("Denis", "Dmitrienko", (byte) 28);

        List<User> allUsers = userService.getAllUsers();

        userService.cleanUsersTable();

//        userService.dropUsersTable();

    }
}
