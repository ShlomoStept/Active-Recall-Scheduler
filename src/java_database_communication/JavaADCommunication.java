package java_database_communication;

/*  Written by: Shlomo Stept
Purpose: This project is a java class/package that greatly simplifies the communication between
my Java program and the apache derby  Database.
*/
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JavaADCommunication {

//---------------------------------------------------------------------------------------------------------------------
// 0 -> Data Fields:
//---------------------------------------------------------------------------------------------------------------------
private String url; // url of database
//private String userName; // username to database
//private String password; // password to database
//private String databaseName; // database name
private String tableName; // table-name in database
// to do :: --> :: private Boolean create = false; // is there a table currently in the database

//---------------------------------------------------------------------------------------------------------------------
// 1 -> Constructors: JavaSqlCommunication
//---------------------------------------------------------------------------------------------------------------------


//  --> 1.1 JavaADCommunication
//-------------------------------------------------------------------------------------
/**
 * JavaADCommunication is a Constructor that instantiates a new instance of the JavaADCommunication class
 * @param url The url to the database.
 * @param tableName The table name.
 */
public  JavaADCommunication(String url,  String tableName)
{
    if(url == null )
        throw new IllegalArgumentException("JavaADCommunication: url is null");
    if(tableName  == null )
        throw new IllegalArgumentException("JavaADCommunication: TablenName is null");

    this.url = url;
    this.tableName = tableName;
}


//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for Constructors  <----- End.
//---------------------------------------------------------------------------------------------------------------------

//********************************************************************************************************************

//---------------------------------------------------------------------------------------------------------------------
// Methods: Part 2 : JavaSqlCommunication  --> Connection <-- operations
//---------------------------------------------------------------------------------------------------------------------

//  --> 2.1 getConnection
//-------------------------------------------------------------------------------------
/**
 * getConnection is a function that obtains a connection to the database requested and returns that as a Connection object
 * @return Returns the Connection object with a connection to the database specified in the respective JavaSqlCommunication Instance
 */
public Connection getConnection() throws Exception {
    return getConnection(this.url+"");
}

//-----------------------------------------------------------------------------------------------------------------
//  --> 2.2 getConnection
//-------------------------------------------------------------------------------------

/**
 * getConnection is a function that obtains a connection to the database requested and returns that as a Connection object
 * @return Returns the Connection object with a connection to the database specified in the accompanied JavaSqlCommunication Instance
 * --> NOTE --> CAN BE USED TO CONNECT TO ANY DATABASE, ITS NOT LIMITEd TO THE ONE SPECIFIED WHEN INSTANTIATING THE RESPECTIVE INSTANCE of JavaSqlCommunication
 */
public Connection getConnection(String url) throws Exception {
    if(url == null )
        throw new IllegalArgumentException("JavaADCommunication: url is null");

    Connection conn = null;
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    try {
    	conn = DriverManager.getConnection(url+"");
    	}catch (SQLException ex) {
        	System.out.println("] connection failed");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        //System.out.println("Connection Successful");
 
    return conn;
}


//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for getConnection  <----- End.
//---------------------------------------------------------------------------------------------------------------------

//********************************************************************************************************************

//---------------------------------------------------------------------------------------------------------------------
// Methods: Part 3 : JavaSqlCommunication  --> Create <-- operations
//---------------------------------------------------------------------------------------------------------------------

//  --> 3.1 createTable
//-------------------------------------------------------------------------------------
/**
 * createTable is a void function that creates a new table with the given tablename in the database specified when instantiating the respective instance of JavaSqlCommunication
 * -->>>>>>>> ** FURTHER WORK MUST BE DONE TO CREATE AN INSTANCE OF THIS METHOD THT ALLOWS FOR THE USER TO CREATE 1 SPECIFIED ROW WITH A RESPECTIVE COLUMN NAME
 * --> by default since a table must have at least one column if no column is specified the column id - with datatype int will be initialized
 */
public void createTable(String tablename) throws Exception {
    if(tablename == null)
        throw new IllegalArgumentException("JavaSqlCommunication: tablename is null");
    try (Connection conn = getConnection();
         PreparedStatement create = conn.prepareStatement("CREATE TABLE "+tablename+"(id int)")){
        create.execute();
        //System.out.println("Create_table Successfully executed");
        try(PreparedStatement alter = conn.prepareStatement("ALTER TABLE "+tablename+ " CHANGE COLUMN  id"+ "  id"+" INT(11) NOT NULL AUTO_INCREMENT" + " , ADD PRIMARY KEY ( id );")
        ){
            alter.executeUpdate();
            alter.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }catch (SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());

    }
}


//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for Part 3: Create  <----- End.
//---------------------------------------------------------------------------------------------------------------------


//********************************************************************************************************************

//---------------------------------------------------------------------------------------------------------------------
// Methods: Part 4 : JavaSqlCommunication  --> Update, where  <-- operations
//---------------------------------------------------------------------------------------------------------------------


//-----------------------------------------------------------------------------------------------------------------
//  --> 4.1.1 updateText
//-------------------------------------------------------------------------------------
/**
 * updateText is a function that Updates all rows in the specified column with the specified text, --> (A) where the specified condition is true
 * :: --> ::NOTE 0: This method allows the user to specify the table where the update should occur in
 *      * --> Note 1: The column must already exist in the database specified by current JavaSqlCommunication instance.
 *      * --> Note 2: This method will NOT create a new row, HOWEVER it will update the value to an existing row
 * @param input_text The Text to be entered into the column
 * @param column_Name The Column-name where the Text will be inserted
 * @param tablename The name of the Table where the Text will be inserted
 * @param whereCondition The Condition that must be met for the update to occur in a specific row
 */
public void updateText(String input_text, String column_Name, String tablename, String whereCondition ) throws Exception{
    if(input_text == null)
        throw new IllegalStateException(" updateText: input_text is null");
    if(column_Name == null)
        throw new IllegalStateException(" updateText: column_Name is null");
    if(tablename == null)
        throw new IllegalStateException(" updateText: tablename is null");
    if(whereCondition == null)
        throw new IllegalStateException(" updateText: whereCondition is null");

    String var1 =   "\'" + input_text+ "\'" ;
    try (Connection con = getConnection(this.url+"");
         PreparedStatement posted = con.prepareStatement("UPDATE " + tablename + " SET " + column_Name + " = " + var1  +" WHERE " + whereCondition)
    ){
        posted.executeUpdate();
    }catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }//finally{
       // String shutdown = "jdbc:derby;:shutdown=true;";
        //DriverManager.getConnection(shutdown);
    //}
}


//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for Part 4 :  Update, where  <----- End.
//---------------------------------------------------------------------------------------------------------------------


//********************************************************************************************************************


//---------------------------------------------------------------------------------------------------------------------
// Methods: Part 5 : JavaSqlCommunication  --> Select  <-- operations, returns the datatype that asked for or object if requested a full row of data
//---------------------------------------------------------------------------------------------------------------------

//  --> 5.1.1 selectText
//-------------------------------------------------------------------------------------
/**
 * selectText is a function that returns a single row of text from the database, based specific table specified by current JavaSqlCommunication instance
 * @param column_name  The Column-name from where the value is selected
 * @param whereCondition The Condition that must be met for the select to occur in a specific row
 * @return Returns a single row (table-entry) of text
 */
public String selectText(String column_name, String whereCondition ) throws Exception
{
    return selectText(column_name, whereCondition, this.tableName);
}

//-----------------------------------------------------------------------------------------------------------------
//  --> 5.1.2 selectText
//-------------------------------------------------------------------------------------
/**
 * selectText is a function that returns a single row of text from the database specified by current JavaSqlCommunication instance,
 * NOTE: This method allows the user to specify the table where the text should be obtained from
 * @param column_name The Column-name from where the value is selected
 * @param table_name The name of the Table from where the value is selected
 * @param whereCondition The Condition that must be met for the select to occur in a specific row
 */
public String selectText(String column_name, String whereCondition, String table_name ) throws Exception
{
    if(column_name == null)
        throw new IllegalStateException("selectText: column_name is null");
    if(whereCondition == null)
        throw new IllegalStateException("selectText: whereCondition is null");
    if(table_name == null)
        throw new IllegalStateException("selectText: table_name is null");
    // --> must code in a unique method to make this possible
    // --> i.e to actually return all the data values tht are in a row of seperate columns ,
    // we could also ust print it as a string or return it all as a string
    if(column_name.compareToIgnoreCase("all") == 0 )
        column_name = "*";

    String text = null;
    try ( Connection con = getConnection(this.url+"");
          PreparedStatement posted = con.prepareStatement("SELECT " + column_name + " FROM " + table_name + " WHERE " + whereCondition);
          ResultSet temp = posted.executeQuery()
    ){
        temp.next();
        text =temp.getString(1);
    }catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }//finally{
       // String shutdown = "jdbc:derby;:shutdown=true;";
       // DriverManager.getConnection(shutdown);
    //}
    return text;
}
//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for Part 5 :  Select  <----- End.
//---------------------------------------------------------------------------------------------------------------------

