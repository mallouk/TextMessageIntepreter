package textMessageProg;

import javax.swing.JFrame;

public class TextMessages extends JFrame{
	static TextMessages textMess = new TextMessages();


	class NamePerson{
		TreeObject convoCounter = new TreeObject();
		String name;
		
		public void assignPersonName(String record1){
			String[] tokens = record1.split("-");
			name = tokens[1];
		}
	}

	class Conversations{
		TreeObject recTexts = new TreeObject();
		TreeObject senTexts = new TreeObject();
		int convoCount;
		
		public void countConversations(String record1){
			String[] tokens = record1.split("/");
			convoCount = Integer.parseInt(tokens[1]);
		}
	}

	class ReceivedTexts{
		String recMessDay;
		String recMessDate;
		String recMessTime;
		String recMessage;

		public void assignRecievedTexts(String record1){
			String[] tokens = record1.split("-");
			recMessDay = tokens[1];
			recMessDate = tokens[2];
			recMessTime = tokens[3];
			recMessage = tokens[4];
		}
	}

	class SentTexts{
		String sentMessDay;
		String sentMessDate;
		String sentMessTime;
		String sentMessage;

		public void assignSentTexts(String record1){
			String[] tokens = record1.split("-");
			sentMessDay = tokens[1];
			sentMessDate = tokens[2];
			sentMessTime = tokens[3];
			sentMessage = tokens[4];
		}
	}
}

//comment
