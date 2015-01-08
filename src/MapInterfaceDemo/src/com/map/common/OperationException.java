package com.map.common;

public class OperationException extends RuntimeException {
	
	private String errorCode;

	private static final long serialVersionUID = 9105899161956614203L;

	public OperationException(){
		
	}
	
	public OperationException(String msg){
		super(msg);
	}
	
	public OperationException(Throwable t){
		super(t);
	}
	
	
	public OperationException(String errorCode, String msg){
		super(msg);
		// set the error code
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
