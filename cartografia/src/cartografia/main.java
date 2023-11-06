package cartografia;

import java.util.ArrayList;
import java.util.Scanner;


public class main {

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		System.out.println(" --------------------------------------------");
		System.out.println("| Trabalho realizado por: Ana Nunes 51596 LTI |");
		System.out.println(" --------------------------------------------");
		System.out.println("\nA transformação que pretende consiste em transformar coordenadas:");
		System.out.println("(1) - num mesmo datum");
		System.out.println("(2) - entre data distintos");
		String number2 = userInput.nextLine();
		int numberInserido2 = Integer.parseInt(number2);
		
		//primeira parte
		if (numberInserido2 == 1) {
			System.out.println("Escolha o numero da ação que deseja fazer:");
			System.out.println("(1) - Transformar as coordenadas geográficas para retangulares -> (M,P)"); // Geográficas/Retangulares
			System.out.println("(2) - Transformar as coordenadas retangulares para geográficas -> [aa]º [bb]' [cc]'' [NWSE]"); // Retangulares/Geográficas
			System.out.println("(3) - Transformar as coordenadas geográficas para cartesianas tridimensionais - (X,Y,Z)"); // Geográficas/Cartesianas
			System.out.println("(4) - Transformar as coordenadas cartesianas tridimensionais para geográficas - (latitude,longitude,h)"); // Cartesianas/Geográficas
			System.out.println("(5) - Transformar as coordenadas cartesianas tridimensionais para retangulares - (M,P)"); // EXERCICIO 4 + EXERCICIO 1
			System.out.println("(6) - Transformar as coordenadas retangulares para cartesianas tridimensionais - (X,Y,Z)"); // EXERCICIO 2 + EXERCICIO 3
			System.out.println("(7) - Sair");
			
			String number = userInput.nextLine();
			int numberInserido = Integer.parseInt(number);
			
			if (numberInserido == 1) {	
				primeiraParte.exercicio1(args, userInput);
						
			} else if (numberInserido == 2) {
				primeiraParte.exercicio2(args, userInput);
					
			} else if (numberInserido == 3) {
				primeiraParte.exercicio3(args, userInput);
				
			} else if (numberInserido == 4) {
				primeiraParte.exercicio4(args, userInput);

			} else if (numberInserido == 5) {
				primeiraParte.exercicio4mais1(args, userInput);
			
			} else if (numberInserido == 6) { 
				primeiraParte.exercicio2mais3(args, userInput);
			
			}  else if (numberInserido == 7) {
				System.out.println("Adeus!");
				userInput.close();
			
			} else {
				System.out.println("O numero "+ number + " não faz parte de nenhuma opção. Tente novamente.\n");
				System.out.println("--------------------------------------------------------------------------");
				main(args);
			}	
		
			//segunda parte
		} else if (numberInserido2 == 2) {
			ArrayList<String> datum = utils.checksDatumEntryOut(userInput, args);
			ArrayList<String> transformacao = utils.checksTransf(userInput, args);
		
			if (transformacao.get(0) == "Bursa-Wolf") {
				String coords = utils.coordss(userInput);	
				if (coords.equals("Cartesianas/Cartesianas")) { // EXERCICIO 5
					segundaParte.exercicio5(args, userInput, datum);
				
				} else if (coords.equals("Cartesianas/Geográficas")) {
					//
				} else if (coords.equals("Cartesianas/Retangulares")) {
					//
				} else if (coords.equals("Geográficas/Cartesianas")) { // EXERCICIO 3 + EXERCICIO 5
					segundaParte.exercicio3mais5(args, userInput, datum);
					
				} else if (coords.equals("Geográficas/Geográficas")) {
					//
				} else if (coords.equals("Retangulares/Geodésicas")) {
					//
				} else if (coords.equals("Retangulares/Retangulares")) { // EXERCICIO 2 + EXERCICIO 3 + EXERCICIO 5 + EXERCICIO 4 + EXERCICIO 1
					segundaParte.exercicio2mais3mais5mais4mais1(args, userInput, datum);
				}

			} else if (transformacao.get(0) == "Molodenski") {
				String coords = utils.coordss(userInput);
				if (coords.equals("Cartesianas/Cartesianas")) { // EXERCICIO 4 + EXERCICIO 6 + EXERCICIO 3 (Nao fiz)
					//
				} else if (coords.equals("Geográficas/Geográficas")) { // EXERCICIO 6
					segundaParte.exercicio6(args, userInput, datum);
				} else if (coords.equals("Retangulares/Retangulares")) { // EXERCICIO 2 + EXERCICIO 6 + EXERCICIO 1 (Nao fiz)
					//
				}		
			}  else if (transformacao.get(0) == "Polinomial 2º grau") {
				String coords = utils.coordss(userInput);
				if (coords.equals("Cartesianas/Cartesianas")) { // EXERCICIO 4 + EXERCICIO 1 + EXERCICIO 7 + EXERCICIO 2 + EXERCICIO 3
					segundaParte.exercicio4mais1mais7mais2mais3(args, userInput, datum);
				} else if (coords.equals("Geográficas/Geográficas")) { // EXERCICIO 1 + EXERCICIO 7 + EXERCICIO 2 + EXERCICIO 3
					segundaParte.exercicio1mais7mais2mais3(args, userInput, datum);
				} else if (coords.equals("Retangulares/Retangulares")) { // EXERCICIO 7
					segundaParte.exercicio7(args, userInput, datum);
				}	
			}
			
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
			main(args);
		
		} else {
			System.out.println("O numero "+ number2 + " não faz parte de nenhuma opção. Tente novamente.\n");
			System.out.println("--------------------------------------------------------------------------");
			main(args);
		}
	}

}
