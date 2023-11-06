package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex6molodenski {
	static DecimalFormat df = new DecimalFormat("#.#####");
	static DecimalFormat df2 = new DecimalFormat("#.####");
	public static ArrayList<String> molo(ArrayList<String> coordenadasConcluidasPhi, ArrayList<String> coordenadasConcluidasLbd, ArrayList<String> datum, String h) {
		ArrayList<String> coordenadasFinais = new ArrayList<String>();
		if (datum.get(0) == "Datum Lisboa/ETRS89") { 
			int a = 6378388;
			double f = (double)1/297;
			double vetorX = -303.861;
			double vetorY = -60.693;
			double vetorZ = 103.607;
			double vetora = -251.000;
			double vetorf = -0.000014192686;
			double h2 = Double.parseDouble(h);
			ArrayList<Double> coordsConv = conv(coordenadasConcluidasPhi, coordenadasConcluidasLbd, h2, vetorX, vetorY, vetorZ, vetora, vetorf, a, f);
			coordenadasFinais = finalgeodesicCoords(coordsConv);
		} else if (datum.get(0) == "Datum 73/ETRS89") {
			int a = 6378388;
			double f = (double)1/297;
			double vetorX = -223.150;
			double vetorY = 110.132;
			double vetorZ = 36.711;
			double vetora = -251.000;
			double vetorf = -0.000014192686;
			double h2 = Double.parseDouble(h);
			ArrayList<Double> coordsConv = conv(coordenadasConcluidasPhi, coordenadasConcluidasLbd, h2, vetorX, vetorY, vetorZ, vetora, vetorf, a, f);
			coordenadasFinais = finalgeodesicCoords(coordsConv);
		}
		
		prints(coordenadasFinais, datum);
		return coordenadasFinais;
	}
	
	private static ArrayList<Double> conv(ArrayList<String> coordenadasConcluidasPhi,
			ArrayList<String> coordenadasConcluidasLbd, double h2, double vetorX, double vetorY, double vetorZ,
			double vetora, double vetorf, double a, double f) {
		
		double grausPhi = Double.parseDouble(coordenadasConcluidasPhi.get(0));
		double minutosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(1));
		double segundosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(2));
		double grauslbd = Double.parseDouble(coordenadasConcluidasLbd.get(0));
		double minutoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(1));
		double segundoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(2));
		
		double phiD =  (double) grausPhi + (double) minutosPhi/60 + (double) segundosPhi/3600; // latitude graus
		double lbdD = (double) grauslbd + (double) minutoslbd/60 + (double) segundoslbd/3600; // longitude graus
		double phiDRadius = (double) Math.toRadians(phiD);
		double lbdDRadius = (double) Math.toRadians(lbdD);
		
		double n = (double) Math.sqrt(f*(2-f)); // e
		double b = a * (1-f);
		double N = (double) a /
				(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(phiDRadius),2), 0.5);
		double rho = (double) a * (
				(double) 1-Math.pow(n,2)) / (Math.pow((1-Math.pow(n,2) * Math.pow(Math.sin(phiDRadius),2)), 3/2)); 
		 
		// latitude
		double phi1 =  -vetorX * Math.sin(phiDRadius) * Math.cos(lbdDRadius);
		double phi2 = vetorY * Math.sin(phiDRadius) * Math.sin(lbdDRadius);
		double phi3 =  vetorZ * Math.cos(phiDRadius);
		double phi4 =  vetora * (Math.pow(n, 2)* N * Math.sin(phiDRadius) * Math.cos(phiDRadius)/a);
		double phi5 = vetorf * Math.sin(phiDRadius) * Math.cos(phiDRadius);
		double phi6 = phi5 * (a/b*rho + b/a*N);
		double latitude_final = phiDRadius + ((phi1 + phi2 + phi3 + phi4 + phi6)/(rho+h2));

		// longitude 
		double lbd1 = vetorX*Math.sin(lbdDRadius);
		double lbd2 = vetorY*Math.cos(lbdDRadius);
		double lbd3 = (N+h2)*Math.cos(phiDRadius);
		double longitude_final = -lbdDRadius + ((lbd1+lbd2)/lbd3);
		
		// h
		double h_parte1 = vetorX*Math.cos(phiDRadius)*Math.cos(lbdDRadius);
		double h_parte2 = -vetorY*Math.cos(phiDRadius)*Math.sin(lbdDRadius);
		double h_parte3 = vetorZ*Math.sin(phiDRadius);
		double h_parte4 = vetora*(a/N);
		double h_parte5 =  vetorf * ((b/a)*N*Math.pow(Math.sin(phiDRadius), 2));
		double h_final = h2 + h_parte1 + h_parte2 + h_parte3 - h_parte4 + h_parte5;
		
		ArrayList<Double> coordenadasGeoFinais = new ArrayList<Double>();
		coordenadasGeoFinais.add(Math.toDegrees(latitude_final));
		coordenadasGeoFinais.add(Math.toDegrees(longitude_final));
		coordenadasGeoFinais.add(h_final);
		
		return coordenadasGeoFinais;
	}

	private static ArrayList<String> finalgeodesicCoords(ArrayList<Double> coordsConv) {
		
		double phigrausDouble = coordsConv.get(0);
		int phiGraus = (int)phigrausDouble;
		int phiMinutes = (int) ((phigrausDouble - phiGraus) * 60);
		double phiSeconds_1 = (double) phigrausDouble - phiGraus;
		double phiSeconds_2 = (double) phiMinutes/60;
		double phiSeconds = (phiSeconds_1 - (phiSeconds_2)) * 3600;
		
		double lbdgrausDouble = coordsConv.get(1);
		int lbdGraus = (int)lbdgrausDouble;
		int lbdMinutes = (int) ((lbdgrausDouble - lbdGraus) * 60);
		double lbdSeconds_1 = (double) lbdgrausDouble - lbdGraus;
		double lbdSeconds_2 = (double) lbdMinutes/60;
		double lbdSeconds = (lbdSeconds_1 - (lbdSeconds_2)) * 3600;
	
		ArrayList<String> coordsFinais = new ArrayList<String>();
		
		if (phigrausDouble < 0) { 
			if (lbdgrausDouble < 0) { 
				coordsFinais.add(Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add( Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} else if (lbdgrausDouble > 0) {
				coordsFinais.add( Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add( Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		} else if (phigrausDouble > 0) { 
			if (lbdgrausDouble < 0) {  
				coordsFinais.add( Math.abs(phiGraus)+ " " +  Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N");
				coordsFinais.add( Math.abs(lbdGraus)+ " " +  Math.abs(lbdMinutes)+ " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} if (lbdgrausDouble > 0) { 
				coordsFinais.add( Math.abs(phiGraus)+ " " +  Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N");
				coordsFinais.add( Math.abs(lbdGraus)+ " " +  Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		}
		
		coordsFinais.add(coordsConv.get(2).toString()); // h
		return coordsFinais;
	}
	
	private static void prints(ArrayList<String> coordenadasFinais, ArrayList<String> datum) {
		// longitude
		String[] mepString = coordenadasFinais.get(0).split(" ");
		String grausPhi = mepString[0];
		String minutosPhi = mepString[1];
		String segundosPhi = mepString[2];
		String NSEWPhi = mepString[3];
		// latitude
		String[] mepStringLbd = coordenadasFinais.get(1).split(" ");
		String grausLbd = mepStringLbd[0];
		String minutosLbd = mepStringLbd[1];
		String segundosLbd = mepStringLbd[2];
		String NSEWLbd = mepStringLbd[3];
		//h 
		double h = Double.parseDouble(coordenadasFinais.get(2));
		
		System.out.println("O resultado das coordenadas geodesicas transformadas "+datum.get(0)+" : " + "\nLatitude = " + grausPhi + "º " + minutosPhi + "' " + segundosPhi + "'' "+ NSEWPhi + "     Longitude = " + grausLbd + "º " + minutosLbd + "' " + segundosLbd + "'' "+ NSEWLbd + "     h = " + df2.format(h) + " m");
	}
}
