import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateQuiz extends Frame 
{
	Button updateQuizButton;
	List quizIDList;
	TextField qidText, qnameText,qlangText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateQuiz() 
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
	
	private void loadQuiz() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT QuizID FROM Quiz");
		  while (rs.next()) 
		  {
			quizIDList.add(rs.getString("QuizID"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	
	public void buildGUI() 
	{		
	    quizIDList = new List(10);
		loadQuiz();
		add(quizIDList);
		
		//When a list item is selected populate the text fields
		quizIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM quiz where quizID ='"+quizIDList.getSelectedItem()+"'");
					rs.next();
					qidText.setText(rs.getString("QuizID"));
					qnameText.setText(rs.getString("Quizname"));
					qlangText.setText(rs.getString("quizlang"));
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		//Handle Update Sailor Button
		updateQuizButton = new Button("Update Quiz");
		updateQuizButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE quiz SET quizname='" + qnameText.getText() + "',quizlang='"+qlangText.getText()+"'WHERE quizid = '"
					+ quizIDList.getSelectedItem()+"'");
					
					errorText.append("\nUpdated " + i + " rows successfully");
					quizIDList.removeAll();
					loadQuiz();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		qidText = new TextField(15);
		qidText.setEditable(false);
		qnameText = new TextField(15);
		qlangText = new TextField(15);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Quiz ID:"));
		first.add(qidText);
		first.add(new Label("Quiz Name:"));
		first.add(qnameText);
		first.add(new Label("Quiz Lang:"));
		first.add(qlangText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateQuizButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Quiz");
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
