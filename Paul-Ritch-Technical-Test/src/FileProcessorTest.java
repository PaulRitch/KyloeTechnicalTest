import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FileProcessorTest {

	@Test
	final void testEventsByType() {
		final FileProcessor  fileProcessor = new FileProcessor();
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		lines.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		lines.add("DELETED, Placement, 13, null, 2018-04-10 12:53:10.123");
		ArrayList<String> insertedResult = new ArrayList<String>();
		insertedResult.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		ArrayList<String> updatedResult = new ArrayList<String>();
		updatedResult.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		ArrayList<String> deletedResult = new ArrayList<String>();
		deletedResult.add("DELETED, Placement, 13, null, 2018-04-10 12:53:10.123");
		assertEquals(insertedResult, fileProcessor.eventsByType(lines, "INSERTED"));
		assertEquals(updatedResult, fileProcessor.eventsByType(lines, "UPDATED"));
		assertEquals(deletedResult, fileProcessor.eventsByType(lines, "DELETED"));
	}

	@Test
	final void testEventsByField() {
		final FileProcessor  fileProcessor = new FileProcessor();
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		lines.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		lines.add("UPDATED, Placement, 13, [status, hoursPerDay, overtimeRate], 2018-04-10 14:52:43.699");
		ArrayList<String> statusResult = new ArrayList<String>();
		statusResult.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		statusResult.add("UPDATED, Placement, 13, [status, hoursPerDay, overtimeRate], 2018-04-10 14:52:43.699");
		ArrayList<String> companyUrlResult = new ArrayList<String>();
		companyUrlResult.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		ArrayList<String> overtimeRateResult = new ArrayList<String>();
		overtimeRateResult.add("UPDATED, Placement, 13, [status, hoursPerDay, overtimeRate], 2018-04-10 14:52:43.699");
		ArrayList<String> noResult = new ArrayList<String>();
		assertEquals(statusResult, fileProcessor.eventsByField(lines, "Status"));
		assertEquals(statusResult, fileProcessor.eventsByField(lines, "status"));
		assertEquals(statusResult, fileProcessor.eventsByField(lines, "STATUS"));
		assertEquals(companyUrlResult, fileProcessor.eventsByField(lines, "CompanyURL"));
		assertEquals(companyUrlResult, fileProcessor.eventsByField(lines, "companyUrl"));
		assertEquals(companyUrlResult, fileProcessor.eventsByField(lines, "companyurl"));
		assertEquals(overtimeRateResult, fileProcessor.eventsByField(lines, "overtimeRate"));
		assertEquals(overtimeRateResult, fileProcessor.eventsByField(lines, "OverTimeRate"));
		assertEquals(overtimeRateResult, fileProcessor.eventsByField(lines, "overtimerate"));
		assertEquals(noResult, fileProcessor.eventsByField(lines, "test"));
		assertEquals(noResult, fileProcessor.eventsByField(lines, ""));
		assertEquals(noResult, fileProcessor.eventsByField(lines, " "));
		assertEquals(noResult, fileProcessor.eventsByField(lines, ", "));
		assertEquals(noResult, fileProcessor.eventsByField(lines, ","));
	}

	@Test
	final void testEventsBetweenTimestamps() {
		final FileProcessor  fileProcessor = new FileProcessor();
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		lines.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		lines.add("DELETED, Placement, 13, null, 2018-04-10 12:53:10.123");
		ArrayList<String> firstResult = new ArrayList<String>();
		firstResult.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		ArrayList<String> secondResult = new ArrayList<String>();
		secondResult.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		secondResult.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		secondResult.add("DELETED, Placement, 13, null, 2018-04-10 12:53:10.123");
		ArrayList<String> thridResult = new ArrayList<String>();
		thridResult.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		thridResult.add("UPDATED, Company, 123, [status, companyUrl], 2018-04-10 12:44:00.123");
		ArrayList<String> fourthResult = new ArrayList<String>();
		ArrayList<String> fifthResult = new ArrayList<String>();
		fifthResult.add("INSERTED, Placement, 12, null, 2018-04-10 12:34:56.789");
		assertEquals(firstResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:30:56.789", "2018-04-10 12:38:56.789"));
		assertEquals(secondResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:30:56.789", "2018-04-10 12:55:10.123"));
		assertEquals(thridResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:34:56.789" , "2018-04-10 12:44:00.123"));
		assertEquals(fourthResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:54:56.789" , "2018-04-10 12:44:00.123"));
		assertEquals(fifthResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:34:56.789" , "2018-04-10 12:34:56.789"));
		assertEquals(fourthResult, fileProcessor.eventsBetweenTimestamps(lines, "2018-04-10 12:54:56.789" , "365673573"));
		assertEquals(fourthResult, fileProcessor.eventsBetweenTimestamps(lines, "4368349676", "2018-04-10 12:54:56.789"));
	}

}
