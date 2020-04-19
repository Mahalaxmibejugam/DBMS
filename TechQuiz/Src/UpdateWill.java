import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateWill extends Frame 
{
	Button updateWillButton;
	List studentIDList,loginIDList;
	TextField sidText, loginidText, dateText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateWill() 
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
		  rs = statement.executeQuery("SELECT STUDENTID FROM students_login");
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
	
	private void loadLogin() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT loginID FROM students_login");
		  while (rs.next()) 
		  {
			loginIDList.add(rs.getString("loginID"));
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
		add(studentIDList);
		loginIDList=new List(10);
		loadLogin();
		add(loginIDList);
		
		//When a list item is selected populate the text fields
		studentIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM students_login where STUDENTID ='"+studentIDList.getSelectedItem()+"'");
					rs.next();
					sidText.setText(rs.getString("STUDENTID"));
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
		loginIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM students_login where loginID ='"+loginIDList.getSelectedItem()+"'");
					rs.next();
					loginidText.setText(rs.getString("loginID"));
					rs=statement.executeQuery("select day from students_login where studentid='"+sidText.getText()+"' and loginid='"+loginidText.getText()+"'");
					rs.next();
					dateText.setText(rs.getString("Day"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
	    
		//Handle Update Sailor Button
		updateWillButton = new Button("Update Will");
		updateWillButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE students_login SET Day ='"+ dateText.getText() + " 'WHERE studentid = '"
					+ studentIDList.getSelectedItem()+"' and loginid='" +loginIDList.getSelectedItem()+"'");
					
					errorText.append("\nUpdated " + i + " rows successfully");
					studentIDList.removeAll();
					loadStudents();
					loginIDList.removeAll();
					loadLogin();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		sidText = new TextField(15);
		sidText.setEditable(false);
		loginidText = new TextField(15);
		dateText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Student ID:"));
		first.add(sidText);
		first.add(new Label("Login ID:"));
		first.add(loginidText);
		first.add(new Label("Date:"));
		first.add(dateText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateWillButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Will");
		setSize(500, 600);
		setLayout(new FlowLayout());
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
