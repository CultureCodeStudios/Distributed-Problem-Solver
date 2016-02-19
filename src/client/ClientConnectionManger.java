package client;

import java.io.IOException;

import problemModule.ProblemModule;

public interface ClientConnectionManger {

	public void close()throws IOException;

	public Object readObject() throws ClassNotFoundException, IOException ;

	public void writeObject(ProblemModule task)throws IOException;

}
