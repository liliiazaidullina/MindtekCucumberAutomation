package utilities;

import java.sql.*;
import java.util.*;

public class JDBCUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * This method will establish connection with Oracle SQL database
     */
    public static void establishConnection(){
        try{
            connection = DriverManager.getConnection(
                    Configuration.getProperty("DBURL"),
                    Configuration.getProperty("DBUsername"),
                    Configuration.getProperty("DBPassword")
            );
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.out.println("Could not establish database connection");
            System.out.println(e.getMessage());
        }
    }

    /** This method will return list of map as a result of executed query
     * @param query
     * @return
     * @trows SQLException
     */
    public static List<Map<String, Object>> runQuery(String query) throws SQLException {
        List<Map<String, Object>> table =new ArrayList<>();
        resultSet = statement.executeQuery(query);
        ResultSetMetaData rMetaData = resultSet.getMetaData();
        // looping through rows of that table
        while(resultSet.next()){
            Map<String, Object> row = new HashMap<>();
            // It is looping through each column of current row and stores to map
            for (int i=1; i<=rMetaData.getColumnCount(); i++){
                row.put(rMetaData.getColumnName(i), resultSet.getObject(rMetaData.getColumnName(i)));
            }
            table.add(row);
        }
        return table;
    }
    /**
     * This method will return the number of rows in provided query
     * @param query
     * @return
     * @throws SQLException
     */
    public static int countRows(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        resultSet.last();
        return resultSet.getRow();
    }

    /**
     * This method will close connection to database
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if(connection!=null){
            connection.close();
        }
        if(statement!=null){
            statement.close();
        }
        if(resultSet!=null){
            resultSet.close();
        }
    }




}
