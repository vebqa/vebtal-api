package org.vebqa.vebtal;

import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.Response;

public interface TestAdaptionResource {

	public Response execute(Command aCmd);
}
