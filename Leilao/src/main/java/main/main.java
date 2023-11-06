package main;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Scanner;

import business.Utilizador;
import business.Artigo;
import business.Composto;
import business.Individual;
import business.Leilao;
import business.TipoLeilao;

/**
 *  Grupo: ADS 15
 * 	@author Ana Nunes 51596
 * 	@author Emanuel Nunes 44713
 *  @author Pedro Barata 49763
 *  Main da classe leiloes
 * 
 */


public class main {
	
	static ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();
	static ArrayList<Artigo> artigos= new ArrayList<Artigo>();
	static ArrayList<Leilao> leiloes= new ArrayList<Leilao>();
	
	public static void main(String[] args) {
		
		Utilizador user1 = new Utilizador(utilizadores.size()+1, "Ana", 3, "password1");
		utilizadores.add(user1);
		Utilizador user2 = new Utilizador(utilizadores.size()+1, "Ana", 3, "password2");
		utilizadores.add(user2);
		
		
		//Leilao nLeilao = user1.criarLeilao(leiloes.size()+1, "Leilao numero 1", 500.0);
		leiloes.add(user1.criarLeilao(leiloes.size()+1, "Leilao numero 1", 500.0));
		//Leilao nLeilao = user2.criarLeilao(leiloes.size()+1, "Leilao numero 2 tipo batata", 500.0);
		leiloes.add(user2.criarLeilao(leiloes.size()+1, "Leilao numero 2 tipo batata", 630.0));
		
		
		Artigo artigo1 = new Individual(artigos.size()+1, "gelado", "gelado de morango");
		artigos.add(artigo1);
		Artigo artigo2 = new Individual(artigos.size()+1, "coco", "gelado de coco");
		artigos.add(artigo2);
		
		Composto composto1 = new Composto(artigos.size()+1, "gelado de morango e coco", "Morango, coco");
		composto1.addArtigo(artigo1);
		composto1.addArtigo(artigo2);
		
		user1.addArtigosLista(artigo2);
		leiloes.get(0).setArtigosLeilao(artigo2);

		leiloes.get(1).setArtigosLeilao(composto1);
		System.out.println(user1.getLeiloes());
		System.out.println(user2.getLeiloes());
		
		
		
		
		int idleilao = 0;
		//user1.publicarLeilao(leiloes.get(idleilao), 10,15,19);
		user1.publicarLeilao(leiloes.get(idleilao), 19,23,50);// tem de ter 2 digitos sempre tanto na hora, como no minuto, e no segundo
		
		
		
		System.out.println("Leilao "+ (idleilao + 1));
		System.out.println(leiloes.get(idleilao).getValorAtual());
		
		int id=0;
		double value=0;
		String password="";
		Scanner sc = new Scanner(System.in);
		while (leiloes.get(idleilao).getTipo() != TipoLeilao.FECHADO){
			System.out.println("\n\n");
			System.out.println("Valor atual do leilão");
			System.out.println(leiloes.get(idleilao).getValorAtual());
			System.out.println("insira o seu utiliador 1 ou 2\n");
			id = sc.nextInt();
			System.out.println("insira o valor a licitar\n");
			value= sc.nextDouble();

			System.out.println("insira a password do user: "+id+"\n");
			password = sc.next();
				switch (id) {
				case 1:
					if (user1.auth(password)){
						user1.licitar(leiloes.get(idleilao), value);
					}else{
						System.out.println("Erro na autenticação");
					}
					break;
					
				case 2:
					if (user2.auth(password)){
						user2.licitar(leiloes.get(idleilao), value);
					}else{
						System.out.println("Erro na autenticação");
					}
					break;

				default:
					System.out.println("Utilizador não encontrado");
					break;
				}
			password="";

		}
		
	}

}
