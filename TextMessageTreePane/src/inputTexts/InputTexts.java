package inputTexts;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


import textMessageProg.ColorPane;

public class InputTexts {
	public static void main(String[] args) throws FileNotFoundException{
		JFrame frame = new JFrame("Input Texts");
		JPanel panelFull = new JPanel();
		JPanel panelOne = new JPanel();
		JPanel panelTwo = new JPanel();
		JPanel panelThree = new JPanel();
		JPanel panelFour = new JPanel();
		JPanel panelFive = new JPanel();

		String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		String[] monthsOfYear = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
								 " October", "November", "December"};
		final String year = "2014";
		final JComboBox dayComboBox = new JComboBox();
		final JComboBox monthComboBox = new JComboBox();
		
		for (int i = 0; i < daysOfWeek.length; i++){
			dayComboBox.addItem(daysOfWeek[i]);
		}
		
		for (int i = 0; i < monthsOfYear.length; i++){
			monthComboBox.addItem(monthsOfYear[i]);
		}

		
		
		JLabel dayNumLabel = new JLabel("Enter the number of the month:");
		JLabel timeHourLabel = new JLabel("Enter the hour of time:     ");
		JLabel timeMinuteLabel = new JLabel("Enter the minute of the time:");
		
		
		final JTextField dayNum = new JTextField("", 5);
		final JTextField timeHour = new JTextField("", 5);
		final JTextField timeMinute = new JTextField("", 5);
		final JComboBox dayNightComboBox = new JComboBox();

		dayNightComboBox.addItem("AM");
		dayNightComboBox.addItem("PM");

		
		final ColorPane message = new ColorPane();
		
		final JButton submitRecieve = new JButton("Recieve Texts");
		final JButton submitSent = new JButton("Sent Texts");
		final JButton closeFile = new JButton("Close File");
		submitRecieve.setBackground(Color.GREEN);
		submitSent.setBackground(Color.RED);
		closeFile.setBackground(Color.YELLOW);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JScrollPane scrollPane = new JScrollPane(message);
		
		splitPane.setTopComponent(panelFull);
		splitPane.setBottomComponent(scrollPane);

		splitPane.setDividerLocation(230);
		
		panelOne.add(dayComboBox);
		panelOne.add(monthComboBox);
		
		panelTwo.add(dayNumLabel);
		panelTwo.add(dayNum);
		
		panelThree.add(timeHourLabel);
		panelThree.add(timeHour);
		
		panelFour.add(timeMinuteLabel);
		panelFour.add(timeMinute);
		panelFour.add(dayNightComboBox);

		panelFive.add(submitRecieve);
		panelFive.add(submitSent);
		panelFive.add(closeFile);

		panelFull.add(panelOne);
		panelFull.add(panelTwo);
		panelFull.add(panelThree);
		panelFull.add(panelFour);
		panelFull.add(panelFive);

		frame.add(splitPane);
		frame.setSize(350,500);
		frame.setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit Program");
		
		menuBar.add(fileMenu);
		fileMenu.add(exitItem);
		
		frame.setJMenuBar(menuBar);
		
		final PrintWriter pw = new PrintWriter("Z:/Partial.txt");

		
		closeFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				pw.close();
				System.out.println("File is closed.");
				closeFile.setBackground(Color.GREEN);
				submitRecieve.setBackground(Color.RED);
				submitSent.setBackground(Color.RED);
			}
		});
		
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		submitRecieve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String textRec = ("       *- " + dayComboBox.getSelectedItem() + "- " + monthComboBox.getSelectedItem() + 
							" " + dayNum.getText() + ", " + year + " - " + timeHour.getText() + ":" +
							timeMinute.getText() +  " " +	dayNightComboBox.getSelectedItem() + " -" + 
							message.getText() + "-");
				timeHour.setText("");
				timeMinute.setText("");
				message.setText("");
				
				pw.print(textRec);
				pw.println();
				System.out.println("Recieved Text Data written");
				
				submitRecieve.setBackground(Color.RED);
				submitSent.setBackground(Color.GREEN);
				
			}
		});
		
		
		submitSent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String textSent = ("            -" + dayComboBox.getSelectedItem() + "- " + 
						monthComboBox.getSelectedItem() + " " + dayNum.getText() + ", " + year + " - " + 
						timeHour.getText() + ":" + timeMinute.getText() +  " " + dayNightComboBox.getSelectedItem() + 
						" -" + message.getText() + "-");				
				timeHour.setText("");
				timeMinute.setText("");
				message.setText("");
				
				pw.print(textSent + "\n\n\n\n");
				pw.println();
				System.out.println("Sent Text Data written");
				
				submitSent.setBackground(Color.RED);
				submitRecieve.setBackground(Color.GREEN);
			}
		});
	}
}
