import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client extends AttemptChallenge {
    private String user;
    private String email;
    public static void main(String [] args) throws IOException, InterruptedException {
        Socket socket=new Socket("localhost",8000);
        Client client =new Client();


        client.LogIn_Menu(socket);

    }

    //Method clears console after a user logs out
    public static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
    }

    //first screen that is displayed
    void LogIn_Menu(Socket socket) throws IOException, InterruptedException {

        System.out.println("Log In as:");
        System.out.println("1.School Representative");
        System.out.println("2.Student");
        Scanner scanner=new Scanner(System.in);
       int option= scanner.nextInt();
        if(1==option){
           this.LogIn(socket);
        } else if (2==option) {
            this.handleinput(socket);
        }
        else{
            LogIn_Menu(socket);
        }

    }


    //METHOD 1..displays the main menu and recieves a command
    public static String Register ()throws IOException{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Run the command --Register-- to Register and Enter your details in the order below");
        System.out.println("Register username firstname lastname emailAddress date_of_birth school_registration_number image_file.png ");
        System.out.println("Run the command --ViewChallenges-- to view available challenges..");
        System.out.println("Enter command:");
        String input=scanner.nextLine();
        return input;
    }

    //method 3...method sends output and receives input from server for a participant
    public static String connectServer(String  details,Socket socket)throws IOException {

        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        out.println(details);
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String string=reader.readLine();
        return string;

    }

    //method 2...Method that handles commands and performs given actions
    private void handleinput(Socket socket) throws IOException, InterruptedException {
        String input=Register();
        String [] details=input.split(" ");
        if("Register".equals(details[0])){
            if(details.length==8){
                boolean bool=checkDateFormat(details[5]);
                if(bool){
                    boolean bool2=validateImagePath(details[7]);
                    if(bool2){
                        String in=connectServer(input,socket);
                        System.out.println(in);
                        Scanner scanner=new Scanner(System.in);
                        String out=scanner.nextLine();
                        if("Exit".equals(out)){
                            handleinput(socket);}
                        else{
                            System.out.println("Invalid image path!");
                            handleinput(socket);

                        }
                    }

                } else  {
                    System.out.println("Invalid date format !\nUse yyyy-MM-dd format");
                    handleinput(socket);
                }

            }

            else{
                System.out.println("Missing details.Please make sure there are no missing fields!");
                Scanner scanner=new Scanner(System.in);
                String out=scanner.nextLine();
                if("Exit".equals(out)){
                    handleinput(socket);
                }

            }
        }
        else if("viewChallenges".equals(details[0]) && details.length<2){

            Scanner key=new Scanner(System.in);
            System.out.println("Please log in first");
            System.out.print("Enter 1 if already registered\n" +
                "Enter 2 to setup your password if new user:");
            String in=key.nextLine();
            if("1".equals(in)){
                System.out.println("Username:");
                String username=key.nextLine();
                System.out.println("Password:");
                String passWord=key.nextLine();
                String user=username+ " " +passWord;
                String out= connectServer(user,socket);
                if("true".equals(out)){
                    String view="viewChallenges";
                    this.user=username;
                    try{ PrintWriter send=new PrintWriter(socket.getOutputStream(),true);
                        send.println(view);}
                    catch (java.io.IOException ioException){
                        System.out.println("Error in Viewing Challenges");
                    }
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String row;
                    System.out.println("Challenge_No"+ "    " +"Opening date"+ "      "+ "  "+ "    " + "Closing date"+  "       " + "    " + "duration(minutes)");
                    while (!(row=bufferedReader.readLine()).equals("END")){
                        System.out.println(row);}

                    Scanner keyboard=new Scanner(System.in);
                    String choice=keyboard.nextLine();
                    String [] split=choice.split(" ");
                    if("attemptChallenge".equals(split[0])){

                        try{ this.handleChallenge(split[1],this.user);}
                        catch (java.lang.ClassNotFoundException e){
                            e.printStackTrace();
                        }}
                    else{
                        handleinput(socket);
                    }


                }
                else {
                    System.out.println("Invalid username or password");
                    Scanner scanner=new Scanner(System.in);
                    String ou=scanner.nextLine();
                    if("Exit".equals(ou)){
                        handleinput(socket);
                    }

                }

            }
            else if("2".equals(in)){
                System.out.println("Username:");
                String username=key.nextLine();
                System.out.println("Password to use:");
                String passWord=key.nextLine();
                String user="New"+ " " +username+ " " +passWord;
                String out= connectServer(user,socket);
                System.out.println(out);
                if("true".equals(out)){
                    String view="viewChallenges";
                    this.user=username;
                    try{ PrintWriter send=new PrintWriter(socket.getOutputStream(),true);
                        send.println(view);}
                    catch (java.io.IOException ioException){
                        System.out.println("Error in Viewing Challenges");
                    }
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String row;

                    while (!(row=bufferedReader.readLine()).equals("END")){
                        System.out.println(row);}

                    Scanner keyboard=new Scanner(System.in);
                    String choice=keyboard.nextLine();
                    String [] split=choice.split(" ");
                    if("attemptChallenge".equals(split[0])){

                        try{ this.handleChallenge(split[1],this.user);}
                        catch (java.lang.ClassNotFoundException e){
                            e.printStackTrace();
                        }}
                    else{
                        handleinput(socket);
                    }

                }
                else {

                    System.out.println("Operation not successful!");
                    Scanner scanner=new Scanner(System.in);
                    String ou=scanner.nextLine();
                    if("Exit".equals(ou)){
                        handleinput(socket);
                    }
                    else {
                        handleinput(socket);
                    }
                }
            }

        } else if ("Exit".equals(details[0])) {
           clearScreen();
            LogIn_Menu(socket);

        } else {
            System.out.println("Unknown command!");
            Scanner scanner=new Scanner(System.in);
            String ou=scanner.nextLine();
            if("Exit".equals(ou)){
                handleinput(socket);
            }
            else {
                handleinput(socket);
            }

        }
    }

    //Method verifies that birth date is in acceptable Mysql format
    private boolean checkDateFormat(String date){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try{
            Date parsedDate=sdf.parse(date);
            return true;
        }catch(ParseException e){
            return false;
        }

    }

    //Method validates image path
    private boolean validateImagePath(String path){
        File file =new File(path);
        return file.exists() && file.isFile();

    }

    //Methods for Representative
