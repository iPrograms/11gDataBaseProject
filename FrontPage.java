import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrontPage {
	
	
	private int selected =0;	
	//the main hold of all the gui parts
	final JFrame frame = new JFrame();
	//holds the  message to the user
	private JPanel header = new JPanel();
	//holds the 2 serach option
	private JPanel twobuttonPanel = new JPanel();
	//holds the list of auto. makers or vendors
	private JPanel display = new JPanel();

	private CarMaker carP = new CarMaker();
	private PartVendor partP = new PartVendor();
	
	//private Descriptor details = new Descriptor(); 
	
	
	public FrontPage ()
	{
		
		frame.setLayout(new BorderLayout());		
		twobuttonPanel.setLayout(new GridLayout(1,2));
		
		
		// 2 buttons let user choose how to search
		JButton mak = new JButton("Car Maker");
		JButton vendor = new JButton("Vendor");
		header.setLayout(new BorderLayout());
		Font font = new Font("Dialog", 1, 24);
		JLabel msgLabel = new JLabel("Select Search Option");
		msgLabel.setFont(font);
		msgLabel.setForeground(Color.RED);
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		twobuttonPanel.add(mak);
		twobuttonPanel.add(vendor);
		header.add(msgLabel, BorderLayout.NORTH);
		header.add(twobuttonPanel, BorderLayout.CENTER);
		frame.add(header,BorderLayout.NORTH);
		frame.add(display, BorderLayout.CENTER);
		//frame.add(details, BorderLayout.SOUTH);
		display.add(partP);
		display.add(carP);
		carP.setVisible(false);
		partP.setVisible(false);
	//	details.setVisible(false);
		
		
		
		
		//final boolean carMan = true;
		mak.addActionListener( 
	    		  	new ActionListener()
	    		  	{
	    		  		public void actionPerformed(ActionEvent event)
	    		  		{
	    		  			
	    		  				    		  			
	    		  			final int ans =1;
	    		  			selected =ans ;	    		 
	    		  			changeView();
	    		  			//stateChanged(new ChangeEvent(this));
	    		  		}
	    		  	}
	      );
		
		
		vendor.addActionListener( 
	    		  	new ActionListener()
	    		  	{
	    		  		public void actionPerformed(ActionEvent event)
	    		  		{
	    		  			
	    		  			final int ans2 =2;
	    		  			selected =ans2 ;
	    		  			//stateChanged(new ChangeEvent(this));
	    		  			changeView();
	    		  			
	    		  			   		  			
	    		  			
	    		  		}
	    		  	}
	      );
		
		frame.setTitle("AUTOMOBILE PART SEARCH SYSTEM");
		frame.setSize(1500, 900);
		//frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
			
				
		
	}
	
	/*
	 * 
	 */
	
	
	
	public void changeView()
	{
		//just shows to see if how it looks
			//	details.setVisible(true);
				
				// TODO Auto-generated method stub
				if (selected ==1)
				{
					carP.reSetMakers();
					partP.setVisible(false);
					carP.setVisible(true);
					display.revalidate();
				}
				else //if (selected ==2)
				{
					partP.reSetVendor();
					carP.setVisible(false);
					partP.setVisible(true);
					display.revalidate();
				}
				
	}
	

}
