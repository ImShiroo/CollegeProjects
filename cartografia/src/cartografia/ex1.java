package cartografia;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex1 {
	static DecimalFormat df = new DecimalFormat("#.####");
	public static ArrayList<Double> geo_rectangular(ArrayList<String> coordenadasConcluidasPhi, ArrayList<String> coordenadasConcluidasLbd, ArrayList<String> phi0, ArrayList<String> lbd0, ArrayList<String> datum) {
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		if (datum.get(0) == "PT-TM06/ETRS89") {
			/*  latitude -> 37 53 58.7635 N 
			 *  longitude -> 07 43 07.2999 WGr
			 *  M = 36 448.61 m
			 * P = -196 253.96 m
			*/
			int a = 6378137;
			double f = (double)(1/298.257222101);
			double k0 = 1;
			double M = 0;
			double P = 0;
			coordsConv = conv(coordenadasConcluidasPhi, phi0, coordenadasConcluidasLbd, lbd0, a, f, k0, M, P);
		    System.out.println("O resultado das coordenadas geodesicas -> retangulares: M = " + df.format(coordsConv.get(0)) + " metros, P = " + df.format(coordsConv.get(1)) + " metros\n");
		    System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		} else if (datum.get(0) == "Datum Lisboa") {
			/* latitude -> 37 53 53.17608 N
 				longitude -> 07 43 03.09455 WGr 
 				M = 36 448.0117 m
				P = -196 254.9317 m*/
			int a = 6378388;
			double f = (double)1/297;
			double k0 = 1;
			double M = 0;
			double P = 0;
			coordsConv = conv(coordenadasConcluidasPhi, phi0, coordenadasConcluidasLbd, lbd0, a, f, k0, M, P);
			System.out.println("O resultado das coordenadas geodesicas -> retangulares: M = " + df.format(coordsConv.get(0)) + " metros, P = " + df.format(coordsConv.get(1)) + " metros\n");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		} else if (datum.get(0) == "Datum 73") {
			/* latitude -> 37 53 56.01135 N
				longitude -> 07 43 10.59207 WGr 
				M= 36 445.0373 m 
				P= -196 255.3140 m*/
			int a = 6378388;
			double f = (double)1/297;
			double k0 = 1;
			double M = 180.598;
			double P = -86.990;
			coordsConv = conv(coordenadasConcluidasPhi, phi0, coordenadasConcluidasLbd, lbd0, a, f, k0, M, P);
			System.out.println("O resultado das coordenadas geodesicas -> retangulares: M = " + df.format(Math.round(coordsConv.get(0))) + " metros, P = " + df.format(coordsConv.get(1)) + " metros\n");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		} else if (datum.get(0) == "PTRA08-UTM/ITRF93") {
			/* latitude -> 33 02 15.2697 N
				longitude -> 16 21 41.8679 WGr 
				M= 372 851.2519 m 
				P= 3 656 276.3028 m*/
			int a = 6378137;
			double f = (double)(1/298.257222101);
			double k0 = 0.9996;
			double M = 500000;
			double P = 0;
			coordsConv = conv(coordenadasConcluidasPhi, phi0, coordenadasConcluidasLbd, lbd0, a, f, k0, M, P);
			System.out.println("O resultado das coordenadas geodesicas -> retangulares: M = " + df.format(coordsConv.get(0)) + " metros, P = " + df.format(coordsConv.get(1)) + " metros\n");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		}
		return coordsConv;
	}

	static ArrayList<Double> conv(ArrayList<String> coordenadasConcluidasPhi, ArrayList<String> phi0, ArrayList<String> coordenadasConcluidasLbd, ArrayList<String> lbd0, int a, double f2, double k0, double m, double p) {
		 // constantes
		
		double grausPhi = Double.parseDouble(coordenadasConcluidasPhi.get(0));
		double minutosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(1));
		double segundosPhi = Double.parseDouble(coordenadasConcluidasPhi.get(2).replace(",", "."));
		double grausPhi0 = Double.parseDouble(phi0.get(0));
		double minutosPhi0 = Double.parseDouble(phi0.get(1));
		double segundosPhi0 = Double.parseDouble(phi0.get(2).replace(",", "."));
		double grauslbd = Double.parseDouble(coordenadasConcluidasLbd.get(0));
		double minutoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(1));
		double segundoslbd = Double.parseDouble(coordenadasConcluidasLbd.get(2).replace(",", "."));
		double grauslbd0 = Double.parseDouble(lbd0.get(0));
		double minutoslbd0 = Double.parseDouble(lbd0.get(1));
		double segundoslbd0 = Double.parseDouble(lbd0.get(2).replace(",", "."));
		
		double n = (double)Math.sqrt(f2*(2-f2)); // e

		double phiD =  (double) grausPhi + (double) minutosPhi/60 + (double) segundosPhi/3600; // latitude graus
		double phi0D =  (double) grausPhi0 + (double) minutosPhi0/60 + (double) segundosPhi0/3600; // latitude phi0 graus
			    
		double lbdD = (double) grauslbd + (double) minutoslbd/60 + (double) segundoslbd/3600; // longitude grays
		double lbd0D = (double) grauslbd0 + (double) minutoslbd0/60 + (double) segundoslbd0/3600; // longitude lbd0 graus
			    
		double phiDRadius = (double) Math.toRadians(phiD);
		double phi0DRadius = (double) Math.toRadians(phi0D);
		
		double lbdDRadius = (double) Math.toRadians(lbdD);
		double lbd0DRadius = (double) Math.toRadians(lbd0D);
		double lbdRadius = (lbd0DRadius - lbdDRadius);
		
		double A = (double) 1+
				(double) 3/4*Math.pow(n,2) + 
				(double) 45/64*Math.pow(n,4) + 
				(double) 175/256*Math.pow(n,6) + 
				(double) 11025/16384*Math.pow(n,8) + 
				(double) 43659/65536*Math.pow(n,10);
		
		double B = (double) 3/4*Math.pow(n,2) + 
				(double) 15/16*Math.pow(n,4) + 
				(double) 525/512*Math.pow(n,6) + 
				(double) 2205/2048*Math.pow(n,8) + 
				(double) 72765/65536*Math.pow(n,10);
		
		double C = (double)15/64*Math.pow(n,4) + 
				(double) 105/256*Math.pow(n,6) + 
				(double) 2205/4096*Math.pow(n,8) + 
				(double) 10395/16384*Math.pow(n,10);
	
		double D = (double)35/512*Math.pow(n,6) + 
				(double) 315/2048*Math.pow(n,8) + 
				(double) 31185/131072*Math.pow(n,10);
	
		double E = (double)315/16384*Math.pow(n,8) + 
				(double)3465/65536*Math.pow(n,10);
	
		double F = (double)3465/131072*Math.pow(n,10);	 
		
		// calculo arco meridiano
		double arco_merid = ex2.calc_arcoMerid(n, a, phiDRadius, phi0DRadius, A, B, C, D, E, F);
		
		// N
		double N = (double) a /
				(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(phiDRadius),2), 0.5);
		
		 // raio curvatura do meridiano
		double rho = (double) a * (
				(double) 1-Math.pow(n,2)) / (Math.pow((1-Math.pow(n,2) * Math.pow(Math.sin(phiDRadius),2)), 1.5)); 
		
		// fatores escala k
		double k1 = (double)N/rho-
				(double) Math.pow(Math.tan(phiDRadius), 2);
		
		double k2 = (double)N/rho+
				(double) 4 * (
				(double) Math.pow(N, 2)/ (double) Math.pow(rho,2))- (double) Math.pow(Math.tan(phiDRadius),2);
		
		double k3 = (double)4*(Math.pow(N, 3)/Math.pow(rho,3))*(1-6*Math.pow(Math.tan(phiDRadius),2)) + (Math.pow(N, 2)/Math.pow(rho,2))*(1+8*Math.pow(Math.tan(phiDRadius),2))-2*(N/rho)*Math.pow(Math.tan(phiDRadius),2)+Math.pow(Math.tan(phiDRadius),4);
		
		double k4 = (double)8*Math.pow(N, 4)/Math.pow(rho,4)*(11-24*Math.pow(Math.tan(phiDRadius),2))-28*(Math.pow(N,3)/Math.pow(rho,3))*(1-6*Math.pow(Math.tan(phiDRadius),2)) + (Math.pow(N,2)/Math.pow(rho,2))*(1-32*Math.pow(Math.tan(phiDRadius),2))-2*(N/rho)*Math.pow(Math.tan(phiDRadius),2)+Math.pow(Math.tan(phiDRadius),4);
		
		double k5 = (double)61-479*Math.pow(Math.tan(phiDRadius),2) + 179*Math.pow(Math.tan(phiDRadius),4)-Math.pow(Math.tan(phiDRadius),6);
		
		double k6 = (double)1385-3111*Math.pow(Math.tan(phiDRadius),2) + 543*Math.pow(Math.tan(phiDRadius),4) + Math.pow(Math.tan(phiDRadius),6);			    		    
		
		
		// trans coords
		double y0 = (double)Math.pow(lbdRadius, 2)/2 * N * Math.sin(phiDRadius) * Math.cos(phiDRadius);
		double y1 = (double)Math.pow(lbdRadius, 4)/24 * N * Math.sin(phiDRadius) * Math.pow(Math.cos(phiDRadius), 3) * k2;
		double y2 = (double)Math.pow(lbdRadius, 6)/720 * N * Math.sin(phiDRadius) * Math.pow(Math.cos(phiDRadius), 5) * k4;
		double y3 = (double)Math.pow(lbdRadius, 8)/40320 * N * Math.sin(phiDRadius) * Math.pow(Math.cos(phiDRadius), 7) * k6;
		double y = (double) k0 * (arco_merid + y0 + y1 + y2 + y3) + p; 
		
		double x0 = (double) lbdRadius * N * Math.cos(phiDRadius);
		double x1 = (double) Math.pow(lbdRadius, 3)/6 * N * Math.pow(Math.cos(phiDRadius), 3) * k1;
		double x2 = (double) Math.pow(lbdRadius, 5)/120 * N * Math.pow(Math.cos(phiDRadius), 5) * k3;
		double x3 = (double) Math.pow(lbdRadius, 7)/5040 * N * Math.pow(Math.cos(phiDRadius), 7) * k5;
		double x = (double) k0 * (x0 + x1 + x2 + x3) + m;
			
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		coordsConv.add(x);
		coordsConv.add(y);
		
		return coordsConv;			    
	}
}
