package dataAnalysis;

public class matrixValues_old_02 {

	private int[] pos ;
	private double[] vals0 , vals1 , ref ;
	private int id ;
	private String[] nameVals1 = new String[10];
	
	public matrixValues_old_02 (int id ,double[] ref , double[] vals0 , double[] vals1) {
		this.id = id ;
		this.ref = ref ;
		this.vals0 = vals0 ;
		this.vals1 = vals1 ;
	}
	
	public matrixValues_old_02 (int id ,double[] ref , double[] vals0 , double[] vals1 , String[] nameVals1 ) {
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
	
	public double[] getvals0 ( ) {
		return vals0;
	}
	public double[] getvals1 ( ) {
		if ( vals1 == null )
			vals1 = new double[0] ;
		return vals1;
	}
	
	public String[] getNameVals1 ( ) {
		return nameVals1;
	}
	
// SET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public void setref (double[] ref) {
		this.ref = ref ;
	}
	
	public void setvals0(double[] vals0 ) {
		this.vals0= vals0 ;
	}
	
	public void setvals1(double[] vals1 ) {
		this.vals1= vals1 ;
	}
	
	public void  setId (int id ) {
		this.id = id ;
	}
	
	public void setNameVals1 ( String [] nameVals1 ) {
		this.nameVals1 = nameVals1 ;
	}

}
