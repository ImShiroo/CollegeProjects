package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex2 {
	static DecimalFormat df = new DecimalFormat("#.####");
	public static ArrayList<String> ret_geodesico(String m_recebido, String p_recebido, ArrayList<String> phi0, ArrayList<String> lbd0, ArrayList<String> datum) { 
		ArrayList<String> MeP = new ArrayList<String>();
		if (datum.get(0) == "PT-TM06/ETRS89") {
			/*  latitude -> 37 53 58.7635 N 
			 *  longitude -> 07 43 07.2999 WGr
			 *  M = 36448.61 m
			 * P = -196253.96 m
			*/
			int a = 6378137;
			double f = (double)(1/298.257222101);
			double k0 = 1;
			double M = 0;
			double P = 0;
			ArrayList<Double> coordsConv = conv(datum, M, phi0, P, lbd0, a, f, k0, m_recebido, p_recebido);
			MeP = finalgeodesicCoords(coordsConv);
		
		} else if (datum.get(0) == "Datum Lisboa") {
			/* latitude -> 37 53 53.17608 N
 				longitude -> 07 43 03.09455 WGr 
 				M = 36448.0117 m
				P = -196254.9317 m*/
			int a = 6378388;
			double f = (double)1/297;
			double k0 = 1;
			double M = 0;
			double P = 0;	
			ArrayList<Double> coordsConv = conv(datum, M, phi0, P, lbd0, a, f, k0, m_recebido, p_recebido);			
			MeP = finalgeodesicCoords(coordsConv);		

		} else if (datum.get(0) == "Datum 73") {
			/* latitude -> 37 53 56.01135 N
				longitude -> 07 43 10.59207 WGr 
				M= 36445.0373 m 
				P= -196255.3140 m*/
			int a = 6378388;
			double f = (double)1/297;
			double k0 = 1;
			double M = 180.598;
			double P = -86.990;
			
			ArrayList<Double> coordsConv = conv(datum, M, phi0, P, lbd0, a, f, k0, m_recebido, p_recebido);			
			MeP = finalgeodesicCoords(coordsConv);

		} else if (datum.get(0) == "PTRA08-UTM/ITRF93") {
			/* latitude -> 33 02 15.2697 N
				longitude -> 16 21 41.8679 WGr 
				M= 372851.2519 m 
				P= 3656276.3028 m*/
			
			int a = 6378137;
			double f = (double)(1/298.257222101);
			double k0 = 0.9996;
			double M = 500000;
			double P = 0;
			
			ArrayList<Double> coordsConv = conv(datum, M, phi0, P, lbd0, a, f, k0, m_recebido, p_recebido);			
			MeP = finalgeodesicCoords(coordsConv);
		}
		
		prints(MeP);
		return MeP;
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
		
		if (phigrausDouble < 0) { // Se M negativo
			if (lbdgrausDouble < 0) { // Se M negativo e P negativo
				coordsFinais.add( Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add( Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} else if (lbdgrausDouble > 0) { // Se M negativo e P positivo
				coordsFinais.add( Math.abs(phiGraus)+ " " + Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "S");
				coordsFinais.add( Math.abs(lbdGraus)+ " " + Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		} else if (phigrausDouble > 0) { // Se M positivo
			if (lbdgrausDouble < 0) {  // Se M positivo e P negativo
				coordsFinais.add( Math.abs(phiGraus)+ " " +  Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N");
				coordsFinais.add( Math.abs(lbdGraus)+ " " +  Math.abs(lbdMinutes)+ " "+ df.format(Math.abs(lbdSeconds)) + " " + "WGr");
			} if (lbdgrausDouble > 0) {  // Se M positivo e P positivo
				coordsFinais.add( Math.abs(phiGraus)+ " " +  Math.abs(phiMinutes) + " "+ df.format(Math.abs(phiSeconds)) + " " + "N");
				coordsFinais.add( Math.abs(lbdGraus)+ " " +  Math.abs(lbdMinutes) + " "+ df.format(Math.abs(lbdSeconds)) + " " + "E");
			}
		}
		
		return coordsFinais;
	}

	private static void prints(ArrayList<String> MeP) {
		// longitude
		String[] mepString = MeP.get(0).split(" ");
		String grausPhi = mepString[0];
		String minutosPhi = mepString[1];
		String segundosPhi = mepString[2];
		String NSEWPhi = mepString[3];
		// latitude
		String[] mepStringLbd = MeP.get(1).split(" ");
		String grausLbd = mepStringLbd[0];
		String minutosLbd = mepStringLbd[1];
		String segundosLbd = mepStringLbd[2];
		String NSEWLbd = mepStringLbd[3];
		
		System.out.println("O resultado das coordenadas retangulares -> geodesicas: " + "Latitude = " + grausPhi + "º " + minutosPhi + "' " + segundosPhi + "'' "+ NSEWPhi + "     Longitude = " + grausLbd + "º " + minutosLbd + "' " + segundosLbd + "'' "+ NSEWLbd);
		System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		
	}

	private static ArrayList<Double> conv(ArrayList<String> datum, double m, ArrayList<String> phi0, double p, ArrayList<String> lbd0, int a,
			double f, double k0, String m_recebido, String p_recebido) {
		
		double PUpdate = (double) Double.parseDouble(p_recebido);
		double MUpdate = (double) Double.parseDouble(m_recebido);
		
		MUpdate = MUpdate - m;
		PUpdate = PUpdate - p;
		
		double sigmaAprox = (double) PUpdate/k0;
		double n = (double) Math.sqrt(f*(2-f)); // e
		double grausPhi0 = Double.parseDouble(phi0.get(0));
		double minutosPhi0 = Double.parseDouble(phi0.get(1));
		double segundosPhi0 = Double.parseDouble(phi0.get(2));
		double grauslbd0 = Double.parseDouble(lbd0.get(0));
		double minutoslbd0 = Double.parseDouble(lbd0.get(1));
		double segundoslbd0 = Double.parseDouble(lbd0.get(2));
		
		double phi0D =  (double) grausPhi0 + (double) minutosPhi0/60 + (double) segundosPhi0/3600; // latitude phi0 graus
		double lbd0D = (double) grauslbd0 + (double) minutoslbd0/60 + (double) segundoslbd0/3600; // longitude lbd0 graus
		lbd0D = -lbd0D;
		double phi0DRadius = (double) Math.toRadians(phi0D);
		double lbd0DRadius = (double) Math.toRadians(lbd0D);
		
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
		
		double phi = (double) phi0DRadius + 
				(double) (sigmaAprox/(A*a*(1-Math.pow(n,2)))); // aproximacao ao phi latitude
		
		double delta_phi = (double) 1;
		double expressao = Math.pow(10, -10);
		double rho = 0;
		while (Math.abs(delta_phi) > expressao) {
			double arco_merid = calc_arcoMerid(n,a,phi,phi0DRadius, A, B, C, D, E, F);
			rho = (double) a * (
					(double) 1-Math.pow(n,2)) / (Math.pow((1-Math.pow(n,2) * Math.pow(Math.sin(phi),2)), 1.5)); 
			delta_phi = (sigmaAprox - arco_merid)/rho;
			phi = phi + delta_phi;	// latitude
		}
		
		double N = (double) a /
				(double) Math.pow(1-Math.pow(n,2) * (double) Math.pow(Math.sin(phi),2), 0.5);
		double psii = (double) N/rho;
		double tanPhi = Math.tan(phi);
	
		double phiFinal = (double) calcLat(phi,phi0DRadius,tanPhi,k0,rho,MUpdate,N,psii);
		double lbdFinal = (double) calcLong(phi,lbd0DRadius,tanPhi,k0,MUpdate,N,psii,PUpdate);
		
		ArrayList<Double> coordenadasGeoFinais = new ArrayList<Double>();
		coordenadasGeoFinais.add(Math.toDegrees(phiFinal));
		coordenadasGeoFinais.add(Math.toDegrees(lbdFinal));
	
		return coordenadasGeoFinais;
	}

	private static Double calcLong(double phi, double lbd0DRadius, double tanPhi, double k0, double mUpdate,
			double N, double psii, double pUpdate) {
		
		double expressaoLat_1 = (double) mUpdate / (k0*N);
		double expressaoLat_2 = (double) Math.pow(mUpdate, 3) / (6*Math.pow(k0, 3)*Math.pow(N, 3));
		double expressaoLat_3 = (double) psii+2*Math.pow(tanPhi, 2);
		double expressaoLat_4 = (double) Math.pow(mUpdate, 5) / (120*Math.pow(k0, 5)*Math.pow(N,5));
		double expressaoLat_5 = (double) -(4)*Math.pow(psii, 3) * (1-6*Math.pow(tanPhi, 2)) + Math.pow(psii, 2) * (9-68*Math.pow(tanPhi, 2)) + 72*psii*Math.pow(tanPhi, 2) + 24*Math.pow(tanPhi, 4);
		double expressaoLat_6 = (double) Math.pow(mUpdate, 7) / (5040*Math.pow(k0, 7)*Math.pow(N, 7));
		double expressaoLat_7 = (double) 61 + 662*Math.pow(tanPhi, 2) + 1320*Math.pow(tanPhi, 4) + 720*Math.pow(tanPhi, 6);
		double longitude = (double) (expressaoLat_1 - expressaoLat_2 * expressaoLat_3 + expressaoLat_4 * expressaoLat_5 - expressaoLat_6 * expressaoLat_7)/Math.cos(phi);   
		
		longitude += lbd0DRadius;
		
		return longitude;
	}

	private static Double calcLat(double phi,double phi0DRadius, double tanPhi, double k0, double rho, double mUpdate, double N,
			double psii) {
			
		double expressaoLat_1 = (double) tanPhi / (k0*rho);
		double expressaoLat_2 = (double) Math.pow(mUpdate, 2) / (2*k0*N);
		double expressaoLat_3 = (double) Math.pow(mUpdate, 4) / (24*Math.pow(k0, 3)*Math.pow(N, 3));
		double expressaoLat_4 = (double) -4*Math.pow(psii, 2) + 9*psii*(1 - Math.pow(tanPhi, 2)) + 12*Math.pow(tanPhi, 2);
		double expressaoLat_5 = (double) Math.pow(mUpdate, 6) / (720*Math.pow(k0, 5)*Math.pow(N, 5));
		double expressaoLat_6 = (double) 8*Math.pow(psii, 4)*(11 - 24*Math.pow(tanPhi, 2)) - 12*Math.pow(psii, 3)*(21-71*Math.pow(tanPhi, 2));
		double expressaoLat_7 = (double) 15*Math.pow(psii, 2)*(15 - 98*Math.pow(tanPhi, 2) + 15*Math.pow(tanPhi, 4)) + 180*psii * (5*Math.pow(tanPhi, 2) - 3*Math.pow(tanPhi, 4) - 360*Math.pow(tanPhi, 4));
		double expressaoLat_8 = (double) Math.pow(mUpdate, 8) / (40320*Math.pow(k0, 7)*Math.pow(N, 7));
		double expressaoLat_9 = (double) 1385 + 3633*Math.pow(tanPhi, 2) + 4095*Math.pow(tanPhi, 4) + 1575*Math.pow(tanPhi, 6);
		double latitude = phi - expressaoLat_1 * expressaoLat_2 + expressaoLat_1 * expressaoLat_3 * expressaoLat_4 - expressaoLat_1 * expressaoLat_5 * 
				(expressaoLat_6 + expressaoLat_7) + expressaoLat_1 * expressaoLat_8 * (expressaoLat_9);
		
		return latitude;
	}

	public static double calc_arcoMerid(double n, int a, double phi, double phi0DRadius, double A, double B, double C, double D, double E, double F) {
		
		double p0 = (double)a*(
				(double)1-Math.pow(n, 2));
		double p1 = (double)A*(
				(double)phi-phi0DRadius);
		double p2 = (double) B/2*(
				(double) Math.sin(2*phi)-
				(double) Math.sin(2*phi0DRadius));
		double p3 = (double)C/4 * (
				(double) Math.sin(4*phi)-
				(double) Math.sin(4*phi0DRadius));
		double p4 = (double)D/6 *( 
				(double) Math.sin(6*phi)-
				(double)Math.sin(6*phi0DRadius));
		double p5 = (double)E/8 * (
				(double)Math.sin(8*phi)-
				(double)Math.sin(8*phi0DRadius));
		double p6 = (double) F/10 * (
				(double)Math.sin(10*phi)-
				(double) Math.sin(10*phi0DRadius)); 
		
		double arco_merid = (double) p0 * (
				(double) p1-p2+p3-p4+p5-p6);
		return arco_merid;
	}
	
}
