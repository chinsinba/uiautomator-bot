package in.BBAT.TestRunner.device;

public class DeviceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceException(String message, Throwable e) {
		super(message, e);
	}
	public DeviceException(String message) {
		super(message);
	}
	
}
