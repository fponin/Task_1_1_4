package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        //Создаем таблицу users
        userService.createUsersTable();

        //Добавляем в таблицу 4 User'a
        userService.saveUser("Rachel", "Green", (byte) 25);
        userService.saveUser("Chandler", "Bing", (byte) 27);
        userService.saveUser("Monica", "Geller", (byte) 26);
        userService.saveUser("Joseph", "Tribbiani", (byte) 28);

        //Получаем содержимое таблицы
        System.out.println(userService.getAllUsers());

        //Очищаем таблицу
        userService.cleanUsersTable();

        //Удаляем таблицу
        userService.dropUsersTable();
    }
}
