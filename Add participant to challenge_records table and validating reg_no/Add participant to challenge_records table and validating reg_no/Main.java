import java.sql.*;

public class Main {

    //Method checks to see if applicants' Registration number is among registered schools
    private boolean validateReg(String reg_no) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM schools WHERE Registration=? ";
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nationschools","root","");

        PreparedStatement statement =connection.prepareStatement(sql);
        statement.setString(1,reg_no);

        ResultSet resultSet=statement.executeQuery();
        if(resultSet.next()){
            return true;

        }
         else return false;}

    //method adds a participant in to a challenge_records table to track their scores
    private void challenge_records(String user,String reg_no) throws ClassNotFoundException, SQLException {
        String sql ="INSERT INTO challenge_records (username,registrationNumber) VALUES(?,?)";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nationschools","root","");

        PreparedStatement statement =connection.prepareStatement(sql);
        statement.setString(1,user);
        statement.setString(1,reg_no);
   statement.executeUpdate();

    }




}
