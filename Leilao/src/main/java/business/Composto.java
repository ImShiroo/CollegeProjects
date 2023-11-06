package business;

import java.util.ArrayList;
/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe � uma exten�ao do Artigo e define se � um artigo Composto
 * 
 */

public class Composto extends Artigo {
	
	private static ArrayList<Artigo> artigosCompostos = new ArrayList<Artigo>();
	
	
	/**
	 * Construtor da classe Composto
	 * @param idArtigo int id do artigo
	 * @param nome String com o nome do artigo
	 * @param descricao String com a descri�ao do artigo
	 */
	public Composto(int idArtigo, String nome, String descricao) {
		super(idArtigo, nome, descricao);
		
	}
	
	
	/**
	 * Esta fun�ao guarda o artigo numa cole�ao de artigos
	 * @param artigo obj artigo
	 * @requires artigo != null
	 */
	public void addArtigo(Artigo artigo) {
		artigosCompostos.add(artigo);
	}
	
	/**
	 * esta fun�ao remove os artigos da cole�ao de artigos
	 * @param artigo
	 * @requires artigo != null
	 */
	public void remArtigo(Artigo artigo) {
		for(int i = 0; i <= artigosCompostos.size(); i++) {
			if(artigosCompostos.get(i).getID() == artigo.getID()) {
				artigosCompostos.remove(i);
			}
		}
	}

}
