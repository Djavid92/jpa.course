package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Али", "Ибрагимов", (byte) 30);
        userService.saveUser("София", "Петрова", (byte) 25);
        userService.saveUser("Иван", "Сидоров", (byte) 40);
        userService.saveUser("Мария", "Козлова", (byte) 35);

        System.out.println(userService.getAllUsers());
      userService.cleanUsersTable();
      userService.dropUsersTable();
    }
}