//********************************************************************************************************************

//---------------------------------------------------------------------------------------------------------------------
// Methods: Part 6 : JavaSqlCommunication  --> useful functions  <-- operations
//---------------------------------------------------------------------------------------------------------------------

//  --> 6.1.1 addText
//-------------------------------------------------------------------------------------
/**
 * addText is a function that adds tet to an existing entry of text contianedd in a  in the specified column with the specified value, --> (A) where the specified condition is true
 * :: --> ::NOTE 0: This method allows the user to specify the table where the update should occur in
 *      * --> Note 1: The column must already exist in the database specified by current JavaSqlCommunication instance.
 *      * --> Note 2: This method will NOT create a new row, HOWEVER it will update the value to an existing row
 * @param input_value The Value to be entered into the column
 * @param column_Name The Column-name where the Text will be inserted
 * @param tablename The name of the Table where the Text will be inserted
 * @param whereCondition The Condition that must be met for the update to occur in a specific row
 */
public void addText(String input_value, String column_Name, String whereCondition, String tablename ) throws Exception{
    if(input_value == null)
        throw new IllegalStateException(" update: input_value is null");
    if(column_Name == null)
        throw new IllegalStateException(" update: column_Name is null");
    if(tablename == null)
        throw new IllegalStateException(" update: tablename is null");
    if(whereCondition == null)
        throw new IllegalStateException(" update: whereCondition is null");

    StringBuilder text = new StringBuilder();
    try(
            // part 1 --> get the value already in the box/location and add the new addition to the end
            Connection con = getConnection(this.url+"");
            PreparedStatement get = con.prepareStatement("SELECT " + column_Name + " FROM " + tablename + " WHERE " + whereCondition);
            ResultSet temp = get.executeQuery();
    ){
        temp.next();
        text.append(temp.getString(1));
        // ------>
        // ------>
        // ------>
        // ------> // ------>  // ------>
        ///   WARNING DO I NEED  To add THE COMMA (,)
        text.append(/*", " + */input_value);
        // ------> // ------> // ------>
        // ------>
        // ------>
        // ------>
        String var1 =   "\'" + text.toString()+ "\'" ;

        // part 2 --> update the var/box
        try(
                PreparedStatement posted = con.prepareStatement("UPDATE " + tablename + " SET " + column_Name + " = " + var1  +" WHERE " + whereCondition);
        ){
            posted.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }//finally{
        //String shutdown = "jdbc:derby;:shutdown=true;";
      //  DriverManager.getConnection(shutdown);
    //}
}




//-----------------------------------------------------------------------------------------------------------------
//  --> 6.2.1 isLocationEmpty
//-------------------------------------------------------------------------------------
/**
 * selectBlob is a function that returns a single Blob value from the database, based specific table specified by current JavaSqlCommunication instance
 * @param column_name The Column-name from where the value is selected
 * @param whereCondition The Condition that must be met for the select to occur in a specific row
 * @return Returns a single (table-entry) Blob value
 */
public boolean isLocationEmpty(String column_name, String whereCondition ) throws Exception
{
    return isLocationEmpty(column_name, whereCondition, this.tableName);
}

//-----------------------------------------------------------------------------------------------------------------
//  --> 6.2.2 isLocationEmpty
//-------------------------------------------------------------------------------------
/**
 * selectBlob is a function that returns a single Blob value from the database specified by current JavaSqlCommunication instance,
 * NOTE: This method allows the user to specify the table where the value should be obtained from
 * @param column_name The Column-name from where the value is selected
 * @param table_name The name of the Table from where the value is selected
 * @param whereCondition The Condition that must be met for the select to occur in a specific row
 * @return Returns a single (table-entry) Blob value
 */
public boolean isLocationEmpty(String column_name, String whereCondition, String table_name ) throws Exception {
    if(column_name == null)
        throw new IllegalStateException("isLocationEmpty: column_name is null");
    if(whereCondition == null)
        throw new IllegalStateException("isLocationEmpty: whereCondition is null");
    if(table_name == null)
        throw new IllegalStateException("isLocationEmpty: table_name is null");
    
    
    String blob = null;
    try( Connection con = getConnection(this.url+"");
    		PreparedStatement get = con.prepareStatement("SELECT " + column_name  + " FROM " + table_name + " WHERE " + whereCondition);
            ResultSet temp = get.executeQuery();
    ){
        temp.next();

        if(temp.getString(1)!=null)
            return false;
        else
            return true;
        
    }catch (SQLException ex) {
    	if( ex.getSQLState().contains("X05") ){
    		System.out.println("] connection failed");
    		setupdatabase();
    	}
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        return true;
    }
    //return true;
}


	


//---------------------------------------------------------------------------------------------------------------------
// End ----> Code for Part 6 :  useful functions  <----- End.
//---------------------------------------------------------------------------------------------------------------------




//********************************************************************************************************************


//---------------------------------------------------------------------------------------------------------------------
//Start ----> Code for Part 7 :  Datebase setup code <----- End.
//---------------------------------------------------------------------------------------------------------------------



//-----------------------------------------------------------------------------------------------------------------
//--> 7.0- addThreeYears(): adds three years of dates to the database -- will need to be updated in three years 
//-------------------------------------------------------------------------------------
public void  addThreeYears(String column_name, String table_name ) throws Exception{
	
	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
	
	LocalDate start = LocalDate.now();
	start = start.minusDays(30);
	LocalDate end = start.plusDays(1125);
	
	try( Connection con = getConnection(this.url+"");
		    ){
		System.out.println("Got commection now");
		 while (start.compareTo(end) <= 0) {
			 System.out.println(start);
			 start = start.plusDays(1);
			 PreparedStatement insertion = con.prepareStatement("INSERT INTO " + table_name  + " (" +column_name+ ") " +  "values (?)");
			 insertion.setString(1, start.toString());
			 insertion.executeUpdate();   
			 System.out.println("SUCESS");
			 }
	}catch (SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
	}
	
}

public void setupdatabase() throws Exception {
	
	// 1.0 Create Database 
	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	  String jdbcUrl ="jdbc:derby:Main_Database;create=true;"; 
	  Connection connection = DriverManager.getConnection(jdbcUrl);
	  System.out.println("Create-DataBase Successful");
	  
	  // 2.0 Create table
	  
	  String sql = "CREATE TABLE allarqs" +
	  "(id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
	  + " dailyArqs VARCHAR(150), " + "Date DATE)"; 
	  Statement statement = connection.createStatement(); 
	  statement.executeUpdate(sql);
	  System.out.print("table created");
	  
	  
	  //3.0 add three years JavaADCommunication com = new JavaADCommunication("jdbc:derby:testdb;create=true", "arqs_2021");
	  JavaADCommunication com = new JavaADCommunication(jdbcUrl, "allarqs");
	  com.addThreeYears("Date", "allarqs");
	  
	  
		  //String jdbcUrl ="jdbc:derby:Main_Database;create=true;"; 
		  //JavaADCommunication com = new JavaADCommunication(jdbcUrl, "allarqs");
		  //System.out.println(com.isLocationEmpty("dailyArqs", "(Date=" + "\'2021-07-30\')"));
	  
	  }
	


public void resetDatabase() throws Exception {
	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	  String jdbcUrl ="jdbc:derby:Main_Database;create=true;"; 
	  Connection connection = DriverManager.getConnection(jdbcUrl);
	  
	  String sql = "DELETE FROM allarqs";
	  Statement statement = connection.createStatement(); 
	  statement.executeUpdate(sql);
	  
	  JavaADCommunication com = new JavaADCommunication(jdbcUrl, "allarqs");
	  com.addThreeYears("Date", "allarqs");
	  
	  
}
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------

/*
 * 

//-------------------------------------------------------------------------------------------------------------------------------
// :::::: ---->  :  Main --> USED TO setup Apache derby database (currently set up to function until august 2024) <----- ::::::
//-------------------------------------------------------------------------------------------------------------------------------
  public static void main(String[] args) throws Exception {
  
	  
  // 1.0 Create Database 
  String jdbcUrl ="jdbc:derby:Main_Database;create=true;"; 
  Connection connection = DriverManager.getConnection(jdbcUrl);
  System.out.println("Create-DataBase Successful");
  
  // 2.0 Create table
  
  String sql = "CREATE TABLE allarqs" +
  "(id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
  + " dailyArqs VARCHAR(150), " + "Date DATE)"; 
  Statement statement = connection.createStatement(); 
  statement.executeUpdate(sql);
  System.out.print("table created");
  
  
  //3.0 add three years JavaADCommunication com = new JavaADCommunication("jdbc:derby:testdb;create=true", "arqs_2021");
  JavaADCommunication com = new JavaADCommunication(jdbcUrl, "allarqs");
  com.addThreeYears("Date", "allarqs");
  
  
	  //String jdbcUrl ="jdbc:derby:Main_Database;create=true;"; 
	  //JavaADCommunication com = new JavaADCommunication(jdbcUrl, "allarqs");
	  //System.out.println(com.isLocationEmpty("dailyArqs", "(Date=" + "\'2021-07-30\')"));
  
  }
 
 */

}
