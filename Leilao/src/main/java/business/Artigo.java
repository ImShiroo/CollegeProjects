package business;
/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe define tudo o que é comum a vários tipos de artigos.
 * 
 */
public class Artigo {
	
	private String nome;
	private String descricao;
	private int idArtigo;

	/**
	 * Cria o objecto artigo.
	 * @param idArtigo inteiro com o id do artigo
	 * @param nome String com o nome do artigo
	 * @param descricao String com a descricao do artigo
	 */
	public Artigo(int idArtigo, String nome, String descricao) {
		this.idArtigo = idArtigo;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	/**
	 * Retorna o nome do objecto artigo
	 * @return name String
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Retorna a descrição do objecto artigo
	 * @return descricao String
	 */
	public String getDescr() {
		return this.descricao;
	}
	
	/**
	 * Retorna o id do objecto artigo
	 * @return id int
	 */
	public int getID() {
		return this.idArtigo;
	}
	/**
	 * Devolve a representaçao do artigo
	 */
	public String toString(){
		return "ID do Artigo: " + this.idArtigo + " Nome do Artigo: " + this.nome + " Descricao do Artigo: "+ this.descricao;
	}
	

}
