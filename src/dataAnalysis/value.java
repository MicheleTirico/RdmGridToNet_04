package dataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dataAnalysis.computeValue.whichVal;

public class value {

	private int[] pos ;
	private double[] ref ;
	private String[] refStr ;
	private int id ;
 
	private ArrayList<String> listNameVals = new ArrayList<String> () ;	
	private Map<String, ArrayList<Double>> mapIdVals = new HashMap<String, ArrayList<Double>> () ;
	
	public value (int id ,double[] ref , Map<String, ArrayList<Double>> mapIdVals ) {
		this.id = id ;
		this.ref = ref ;
		this. mapIdVals = mapIdVals ;
	}
	
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------

	public int getId ( ) {
		return id;
	}
		
	public double[] getRef ( ) {
		return ref;
	}
	public Map <String, ArrayList<Double>> getMapIdVals () {
		return mapIdVals ;
	}
	
	public ArrayList<Double> getListValues ( String ind ) {
		try {
		return mapIdVals.get(ind ) ;
		} catch (NullPointerException e) {
			return null ; 
		}
	}

	
// SET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public void setRef (double[] ref) {
		this.ref = ref ;
	}
	
	public void appendVal ( String id, double val ) {
		ArrayList<Double> ar ;
		if ( mapIdVals.containsKey(id) ) 
			ar = mapIdVals.get(id);	
		else 
			ar = new ArrayList<Double> ();
		ar.add(val);
		mapIdVals.put(id, ar);	
	}
	
	public void appendVal ( String id, double val , boolean setValNAmeInList ) {
		ArrayList<Double> ar ;
		if ( mapIdVals.containsKey(id) ) 
			ar = mapIdVals.get(id);	
		else 
			ar = new ArrayList<Double> ();
		ar.add(val);
		mapIdVals.put(id, ar);	
		if ( setValNAmeInList && ! listNameVals.contains(id))
			listNameVals.add(id);
	}	
	
	public void appendValArr ( String id, double val ) {
		ArrayList<Double> ar ;
		if ( mapIdVals.containsKey(id) ) 
			ar = mapIdVals.get(id);	
		else 
			ar = new ArrayList<Double> ();
		ar.add(val);
		mapIdVals.put(id, ar);	
	}
	
	public void setEmptyListVals (String[] idList) {
		for (String id : idList )
			mapIdVals.put(id, new ArrayList<Double> () ) ;
	}
	
	public ArrayList<String> getListVals ( ) {
		return listNameVals ;
	}
	

}