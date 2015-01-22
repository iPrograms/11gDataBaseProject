import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


public class PartVendor extends JPanel {
	
	private JButton[] vendors = new JButton[4];
	//the panel holds the vendors
	private JPanel vendorPanel = new JPanel();	
	//the panel holds the table that shows the parts of the chosen vendor
	private JPanel partPanel = new JPanel();
	
	TableNameHelper[]  helper = new TableNameHelper[4];
	//need this to know which tablename will be used to execute sql command
	private String chosen;
	//table to display the list
	private JTable partTable;
	//the header of the table
	final private String[] discolumn ={"Part Number", "CORE", "INHEAD", "OUTHEAD", "INCON", "OUCON", "TMOUT","OILCOOL", "PRICE", "AMT AVAIL"};
	private JLabel msgLabel = new JLabel();
	private JLabel msgLabel1 = new JLabel();
	
	private String[][] partList;
	private Connection con;
	
	
	public PartVendor()
	{	
		DBConnection connection = new DBConnection();
		con = connection.getDBConnection();
		this.setLayout(new BorderLayout());
		
		Font font = new Font("Dialog", 1, 18);
		msgLabel = new JLabel("Please select the Part Vendor");
		msgLabel.setFont(font);
		msgLabel.setForeground(new Color(0x444444));
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(msgLabel, BorderLayout.NORTH);
		
		//create all these table helpers in  all the car makers' name and table name		
		helper[0]= new TableNameHelper("ARS", "RDIMARS");
		helper[1]= new TableNameHelper("BEH","RDIMBEH");
		helper[2]= new TableNameHelper("DAN", "RDIMDAN");
		helper[3]= new TableNameHelper("MOD", "RDIMOND");
		
				
		vendorPanel.setLayout(new GridLayout(2, 2));
		
		for(int i= 0; i<vendors.length; i++)
		{	
			//add the buttons to the markerPanel
			vendors[i]= new JButton(helper[i].getCompanyName());
			vendorPanel.add(vendors[i]);
			final String tableName = helper[i].getTableName();
			vendors[i].addActionListener(new
					ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							chosen = tableName;
							displayParts();
						}
					});
			
		}
		vendorPanel.setVisible(true);
		this.add(vendorPanel,BorderLayout.CENTER);
		//partPanel.setLayout(new BorderLayout());
	//	partPanel.setVisible(false);
		//this.add(partPanel, BorderLayout.CENTER);
		//this.setSize(600, 400);
		
	
		
		
	}
	
	public void reSetVendor()
	{	
		this.removeAll();
		this.add(vendorPanel, BorderLayout.CENTER);
		this.add(msgLabel, BorderLayout.NORTH);
		partPanel.setVisible(false);
		vendorPanel.setVisible(true);		
		msgLabel.setVisible(true);
		msgLabel1.setVisible(false);
		
		repaint();
		
		
	}
	
	public void displayParts()
	{
		
		vendorPanel.setVisible(false);
		
		provideParts();
		partTable = new JTable(partList, discolumn);
		//create a header to show
		JTableHeader header = partTable.getTableHeader();		
		partPanel.add(header);
		partPanel.add(partTable);
		partTable.setRowSelectionAllowed(false);
		partTable.setCellSelectionEnabled(false);
		partTable.setEnabled(false);
		TableColumn column = null;
		for (int i = 0; i < 10; i++) {
		    column = partTable.getColumnModel().getColumn(i);
		    if (i==1|| i==3 || i==4 || i==6) 
		    {
		        column.setPreferredWidth(500); 
		        
		    } 
		    else if (i==2)
		    {	
		    	column.setPreferredWidth(600);
		    }
		    else {
		        column.setPreferredWidth(150);
		    }
		}
		partTable.setPreferredScrollableViewportSize
		(new Dimension(1200, 500));
		//partTable.setFillsViewportHeight(false);
		JScrollPane scroll = new JScrollPane(partTable);
		scroll.getMaximumSize();
		//scroll.setSize(800, 700);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel.setVisible(false);
		msgLabel1 = new JLabel("The list of parts available for your chosen vendor:");
		msgLabel1.setFont(font);
		msgLabel1.setForeground(new Color(0x444444));
		msgLabel1.setHorizontalAlignment(JLabel.CENTER);
		msgLabel1.setVisible(true);
		this.add(msgLabel1, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
		partPanel.setVisible(true);
		repaint();
		

			
		
		
	}
	
	
	public void provideParts()
	
	{
		
		
		Statement stmt = null;
		int count =0;
		
		String query = "SELECT count (*)  from " + chosen   ;
		System.out.println("query:" + query);
		
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())			
			{	
				count = rs.getInt("count(*)");
			
			}
		}
		catch (SQLException ex)
		{
			System.out.println("Fail to execute the query");
		}
		
		partList = new String[count][10];
		int index =0;
		
		String query2 = "Select * from " + chosen;
		System.out.println("query:" + query2);
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query2);
			
			while (rs.next())
				
			{	String[] line = new String[10];	
				line[0] = rs.getString("P_Number");
				line[1]	= rs.getString("CORE");
				line[2]	= rs.getString("INHEAD");
				line[3]	= rs.getString("OUTHEAD");
				line[4]	= rs.getString("INCON");
				line[5]	= rs.getString("OUCON");
				line[6]	= rs.getString("TMOUNT");
				line[7]	= rs.getString("OILCOOL");
				line[8]	= rs.getDouble("PRICE")+"";
				line[9]	= rs.getInt("AMOUNT") +"";
				partList[index]= line;
				index++;
			}
		}
	
		catch (SQLException ex)
		{
			System.out.println("Fail to execute the query");
		}
	}

}


