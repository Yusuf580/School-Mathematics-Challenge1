import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    String DB_URL;
    String USER;
    String PASS;

    //Method stores records after a participant finishes a challenge
    private void records(String username,int finalscore,int attempt,String status,String title) throws ClassNotFoundException, SQLException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        JSONObject result=new JSONObject();
        result.put("score",finalscore);
        result.put("attempt",attempt);
        result.put("status",status);
        String sql ="UPDATE challenge_records SET"+ " " + title+ " " +"=? WHERE username=?";
        PreparedStatement statement=conn.prepareStatement(sql);

        statement.setObject(1,result.toString());
        statement.setString(2,username);
        statement.executeUpdate();


    }
}



}
