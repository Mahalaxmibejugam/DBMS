import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class DeleteScore extends Frame 
{
	Button deleteScoreButton;
	List scoreIDList;
	TextField scridText, marksText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	ResultSet rs;
	
	public DeleteScore() 
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
		  rs = statement.executeQuery("SELECT * FROM Score");
		  while (rs.next()) 
		  {
			scoreIDList.add(rs.getString("SCRID"));
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
					rs = statement.executeQuery("SELECT * FROM score");
					while (rs.next()) 
					{
						if (rs.getString("SCRID").equals(scoreIDList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						scridText.setText(rs.getString("ScrID"));
						marksText.setText(rs.getString("marks"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});		
		
	    
		//Handle Delete Sailor Button
		deleteScoreButton = new Button("Delete Score");
		deleteScoreButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM score WHERE ScrID = "
							+ "'"+scoreIDList.getSelectedItem()+"'");
					errorText.append("\nDeleted " + i + " rows successfully");
					scridText.setText(null);
					marksText.setText(null);
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
		second.add(deleteScoreButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		setTitle("Remove Score");
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
