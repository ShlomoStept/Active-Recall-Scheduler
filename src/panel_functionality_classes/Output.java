package panel_functionality_classes;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;

import java_database_communication.JavaADCommunication;


    //TODO
    // 1.0 Fix the toString() using a stringBuilder
    // 2.0 FIGUre Out a Better sorting method for the more complex cases such as:  cs106 vs cs 106 vs CS 106
    // 2.0 Must abstract out the for repeated get connection calls, in the selectText and getConnection methods
    // 3.0 Can i abstract out the need to the user to be created in this class (inherance/extension)



public class Output {


    //-----------------------------------------------------------------------------------------
        // Data fields :: Current day data
    //-----------------------------------------------------------------------------------------
    private static LocalDate today;
    private static ArrayList<String> arqs;
    private static ArrayList<LocalDate> previousDates;
    private static User User;


//------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------
    //  ::::: Constructors :::::::
    //-----------------------------------------------------------------------------------------

    // 1.0 -- Default constructor
    //-----------------------------------------------------------------------------------------
    public Output(User user) throws Exception {
        if(user == null)
            throw new IllegalArgumentException("Error :: User cannot be null");
        this.User = user;
        today= getTodaysDate();
        previousDates = getPreviousDates(today);
        arqs = getallArqsforDate(today);
    }

//------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------
    // Methods :: part A -> User Info  ------> :::: Setters and getters. :::::: <---------
    //-----------------------------------------------------------------------------------------

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

        //---------------------------------------------------------------------------

    public ArrayList<String> getArqs() {
        return arqs;
    }

    public void setArqs(ArrayList<String> arqs) {
        this.arqs = arqs;
    }

        //---------------------------------------------------------------------------

    public ArrayList<LocalDate> getPreviousDates() {
        return previousDates;
    }

    public void setPreviousDates(ArrayList<LocalDate> previousDates) {
        this.previousDates = previousDates;
    }

        //---------------------------------------------------------------------------

    public static User getUser() {
        return User;
    }

    public static void setUser(User user) {
        User = user;
    }

        //---------------------------------------------------------------------------


    // TODO ----> THIS  ::::: toString() :::: DOES NOT WORK !!!!!!!!!
    @Override
    public String toString() {
        return "Output{" +
                "User="+ User.toString() +
                "today=" + today +
                ", arqs=" + arqs +
                ", previousDates=" + previousDates +
                '}';
    }


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
            //  --> 2.0: getPreviousDates:  get the dates for DAY-1, DAY - 4, DAY -11 DAY -23
    //-------------------------------------------------------------------------------------
    private static ArrayList<LocalDate> getPreviousDates(LocalDate today){
        LocalDate DAY1 = today.minusDays(1);
        LocalDate DAY4 = today.minusDays(4);
        LocalDate DAY11 = today.minusDays(11);
        LocalDate DAY23 = today.minusDays(23);

        ArrayList<LocalDate> arqDates = new ArrayList<LocalDate>();
        arqDates.add(DAY23);
        arqDates.add(DAY11);
        arqDates.add(DAY4);
        arqDates.add(DAY1);

        return arqDates;
    }


        //-----------------------------------------------------------------------------------------------------------------
            //  --> 3.0  getArqs: given a date it returns a sorted arraylist of the arqs to study on the date
    //-------------------------------------------------------------------------------------
    public static ArrayList<String> getArqsforOneDate(LocalDate date) throws Exception {

        // A --> connect to the database
    	JavaADCommunication com = new JavaADCommunication(User.getDatabaseUrl(), User.getDatabaseTablename());
        //DO I NEED THIS??? Connection conn = com.getConnection();



        // B--> look up all the arq entries associated with the given date
        ArrayList<String> List = new ArrayList<String>();
        String tempList="";

        // B-1.0 --> LOOKUP AND GRAB EACH DAYS LIST OF CLASSES if they exist
            if(com.isLocationEmpty("dailyArqs", "( Date = " + "\'"+date+"\')")) {
            
                 // TODO : should i keep?
                //System.out.println( date + " has no Arq's.");

            } else {
                tempList = com.selectText("dailyArqs", "( Date = " + "\'"+date+"\')");
            }

            // B-2.0 --> if the current day has classes: save each comma separated class into an arraylist
            if(tempList!=null && tempList.length() >1) {
                // NOTE:: addAll --> adds the collection of all...
                List.addAll( parseArqs(tempList) );
            }


        // 3.0 NEXT SORT THE ORDER to make the classes all align
        // TODO --> figure out how to sort in more complex ares cs106 vs cs 106 vs CS 106
        //  --> maybe in input if there is a space between CS and 106 (ex Cs 106 # 4)
        //  --> figure out how to concatenate correctly
        List.sort(Comparator.naturalOrder());

        return List;
    }



