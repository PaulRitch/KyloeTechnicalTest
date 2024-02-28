import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
	/**
	 * @param lines
	 * @param type
	 * @return List of strings
	 */
	public ArrayList<String> eventsByType(ArrayList<String> lines, String type) {
		ArrayList<String> linesToDisplay = new ArrayList<String>();
		lines.forEach(line -> {
			String[] entityParts = splitIgnoreInBrackets(line);
			if (entityParts[0].equals(type)) {
				linesToDisplay.add(line);
			}
		});
		return linesToDisplay;
	}
	
	/**
	 * @param lines
	 * @param field
	 * @return List of strings
	 */
	public ArrayList<String> eventsByField(ArrayList<String> lines, String field) {
		ArrayList<String> linesToDisplay = new ArrayList<String>();
		if (field.equals("") || field.equals(" ") || field.equals(", ") || field.equals(",")) {
			return linesToDisplay;
		}
		lines.forEach(line -> {
			String[] entityParts = splitIgnoreInBrackets(line);
			if (entityParts[3].toLowerCase().contains(field.toLowerCase())) {
				linesToDisplay.add(line);
			}
		});
		return linesToDisplay;
	}
	
	/**
	 * @param lines
	 * @param startTimeStamp
	 * @param endTimeStamp
	 * @return List of strings
	 */
	public ArrayList<String> eventsBetweenTimestamps(ArrayList<String> lines, String startTimeStamp, String endTimeStamp) {
		ArrayList<String> linesToDisplay = new ArrayList<String>();

		try {
			Timestamp startTimestamp = Timestamp.valueOf(startTimeStamp);
			Timestamp endTimestamp = Timestamp.valueOf(endTimeStamp);
			if (startTimestamp.after(endTimestamp)) {
				System.out.println("Start timestamp is after end timestamp");
				return linesToDisplay;
			}
			lines.forEach(line -> {
				String[] entityParts = splitIgnoreInBrackets(line);
				Timestamp timestamp = Timestamp.valueOf(entityParts[4]);
				if ((timestamp.after(startTimestamp)|| timestamp.equals(startTimestamp)) && (timestamp.before(endTimestamp) || timestamp.equals(endTimestamp))) {
					linesToDisplay.add(line);
				}
			});
		} catch(Exception e) {
			System.out.println("Invalid timestamp" + e);
		}
		return linesToDisplay;
	}
	
	/**
	 * @param input
	 * @return Array of strings
	 */
	private String[] splitIgnoreInBrackets(String input) {
		List<String> parts = new ArrayList<String>();
		int startPosition = 0;
		boolean isInBrackets = false;
		for (int currentPosition = 0; currentPosition < input.length(); currentPosition++) {
		    if (input.charAt(currentPosition) == '[') {
		        isInBrackets = true;
		    }
		    else if (input.charAt(currentPosition) == ']') {
		    	isInBrackets = false;
		    }
		    else if (input.charAt(currentPosition) == ',' && !isInBrackets) {
		        parts.add(input.substring(startPosition, currentPosition));
		        startPosition = currentPosition + 1;
		    }
		}
		
	    parts.add(input.substring(startPosition));
		
		return parts.toArray(new String[parts.size()]);
	}

}
