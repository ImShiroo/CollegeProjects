package business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LicitacaoTest {
	Licitacao licitacao;
	Leilao leilao;
	Utilizador utilizador;
	
	@Before
	public void setUp() throws Exception {
		leilao = new Leilao(10, "Leilao numero 1", 500.0);
		utilizador = new Utilizador(1, "Ana", 3, "password1");
		licitacao = new Licitacao(leilao, 600, utilizador.getID());
	}

	@Test
	public void testIDLicitador() {
		int expected = 1;
	int actual = licitacao.getIDuser();
	assertEquals(expected, actual);
	}
}
