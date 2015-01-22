
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import java.util.*;

public class CarMaker extends JPanel {
	
	private JButton[] makersB = new JButton[19];// I only see 19 car markers' tables
	//a list hold all the model buttons
	private ArrayList<JButton> modelsB;
	private ArrayList<JButton> yearB;
	private ArrayList<JButton> vendors;
	
	private JPanel display = new JPanel();
	//holder for all the carmarker buttons 	
	private JPanel makerPanel = new JPanel();	
	//this is a holder for all the models for user's selected automobile maker
	private JPanel modelPanel = new JPanel();
	//this is a holder for all the years for the chosen model
	private JPanel yearPanel = new JPanel();
	//this is the holder for displaying all the engine description for the selected model and year
	private JPanel enginePanel = new JPanel();
	//this is the panel of different vendors that sells the parts that match with user's selection
	private JPanel vendorPanel= new JPanel();
	//this is for part user selection to display details	
	private JPanel partPanel = new JPanel();
	
	//create array with size of 4 to hold the columns of the table for listing all the details of the models 
	final private String[] columns = {"Description","Litres","Engine Type","cubic_inches"};
	//create array with size of 10 to hold the columns of the table for the part description;
	final private String[] discolumn ={"Part Number", "CORE", "INHEAD", "OUTHEAD", "INCON", "OUCON", "TMOUT","OILCOOL", "PRICE", "AMT AVAIL"};
	//used to store the details of  description
	
	private String [][] engDis;
	//used to display the list of different engine 
	private JTable engTable;
	//used to display the detail of the chosen part
	private JTable partTable;
	private int selectedRow;
	private String Rlink;
	//these are the messages/instructions for user
	private JLabel msgLabel = new JLabel();
	private JLabel msgLabel2 = new JLabel();
	private JLabel msgLabel3 = new JLabel() ;
	private JLabel msgLabel4 = new JLabel();
	private JLabel msgLabel5 = new JLabel();
	private JLabel msgLabel6 = new JLabel();
	private JLabel msgNoFound = new JLabel();
	//private String message;
	TableNameHelper[]  helper = new TableNameHelper[19];
	//need this to execute sql command
	//private TableNameHelper chosen;
	private String tableChosen;
	//get the chosen model
	private String modelChosen;
	//get the chosen year
	private int yearChosen;
	//a list that holds all the user's chosen models
	ArrayList<String> models;
	//a list that holds the years of chosen model
	ArrayList<Integer> years ;
	// the return result by looking for the Rlink number 
	String[] rlinklist;
	//store the description of the chosen part
	String[] discrip;
	//create a DBConnection to the database	
	private String partChosen;
	//need to know which table/vendor the chosen part is located
	private String vendorChosen;
	private int partIndexChosen;
	private Connection con;
	
