package dataMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dataAnalysis.computeValue.whichVal;

public class valueSet {

	private int id = 0 ; 
	protected double Da , Db, f, k,  numStartSeed, sizeGrid , stepStore ;
	
	public enum setNameFile {	
		degreeDistribution , edgeCount , seedCount ,totalEdgeLength ,totalEdgeLengthMST	;
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
	
	private ArrayList< value > arrayValue = new ArrayList<value>();
	private Map <Integer, value> mapIdV = new HashMap <Integer, value >() ;
	private Map <Integer, double[]> mapIdRef = new HashMap <Integer,  double[]>() ;
	private Map <double[], value > mapRefValue = new HashMap <double[], value > () ;

	public boolean isRefInSet ( double[] ref ) {
		if ( listRef0.contains(ref[0]) && listRef1.contains(ref[1]))	
			return true ;
		else 
			return false;
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

	
	public value getValue ( int id ) {
		return mapIdV.get(id);
	}	
	
	public value getValue ( double[] ref ) {
		return mapRefValue.get(ref);
	}
	public Map getMap () {
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
