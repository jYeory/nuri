package com.nuri.common.exception;


public class DefaultException extends Exception {
	private String message = null;
	
	private static final long serialVersionUID = 1L;

	public DefaultException(){
		super();
		System.out.println("HijackThrowException : Throw exception hijacked!");
	}
	
	public DefaultException(String message){
		super(message);
		this.message = message;
		System.out.println("HijackThrowException : Throw exception hijacked!");
	}
	
	public DefaultException(Throwable cause){
		super(cause);
		System.out.println("HijackThrowException : Throw exception hijacked!");
	}
	
	@Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
