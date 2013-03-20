import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class StoreGUI extends JFrame implements ItemListener, ActionListener 
{

	private JPanel search;
	private JPanel view;
	private JPanel purchase;
	private JPanel rate;
	private JPanel thankYou;
        private JLabel welcomeLabel;
        private JLabel medTypeText;
        private JLabel searchByText;
	private JTextField searchField;
	private JButton go;
        private JButton mngrLoginButton;
	private JButton viewItem;
	private JButton purchaseItem;
	private JButton searchAgain;
	private JRadioButton rate1;
	private JRadioButton rate2;
	private JRadioButton rate3;
	private JRadioButton rate4;
	private JRadioButton rate5;
	private JButton submit;
        private JComboBox mediaType;
        private JComboBox searchType;
	private JTabbedPane tabs;
        
        //MANAGER LOG IN
        private String managerPsw;
        private String psw;
        private int pswInt;
        private JPasswordField passField;
        
        //MEDIA

        
        //PLACEHOLDER DATABASE
        private String[] medTypeArray;
        private String[] albumGenreArray;
        private String[] movieGenreArray;
        private String[] audiobookGenreArray;
        private String[] searchTypeArray;


	
	
	public StoreGUI() 
        { 
		super("Store GUI"); 
                managerPsw = "password";        //temporary password

		tabs = new JTabbedPane();
		
		//4 panels are created
		search = new JPanel(new GridLayout(20,2));
		view = new JPanel();
		purchase = new JPanel();
		rate = new JPanel();
		thankYou = new JPanel();
                
                //PLACEHOLDER DATABASE
                medTypeArray = new String[]{"Albums", "Movies", "Audiobooks"};  //placeholder media type array
                
                searchTypeArray = new String[]{"Genre", "Artist", "Producer","Author"};
                
                albumGenreArray = new String[]{"Rock", "Hip-Hop", "Country", "Dance"};
        
                movieGenreArray = new String[]{"Action/Adventure", "Comedy", "Drama", "Horror"};
        
                audiobookGenreArray = new String[]{"Action/Adventure","Horror", "Romance", "Sci-Fi"};
                
                welcomeLabel = new JLabel("Welcome to our Media Store!");  //creates the welcome label
                welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
                welcomeLabel.setForeground(Color.DARK_GRAY);
                welcomeLabel.setFont(new Font("", Font.BOLD, 24));
                
                mngrLoginButton = new JButton("Manager Login");            //set up the manager login button
                Border emptyBorder = BorderFactory.createEmptyBorder();
                mngrLoginButton.setBorder(emptyBorder);
                mngrLoginButton.setForeground(Color.GRAY);
                mngrLoginButton.setHorizontalAlignment(JButton.CENTER);
                mngrLoginButton.addActionListener(this);

		JLabel searchLabel = new JLabel("Search");	//creates a label for the search field
                
                medTypeText = new JLabel("Media Type:");        //Media type search specifier
                medTypeText.setHorizontalAlignment(JTextField.LEFT);
                medTypeText.setForeground(Color.DARK_GRAY);
                medTypeText.setFont(new Font("", Font.BOLD, 10));
                
                
                mediaType = new JComboBox(medTypeArray);    //Select the type of media from drop box
                mediaType.addActionListener(this);
                
                searchByText = new JLabel("What would you like to search by?");  // initialize a label for the search by
                searchByText.setHorizontalAlignment(JTextField.LEFT);
                searchByText.setForeground(Color.DARK_GRAY);
                searchByText.setFont(new Font("", Font.BOLD, 10));
                searchByText.setVisible(false);
                
                searchType = new JComboBox(searchTypeArray);    //Select how you want to search
                searchType.addActionListener(this);
                searchType.setVisible(false);
		
                searchField = new JTextField(10); 	//creates the text field for searching      
		searchField.addActionListener(this);
                
	    
                go = new JButton("GO!");	//creates the search button
                go.addActionListener(this);
                

		viewItem = new JButton("View");		//creates the view button
		viewItem.addActionListener(this);
		
		purchaseItem = new JButton("Buy");	//creates the purchase button
		purchaseItem.addActionListener(this);
		
		JLabel rateLabel = new JLabel("Rate: ");	//label for the rate button group
		
		ButtonGroup rateItem = new ButtonGroup();	//creates a button group
		
		rate1 = new JRadioButton("1",false);	//instantiates button 1
		
		rate2 = new JRadioButton("2",false);	//instantiates button 2
		
		rate3 = new JRadioButton("3",false);	//instantiates button 3

		rate4 = new JRadioButton("4",false);	//instantiates button 4
		
		rate5 = new JRadioButton("5",false);	//instantiates button 5
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		//adds all 5 rate buttons to the group
		rateItem.add(rate1);	
		rateItem.add(rate2);
		rateItem.add(rate3);
		rateItem.add(rate4);
		rateItem.add(rate5);
		
		searchAgain = new JButton("Search Again");
		searchAgain.addActionListener(this);
		
                search.add(welcomeLabel);       //add the welcome label to the search panel
		search.add(searchLabel);	//adds the text "Search" to the search panel
		search.add(searchField);	//adds the text field to the search panel
                search.add(medTypeText);
                search.add(mediaType);
                search.add(searchByText);
                search.add(searchType);
		search.add(go);                 //adds the go button to the search panel
                search.add(mngrLoginButton);    //add the manager login button to the search panel
                

                tabs.addTab("Search", search);
	
		view.add(viewItem);		//adds the view button to the view panel

		
		purchase.add(purchaseItem);	//adds the purchase button to the panel

		
		rate.add(rateLabel);            //adds the text "Rate: " to the rate panel
		rate.add(rate1);		//adds the lowest rating button to the panel
		rate.add(rate2);		//adds the next lowest rating button to the panel
		rate.add(rate3);		//adds the middle rating button to the panel
        	rate.add(rate4);		//adds the fourth rating button to the panel
		rate.add(rate5);		//adds the highest rating button to the panel
		rate.add(submit);
		
		thankYou.add(searchAgain);
		
		add(tabs);
	}
	

	
	@Override
	public void paint(Graphics g) 
        {
		super.paint(g);
	
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
        {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
        {       
                //Handle the manager login attempt
		if (e.getSource() == mngrLoginButton)       //if manager button is pressed
                {
            
                    passField = new JPasswordField();
                    pswInt = JOptionPane.showConfirmDialog(null,
                    passField, "Enter your Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (pswInt == JOptionPane.OK_OPTION) 
                    {
                        psw = new String(passField.getPassword());
                        if (psw.equals(managerPsw)) 
                        {
                            JOptionPane.showMessageDialog(this, "Correct Password");
                        } 
                        else
                        {
                            JOptionPane.showMessageDialog(this,"Incorrect Password");

                        }
                    }

                }
                 // if the user selects Albums
                if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 0) 
                {
                    searchByText.setVisible(true);
                    searchType.setVisible(true);
                }
        
                // if user selects Movies
                if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 1) 
                {
                    searchByText.setVisible(true);
                    searchType.setVisible(true);
                }
        
                // if user selects audiobooks
                if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 2)
                {
                    searchByText.setVisible(true);
                    searchType.setVisible(true);
                }
                
                if(e.getSource() == go)
                {
			tabs.addTab("View", view);
			tabs.remove(search);
		}
		
		if(e.getSource() == searchField)
                {
			tabs.addTab("View", view);
			tabs.remove(search);
		}
		
		if(e.getSource() == viewItem)
                {
			tabs.addTab("Purchase", purchase);
			tabs.remove(view);
		}
		
		if(e.getSource() == purchaseItem)
                {
			tabs.addTab("Rate", rate);
			tabs.remove(purchase);
		}
		
		if(e.getSource() == submit)
                {
			tabs.addTab("Thank You!", thankYou);
			tabs.remove(rate);
		}
		
		if(e.getSource() == searchAgain)
                {
			tabs.addTab("Search",search);
			tabs.remove(thankYou);
		}
		
		repaint();                   
	
	}

	public static void main(String[] args)
        {
            StoreGUI custGUI = new StoreGUI();
            custGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            custGUI.setSize(600,600);
            custGUI.setVisible(true); //the frame is visible on screen
	
	
	
	}
}
