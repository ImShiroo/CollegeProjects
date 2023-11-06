package business;


import java.time.LocalTime;
import java.util.ArrayList;

/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe define o utilizador
 * 
 */

public class Utilizador {
	
	private int idUser;
	private int reputacao;
	private String nome;
	private String pass;
	private ArrayList<Leilao> leiloesLista;
	private ArrayList<Artigo> artigosLista;

	/**
	 * Construtor da Classe Utilizador
	 * @param idUser int com o id do utilizador
	 * @param nome String com o nome do utilizador
	 * @param reputacao int com a reputaçao do utilizador
	 * @param passwd String com a password do user
	 * @required idUser != null
	 * @requires nome !=""
	 * @requires reputacao != null
	 */
	public Utilizador(int idUser, String nome, int reputacao, String passwd) {
		this.idUser = idUser;
		this.reputacao = reputacao;
		this.nome = nome;
		this.pass = passwd;
		this.artigosLista= new ArrayList<Artigo>();
		this.leiloesLista = new ArrayList<Leilao>();
	}
	
	/**
	 * Devolve o nome do utilizador
	 * @return nome String
	 */
	public String getName() {
		return this.nome;
	}
	
	/**
	 * Autentica o utilizador
	 * @param passwd != ""
	 * @return true se a password for correcta senao False
	 */
	public boolean auth(String passwd){
		return passwd.equals(this.pass);
	}
	/**
	 * Devolve o id do Utilizador
	 * @return id int
	 */
	public int getID() {
		return this.idUser;
	}
	/**
	 * Devolve a Reputação do utilizador
	 * @return reputação int
	 */
	public int getReputacao() {
		return this.reputacao;
	}
	/**
	 * Recebe um artigo e guarda o numa lista de artigos do utilizador
	 * @param artigos objecto 
	 * @requires artigos != null
	 */
	public void addArtigosLista(Artigo artigos) {
		artigosLista.add(artigos);
	}
	/**
	 * Esta função cria um leilão
	 * @param idLeilao
	 * @param titulo String com o titulo do leilao
	 * @param valor float com o valor base do leilão
	 * @requires idLeilao != null
	 * @requires titulo !=""
	 * @requires valor != null
	 * @return um objecto do tipo Leilao
	 */
	public Leilao criarLeilao(int idLeilao, String titulo, double valor) {
		Leilao leilao = new Leilao(idLeilao, titulo, valor);
		leilao.setTipo(TipoLeilao.CRIADO);
		this.leiloesLista.add(leilao); 
		return leilao;
	}
	/**
	 * Esta função adiciona os artigos ao leilao criado anteriormente
	 * @param leilao do tipo Leilao e contem um objecto do tipo Leilao
	 * @param artigos do tipo Artigo e contem um objecto do tipo Artigo
	 * @requires leilao != null
	 * @requires artigos != null
	 */
	public void setArtigos(Leilao leilao, Artigo artigos) {
		if(artigosLista.contains(artigos)) {
			leilao.setArtigosLeilao(artigos);
		} else {
			System.out.println("artigo ainda nao pertence à lista deste utilizador");
		}
	}
	/**
	 * Devolve a Lista de todos os leiloes do utilizador
	 * @return LeiloesLista do tipo ArrayList
	 */
	public ArrayList<Leilao> getLeiloes() {
		if (this.leiloesLista.size() > 0) {
			return this.leiloesLista;
		}else
		{
			return null;
		}
	}
	/**
	 * Devolve a Lista de todos os Artigos do Utilizador
	 * @return artigosLista do tipo ArrayList
	 */
	public ArrayList<Artigo> getArtigos() {
		return this.artigosLista;
	}
	
	/**
	 * Publica o Leilao de forma a ficar ativo e poder ser licitado
	 * @param leilao do tipo Leilao 
	 * @param time do tipo LocalTime que é o tempo a que o leilao vai acabar (data e hora)
	 * @param hora int com as horas a que acaba o leilao
	 * @param min int com os minutos a que acaba o leilao
	 * @param seg int com os segundos a que acaba o leilao
	 * @requires leilao != null
	 * @requires time != null
	 */
	public void publicarLeilao(Leilao leilao, int hora, int min, int sec) {
		
		//LocalTime time2 = LocalTime.parse(time.getHour()+":"+time.getMinute()+":"+time.getSecond());
		LocalTime time = LocalTime.parse(hora+":"+min+":"+sec);
		if (!(time.isBefore(LocalTime.now()))) {
			if (getLeiloes().contains(leilao)) {
				leilao.publicar();
				leilao.setTime(time);
				System.out.println("Leilão publicado com sucesso!");
			} else {
				System.out.println("Leilão nao pertence a este utilizador");
			}
		} else {
			System.out.println("Valor de tempo inválido.");
		}
	}
	
	/**
	 * Esta função serve para Licitar um dado leilao por um determinado valor
	 * @param leilao do tipo Leilao 
	 * @param valor double valor com que vai licitar
	 */
	public void licitar(Leilao leilao, double valor) {
		if(!(getLeiloes().contains(leilao)) ) {
			if(leilao.getTipo() == TipoLeilao.PUBLICADO){
				Licitacao lic = new Licitacao(leilao, valor, this.getID());
				leilao.validar(lic.getValor(), this.getID());
			}else{
				System.out.println("Leilão encontra-se fechado.");
			}
		} else {
			System.out.println("Não pode licitar o seu proprio Leilão");
		}	
	}
	
	/**
	 * Devolve a representaçao do utilizador
	 */
	public String toString(){
		return "Id utilizador: "+ this.idUser + " Nome: "+ this.nome + " Reputação: "+ this.reputacao + " Estrelas";
	}

}
