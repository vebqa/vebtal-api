package org.vebqa.vebtal.command;

import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;

public interface ICommand {
	
	public CommandType getType();
	
	public Response executeImpl(Object aDriver);

}
