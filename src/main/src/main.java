import Controller.DatabaseController;
import database.ConnectionData;
import domain.Duty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by mond on 10.05.2016.
 * Consolenanwendung
 */
public class main {

    public static void main(String[] args) {
        /*Create a databasecontroller to access the data from the DB*/
        DatabaseController controller = new DatabaseController(new ConnectionData());
        boolean compareDates = false;
        boolean startDateOk = false;
        Timestamp start = null;
        boolean endDateOk = false;
        Timestamp end = null;
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));

        /*OUTPUT*/
        System.out.println("DUTY LOADER 3000");
        System.out.println("Enter a date in the format: hh:mm:ss dd.MM.yyyy");

        /*Check for a correct start date until its done*/
        while (!compareDates) {
            System.out.println("Enter an Startdate:");
            while (!startDateOk) {
                try {
                    String input = d.readLine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
                    Date parsedDate = dateFormat.parse(input);
                    start = new java.sql.Timestamp(parsedDate.getTime());
                    System.out.println(start.toString());
                    startDateOk = true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    System.err.println("Wrong Date format. Use this: hh:mm:ss dd.MM.yyyy");
                }
            }
        /*Check for a correct 10end date until its done*/
            System.out.println("Enter an Enddate: ");
            while (!endDateOk) {
                try {
                    String input = d.readLine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
                    Date parsedDate = dateFormat.parse(input);
                    end = new java.sql.Timestamp(parsedDate.getTime());
                    endDateOk = true;
                } catch (IOException e) {

                    e.printStackTrace();
                } catch (ParseException e) {
                    System.err.println("Wrong Date format. Use this: hh:mm:ss dd.MM.yyyy");
                }
            }
            if (start.before(end) || start.equals(end)) {
                compareDates = true;
            }
            endDateOk = false;
            startDateOk = false;
        }
        /*try to get the requested data and write it in the output of the console*/
        Connection connection = null;
        try {
            connection = controller.openConnectionToDatabase();
            System.out.println(controller.getRosterBetween(start.toString(), end.toString(),connection));
            controller.closeConnectionToDatabse(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
