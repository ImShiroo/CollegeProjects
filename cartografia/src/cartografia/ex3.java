package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;
// Falta colocar o resultado dos diferentes fusos 
// Ver o porque de o valor Z não estar 100%
public class ex3 {
	static DecimalFormat df = new DecimalFormat("#.####");
	public static ArrayList<Double> geo_tridi(ArrayList<String> coordenadasConcluidasPhi, ArrayList<String> coordenadasConcluidasLbd, ArrayList<String> datum, String h) { 
		// X é latitude
		// y é longitude
		// h é o Z
		ArrayList<Double> coordsConv = new ArrayList<Double>();	
			/*  latitude -> 37 53 58.7635 N 
			 *  longitude -> 07 43 07.2999 WGr
			 *  h -> 257.85
			 * 	latitude -> 33 02 15.2697 N
			longitude -> 16 21 41.8679 WGr 
			h = 32.27 
			*/
		if (datum.get(0) == "Datum 73" || datum.get(0) == "Datum Lisboa") {
			int a = 6378388;
			double f = (double)1/297;
			coordsConv = conv(coordenadasConcluidasPhi, coordenadasConcluidasLbd, a, f, h);
		
		} else {
			int a = 6378137;
			double f = (double)(1/298.257222101);
			coordsConv = conv(coordenadasConcluidasPhi, coordenadasConcluidasLbd, a, f, h);
		}

		prints(coordsConv,coordenadasConcluidasPhi,coordenadasConcluidasLbd);
		return coordsConv;
	}
	
	private static void prints(ArrayList<Double> coordsConv, ArrayList<String> coordenadasConcluidasPhi, ArrayList<String> coordenadasConcluidasLbd) {
	
		if (coordenadasConcluidasPhi.get(3).equals("N")) {
			if (coordenadasConcluidasLbd.get(3).equals("E")) {
				System.out.println("O resultado das coordenadas geodesicas -> tridimensionais: ("+df.format(coordsConv.get(0))+", "+df.format(coordsConv.get(1)) + ", " + df.format(coordsConv.get(2)) + ")");
			} else if (coordenadasConcluidasLbd.get(3).equals("W") || coordenadasConcluidasLbd.get(3).equals("WGr")) {
				System.out.println("O resultado das coordenadas geodesicas -> tridimensionais: ("+df.format(coordsConv.get(0))+", -"+df.format(Math.abs(coordsConv.get(1))) + ", " + df.format(coordsConv.get(2)) + ")");
			}
		} else if (coordenadasConcluidasLbd.get(3).equals("S")) {
			if (coordenadasConcluidasLbd.get(3).equals("E")) { 
				System.out.println("O resultado das coordenadas geodesicas -> tridimensionais: (-"+df.format(coordsConv.get(0))+", "+df.format(coordsConv.get(1)) + ", " + df.format(coordsConv.get(2)) + ")");
			} else if (coordenadasConcluidasLbd.get(3).equals("W") || coordenadasConcluidasLbd.get(3).equals("WGr")) {
				System.out.println("O resultado das coordenadas geodesicas -> tridimensionais: (-"+df.format(coordsConv.get(0))+", -"+df.format(coordsConv.get(1)) + ", " + df.format(coordsConv.get(2)) + ")");
			}
		}
		
		System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
	}
	
	private static ArrayList<Double> conv(ArrayList<String> coordenadasConcluidasPhi,
			ArrayList<String> coordenadasConcluidasLbd, int a, double f, String h) {

		double grausPhi = Double.parseDouble(coordenadasConcluidasPhi.get(0));
		double minutosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(1));
		double segundosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(2));
		double grauslbd = Double.parseDouble(coordenadasConcluidasLbd.get(0));
		double minutoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(1));
		double segundoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(2));
		double n = (double) Math.sqrt(f*(2-f)); // e
		
		double phiD =  (double) grausPhi + (double) minutosPhi/60 + (double) segundosPhi/3600; // latitude graus
		double lbdD = (double) grauslbd + (double) minutoslbd/60 + (double) segundoslbd/3600; // longitude graus
		
		lbdD = -lbdD;
		double phiDRadius = (double) Math.toRadians(phiD);
		double lbdDRadius = (double) Math.toRadians(lbdD);
		
		double N = (double) a /
				(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(phiDRadius),2), 0.5);
		
		double x = (double) calcX(N,h,phiDRadius,lbdDRadius);
		double y = (double) calcY(N,h,phiDRadius,lbdDRadius);
		double z = (double) calcZ(N,h,phiDRadius,n);
		
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		coordsConv.add(x);
		coordsConv.add(y);
		coordsConv.add(z);
		
		
		/*if (coordenadasConcluidasPhi.get(3).equals("N")) {
			if (coordenadasConcluidasLbd.get(3).equals("E")) {
				coordsConv.add(x);
				coordsConv.add(y);
				coordsConv.add(z);
			} else if (coordenadasConcluidasLbd.get(3).equals("W") || coordenadasConcluidasLbd.get(3).equals("WGr")) {
				System.out.println(coordsConv);
				coordsConv.removeAll(coordsConv);
				coordsConv.add(x);
				coordsConv.add(-y);
				coordsConv.add(z);
			}
		} else if (coordenadasConcluidasLbd.get(3).equals("S")) {
			if (coordenadasConcluidasLbd.get(3).equals("E")) { 
				coordsConv.add(x * (-1));
				coordsConv.add(y);
				coordsConv.add(z);
			} else if (coordenadasConcluidasLbd.get(3).equals("W") || coordenadasConcluidasLbd.get(3).equals("WGr")) {
				coordsConv.add(x * (-1));
				coordsConv.add(y * (-1));
				coordsConv.add(z);
			}
		}*/
		return coordsConv;
	}
	
	private static double calcZ(double N, String h, double phiDRadius, double n) {
		double h2 = Double.parseDouble(h);
		double z_1 =  (1 - Math.pow(n, 2)) * N + h2;
		double z_3 = Math.sin(phiDRadius);
		double z = (z_1) * z_3;
		
		return z;
	}
	private static double calcY(double N, String h, double phiDRadius, double lbdRadius) {
		double h2 = Double.parseDouble(h);
		double y_1 = (double) N+h2;
		double y_2 = (double) Math.cos(phiDRadius);
		double y_3 = (double) Math.sin(lbdRadius);
		double y = (y_1) * y_2 * y_3;	
		
		return y;
	}
	private static double calcX(double N, String h, double phiDRadius, double lbdRadius) {
		double h2 = Double.parseDouble(h);
		double x_1 = (double) N+h2;
		double x_2 = (double) Math.cos(phiDRadius); // LATITUDE
		double x_3 = (double) Math.cos(lbdRadius); // LONGITUDE
		double x = (x_1) * x_2 * x_3;
		
		return x;
	}
}
