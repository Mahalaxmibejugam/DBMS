import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class UpdateScore extends Frame 
{
	Button updateScoreButton;
	List scoreIDList;
	TextField scridText,  marksText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public UpdateScore() 
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
	
	private void loadScores() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT SCRID FROM SCORE");
		  while (rs.next()) 
		  {
			scoreIDList.add(rs.getString("ScrID"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	
	public void buildGUI() 
	{		
	    scoreIDList = new List(10);
		loadScores();
		add(scoreIDList);
		
		//When a list item is selected populate the text fields
		scoreIDList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM score where ScrID ='"+scoreIDList.getSelectedItem()+"'");
					rs.next();
					scridText.setText(rs.getString("SCRID"));
					marksText.setText(rs.getString("MARKS"));
					
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		//Handle Update Sailor Button
		updateScoreButton = new Button("Update Scores");
		updateScoreButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE score SET marks=" + marksText.getText() + " WHERE scrid = '"
					+ scoreIDList.getSelectedItem()+"'");
					
					errorText.append("\nUpdated " + i + " rows successfully");
					scoreIDList.removeAll();
					loadScores();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		scridText = new TextField(15);
		scridText.setEditable(false);
		marksText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Score ID:"));
		first.add(scridText);
		first.add(new Label("Marks:"));
		first.add(marksText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateScoreButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Update Score");
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
