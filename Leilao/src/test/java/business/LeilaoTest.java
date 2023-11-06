package business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LeilaoTest {
	
	Leilao leilao;
	
	@Before
	
	public void setUp() throws Exception {
		leilao = new Leilao(10, "Leilao numero 1", 500.0);
	}

	@Test
	public void testID() {
			int expected = 10;
		int actual = leilao.getID();
		assertEquals(expected, actual);
	}
}
