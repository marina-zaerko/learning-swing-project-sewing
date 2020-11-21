import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class myClient {
    public Connection connect;

    myClient() {
        //myClient c = new myClient();
        initConnection();
    }

    int initConnection(){
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/sewing?serverTimezone=UTC","root", "1234");
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }



    int execQuery(String q){
        try {
            connect.prepareStatement(q).executeUpdate();
            return 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    ResultSet execSelect(String table){
        ResultSet results = null;
        try {
            results = connect.createStatement().executeQuery("select * from " + table);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return results;
    }

    String getLastIdFromTablePlusOne(String table){
        ResultSet results = execSelect(table);
        String id = null;
        Integer idint = 1;
        try {
            while (results.next()){
                id = results.getString("id");
            }
            idint = idint + Integer.parseInt(id);
            return Integer.toString(idint);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;

    }
}

