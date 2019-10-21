package cecs323.jdbc;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to access, view, and manage a Derby Database.
 * Project: JDBC
 * @author  Jessica Wei
 * @version 1.01 Oct 21, 2019
 */
public class Cecs323JDBC {
    /*  Database credentials */
    /* User entered Username */
    static String USER;
    /* User entered Password */
    static String PASS;
    /* User entered Database Name */
    static String DBNAME;
    // Required entries to access database (ENTER THESE STRINGS IN ORDER TO ACCESS DB)
    /* Correct Database name*/
    static final String cDB = "clientdriver";
    /* Correct Username */
    static final String cUSER = "jessicawei";
    /* Correct Password */
    static final String cPASS = "password";
    
    /* Data Formatting */
    /* Writing Group Data Format */
    static final String displayFormatWriter ="%-28s%-28s%-28s%-28s\n";
    /* Publisher Data Format */
    static final String displayFormatPublisher ="%-28s%-28s%-28s%-28s\n";
    /* Book Data Format*/
    static final String displayFormatBook ="%-28s%-28s%-28s%-28s%-28s%-28s%-28s%-28s%-28s%-28s%-28s\n";
    /* JDBC driver name */
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    /* JDBC database URL */
    static String DB_URL = "jdbc:derby://localhost:1527/";
    