//Method to enable Representative to login
    void LogIn(Socket socket) throws java.io.IOException, InterruptedException {
        String verify;
        //String email;
        System.out.println("------Enter Login details------");
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter emailAdress:");
        this.email=scanner.nextLine();
        if("Exit".equals(this.email)){

                clearScreen();
                LogIn_Menu(socket);
                    }
        else{
        System.out.println();
        System.out.print ("Enter password:");
        String password=scanner.nextLine();
        verify=verifyLogIn(socket,email,password);
        if("true".equals(verify))
        {
            System.out.println("You are logged in!");
            menu(socket);

             }
        else
            System.out.println(
                "Invalid login details");

        LogIn(socket);}


    }

    //This method sends login details to the server to verify that details passed to login method exist in the database
    public static String verifyLogIn(Socket socket,String email,String password)throws java.io.IOException{
        String output="Login" + " "+ email+ " " +password;
        PrintWriter input=new PrintWriter(socket.getOutputStream(),true);
        input.println(output);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String read=bufferedReader.readLine();
        if("true".equals(read)){
            return "true";}
        else {return "false";}
    }

    //Method requests server for  available applicants' details .
    public static void viewApplicants(String email,Socket socket)throws java.io.IOException{
        try{ PrintWriter input=new PrintWriter(socket.getOutputStream(),true);
            input.println("viewApplicants"+ " " +email);}
        catch (java.io.IOException ioException){
            System.out.println("Error in Viewing Applicants details!");
        }
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String row;

        while (!(row=bufferedReader.readLine()).equals("END")){
            System.out.println(row);}

    }

    //Method to confirm Applicants
    private void ConfirmApplicant(Socket socket,String in){
        String[]output=in.split(" ");
        String confirm=output[1];
        String username=output[2];
        if("yes".equals(confirm)){
            try{ PrintWriter input=new PrintWriter(socket.getOutputStream(),true);
                input.println("confirm"+ " "+ "yes"+ " " +username );
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String out= reader.readLine();
                System.out.println(out);
            }
            catch (java.io.IOException e){
                e.printStackTrace();
            }}
        else {
            try{ PrintWriter input=new PrintWriter(socket.getOutputStream(),true);
                input.println("confirm"+ " "+ "no"+ " " +username );
                BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String out= reader.readLine();
                System.out.println(out);
            }
            catch (java.io.IOException e){
                e.printStackTrace();

            }


        }}

   //Method displays a menu for representatives
    private void menu(Socket socket) throws java.io.IOException, InterruptedException {
        System.out.println("----Commands-----\n ---viewApplicants--\n---confirmApplicant yes/no username---");
        Scanner scanner=new Scanner(System.in);
        String in =scanner.nextLine();
        String[]output=in.split(" ");
        if("viewApplicants".equals(output[0])&&output.length==1){

            viewApplicants(this.email,socket);
            String command=scanner.nextLine();
            if("Exit".equals(command)){
                menu( socket);
            }
                else{menu(socket);

            }
        }
        else if("confirm".equals(output[0])&&output.length==3){

            ConfirmApplicant(socket,in);
            String command=scanner.nextLine();
            if("Exit".equals(command)){
                menu( socket);
            }}



        else if ("Exit".equals(output[0])) {
           clearScreen();
            LogIn_Menu(socket);

        } else{
            System.out.println("Unknown command");
            System.out.println("Enter --- Exit");
            String command=scanner.nextLine();
            if("Exit".equals(command)){
                clearScreen();
                LogIn_Menu(socket);
            }
            else {
                menu(socket);
            }

        }

    }



}

