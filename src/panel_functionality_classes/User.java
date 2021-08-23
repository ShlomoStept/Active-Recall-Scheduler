
// Class user: A class designated to distinguish a unique user, and general info required to use
    // a- user info
    // B- Database info
// TODO  (in future get the relevant info) by prompting user if its the sign up time.

package panel_functionality_classes;


public class User {

    //-----------------------------------------------------------------------------------------
        // Data fields :: part A -> User Info
    //-----------------------------------------------------------------------------------------
    private String FirstName;
    /*private String MiddleName;*/
    private String LastName;
    private int userId;


    //-----------------------------------------------------------------------------------------
        // Data fields :: part B -> DataBase Info
    //-----------------------------------------------------------------------------------------
    private static String databaseUrl;          
    //private static String databasePassword;     
    //private static String databaseUserName;          
    private static String databaseName;       
    private static String databaseTablename;    

//------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------
            //  ::::: Constructors :::::::
    //-----------------------------------------------------------------------------------------

        // 1.0 -- Default constructor
    //-----------------------------------------------------------------------------------------
    public User(){
        this.FirstName = "";
        /*this.MiddleName = "";*/
        this.LastName = "";
        this.userId = 0;
        this.databaseUrl = "";
        //this.databasePassword = "";
        //this.databaseUserName = "";
        this.databaseName = "";
        this.databaseTablename = "";
        //super();

    }

        //-----------------------------------------------------------------------------------------
            // 2.0 -- User-Info Constructor
    //-----------------------------------------------------------------------------------------
    public User(String fn, /*String mn,*/ String ln){
        this.FirstName = fn;
        /*this.MiddleName = "";*/
        this.LastName = ln;
        this.userId = fn.hashCode() + ln.hashCode();
        this.databaseUrl = "";
        //this.databasePassword = "";
        //this.databaseUserName = "";
        this.databaseName = "";
        this.databaseTablename = "";

    }

        //-----------------------------------------------------------------------------------------
            // 3.0 -- Database-Info Constructor -->> Never really going to use this
    //-----------------------------------------------------------------------------------------
    public User( String dburl, String dbpass, String dbuname, String dbname, String tn ){
        this.FirstName = "";
        //this.MiddleName = "";
        this.LastName = "";
        this.userId = 0;
        this.databaseUrl = dburl;
        //this.databasePassword = dbpass;
        //this.databaseUserName = dbuname;
        this.databaseName = dbname;
        this.databaseTablename = tn;


    }

        //-----------------------------------------------------------------------------------------
            // 4.0 -- Primary Constructor
    //-----------------------------------------------------------------------------------------
    public User(String fn, /*String mn,*/ String ln, String dburl, String dbpass, String dbuname, String dbname, String tn ){
        this.FirstName = fn;
        //this.MiddleName = "";
        this.LastName = ln;
        this.userId = fn.hashCode() + ln.hashCode();
        this.databaseUrl = dburl;
        //this.databasePassword = dbpass;
        //this.databaseUserName = dbuname;
        this.databaseName = dbname;
        this.databaseTablename = tn;

    }

//------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------
        // Methods :: part A -> User Info  ------> :::: Setters and getters. :::::: <---------
    //-----------------------------------------------------------------------------------------

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

        //---------------------------------------------------------------------------

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

        //---------------------------------------------------------------------------

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    //-----------------------------------------------------------------------------------------
        // Methods :: part B -> DataBase Info  ------> :::: Setters and getters. :::::: <---------
    //-----------------------------------------------------------------------------------------

    public static String getDatabaseUrl() {
        return databaseUrl;
    }

    public static void setDatabaseUrl(String databaseUrl) {
        User.databaseUrl = databaseUrl;
    }

        //----------------------------------------------------------------------------

		/*
		 * public static String getDatabasePassword() { return databasePassword; }
		 * 
		 * public static void setDatabasePassword(String databasePassword) {
		 * User.databasePassword = databasePassword; }
		 */

        //----------------------------------------------------------------------------

		/*
		 * public static String getDatabaseUserName() { return databaseUserName; }
		 * 
		 * public void setDatabaseUserName(String databaseUserName) {
		 * this.databaseUserName = databaseUserName; }
		 */

        //----------------------------------------------------------------------------

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        User.databaseName = databaseName;
    }

        //-----------------------------------------------------------------------------
    public static String getDatabaseTablename() {
        return databaseTablename;
    }

    public static void setDatabaseTablename(String databaseTablename) {
        User.databaseTablename = databaseTablename;
    }


//------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------
    // Methods :: part C -> Extras  ------> :::: ToString, isvalid???, ... :::::: <---------
    //-----------------------------------------------------------------------------------------


    @Override
    public String toString() {
        return "User{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", userId=" + userId +
// CANT INCLUDE THIS === Security risk::-->  ", databaseUrl='" + databaseUrl + '\'' +
// CANT INCLUDE THIS === Not Being utilized::-->  ", databasePassword='" + databasePassword + '\'' +
// CANT INCLUDE THIS === Not Being utilized::-->                  ", databaseUserName='" + databaseUserName + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", databaseTablename='" + databaseTablename + '\'' +
                '}';
    }
}
