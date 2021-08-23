package panel_uis;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextPane;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.border.LineBorder;

import panel_functionality_classes.Output;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class PanelToday extends JPanel {

	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	public PanelToday(Output out) throws Exception {
		setBorder(null);
		setBackground(new Color(255, 240, 245));
		setForeground(Color.DARK_GRAY);
		setBounds(0,0, 536, 482);
		setLayout(null);
		
		
		
		//-----------------------------------------------------------------------------------------------------------
		// Main Text fields: part 2 ::  Bottom
		//-----------------------------------------------------------------------------------------------------------
		
			//------------------------------------------------------------------------
				// 1.0 -->  Welcome ___ First Name ___ LastName 
		//------------------------------------------------------------------------
		JFormattedTextField Welcome = new JFormattedTextField();
		Welcome.setHorizontalAlignment(SwingConstants.LEFT);
		Welcome.setBackground(new Color(165,205,255));
		Welcome.setFont(new Font("Bangla MN", Font.BOLD, 22));
		//Welcome.setHorizontalAlignment(SwingConstants.LEFT);
		//Welcome.setAlignmentY(SwingConstants.BOTTOM);
		Welcome.setText("  Hello, " );  // Welcome.setText("  Hello,  " + out.getUser().getFirstName() );  --> not customized yet for gettign the users name
		Welcome.setBorder(null);
		Welcome.setBounds(25, 27, 200, 35);
		
		add(Welcome);
		

		
			//------------------------------------------------------------------------
				// 2.0 -->  Today is 
		//------------------------------------------------------------------------
		
		JLabel todayis = new JLabel();
		todayis.setBackground(new Color(255, 240, 245));
		todayis.setFont(new Font("Monaco", Font.BOLD, 17));
		// Get today and format it in the form --> Wednesday, April 3, 2019.  ::--::  (4/3/2019)
		LocalDate today = out.getToday();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
		LocalDate shorttoday = out.getToday();
		String shortday = shorttoday.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		todayis.setText("Today is : "+formattedDate + ". (" + shortday+ ")");
		todayis.setHorizontalAlignment(SwingConstants.CENTER);
		todayis.setBorder(null);
		todayis.setBounds(25, 73, 495, 38);
		add(todayis);
		
		
		//------------------------------------------------------------------------
		// 3.0  --> THe DAtes andt the Lists of the arqs associated with each day in the arq schedule 
		//------------------------------------------------------------------------
		//1.0 Lists of the arqs associated with each day in the arq schedule 
		String day4 = out.getPreviousDates().get(3).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		String day3 = out.getPreviousDates().get(2).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		String day2 = out.getPreviousDates().get(1).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		String day1 = out.getPreviousDates().get(0).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
		ArrayList<String> list1 = out.getArqsforOneDate(out.getPreviousDates().get(3));
		ArrayList<String> list2 = out.getArqsforOneDate(out.getPreviousDates().get(2));
		ArrayList<String> list3 = out.getArqsforOneDate(out.getPreviousDates().get(1));
		ArrayList<String> list4 = out.getArqsforOneDate(out.getPreviousDates().get(0));
		
		
		//-----------------------------------------------------------------------------------------------------------
		// Main Text fields: part 2 ::  Bottom
		//-----------------------------------------------------------------------------------------------------------
		
		// a: text field for "Dates:"
		JLabel right = new JLabel();
		right.setBackground(new Color(255, 240, 245));
		right.setFont(new Font("Monaco", Font.PLAIN, 17));
		right.setText("Dates:  ");
		right.setHorizontalAlignment(SwingConstants.CENTER);
		right.setBorder(null);
		right.setBounds(43, 161, 130, 35);
		add(right);
		
		
		// b: text field for "Active Recall Questioneers:"
		JLabel left = new JLabel();
		left.setHorizontalAlignment(SwingConstants.RIGHT);
		left.setBackground(new Color(255, 240, 245));
		left.setFont(new Font("Monaco", Font.PLAIN, 17));
		left.setText("Active Recall Questioneers\n");
		left.setBorder(null);
		left.setBounds(225, 161, 280, 35);
		add(left);
		
		
		// c: text pane for "Dates:"
		JTextPane rightdates = new JTextPane();
		rightdates.setBackground(new Color(255, 240, 245));
		rightdates.setFont(new Font("Monaco", Font.PLAIN, 15));
		rightdates.setText( "(1) For: " + day4 +
							"\n(2) For: " + day3  +
							"\n(3) For: " + day2  +
							"\n(4) For: " + day1  
						  );
		rightdates.setBorder(null);
		rightdates.setBounds(48, 210, 158, 184);
		add(rightdates);
		
		
		// a: text pane for "Active Recall study sheets:"
		JTextPane leftarqs = new JTextPane();
		leftarqs.setBackground(new Color(255, 240, 245));
		leftarqs.setFont(new Font("Monaco", Font.PLAIN, 15));
		leftarqs.setText(   "(1) " + out.ArraytoString(list1) +"\n"+
							"(2) "+out.ArraytoString(list2) +"\n"+
							"(3) "+out.ArraytoString(list3) +"\n"+
							"(4) "+out.ArraytoString(list4) 
						);
		leftarqs.setBorder(null);
		leftarqs.setBounds(232, 210, 272, 184);
		add(leftarqs);
		
		Box middle_seperator = Box.createVerticalBox();
		middle_seperator.setBorder(new LineBorder(Color.GRAY, 2));
		middle_seperator.setBounds(210, 165, 2, 214);
		add(middle_seperator);
		
		Box top_horizontal_line = Box.createVerticalBox();
		top_horizontal_line.setBackground(Color.GRAY);
		top_horizontal_line.setBorder(new LineBorder(Color.GRAY, 2));
		top_horizontal_line.setBounds(23, 115, 498, 2);
		add(top_horizontal_line);
		
	
		Box bottom_horizontal_line = Box.createVerticalBox();
		bottom_horizontal_line.setBorder(new LineBorder(Color.GRAY, 2));
		bottom_horizontal_line.setBounds(43, 196, 461, 2);
		add(bottom_horizontal_line);
		
		
		

	}
}
