import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {


    private void viewChallenges(Socket socket)throws java.sql.SQLException,
            java.lang.ClassNotFoundException,java.io.IOException {
      PrintWriter  output=new PrintWriter(socket.getOutputStream(),true);
        String sql="SELECT * FROM challenges WHERE is_active=1;";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nationschools","root","");
        Statement statement =connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        if(resultSet.next()){
            while(resultSet.next()){
                String challenge_number=resultSet.getString("title");
                String openingDate=resultSet.getString("start_date");
                String closingDate=resultSet.getString("end_date");
                String duration=resultSet.getString("duration");
                String challenge=(challenge_number+ " " + openingDate + " " +closingDate+ " " +duration);
                output.println(challenge);

            }
            output.println("END");
        }
        else {
            output.println("No available challenges");
        }
    }
}