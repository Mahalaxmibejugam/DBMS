import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateAttempts extends Frame 
{
	Button updateAttemptsButton;
	List studentIDList,quizIDList;
	TextField sidText, quizidText,noText, dateText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateAttempts() 
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
		  rs = statement.executeQuery("SELECT STUDENTID FROM students_quiz");
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
	
	private void loadQuiz() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT quizID FROM students_quiz");
		  while (rs.next()) 
		  {
			quizIDList.add(rs.getString("quizID"));
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
		quizIDList=new List(10);
		loadQuiz();
		add(quizIDList);
		
		//When a list item is selected populate the text fields
		studentIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM students_quiz where STUDENTID ='"+studentIDList.getSelectedItem()+"'");
					rs.next();
					sidText.setText(rs.getString("STUDENTID"));
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
		quizIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM students_quiz where quizID ='"+quizIDList.getSelectedItem()+"'");
					rs.next();
					quizidText.setText(rs.getString("quizID"));
					rs=statement.executeQuery("select noofattempts,day from students_quiz where studentid='"+sidText.getText()+"' and quizid='"+quizidText.getText()+"'");
					rs.next();
					noText.setText(rs.getString("noofattempts"));
					dateText.setText(rs.getString("Day"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
	    
		//Handle Update Sailor Button
		updateAttemptsButton = new Button("Update Attempts");
		updateAttemptsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE students_quiz SET Day ='"+ dateText.getText() + " ',noofattempts="+noText.getText()+"WHERE studentid = '"
					+ studentIDList.getSelectedItem()+"' and quizid='" +quizIDList.getSelectedItem()+"'");
					
					errorText.append("\nUpdated " + i + " rows successfully");
					studentIDList.removeAll();
					loadStudents();
					quizIDList.removeAll();
					loadQuiz();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		sidText = new TextField(15);
		sidText.setEditable(false);
		quizidText = new TextField(15);
		noText = new TextField(15);
		dateText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Student ID:"));
		first.add(sidText);
		first.add(new Label("Quiz ID:"));
		first.add(quizidText);
		first.add(new Label("No of Attempts:"));
		first.add(noText);
		first.add(new Label("Date:"));
		first.add(dateText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateAttemptsButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Attempts");
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
