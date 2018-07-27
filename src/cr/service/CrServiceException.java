package cr.service;

@SuppressWarnings("serial")
public class CrServiceException extends Exception {
	
	public CrServiceException(String msg) {
		super(msg);
	}
	
	public CrServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