        //-----------------------------------------------------------------------------------------------------------------
            //  --> 4.0  getArqs: given a date it returns a sorted arraylist of the arqs to study on the date
    //-------------------------------------------------------------------------------------
    protected static ArrayList<String> getallArqsforDate(LocalDate date) throws Exception {

        // A --> connect to the database
    	JavaADCommunication com = new JavaADCommunication(User.getDatabaseUrl() , User.getDatabaseTablename());
      //DO I NEED THIS??? Connection conn = com.getConnection();


        // B --> get a list of all the arq dates to study based on today's date
        ArrayList<LocalDate> arqDates = getPreviousDates(date);


        // C --> look up all the strings in the daily arq columns associated with each day
        ArrayList<String> fullList = new ArrayList<String>();

        for(int i =0; i < arqDates.size(); i++){
            String nextDate = arqDates.get(i).toString();

            // C-1.0 --> LOOKUP AND GRAB EACH DAYS LIST OF CLASSES if they exist
            String daylist =null;
            if(com.isLocationEmpty("dailyArqs ", "(Date =" + "\'"+nextDate+"\')")) {

                //System.out.println(nextDate + " has no Arq's."); // TODO : should i keep?

            } else {
                daylist = com.selectText("dailyArqs", "( Date = " + "\'"+nextDate+"\')");

                        //System.out.println(nextDate + " Does have Arq's.");
            }

                // C-2.0 --> if the current day has classes: save each comma separated class into an arraylist
            if(daylist!=null && daylist.length() >1) {
                    // NOTE:: addAll --> adds the collection of all...
                fullList.addAll( parseArqs(daylist) );
            }

                    //System.out.println(nextDate); TODO : should i print?
        }

            // 3.0 NEXT SORT THE ORDER to make the classes all align
        // TODO --> figure out how to sort in more complex ares cs106 vs cs 106 vs CS 106
        //  --> maybe in input if there is a space between CS and 106 (ex Cs 106 # 4)
        //  --> figure out how to concatenate correctly

        fullList.sort(Comparator.naturalOrder());
        
        // ------> 
        // ------> // ------>  // ------> 
		// CHANGE TO Ignore case when sorting   ------ SOMETHING LIKE -->  fullList.sort(Comparator.comparing(String::fullList,String.CASE_INSENSITIVE_ORDER);
        // ------> // ------> // ------> 
        // ------> 
       

        return fullList;
    }


        //-----------------------------------------------------------------------------------------------------------------
            //  --> 5.0 parseArqs: separates the line by parsing each comma separating a sequence of characters
            //          --> this method takes in a text blob, containing comma separated arqs,
            //              and separates each one and then puts each one into its own arraylist entry
    //-------------------------------------------------------------------------------------
    protected static ArrayList<String> parseArqs(String list){
        ArrayList<String> allArqs = new ArrayList<String>();

        while(list !=null && !list.isEmpty() && list.length() >1){
            int index = list.indexOf(',');
            String temp = list.substring(0,index).strip();
           
            allArqs.add(temp);
            list = list.substring(index+1).strip();
        }

        return allArqs;
    }


        //-----------------------------------------------------------------------------------------------------------------
            //  --> 6.0  print out the arqs in a nice way
    //-------------------------------------------------------------------------------------
    protected static void printPretty(ArrayList<String> fullList){
        for(String t: fullList)
            System.out.println(t);
    }
    
    	//-----------------------------------------------------------------------------------------------------------------
    		//  --> 7.0  turn a list to a one line string
	//-------------------------------------------------------------------------------------
    public static String ArraytoString(ArrayList<String> fullList){
    	StringBuilder s = new StringBuilder();
    	if(fullList.size()>0) {
    		for(String t: fullList)
    			s.append(t + ", ");
    		s.delete(s.length()-2, s.length()-1);
    		return s.toString();
    	}
    	return "";
    }

}
