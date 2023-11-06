package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex4 {
	static DecimalFormat df = new DecimalFormat("#.####");
	static DecimalFormat df2 = new DecimalFormat("#.##");
	public static ArrayList<String> tridi_geo(ArrayList<Double> coordenadasTridiEscolhidas, ArrayList<String> datum) {
		ArrayList<String> coordsfinais = new ArrayList<String>();
		if (datum.get(0) == "Datum 73" || datum.get(0) == "Datum Lisboa") {
			int a = 6378388;
			double f = (double)1/297;
			ArrayList<Double> coordsConv = conv(coordenadasTridiEscolhidas,a, f);
			coordsfinais = finalgeodesicCoords(coordsConv);
		
		} else {
			int a = 6378137;
			double f = (double)(1/298.257222101);
			ArrayList<Double> coordsConv = conv(coordenadasTridiEscolhidas,a, f);
			coordsfinais = finalgeodesicCoords(coordsConv);
		}
		
		prints(coordsfinais);
		return coordsfinais;
		
	}

	private static ArrayList<Double> conv(ArrayList<Double> coordenadasTridiEscolhidas, int a, double f) {
		
		double X = coordenadasTridiEscolhidas.get(0);
		double Y = coordenadasTridiEscolhidas.get(1);
		double Z = coordenadasTridiEscolhidas.get(2);

		double n = (double) Math.sqrt(f*(2-f)); // e
		double P = Math.pow(Math.pow(X,2) + Math.pow(Y, 2), 0.5);
	
		// longitude
		double divisaoYX = Y/X;	
		double longitude = Math.toDegrees(Math.atan(divisaoYX));
		
		// latitude
		double delta_phi = (double) 1;
		double divisaoZ = Z/(P*(1-Math.pow(n, 2)));
		double latAprox = Math.atan(divisaoZ);
		double N = (double) a /
				(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(latAprox),2), 0.5);
		double h = (P/Math.cos(latAprox)) - N;
		double expressao = Math.pow(10, -10);
		double latitude = 0;
		while (Math.abs(delta_phi) > expressao) {	
			double latitude_parte1 = Z+Math.pow(n,2)*N*Math.sin(latAprox);
			latitude = Math.atan(latitude_parte1/P);
			N = (double) a /
					(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(latitude),2), 0.5);
			h = (P/Math.cos(latitude)) - N;
			latAprox = latitude;
			delta_phi = latitude - latAprox;

		}
		
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		coordsConv.add(Math.toDegrees(latitude));
		coordsConv.add(longitude);
		coordsConv.add(h);
		
		return coordsConv;
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
		
		String h = coordsConv.get(2).toString();
		
		ArrayList<String> coordsFinais = new ArrayList<String>();
		
		if (phigrausDouble < 0) { // Se M negativo
			if (lbdgrausDouble < 0) { // Se M negativo e P negativo
				coordsFinais.add(Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add(Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} else if (lbdgrausDouble > 0) { // Se M negativo e P positivo
				coordsFinais.add(Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add(Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		} else if (phigrausDouble > 0) { // Se M positivo
			if (lbdgrausDouble < 0) {  // Se M positivo e P negativo
				coordsFinais.add((Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N"));
				coordsFinais.add(Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} if (lbdgrausDouble > 0) {  // Se M positivo e P positivo
				coordsFinais.add(Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N");
				coordsFinais.add(Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		}
		
		String phiGrausInt = ""+phiGraus+"";
		String lbdgrausInt = ""+lbdGraus+"";
		coordsFinais.add(h);
		coordsFinais.add(phiGrausInt);
		coordsFinais.add(lbdgrausInt);

		return coordsFinais;

	}

	private static void prints(ArrayList<String> coordsFinais) {
		
		int phigrausDouble = Integer.parseInt(coordsFinais.get(3));
		int phiMinutes = Integer.parseInt(coordsFinais.get(0).split(" ")[1]);
		double phiSeconds = Math.abs(Double.parseDouble(coordsFinais.get(0).split(" ")[2].replace(",", ".")));
		int lbdgrausDouble = Integer.parseInt(coordsFinais.get(4));
		int lbdMinutes = Integer.parseInt(coordsFinais.get(1).split(" ")[1]);
		double lbdSeconds = Math.abs(Double.parseDouble(coordsFinais.get(1).split(" ")[2].replace(",", ".")));
		double h = Double.parseDouble(coordsFinais.get(2));
		
		if (phigrausDouble < 0) { // Se M negativo
			if (lbdgrausDouble < 0) { // Se M negativo e P negativo
				System.out.println("O resultado das coordenadas retangulares -> geodesicas: " + "Latitude = " + Math.abs(phigrausDouble) + "º " + Math.abs(phiMinutes) + "' " + df.format(phiSeconds) + "'' S" + "     Longitude = " + Math.abs(lbdgrausDouble) + "º " + Math.abs(lbdMinutes) + "' " + df.format(lbdSeconds) + "'' WGr    h = "+ df2.format(h));
			} else if (lbdgrausDouble > 0) { // Se M negativo e P positivo
				System.out.println("O resultado das coordenadas retangulares -> geodesicas: " + "Latitude = " + Math.abs(phigrausDouble) + "º " + Math.abs(phiMinutes) + "' " + df.format(phiSeconds) + "'' S" + "     Longitude = " + Math.abs(lbdgrausDouble) + "º " + Math.abs(lbdMinutes) + "' " + df.format(lbdSeconds) + "'' E    h = "+ df2.format(h));
			}
		} else if (phigrausDouble > 0) { // Se M positivo
			if (lbdgrausDouble < 0) {  // Se M positivo e P negativo
				System.out.println("O resultado das coordenadas retangulares -> geodesicas: " + "Latitude = " + Math.abs(phigrausDouble) + "º " + Math.abs(phiMinutes) + "' " + df.format(phiSeconds) + "'' N" + "     Longitude = " + Math.abs(lbdgrausDouble) + "º " + Math.abs(lbdMinutes) + "' " + df.format(lbdSeconds) + "'' WGr    h = "+ df2.format(h));
			} if (lbdgrausDouble > 0) {  // Se M positivo e P positivo
				System.out.println("O resultado das coordenadas retangulares -> geodesicas: " + "Latitude = " + Math.abs(phigrausDouble) + "º " + Math.abs(phiMinutes) + "' " + df.format(phiSeconds) + "'' N" + "     Longitude = " + Math.abs(lbdgrausDouble) + "º " + Math.abs(lbdMinutes) + "' " + df.format(lbdSeconds) + "'' E    h = "+ df2.format(h));
			}
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		
		
	}

}
