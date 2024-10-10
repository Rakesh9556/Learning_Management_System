package com.lms.util;

public class DuplicateEntryException  extends Exception{
	private static final long serialVersionUID = -1251686024733336924L;

	public DuplicateEntryException(String message, Throwable cause) {
		super(message, cause);
	}
}
