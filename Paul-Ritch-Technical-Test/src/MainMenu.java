import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.menu();
	}
	
	public void menu() {
        int selection;
        Scanner input = new Scanner(System.in);
        FileProcessor fileProcessor = new FileProcessor();
		InputFile inputFile = new InputFile();
		ArrayList<String> linesToDisplay = new ArrayList<String>();
		boolean readFile = true;
		while(readFile) {
			readFile = inputFile.readFile(input);
		}
		try {
			System.out.println("Choose from these choices");
			System.out.println("-------------------------\n");
			System.out.println("1 - Event by Type");
			System.out.println("2 - Event by Field");
			System.out.println("3 - Event between Timestamp");
			System.out.println("4 - Event by Type and Field");
			System.out.println("5 - Event by Type and Timestamp");
			System.out.println("6 - Event by Field and Timestamp");
			System.out.println("7 - Event by Type, Field and Timestamp");
			ArrayList<String> lines = inputFile.getLines();
			selection = input.nextInt();
			switch(selection) {
				case 1:
					linesToDisplay = this.eventsByTypeMenu(lines, fileProcessor, input);
					break;
				case 2:
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsByFieldMenu(lines, fileProcessor, input);
					break;
				case 3:
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsBetweenTimestampsMenu(lines, fileProcessor, input);
					break;
				case 4:
					linesToDisplay = this.eventsByTypeMenu(lines, fileProcessor, input);
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsByFieldMenu(linesToDisplay, fileProcessor, input);
					break;
				case 5:
					linesToDisplay = this.eventsByTypeMenu(lines, fileProcessor, input);
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsBetweenTimestampsMenu(linesToDisplay, fileProcessor, input);
					break;
				case 6:
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsByFieldMenu(lines, fileProcessor, input);
					linesToDisplay = this.eventsBetweenTimestampsMenu(linesToDisplay, fileProcessor, input);
					break;
				case 7:
					linesToDisplay = this.eventsByTypeMenu(lines, fileProcessor, input);
					input.nextLine(); //read to end of line since last read was nextInt
					linesToDisplay = this.eventsByFieldMenu(linesToDisplay, fileProcessor, input);
					linesToDisplay = this.eventsBetweenTimestampsMenu(linesToDisplay, fileProcessor, input);
					break;
			}	
    	} catch (Exception e) {
    		System.out.println("Value not an integer" + e);
    	}
		linesToDisplay.forEach(line -> {
			System.out.println(line);
		});
        input.close();
    }

	
	/**
	 * @param lines
	 * @param fileProcessor
	 * @param input
	 * @return List of strings
	 */
	private ArrayList<String> eventsByTypeMenu(ArrayList<String> lines, FileProcessor fileProcessor, Scanner input){
    	int type;
        ArrayList<String> linesToDisplay = new ArrayList<String>();
    	try {
    		System.out.println("Choose the type to return");
    		System.out.println("-------------------------\n");
    		System.out.println("1 - INSERTED");
    		System.out.println("2 - UPDATED");
    		System.out.println("3 - DELETED");
    		type = input.nextInt();
    		switch(type) {
        		case 1:
        			//call method with INSERTED
        			linesToDisplay = fileProcessor.eventsByType(lines, "INSERTED");
        			break;
        		
        		case 2:
        			//call method with UPDATED
        			linesToDisplay = fileProcessor.eventsByType(lines, "UPDATED");
        			break;
        		
        		case 3:
        			//call method with DELETED
        			linesToDisplay = fileProcessor.eventsByType(lines, "DELETED");
        			break;
    		}
    	} catch (Exception e) {
    		System.out.println("Value not an integer" + e);
    	}
        return linesToDisplay;
	}
	
	/**
	 * @param lines
	 * @param fileProcessor
	 * @param input
	 * @return List of strings
	 */
	private ArrayList<String> eventsByFieldMenu(ArrayList<String> lines, FileProcessor fileProcessor, Scanner input){
		String field;
		ArrayList<String> linesToDisplay = new ArrayList<String>();
    	System.out.println("Please enter the name of the field to return");
    	field = input.nextLine();
    	//call method with field
    	linesToDisplay = fileProcessor.eventsByField(lines, field);
    	return linesToDisplay;
	}
	
	/**
	 * @param lines
	 * @param fileProcessor
	 * @param input
	 * @return List of strings
	 */
	private ArrayList<String> eventsBetweenTimestampsMenu(ArrayList<String> lines, FileProcessor fileProcessor, Scanner input){
		String startTimeStamp;
    	String endTimeStamp;
    	ArrayList<String> linesToDisplay = new ArrayList<String>();
    	System.out.println("Please enter the start timestamp");
    	startTimeStamp = input.nextLine();
    	System.out.println("Please enter the end timestamp");
    	endTimeStamp = input.nextLine();
    	linesToDisplay = fileProcessor.eventsBetweenTimestamps(lines, startTimeStamp, endTimeStamp);
    	return linesToDisplay;
	}
}

