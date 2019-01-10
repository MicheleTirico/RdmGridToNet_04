package dataAnalysis;

import java.util.ArrayList;

public class matrixValues {

	private int[] pos ;
	private double[] ref ;
	private int id ;

	private ArrayList<Double> vals0 = new ArrayList<Double> ( ),
			vals1 = new ArrayList<Double> ( );
	private ArrayList<String> nameVals0 = new ArrayList<String> ( ) ,
			 nameVals1 = new ArrayList<String> ( ) ;
		
	public matrixValues (int id ,double[] ref , ArrayList<Double> vals0 , ArrayList<Double> vals1 , ArrayList<String>  nameVals1 ) {
		this.id = id ;
		this.ref = ref ;
		this.vals0 = vals0 ;
		this.vals1 = vals1 ;
		this. nameVals1 = nameVals1 ;
	}
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------

	public int getId ( ) {
		return id;
	}	
	public double[] getRef ( ) {
		return ref;
	}
	public ArrayList<Double>getvals0 ( ) {
		return vals0;
	}
	public ArrayList<Double> getvals1 ( ) {
		return vals1;
	}
	
	public ArrayList<String> getNameVals1 ( ) {

		return nameVals1;
	}
	
// SET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public void setref (double[] ref) {
		this.ref = ref ;
	}
	
	public void setvals0(ArrayList<Double> vals0 ) {
		this.vals0= vals0 ;
	}
	
	public void setvals1(ArrayList<Double> vals1 ) {
		this.vals1= vals1 ;
	}
	
	public void  setId (int id ) {
		this.id = id ;
	}
	
	public void setNameVals1 ( ArrayList<String> nameVals1 ) {
		this.nameVals1 = nameVals1 ;
	}
	
	public void addNameVals  ( ArrayList<String> nameVals1 ) {
		this.nameVals1.addAll(nameVals1);
	}
	
}
