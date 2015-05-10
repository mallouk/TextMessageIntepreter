package textMessageProg;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import textMessageProg.TextMessages.ReceivedTexts;
import textMessageProg.TextMessages.SentTexts;



public class SearchFunction {

	private TextMessages.NamePerson namePerson;
	private TextMessages.Conversations convos;
	private TextMessages.ReceivedTexts recievedTexts;
	private TextMessages.SentTexts sentTexts;
	private TreeObject personName;

	JTextField searchField = new JTextField("", 40);
	ColorPane textArea = new ColorPane();

	String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	String[] monthsOfYear = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
			" October", "November", "December"}; 

	public SearchFunction(TreeObject personName, TextMessages textMess){

		TextMessages.NamePerson namePerson = textMess.new NamePerson();
		TextMessages.Conversations convos = textMess.new Conversations();
		TextMessages.ReceivedTexts recievedTexts = textMess.new ReceivedTexts();
		TextMessages.SentTexts sentTexts = textMess.new SentTexts();
		this.personName = personName;
		this.namePerson = namePerson;
		this.convos = convos;
		this.recievedTexts = recievedTexts;
		this.sentTexts = sentTexts;


		final JFrame frame = new JFrame();
		JPanel panelFull = new JPanel();

		JPanel panelTop = new JPanel(new FlowLayout());
		final JPanel panelMiddle = new JPanel();
		final JPanel panelBottom = new JPanel();
		panelBottom.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel searchLabel = new JLabel("Search:");

		final JCheckBox recieveTextCheck = new JCheckBox("Recieved Texts");
		final JCheckBox sentTextCheck = new JCheckBox("Sent Texts");
		recieveTextCheck.setSelected(true);
		sentTextCheck.setSelected(true);


		final JComboBox searchPeopleList = new JComboBox();
		searchPeopleList.addItem("All People");
		for (int i = 0; i < personName.size(); i++){
			namePerson = (TextMessages.NamePerson)personName.get(i);
			searchPeopleList.addItem(namePerson.name);
		}
		JButton searchButton = new JButton("Search");
		//searchButton.setFont(new Font(null, Font.PLAIN, 14));


		final JComboBox monthParameter = new JComboBox();
		monthParameter.addItem("All Months Of the Year");
		for (int i = 0; i < monthsOfYear.length; i++){
			monthParameter.addItem(monthsOfYear[i]);
		}

		final JComboBox dayParameter = new JComboBox();
		dayParameter.addItem("All Days Of the Week");
		for (int i = 0; i < daysOfWeek.length; i++){
			dayParameter.addItem(daysOfWeek[i]);
		}


		JScrollPane scrollArea = new JScrollPane(textArea);


		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		splitPane.setTopComponent(panelFull);
		splitPane.setBottomComponent(scrollArea);
		splitPane.setDividerLocation(125);


		panelFull.add(panelTop);
		panelFull.add(panelMiddle);
		panelFull.add(panelBottom);

		frame.add(splitPane);

		panelTop.add(searchLabel);
		panelTop.add(searchField);

		panelMiddle.add(recieveTextCheck);
		panelMiddle.add(sentTextCheck);
		panelMiddle.add(searchPeopleList);

		panelBottom.add(new JPanel());
		panelBottom.add(monthParameter);
		panelBottom.add(searchButton);
		panelBottom.add(dayParameter);

		frame.setSize(800,700);
		frame.setVisible(true);
		frame.setTitle("Search For a Word Or Phrase");

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit Program");

		menuBar.add(fileMenu);
		fileMenu.add(exitItem);
		frame.setJMenuBar(menuBar);





		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				boolean bothChecked = sentTextCheck.isSelected() && recieveTextCheck.isSelected();

				int selectedIndex = searchPeopleList.getSelectedIndex();
				String searchText = searchField.getText();
				if (bothChecked){					
					if (selectedIndex == 0){
						searchAll(searchText, 0, 1, 1);
					}else{
						searchAll(searchText, selectedIndex, 1, 1);
					}
				}else if (sentTextCheck.isSelected()){
					if (selectedIndex == 0){
						searchAll(searchText, 0, 1, 0);
					}else{
						searchAll(searchText, selectedIndex, 1, 0);
					}
				}else if (recieveTextCheck.isSelected()){
					if (selectedIndex == 0){
						searchAll(searchText, 0, 0, 1);
					}else{
						searchAll(searchText, selectedIndex, 0, 1);
					}
				}else{
					JOptionPane.showMessageDialog(null,"One or both of the checkboxs must be checked in order for a " +
					"search to be viable.");
				}
			}
		});


		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});


		
	}

	/** Method that searches the specific subset of data.
	 * 
	 * @param searchFieldArray			searches the data for the word or phrase
	 * @param indexPerson				checks and makes it scan for a particular phrase within only a person's texts.
	 * @param processSentTexts			scans only sent texts
	 * @param processRecievedTexts		scans only received texts
	 */
	public void searchAll(CharSequence searchFieldArray, int indexPerson, int procSentTexts, int procRecievedTexts){
		textArea.setText("");
		textArea.setFont(new Font("Candara", Font.BOLD, 18));
		for (int i = 0; i < personName.size(); i++){
			namePerson = (TextMessages.NamePerson)personName.get(i);
			if (indexPerson != 0){
				namePerson = (TextMessages.NamePerson)personName.get(indexPerson - 1);
				textArea.setText("");
			}
			for (int x = 0; x < namePerson.convoCounter.size(); x++){
				convos = (TextMessages.Conversations)namePerson.convoCounter.get(x);
				for (int r = 0; r < convos.textMessages.size(); r++){
					recievedTexts = (ReceivedTexts) convos.textMessages.get(r);
					sentTexts = (SentTexts) convos.textMessages.get(r);
					if (!recievedTexts.recMessTime.equalsIgnoreCase(" Time ") && procRecievedTexts == 1){
						if (recievedTexts.recMessage.contains(searchFieldArray)){
							printRecievedMessages(x);
						}
					}
					
					if (!sentTexts.sentMessTime.equalsIgnoreCase(" Time ") && procSentTexts == 1){
						if (sentTexts.sentMessage.contains(searchFieldArray)){
							printSentMessages(x);
						}
					}
				}
			}
		}
	}

	
	public void printSentMessages(int x){
		textArea.append(Color.BLUE, "You:");
		textArea.append(new Color(100,10,140), "            " + 
				sentTexts.sentMessDay + sentTexts.sentMessDate + " at " + 
				sentTexts.sentMessTime);
		textArea.append(new Color(7,197,218), " : Conversation " + (x+1) + 
				" with " + namePerson.name + "\n");
		textArea.append(Color.BLACK, sentTexts.sentMessage + "\n\n");
		textArea.append(Color.ORANGE, "-----------------------------------------------------------" +
				"---------------------------------------------------------------------------------" +
		"----------\n\n");
	}
	
	public void printRecievedMessages(int x){
		textArea.append(Color.RED, namePerson.name + ": ");  
		textArea.append(new Color(20,140,10), "            " + 
				recievedTexts.recMessDay + recievedTexts.recMessDate + " at " + 
				recievedTexts.recMessTime);
		textArea.append(new Color(7,197,218), " : Conversation " + (x+1) + "\n");
		textArea.append(Color.BLACK, recievedTexts.recMessage + "\n\n");

		textArea.append(Color.ORANGE, "-----------------------------------------------------------" +
				"---------------------------------------------------------------------------------" +
		"----------\n\n");
	}

}
