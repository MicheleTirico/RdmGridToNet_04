package dataAnalysis;

import java.text.NumberFormat;
import java.util.ArrayList;



public class testRef2 {

	public static void main(String[] args) {
	
		double min = 0.005 , max = 0.095 , inc = 0.001;
		
		
		value [][] valueMatrix  = new value [(int) Math.round(( max - min ) / inc ) + 1 ][(int) Math.round(( max - min ) / inc ) + 1 ];
		
		ArrayList<Double> listF =  new ArrayList<Double> () ,
				 listK =  new ArrayList<Double> () ;
	
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		valueSet vs = new valueSet() ;
		
		int id = 0 ;
		for ( double f = min ; f <= max + inc ; f = f + inc ) {
			for ( double k = min ; k <= max + inc ; k = k + inc ) { 
				
				listF.add(Double.parseDouble(nf.format(f) ) );
				listK.add(Double.parseDouble(nf.format(k) ) );

				double[] fk = new double[] { Double.parseDouble(nf.format(f)) , Double.parseDouble(nf.format(k)) } ;
				value v = vs.createValue(id++, fk ) ;
				valueMatrix [(int) ((f - min ) / inc) ]  [(int) ((k - min ) / inc) ] = v ; 
			}
		}
		
		double f = 0.005 , k = 0.095 ;
	
		int posX = (int) Math.round( ( f - min ) / inc ) ,
				posY = (int) Math.round( ( k - min ) / inc ) ;
	
		int a = (int) Math.round(( max - min ) / inc ) ;
		System.out.println( a );
		value v = valueMatrix[ posX ] [posY ] ;
				
		System.out.println(v.getRef()[0] + " " + v.getRef()[1]);
		
	}

}
