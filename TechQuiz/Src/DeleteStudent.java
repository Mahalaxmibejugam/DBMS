import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteStudent extends Frame 
{
	
	Button deleteStudentButton;
	List studentIDList;
	TextField sidText, snameText, mailText, collegeText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public DeleteStudent() 
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
	
	public void buildGUI() 
	{		
	    studentIDList = new List(10);
		loadStudents();
		add(studentIDList);
		
		//When a list item is selected populate the text fields
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
						snameText.setText(rs.getString("SNAME"));
						mailText.setText(rs.getString("MAILID"));
						collegeText.setText(rs.getString("COLLEGE"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		//Handle Delete Sailor Button
		deleteStudentButton = new Button("Delete Student");
		deleteStudentButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM students WHERE STUDENTID = '"+studentIDList.getSelectedItem()+"' and sname='"+snameText.getText()+"' and mailid='"+mailText.getText()+"' and college='"+collegeText.getText()+"'");
					errorText.append("\nDeleted " + i + " rows successfully");
					sidText.setText(null);
					snameText.setText(null);
					mailText.setText(null);
					collegeText.setText(null);
					studentIDList.removeAll();
					loadStudents();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		sidText = new TextField(15);
		snameText = new TextField(15);
		mailText = new TextField(15);
		collegeText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Student ID:"));
		first.add(sidText);
		first.add(new Label("Name:"));
		first.add(snameText);
		first.add(new Label("Mail_id:"));
		first.add(mailText);
		first.add(new Label("College:"));
		first.add(collegeText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteStudentButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Remove Student");
		setSize(450, 600);
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
