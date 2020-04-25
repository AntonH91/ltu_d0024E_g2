package dbbg2.controllers;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;

import java.sql.*;

public class InventoryController {
    private PreparedStatement insertNewBook;
    private PreparedStatement insertNewFilm;
    private PreparedStatement insertInventoryCopy;
    private Connection connection;



    // ADD AN ENTRY TO THE DATABASE

    public int addBook(
            String title, ItemCategory category, boolean isAvailable, String isbn, String author)
    {

            int result = 0;

            try
            {
                insertNewBook.setString(1, title);
                insertNewBook.setObject(2, category);
                insertNewBook.setBoolean(3, isAvailable);
                insertNewBook.setString(4, isbn);
                insertNewBook.setString(5, author);
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }

            return result;
    }

    public int addFilm(String title, ItemCategory category, boolean isAvailable, String director, int ageLimit, String originCountry) {

            int result = 0;

            try {
                insertNewFilm.setString(1, title);
                insertNewFilm.setObject(2, category);
                insertNewFilm.setBoolean(3, isAvailable);
                insertNewFilm.setString(4, director);
                insertNewFilm.setInt(5, ageLimit);
                insertNewFilm.setString(6, originCountry);

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
            return result;
        }

    public int addInventoryCopy(String barcode, String location, boolean lendable, InventoryItem item) {

        int result = 0;

        try {
            insertInventoryCopy.setString(1, barcode);
            insertInventoryCopy.setString(2, location);
            insertInventoryCopy.setBoolean(3, lendable);
            insertInventoryCopy.setObject(4, item);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }

    public void close(){
        try {
            connection.close();
        }
        catch(SQLException sqlException)
        { sqlException.printStackTrace();

        }
    }

    // DISPLAY CONTENTS OF TABLES

    public void sqlSelectBook() {
        final String SELECT_QUERY_BOOK = "Select* FROM Book";
        // TODO add database URL
        final String DATABASE_URL = "";

        try(
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, "user", "Password"
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY_BOOK)
                )
        {   ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.printf("Book information:%n%n");

        for (int i = 1; i <= numberOfColumns; i++)
            System.out.printf("%-8s\t", metaData.getColumnName(i));
        System.out.println();
        while(resultSet.next())
        {
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", resultSet.getObject(i));
        }

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }



    public void sqlSelectFilm() {
        final String SELECT_QUERY_FILM = "Select* FROM Film";
        // TODO add database URL
        final String DATABASE_URL = "";

        try(
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, "user", "Password"
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY_FILM)
        )
        {   ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.printf("Book information:%n%n");

            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();
            while(resultSet.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
            }

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

    public void sqlSelectCopy() {
        final String SELECT_QUERY_COPY = "Select* FROM InventoryCopy";
        // TODO add database URL
        final String DATABASE_URL = "";

        try(
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, "user", "Password"
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY_COPY)
        )
        {   ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.printf("Book information:%n%n");

            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();
            while(resultSet.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
            }

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

    //TODO fix how to update is available for books
    public void sqlBookUpdate() {
        final String UPDATE_ISAVAILABLE = "UPDATE Book" + "SET isAvailable = ?" + "WHERE isbn = ?";
        // TODO add database URL
        final String DATABASE_URL = "";

        try(
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, "user", "Password"
                );
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(UPDATE_ISAVAILABLE)
        )
        {

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

}
