package panel_functionality_classes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import java_database_communication.JavaADCommunication;

public class Input {

    //-----------------------------------------------------------------------------------------
    // Data fields :: Input data fields
    //-----------------------------------------------------------------------------------------

    private static LocalDate today;
    private static User User;

    private static ArrayList<String> arqsToAdd;
    private static LocalDate otherDay;
    private static boolean useToday;


//------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------
    //  ::::: Constructors :::::::
    //-----------------------------------------------------------------------------------------

    // 1.0 -- Default constructor
    //-----------------------------------------------------------------------------------------
    public Input(User user){
        if(user == null)
            throw new IllegalArgumentException("Error :: User cannot be null");
        User = user;
        this.today = getTodaysDate();

    }



    
//------------------------------------------------------------------------------------------------------------

    
    
    //-----------------------------------------------------------------------------------------
    // Methods :: part A -> User Info  ------> :::: Setters and getters. :::::: <---------
    //-----------------------------------------------------------------------------------------

    public static LocalDate getToday() {
        return today;
    }

    public static void setToday(LocalDate today) {
        Input.today = today;
    }

        //---------------------------------------------------------------------------

    public static User getUser() {
        return User;
    }

    public static void setUser(User user) {
        User = user;
    }

        //---------------------------------------------------------------------------

    public static ArrayList<String> getArqsToAdd() {
        return arqsToAdd;
    }

    public static void setArqsToAdd(ArrayList<String> arqsToAdd) {
        Input.arqsToAdd = arqsToAdd;
    }

        //---------------------------------------------------------------------------

    public static LocalDate getOtherDay() {
        return otherDay;
    }

    public static void setOtherDay(LocalDate otherDay) {
        Input.otherDay = otherDay;
    }

        //---------------------------------------------------------------------------

    public static boolean isUseToday() {
        return useToday;
    }

    public static void setUseToday(boolean useToday) {
        Input.useToday = useToday;
    }

        //---------------------------------------------------------------------------

    // TODO ----> THIS  ::::: toString() :::: DOES NOT WORK !!!!!!!!!
   /* @Override
    public String toString() {
        return "Input{" +
                "Use=" + Use +
                '}';
    }*/

//------------------------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------------------------------
        // Methods :: part B -> MAIN METHODS ------> ::::  KEY METHODS :::::: <---------
    //---------------------------------------------------------------------------------------------------------------------

        //  --> 1.0: getTodaysDate
    //-------------------------------------------------------------------------------------e
    public static LocalDate getTodaysDate(){
        ZoneId timezone = ZoneId.of("America/New_York");
        LocalDate today = LocalDate.now(timezone);

        return today;
    }

        //-----------------------------------------------------------------------------------------------------------------
            //  --> 2.0: Usetoday?
    //-------------------------------------------------------------------------------------
    public void useToday(){
        String answer;
        do {
            System.out.print("Use Current day? :  ");
            Scanner scan = new Scanner(System.in);
            answer = scan.next().toLowerCase();
            scan.close();
            if (answer.contains("y"))
                useToday = true;
            else if (answer.contains("n"))
                useToday = false;
        }while(!answer.contains("y") || !answer.contains("n"));


    }

        //-----------------------------------------------------------------------------------------------------------------
            //  --> 2.0: Welcome
    //-------------------------------------------------------------------------------------
    public void Welcome(){
        System.out.print(
                "Welcome to the Arq Scheduler, " +
                        "First: Please select a day to add arqs to" +
                        "Next: Type in the arqs, one at a time, and " +
                        " --> :: Remember you MUST separate each arq by a comma :: <--"
        );
    }


        //-----------------------------------------------------------------------------------------------------------------
            //  --> 2.0: getDayToUse
    //-------------------------------------------------------------------------------------
    public void getDaytoUse(){
        if(useToday != true && useToday != false) {
            useToday();
        }
        if(useToday==true) {
            //do nothing
        } else{
            System.out.print("Select day to use from the calendar :  ");

            System.out.print("Enter date in the form (dd/MM/yyyy) :  ");
            Scanner scan = new Scanner(System.in);
            String date = scan.next().toLowerCase();

            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            otherDay = LocalDate.parse(date, dtf);
        }


    }




    //-----------------------------------------------------------------------------------------------------------------
    //  --> 2.0: getArqsfromUser
    //-------------------------------------------------------------------------------------
    
    


    //-----------------------------------------------------------------------------------------------------------------
    //  --> 2.0: updateDatabase
    //-------------------------------------------------------------------------------------
    public void updateDatabase(String newarqs,  LocalDate date) throws Exception{
    	//System.out.println("in input the date is : "+date);
    	//System.out.println("in input the newarqs are : "+newarqs);
    	
    	
    	JavaADCommunication com = new JavaADCommunication(User.getDatabaseUrl() , User.getDatabaseTablename());
    	// TRY TO GENERALIZE THE COLUMN NAME ENTRY (ENTRY #2)
    	if(com.isLocationEmpty("dailyArqs", "( Date = " + "\'"+date+"\')")) {
    		com.updateText(newarqs, "dailyArqs", User.getDatabaseTablename(), "( Date = " + "\'"+date+"\')");
    	}else {
    		com.addText(newarqs, "dailyArqs",  "( Date = " + "\'"+date+"\')", User.getDatabaseTablename() );
    	}
		
    
    }




}
