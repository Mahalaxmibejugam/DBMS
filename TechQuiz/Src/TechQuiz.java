import java.awt.*; 
import java.awt.event.*; 

class TechQuiz extends Frame implements ActionListener
{ 
	  String msg = ""; 
	  Label ll;
	  InsertStudent stud;
	  UpdateStudent ups;
	  DeleteStudent dels;
	  InsertScore scr;
	  UpdateScore upscr;
	  DeleteScore delscr;
	  InsertGets get;
	  DeleteGets delget;
	  UpdateGets upget;
	  InsertQuiz Quiz;
	  DeleteQuiz delq;
	  UpdateQuiz upquiz;
	  InsertLogin login;
	  UpdateLogin upl;
	  DeleteLogin dell;
	  InsertAttempts attempt;
	  DeleteAttempts dela;
	  UpdateAttempts upa;
	  InsertWill will;
	  UpdateWill upw;
	  DeleteWill delw;
	
	  TechQuiz() 
	  { 
			ll = new Label();
			ll.setAlignment(Label.CENTER);  
			ll.setBounds(90,250,250,100); 			
			ll.setText("Welcome to XYZ TechQuiz");
			add(ll);
		 
			// create menu bar and add it to frame 
			MenuBar mbar = new MenuBar(); 
			setMenuBar(mbar); 
		 
			// create the menu items and add it to Menu
			Menu Student = new Menu("Students"); 
			MenuItem item1, item2, item3; 
			Student.add(item1 = new MenuItem("Insert Student")); 
			Student.add(item2 = new MenuItem("View Students")); 
			Student.add(item3 = new MenuItem("Delete Student")); 
			mbar.add(Student);  
		 
			Menu Score = new Menu("Score"); 
			MenuItem item4, item5, item6; 
			Score.add(item4 = new MenuItem("Insert Score")); 
			Score.add(item5 = new MenuItem("View Scores")); 
			Score.add(item6 = new MenuItem("Delete Score"));  
			mbar.add(Score); 
			
			Menu gets = new Menu("Gets"); 
			MenuItem item7, item8, item9; 
			gets.add(item7 = new MenuItem("Insert gets")); 
			gets.add(item8 = new MenuItem("View gets")); 
			gets.add(item9 = new MenuItem("Delete gets")); 
			mbar.add(gets); 

			Menu quiz=new Menu("Quiz");
			MenuItem item10,item11,item12;
			quiz.add(item10=new MenuItem("Insert Quiz"));
			quiz.add(item11 = new MenuItem("View Quiz")); 
			quiz.add(item12 = new MenuItem("Delete Quiz")); 
			mbar.add(quiz); 
			
			Menu Login = new Menu("Login"); 
			MenuItem item13, item14, item15; 
			Login.add(item13 = new MenuItem("Insert Login")); 
			Login.add(item14 = new MenuItem("View Login")); 
			Login.add(item15 = new MenuItem("Delete Login")); 
			mbar.add(Login);  

			Menu Attempts = new Menu("Attempts"); 
			MenuItem item16, item17, item18; 
			Attempts.add(item16 = new MenuItem("Insert Attempts")); 
			Attempts.add(item17 = new MenuItem("View Attempts")); 
			Attempts.add(item18 = new MenuItem("Delete Attempts")); 
			mbar.add(Attempts);

			Menu Will = new Menu("Will"); 
			MenuItem item19, item20, item21; 
			Will.add(item19 = new MenuItem("Insert Will")); 
			Will.add(item20 = new MenuItem("View Will")); 
			Will.add(item21 = new MenuItem("Delete Will")); 
			mbar.add(Will);
			// register listeners
			item1.addActionListener(this); 
			item2.addActionListener(this); 
			item3.addActionListener(this); 
			item4.addActionListener(this); 
			item5.addActionListener(this); 
			item6.addActionListener(this); 
			item7.addActionListener(this); 
			item8.addActionListener(this); 
			item9.addActionListener(this);
			item10.addActionListener(this);
			item11.addActionListener(this);
			item12.addActionListener(this); 
			item13.addActionListener(this); 
			item14.addActionListener(this); 
			item15.addActionListener(this); 
			item16.addActionListener(this); 
			item17.addActionListener(this); 
			item18.addActionListener(this); 
			item19.addActionListener(this); 
			item20.addActionListener(this); 
			item21.addActionListener(this); 
			
					
			 // Anonymous inner class which extends WindowAdaptor to handle the Window event: windowClosing  
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we) 
				{ 
					System.exit(0);	
				} 
			}); 
			
			//Frame properties
			setTitle("XYZ Online TechQuiz"); 
			Color clr = new Color(200, 100, 150);
			setBackground(clr); 
			setFont(new Font("SansSerif", Font.BOLD, 14)); 
			setLayout(null);
			setSize(500, 600); 
			setVisible(true);	
			
	  }   
	  
	  public void actionPerformed(ActionEvent ae) 
	  { 

		  String arg = ae.getActionCommand(); 
		  if(arg.equals("Insert Student"))
		  {
			stud = new InsertStudent();
			stud.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				stud.dispose();
			}
			});		
			stud.buildGUI();	
          }			
		 
		 else if(arg.equals("View Students")) 
		 {
			ups = new UpdateStudent();
			ups.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
		    {
				ups.dispose();
			}
			});		
			ups.buildGUI();		 
		 }
		 
		 else if(arg.equals("Delete Student")) 
		 {
			dels = new DeleteStudent();

			dels.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				dels.dispose();
			}
			});		
			dels.buildGUI();		 
		 }
		 else if(arg.equals("Insert Score"))
		  {
			scr = new InsertScore();

			scr.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				scr.dispose();
			}
			});		
			scr.buildGUI();	
          }			
		 
		 else if(arg.equals("View Scores")) 
		 {
			upscr = new UpdateScore();
			upscr.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
		    {
				upscr.dispose();
			}
			});		
			upscr.buildGUI();		 
		 }
		 
		 else if(arg.equals("Delete Score")) 
		 {
			delscr = new DeleteScore();

			delscr.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				delscr.dispose();
			}
			});		
			delscr.buildGUI();		 
		 }
		 else if(arg.equals("Insert gets"))
		  {
			get = new InsertGets();

			get.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				get.dispose();
			}
			});		
			get.buildGUI();	
          }			
		 
		 else if(arg.equals("View gets")) 
		 {
			upget = new UpdateGets();
			upget.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
		    {
				upget.dispose();
			}
			});		
			upget.buildGUI();		 
		 }
		 
		 else if(arg.equals("Delete gets")) 
		 {
			delget = new DeleteGets();

			delget.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) 
			{
				delget.dispose();
			}
			});		
			delget.buildGUI();		 
		 }
		 else if(arg.equals("Insert Quiz"))
		 {
		 	Quiz=new InsertQuiz();
		 	Quiz.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			Quiz.dispose();
		 		}
		 	});
		 	Quiz.buildGUI();

		 }
		 else if(arg.equals("Delete Quiz"))
		 {
		 	delq=new DeleteQuiz();
		 	delq.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			delq.dispose();
		 		}
		 	});
		 	delq.buildGUI();

		 }
		 else if(arg.equals("View Quiz"))
		 {
		 	upquiz=new UpdateQuiz();
		 	upquiz.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			upquiz.dispose();
		 		}
		 	});
		 	upquiz.buildGUI();

		 }

		 else if(arg.equals("View Login"))
		 {
		 	upl=new UpdateLogin();
		 	upl.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			upl.dispose();
		 		}
		 	});
		 	upl.buildGUI();

		 }

		 else if(arg.equals("Insert Login"))
		 {
		 	login=new InsertLogin();
		 	login.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			login.dispose();
		 		}
		 	});
		 	login.buildGUI();

		 }
		else if(arg.equals("Delete Login"))
		 {
		 	dell=new DeleteLogin();
		 	dell.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			dell.dispose();
		 		}
		 	});
		 	dell.buildGUI();

		 }

		 else if(arg.equals("Insert Attempts"))
		 {
		 	attempt=new InsertAttempts();
		 	attempt.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			attempt.dispose();
		 		}
		 	});
		 	attempt.buildGUI();

		 }
		 else if(arg.equals("View Attempts"))
		 {
		 	upa=new UpdateAttempts();
		 	upa.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			upa.dispose();
		 		}
		 	});
		 	upa.buildGUI();

		 }
		 else if(arg.equals("Delete Attempts"))
		 {
		 	dela=new DeleteAttempts();
		 	dela.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			dela.dispose();
		 		}
		 	});
		 	dela.buildGUI();

		 }
		 else if(arg.equals("Insert Will"))
		 {
		 	will=new InsertWill();
		 	will.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			will.dispose();
		 		}
		 	});
		 	will.buildGUI();

		 }
		 else if(arg.equals("View Will"))
		 {
		 	upw=new UpdateWill();
		 	upw.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			upw.dispose();
		 		}
		 	});
		 	upw.buildGUI();

		 }
		 else if(arg.equals("Delete Will"))
		 {
		 	delw=new DeleteWill();
		 	delw.addWindowListener(new WindowAdapter(){
		 		public void windowClosing(WindowEvent e)
		 		{
		 			delw.dispose();
		 		}
		 	});
		 	delw.buildGUI();

		 }
		
	  }
	  public static void main(String []args)
	  {
			new TechQuiz();	  
	  }
} 
 

 
