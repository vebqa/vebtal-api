package org.vebqa.vebtal;

import java.util.Calendar;

import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.Response;

public class AbstractTestAdaptionResource implements TestAdaptionResource {

	private Long startTime;
	private Long finishTime;
	
	public AbstractTestAdaptionResource() {
		startTime = 0L;
		finishTime = 0L;
	}
	
	@Override
	public Response execute(Command aCmd) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCommandClassName(Command aCmd) {
		// Test - to be refactored
		// Command instanziieren
		// erst alles klein schreiben
		String tCmd = aCmd.getCommand().toLowerCase().trim();
		// erster Buchstabe gross
		String cmdFL = tCmd.substring(0, 1).toUpperCase(); 
		String cmdRest = tCmd.substring(1);
		return cmdFL + cmdRest;
	}

	
	@Override
	public Long getDuration() {
		return finishTime - startTime;
	}

	@Override
	public void setStart() {
		startTime = Calendar.getInstance().getTimeInMillis();
	}

	@Override
	public void setFinished() {
		finishTime = Calendar.getInstance().getTimeInMillis();
	}

}
