package textMessageProg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import textMessageProg.TextMessages.Conversations;
import textMessageProg.TextMessages.Messages;
import textMessageProg.TextMessages.NamePerson;

public class FixOld {

	public static void main(String[] args){
		//Get filename
		File f = new File("Z:/Messages.txt");
		try{
			//Iterate through the file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			String record1 = bufferedReader.readLine().toString();
			//We go through our loop if we have lines in our file.
			while (record1 != null){
				if (record1.startsWith("       *- D") || record1.startsWith("            - D-")){
					//Do Nothing
				}else if (record1.startsWith("            ")){
					String[] tokens = record1.split("-");
					String recMessDay = tokens[1];
					String recMessDate = tokens[2];
					String recMessTime = tokens[3];
					String recMessage = tokens[4];
					System.out.println("       *- " + recMessDay + "- " + recMessDate + 
							" - "+recMessTime+" -"+recMessage+"-rec-");
				}else if (record1.startsWith("      *")){
					System.out.println(record1 + "sent-");
				}else{
					System.out.println(record1);
				}
				record1 = bufferedReader.readLine().toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
