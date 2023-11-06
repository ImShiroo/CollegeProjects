package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex7polinomial {
	static DecimalFormat df = new DecimalFormat("#.####");
	public static ArrayList<Double> poli(double M, double P, ArrayList<String> datum) {
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		if (datum.get(0) == "Datum Lisboa/ETRS89") { 
			double a0 = 1.38051;
			double a1 = 129998.56256;
			double a2 = -1.69483;
			double a3 = -0.57226;
			double a4 = -2.9606;
			double a5 = -2.45601;
			double b0 = 0.80894;
			double b1 = 1.31669;
			double b2 = 279995.74505;
			double b3 = 0.24888;
			double b4 = 2.65999;
			double b5 = -3.86484;
			double x0 = 0;
			double y0 = 0;
			double h = 130000;
			double k = 280000;
			coordsConv = conv(M, P, a0, a1, a2, a3, a4, a5, b0, b1, b2, b3, b4, b5, x0, y0, h, k);
		} else if (datum.get(0) == "Datum 73/ETRS89") {
			double a0 = 0.28961;
			double a1 = 129999.16977;
			double a2 = -5.26888;
			double a3 = -0.32257;
			double a4 = -0.87853;
			double a5 = -1.22237;
			double b0 = -0.08867;
			double b1 = 2.39595;
			double b2 = 279997.91435;
			double b3 = 0.15146;
			double b4 = 1.11109;
			double b5 = -1.06143;
			double x0 = 0;
			double y0 = 0;
			double h = 130000;
			double k = 280000;
			coordsConv = conv(M, P, a0, a1, a2, a3, a4, a5, b0, b1, b2, b3, b4, b5, x0, y0, h, k);
		}
		
		prints(coordsConv, datum);
		return coordsConv;
	}
	
	private static ArrayList<Double> conv(double m, double p, double a0, double a1, double a2, double a3, double a4,
			double a5, double b0, double b1, double b2, double b3, double b4, double b5, double x0, double y0, double h,
			double k) {
		double u = (m - x0) / h;
		double v = (p - y0)/k;
		double M_final = a0 + a1*u + a2*v + a3*Math.pow(u, 2) + a4*u*v+ a5*Math.pow(v, 2);
		double P_final = b0 + b1*u + b2*v + b3*Math.pow(u, 2) + b4*u*v+ b5*Math.pow(v, 2);
		ArrayList<Double> coordsConv = new ArrayList<Double>();
		coordsConv.add(M_final);
		coordsConv.add(P_final);
		
		return coordsConv;	
	}

	private static void prints(ArrayList<Double> coordenadasFinais, ArrayList<String> datum) {
		System.out.println("O resultado das coordenadas retangulares transformadas "+datum.get(0)+" : M = " + df.format(coordenadasFinais.get(0)) + ", P = " + df.format(coordenadasFinais.get(1)));
	}
	

}
