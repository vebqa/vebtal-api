package org.vebqa.vebtal;

public class KeywordEntry {

	private String module;
	private String command;
	private String hintTarget;
	private String hintValue;

	public KeywordEntry(String aModule, String aCommand, String aHintTarget, String aHintValue) {
		this.module = aModule;
		this.command = aCommand;
		this.hintTarget = aHintTarget;
		this.hintValue = aHintValue;
	}
	
	public String getModule() {
		return module;
	}

	public String getCommand() {
		return command;
	}

	public String getHintTarget() {
		return hintTarget;
	}

	public String getHintValue() {
		return hintValue;
	}

}
