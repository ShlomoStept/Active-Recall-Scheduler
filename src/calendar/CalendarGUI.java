package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.StyleContext;

//------------------------------------------------------------------------------------------------------------------------------------------------
//CALENDERGUI :: START :: ---->>>
//------------------------------------------------------------------------------------------------------------------------------------------------
	
	public class CalendarGUI implements Runnable {
	  

		//---------------------------------------------------------------------------------------------------------------------
//			   COLORS AND FONT SETTINGS CAN BE CHANGED
		//---------------------------------------------------------------------------------------------------------------------


		    //-->>> //-->>> //-->>>
		    // A. COLORS --> can be changed
		    //-->>> //-->>> //-->>>
		    Color mainpanel = new Color(37, 130, 210);
		    Color salmond = new Color(255, 240, 245);
		    Color green =new Color(247, 255, 245, 255);
		    Color matteblue = new Color(233, 248, 253, 255);
		    Color mattegreen = new Color(151, 236, 164, 255);
		    Color highlightblue = new Color(165,205,255);
		    //-->>> //-->>> //-->>> //-->>> //-->>> //-->>>
		    //-->>> //-->>> //-->>> //-->>> //-->>> //-->>>
		    private Color backgroundColor = green;
		    private Color FrameBoarderColor = green;
		    private Color DateLabelBackgorund = highlightblue;
		    private Color dateBoxBackground =green;
		    private Color dateBoxBoarder = matteblue;
		    private Color calendarBackground = salmond;
		    private Color clickHighlight = mattegreen;
		    private Color selectedDateColor = highlightblue;
		    //-->>>//-->>> //-->>> //-->>> //-->>> //-->>> //-->>>
		    //-->>> //-->>> //-->>> //-->>> //-->>>
		    //-->>> //-->>> //-->>>


		    //-->>> //-->>> //-->>>
		    // B. FONTS --> can be changed
		    //-->>> //-->>> //-->>>
		    StyleContext s = new StyleContext();

		    Font font = s.getFont( "Monaco", Font.PLAIN, 12); // GENERAL FONT USED IN MOST LOCATIONS
		    Font dayNamesFont = s.getFont( "Monaco", Font.PLAIN, 11);
		    Font dayNumberFont = s.getFont( "Monaco", Font.BOLD, 14);
		    Font updateDateTitleFont = s.getFont( "Monaco", Font.BOLD, 15);


		    //-->>> //-->>> //-->>>
		    // B. COMPONENT SIZE --> The Size of each component can be changed by altering the following sizes
		    //-->>> //-->>> //-->>>
		    Dimension monthComboBoxSize = new Dimension(110, 23); // Month dropdown menu on top panel
		    Dimension prevMonthButtonSize = new Dimension(100, 23);
		    Dimension nextMonthButtonSize = new Dimension(100, 23);
		    Dimension weekdayNamesPanelSize = new Dimension(40, 20);
		    Dimension dayNumberBoxSize = new Dimension(40, 40);
		    int FrameHightSize = 308;
		    int FrameWidthSize = 278;




		    //------------------------------------------------------------------------
		    // 1.0 -> Main data fields :: DO NOT CHANGE ANY SETTINGs
		    //-----------------------------------------------------------------------

		    private static final String[] DAY_NAMES = {"Sun", "Mon", "Tues",
		            "Wedn", "Thur", "Fri", "Sat"};
		    private static final String[] MONTH_NAMES = {"January", "February",
		            "March", "April", "May", "June", "July",
		            "August", "September", "October", "November", "December"};

		    private JComboBox<String> monthComboBox;
		    private JLabel[][] dayLabel;
		    private JLabel titleLabel;

		    private JTextField dayField;
		    private JTextField yearField;
		    private LocalDate calendarDate;
		    public LocalDate inputDate;
		    public int oldj;
		    public int oldi;

		    private static JPanel frame;
		    JButton nextMonthButton;
		    JButton previousMonthButton;

		    // Getter for the frame --->> USE IN YOUR APPLICATIONS MAIN FUNCTION TO GET THE CAlendars Panel
		    public JPanel getFrame() {
		        return frame;
		    }




		    //------------------------------------------------------------------------
		    // 2.0 --> ::: CONSTRUCTOR :::
		    //-----------------------------------------------------------------------
		    public CalendarGUI(){
		        this.calendarDate = LocalDate.now();
		        this.inputDate = calendarDate;
		        run();

		    }

		    //------------------------------------------------------------------------
		    // 3.0 ->  Run function
		    //-----------------------------------------------------------------------
		    @Override
		    public void run() {

		        //  --> 3.1.0: getTodaysDate
		        //-------------------------------------------------------------------------------------e
		        ZoneId timezone = ZoneId.of("America/New_York");
		        inputDate = LocalDate.now(timezone);

		        // --> 3.2.0 Print out
		        //------------------------------------------------------------
		        //System.out.println("Input Date is :  " + inputDate );


		        //------------------------------------------------------------------------
		        // --> 3.3.0  Frame creation and Initialization
		        //-----------------------------------------------------------------------
		        frame = new JPanel(new BorderLayout());

		        //***************************************************************************************************
		        //	KEY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		        //-->>> //-->>> //-->>>
		        //-->>> //-->>> //-->>> //-->>> //-->>>
		        //-->>> //-->>> //-->>> //-->>> //-->>> //-->>> //-->>>
		        frame.setBounds(1, 1, FrameHightSize , FrameWidthSize );
		        //-->>>//-->>> //-->>> //-->>> //-->>> //-->>> //-->>>
		        //-->>> //-->>> //-->>> //-->>> //-->>>
		        //-->>> //-->>> //-->>>
		        //***************************************************************************************************


		        // creation/invoking the topPanel and calendar panel creation
		        frame.add(createTopPanel(), BorderLayout.BEFORE_FIRST_LINE);
		        frame.add(createCalendarPanel(), BorderLayout.CENTER);
		        frame.setBorder(BorderFactory.createLineBorder(FrameBoarderColor,2));
		        frame.setVisible(true);
		    }



		//---------------------------------------------------------------------------------------------------------------------
//		    ::: START PART A ----> :::::: PART A: Top Panel for the calendar
		//---------------------------------------------------------------------------------------------------------------------


		    //------------------------------------------------------------------------
		    // 4.0 -> createTopPanel(): creates the top panel (<month, [month selection], month>) and the DATE: mm/dd/yyyy below it
		    //-----------------------------------------------------------------------
		    private JPanel createTopPanel() {
		        JPanel panel = new JPanel(new BorderLayout());
		        panel.add(createDatePanel(), BorderLayout.BEFORE_FIRST_LINE);
		        panel.add(createTitlePanel(), BorderLayout.AFTER_LAST_LINE);
		        return panel;
		    }



		    //------------------------------------------------------------------------
		    // 5.0 -> createDatePanel(): creates the top panel to the calendar with the (<month, [month selection], month>)
		    //-----------------------------------------------------------------------
		    private JPanel createDatePanel() {
		        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));


		        // A-- General panel settings setup
		        panel.setBackground(calendarBackground);


		        //****------------------------------------------------------------------
		        // 5.1.0 -> Initializing the month drop-down menu, and date tracking used in other parts of the program
		        //****--------------------------------------------------------------------

		        // :::: WARNING THIS IS A USEFULL PART OF THE PROGRAM --> I<E KEEP THESE FEILDS ::: HOWEVER ----> DO NOT ADD FEILDS TO THE CALENDAR
		        monthComboBox = new JComboBox<>(MONTH_NAMES);
		        monthComboBox.setEditable(false);
		        monthComboBox.setPreferredSize(monthComboBoxSize);
		        monthComboBox.setFont(font);
		        monthComboBox.setSelectedIndex(calendarDate.getMonth().ordinal());


		        dayField = new JTextField(2);
		        dayField.setFont(font);
		        dayField.setText(Integer.toString(calendarDate.getDayOfMonth()));


		        yearField = new JTextField(4);
		        yearField.setFont(font);
		        yearField.setText(Integer.toString(calendarDate.getYear()));



		        //****------------------------------------------------------------------
		        // 5.2.0 -> Previous MONTH button
		        //****--------------------------------------------------------------------
		        previousMonthButton = new JButton("< Month");
		        previousMonthButton.setPreferredSize(prevMonthButtonSize);
		        previousMonthButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		                int month = monthComboBox.getSelectedIndex();
		                month--;
		                if (month < 0) {
		                    int year = Integer.valueOf(yearField.getText()) - 1;
		                    yearField.setText(Integer.toString(year));
		                    month = 11;
		                }
		                monthComboBox.setSelectedIndex(month);
		                updateDayLabels();
		            }
		        });

		        //****------------------------------------------------------------------
		        // 5.3.0 -->NEXT MONTH Button
		        //****-------------------------------------------------------------------
		        nextMonthButton = new JButton("Month >");
		        nextMonthButton.setPreferredSize(nextMonthButtonSize);
		        nextMonthButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent event) {
		                int month = monthComboBox.getSelectedIndex();
		                month++;
		                if (month > 11) {
		                    int year = Integer.valueOf(yearField.getText()) + 1;
		                    yearField.setText(Integer.toString(year));
		                    month = 0;
		                }
		                monthComboBox.setSelectedIndex(month);
		                updateDayLabels();
		            }
		        });


		        // -->  ADD THE BUTTONS  (<month, [month selection], month>):(5.1.0, 5.2.0, 5.3.0) to the Top date panel
		        //****-------------------------------------------------------------------------------------

		        panel.add(previousMonthButton);
		        panel.add(monthComboBox);
		        panel.add(nextMonthButton);
		        return panel;
		    }



		    //------------------------------------------------------------------------
		    // 6.0 --> createTitlePanel: creates the DATE: dd/mm/yy title in the top panel
		    //------------------------------------------------------------------------
		    private JPanel createTitlePanel() {
		        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		        titleLabel = new JLabel(" ");

		        titleLabel.setFont(font);
		        panel.setBackground(selectedDateColor);

		        panel.add(titleLabel);
		        return panel;
		    }


		//---------------------------------------------------------------------------------------------------------------------
