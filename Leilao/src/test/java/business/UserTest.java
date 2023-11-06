package business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	
	Utilizador utilizador;

	@Before
	public void setUp() throws Exception {
		utilizador = new Utilizador(1, "Ana", 3, "password1");
	}

	@Test
	public void testID() {
			int expected = 1;
		int actual = utilizador.getID();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testReputacao() {
			int expected = 3;
		int actual = utilizador.getReputacao();
		assertEquals(expected, actual);
	}

}
