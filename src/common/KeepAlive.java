package common;

public class KeepAlive implements Packets{

	//TODO: add functionality if needed. Probably remove QueuePos since we are using a blocking queue. Checking position keeps the queue from giving tasks.
	//TODO: this may also go away if we are using a disconnect reconnect structure.
	private static final long serialVersionUID = 1L;
	long QueuePos;
	
	public KeepAlive(){
		QueuePos = 0;
	}
	
	public KeepAlive(long Q){
		QueuePos = Q;
	}
	
	public long getPos(){
	return QueuePos;
	}
	
	public void setPos(long L){
		QueuePos=L;
	}
	
	
	
}
