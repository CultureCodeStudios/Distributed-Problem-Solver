package common;

public class Status implements Packets {
	
	
	//TODO: Can change this, it's implemented but the status codes are not used for anything other than printing.
	/**
	 * currently -1 for busy. +1 for ready; 0 is reserved for server.
	 */
	private static final long serialVersionUID = 1L;
	private int Status;
	
	public Status(int stat){
		Status = stat;
	}
	
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
}
