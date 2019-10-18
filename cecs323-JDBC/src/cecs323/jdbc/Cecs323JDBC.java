package cecs323.jdbc;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author:  Jessica Wei
 * Created: Oct 9, 2019
 * Due: Oct 21, 2019
 */
public class Cecs323JDBC {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    // Required entries to access database 
    static final String cDB = "clientdriver";
    static final String cUSER = "jessicawei";
    static final String cPASS = "password";
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormatWriter ="%-25s%-25s%-20s%-20s\n";
    static final String displayFormatPublisher ="%-25s%-25s%-25s%-25s\n";
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    //            + "testdb;user=";
    
    public static void main(String[] args) {
        login();
        Connection conn = connectDB();
        Scanner menuIn = new Scanner(System.in);
        boolean quit = false;
        int mOption = 0;
        String sql = "";
        
        ArrayList<String> writers = new ArrayList<String>();
        ArrayList<String> publishers = new ArrayList<String>();
        ArrayList<String> books = new ArrayList<String>();
        
        PreparedStatement stmt;
        
        /*PreparedStatement listNames;
        try {
            listNames = conn.prepareStatement("SELECT = ? FROM = ?");
        } catch (SQLException se) {
            se.printStackTrace();
        }*/
                
        // Begin Menu Loop
        while(!quit){
            System.out.println();
            printMenu();
            mOption = getIntBetween(menuIn, 1, 10, "Menu Selection");
            System.out.println();
            switch(mOption){
                case 1: 
                    // List all writing groups NAME ONLY
                    writers = listNames(conn, true, "groupName", "WritingGroups");
                    System.out.println("\nGroup Name");
                    for(int i = 0; i < writers.size(); i++){
                        System.out.println(writers.get(i));
                    }
                    mOption = 1;
                    break;
                case 2:
                    // List all the data for ONE group specified by the user
                    writers = listNames(conn, true, "groupName", "WritingGroups");
                    for(int i = 0; i < writers.size(); i++){
                        System.out.print((i+1)+". ");
                        System.out.println(writers.get(i));
                    }
                    System.out.println();
                    mOption = getIntBetween(menuIn, 1, writers.size(), "Select Writing Group") - 1;
                    try{
                        sql = "SELECT groupname, headwriter, yearformed, subject FROM WritingGroups "
                                + "WHERE groupname = '" + writers.get(mOption) + "'";
                        stmt = conn.prepareStatement(sql);
                        
                        System.out.println();
                        ResultSet rs = executeQuery(conn, stmt, true);
                        System.out.printf(displayFormatWriter, "\nGroup Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) {
                            //Retrieve by column name
                            String gname = rs.getString("groupname");
                            String hwriter = rs.getString("headwriter");
                            String yformed = rs.getString("yearformed");
                            String subject = rs.getString("subject");
                            System.out.printf(displayFormatWriter, dispNull(gname), dispNull(hwriter), dispNull(yformed), dispNull(subject));
                        }
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 2;
                    break;
                case 3:
                    // List all publishers NAME ONLY
                    publishers = listNames(conn, true, "publishername","publishers");
                    System.out.println("\nPublisher Name");
                    for(int i = 0; i < publishers.size(); i++){
                        System.out.println(publishers.get(i));
                    }
                    mOption = 3;
                    break;
                case 4:
                    // List all the data for ONE publisher specified by the user
                    publishers = listNames(conn, true, "publishername","publishers");
                    for(int i = 0; i < publishers.size(); i++){
                        System.out.print((i+1)+". ");
                        System.out.println(publishers.get(i));
                    }
                    System.out.println();
                    mOption = getIntBetween(menuIn, 1, publishers.size(), "Select Publisher") - 1;
                    try{
                        sql = "SELECT publishername, publisheraddress, publisherphone, publisheremail FROM Publishers "
                                + "WHERE publishername = '" + publishers.get(mOption) + "'";
                        stmt = conn.prepareStatement(sql);
                        System.out.println();
                        ResultSet rs = executeQuery(conn, stmt,true);
                        System.out.printf(displayFormatPublisher, "\nPublisher Name", "Address", "Phone", "Email");
                        while (rs.next()) {
                            //Retrieve by column name
                            String pname = rs.getString("publishername");
                            String paddress = rs.getString("publisheraddress");
                            String pphone = rs.getString("publisherphone");
                            String pemail = rs.getString("publisheremail");
                            System.out.printf(displayFormatPublisher, dispNull(pname), dispNull(paddress), dispNull(pphone), dispNull(pemail));
                        }
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 4;
                    break;
                case 5:
                    // List all book titles NAME ONLY
                    /**try{
                        sql = "SELECT bookTitle FROM Books";
                        Statement stmt = conn.createStatement();
                        ResultSet rs = executeQuery(conn, sql, true);
                        
                        System.out.println("\nBook Title");
                        while (rs.next()) {
                            //Retrieve by column name
                            String bname = rs.getString("bookTitle");
                            //Display values
                            System.out.println(dispNull(bname));
                        }
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }**/
                    books = listNames(conn, true, "booktitle", "Books");
                    mOption = 5;
                    break;
                case 6:
                    // List all the data for ONE book specified by the user INCLUDING Writing Groups and Publishers ALL DATA
                    mOption = 6;
                    break;
                case 7:
                    // Insert a new book
                    mOption = 7;
                    break;
                case 8:
                    // Insert a new publisher and update all books published by one publisher
                    // to be published by the new publisher. Leave the old publisher alone, just modify
                    // the books that they have published. Assume that the new publisher has bought out
                    // the old one, so now any books published by the old publisher are publisehd by the new one
                    mOption = 8;
                    break;
                case 9:
                    // Remove a book specified by a user 
                    mOption = 9;
                    break;
                default:
                    quit = true;
                    break;
            }
        }
        

        disconnectDB(conn);
        System.out.println("Goodbye!");
    }//end main
    
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }//end dispNull
    
/**
 * Prompts for the database login credentials and loops until it's valid
 */
    public static void login(){
        //Prompt the user for the database name, and the credentials.
        boolean loggedIn = false;
        Scanner in = new Scanner(System.in);
        // While the credentials are invalid, loop until valid
        while(!loggedIn){
            System.out.print("Name of the database (not the user account): ");
            DBNAME = in.nextLine();
            System.out.print("Database user name: ");
            USER = in.nextLine();
            // Password is CASE SENSITIVE
            System.out.print("Database password: ");
            PASS = in.nextLine();
            if(DBNAME.toLowerCase().equals(cDB) && USER.toLowerCase().equals(cUSER) && PASS.equals(cPASS))
                loggedIn = true;
            else
                System.out.print("Cannot connect to ClientDriver Database. Please try again.\n\n");
        }
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
    }//end login
    
    public static Connection connectDB(){
        Connection conn = null; //initialize the connection
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void disconnectDB(Connection conn){
        try {
            //STEP 6: Clean-up environment
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    
    public static void cleanup(Statement stmt, ResultSet rs){
        try {
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do; end finally try
        }//end try
    }
    
    public static ResultSet executeQuery(Connection conn, PreparedStatement psmt, boolean showConnecting){
        ResultSet rs = null;
         //STEP 4: Execute a query 
        try{
            if(showConnecting){
                System.out.println("Creating statement...");
            }
            rs = psmt.executeQuery();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return rs;
    }
    
    public static void printMenu(){
        System.out.print("1. List all writing groups\n" +
                            "2. List all the data for one writing group\n"+
                            "3. List all publishers\n"+
                            "4. List all the data for one publisher\n"+
                            "5. List all books\n"+
                            "6. List all the data for one book\n"+
                            "7. Insert new book\n"+
                            "8. Insert new publisher\n"+
                            "9. Remove a book\n"+
                            "10. Quit\n\n");
    }
    
    public static int getIntBetween(Scanner mIn, int min, int max, String prompt){
        int mOption = -1;
        boolean valid = false;
        while(!valid){
            System.out.print(prompt + ": ");
            if(mIn.hasNextInt()){
                mOption = mIn.nextInt();
                if(mOption > (min-1) && mOption < (max+1)){
                    valid = true;
                    return mOption;
                }
                else{
                    System.out.println("Input out of range. Please choose a number between " + min + " and " + max + ".");
                }
                    
            }else{
                System.out.println("Invalid Input. Please enter an integer.");
                mIn.next();
            }
        }
        return mOption;
    }
    
    public static void copyList(ArrayList<String> o, ArrayList<String> c){
        for(int i = 0; i < o.size(); i++){
            c.set(i, o.get(i));
        }
    }
    
    public static ArrayList<String> listNames(Connection conn, boolean showConnecting, String name, String table){
        PreparedStatement psmt;
        ArrayList<String> groups = new ArrayList<String>();
        try{
            String sql = "SELECT " +name + " FROM " + table;
            psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
                    //executeQuery(conn, stmt, sql, showConnecting);
            while (rs.next()) {
                //Retrieve by column name
                String listedname = rs.getString(name);
                //Display values
                //System.out.println(dispNull(gname));
                groups.add(dispNull(listedname));
            }
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return groups;
    }
    
    /**public static ArrayList<String> listPublishers(Connection conn, boolean showConnecting){
        ArrayList<String> publishers = new ArrayList<String>();
        try{
            String sql = "SELECT publishername FROM Publishers";
            Statement stmt = conn.createStatement();
            ResultSet rs = executeQuery(conn, stmt, showConnecting);
            while (rs.next()) {
                //Retrieve by column name
                String pname = rs.getString("publishername");
                //Display values
                //System.out.println(dispNull(pname));
                publishers.add(dispNull(pname));
            }
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return publishers;
    }**/
}


                    /*try {
                        String sql = "SELECT groupname, headwriter, yearformed, subject FROM WritingGroups"; 
                        Statement stmt = conn.createStatement(); //initialize the statement that we're using
                        ResultSet rs = executeQuery(conn, stmt, sql);

                        //STEP 5: Extract data from result set
                        System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                        while (rs.next()) {
                            //Retrieve by column name
                            String gname = rs.getString("groupname");
                            String hwriter = rs.getString("headwriter");
                            String yformed = rs.getString("yearformed");
                            String subject = rs.getString("subject");
                            //Display values
                            System.out.printf(displayFormat, 
                                    dispNull(gname), dispNull(hwriter), dispNull(yformed), dispNull(subject));
                        }
                        //STEP 6: Clean-up environment
                        cleanup(stmt,rs);
                    } catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }**/