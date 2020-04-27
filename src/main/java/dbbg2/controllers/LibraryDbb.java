package dbbg2.controllers;

import dbbg2.data.users.Employee;
import dbbg2.data.users.User;

import java.sql.SQLException;

/**
 * Program entrypoint
 */
public class LibraryDbb {
    public static void main(String[] args) {
        User a = new Employee();

        a.setFirstName("Anton");
        a.setLastName("Högelin");
        a.setStreetAddress("Sjösavägen 13");

        try {
            a.saveUser();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
