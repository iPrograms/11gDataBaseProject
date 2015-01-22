import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/*This class contains a JTextArea used to display the details of the part
 * 
 * 
 */
public class Descriptor extends JPanel implements ChangeListener{

	private JLabel msg = new JLabel();
	//or it could be another JLabel tha with better layout and format
	private JLabel details = new JLabel();
	
	
	
	
	public Descriptor()
	{
		this.setLayout(new BorderLayout());
		Font font = new Font("Dialog", 1, 18);
		msg = new JLabel("Part Details");
		msg.setFont(font);
		msg.setForeground(new Color(0x444444));
		msg.setHorizontalAlignment(JLabel.CENTER);
		add(msg, BorderLayout.NORTH);
		add(details, BorderLayout.CENTER);
	}
	
	
	public JLabel getDiscription()
	{
		return details;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		update();
		
	}

	
	public void update()
	{
		//put the details into the textpad
	}
	
	
	
}