	public CarMaker()
	{	
		
		DBConnection connection = new DBConnection();
		con = connection.getDBConnection();
		//set the layout for this container
		this.setLayout(new BorderLayout());
		//set the message label and add it to this panel
		Font font = new Font("Dialog", 1, 18);
		msgLabel = new JLabel("Please select the Automobile Maker");
		msgLabel.setFont(font);
		msgLabel.setForeground(new Color(0x444444));
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(msgLabel, BorderLayout.NORTH);
		
		
		//create all these table helpers with  all the car makers' name and table name		
		helper[0]= new TableNameHelper("BUICK", "APLBUK");
		helper[1]= new TableNameHelper("CADILIAC","APLCAD");
		helper[2]= new TableNameHelper("CHEVROLET", "APLCHE");
		helper[3]= new TableNameHelper("CHRYSLER", "APLCRY");
		helper[4]= new TableNameHelper("FORD LIGHT TRUCK AND VAN", "APLFDT");
		helper[5]= new TableNameHelper("FORD", "APLFOR");
		helper[6]= new TableNameHelper("CHEVROLET & GMC TRACK & VAN", "APLGMC");
		helper[7]= new TableNameHelper("INTERNATIONAL TRUCK (I.H.C)", "APLINT");
		helper[8]= new TableNameHelper("ISUZU", "APLISU");
		helper[9]= new TableNameHelper("LINCON", "APLLIN");
		helper[10]= new TableNameHelper("MAZDA", "APLMZD");
		helper[11]= new TableNameHelper("OLDSMOBILE", "APLOLD");
		helper[12]= new TableNameHelper("PORSCH", "APLPOR");
		helper[13]= new TableNameHelper("RENAULT", "APLREN");
		helper[14]= new TableNameHelper("SABB", "APLSAB");
		helper[15]= new TableNameHelper("SUBARU", "APLSUB");
		helper[16]= new TableNameHelper("TOYOTA", "APLTOY" );
		helper[17]= new TableNameHelper("UPS", "APLUPS");
		helper[18]= new TableNameHelper("VOLKSWAGEN", "APLVOL");
		
		
		
		
		makerPanel.setLayout(new GridLayout(25, 6));
		modelPanel.setLayout(new GridLayout(25, 6));
		yearPanel.setLayout(new GridLayout(30, 3));
		enginePanel.setLayout(new GridLayout(2,1));
		vendorPanel.setLayout(new GridLayout(16,1));
		partPanel.setLayout(new GridLayout(30, 3));
		//need one for the yearPanel here;
		
		
		for(int i= 0; i<makersB.length; i++)
		{	
			
			//add the buttons to the markerPanel
			makersB[i]= new JButton(helper[i].getCompanyName());
			makerPanel.add(makersB[i]);
			final String tablename = helper[i].getTableName();
			makersB[i].addActionListener(new
					ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							
							tableChosen = tablename;
							changeModelButton( );
												
						}

						
					});
			
			
		}
		
		
		makerPanel.setVisible(true);
		display.add(makerPanel);
		modelPanel.setVisible(false);
		display.add(modelPanel);
		yearPanel.setVisible(false);
		display.add(yearPanel);
		enginePanel.setVisible(false);
		display.add(enginePanel);
		vendorPanel.setVisible(false);
		display.add(vendorPanel);
		partPanel.setVisible(false);
		display.add(partPanel);
		this.add(display, BorderLayout.CENTER);
		
		
		
		
	
		
		
		
	}
	
	
	public void disconnect() 
	{
		((DBConnection) con).disconnectFromDB();
	}
	
	public void reSetMakers() 
	{
		makerPanel.setVisible(true);
		
		modelPanel.setVisible(false);
		//partPanel.setVisible(false);
		yearPanel.setVisible(false);
		enginePanel.setVisible(false);
		vendorPanel.setVisible(false);
		partPanel.setVisible(false);
		
		//msgLabel3.setVisible(false);
		this.add(msgLabel, BorderLayout.NORTH);
		msgLabel.setVisible(true);
		msgLabel2.setVisible(false);
		msgLabel3.setVisible(false);
		msgLabel4.setVisible(false);
		msgLabel5.setVisible(false);
		msgNoFound.setVisible(false);
		msgLabel6.setVisible(false);
		
		//display.validate();
		
	}
	
	
	
	/*
	 * 
	 */
	
	public void changeModelButton ( )
	{	
		
		provideModel();
		//clear up the old chosen maker's models
		modelPanel.removeAll();
		modelsB = new ArrayList<JButton>();
		//make the makerPaner invisible
		makerPanel.setVisible(false);
		yearPanel.setVisible(false);
		vendorPanel.setVisible(false);
		partPanel.setVisible(false);
		
		
		//create model buttons for user to choose from
		
		for(int i =0; i<models.size(); i++)
		{
			final String model = models.get(i);
			JButton temp =new JButton(models.get(i));
			
			
			modelsB.add(temp);
			temp.addActionListener(new
					ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							modelChosen = model;
							changeYearButton();
							//stateChanged(new ChangeEvent(this));
						}

						
					});
			
			
			modelPanel.add(temp);
				
		}
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel2 = new JLabel("Please select the model of the vichele");
		msgLabel2.setFont(font);
		msgLabel2.setForeground(new Color(0x444444));
		msgLabel2.setHorizontalAlignment(JLabel.CENTER);
		msgLabel.setVisible(false);
		this.add(msgLabel2, BorderLayout.NORTH);
		modelPanel.setVisible(true);	
		repaint();
		
		
	}	
	
	
