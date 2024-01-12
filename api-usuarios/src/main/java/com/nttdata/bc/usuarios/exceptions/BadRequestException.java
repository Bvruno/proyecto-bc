package com.nttdata.bc.usuarios.exceptions;

import java.io.Serial;

public class BadRequestException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION = "Bad Request Exception";

	public BadRequestException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}


}
