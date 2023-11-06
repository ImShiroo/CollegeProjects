package cartografia;

import java.util.ArrayList;
import java.util.Scanner;

public class utils {
	
	public static String coordss(Scanner userInput) {
		System.out.println("Quais as coordenadas de entrada e de saída:");
		System.out.println("(1) - Cartesianas/Cartesianas");
		System.out.println("(2) - Cartesianas/Geográficas"); 
		System.out.println("(3) - Cartesianas/Retangulares");
		System.out.println("(4) - Geográficas/Cartesianas"); 
		System.out.println("(5) - Geográficas/Geográficas"); 
		System.out.println("(6) - Retangulares/Geodésicas"); 
		System.out.println("(7) - Retangulares/Retangulares"); 
		
		String coord = userInput.nextLine();
		int coordInt = Integer.parseInt(coord);
		if (coordInt == 1) {
			coord = "Cartesianas/Cartesianas";
		} else if (coordInt == 2) {
			coord = "Cartesianas/Geográficas";
		} else if (coordInt == 3) {
			coord = "Cartesianas/Retangulares";
		} else if (coordInt == 4) {
			coord = "Geográficas/Cartesianas";
		} else if (coordInt == 5) {
			coord = "Geográficas/Geográficas";
		} else if (coordInt == 6) {
			coord = "Retangulares/Geodésicas";
		} else if (coordInt == 7) {
			coord = "Retangulares/Retangulares";
		}
		
		return coord;
	}


	public static ArrayList<String> dataTreatmentLbd(ArrayList<String> datum) {
		ArrayList<String> lbd0 = new ArrayList<String>();

		if (datum.get(0) == "PT-TM06/ETRS89") {
            lbd0.add("08");lbd0.add("07");lbd0.add("59.19");lbd0.add("W");
		} else if (datum.get(0) == "Datum Lisboa") {
            lbd0.add("08");lbd0.add("07");lbd0.add("54.862");lbd0.add("W");
		} else if (datum.get(0) == "Datum 73") {
            lbd0.add("08");lbd0.add("07");lbd0.add("54.862");lbd0.add("W");
		} else if (datum.get(0) == "PTRA08-UTM/ITRF93") {
			int fuso = Integer.parseInt(datum.get(1));
			if (fuso == 25) {
				lbd0.add("33");lbd0.add("00");lbd0.add("00");lbd0.add("W");
			} else if (fuso == 26) {
				lbd0.add("27");lbd0.add("00");lbd0.add("00");lbd0.add("W");
			} else if (fuso == 28) {
				lbd0.add("15");lbd0.add("00");lbd0.add("00");lbd0.add("W");
			}
		}
		return lbd0;
	}


	public static ArrayList<String> dataTreatmentPhi(ArrayList<String> datum) {
		ArrayList<String> phi0 = new ArrayList<String>();
		if (datum.get(0) == "PT-TM06/ETRS89") {
			phi0.add("39");phi0.add("40");phi0.add("05.73");phi0.add("N");
		} else if (datum.get(0) == "Datum Lisboa") {
			phi0.add("39");phi0.add("40");phi0.add("00");phi0.add("N");
		} else if (datum.get(0) == "Datum 73") {
			phi0.add("39");phi0.add("40");phi0.add("00");phi0.add("N");
		} else if (datum.get(0) == "PTRA08-UTM/ITRF93") {
			phi0.add("00");phi0.add("00");phi0.add("00");phi0.add("N");
		}
		return phi0;
	}


	public static ArrayList<String> checksDatum(Scanner userInput) { // com fusos e 4 opçoes
		System.out.println("\nQual o Datum desejado para a conversão? \n(1): PT-TM06/ETRS89  \n(2): Datum Lisboa  \n(3): Datum 73  \n(4): PTRA08-UTM/ITRF93");
		String datum = userInput.nextLine();
		int datumint = Integer.parseInt(datum);
		ArrayList<String> datums = new ArrayList<String>();
	    if (datumint == 1) {
	    	datums.add("PT-TM06/ETRS89");
	    }
	    else if (datumint == 2) {
	    	datums.add("Datum Lisboa");
	    }
	    else if (datumint == 3) {
	    	datums.add("Datum 73");
	    }
	    else if (datumint == 4) {
	    	datums.add("PTRA08-UTM/ITRF93");
	    	System.out.println("Qual o fuso desejado? 25, 26 ou 28");
	    	//Scanner fusoScanner = new Scanner(System.in);
			String fuso = userInput.nextLine();
			datums.add(fuso);
	    }
		return datums;
	}
	
