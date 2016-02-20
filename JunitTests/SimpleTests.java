import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import client.ModularTestClient;

public class SimpleTests {

	@Test
	public void DefaultConfigTest() throws UnknownHostException, IOException, ClassNotFoundException {
		ModularTestClient MTC = new ModularTestClient();
		MTC.startWithDefaults(null, 9090, 2, 3);
		assertTrue(MTC.getSuccess());
	}

}
