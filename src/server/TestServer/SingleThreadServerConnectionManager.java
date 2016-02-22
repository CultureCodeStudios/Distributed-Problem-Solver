package server.TestServer;

import java.io.IOException;

import problemModule.ProblemModule;
import problemModule.TestProblemModule;

public interface SingleThreadServerConnectionManager {

	void writeObject(ProblemModule task) throws IOException;
	Object readObject() throws ClassNotFoundException, IOException;
	void writeObject(TestProblemModule task) throws IOException;
	void close() throws IOException;
	void ServerShutdown() throws IOException;
	
}
