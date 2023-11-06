package business;
/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe é uma extençao do Artigo e define se é um artigo individual
 * 
 */
public class Individual extends Artigo {
	
	
	/**
	 * Construtor da classe individual
	 * @param idArtigo int id do artigo
	 * @param nome String com o nome do artigo
	 * @param descricao String com a descriçao do artigo
	 */
	public Individual(int idArtigo, String nome, String descricao) {
		super(idArtigo, nome, descricao);

	}

}
