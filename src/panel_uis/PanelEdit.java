package panel_uis;

import javax.swing.JPanel;

import java_database_communication.JavaADCommunication;
import panel_functionality_classes.User;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

public class PanelEdit extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	User u ;
	public PanelEdit(User user) {
		setBackground(new Color(255, 245, 238));
		u = user;
		
		setBounds(0,0, 536, 482);
		setLayout(null);
		
		Button button = new Button("ResetDatabase");
		button.setFont(new Font("Monaco", Font.PLAIN, 16));
		button.setForeground(new Color(236, 209, 94, 255) );
		button.setBackground(new Color(255, 240, 245));
		button.setBounds(100, 200, 349, 48);
		add(button);
		setVisible(true);
		button.addActionListener(this);


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JavaADCommunication com = new JavaADCommunication(u.getDatabaseUrl() , u.getDatabaseTablename());
		try {
			com.resetDatabase();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