//			    ::: END PART A ----> :::::: PART A: Top Panel for the calendar
		//---------------------------------------------------------------------------------------------------------------------


		//**********************************************************************************************************************************


		//---------------------------------------------------------------------------------------------------------------------
//			    ::: START PART B ----> :::::: PART B: the core of the calendar, (1) date boxes and (2) day names
		//---------------------------------------------------------------------------------------------------------------------



		    //------------------------------------------------------------------------
		    // 7.0 --> createCalendarPanel()
		    //------------------------------------------------------------------------
		    private JPanel createCalendarPanel() {
		        JPanel panel = new JPanel(new BorderLayout());
		        panel.setBackground(calendarBackground);

		        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		        // TEST 2 --> KEY PARTS
		        panel.add(createWeekdayLabels(), BorderLayout.BEFORE_FIRST_LINE);
		        panel.add(createDayLabels(), BorderLayout.CENTER);

		        updateDayLabels();
		        return panel;
		    }


		    //------------------------------------------------------------------------
		    // 8.0 --> createWeekdayLabels()
		    //------------------------------------------------------------------------
		    private JPanel createWeekdayLabels() {

		        JPanel panel = new JPanel(new GridLayout(0, DAY_NAMES.length, 5, 5));
		        panel.setBackground(calendarBackground);


		        for (int i = 0; i < DAY_NAMES.length; i++) {
		            JPanel dayPanel = new JPanel(new BorderLayout());
		            dayPanel.setPreferredSize(weekdayNamesPanelSize);
		            JLabel label = new JLabel(DAY_NAMES[i]);

		            dayPanel.setBackground(calendarBackground);
		            label.setFont(dayNamesFont);


		            label.setHorizontalAlignment(JLabel.CENTER);
		            dayPanel.add(label, BorderLayout.CENTER);
		            panel.add(dayPanel);
		        }
		        return panel;
		    }



		    //------------------------------------------------------------------------
		    // 9.0 --> createDayLabels() :: creates each individual day box setup and
		    //------------------------------------------------------------------------

		    private JPanel createDayLabels() {
		        //1.0 Overall panel settings
		        JPanel panel = new JPanel(new GridLayout(0, DAY_NAMES.length, 5, 5));
		        panel.setBackground(calendarBackground);
		        dayLabel = new JLabel[6][DAY_NAMES.length];


		        // 2.0 settings for each box
		        for (int j = 0; j < dayLabel.length; j++) {
		            for (int i = 0; i < dayLabel[j].length; i++) {
		                JPanel dayPanel = new JPanel(new BorderLayout());

		                // CHanged the boarder for the small boxes
		                dayPanel.setBorder(BorderFactory.createLineBorder(dateBoxBoarder, 2));
		                dayPanel.setPreferredSize(dayNumberBoxSize);


		                dayLabel[j][i] = new JLabel(" ");
		                dayLabel[j][i].setFont(dayNumberFont);
		                dayLabel[j][i].setHorizontalAlignment(JLabel.CENTER);


		                dayPanel.add(dayLabel[j][i], BorderLayout.CENTER);
		                panel.add(dayPanel);

		            }
		        }
		        return panel;
		    }



		    //------------------------------------------------------------------------
		    // 10.1.0 --> updateDayLabels(Boolean b) --> use when updating the value of calendar date
		    // Ensures that a loop does not occur with updateDayLabels-> calling the function fill-days which just called -> this updateDayLabels function
		    //------------------------------------------------------------------------
		    public void updateDayLabels(Boolean b) {
		        if(b) {
		            int month = monthComboBox.getSelectedIndex();
		            int day = valueOf(dayField.getText().trim());
		            int year = valueOf(yearField.getText().trim());
		            if (year > 0 && day > 0) {

		                calendarDate = LocalDate.of(year, month + 1, day); // --> month is 1 off
		                LocalDate monthDate = getPreviousSunday(year, month);
		                fillDays(monthDate, year, month, day, false);


		                // Changes the date title so that the current day is updated appropriately
		                String title = " Date: " + inputDate.getMonthValue() +
		                        "/" + inputDate.getDayOfMonth() + "/" + inputDate.getYear();
		                titleLabel.setText(title);
		                titleLabel.setFont(updateDateTitleFont);

		                //TODO ___>>>>>>
		                /// I WANT IT TO FLASH GREEN AND PUTPLE BEFORE IT GOES BACK TO BLACK SEE IF I CAN CODE THAT

		            }
		        }
		    }

		    //------------------------------------------------------------------------
		    // 10.2.0 --> updateDayLabels(): this orginal function is the parent function to the helper above
		    //------------------------------------------------------------------------
		    public void updateDayLabels() {
		        int month = monthComboBox.getSelectedIndex();
		        int day = valueOf(dayField.getText().trim());
		        int year = valueOf(yearField.getText().trim());

		        if (year > 0 && day > 0) {
		            calendarDate = LocalDate.of(year, month + 1, day);
		            LocalDate monthDate = getPreviousSunday(year, month);
		            fillDays(monthDate, year, month, day,true);

		            // Changes the date title so that the current day is updated appropriately
		            String title = " Date: " + inputDate.getMonthValue() +
		                    "/" + inputDate.getDayOfMonth() + "/" + inputDate.getYear();
		            titleLabel.setText(title);
		            titleLabel.setFont(updateDateTitleFont);

		        }
		    }

		//---------------------------------------------------------------------------------------------------------------------------


		    //------------------------------------------------------------------------
		    // 11.0 --> getPreviousSunday ;; --> Helper function for the updateDayLables functions
		    //------------------------------------------------------------------------
		    private LocalDate getPreviousSunday(int year, int month) {
		        LocalDate monthDate = LocalDate.of(year, month + 1, 1);
		        int weekday = monthDate.getDayOfWeek().ordinal();
		        if (weekday < 6) {
		            monthDate = monthDate.minusDays((long) (weekday + 1));
		        }
		        return monthDate;
		    }


		    //------------------------------------------------------------------------
		    // 12.0 --> valueOf:: helper function that converts a string to an integer
		    //------------------------------------------------------------------------
		    public int valueOf(String text) {
		        try {
		            return Integer.valueOf(text);
		        } catch (NumberFormatException e) {
		            return -1;
		        }
		    }


		//---------------------------------------------------------------------------------------------------------------------------


		    //------------------------------------------------------------------------
		    // 13.0 -->  fillDays : fills the calendar days with appropriate value and keeps a mouse listener by each location
		    //------------------------------------------------------------------------

		    private void fillDays(LocalDate monthDate, int year, int month, int day, boolean b) {
		        if(b) {
		            for (int j = 0; j < dayLabel.length; j++) {
		                for (int i = 0; i < dayLabel[j].length; i++) {
		                    int calMonth = monthDate.getMonth().ordinal();
		                    int calYear = monthDate.getYear();

		                    dayLabel[j][i].getParent().setBackground(backgroundColor);
		                    dayLabel[j][i].setText(" ");

		                    if (year == calYear && month == calMonth) {
		                        int calDay = monthDate.getDayOfMonth();

		                        // KEY !!!!!!!!!!!!!!!!!!!!!!!!!!
		                        dayLabel[j][i].setText(Integer.toString(calDay));

		                        // Make the selected day background color blue
		                        if (calDay == day || inputDate.getDayOfMonth()+1 == day) {
		                            dayLabel[j][i].getParent().setBackground(selectedDateColor);
		                            oldj = j;
		                            oldi =i;
		                        }
		                    }

		                    // Must create new variables for them to be valid in the mouselistener scope
		                    int finalI = i;
		                    int finalJ = j;
		                    int finalcalYear = calYear;
		                    int finalcalMonth = calMonth;

		                    dayLabel[j][i].addMouseListener(new MouseAdapter() {


		                        //------------------------------------------------------------------------
		                        // 12.1.0 --> mousePressed --> changes colors and updates the input date with the selected date
		                        //------------------------------------------------------------------------
		                        @Override
		                        public void mousePressed(MouseEvent e) {
		                            int id = Integer.parseInt(dayLabel[finalJ][finalI].getText());
		                            inputDate = LocalDate.of(finalcalYear + 0, finalcalMonth + 1, id + 0);

		                            // --> PRINT OUTS
		                            //------------------------------------------------------------
		                            //System.out.println("Yay you clicked me");
		                            //System.out.println("INPUT DATE IS::" + inputDate);
		                            //System.out.println(id);

		                            // --> KEY
		                            //------------------------------------------------------------
		                            // THIS CALLS FILLDAYS WHICH IN TURN CALLS UPDATEDAYLABELS SO ITS A LOOP
		                            dayLabel[oldj][oldi].getParent().setBackground(backgroundColor);
		                            dayLabel[finalJ][finalI].getParent().setBackground(  clickHighlight );

		                            oldj = finalJ;
		                            oldi = finalI;

		                            // KEY!!!! == calls the version of updateDayLAbels that doesn't recursively call the Filldays()
		                            //   function when it receives the true boolean function,
		                            //   Because this fields function sets the fillDays() boolean value as false in this case
		                            updateDayLabels(true);

		                        }
		                        @Override
		                        public void mouseReleased(MouseEvent E) {
		                            dayLabel[finalJ][finalI].getParent().setBackground(DateLabelBackgorund);
		                        }
		                    });

		                    monthDate = monthDate.plusDays(1L);
		                }
		            }
		        }
		    }



}