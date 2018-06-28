package org.vebqa.vebtal.command;

import org.vebqa.vebtal.model.CommandType;

public interface ICommand {
	
	public CommandType getType();
	
	// abstract Response executeImpl(ISut aSut);

}
