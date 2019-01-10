package dataAnalysis;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dataAnalysis.computeValue.whichVal;

public class valueSet {

	protected double Da , Db, f, k,  numStartSeed, sizeGrid , stepStore , min , max , inc ;
	private value[][] valueMatrix ;
	
	public enum setNameFile {	
		degreeDistribution , edgeCount , seedCount ,totalEdgeLength ,totalEdgeLengthMST	 , pathLengthDistribution;
		public String getNameVal ( ) {
			return this.toString() ;
		}
	}
	
	protected enum whichVal {
		stepMax, nodeCount ,nodeCountNo2d ,seedCount , averageDegree , 
		totalEdgeLength , totalEdgeLengthMST , edgeCount ;
		
		public String getNameVal ( ) {
			return this.toString() ;
		}
	}
	private ArrayList<Integer> listId = new  ArrayList<Integer> ();
	private ArrayList<double[]> listRef = new  ArrayList<double[]> () ;
	private ArrayList<Double> listRef0 = new  ArrayList<Double> () ;
	private ArrayList<Double> listRef1 = new  ArrayList<Double> () ;
	
	private ArrayList<Double> listF =  new ArrayList<Double> () ,
			 listK =  new ArrayList<Double> () ;
	
	
	private ArrayList<String> listNameVals = new ArrayList <String> () ;
	
	private ArrayList< value > arrayValue = new ArrayList<value>();
	private Map <Integer, value> mapIdV = new HashMap <Integer, value >() ;
	private Map <Integer, double[]> mapIdRef = new HashMap <Integer,  double[]>() ;
	private Map <double[], value > mapRefValue = new HashMap <double[], value > () ;

	public boolean isRefInSet ( double[] ref ) {	
		if ( listRef0.contains(ref[0]) && listRef1.contains(ref[1])) 
				return true  ;
		else 
			return false ;
	}
	
	public void initMatrixValues ( double min , double max, double inc ) {
		this.min = min ;
		this.max = max ;
		this.inc = inc ;
		valueMatrix  = new value [(int) Math.round(( max - min ) / inc ) + 1 ][(int) Math.round(( max - min ) / inc ) + 1 ];
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(3);
		
		int id = 0 ;
		for ( double f = min ; f <= max + inc ; f = f + inc ) {
			for ( double k = min ; k <= max + inc ; k = k + inc ) { 
				
				listF.add(Double.parseDouble(nf.format(f) ) );
				listK.add(Double.parseDouble(nf.format(k) ) );

				double[] fk = new double[] { Double.parseDouble(nf.format(f)) , Double.parseDouble(nf.format(k)) } ;
				value v = createValue(id++, fk ) ;
				valueMatrix [(int) ((f - min ) / inc) ]  [(int) ((k - min ) / inc) ] = v ; 
			}
		}
	}
	

	public boolean isIdInSet ( int id ) {
		if ( listId.contains(id) ) 
			return true ; 
		else 
			return false;
	}
// SET VALUE SET ------------------------------------------------------------------------------------------------------------------------------------	
	public void setValue ( value v ) {
		arrayValue.add(v) ;
		listId.add(v.getId());
		this.setRef(v.getRef());
	}
	
	public value createValue ( int id , double[] ref , ArrayList<Double>vals0,ArrayList<Double> vals1, ArrayList<String> nameVals1 ) {
		if ( listId.contains(id)) {
			System.out.println("id already exit") ;
			return null;
		}
		value v = new value(id, ref, null ) ;
		setValue(v);
		mapIdV.put(id, v);
		mapRefValue.put(ref, v);
		return v ; 
	}
	
	public value createValue ( int id , double[] ref , Map <String, ArrayList<Double>> mapIdVals ) {
		if ( listId.contains(id)) {
			System.out.println("id already exit") ;
			return null;
		}
		value v = new value(id, ref, mapIdVals ) ;
		setValue(v);
		mapIdV.put(id, v);
		mapRefValue.put(ref, v);
		return v ; 
	}
	
	public value createValue ( int id , double[] ref  ) {
		if ( listId.contains(id)) {
			System.out.println("id already exit") ;
			return null;
		}
		value v = new value(id, ref, new HashMap<String, ArrayList<Double>> () ) ;
		setValue(v);
		mapIdV.put(id, v);
		mapRefValue.put(ref, v);
		return v ; 
	}	
	
	private void setRef ( double [] ref ) {

		if ( ! listRef0.contains(ref[0]) )
			listRef0.add(ref[0]);
		
		if ( ! listRef1.contains(ref[1])) 
			listRef1.add(ref[1]);
		
		listRef.add(ref);	
	}

// GET VALUE SET ------------------------------------------------------------------------------------------------------------------------------------	
	public value[][] getValueMatrix ( ) {
		return valueMatrix ;
	}
	
	
	public value getValue ( int id ) {
		return mapIdV.get(id);
	}	
	public value getValue ( double f , double k ) {
		return valueMatrix[ (int) ( Math.round(( f - min ) / inc ))  ] [(int) ( Math.round(( k - min ) / inc ) ) ] ;
	}
	
	public value getValue ( double[] ref ) {
		return mapRefValue.get(ref);
	}
	public Map getMapRefVal () {
		return mapRefValue ;
	}
	public ArrayList<Integer> getListId () {
		return listId;
	}
	public ArrayList<double[]> getListRef () {
		return listRef;
	}
	public ArrayList<Double> getListRef0 () {
		return listRef0;
	}
	public ArrayList<Double> getListRef1 () {
		return listRef1;
	}
	public ArrayList<value> getListValues ( ) {
		return arrayValue ;
	}
}
