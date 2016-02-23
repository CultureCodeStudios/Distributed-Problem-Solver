import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import client.ModularTestClient;

public class SimpleTests {
	//TODO: this should be a series of JUnit tests that test the very basic aspects of the system to make sure changes don't break the basics.

	@Test
	public void DefaultConfigTest() throws UnknownHostException, IOException, ClassNotFoundException {
		ModularTestClient MTC = new ModularTestClient();
		MTC.startWithDefaults(null, 9090, 2, 3);
		assertTrue(MTC.getSuccess());
	}

}