    public static void main(String[] args) {
        /* Logs user in */
        login(); 
        /* Creates new connection between program, database */
        Connection conn = connectDB();
        /* Creates new Scanner to take in user input */
        Scanner menuIn = new Scanner(System.in); 
        /* Stays false until user wants to quit out of the menu */
        boolean quit = false;
        /* Keeps track of menu input */
        int mOption = 0;
        /* Stores sql statements */
        String sql = "";
        /* Initialize PreparedStatement */
        PreparedStatement pstmt;
                
        // Begin Menu Loop
        while(!quit){
            System.out.println();
            /* Prints Menu */
            printMenu();
            mOption = getIntBetween(menuIn, 1, 10, "Menu Selection");
            System.out.println();
            
            switch(mOption){
                case 1: 
                    // List all writing groups NAME ONLY
                    try{
                        sql = "SELECT groupname FROM WritingGroups ORDER BY groupname";
                        pstmt = conn.prepareStatement(sql);
                        System.out.println();
                        
                        ResultSet rs = executeQ(conn, pstmt, true, false);
                        /* Print out all Book names*/
                        System.out.println("\nGroup Name");
                        boolean emptyCheck = true; // Assume that the the list of books is empty
                        while (rs.next()) {
                            //Retrieve by column name
                            emptyCheck = false; // not empty
                            String gname = rs.getString("groupname");
                            System.out.println(dispNull(gname));
                        }
                        if(emptyCheck)
                            System.out.println("ERROR: No WriterGroup data to show!");
                        cleanup(pstmt, rs);
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 1;
                    break;
                case 2:
                    // List all the data for ONE group specified by the user
                    try{
                        /* String of user input to check valid input & store information */
                        String check = getWriterSelection(conn);
                        if(!check.equals("")){ // if writer is valid
                            sql = "SELECT groupname, headwriter, yearformed, subject FROM WritingGroups "
                                + "WHERE groupname = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, check); // Insert group name into sql

                            System.out.println();
                            ResultSet rs = executeQ(conn, pstmt, true, false);
                            /* Print out WritingGroup information */
                            System.out.printf(displayFormatWriter, "\nGroup Name", "Head Writer", "Year Formed", "Subject");
                            while (rs.next()) {
                                //Retrieve by column name
                                String gname = rs.getString("groupname");
                                String hwriter = rs.getString("headwriter");
                                String yformed = rs.getString("yearformed");
                                String subject = rs.getString("subject");
                                System.out.printf(displayFormatWriter, dispNull(gname), dispNull(hwriter), dispNull(yformed), dispNull(subject));
                            }
                            cleanup(pstmt, rs);
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
                    try{
                        int empty = 0;
                        sql = "SELECT publishername FROM Publishers ORDER BY publishername";
                        pstmt = conn.prepareStatement(sql);
                        System.out.println();
                        
                        ResultSet rs = executeQ(conn, pstmt, true, false);
                        /* Print out all publisher names */
                        System.out.println("\nPublisher Name");
                        while (rs.next()) {
                            //Retrieve by column name
                            empty++;
                            String pname = rs.getString("publishername");
                            System.out.println(dispNull(pname));
                        }
                        if(empty == 0){
                            System.out.println("ERROR: No Publisher data to show!");
                        }
                        cleanup(pstmt, rs);
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 3;
                    break;
                case 4:
                    // List all the data for ONE publisher specified by the user
                    try{
                        /* String of user input to check valid input & store information */
                        String check = getPublisherSelection(conn, false);
                        if(!check.equals("")){
                            sql = "SELECT publishername, publisheraddress, publisherphone, publisheremail FROM Publishers "
                                + "WHERE publishername = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, check); // Insert publisher name into sql
                            System.out.println();
                            ResultSet rs = executeQ(conn, pstmt,true, false);
                            /* Print out Information */
                            System.out.printf(displayFormatPublisher, "\nPublisher Name", "Address", "Phone", "Email");
                            while (rs.next()) {
                                //Retrieve by column name
                                String pname = rs.getString("publishername");
                                String paddress = rs.getString("publisheraddress");
                                String pphone = rs.getString("publisherphone");
                                String pemail = rs.getString("publisheremail");
                                System.out.printf(displayFormatPublisher, dispNull(pname), dispNull(paddress), dispNull(pphone), dispNull(pemail));
                            }
                            cleanup(pstmt, rs);
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
                    int empty = 0;
                    try{
                        sql = "SELECT booktitle, groupname FROM Books ORDER BY booktitle";
                        pstmt = conn.prepareStatement(sql);
                        
                        System.out.println();
                        
                        ResultSet rs = executeQ(conn, pstmt, true, false);
                        /* Print out all group names */
                        System.out.println("\nBook Title");
                        while (rs.next()) {
                            //Retrieve by column name
                            empty++;
                            String btitle = rs.getString("booktitle");
                            String gname = rs.getString("groupname");
                            System.out.println(dispNull(btitle) + " (By: " + dispNull(gname) + ")");
                        }
                        cleanup(pstmt, rs);
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    if(empty == 0){
                        System.out.println("ERROR: No Book data to show!");
                    }
                    mOption = 5;
                    break;
                case 6:
                    // List all the data for ONE book specified by the user INCLUDING Writing Groups and Publishers ALL DATA
                    try{
                        /* String of user input to check valid input & store information */
                        String[] check = getBookSelection(conn);
                        
                        if(!check[0].equals("")){
                            sql = "SELECT booktitle, yearpublished, numberpages, "
                                + "groupname, headwriter, yearformed, subject, "
                                + "publishername, publisheraddress, publisherphone, publisheremail "
                                + "FROM Books b " 
                                + "NATURAL JOIN writingGroups w "
                                + "NATURAL JOIN publishers p "
                                + "WHERE booktitle = ? AND w.groupname = b.groupname";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, check[0]);
                        
                            System.out.println();
                            ResultSet rs = executeQ(conn, pstmt, true, false);
                            /* Print out all information */
                            System.out.printf(displayFormatBook, "\nBook Title", "Year Published", "Number Pages", 
                                    "Group Name", "Head Writer", "Year Formed", "Subject",
                                    "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
                            while (rs.next()) {
                                //Retrieve by column name
                                String btitle = rs.getString("booktitle");
                                String ypublished = rs.getString("yearpublished");
                                String nPages = rs.getString("numberpages");
                                String gname = rs.getString("groupname");
                                String hwriter = rs.getString("headwriter");
                                String yFormed = rs.getString("yearformed");
                                String sub = rs.getString("subject");
                                String pname = rs.getString("publishername");
                                String padd = rs.getString("publisheraddress");
                                String pphone = rs.getString("publisherphone");
                                String pemail = rs.getString("publisheremail");
                                System.out.printf(displayFormatBook, dispNull(btitle), dispNull(ypublished), dispNull(nPages),
                                                    dispNull(gname), dispNull(hwriter), dispNull(yFormed), dispNull(sub),
                                                    dispNull(pname), dispNull(padd), dispNull(pphone), dispNull(pemail));
                            }
                            cleanup(pstmt, rs);
                        }
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 6;
                    break;
                case 7:
                    // Insert a new book
                    try{
                        boolean badWriter = false;
                        boolean badPub = false;
                        //insert into customers (first_name, last_name, phone, street, zipcode)
                        //values ('Tom', 'Jewett', '714-888-7000', '123 Mockingbird Lane', '90210');
                        sql = "INSERT INTO Books (groupname, publishername, booktitle, yearpublished, numberpages) values "+
                                "(?, ?, ?, ?, ?)";
                        String strIn = "";
                        int intIn = -1;
                        
                        pstmt = conn.prepareStatement(sql);
                        // Get Writing Group
                        strIn = getWriterSelection(conn);
                        if(strIn.length() == 0){
                            badWriter = true;
                        }
                        pstmt.setString(1, strIn);
                        // Get Publisher
                        strIn = getPublisherSelection(conn, false);
                        if(strIn.length() == 0){
                            badPub = true;
                        }
                        pstmt.setString(2, strIn);
                        // Get Title
                        strIn = getValidString(menuIn, 25, "Title");
                        pstmt.setString(3, strIn);
                        // Get Year
                        intIn = getIntBetween(menuIn, 0, 9999, "Year Published");
                        pstmt.setInt(4, intIn);
                        // Get Positive Int
                        intIn = getPositiveInt(menuIn, "Number of Pages");
                        pstmt.setInt(5, intIn);
                        
                        System.out.println();
                        if(!badWriter && !badPub){
                            System.out.print("Creating Book... ");
                            executeQ(conn, pstmt, false, true);
                        } 
                        else{
                            if(badWriter)
                                System.out.println("ERROR: Invalid Writing Group entry.");
                            if(badPub)
                                System.out.println("ERROR: Invalid Publisher entry.");
                        }
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    }catch (SQLException se) {
                        if (se instanceof SQLIntegrityConstraintViolationException){
                            System.out.println("Duplicate Entry. Cannot store entered Book into database.");
                        }
                        //Handle errors for JDBC
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 7;
                    break;
                case 8:
                    // Insert a new publisher and update all books published by one publisher
                    // to be published by the new publisher. Leave the old publisher alone, just modify
                    // the books that they have published. Assume that the new publisher has bought out
                    // the old one, so now any books published by the old publisher are publishesd by the new one
                    try{
                        //insert into customers (first_name, last_name, phone, street, zipcode)
                        //values ('Tom', 'Jewett', '714-888-7000', '123 Mockingbird Lane', '90210');
                        sql = "INSERT INTO Publishers (publishername, publisheraddress, publisherphone, publisheremail) values "+
                                "(?, ?, ?, ?)";
                        String strIn = "";
                        String newName = "";
                        
                        pstmt = conn.prepareStatement(sql);
                        // Get Publisher
                        newName = getValidString(menuIn, 25, "Name");
                        pstmt.setString(1, newName);
                        // Get Address
                        strIn = getValidString(menuIn, 25, "Address");
                        pstmt.setString(2, strIn);
                        // Get Phone
                        strIn = getValidPhone(menuIn, 11, "Phone");
                        pstmt.setString(3, strIn);
                        // Get Email
                        strIn = getValidString(menuIn, 25, "Email");
                        pstmt.setString(4, strIn);
                        
                        // Get Replaced Publisher
                        String replacedP = getPublisherSelection(conn, true);
                        
                        if(!replacedP.equals("")){
                            System.out.print("Creating publisher... ");
                            executeQ(conn, pstmt, false, true);
                            sql = "UPDATE Books SET publishername = ? WHERE publishername = ?";
                            pstmt = conn.prepareStatement(sql);
                            if(!newName.equals("")){
                                pstmt.setString(1, newName);
                                pstmt.setString(2, replacedP);
                                System.out.print("Replacing publisher... ");
                                executeQ(conn, pstmt, false, true);
                            }
                            else{
                                System.out.println("Invalid Publisher name.");
                            }
                        }
                        else
                            System.out.println("Invalid Publisher to be replaced.");
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    }catch (SQLException se) {
                        if (se instanceof SQLIntegrityConstraintViolationException){
                            System.out.println("Duplicate Entry. Cannot store entered Publisher into database.");
                        }
                        //Handle errors for JDBC
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    mOption = 8;
                    break;
                case 9:
                    // Remove a book specified by a user 
                    try{
                        sql = "DELETE FROM Books WHERE booktitle = ? AND groupname = ?";
                        pstmt = conn.prepareStatement(sql);
                        String bookCheck[] = getBookSelection(conn);
                        if(!bookCheck[0].equals("")){
                            pstmt.setString(1, bookCheck[0]);
                            if(!bookCheck[1].equals("")){
                                pstmt.setString(2, bookCheck[1]);
                                System.out.print("Removing Book... ");
                                executeQ(conn, pstmt, false, true);
                            }
                            else
                                System.out.println("ERROR: Invalid Writing Group.");
                        }
                        else
                            System.out.println("ERROR: Invalid Book Title.");
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    }catch (SQLException se) {
                        //Handle errors for JDBC
                        se.printStackTrace();
                    } catch (Exception e) {
                        //Handle errors for Class.forName
                        e.printStackTrace();
                    }
                    
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
/**
 * Connects to the Database at the beginning of the program
 */
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
    } // end connectDB
/**
 * Disconnects the Database at the end of the program
 * @param conn the Connection object that connects the program to the database
 */
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
    }// end disconnectDB
/**
 * Closes the Result Set and Statement to prevent memory leaks
 * @param pstmt the PreparedStatement to be closed
 * @param rs the ResultSet to be closed
 */
    public static void cleanup(PreparedStatement pstmt, ResultSet rs){
        try {
            //STEP 6: Clean-up environment
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do; end finally try
        }//end try
    }// end cleanup
/**
 * Executes the requested statement
 * @param conn the Connection between the program and the database
 * @param psmt the PreparedStatement to be executed
 * @param showConnecting Whether to show "Creating statement..." or not
 * @param updating If the prepared statement is an Update or just a View
 * @return ResultSet the set of data received from executing the Query (is NULL if updating)
 */
    public static ResultSet executeQ(Connection conn, PreparedStatement psmt, boolean showConnecting, boolean updating){
        ResultSet rs = null;
         //STEP 4: Execute a query 
        try{
            if(showConnecting){
                System.out.println("Creating statement...");
            }
            if(!updating)
                rs = psmt.executeQuery();
            else{
                psmt.executeUpdate();
                System.out.println("Complete.");
            }
                
        } catch (SQLException se) {
            if (se instanceof SQLIntegrityConstraintViolationException){
                System.out.println("ERROR: Duplicate Entry. Cannot store into database.");
            }
            //Handle errors for JDBC
            //se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return rs;
    }// end executeQ
/**
 * Prints the Main Menu Options
 */
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
    }// end printMenu
/**
 * Gets a valid integer between a given range from user input
 * @param mIn the Scanner used to take in user input
 * @param min the smallest valid input
 * @param max the largest valid input
 * @param prompt the String that the user sees to know what information to enter
 * @return the valid int of choice
 */    
    public static int getIntBetween(Scanner mIn, int min, int max, String prompt){
        int mOption = -1;
        boolean valid = false;
        while(!valid){
            System.out.print(prompt + ": ");
            if(mIn.hasNextInt()){
                mOption = mIn.nextInt();
                mIn.nextLine();
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
    }// end getIntBetween
/**
 * Gets a positive integer from user input
 * @param mIn the Scanner used to take in user input
 * @param prompt the String that the user sees to know what information to enter
 * @return the valid int of choice
 */
    public static int getPositiveInt(Scanner mIn, String prompt){
        int mOption = -1;
        boolean valid = false;
        while(!valid){
            System.out.print(prompt + ": ");
            if(mIn.hasNextInt()){
                mOption = mIn.nextInt();
                mIn.nextLine();
                if(mOption > -1){
                    valid = true;
                    return mOption;
                }
                else{
                    System.out.println("Input out of range. Please choose a positive number.");
                }
                    
            }else{
                System.out.println("Invalid Input. Please enter an integer.");
                mIn.next();
            }
        }
        return mOption;
    }// end getPositiveInt
/**
 * Gets a valid String from the user depending on its length
 * @param mIn the Scanner used to take in user input
 * @param maxLen the maximum length that the string can be
 * @param prompt the String that the user sees to know what information to enter
 * @return the valid string of choice
 */
    public static String getValidString(Scanner mIn, int maxLen, String prompt){
        String store = "";
        boolean valid = false;
        while(!valid){
            System.out.print(prompt +": ");
            store = mIn.nextLine();
            if(store.length() <= maxLen && store.length() != 0){
                valid = true;
                return store;
            }else{
                System.out.println("Invalid Input. Please enter string less than " + maxLen + " characters in length.");
            }
        }
        return store;
    }// end getValidString
/**
 * Gets a valid phone number from the user depending on length and content
 * @param mIn the Scanner used to take in user input
 * @param maxLen the maximum length that the phone number can be
 * @param prompt the String that the user sees to know what information to enter
 * @return the valid phone number of choice
 */    
    public static String getValidPhone(Scanner mIn, int maxLen, String prompt){
        String store = "";
        boolean valid = false;
        while(!valid){
            System.out.print(prompt +": ");
            store = mIn.nextLine();
            if(store.length() == maxLen){
                try{
                    Long.parseLong(store);
                    valid = true;
                }catch (NumberFormatException nf){
                    System.out.println("Invalid Input. Please enter a phone number with only digits.");
                    valid = false;
                }
            }else{
                System.out.println("Invalid Input. Please enter phone number " + maxLen + " digits in length.");
            }
        }
        return store;
    }// end getValidPhone
/**
 * Gets a valid WritingGroup from the user depending on existence
 * @param conn the Connection between the program and the database
 * @return a String representation of the WriterGroup's name
 */    
    public static String getWriterSelection(Connection conn){
        String validWriter = "";
        Scanner in = new Scanner(System.in);
        try{
            String sql = "SELECT groupname FROM WritingGroups WHERE groupname = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.print("Writer: ");
            validWriter = in.nextLine();
            pstmt.setString(1, validWriter);
            
            ResultSet rs = executeQ(conn, pstmt, false, false);
            if(!rs.next()){
                System.out.println("That group does not exist.");
                validWriter = "";
            }
            }catch (SQLException se) {
                System.out.println("Invalid Group.");
            }
        return validWriter;
    }// end getWriter Selection
/**
 * Gets a valid Publisher from the user depending on existence
 * @param conn the Connection between the program and the database
 * @param replaced if the publisher entered is to be replaced (prompt purposes only)
 * @return a String representation of the Publisher's name
 */
    public static String getPublisherSelection(Connection conn, boolean replaced){
        String validPublisher = "";
        Scanner in = new Scanner(System.in);
        try{
            String sql = "SELECT publishername FROM Publishers WHERE publishername = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if(replaced)
                System.out.print("Replaced ");
            System.out.print("Publisher: ");
            validPublisher = in.nextLine();
            pstmt.setString(1, validPublisher);

            ResultSet rs = executeQ(conn, pstmt, false, false);
            if(!rs.next()){
                validPublisher = "";
                System.out.println("That publisher does not exist.");
            }


        }catch (SQLException se) {
            System.out.println("Invalid Publisher.");
        }
        return validPublisher;
    }//end getPublisherSelection
/**
 * Gets a valid Book from the user depending on existence (name and writing group)
 * @param conn the Connection between the program and the database
 * @return a String[] representation of the Book title and WriterGroup name
 */
    public static String[] getBookSelection(Connection conn){
        String[] validBook = new String[2];
        Scanner in = new Scanner(System.in);
        try{
            String sql = "SELECT booktitle FROM Books WHERE booktitle = ? AND groupname = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.print("Book: ");
            validBook[0] = in.nextLine();
            pstmt.setString(1, validBook[0]);
            validBook[1] = getWriterSelection(conn);
            pstmt.setString(2, validBook[1]);
            ResultSet rs = executeQ(conn, pstmt, false, false);
            if(!rs.next()){
                validBook[0] = "";
                validBook[1] = "";
                System.out.println("That book does not exist.");
            }
        }catch (SQLException se) {
            System.out.println("Invalid Book.");
        }
        return validBook;
    }// end getBookSelection
}// end Cecs323JDBC class