package org.veritasopher.mypubservice.exception;

public class WorkspaceFileNotFoundException extends WorkspaceException {

	public WorkspaceFileNotFoundException(String message) {
		super(message);
	}

	public WorkspaceFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
