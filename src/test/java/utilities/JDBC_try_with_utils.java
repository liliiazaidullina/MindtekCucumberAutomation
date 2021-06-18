package utilities;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBC_try_with_utils {

    public static void main(String[] args) throws SQLException {
        JDBCUtils.establishConnection();
        List<Map<String, Object>> myFirstQuery = JDBCUtils.runQuery("select * from employees");
        //We can print list of map using lamda
     //   myFirstQuery.stream().flatMap(m->m.entrySet().stream()).forEach(e-> System.out.println(e.getKey()+":"+e.getValue()));
        System.out.println(myFirstQuery);
        System.out.println(myFirstQuery.get(0).get("FIRST_NAME")); //return first name of the first employee in the system

        JDBCUtils.closeConnection();

        System.out.println(JDBCUtils.runQuery("select * from employees"));
    }
}
