package common;

public class KeepAlive implements Packets{

	private static final long serialVersionUID = 1L;
	long QueuePos;
	
	public long getPos(){
	return QueuePos;
	}
	
	public void setPos(long L){
		QueuePos=L;
	}
	
	
}