	public static ArrayList<String> checksDatum_fusos(Scanner userInput) { // com fusos com 2 opçoes
		System.out.println("\nQual o Datum desejado para a conversão? \n(1): PT-TM06/ETRS89 \n(2): PTRA08-UTM/ITRF93");
		String datum = userInput.nextLine();
		int datumint = Integer.parseInt(datum);
		ArrayList<String> datums = new ArrayList<String>();
	    if (datumint == 1) {
	    	datums.add("PT-TM06/ETRS89");
	    }
	    else if (datumint == 2) {
	    	datums.add("PTRA08-UTM/ITRF93");
	    	System.out.println("Qual o fuso desejado? 25, 26 ou 28");
	    	//Scanner fusoScanner = new Scanner(System.in);
			String fuso = userInput.nextLine();
			datums.add(fuso);
	    }
		return datums;
	}
	
	public static ArrayList<String> checksDatum1(Scanner userInput) { // sem fusos
		System.out.println("\nQual o Datum desejado para a conversão? \n(1): PT-TM06/ETRS89  \n(2): Datum Lisboa  \n(3): Datum 73  \n(4): PTRA08-UTM/ITRF93");
		String datum = userInput.nextLine();
		int datumint = Integer.parseInt(datum);
		ArrayList<String> datums = new ArrayList<String>();
	    if (datumint == 1) {
	    	datums.add("PT-TM06/ETRS89");
	    }
	    else if (datumint == 4) {
	    	datums.add("PTRA08-UTM/ITRF93");
	    } else if (datumint == 2) {
	    	datums.add("Datum Lisboa");
	    } else if (datumint == 3) {
	    	datums.add("Datum 73");
	    }
		return datums;
	}
	
	public static ArrayList<String> checksDatumEntryOut(Scanner userInput, String[] args) { 
		System.out.println("Qual o datum de entrada e o de saída:");
		System.out.println("(1) - Datum Lisboa/ETRS89"); // Geográficas/Retangulares
		System.out.println("(2) - Datum 73/ETRS89"); // Retangulares/Geográficas
		System.out.println("(3) - ETRS89/Datum Lisboa"); // Geográficas/Cartesianas
		System.out.println("(4) - ETRS89/Datum 73"); // Cartesianas/Retangulares
		System.out.println("(5) - Sair");		
		String datum = userInput.nextLine();
		
		int datumint = Integer.parseInt(datum);
		ArrayList<String> datums = new ArrayList<String>();
	    // entrada index 0
		// saida index 1
		if (datumint == 1) {
	    	datums.add("Datum Lisboa/ETRS89");
	    }
	    else if (datumint == 2) {
	    	datums.add("Datum 73/ETRS89");
	    }
	    else if (datumint == 3) {
	    	datums.add("ETRS89/Datum Lisboa");
	    }
	    else if (datumint == 4) {
	    	datums.add("ETRS89/Datum 73");
	    	//System.out.println("Qual o fuso desejado? 25, 26 ou 28");
	    	//Scanner fusoScanner = new Scanner(System.in);
			//String fuso = userInput.nextLine();
			//datums.add(fuso);
	    } else if (datumint == 5) {
	    	main.main(args);
	    } else {
	    	System.out.println("Tente novamente.");
	    	checksDatumEntryOut(userInput, args);
	    }
		
		return datums;
	}
	

	public static ArrayList<String> checksTransf(Scanner userInput, String[] args) {
		System.out.println("Qual a transformação desejada:");
		System.out.println("(1) - Bursa-Wolf"); // Geográficas/Retangulares
		System.out.println("(2) - Molodenski"); // Retangulares/Geográficas
		System.out.println("(3) - Polinomial 2º grau"); // Geográficas/Cartesianas
		System.out.println("(4) - Sair");	
		
		String transfinput = userInput.nextLine();
		int datumint = Integer.parseInt(transfinput);
		ArrayList<String> transf = new ArrayList<String>();
	    
		if (datumint == 1) {
			transf.add("Bursa-Wolf");
	    }
	    else if (datumint == 2) {
	    	transf.add("Molodenski");
	    }
	    else if (datumint == 3) {
	    	transf.add("Polinomial 2º grau");
	    }
	    else if (datumint == 4) {
	    	main.main(args);
	    } else {
	    	System.out.println("Tente novamente.");
	    	checksTransf(userInput, args);
	    }
		
		return transf;
	}
}
