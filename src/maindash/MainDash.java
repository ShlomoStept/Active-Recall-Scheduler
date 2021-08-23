package maindash;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;

import panel_functionality_classes.Input;
import panel_functionality_classes.Output;
import panel_functionality_classes.User;
import panel_uis.PanelEdit;
import panel_uis.PanelToday;
import panel_uis.Panelinput;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MainDash extends JFrame {
	
	private static User me;
	private static Output out;
	private static Input in;
	
	private static JFrame frame1;
	private JPanel contentPane;
	private JTextArea txtWelcomeToDaily;
	
	private PanelToday panelToday;
	private Panelinput panelInput;
	private PanelEdit panelEdit;
	
	 //----------------->
	 //-----------------> //----------------->            Main COLORS I used 
	 //-----------------> //-----------------> //----------------->
	 Color mainpnnel = new Color(37, 130, 210);
	 Color salmond = new Color(255, 240, 245);
	 Color green =new Color(247, 255, 245, 255);
	 Color matteblue = new Color(233, 248, 253, 255);
	 Color mattegreen = new Color(151, 236, 164, 255);
	 Color highlightblue = new Color(165,205,255);
	 //-----------------> //-----------------> //----------------->
	 //-----------------> //----------------->
	 //----------------->

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 1.0 -> Create the outer box/ window
					frame1 = new JFrame("Active Recall App");
					frame1.setBounds(100, 100, 800, 494);
					frame1.setVisible(true);
					frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					
					
					
					// ------> 
			        // ------> // ------>  // ------> 
					// MUST INITALIZE YOUR FIRSTNAME, LASTNAME, THE DATABASE URL, THE DATABASE PASSWORD, THE DATABASE USERNAME, THE DATBASE NAME, AND THE TABLENAME
					// IF you used the script on a already create database then it should be easier. 
			        // ------> // ------> // ------> 
			        // ------> 
					
					// 2.0 -> Now initialize the the user, the output the input and in the future the edit objects, and make the print out dynamic --> print change depeing on presence of the 
					
					me = new User("Best", "Student", "jdbc:derby:Main_Database;create=true;",
					         "N/A", "user", "Main_Database", "allarqs");
					out = new Output(me);
					in = new Input(me);
					
					
					
					// 3.0 -> Now we include the MainDash panel to the window frame
					MainDash frame = new MainDash();
					
					// THERES was a problem but its fixed --> you can't add a frame to a frame --> but you can an a JPanel to the JFrame so i added frame.contentPane
					frame1.getContentPane().add(frame.contentPane);
					//frame.setVisible(true); /// this displays the pane in but by itself not inside the window 
					frame1.setVisible(true);
					
				} catch (Exception e) {
					try {
						DriverManager.getConnection("jdbc:derby:;shutdown=true");
					} catch (SQLException e1) {
						System.out.println("help");
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public MainDash() throws Exception {
		
		//---------------------------------------------------------------------------------------------------------------
		// 1 - General frame settings
		//--------------------------------------------------------------------------------------------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 494);
		setUndecorated(true);
		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(salmond);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		frame1.getContentPane().add(contentPane);
		
		
		//---------------------------------------------------------------------------------------------------------------
		// 2 - Menu panel
		//---------------------------------------------------------------------------------------------------------------
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(mainpnnel);
		panelMenu.setBounds(0, 0, 252, 494);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		
		//---------------------------------------------------------------------------------------------------------------
		// 3 - All other panels
		//---------------------------------------------------------------------------------------------------------------
		panelToday = new PanelToday(out);
		panelInput = new Panelinput(in);
		panelEdit = new PanelEdit(me);
				//---------------------------------------------------------------------------------------------------------------
				// Add and setup the middle pannel that adapts when a different menu item is selected
				//---------------------------------------------------------------------------------------------------------------
				
				
				JPanel panel_changing_content = new JPanel();
				panel_changing_content.setBounds(258, 6, 536, 482);
				contentPane.add(panel_changing_content);
				panel_changing_content.setLayout(null);
				
				panel_changing_content.add(panelToday);
				panel_changing_content.add(panelInput);
				panel_changing_content.add(panelEdit);
				
				menuClicked(panelToday);
		
		
	
		
		//---------------------------------------------------------------------------------------------------------------
		// 4 - Todays arqs button settings -- on the Menu panel
		//---------------------------------------------------------------------------------------------------------------
		JPanel todaysArqsPanel = new JPanel();
		todaysArqsPanel.setBorder(new LineBorder(salmond , 1, true));
		todaysArqsPanel.addMouseListener(new PanelButtonMouseAdapter(todaysArqsPanel) {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelToday);
			}
		});
		todaysArqsPanel.setBackground(mainpnnel);
		todaysArqsPanel.setForeground(mainpnnel);
		todaysArqsPanel.setBounds(-1, 175, 254, 50);
		//todaysArqsPanel.setBounds(2, 175, 250, 50);
		panelMenu.add(todaysArqsPanel);
		todaysArqsPanel.setLayout(null);
		
		JLabel todaysArqs = new JLabel("Today's Arq's");
		todaysArqs.setFont(new Font("Monaco", Font.BOLD, 18));
		todaysArqs.setHorizontalAlignment(SwingConstants.LEFT);
		todaysArqs.setForeground(salmond );
		todaysArqs.setBounds(70, 6, 159, 38);
		todaysArqsPanel.add(todaysArqs);
		
		  // --> ICON 
	    
		JLabel todaysarqicon = new JLabel();
		
		ImageIcon imageIcon = new ImageIcon("/Users/shlomostept/Desktop/Arq_App/arqApp/Eclipse/arqdash/ARQDash/src/res/cal1.png"); 
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		todaysarqicon.setIcon(imageIcon);
		
		
		todaysarqicon.setBounds(18, 5, 40, 40);
		todaysArqsPanel.add(todaysarqicon);
		
		//---------------------------------------------------------------------------------------------------------------
		
		
		//---------------------------------------------------------------------------------------------------------------
		// 5 - Add New arqs button settings -- on the Menu panel
		//---------------------------------------------------------------------------------------------------------------
				
		JPanel addNewArqsPanel = new JPanel();
		addNewArqsPanel.setBorder(new LineBorder(salmond , 1, true));
		addNewArqsPanel.addMouseListener(new PanelButtonMouseAdapter(addNewArqsPanel){
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelInput);
			}
		});
		addNewArqsPanel.setBackground(mainpnnel);
		addNewArqsPanel.setForeground(mainpnnel);
		addNewArqsPanel.setBounds(-1, 224, 254, 50);
		//addNewArqsPanel.setBounds(2, 224, 250, 50);
		panelMenu.add(addNewArqsPanel);
		addNewArqsPanel.setLayout(null);
		
		JLabel addNewArqs = new JLabel("Add New Arqs");
		addNewArqs.setFont(new Font("Monaco", Font.BOLD, 18));
		addNewArqs.setHorizontalAlignment(SwingConstants.LEFT);
		addNewArqs.setForeground(salmond );
		addNewArqs.setBounds(70, 6, 170, 38);
		addNewArqsPanel.add(addNewArqs);
		
		JLabel todaysarqicon_1 = new JLabel();
		ImageIcon imageIcon2 = new ImageIcon("/Users/shlomostept/Desktop/Arq_App/arqApp/Eclipse/arqdash/ARQDash/src/res/plus2.png"); 
		Image image2 = imageIcon2.getImage(); // transform it 
		Image newimg2 = image2.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon2 = new ImageIcon(newimg2);  // transform it back
		
		todaysarqicon_1.setIcon(imageIcon2);
		
		todaysarqicon_1.setBounds(18, 5, 40, 40);
		addNewArqsPanel.add(todaysarqicon_1);
		
		//---------------------------------------------------------------------------------------------------------------
		
		
		//---------------------------------------------------------------------------------------------------------------
		// 6 - Edit arqs button settings -- on the Menu panel
		//---------------------------------------------------------------------------------------------------------------
				
		JPanel editArqsPanel = new JPanel();
		editArqsPanel.setBorder(new LineBorder(salmond, 1, true));
		editArqsPanel.addMouseListener(new PanelButtonMouseAdapter(editArqsPanel){
			@Override
			public void mouseClicked(MouseEvent e) {
				menuClicked(panelEdit);
			}
		});
		editArqsPanel.setBackground(mainpnnel );
		editArqsPanel.setForeground(mainpnnel );
		editArqsPanel.setBounds(-1, 273, 254, 50);
		//editArqsPanel.setBounds(2, 273, 250, 50);
		panelMenu.add(editArqsPanel);
		editArqsPanel.setLayout(null);
		
		JLabel editArqs = new JLabel("Edit Arqs");
		editArqs.setFont(new Font("Monaco", Font.BOLD, 18));
		editArqs.setHorizontalAlignment(SwingConstants.LEFT);
		editArqs.setForeground(salmond);
		editArqs.setBounds(70, 6, 150, 38);
		editArqsPanel.add(editArqs);
		
		JLabel todaysarqicon_2 = new JLabel();
		ImageIcon iI3 = new ImageIcon("/Users/shlomostept/Desktop/Arq_App/arqApp/Eclipse/arqdash/ARQDash/src/res/pen.png"); 
		Image image3 = iI3.getImage(); // transform it 
		Image newimg3 = image3.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		iI3 = new ImageIcon(newimg3);  // transform it back
		
		todaysarqicon_2.setIcon(iI3);
		
		todaysarqicon_2.setBounds(18, 5, 40, 40);
		editArqsPanel.add(todaysarqicon_2);
		
		//---------------------------------------------------------------------------------------------------------------
		
		
		//---------------------------------------------------------------------------------------------------------------
		// 7 -Exit button settings -- on the Menu panel
		//---------------------------------------------------------------------------------------------------------------
				
		JPanel ExitPanel = new JPanel();
		ExitPanel.setBorder(new LineBorder(salmond, 1, true));
		ExitPanel.addMouseListener(new PanelButtonMouseAdapter(ExitPanel){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", "Confirmation",JOptionPane.YES_NO_OPTION ) == 0) {
					MainDash.this.dispose();
					frame1.dispose();
				}
			}
		});
		ExitPanel.setBackground(mainpnnel );
		ExitPanel.setForeground(mainpnnel );
		ExitPanel.setBounds(-1, 376, 254, 50);
		//ExitPanel.setBounds(2, 376, 250, 50);
		panelMenu.add(ExitPanel);
		ExitPanel.setLayout(null);
		
		JLabel Exit = new JLabel("Exit");
		Exit.setFont(new Font("Monaco", Font.BOLD, 22));
		Exit.setHorizontalAlignment(SwingConstants.LEFT);
		Exit.setForeground(new Color(236, 209, 94, 255));  // Mustard yellow
		Exit.setBounds(90, 6, 75, 38);
		ExitPanel.add(Exit);
		
		JLabel exiticon = new JLabel();
		ImageIcon iI4 = new ImageIcon("/Users/shlomostept/Desktop/Arq_App/arqApp/Eclipse/arqdash/ARQDash/src/res/exit3.png"); 
		Image image4 = iI4.getImage(); // transform it 
		Image newimg4 = image4.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		iI4 = new ImageIcon(newimg4);  // transform it back
		
		exiticon.setIcon(iI4);
		
		exiticon.setBounds(18, 5, 40, 40);
		ExitPanel.add(exiticon);
		
		
		//---------------------------------------------------------------------------------------------------------------
		

		//---------------------------------------------------------------------------------------------------------------
		// 8 - App Logo and  -- on the Menu panel
		//---------------------------------------------------------------------------------------------------------------
				
		
		JLabel logo1 = new JLabel();
		
		ImageIcon logo = new ImageIcon("/Users/shlomostept/Desktop/Arq_App/arqApp/Eclipse/arqdash/ARQDash/src/res/logo4.png"); 
		Image imagelogo = logo.getImage(); // transform it 
		Image newlogo = imagelogo.getScaledInstance(125, 125,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		logo = new ImageIcon(newlogo);  // transform it back
		
		logo1.setIcon(logo);
		logo1.setBounds(65, 7, 125, 125);
		panelMenu.add(logo1);
		
		JLabel appname = new JLabel("Active Recall App");
		appname.setHorizontalAlignment(SwingConstants.CENTER);
		appname.setForeground(salmond);
		appname.setFont(new Font("Monaco", Font.BOLD, 18));
		appname.setBounds(32, 132, 195, 30);
		panelMenu.add(appname);
		
		
		
		
		
		//---------------------------------------------------------------------------------------------------------------
		// Add and setup the middle pannel that adapts when a different menu item is selected
		//---------------------------------------------------------------------------------------------------------------
		
	
	}
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------
	// 9 -- Settings and SetUp for the panel Menu selections 
	//---------------------------------------------------------------------------------------------------------------
			
	//---------------------------------------------------------------------------------------------------------------
		// 9.1.0 -- mouseClicked: sets up mouse listeners for the menu selection panel  (turns on the pannel thats been selected)
	//---------------------------------------------------------------------------------------------------------------
	public void menuClicked(JPanel panel) {
		panelToday.setVisible(false);
		panelInput.setVisible(false);
		panelEdit.setVisible(false);
		
		panel.setVisible(true);
		
		
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------
	// 9.2.0 -- changes the colors based on mouse entry exit and ... 
	//---------------------------------------------------------------------------------------------------------------
	
	
	private class PanelButtonMouseAdapter extends MouseAdapter{
		JPanel panel;
		
		public PanelButtonMouseAdapter(JPanel panel) {
			this.panel = panel;
		}
		
		@Override
		public void mouseEntered(MouseEvent E) {
			panel.setBackground(highlightblue );
		}
		
		@Override
		public void mouseExited(MouseEvent E) {
			panel.setBackground(mainpnnel);
		}
		
	
        // ------> 
        // ------> // ------>  // ------> 
		//MAYBE CHANGE TO MOUSE CLICKED --> but i need to figure out how to get the color flash when clicked, however there does seem to currently be an issue with the mouse event not pickng up every click
        // ------> // ------> // ------> 
        // ------> 
		
		@Override
		public void mousePressed(MouseEvent E) {
			panel.setBackground(mattegreen );
		}
		@Override
		public void mouseReleased(MouseEvent E) {
			panel.setBackground(highlightblue);
		}
	}
}