/*execute the sql command and put all the models from the chosen automobile maker in to any arraylist
 * 
 */

	public void provideModel()
	{
		models = new ArrayList<String> ();
		Statement stmt = null;
		String query = "SELECT distinct model FROM " + tableChosen + " order by model asc" ;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String model = rs.getString("MODEL");
				
				models.add(model);
					
			}
				
				
		}	
		catch (SQLException ex)
			{
				System.out.println("Fail to execute the query");
			}
		
	
		
		
	}
	
	public void changeYearButton()
	{
		provideYear();
		yearPanel.removeAll();
		yearB = new ArrayList<JButton>();
		//make the makerPaner invisible
		modelPanel.setVisible(false);
		makerPanel.setVisible(false);

	//	partPanel.setVisible(false);
		
		//create model buttons for user to choose from
		
		for(int i =0; i<years.size(); i++)
		{
			final int year = years.get(i);
			JButton temp =new JButton(years.get(i).toString());
			yearB.add(temp);
			temp.addActionListener(new
					ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							yearChosen = year;
							showModelDetails();
							//stateChanged(new ChangeEvent(this));
						}

						
					});
			
			
			yearPanel.add(temp);
				
		}
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel3 = new JLabel("Please select the year of the model");
		msgLabel3.setFont(font);
		msgLabel3.setForeground(new Color(0x444444));
		msgLabel3.setHorizontalAlignment(JLabel.CENTER);
		msgLabel2.setVisible(false);
		this.add(msgLabel3, BorderLayout.NORTH);
		yearPanel.setVisible(true);	
		repaint();
		
	}
	
		
	public void provideYear()
	{	
		//create a arraylist to hold all search result for all the year
		years = new ArrayList<Integer> ();
		Statement stmt = null;
		String query = "SELECT distinct YEAR from " + tableChosen + " where model= " + "'" +modelChosen+ "'"+ " order by year asc" ;
		System.out.println("query:" + query);
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs != null && rs.next())
			{
				int year = rs.getInt("year");
				years.add(year);
					
			}
				
				
		}	
		catch (SQLException ex)
			{
				System.out.println("Fail to execute the query");
			}
		
	
		
		
	}	


	public void showModelDetails()
	{
		yearPanel.setVisible(false);
		
		enginePanel.removeAll();
		int length = provideEng();
		String [][] temp = new String[length][4];
		
		/*
		 if (row < engDis.length) // the array could be trimmed
		  {
		       engDis = new String [engDis.length-size];
		  }
		  */
		
		if(length !=0)
		{	//engDis = new String[length] [4];
			for(int i=0; i<length; i++)
			{
				for(int j=0; j<4; j++)
				{
					temp[i][j] = engDis[i][j];
				}
			}
		}
		

		DefaultTableModel tableModel = new DefaultTableModel() {

		  
		    public boolean isCellEditable(int row, int column) 
		    {
		       //all cells false
		       return false;
		    }
		};
		engTable = new JTable(temp, columns );
		
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
		    column = engTable.getColumnModel().getColumn(i);
		    if (i == 0) {
		        column.setPreferredWidth(200); //frist column is bigger
		        
		    } else {
		        column.setPreferredWidth(100);
		    }
		}
		
		engTable.setRowHeight(30);
		//table.getRowcount
		//engTable.
		//engTable.setModel(tableModel);
		//engTable.set
		//engTable.setEnabled(true);
		engTable.setRowSelectionAllowed(true);
		//engTable.setCellSelectionEnabled(false);
		
		
		//create a header to show
		JTableHeader header = engTable.getTableHeader();
		
		engTable.addMouseListener(				
				new MouseAdapter()
    		  	{
    		  		
					@Override
					public void mouseClicked(MouseEvent e) {
						
						selectedRow= engTable.getSelectedRow();
						Rlink =engDis[selectedRow][4];
						
						changeVendorButton();
						
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// do nothing
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// do nothing
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
			
					//do nothing				
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
    		  	});
			
		
		
		enginePanel.add(header);
		enginePanel.add(engTable);
		
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel3.setVisible(false);
		msgLabel4 = new JLabel("Please select the Engine for your selected model");
		msgLabel4.setFont(font);
		msgLabel4.setForeground(new Color(0x444444));
		msgLabel4.setHorizontalAlignment(JLabel.CENTER);
		msgLabel4.setVisible(true);
		this.add(msgLabel4, BorderLayout.NORTH);
		enginePanel.setVisible(true);
		repaint();
		
		
		
	}
	
	public int provideEng()
	{	
		//create a array to hold all search result for all the year
		engDis = new String[10][5];
		//start index for the row
		int row=0;
		Statement stmt = null;
		//SQL> select Description, Litres, Engine_type, cubic_inches from APLBUK where mod
		//el ='CENTURY' and Year='82';

		String query = "SELECT *  from " + tableChosen + " where model= " + "'" 
						+modelChosen+ "'"+ " and year= " + "'"+yearChosen + "'" ;
		
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{	String[] line = new String[5];
				
				line[0]= rs.getString("Description");
				line[1]	 = rs.getString("Litres");
				line[2] = rs.getString("Engine_type");
				line[3] = rs.getString("cubic_inches");
				line[4] =  String.valueOf(rs.getInt("RLINK"));				
						
				
				
				engDis[row] = line;
				row++;
			}
			
				
		}	
		catch (SQLException ex)
			{
				System.out.println("Fail to execute the query");
			}
		
	
		return row;
		
	}	
	
	
	public void changeVendorButton()
	{
		
		int hasChoose = 0;
		enginePanel.setVisible(false);
		provideParts();
		//clear up the resutl from previous chosen vehicle
		vendorPanel.removeAll();
		vendors = new ArrayList<JButton>();
		
		
		//create available vendor parts for user to choose from 		
		//use i <.length and  not null
		for(int i =0; i<rlinklist.length; i++)
					
		{
			
			final int num =i;
			int lastDig = 1+ (i%(4));
			
		
			if( rlinklist[i] == null)
				{
								
					//dothing
				}
			else if (rlinklist[i].equalsIgnoreCase("NS"))
			{
				//do nothing
			}
			else
				{
					hasChoose = hasChoose+1;
					if(i>=0 && i<=3)
					{
					
						JButton temp = new JButton("ARS"+lastDig);
						temp.addActionListener(new
									ActionListener()
						{
							public void actionPerformed(ActionEvent event)
							{
								partChosen = rlinklist[num];
								partIndexChosen = num;
								vendorChosen ="ARS";
								displayPartDetails();
							}
						});
							
						vendorPanel.add(temp);
						
					}	
					
					if(i>=4 && i<=7)
					{
					
						JButton temp = new JButton("MOD"+lastDig);
						temp.addActionListener(new
									ActionListener()
						{
							public void actionPerformed(ActionEvent event)
							{
								partChosen = rlinklist[num];
								partIndexChosen = num;
								vendorChosen ="MOD";
								displayPartDetails();
							}
						});
							
						vendorPanel.add(temp);
						
					
					}	
					
					if(i>=8 && i<=11)
					{
					
						JButton temp = new JButton("BEH"+lastDig);
						temp.addActionListener(new
									ActionListener()
						{
							public void actionPerformed(ActionEvent event)
							{
								partChosen = rlinklist[num];
								partIndexChosen = num;
								vendorChosen ="BEH";
								displayPartDetails();
							}
						});
							
						vendorPanel.add(temp);
						
					
					}	
					
					if(i>=12 && i<=15)
					{
					
						JButton temp = new JButton("DAN"+lastDig);
						temp.addActionListener(new
									ActionListener()
						{
							public void actionPerformed(ActionEvent event)
							{
								partChosen = rlinklist[num];
								vendorChosen ="DAN";
								partIndexChosen = num;
								displayPartDetails();
							}
						});
							
						vendorPanel.add(temp);
						
					
					}	
					
										
				}
		}
					
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel5 = new JLabel("The following are the available parts from the listed vendors, please choose one to view details");
		msgLabel5.setFont(font);
		msgLabel5.setForeground(new Color(0x444444));
		msgLabel5.setHorizontalAlignment(JLabel.CENTER);
		msgLabel4.setVisible(false);
		msgLabel3.setVisible(false);
		this.add(msgLabel5, BorderLayout.NORTH);
		vendorPanel.setVisible(true);	
		repaint();
		
		
		if(hasChoose==0)
		{
			
			displayNoChoosePanel();
		}
		
	}
	
	/*
	 * This function will find all the parts that are available
	 */
	
	public void provideParts()
	{
		rlinklist =new String[16];
		Statement stmt = null;
		//
		//

		String query = "SELECT *  from RADCRX where RLINK= " + "'"+Rlink+"'" ;
						
		System.out.println("query:" + query);
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				rlinklist[0]	= rs.getString("ARS1");
				rlinklist[1]	= rs.getString("ARS2");
				rlinklist[2]	= rs.getString("ARS3");
				rlinklist[3]	= rs.getString("ARS4");
				rlinklist[4]	= rs.getString("MOD1");
				rlinklist[5]	= rs.getString("MOD2");
				rlinklist[6]	= rs.getString("MOD3");
				rlinklist[7]	= rs.getString("MOD4");
				rlinklist[8]	= rs.getString("BEH1");
				rlinklist[9]	= rs.getString("BEH2");
				rlinklist[10]	= rs.getString("BEH3");
				rlinklist[11]	= rs.getString("BEH4");
				rlinklist[12]	= rs.getString("DAN1");
				rlinklist[13]	= rs.getString("DAN2");
				rlinklist[14]	= rs.getString("DAN3");
				rlinklist[15]	= rs.getString("DAN4");
				
				
			
			
			
			}	
		}
		
		catch (SQLException ex)
		{
			System.out.println("Fail to execute the query");
		}
		
		
	}
