package store;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class StoreGUI extends JFrame implements ItemListener, ActionListener {

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
	private double rating;
	private String rated;
	private JButton submit;
	private JComboBox mediaType;
	private JComboBox searchType;
	private JTabbedPane tabs;
	private HashMap<JButton, Media>  viewButtons;
	
	//Current logged in user
	User user;

	// MANAGER LOG IN
	private String managerPsw;
	private String psw;
	private int pswInt;
	private JPasswordField passField;

	// MEDIA
	private Media mediaObj = DBIO.getMedia(4);
	private String medType = "movie";
	
	// PLACEHOLDER DATABASE
	private String[] medTypeArray;
	private String[] albumGenreArray;
	private String[] movieGenreArray;
	private String[] audiobookGenreArray;
	private String[] searchTypeArray;

	public StoreGUI() {
		super("Store GUI");
		//TODO: Debug only put in log in eventually
		user = new Customer();
		if(DBIO.setDb("/home/jbean/Code/221/massive-tyrion/Store/src/store/Store.sqlite")==false){
			System.err.println("con is null");
			System.exit(0);
		}
		managerPsw = "password"; // temporary password
		
		tabs = new JTabbedPane();
		
		// 4 panels are created
		search = new JPanel(new GridLayout(20, 2));
		view = new JPanel(new GridBagLayout());
		purchase = new JPanel();
		rate = new JPanel();
		thankYou = new JPanel();

		// PLACEHOLDER DATABASE
		medTypeArray = new String[] { "Albums", "Movies", "Audiobooks" }; // placeholder
																			// media
																			// type
																			// array

		searchTypeArray = new String[] { "Genre", "Artist", "Producer",
				"Author", "Item Name" };

		albumGenreArray = new String[] { "Rock", "Hip-Hop", "Country", "Dance" };

		movieGenreArray = new String[] { "Action/Adventure", "Comedy", "Drama",
				"Horror" };

		audiobookGenreArray = new String[] { "Action/Adventure", "Horror",
				"Romance", "Sci-Fi" };

		// creates the welcome label
		welcomeLabel = new JLabel("Welcome to our Media Store!"); 
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		welcomeLabel.setForeground(Color.DARK_GRAY);
		welcomeLabel.setFont(new Font("", Font.BOLD, 24));
		
		// set up the manager login button
		mngrLoginButton = new JButton("Manager Login"); 
		Border emptyBorder = BorderFactory.createEmptyBorder();
		mngrLoginButton.setBorder(emptyBorder);
		mngrLoginButton.setForeground(Color.GRAY);
		mngrLoginButton.setHorizontalAlignment(JButton.CENTER);
		mngrLoginButton.addActionListener(this);
		
		// creates a label for the search field
		JLabel searchLabel = new JLabel("Search"); 

		// Media type search specifier
		medTypeText = new JLabel("Media Type:"); 
		medTypeText.setHorizontalAlignment(JTextField.LEFT);
		medTypeText.setForeground(Color.DARK_GRAY);
		medTypeText.setFont(new Font("", Font.BOLD, 10));

		// Select the type of media from drop box
		mediaType = new JComboBox(medTypeArray); 
		mediaType.addActionListener(this);
		
		// initialize a label for the search by
		searchByText = new JLabel("What would you like to search by?");
		searchByText.setHorizontalAlignment(JTextField.LEFT);
		searchByText.setForeground(Color.DARK_GRAY);
		searchByText.setFont(new Font("", Font.BOLD, 10));
		searchByText.setVisible(false);
		
		// Select how you want to search
		searchType = new JComboBox(searchTypeArray); 
		searchType.addActionListener(this);
		searchType.setVisible(false);
		
		// creates the text field for searching
		searchField = new JTextField(10); 
		searchField.addActionListener(this);

		// creates the search button
		go = new JButton("GO!"); 
		go.addActionListener(this);

		// creates the view button
		viewItem = new JButton("View"); 
		viewItem.addActionListener(this);

		// creates the purchase button
		purchaseItem = new JButton("Buy"); 
		purchaseItem.addActionListener(this);

		// label for the rate button group
		JLabel rateLabel = new JLabel("Rate: "); 
													

		ButtonGroup rateItem = new ButtonGroup(); // creates a button group
		rating = 0;
		rate1 = new JRadioButton("1", false); // instantiates button 1
		rate1.addItemListener(this);
		rate2 = new JRadioButton("2", false); // instantiates button 2
		rate2.addItemListener(this);
		rate3 = new JRadioButton("3", false); // instantiates button 3
		rate3.addItemListener(this);
		rate4 = new JRadioButton("4", false); // instantiates button 4
		rate4.addItemListener(this);
		rate5 = new JRadioButton("5", false); // instantiates button 5
		rate5.addItemListener(this);
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		// adds all 5 rate buttons to the group
		rateItem.add(rate1);
		rateItem.add(rate2);
		rateItem.add(rate3);
		rateItem.add(rate4);
		rateItem.add(rate5);

		searchAgain = new JButton("Search Again");
		searchAgain.addActionListener(this);
		
		/*
		 * Add Items to Panels and Layouts
		 */

		search.add(welcomeLabel); // add the welcome label to the search panel
		search.add(searchLabel); // adds the text "Search" to the search panel
		search.add(searchField); // adds the text field to the search panel
		search.add(medTypeText);
		search.add(mediaType);
		search.add(searchByText);
		search.add(searchType);
		search.add(go); // adds the go button to the search panel
		search.add(mngrLoginButton); // add the manager login button to the
										// search panel

		tabs.addTab("Search", search);
		
		view.add(viewItem); // adds the view button to the view panel

		purchase.add(purchaseItem); // adds the purchase button to the panel

		rate.add(rateLabel); // adds the text "Rate: " to the rate panel
		rate.add(rate1); // adds the lowest rating button to the panel
		rate.add(rate2); // adds the next lowest rating button to the panel
		rate.add(rate3); // adds the middle rating button to the panel
		rate.add(rate4); // adds the fourth rating button to the panel
		rate.add(rate5); // adds the highest rating button to the panel
		rate.add(submit);

		thankYou.add(searchAgain);

		add(tabs);
	}
	
	/**
	 * Small helper function to set constraints and add a label to a JPanel
	 * @param text String text shown in the label
	 * @param gbl GridBagLayout to set the constraints on
	 * @param c GridBagConstraints to set
	 * @param jp JPanel to add the label to
	 */
	protected void makeLabel(String text, GridBagLayout gbl, GridBagConstraints c, JPanel jp){
		Label label = new Label(text);
		gbl.setConstraints(label, c);
		jp.add(label);
	}
	/**
	 * Creates a JPanel displaying a snippet view of an items information
	 * @param mObj Media object to display info about
	 * @return JPanel with information displayed
	 */
	protected JPanel makeItemSnippetPanel(Media mObj){
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JPanel itemDisp = new JPanel(gridBag); 
		
		//Constraints default
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		//Name
		c.anchor = GridBagConstraints.LINE_START;
		makeLabel(mObj.getName(), gridBag, c, itemDisp);
		
		//type
		c.anchor = GridBagConstraints.LINE_END;
		if(mObj instanceof Album){
			makeLabel("Album", gridBag, c, itemDisp);
		}else if(mObj instanceof Audiobook){
			makeLabel("Audiobook", gridBag, c, itemDisp);
		}else if(mObj instanceof Movie){
			makeLabel("Movie", gridBag, c, itemDisp);
		}
		
		//creator
		c.anchor = GridBagConstraints.LINE_START;
		makeLabel(mObj.getCreator(), gridBag, c, itemDisp);
		
		//price
		c.anchor = GridBagConstraints.LINE_END;
		makeLabel(String.valueOf(mObj.getPrice()), gridBag, c, itemDisp);
		
		
		return itemDisp;
	}
	protected void buildView(){
		//TODO: build this -- Jared
		String searchTypeStr = (String)searchType.getSelectedItem();
		String mediaTypeStr = (String)mediaType.getSelectedItem();
		
		ArrayList<Media> searchResults = user.search(searchField.getText(),
				sTypeToEnum(searchTypeStr), mTypeToEnum(mediaTypeStr));
		view.removeAll();
		viewButtons = new HashMap<JButton, Media>();
		for(Media searchResult : searchResults){
			view.add(makeItemSnippetPanel(searchResult));
			viewItem = new JButton("View"); 
			viewItem.addActionListener(this);
			viewButtons.put(viewItem, searchResult);
		}
	}
	private DBIO.SearchField sTypeToEnum(String searchField){
		if(searchField.equalsIgnoreCase("Genre")){
			return DBIO.SearchField.GENRE;
		}else if (searchField.equalsIgnoreCase("Artist") || 
				searchField.equalsIgnoreCase("Producer") ||
				searchField.equalsIgnoreCase("Author")){
			return DBIO.SearchField.CREATOR;
		}else if (searchField.equalsIgnoreCase("Item Name")){
			return DBIO.SearchField.NAME;
		}
		return DBIO.SearchField.NAME;
		
		
	}
	private DBIO.Types mTypeToEnum(String mType){
		if(mType.equalsIgnoreCase("Albums")){
			return DBIO.Types.ALBUM;
		}else if (mType.equalsIgnoreCase("Movies")){
			return DBIO.Types.MOVIE;
		}else if (mType.equalsIgnoreCase("Audiobooks")){
			return DBIO.Types.AUDIOBOOK;
		}
		return DBIO.Types.ALBUM;
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == rate1 && e.getStateChange() == ItemEvent.SELECTED) { 
           rating = 1;
        }
		
		if(e.getSource() == rate2 && e.getStateChange() == ItemEvent.SELECTED) { 
	       rating = 2;
	    }
		
		if(e.getSource() == rate3 && e.getStateChange() == ItemEvent.SELECTED) { 
		   rating = 3;
		}
		
		if(e.getSource() == rate4 && e.getStateChange() == ItemEvent.SELECTED) { 
		   rating = 4;
		}
		
		if(e.getSource() == rate5 && e.getStateChange() == ItemEvent.SELECTED) { 
		   rating = 5;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle the manager login attempt
		if (e.getSource() == mngrLoginButton) // if manager button is pressed
		{

			passField = new JPasswordField();
			pswInt = JOptionPane.showConfirmDialog(null, passField,
					"Enter your Password", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			if (pswInt == JOptionPane.OK_OPTION) {
				psw = new String(passField.getPassword());
				if (psw.equals(managerPsw)) {
					JOptionPane.showMessageDialog(this, "Correct Password");
				} else {
					JOptionPane.showMessageDialog(this, "Incorrect Password");

				}
			}

		}
		//TODO: can we just make these three just one if statement
		// if the user selects Albums
		if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 0) {
			searchByText.setVisible(true);
			searchType.setVisible(true);
		}

		// if user selects Movies
		if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 1) {
			searchByText.setVisible(true);
			searchType.setVisible(true);
		}

		// if user selects audiobooks
		if (e.getSource() == mediaType && mediaType.getSelectedIndex() == 2) {
			searchByText.setVisible(true);
			searchType.setVisible(true);
		}

		if (e.getSource() == go) {
			buildView();
			tabs.addTab("View", view);
			tabs.remove(search);
		}

		if (e.getSource() == searchField) {
			tabs.addTab("View", view);
			tabs.remove(search);
		}

		if (viewButtons != null && viewButtons.containsKey(e.getSource())) {
			mediaObj=viewButtons.get(e.getSource());
			tabs.addTab("Purchase", purchase);
			tabs.remove(view);
		}

		if (e.getSource() == purchaseItem) {
			if(user.purchase(mediaObj, medType)){
				tabs.addTab("Rate", rate);
				tabs.remove(purchase);
			}
			else{
				tabs.addTab("Search",search);
				tabs.remove(purchase);
			}
			
		}

		if (e.getSource() == submit) {
			rated = "Rated: " + rating;
			user.rateMedia(mediaObj, rating);
			JOptionPane.showMessageDialog(this, rated);
			tabs.addTab("Thank You!", thankYou);
			tabs.remove(rate);
		}

		if (e.getSource() == searchAgain) {
			tabs.addTab("Search", search);
			tabs.remove(thankYou);
		}

		repaint();

	}

	public static void main(String[] args) {
		StoreGUI custGUI = new StoreGUI();
		custGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		custGUI.setSize(600, 600);
		custGUI.setVisible(true); // the frame is visible on screen

	}
}
