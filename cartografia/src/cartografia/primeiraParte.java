package cartografia;

import java.util.ArrayList;
import java.util.Scanner;

public class primeiraParte {

	//----------------------------- Inicio primeira parte do projeto ----------------------------------------
	public static void exercicio1(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas geodésicas no seguinte formato: [aa] [bb] [cc] [NWSE]");
		System.out.println("Coordenada Latitude:");
		String[] phi = userInput.nextLine().split(" ");
		System.out.println("Coordenada Longitude:");
		String[] lbd = userInput.nextLine().split(" ");
		String ddPhi = "";
		String ddLbd = "";
		
		try {
			ArrayList<String> NSEW = new ArrayList<String>();
			ArrayList<String> NSEWLbd = new ArrayList<String>();
			NSEW.add(phi[3]);
			NSEWLbd.add(lbd[3]);
			if (NSEW.contains("N") || NSEW.contains("S")) {
				ddPhi = phi[3];
				
			} else {
				System.out.println("Nas coordenadas de latitude só são usadas o N e S.");
				System.out.println("--------------------------------------------------------------------------");
				main.main(args);
			} 
			
			if (NSEWLbd.contains("E") || NSEWLbd.contains("W") || NSEWLbd.contains("WGr")) {
				ddLbd = lbd[3];
			} else {
				System.out.println("Nas coordenadas de longitude só são usadas o E, W ou Wgr.");
				System.out.println("--------------------------------------------------------------------------");
				main.main(args);
			} 
			
			ArrayList<String> coordenadasConcluidasPhi = new ArrayList<String>();		
			coordenadasConcluidasPhi.add(phi[0]);
			coordenadasConcluidasPhi.add(phi[1]);
			coordenadasConcluidasPhi.add(phi[2]);
			coordenadasConcluidasPhi.add(ddPhi);
			
			
			ArrayList<String> coordenadasConcluidasLbd = new ArrayList<String>();
			coordenadasConcluidasLbd.add(lbd[0]);
			coordenadasConcluidasLbd.add(lbd[1]);
			coordenadasConcluidasLbd.add(lbd[2]);
			coordenadasConcluidasLbd.add(ddLbd);	
			
			System.out.println("As coordenadas geodésicas inseridas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi.get(0)+"º "+ coordenadasConcluidasPhi.get(1) + "' "+ coordenadasConcluidasPhi.get(2) + "'' "+ coordenadasConcluidasPhi.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd.get(0)+"º "+ coordenadasConcluidasLbd.get(1) + "' "+ coordenadasConcluidasLbd.get(2) + "'' "+ coordenadasConcluidasLbd.get(3));
			ArrayList<String> datum = utils.checksDatum(userInput);
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum);
			ex1.geo_rectangular(coordenadasConcluidasPhi,coordenadasConcluidasLbd,phi0,lbd0,datum);
			main.main(args);
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}
	
	public static void exercicio2(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas retangulares: ");
		System.out.print("M = ");
		String M = userInput.nextLine();
		System.out.print("P = ");
		String P = userInput.nextLine();
		try {
			double M2 = Double.parseDouble(M);
			double P2 = Double.parseDouble(P);
			System.out.println("As coordenadas retangulares inseridas: \nM = " + M + "\nP = "+ P);
			ArrayList<String> datum = utils.checksDatum(userInput);	
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum);	
			ex2.ret_geodesico(M,P,phi0,lbd0,datum);	
			main.main(args);
		} catch (NumberFormatException e){
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}
	
	public static void exercicio3(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas geodésicas no seguinte formato: [aa] [bb] [cc] [NWSE]");
		System.out.println("Coordenada Latitude:");
		String[] phi = userInput.nextLine().split(" ");
		System.out.println("Coordenada Longitude:");
		String[] lbd = userInput.nextLine().split(" ");
		System.out.println("Valor h (metros):");
		String h = userInput.nextLine();
		String ddPhi = "";
		String ddLbd = "";
		
		try {
			ArrayList<String> NSEW = new ArrayList<String>();
			ArrayList<String> NSEWLbd = new ArrayList<String>();
			NSEW.add(phi[3]);
			NSEWLbd.add(lbd[3]);
			
			if (NSEW.contains("N") || NSEW.contains("S")) {
				ddPhi = phi[3];
			} else {
				System.out.println("Nas coordenadas de latitude só são usadas o N e S.");
				System.out.println("--------------------------------------------------------------------------");
				main.main(args);
			} 
			
			if (NSEWLbd.contains("E") || NSEWLbd.contains("W") || NSEWLbd.contains("WGr")) {
				ddLbd = lbd[3];
			} else {
				System.out.println("Nas coordenadas de longitude só são usadas o E, W ou WGr.");
				System.out.println("--------------------------------------------------------------------------");
				main.main(args);
			} 
			
			ArrayList<String> coordenadasConcluidasPhi = new ArrayList<String>();		
			coordenadasConcluidasPhi.add(phi[0]);
			coordenadasConcluidasPhi.add(phi[1]);
			coordenadasConcluidasPhi.add(phi[2]);
			coordenadasConcluidasPhi.add(ddPhi);
			
			ArrayList<String> coordenadasConcluidasLbd = new ArrayList<String>();
			coordenadasConcluidasLbd.add(lbd[0]);
			coordenadasConcluidasLbd.add(lbd[1]);
			coordenadasConcluidasLbd.add(lbd[2]);
			coordenadasConcluidasLbd.add(ddLbd);	
			
			System.out.println("As coordenadas geodésicas inseridas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi.get(0)+"º "+ coordenadasConcluidasPhi.get(1) + "' "+ coordenadasConcluidasPhi.get(2) + "'' "+ coordenadasConcluidasPhi.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd.get(0)+"º "+ coordenadasConcluidasLbd.get(1) + "' "+ coordenadasConcluidasLbd.get(2) + "'' "+ coordenadasConcluidasLbd.get(3));
			
			ArrayList<String> datum = utils.checksDatum1(userInput);
		
			ex3.geo_tridi(coordenadasConcluidasPhi,coordenadasConcluidasLbd,datum,h);
			main.main(args);
		
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
		
	}
	
	public static void exercicio4(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas tridimensionais no seguinte formato: X,Y,Z");
		String[] coordenadasTridi = userInput.nextLine().split(",");
		
		try {
			String X = coordenadasTridi[0];
			String Y = coordenadasTridi[1];
			String Z = coordenadasTridi[2];
			double X2 = Double.parseDouble(X);
			double Y2 = Double.parseDouble(Y);
			double Z2 = Double.parseDouble(Z);
			
			ArrayList<Double> coordenadasTridiEscolhidas= new ArrayList<Double>();
			coordenadasTridiEscolhidas.add(X2);
			coordenadasTridiEscolhidas.add(Y2);
			coordenadasTridiEscolhidas.add(Z2);
			System.out.println("As coordenadas cartesianas tridimensionais inseridas foram: (" + X+ ", "+ Y+ ", " + Z+ ")");
			
			ArrayList<String> datum = utils.checksDatum1(userInput);
		
			ex4.tridi_geo(coordenadasTridiEscolhidas,datum);
			main.main(args);
		
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
		
	}
	
	public static void exercicio4mais1(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas tridimensionais no seguinte formato: X,Y,Z");
		String[] coordenadasTridi = userInput.nextLine().split(",");
		try {
			String X = coordenadasTridi[0];
			String Y = coordenadasTridi[1];
			String Z = coordenadasTridi[2];
			double X2 = Double.parseDouble(X);
			double Y2 = Double.parseDouble(Y);
			double Z2 = Double.parseDouble(Z);
			
			ArrayList<Double> coordenadasTridiEscolhidas= new ArrayList<Double>();
			coordenadasTridiEscolhidas.add(X2);
			coordenadasTridiEscolhidas.add(Y2);
			coordenadasTridiEscolhidas.add(Z2);
			System.out.println("As coordenadas cartesianas tridimensionais inseridas foram: (" + X+ ", "+ Y+ ", " + Z+ ")");
			
			ArrayList<String> datum = utils.checksDatum(userInput);
			
			ArrayList<String> coordsCartesianas = ex4.tridi_geo(coordenadasTridiEscolhidas,datum);

			ArrayList<String> coordenadasConcluidasPhi = new ArrayList<String>();		
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[0]);
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[1]);
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[2]);
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[3]);
			
			
			ArrayList<String> coordenadasConcluidasLbd = new ArrayList<String>();
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[0]);
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[1]);
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[2]);
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[3]);	
			
			System.out.println("As coordenadas geodésicas inseridas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi.get(0)+"º "+ coordenadasConcluidasPhi.get(1) + "' "+ coordenadasConcluidasPhi.get(2) + "'' "+ coordenadasConcluidasPhi.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd.get(0)+"º "+ coordenadasConcluidasLbd.get(1) + "' "+ coordenadasConcluidasLbd.get(2) + "'' "+ coordenadasConcluidasLbd.get(3));
			
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum);
		
			ex1.geo_rectangular(coordenadasConcluidasPhi,coordenadasConcluidasLbd,phi0,lbd0,datum);
			
			main.main(args);
			
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}	
	}
	
	public static void exercicio2mais3(String[] args, Scanner userInput) {
		System.out.println("\nIntroduza as coordenadas retangulares: ");
		System.out.print("M = ");
		String M = userInput.nextLine();
		System.out.print("P = ");
		String P = userInput.nextLine();
		try {	
			double M2 = Double.parseDouble(M);
			double P2 = Double.parseDouble(P);
			System.out.println("As coordenadas retangulares inseridas: \nM = " + M + "\nP = "+ P);
			
			ArrayList<String> datum = utils.checksDatum(userInput);
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum);
			
			ArrayList<String> coordsCartesianas = ex2.ret_geodesico(M,P,phi0,lbd0,datum);
			System.out.println("\nIntroduza o valor h (metros): ");
			System.out.print("h = ");
			String h = userInput.nextLine();
			
			ArrayList<String> coordenadasConcluidasPhi = new ArrayList<String>();		
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[0].replace(",", "."));
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[1].replace(",", "."));
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[2].replace(",", "."));
			coordenadasConcluidasPhi.add(coordsCartesianas.get(0).split(" ")[3].replace(",", "."));
			
			
			ArrayList<String> coordenadasConcluidasLbd = new ArrayList<String>();
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[0].replace(",", "."));
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[1].replace(",", "."));
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[2].replace(",", "."));
			coordenadasConcluidasLbd.add(coordsCartesianas.get(1).split(" ")[3].replace(",", "."));	

			ex3.geo_tridi(coordenadasConcluidasPhi,coordenadasConcluidasLbd,datum,h);
			main.main(args);
			
		} catch (NumberFormatException e){
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}
	
	// ----------------------------------  Final da primeira parte do projeto 
}