//
	/*this will display the message if no mataching part is found
	 * 
	 */
	
	
	public void displayNoChoosePanel()
	{
		
		vendorPanel.setVisible(false);
		msgLabel5.setVisible(false);
		msgNoFound = new JLabel("Sorry no part is available for the chosen vehicle");
		Font font = new Font("Dialog", 1, 18);
		msgNoFound.setFont(font);
		msgNoFound.setForeground(new Color(0x444444));
		msgNoFound.setHorizontalAlignment(JLabel.CENTER);
		msgNoFound.setVisible(true);
		this.add(msgNoFound);
		
		repaint();
		
		
	}
	
	/*This is used to display the details of the part
	 * 
	 */
	public void displayPartDetails()
	{
		
		
		vendorPanel.setVisible(false);
		partPanel.removeAll();
		discrip =new String[10];
		Statement stmt = null;
		

		String query = "SELECT  * from RDIM"+ vendorChosen +   " where P_NUMBER='"+partChosen +"'"  ;
						
		System.out.println("query:" + query);
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				discrip[0]	= rs.getString("P_NUMBER");
				discrip[1]	= rs.getString("CORE");
				discrip[2]	= rs.getString("INHEAD");
				discrip[3]	= rs.getString("OUTHEAD");
				discrip[4]	= rs.getString("INCON");
				discrip[5]	= rs.getString("OUCON");
				discrip[6]	= rs.getString("TMOUNT");
				discrip[7]	= rs.getString("OILCOOL");
				discrip[8]	= rs.getDouble("PRICE")+"";
				discrip[9]	= rs.getInt("AMOUNT") +"";	
				
				
			
			}	
		}
		
		catch (SQLException ex)
		{
			System.out.println("Fail to execute the query");
		}
		
		
		String [][] temp = {discrip};
		partTable = new JTable(temp, discolumn );
		
		TableColumn column = null;
		for (int i = 0; i < 10; i++) {
		    column = partTable.getColumnModel().getColumn(i);
		    if (i == 2 || i ==1) {
		        column.setPreferredWidth(200); //frist column is bigger
		        
		    } else {
		        column.setPreferredWidth(100);
		    }
		}
		
		partTable.setRowHeight(30);
		//table.getRowcount

		partTable.setRowSelectionAllowed(false);
		partTable.setCellSelectionEnabled(false);
		partTable.setEnabled(false);
		
		//create a header to show
		JTableHeader header = partTable.getTableHeader();
		partPanel.add(header);
		partPanel.add(partTable);
		
		Font font = new Font("Dialog", 1, 18);
		//shows different messge 
		msgLabel5.setVisible(false);
		msgLabel6 = new JLabel("Here is the details for the part you have chosen:");
		msgLabel6.setFont(font);
		msgLabel6.setForeground(new Color(0x444444));
		msgLabel6.setHorizontalAlignment(JLabel.CENTER);
		msgLabel6.setVisible(true);
		this.add(msgLabel6, BorderLayout.NORTH);
		partPanel.setVisible(true);
		repaint();
		
		
		
		
		
		
		
	}
	
	

	
	

}