package textMessageProg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import textMessageProg.TextMessages.Messages;
import textMessageProg.TextMessages.ReceivedTexts;
import textMessageProg.TextMessages.SentTexts;


public class TextTreeDriver {
	static TextMessages textMess = new TextMessages();
	
	static TreeObject personName = new TreeObject();
	static TextMessages.NamePerson namePerson = textMess.new NamePerson();
	static TextMessages.Conversations convos = textMess.new Conversations();
	static TextMessages.Messages texts = textMess.new Messages();

	static JTree tree;
	static ColorPane textArea = new ColorPane();
	final static JFileChooser fc = new JFileChooser("Z:");
	static String fileSelected = "";

	JFrame frame = new JFrame("Tree Tester");
	JPanel panelLeft = new JPanel(new GridLayout(1,1));
	JPanel panelRight = new JPanel(new GridLayout(1,1));

	static DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Messages");

	/** Designs and build the GUI.
	 * 
	 * @throws FileNotFoundException	if the file cannot be found and scanned, throw the error.
	 */
	public TextTreeDriver() throws FileNotFoundException{
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		createNodes(rootNode);
		tree = new JTree(rootNode);


		JScrollPane scrollTree = new JScrollPane(tree);
		JScrollPane scrollArea = new JScrollPane(textArea);

		panelLeft.add(scrollTree);
		panelRight.add(scrollArea);

		splitPane.setLeftComponent(panelLeft);
		splitPane.setRightComponent(panelRight);
		tree.addTreeSelectionListener(new TreeListener());
		splitPane.setDividerLocation(300);
		splitPane.setPreferredSize(new Dimension(1000,600));

		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem searchItem = new JMenuItem("Search");
		JMenuItem exitItem = new JMenuItem("Exit Program");

		menuBar.add(fileMenu);
		fileMenu.add(searchItem);
		fileMenu.add(exitItem);
		
		searchItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				SearchFunction search = new SearchFunction(personName, textMess);
			}
		});
		
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		frame.setJMenuBar(menuBar);
		
		frame.add(splitPane);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(1000,600);
	}


	class TreeListener implements TreeSelectionListener{
		public void valueChanged(TreeSelectionEvent arg0) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			tree.getLastSelectedPathComponent();
			
			try{
				if (node.isLeaf()){
					textArea.setText("");
					String[] tokens = node.toString().split(" ");
					int convoNum = Integer.parseInt(tokens[1]);
					for (int i = 0; i < personName.size();i++){
						namePerson = (TextMessages.NamePerson)personName.get(i);
						if (node.getParent().toString().equals(namePerson.name)){
							printMessages(convoNum-1, node);
						}
					}
				}
			}catch (ArrayIndexOutOfBoundsException e){}
		}
	}

	public void printMessages(int convoNum, DefaultMutableTreeNode node1){
		convos = (TextMessages.Conversations)namePerson.convoCounter.get(convoNum);
		for (int r = 0; r < convos.textMessages.size(); r++){
			texts = (Messages) convos.textMessages.get(r);
			textArea.setFont(new Font("Candara", Font.BOLD, 18));
			if (texts.getMessType().equals("rec")){
				textArea.append(Color.RED, node1.getParent().toString() + ": ");  
				textArea.append(new Color(20, 140, 10), "            " + 
						texts.getMessDay() + texts.getMessDate() + " at " + 
						texts.getMessTime() + "\n");
				textArea.append(Color.BLACK, texts.getMessage() + "\n\n");
			}
			if (texts.getMessType().equals("sent")){
				textArea.append(Color.BLUE, "You: ");  
				textArea.append(new Color(100, 10, 140), "            " + 
						texts.getMessDay() + texts.getMessDate() + " at " + 
						texts.getMessTime() + "\n");
				textArea.append(Color.BLACK, texts.getMessage() + "\n\n");
			}
		}
	}


	public static void createNodes(DefaultMutableTreeNode top) throws FileNotFoundException{
		TextMessages file = new TextMessages();
		fc.showOpenDialog(file);
		String selectedFile = fc.getSelectedFile().toString();;
		File f = new File(selectedFile);
		Scanner scan = new Scanner(f);
		String record1 = "";
		while (scan.hasNextLine()){
			record1 = scan.nextLine();
			if (record1.startsWith(")")){
				namePerson = textMess.new NamePerson();
				namePerson.assignPersonName(record1);
				personName.add(namePerson);
			}else if (record1.startsWith("/")){
				convos = textMess.new Conversations();
				convos.countConversations(record1);
				namePerson.convoCounter.add(convos);
			}else if (record1.contains("*")){
				texts = textMess.new Messages();
				texts.parseMessages(record1);
				convos.textMessages.add(texts);
			}
		}

		DefaultMutableTreeNode namePerTreeNode = new DefaultMutableTreeNode();
		DefaultMutableTreeNode convoBranch = new DefaultMutableTreeNode();
		//Collections.sort(personName);

		for (int i = 0; i < personName.size(); i++){
			namePerson = (TextMessages.NamePerson)personName.get(i);
			namePerTreeNode = new DefaultMutableTreeNode(namePerson.name);
			top.add(namePerTreeNode);
			for (int x = 0; x < namePerson.convoCounter.size(); x++){
				convos = (TextMessages.Conversations)namePerson.convoCounter.get(x);
				convoBranch = new DefaultMutableTreeNode("Conversation: " + (x+1));
				namePerTreeNode.add(convoBranch);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException{
		TextTreeDriver driver = new TextTreeDriver();
		System.out.println("What");
	}
}
