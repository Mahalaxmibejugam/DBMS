import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class InsertLogin extends Frame 
{
	Button insertLoginButton;
	TextField loginidText,passText,typeText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	public InsertLogin() 
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
	public void buildGUI() 
	{		
		//Handle Insert Account Button
		insertLoginButton = new Button("Insert Login");
		insertLoginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				  //String query = "INSERT INTO sailors (SID,SNAME, RATING, AGE) VALUES (2,'Divya',7,20)";				  
				  String query= "INSERT INTO login VALUES("+"'" + loginidText.getText() + "','" + passText.getText() + "','" +typeText.getText()+"')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});

	
		loginidText = new TextField(15);
		passText = new TextField(15);
		typeText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Login ID:"));
		first.add(loginidText);
		first.add(new Label("Password:"));
		first.add(passText);
		first.add(new Label("Type:"));
		first.add(typeText);
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertLoginButton);
        second.setBounds(125,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		setLayout(null);

		add(first);
		add(second);
		add(third);
	    
		setTitle("New Score Creation");
		setSize(500, 600);
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
