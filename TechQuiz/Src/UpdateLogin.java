import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateLogin extends Frame 
{
	Button updateLoginButton;
	List loginIDList;
	TextField loginidText, passText,typeText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateLogin() 
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
	
	private void loadLogins() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT loginID FROM login");
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
	    loginIDList = new List(10);
		loadLogins();
		add(loginIDList);
		
		//When a list item is selected populate the text fields
		loginIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM login where loginID ='"+loginIDList.getSelectedItem()+"'");
					rs.next();
					loginidText.setText(rs.getString("loginID"));
					passText.setText(rs.getString("user_password"));
					typeText.setText(rs.getString("type"));
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		//Handle Update Sailor Button
		updateLoginButton = new Button("Update Login");
		updateLoginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE login SET type='" + typeText.getText() + "',user_password='"+passText.getText()+"'WHERE loginid = '"
					+ loginIDList.getSelectedItem()+"'");
					
					errorText.append("\nUpdated " + i + " rows successfully");
					loginIDList.removeAll();
					loadLogins();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		loginidText = new TextField(15);
		loginidText.setEditable(false);
		passText = new TextField(15);
		typeText = new TextField(15);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Login ID:"));
		first.add(loginidText);
		first.add(new Label("Login Password:"));
		first.add(passText);
		first.add(new Label("Type:"));
		first.add(typeText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateLoginButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Login");
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
