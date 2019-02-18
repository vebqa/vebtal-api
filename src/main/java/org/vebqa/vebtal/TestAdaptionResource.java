package org.vebqa.vebtal;

import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.Response;

public interface TestAdaptionResource {

	Response execute(Command aCmd);
	
	String getCommandClassName(Command aCmd);
	
	Long getDuration();
	
	void setStart();
	
	void setFinished();
}