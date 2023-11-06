package cartografia;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ex5bursawolf {
	static DecimalFormat df = new DecimalFormat("#.####");
	public static ArrayList<Double> bursaW(ArrayList<String> datum, ArrayList<Double> coordenadasEscolhidas) {
		ArrayList<Double> coordenadasFinais = new ArrayList<Double>();
		if (datum.get(0) == "Datum Lisboa/ETRS89") {
				double X = coordenadasEscolhidas.get(0);
				double Y = coordenadasEscolhidas.get(1);
				double Z = coordenadasEscolhidas.get(2);
				double vetorX = -283.088;
				double vetorY = -70.693 ;
				double vetorZ = 117.445;
				double Rx = -1.157;
				double Ry = 0.059;
				double Rz= -0.652;
				double alfa = -4.058;// escala
				coordenadasFinais = convCartToCart(datum, X, Y, Z,vetorX,vetorY,vetorZ,Rx,Ry,Rz,alfa);
			
		} else if (datum.get(0) == "Datum 73/ETRS89") {
				double X = coordenadasEscolhidas.get(0);
				double Y = coordenadasEscolhidas.get(1);
				double Z = coordenadasEscolhidas.get(2);
				double vetorX = -230.994;
				double vetorY = 102.591;
				double vetorZ = 25.199;
				double Rx = 0.633;
				double Ry = -0.239;
				double Rz= 0.900;
				double alfa = 1.950;// escala
				coordenadasFinais = convCartToCart(datum, X, Y, Z,vetorX,vetorY,vetorZ,Rx,Ry,Rz,alfa);
			
		} else if (datum.get(0) == "ETRS89/Datum Lisboa") {
				double X = coordenadasEscolhidas.get(0);
				double Y = coordenadasEscolhidas.get(1);
				double Z = coordenadasEscolhidas.get(2);
				double vetorX = 283.088;
				double vetorY = 70.693 ;
				double vetorZ = -117.445;
				double Rx = 1.157;
				double Ry = -0.059;
				double Rz= 0.652;
				double alfa = 4.058;// escala
				coordenadasFinais = convCartToCart(datum, X, Y, Z,vetorX,vetorY,vetorZ,Rx,Ry,Rz,alfa);
			
		} else if (datum.get(0) == "ETRS89/Datum 73") {
				double X = coordenadasEscolhidas.get(0);
				double Y = coordenadasEscolhidas.get(1);
				double Z = coordenadasEscolhidas.get(2);
				double vetorX = 230.994;
				double vetorY = -102.591;
				double vetorZ = -25.199;
				double Rx = -0.633;
				double Ry = 0.239;
				double Rz= -0.900;
				double alfa = -1.950;// escala
				coordenadasFinais = convCartToCart(datum, X, Y, Z,vetorX,vetorY,vetorZ,Rx,Ry,Rz,alfa);
			
		}
		prints(coordenadasFinais, datum);
		return coordenadasFinais;
	}
	
	private static ArrayList<Double> convCartToCart(ArrayList<String> datum, double x, double y, double z,
			double vetorX, double vetorY, double vetorZ, double rx, double ry, double rz, double alfa) {

		double Rx = (double) Math.toRadians(rx/3600);
		double Ry = (double) Math.toRadians(ry/3600);
		double Rz = (double) Math.toRadians(rz/3600);
		double convAlfa = (double) alfa/1000000;
	
		ArrayList<Double> xyz= new ArrayList<Double>();
		double x_final = (double) vetorX+(1+convAlfa)*(1*x-Rz*y+Ry*z);
		double y_final = (double) vetorY+(1+convAlfa)*(Rz*x+1*y-Rx*z);
		double z_final = (double) vetorZ+(1+convAlfa)*(-Ry*x+Rx*y+1*z);
		
		xyz.add(x_final);
		xyz.add(y_final);
		xyz.add(z_final);
		return xyz;
	}

	private static void prints(ArrayList<Double> coordenadasFinais, ArrayList<String> datum) {
		System.out.println("O resultado das coordenadas tridimensionais transformadas "+datum.get(0)+" : ("+df.format(coordenadasFinais.get(0)).replace(",", ".")+", "+df.format(coordenadasFinais.get(1)).replace(",", ".") + ", " + df.format(coordenadasFinais.get(2)).replace(",", ".") + ")");		
	}

}
