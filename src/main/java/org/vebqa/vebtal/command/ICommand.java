package org.vebqa.vebtal.command;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.sut.ISut;

public interface ICommand {
	
	abstract Response executeImpl(ISut aSut);

}
