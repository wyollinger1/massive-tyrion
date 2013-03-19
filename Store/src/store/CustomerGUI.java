
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomerGUI extends JFrame implements ItemListener, ActionListener {

	private JPanel search;
	private JPanel view;
	private JPanel purchase;
	private JPanel rate;
	private JPanel thankYou;
	private JTextField searchField;
	private JButton go;
	private JButton viewItem;
	private JButton purchaseItem;
	private JButton searchAgain;
	private JRadioButton rate1;
	private JRadioButton rate2;
	private JRadioButton rate3;
	private JRadioButton rate4;
	private JRadioButton rate5;
	private JButton submit;
	private JTabbedPane tabs;

	
	
	public CustomerGUI() { 
		super("Customer GUI"); 
		
		tabs = new JTabbedPane();
		
		//4 panels are created
		search = new JPanel();
		view = new JPanel();
		purchase = new JPanel();
		rate = new JPanel();
		thankYou = new JPanel();

		JLabel searchLabel = new JLabel("Search");	//creates a label for the search field
		
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
		
		
		
		search.add(searchLabel);	//adds the text "Search" to the search panel
		search.add(searchField);	//adds the text field to the search panel
		search.add(go);				//adds the go button to the search panel
		tabs.addTab("Search", search);
	
		view.add(viewItem);		//adds the view button to the view panel

		
		purchase.add(purchaseItem);		//adds the purchase button to the panel

		
		rate.add(rateLabel);	//adds the text "Rate: " to the rate panel
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
	public void paint(Graphics g) {
		super.paint(g);
	
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == go){
			tabs.addTab("View", view);
			tabs.remove(search);
		}
		
		if(e.getSource() == searchField){
			tabs.addTab("View", view);
			tabs.remove(search);
		}
		
		if(e.getSource() == viewItem){
			tabs.addTab("Purchase", purchase);
			tabs.remove(view);
		}
		
		if(e.getSource() == purchaseItem){
			tabs.addTab("Rate", rate);
			tabs.remove(purchase);
		}
		
		if(e.getSource() == submit){
			tabs.addTab("Thank You!", thankYou);
			tabs.remove(rate);
		}
		
		if(e.getSource() == searchAgain){
			tabs.addTab("Search",search);
			tabs.remove(thankYou);
		}
		
		repaint();                   
	
	}

	public static void main(String[] args){
	CustomerGUI custGUI = new CustomerGUI();
	custGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	custGUI.setSize(300,300);
	custGUI.setVisible(true); //the frame is visible on screen
	
	
	
	}
}
