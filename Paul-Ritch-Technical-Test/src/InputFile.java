import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class InputFile {
	private ArrayList<String> lines = new ArrayList<String>();
	
	/**
	 * @return lines
	 */
	public ArrayList<String> getLines() {
		return this.lines;
	}
	
	/**
	 * @param input
	 * @return false if successful and true if not
	 */
	public boolean readFile(Scanner input) {
		String filePath;
		System.out.println("Please enter the filepath");
		filePath = input.nextLine();
		try {
			File file = new File(filePath);
			Scanner fileReader = new Scanner(file);
			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				lines.add(line);      
			}
			fileReader.close();
			return false;
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred. Filepath: " + filePath);
	    	return true;
	    }
		
	}
}
