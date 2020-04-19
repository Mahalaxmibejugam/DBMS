import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class InsertGets extends Frame 
{
	
	List studentIDList,scoreIDList;
	Button insertGetsButton;
	TextField scridText, sidText,  dateText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs,rs1;
	Label l1,l2;
	public InsertGets() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:SHARATH","maha","2000");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }

    private void loadStudents() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT * FROM Students");
		  while (rs.next()) 
		  {
			studentIDList.add(rs.getString("STUDENTID"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}

	 private void loadScores() 
	{	   
		try 
		{
		  rs1 = statement.executeQuery("SELECT * FROM Score");
		  while (rs1.next()) 
		  {
			scoreIDList.add(rs1.getString("ScrID"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}

	public void buildGUI() 
	{	
		
		studentIDList = new List(10);
		loadStudents();
		//studentIDList.add("737-080");
		//String [] str=studentIDList.getItems();
		//for(String k:str)
		//{
		//	System.out.println(k);
		//}
		//add(studentIDList);
		scoreIDList = new List(10);
		loadScores();
		//add(scoreIDList);	
		
		studentIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM students");
					while (rs.next()) 
					{
						if (rs.getString("STUDENTID").equals(studentIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						sidText.setText(rs.getString("STUDENTID"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});
		scoreIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM score");
					while (rs.next()) 
					{
						if (rs.getString("ScrID").equals(scoreIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						scridText.setText(rs.getString("ScrID"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});

		insertGetsButton = new Button("Insert Gets");
		insertGetsButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				  //String query = "INSERT INTO sailors (SID,SNAME, RATING, AGE) VALUES (2,'Divya',7,20)";				  
				  String query= "INSERT INTO students_score VALUES("+"'" + sidText.getText() + "','" + scridText.getText() + "','" + dateText.getText() + "'" +")";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});

	
		sidText = new TextField(15);
		scridText = new TextField(15);
		dateText = new TextField(15);

		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		l1=new Label("STUDENTID");
		l2=new Label("ScrID");
		Panel first = new Panel();
		Panel p=new Panel();
		
		first.add(l1);
		first.add(l2);
		first.add(studentIDList);
		
		first.add(scoreIDList);
		first.setLayout(new GridLayout(2,2));

		p.add(new Label("Student ID:"));
		p.add(sidText);
		p.add(new Label("Scr ID:"));
		p.add(scridText);
		p.add(new Label("Date:"));
		p.add(dateText);
		p.setLayout(new GridLayout(3,2));

		first.setBounds(50,50,250,150);
		p.setBounds(100,200,250,150);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertGetsButton);
        second.setBounds(150,350,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,450,300,200);
		
		setLayout(null);

		add(first);
		add(p);
		add(second);
		add(third);
	    
		setTitle("New Gets Creation");
		setSize(500, 700);
		setVisible(true);
	}

	

	private void displaySQLErrors(SQLException e) 
	{
		Frame f=new Frame();
		JOptionPane.showMessageDialog(f,"Enter valid data types");  
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	

	
}
