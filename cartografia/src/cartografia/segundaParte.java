package cartografia;

import java.util.ArrayList;
import java.util.Scanner;

public class segundaParte {

	public static void exercicio5(String[] args, Scanner userInput, ArrayList<String> datum) {
		System.out.println("\nIntroduza as coordenadas tridimensionais de entrada no seguinte formato: X,Y,Z");
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
			ex5bursawolf.bursaW(datum, coordenadasTridiEscolhidas);
			
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
		
	}

	public static void exercicio3mais5(String[] args, Scanner userInput, ArrayList<String> datum) {
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
			ArrayList<String> datum2 = utils.checksDatum1(userInput);
			ArrayList<Double> xyz = ex3.geo_tridi(coordenadasConcluidasPhi,coordenadasConcluidasLbd,datum2,h);
			ex5bursawolf.bursaW(datum, xyz);
	
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		} 
	}

	public static void exercicio2mais3mais5mais4mais1(String[] args, Scanner userInput, ArrayList<String> datum) {
		System.out.println("\nIntroduza as coordenadas retangulares: ");
		System.out.print("M = ");
		String M = userInput.nextLine();
		System.out.print("P = ");
		String P = userInput.nextLine();
		try {	
			double M2 = Double.parseDouble(M);
			double P2 = Double.parseDouble(P);
			System.out.println("As coordenadas retangulares inseridas: \nM = " + M + "\nP = "+ P);

			ArrayList<String> datum2 = utils.checksDatum1(userInput);
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum2);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum2);
			
			ArrayList<String> retgeo = ex2.ret_geodesico(M,P,phi0,lbd0,datum2);
			
			System.out.println("\nIntroduza o valor h (metros): ");
			System.out.print("h = ");
			String h = userInput.nextLine();
			
			ArrayList<String> coordenadasConcluidasPhi = new ArrayList<String>();		
			coordenadasConcluidasPhi.add(retgeo.get(0).split(" ")[0].replace(",", "."));
			coordenadasConcluidasPhi.add(retgeo.get(0).split(" ")[1].replace(",", "."));
			coordenadasConcluidasPhi.add(retgeo.get(0).split(" ")[2].replace(",", "."));
			coordenadasConcluidasPhi.add(retgeo.get(0).split(" ")[3].replace(",", "."));
			
			
			ArrayList<String> coordenadasConcluidasLbd = new ArrayList<String>();
			coordenadasConcluidasLbd.add(retgeo.get(1).split(" ")[0].replace(",", "."));
			coordenadasConcluidasLbd.add(retgeo.get(1).split(" ")[1].replace(",", "."));
			coordenadasConcluidasLbd.add(retgeo.get(1).split(" ")[2].replace(",", "."));
			coordenadasConcluidasLbd.add(retgeo.get(1).split(" ")[3].replace(",", "."));	

			ArrayList<Double> xyz = ex3.geo_tridi(coordenadasConcluidasPhi,coordenadasConcluidasLbd,datum2,h);

			ArrayList<Double> xyzbursa = ex5bursawolf.bursaW(datum, xyz);
			
			try {
				double X = xyzbursa.get(0);
				double Y = xyzbursa.get(1);
				double Z = xyzbursa.get(2);
				ArrayList<Double> coordenadasTridiEscolhidas= new ArrayList<Double>();
				coordenadasTridiEscolhidas.add(X);
				coordenadasTridiEscolhidas.add(Y);
				coordenadasTridiEscolhidas.add(Z);
				
				ArrayList<String> datum3 = utils.checksDatum1(userInput);
				ArrayList<String> coordsCartesianas = ex4.tridi_geo(coordenadasTridiEscolhidas,datum3);
				
				ArrayList<String> coordenadasConcluidasPhi2 = new ArrayList<String>();		
				coordenadasConcluidasPhi2.add(coordsCartesianas.get(0).split(" ")[0]);
				coordenadasConcluidasPhi2.add(coordsCartesianas.get(0).split(" ")[1]);
				coordenadasConcluidasPhi2.add(coordsCartesianas.get(0).split(" ")[2]);
				coordenadasConcluidasPhi2.add(coordsCartesianas.get(0).split(" ")[3]);
				
				
				ArrayList<String> coordenadasConcluidasLbd2 = new ArrayList<String>();
				coordenadasConcluidasLbd2.add(coordsCartesianas.get(1).split(" ")[0]);
				coordenadasConcluidasLbd2.add(coordsCartesianas.get(1).split(" ")[1]);
				coordenadasConcluidasLbd2.add(coordsCartesianas.get(1).split(" ")[2]);
				coordenadasConcluidasLbd2.add(coordsCartesianas.get(1).split(" ")[3]);	
				
				System.out.println("As coordenadas geodésicas inseridas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi2.get(0)+"º "+ coordenadasConcluidasPhi2.get(1) + "' "+ coordenadasConcluidasPhi2.get(2) + "'' "+ coordenadasConcluidasPhi2.get(3) + 
						"\nCoordenada Longitude = "+ coordenadasConcluidasLbd2.get(0)+"º "+ coordenadasConcluidasLbd2.get(1) + "' "+ coordenadasConcluidasLbd2.get(2) + "'' "+ coordenadasConcluidasLbd2.get(3));
				
				ArrayList<String> phi02 = utils.dataTreatmentPhi(datum3);
				ArrayList<String> lbd02 = utils.dataTreatmentLbd(datum3);
			
				ex1.geo_rectangular(coordenadasConcluidasPhi2,coordenadasConcluidasLbd2,phi02,lbd02,datum3);
				
				main.main(args);
			
			} catch (NumberFormatException e) {
				System.out.println("Algo correu mal.");
				System.out.println("--------------------------------------------------------------------------");
				main.main(args);
			}
			main.main(args);
			
		} catch (NumberFormatException e){
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
		
	}


	public static void exercicio6(String[] args, Scanner userInput, ArrayList<String> datum) {
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
			ex6molodenski.molo(coordenadasConcluidasPhi, coordenadasConcluidasLbd, datum, h);
	
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		} 
		
	}

	public static void exercicio4mais1mais7mais2mais3(String[] args, Scanner userInput, ArrayList<String> datum) {
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
			ArrayList<String> datum2 = utils.checksDatum_fusos(userInput);
			ArrayList<String> coordsCartesianas = ex4.tridi_geo(coordenadasTridiEscolhidas,datum2);
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
			
			System.out.println("As coordenadas geodésicas calculadas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi.get(0)+"º "+ coordenadasConcluidasPhi.get(1) + "' "+ coordenadasConcluidasPhi.get(2) + "'' "+ coordenadasConcluidasPhi.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd.get(0)+"º "+ coordenadasConcluidasLbd.get(1) + "' "+ coordenadasConcluidasLbd.get(2) + "'' "+ coordenadasConcluidasLbd.get(3));
			
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum2);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum2);
			ArrayList<Double> retoutput = ex1.geo_rectangular(coordenadasConcluidasPhi,coordenadasConcluidasLbd,phi0,lbd0,datum2);			
			ArrayList<Double> poli = ex7polinomial.poli(retoutput.get(0),retoutput.get(1),datum);	

			ArrayList<String> geooutput = ex2.ret_geodesico(poli.get(0).toString(),poli.get(1).toString(),phi0,lbd0,datum2);	
			System.out.print("h = ");
			String h = userInput.nextLine();
			ArrayList<String> coordenadasConcluidasPhi2 = new ArrayList<String>();		
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[0].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[1].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[2].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[3].replace(",", "."));
				
			ArrayList<String> coordenadasConcluidasLbd2 = new ArrayList<String>();
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[0].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[1].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[2].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[3].replace(",", "."));	
			System.out.println("As coordenadas geodésicas calculadas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi2.get(0)+"º "+ coordenadasConcluidasPhi2.get(1) + "' "+ coordenadasConcluidasPhi2.get(2) + "'' "+ coordenadasConcluidasPhi2.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd2.get(0)+"º "+ coordenadasConcluidasLbd2.get(1) + "' "+ coordenadasConcluidasLbd2.get(2) + "'' "+ coordenadasConcluidasLbd2.get(3));
			ex3.geo_tridi(coordenadasConcluidasPhi2,coordenadasConcluidasLbd2,datum2,h);
			main.main(args);
		
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}

	public static void exercicio1mais7mais2mais3(String[] args, Scanner userInput, ArrayList<String> datum) {
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
			ArrayList<String> datum2 = utils.checksDatum(userInput);
			ArrayList<String> phi0 = utils.dataTreatmentPhi(datum);
			ArrayList<String> lbd0 = utils.dataTreatmentLbd(datum);
			ArrayList<Double> retoutput = ex1.geo_rectangular(coordenadasConcluidasPhi,coordenadasConcluidasLbd,phi0,lbd0,datum2);
			System.out.println("As coordenadas retangulares inseridas: \nM = " + retoutput.get(0) + "\nP = "+ retoutput.get(1));
			
			ArrayList<Double> poli = ex7polinomial.poli(retoutput.get(0),retoutput.get(1),datum);
			
			ArrayList<String> geooutput = ex2.ret_geodesico(poli.get(0).toString(),poli.get(1).toString(),phi0,lbd0,datum2);	
			System.out.print("h = ");
			String h = userInput.nextLine();
			
			ArrayList<String> coordenadasConcluidasPhi2 = new ArrayList<String>();		
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[0].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[1].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[2].replace(",", "."));
			coordenadasConcluidasPhi2.add(geooutput.get(0).split(" ")[3].replace(",", "."));
				
			ArrayList<String> coordenadasConcluidasLbd2 = new ArrayList<String>();
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[0].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[1].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[2].replace(",", "."));
			coordenadasConcluidasLbd2.add(geooutput.get(1).split(" ")[3].replace(",", "."));	
			
			System.out.println("As coordenadas geodésicas calculadas foram: \nCoordenada Latitude = " + coordenadasConcluidasPhi2.get(0)+"º "+ coordenadasConcluidasPhi2.get(1) + "' "+ coordenadasConcluidasPhi2.get(2) + "'' "+ coordenadasConcluidasPhi2.get(3) + 
					"\nCoordenada Longitude = "+ coordenadasConcluidasLbd2.get(0)+"º "+ coordenadasConcluidasLbd2.get(1) + "' "+ coordenadasConcluidasLbd2.get(2) + "'' "+ coordenadasConcluidasLbd2.get(3));
			
			ex3.geo_tridi(coordenadasConcluidasPhi2,coordenadasConcluidasLbd2,datum2,h);
			main.main(args);
		} catch (NumberFormatException e) {
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}

	public static void exercicio7(String[] args, Scanner userInput, ArrayList<String> datum) {
		System.out.println("\nIntroduza as coordenadas retangulares: ");
		System.out.print("M = ");
		String M = userInput.nextLine();
		System.out.print("P = ");
		String P = userInput.nextLine();
		try {
			double M2 = Double.parseDouble(M);
			double P2 = Double.parseDouble(P);
			System.out.println("As coordenadas retangulares inseridas: \nM = " + M + "\nP = "+ P);
			ex7polinomial.poli(M2,P2,datum);	
			main.main(args);
		} catch (NumberFormatException e){
			System.out.println("Algo correu mal.");
			System.out.println("--------------------------------------------------------------------------");
			main.main(args);
		}
	}
}
