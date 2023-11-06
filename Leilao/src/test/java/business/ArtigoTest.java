package business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArtigoTest {
	Artigo artigoIndividual;
	Artigo artigoComposto;
	
	@Before
	public void setUp() throws Exception {
		artigoIndividual = new Individual(2, "gelado", "gelado de morango");
		artigoComposto = new Composto(3, "gelado de morango e coco", "Morango, coco");
	}

	@Test
	public void testIndividual() {
			int expected = 2;
		int actual = artigoIndividual.getID();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testComposto() {
			int expected = 3;
		int actual = artigoComposto.getID();
		assertEquals(expected, actual);
	}
}
