package Controller;

import database.ConnectionData;
import domain.Duty;
import domain.Member;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mond on 10.05.2016.
 * The Database Controller is used to read data from the database and format this.
 */
public class DatabaseController {

    private ConnectionData connectionData;

    public DatabaseController(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    /**
     * This Methode is used to connect to the Database
     */
    public Connection openConnectionToDatabase() {
        Connection connection = null;
        try {
            Class.forName(connectionData.getDriver()).newInstance();
            connection = DriverManager.getConnection(connectionData.getUrl(), connectionData.getUsername(), connectionData.getPassword());
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnectionToDatabse(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * get the playing members between a specific time slot
     *
     * @param begin requested begin date
     * @param end   requested end date
     * @return a formatted String with all requested Data in it
     */
    public String getRosterBetween(String begin, String end, Connection connection) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("------------------------------------\n");
        builder.append("The result of your request is:\n");
        for (Duty duty : loadDutiesBetween(begin, end, connection)) {
            builder.append("DienstID: " + duty.getId() + "\n");
            String place = duty.getPlace();
            if (place == null) {
                place = "n.a";
            }
            builder.append("Ort: " + place + "\n");
            Timestamp timestamp = duty.getBegin();
            builder.append("Beginn: " + new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(timestamp) + "\n");
            builder.append("------------------------------------\n");
            builder.append("Members of this duty:\n");
            for (Member member : getMemberForDuty(duty, connection)) {
                builder.append(member.toString() + "\n");
            }
            builder.append("------------------------------------\n");
        }
        builder.append("----------------END-----------------\n");
        builder.append("------------------------------------\n");
        return builder.toString();
    }

    /**
     * load all duties in a defined time slot from the database
     *
     * @param begin requested begin date
     * @param end   requested end date
     * @return a list of all Duties between this two dates
     */
    private List<Duty> loadDutiesBetween(String begin, String end, Connection connection) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        LinkedList<Duty> duties = new LinkedList<Duty>();

        Statement statement = connection.createStatement();

        /*Select all "dienst" in a specific time slot*/
        ResultSet resultSet = statement.executeQuery("SELECT * FROM dienst WHERE Beginn BETWEEN '" + begin.toString() + "' AND '" + end.toString() + "' ;");
        /*Iterate over all results and convert them into usable objects*/
        while (resultSet.next()) {
            Duty duty = new Duty();
            duty.setId(resultSet.getString("DienstID"));
            duty.setPlace(resultSet.getString("Ort"));
            duty.setBegin(resultSet.getTimestamp("Beginn"));
            duty.setDayTime(resultSet.getString("Tageszeit"));
            duty.setDuration(Double.valueOf(resultSet.getString("Dauer")));
            duty.setName(resultSet.getString("Name"));
            duty.setRemark(resultSet.getString("Bemerkung"));
            duty.setDutyType(resultSet.getString("Diensttyp"));
            duty.setBesId((resultSet.getString("BesID")));
            duties.add(duty);
        }
        /*Close statement and resultSet to keep it clean*/
        statement.close();
        resultSet.close();
        return duties;
    }

    /**
     * load all Members for a special duty
     *
     * @param duty the requested duty
     * @return all members for one duty
     */
    private List<Member> getMemberForDuty(Duty duty, Connection connection) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        LinkedList<Member> members = new LinkedList<Member>();
        Statement statement = connection.createStatement();

        /*Load all members which play in a "einsatz" with a specific "DienstID*/
        ResultSet resultSet = statement.executeQuery("SELECT * FROM einsatz INNER JOIN orchestermitglied ON einsatz.OMitglID = orchestermitglied.OMitglID WHERE DienstID = " + duty.getId() + ";");
        /*Iterate over all results and convert them into usable objects*/
        while (resultSet.next()) {
            Member member = new Member();
            String omitglID = resultSet.getString("OMitglID");
            member.setId(omitglID);
            member.setFname(resultSet.getString("Vorname"));
            member.setLname(resultSet.getString("Name"));
            member.setReplacement(resultSet.getBoolean("AlsErsatz"));
            members.add(member);
        }

        /*Close statement and resultSet to keep it clean*/
        statement.close();
        resultSet.close();
        return members;
    }


}
