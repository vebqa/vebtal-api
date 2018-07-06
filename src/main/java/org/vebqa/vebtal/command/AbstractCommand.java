package org.vebqa.vebtal.command;

import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;

public abstract class AbstractCommand implements ICommand {
	
	protected final String command;
	protected final String target;
	protected final String value;
	
	protected CommandType type;
	
	public AbstractCommand(String aCommand, String aTarget, String aValue) {
		this.command = aCommand.trim();
		this.target = aTarget.trim();
		this.value = aValue.trim();
	}
	
	public abstract Response executeImpl(Object driver);
	
	public CommandType getType() {
		return this.type;
	}	
}
