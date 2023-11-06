package business;

import java.time.LocalTime;
import java.util.HashMap;
/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Esta classe define tudo o que é comum a Leilões
 * 
 */
public class Leilao {
	
	private int idLeilao;
	private TipoLeilao tipo;
	private double valorBase;
	private LocalTime deadline;
	private String titulo;
	private Artigo artigos;
	HashMap<Double, Integer> participantes;
	private int vencedor = 0;
	
	/**
	 * Construtor da classe Leilao
	 * @param idLeilao int id do leilao
	 * @param titulo String com o titulo do leilao
	 * @param valor double com o valor base do leilao
	 */ 
	public Leilao(int idLeilao, String titulo, double valor) {
		this.titulo = titulo;
		this.idLeilao = idLeilao;
		this.valorBase = valor;
		//this.artigos = artigos;
		
		participantes = new HashMap<Double, Integer>();
	}
	/**
	 * define os artigos do leilao
	 * @param artigos do tipo Artigo
	 * @requires artigos != null
	 */
	public void setArtigosLeilao(Artigo artigos) {
		this.artigos = artigos;
	}
	
	/**
	 * Devolve os artigos do leilao
	 * @return artigos do tipo Artigo
	 */
	public Artigo getArtigosLeiloes() {
		return this.artigos;
	}
	
	/**
	 * devolve o titulo do Leilao
	 * @return String titulo
	 */
	public String getTitulo() {
		return this.titulo;
	}
	
	/**
	 * define o estado do leilao
	 * @param tipo enum do TipoLeilao
	 */
	public void setTipo(TipoLeilao tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * devolve o id do leilao
	 * @return id do leilao int
	 */
	public int getID() {
		return this.idLeilao;
	}
	
	/**
	 * devolve o estado atual do leilao
	 * @return o estado do leilao
	 */
	public TipoLeilao getTipo() {
		return this.tipo;
	}
	
	/**
	 * Define o estado do leilao publicado
	 * @return True
	 */
	public boolean publicar() {
		this.setTipo(TipoLeilao.PUBLICADO);
		return true;
	}
	
	/**
	 * Define o estado do leilao como fechado
	 * informa o utilizador
	 * @return True
	 */
	public boolean fechar() {
		this.setTipo(TipoLeilao.FECHADO);
		System.out.println("Leilão acabou de fechar.");
		System.out.println("O vencedor tem o ID: "+this.vencedor);
		return true;
	}
	
	/**
	 * define o tempo do deadline do leilao
	 * @param time
	 * @requires time != null
	 */
	public void setTime(LocalTime time) {
		this.deadline = time;
	}
	
	/**
	 * devole o momento em que o leilao encerra
	 * @return o tempo final do leilao em que encerra
	 */
	public LocalTime getTime() {
		return this.deadline;
		
	}
	/**
	 * esta funçao valida se a licitaçao que o utilizador está a fazer é superior ao valor atual do leilao
	 * 
	 * @param valor double valor a licitar
	 * @param iduser int id do user
	 * @return true se é uma licitaçao valida caso contrario Falso
	 */
	public boolean validar(double valor, int iduser) {
		LocalTime deadline2 = LocalTime.parse(deadline.getHour()+":"+deadline.getMinute()+":"+deadline.getSecond());
		if (!(LocalTime.now().isAfter(deadline2))) {
			if(valor > this.valorBase) {
				concretizarLicitacao(valor, iduser);
				return true;
			} else {
				System.out.println("Licitação rejeitada.");
				return false;
			}
		} else {
			if(participantes.containsKey(valorBase)) {
				this.vencedor = participantes.get(valorBase);
			}
			fechar();
		}
		return false;
	}
			

	/**
	 * atualiza o valor atual em que o leilao vai durante o decorrer do leilao
	 * 
	 * @param valor double valor a licitar
	 * @param iduser int id do user
	 * @requires validar(valor, iduser) == True
	 * @return true ao atualizar a licitaçao
	 */
	public boolean concretizarLicitacao(double valor, int iduser) {
		participantes.put(valor, iduser);
		this.valorBase = valor;
		System.out.println("Licitação enviada.");
		return true;
	}
	
	/**
	 * devolve o valor do leilão
	 * @return o valor atual do leilao
	 */
	public double getValorAtual(){
		return this.valorBase;
	}
	/**
	 * Devolve a representaçao do leilao
	 */
	public String toString(){
		String a = "";
		a+= "Id Leilão: "+ this.idLeilao;
		a+= "\nEstado do Leilao: "+this.tipo;
		a+= "\nValor base do Leilão: "+this.valorBase;
		a+= "\nData de Fecho do Leilao: "+ this.deadline;
		a+= "\nNome do Leilão: " + this.titulo;
		a+= "\nArtigos do Leilão: " + this.artigos;
		
		if (this.vencedor != 0){
			a+= "\nO vencedor deste leilão eh: " + this.vencedor;
		}else{
			a+= "\nAinda nao existe vencedor";
		}
		return a+"\n --- \n";
	}
	
	

	
	
	
}
