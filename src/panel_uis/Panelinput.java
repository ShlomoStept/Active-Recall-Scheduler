package panel_uis;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import calendar.CalendarGUI;
import panel_functionality_classes.Input;

public class Panelinput extends JPanel implements ActionListener{
	private JTextField txtCurrentDayFor;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	
	protected CalendarGUI calender;
	protected Input in;
	
	/**
	 * Create the panel.
	 */
	public Panelinput(Input i) {
		
		
		/// 
		// 1.0 --> Instantiate global input Object
		in =i;
		//------------------------------------------------------------------------
		//// General panel Settings : part 1 :: 
		//------------------------------------------------------------------------
		setBorder(null);
		setBackground(new Color(255, 240, 245));
		setForeground(Color.DARK_GRAY);
		setBounds(0,0, 536, 482);
		setLayout(null);
		
		
		
		//-----------------------------------------------------------------------------------------------------------
		// Main Text fields: part 2 ::  Bottom
		//-----------------------------------------------------------------------------------------------------------
		
			//------------------------------------------------------------------------
				// 1.0 -->  Instructions
		//------------------------------------------------------------------------
		JFormattedTextField Instructions = new JFormattedTextField();
		Instructions.setHorizontalAlignment(SwingConstants.CENTER);
		//JLabel Instructions = new JLabel();
		//JLabel Instructions = new JLabel( "<html><span bgcolor=2582D2FF>This is the label text</span></html>" );
		Instructions.setFont(new Font("Monaco", Font.BOLD, 15));
		Instructions.setText("(1) Select Date, (2) Enter Arqs, and (3) Click \"Add\"");
		Instructions.setBackground(new Color(165,205,255));
		Instructions.setBorder(null);
		Instructions.setBounds(22, 16, 490, 46);
		
		add(Instructions);
		
		Box TopHorizontalLine = Box.createVerticalBox();
		TopHorizontalLine.setBackground(Color.GRAY);
		TopHorizontalLine.setBorder(new LineBorder(Color.GRAY, 2));
		TopHorizontalLine.setBounds(22, 74, 490, 2);
		add(TopHorizontalLine);
		
	
	
		
		
		//-----------------------------------------------------------------------------------------------------------
		// Main Text fields: part 2 ::  Format and pieces to the input UI 
		//-----------------------------------------------------------------------------------------------------------
		
		// 1.0 Calendar 
		//-----------------------------------------------------------------------------------------------------------
		calender = new CalendarGUI();
		JPanel calenderPanel = new JPanel();
		
		System.out.println(calenderPanel.getHeight());
		System.out.println(calenderPanel.getWidth());
		
		calenderPanel = calender.getFrame();
		calenderPanel.setBounds(20, 88, 330, 300);
		add(calenderPanel);
		
		
		
		// 2.0 basic boxes and springs for tracking dimensions
		//-----------------------------------------------------------------------------------------------------------
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(22, 112, 310, 280);
		add(horizontalBox);
		
		Box LineInMiddle = Box.createVerticalBox();
		LineInMiddle.setBorder(new LineBorder(Color.GRAY));
		LineInMiddle.setBounds(362, 85, 2, 305);
		add(LineInMiddle);
		
		
		// 3.0 JButton to apply the changes --> add/enter the new arqs 
		//-----------------------------------------------------------------------------------------------------------
		JButton getArqs = new JButton("Apply/Add - Arq Entries");
		getArqs.addActionListener(this);
		
		// TODO --> 
        // TODO --> 
        // TODO --> 
        // TODO -->  CREATE A MOUSE EVENT where
		// 1 -> it changes colors
		// 2 -> it sends the arqs back in a Array list 
		//applyChanges.setBorder(new LineBorder(new Color(154, 153, 149)));
		//getArqs.setBackground(new Color(154, 153, 149));
		
		// general background settings
		getArqs.setBackground(new Color(115, 154, 187));
		getArqs.setForeground(Color.BLACK);
		getArqs.updateUI();
		getArqs.setFont(new Font("Monaco", Font.BOLD, 15));
		getArqs.setBounds(90, 408, 350, 38);
		add(getArqs);
		
		
		// a: text field for "  Enter Arq's \n(One per Field)"
		JTextArea txtrEnterArqsone = new JTextArea();
		txtrEnterArqsone.setFont(new Font("Monaco", Font.PLAIN, 13));
		txtrEnterArqsone.setBackground(new Color(255, 240, 245));
		txtrEnterArqsone.setText("  Enter Arq's \n(One per Field)");
		txtrEnterArqsone.setBounds(385, 97, 122, 45);
		add(txtrEnterArqsone);
		
		
		// b: line under box
		Box LineUnderEnterArqs = Box.createVerticalBox();
		LineUnderEnterArqs.setBorder(new LineBorder(Color.GRAY, 2));
		LineUnderEnterArqs.setBackground(Color.GRAY);
		LineUnderEnterArqs.setBounds(372, 150, 142, 2);
		add(LineUnderEnterArqs);
		
		
		// C ----> the boxes to enter arqs into
		textField = new JTextField();
		textField.setFont(new Font("Futura", Font.PLAIN, 14));
		textField.setBackground(new Color(247, 255, 245, 255));
		textField.setBounds(375, 166, 140, 30);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Futura", Font.PLAIN, 14));
		textField_1.setBackground(new Color(247, 255, 245, 255));
		textField_1.setColumns(10);
		textField_1.setBounds(375, 204, 140, 30);
		add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Futura", Font.PLAIN, 14));
		textField_2.setBackground(new Color(247, 255, 245, 255));
		textField_2.setColumns(10);
		textField_2.setBounds(375, 280, 140, 30);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Futura", Font.PLAIN, 14));
		textField_3.setBackground(new Color(247, 255, 245, 255));
		textField_3.setColumns(10);
		textField_3.setBounds(375, 242, 140, 30);
		add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setFont(new Font("Futura", Font.PLAIN, 14));
		textField_4.setBackground(new Color(247, 255, 245, 255));
		textField_4.setBounds(375, 356, 140, 30);
		add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Futura", Font.PLAIN, 14));
		textField_5.setBackground(new Color(247, 255, 245, 255));
		textField_5.setColumns(10);
		textField_5.setBounds(375, 318, 140, 30);
		add(textField_5);
		
		
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//1.0 get the text field entries
		String arq1 = textField.getText();
		String arq2 = textField_1.getText();
		String arq3 = textField_2.getText();
		String arq4 = textField_3.getText();
		String arq5 = textField_4.getText();
		String arq6 = textField_5.getText();
		
		//2.0 --> add text field entries to string if they exist , and separate them with a comma (last entry must must end with a comma!!!!)
		String c = " ";
		if(!arq1.trim().isEmpty())
			c += arq1.toUpperCase() +", ";
		if(!arq2.trim().isEmpty())
			c += arq2.toUpperCase() +", ";
		if(!arq3.trim().isEmpty())
			c += arq3.toUpperCase() +", ";
		if(!arq4.trim().isEmpty())
			c += arq4.toUpperCase() +", ";
		if(!arq5.trim().isEmpty())
			c += arq5.toUpperCase() +", ";
		if(!arq6.trim().isEmpty())
			c += arq6.toUpperCase() +", ";
		
		//3.0 reset the text field entries to be blank
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
		
		//4.0 add the string to the database arq -slot for the current input date
		try {
			in.updateDatabase(c, calender.inputDate);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
}
