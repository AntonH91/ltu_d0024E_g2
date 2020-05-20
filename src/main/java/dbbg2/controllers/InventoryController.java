package dbbg2.controllers;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;


import java.net.URL;
import java.sql.*;

public class InventoryController {
    private PreparedStatement insertNewBook;
    private PreparedStatement insertNewFilm;
    private PreparedStatement insertInventoryCopy;
    private Connection connection;
    private static final String URL = "";
    private static final String USERNAME = "";
    private static final String Password = "";

    private InventoryItem subjectToChange;

    public InventoryItem getInventoryItem(){
        return subjectToChange;
    }

    public void setInventoryItem(InventoryItem iv){
        subjectToChange = iv;
    }



    // ADD AN ENTRY TO THE DATABASE

    public int addBook(
            String title, ItemCategory category, boolean isAvailable, String isbn, String author)
    {

            int result = 0;

            try
            {
                connection = DriverManager.getConnection(URL, USERNAME, Password);

                insertNewBook = connection.prepareStatement(
                        "INSERT INTO Book title, category, is_available, isbn, author" + "values (?, ?, ?, ?, ?"
                );

                insertNewBook.setString(1, title);
                insertNewBook.setObject(2, category);
                insertNewBook.setBoolean(3, isAvailable);
                insertNewBook.setString(4, isbn);
                insertNewBook.setString(5, author);

                result = insertNewBook.executeUpdate();

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

                connection = DriverManager.getConnection(URL, USERNAME, Password);

                insertNewFilm = connection.prepareStatement("INSERT INTO Film title, category, is_available, director, age_limit, origin_country"
                        + "VALUES (?, ?, ?, ?, ?, ?)");

                insertNewFilm.setString(1, title);
                insertNewFilm.setObject(2, category);
                insertNewFilm.setBoolean(3, isAvailable);
                insertNewFilm.setString(4, director);
                insertNewFilm.setInt(5, ageLimit);
                insertNewFilm.setString(6, originCountry);

                result = insertNewBook.executeUpdate();

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
            return result;
        }

    public int addInventoryCopy(String barcode, String location, boolean lendable, InventoryItem item) {

        int result = 0;

        try {

            connection = DriverManager.getConnection(URL, USERNAME, Password);

            insertInventoryCopy = connection.prepareStatement("INSERT INTO InventoryCopy barcode, location, lendable, item"
                    +"VALUES (?, ?, ?, ?)");

            insertInventoryCopy.setString(1, barcode);
            insertInventoryCopy.setString(2, location);
            insertInventoryCopy.setBoolean(3, lendable);
            insertInventoryCopy.setObject(4, item);

            result = insertInventoryCopy.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }

    //Close method for ending connection with DB

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

    public void sqlBookUpdate(String inventoryId){
        try{
            final String DATABASE_URL = "";
            Connection connection = DriverManager.getConnection(
                    DATABASE_URL, "user", "Password");

            final String UPDATE_ISAVAILABLE = "SELECT on_loan FROM InventoryCopy WHERE inventory_id = ? AND on_loan = False";
            PreparedStatement statement = connection.prepareStatement(UPDATE_ISAVAILABLE);
            statement.setString(1, inventoryId);
            ResultSet resultSet = statement.executeQuery(UPDATE_ISAVAILABLE);

            if(resultSet.next()){
                final String BOOK_AVAILABLE = "UPDATE InventoryItem SET is_available = true WHERE inventory_id = ?";
                PreparedStatement statement2 = connection.prepareStatement(BOOK_AVAILABLE);
                statement2.setString(1, inventoryId);
                ResultSet resultSet2 = statement.executeQuery(BOOK_AVAILABLE);
            }
            else {
                final String BOOK_UNAVAILABLE = "UPDATE InventoryItem SET is_available = false WHERE inventory_id = ?";
                PreparedStatement statement3 = connection.prepareStatement(BOOK_UNAVAILABLE);
                statement3.setString(1, inventoryId);
                ResultSet resultSet3 = statement.executeQuery(BOOK_UNAVAILABLE);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}