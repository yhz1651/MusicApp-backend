package tool;

import java.sql.*;

public class DATA_BASE {
    private  Connection conn;
    public  String select( String sql) throws SQLException {
        Statement statement = conn.createStatement();
        String st = null;
        ResultSet resultSet = null;
        resultSet = statement.executeQuery(sql);
//        st=resultSet.getString(0);
        // Print results from select statement
        while (resultSet.next()) {
            st=resultSet.getString(1);
            System.out.println(resultSet.getString(1));
        }
        return st;
    }

    public  void exec(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        if(count == 1)System.out.println("插入成功");
        else System.out.println("插入失败");
    }


    public DATA_BASE() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=MusicApp","sa","1234");
            System.out.println("数据库连接成功");
        }
        catch(Exception e) {
            System.out.println("数据库连接失败\n" + e.toString());
        }
    }
}
