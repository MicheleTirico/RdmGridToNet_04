package dataAnalysis;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

public class toolkit {

	public static String[] getEmptyCsv ( String path ) {
		String[] pathCsv = null ;
		int pos = 0 ;
		File dir = new File(path) ;
		for ( File f : dir.listFiles()) 
			pathCsv[pos++] = f.getPath() ;	
		return pathCsv;
	}
	
	public static void test ( ArrayList<double[]> params , String path ) {
		for ( double[] fk : params ) {
			double f = fk[0], 
					k = fk[1];	//	System.out.println(f + " " + k);
			
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(3);
			String nameFile = "f-"+ nf.format(f)+"_k-"+ nf.format(k)+"_";
			
			
		}
	}
}
