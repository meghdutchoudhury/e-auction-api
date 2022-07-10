package com.fse.eauction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceOperationNotPermittedException extends RuntimeException{
	
	private static final long serialVersionUID = -4023052678698090106L;

	public ResourceOperationNotPermittedException() {
		super();
	}

	public ResourceOperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceOperationNotPermittedException(String message) {
		super(message);
	}

	public ResourceOperationNotPermittedException(Throwable cause) {
		super(cause);
	}

}
