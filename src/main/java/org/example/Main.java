package org.example;
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Али", "Ибрагимов", (byte) 30);
        userService.saveUser("София", "Петрова", (byte) 25);
        userService.saveUser("Иван", "Сидоров", (byte) 40);
        userService.saveUser("Мария", "Козлова", (byte) 35);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
