package business;

import java.util.*;
/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe define tudo o que � comum �s licita��es dos leil�es.
 * 
 */
public class Licitacao {
	
	private double valor;
	private Leilao leilao;
	private int idUser;


	/**
	 * Construtor do objecto leilao
	 * @param leilao id do leilao a que vai afetar
	 * @param valor contem o valor da licitacao que vai ser feita
	 * @param iduser contem o id do utilizador que vai licitar
	 */
	public Licitacao(Leilao leilao, double valor, int iduser) {
		this.valor = valor;
		this.leilao = leilao;
		this.idUser = iduser;
	}
	
	/**
	 * devolve o valor desta licita�ao
	 * @return valor int
	 */
	public Double getValor() {
		return this.valor;
	}
	
	/**
	 * devolve o id do utilizador que fez esta licita�ao
	 * @return idIser int
	 */
	public int getIDuser() {
		return this.idUser;
	}
	
	
}
