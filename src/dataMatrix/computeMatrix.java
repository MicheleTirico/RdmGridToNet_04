package dataMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import dataAnalysis.computeValue;
import dataAnalysis.computeValue.whichVal;
import dataAnalysis.value;
import dataAnalysis.valueSet;
import dataAnalysis.valueSet.setNameFile;

public class computeMatrix {

	private BufferedReader br ;
	private valueSet vs = new valueSet() ;
	private int id = 0;
	private static int stepRange;
	private computeValue cv ;
	private ArrayList<String> listNameIndicators = new ArrayList<String> () ;

	public void setStepRange ( int stepRange ) {
		this.stepRange = stepRange ;
	}
	
	public void initMatrix (double min , double max , double inc ) {
		vs.initMatrixValues(min,max,inc);
	}
// COMPUTE ------------------------------------------------------------------------------------------------------------------------------------------
	public void computeNetSim (String path ) throws IOException {
		File[] files = new File(path).listFiles();	
		
		for ( File file : files ) {		//		
			FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String  lineIndex = br.readLine(),
    				lineParams = br.readLine() ;
    		
    		String[] startParams , nameStartParams ;
    		
    		try {
    			startParams = lineParams.split(";") ;
    			nameStartParams = new String[] {  "Da" , "Db"  ,"f" ,"k" ,"numStartSeed" ,"sizeGrid" ,"stepStore"} ;
        	
    		} catch (NullPointerException e) {
				continue ;
			}
    		double  f = Double.parseDouble( startParams[2] ),
    				k = Double.parseDouble( startParams[3] ) ;
    		System.out.println(f + " " + k );

    		String  lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				line = br.readLine() ,
  					nameVal = "SM_simNet_" ;
    		
    		value v = vs.getValue(f , k );
    		cv = new computeValue(vs, br); 	
    		
    		// compute values for degree distribution csv 
			if ( nameIndStr.equals(setNameFile.degreeDistribution.getNameVal())) {
				int a = 0 ;
        		while ( a < 7 ) 
        			v.appendVal(nameStartParams[a],  Double.parseDouble(startParams[a++]) , true);
        
	        	for ( whichVal wv : new whichVal[] {
	        			whichVal.stepMax ,
	        			whichVal.nodeCount  	} ) 
	        		v.appendVal (nameVal + wv.getNameVal(), cv.getValue(wv) , true );	        	
			}
			// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.edgeCount.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.edgeCount  } ) 
	        		v.appendVal(nameVal + wv.getNameVal(), cv.getValue(wv), true );
    		}
    		// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.pathLengthDistribution.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.gini  } ) 
	        		v.appendVal(nameVal + wv.getNameVal(), cv.getValue(wv) , true );
    		}
		}
		for ( value v : vs.getListValues() ) {
			double edgeCount = getValFromValue(v, "SM_simNet_edgeCount") ; 
    		double nodeCount = getValFromValue(v, "SM_simNet_nodeCount") ; 
    		
    		v.appendVal("SM_simNet_"  + "averageDegree", 2 * edgeCount / nodeCount , true );
    	
    		// set namVals in list 
    		Map<String , ArrayList<Double>> map = v.getMapIdVals();
    		for ( String s : map.keySet()) 
    			if ( ! listNameIndicators.contains(s))
    				listNameIndicators.add(s);
		}	
	}

	public void computeNet (String path ) throws IOException { 
		File[] files = new File(path).listFiles();	 
		int numFile = 0 ;
		for ( File file : files ) {		//		System.out.println(file);
			FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String  lineIndex = br.readLine(),
    				lineParams = br.readLine() ;
    		String[] startParams , nameStartParams ; 
    		try {
    			startParams = lineParams.split(";") ;	
    			nameStartParams = new String[] {  "Da" , "Db"  ,"f" ,"k" ,"numStartSeed" ,"sizeGrid" ,"stepStore"} ;
    		} catch (NullPointerException e) {
    			continue ;
    		}
    		
    		double  f = Double.parseDouble( startParams[2] ),
    				k = Double.parseDouble( startParams[3] ) ;    		//	System.out.println(f + " " + k );

    		String  lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				line = br.readLine() ;
    		 		
    		value v = vs.getValue(f , k );
    		cv = new computeValue(vs, br); 
    		
    		// compute values for degree distribution csv 
			if ( nameIndStr.equals(setNameFile.degreeDistribution.getNameVal())) {
				int a = 0 ;
        		while ( a < 7 ) {
        			v.appendVal(nameStartParams[a],  Double.parseDouble(startParams[a]), true );
        			a++ ; 
        		}
	        	for ( whichVal wv : new whichVal[] {
	        			whichVal.stepMax ,
	        			whichVal.nodeCount ,
	        			whichVal.nodeCountNo2d 	} ) 
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv) , true);
			}
			
			// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.edgeCount.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.edgeCount  } ) 
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv) , true ); 
    		}
    		
			// compute value for seed count csv 
        	if ( nameIndStr.equals(setNameFile.seedCount.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.seedCount } ) 
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv) , true );
        	}
        	
        	// compute values for totalEdgeLength csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLength.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.totalEdgeLength } ) 
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv), true );
    		}
    		
    		// compute values for totalEdgeLengthMST csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLengthMST.getNameVal())) {
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.totalEdgeLengthMST  } ) 
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv), true);
    		}
		}
  	    	
    	// compute values for combined array values 
    	for ( value v : vs.getListValues() ) {
    		
    		double totEdLen = getValFromValue(v, whichVal.totalEdgeLength);	
    		double totEdLenMST = getValFromValue( v , whichVal.totalEdgeLengthMST);		
    		double edgeCount = getValFromValue( v , whichVal.edgeCount) ; 
    		double nodeCount = getValFromValue(v, whichVal.nodeCount) ;
    		
    		v.appendVal("SM_efficacity", totEdLenMST / totEdLen , true);
    		v.appendVal("SM_averageDegree", 2 * edgeCount / nodeCount , true );
    	
    		// set namVals in list 
    		Map<String , ArrayList<Double>> map = v.getMapIdVals();
    		for ( String s : map.keySet()) 
    			if ( ! listNameIndicators.contains(s))
    				listNameIndicators.add(s);
    	}
    }
		
	public void computeNetSimStep (String path ) throws IOException {
		
		File[] files = new File(path).listFiles();	
    	for ( File file : files ) {		// System.out.println(file);
    		FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String lineIndex = br.readLine();
    		String lineParams = br.readLine() ;
    		String[] startParams , nameStartParams ;
    		
    		try {
        		startParams = lineParams.split(";") ;
        		nameStartParams = new String[] {  "Da" , "Db"  ,"f" ,"k" ,"numStartSeed" ,"sizeGrid" ,"stepStore"} ;	
    		}catch (NullPointerException e) {
				continue ;
			}
    		
    		double  f = Double.parseDouble(startParams[2]),
    				k = Double.parseDouble(startParams[3]) ;
    		
    		int posLine = 0 ;
    		String lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				lineStr = br.readLine() ,
    				nameVal = "Step" + stepRange + "_simNet_";
    		
    		double[] fk = new double[] { f , k } ;
    	
      		value v = vs.getValue(f , k );
    		cv = new computeValue(vs, br); 	
    	
    		
    		
    		// compute values for degree distribution csv 
			if ( nameIndStr.equals(setNameFile.degreeDistribution.getNameVal())) {
				int a = 0 ;
        		while ( a < 7 ) 
        			v.appendVal(nameStartParams[a],  Double.parseDouble(startParams[a++]) , true);	
        		
        		cv.computeStepValues(nameVal, cv, lineStr,  stepRange, v,new whichVal[]  {
	        			whichVal.nodeCount ,
	        			} ) ;
        		v.appendVal("SM_" + whichVal.stepMax.getNameVal(), cv.getStepMax(), true);	        	
			}
			
			// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.edgeCount.getNameVal())) {
    			cv.computeStepValues(nameVal, cv, lineStr,  stepRange, v,new whichVal[]  {
	        			whichVal.edgeCount ,
	        			} ) ;
        		v.appendVal("SM_" + whichVal.stepMax.getNameVal(), cv.getStepMax(), true);
    		}
    		
    		// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.pathLengthDistribution.getNameVal())) {
    			cv.computeStepValues(nameVal, cv, lineStr,  stepRange, v,new whichVal[]  {
	        			whichVal.gini ,
	        			} ) ;
        		v.appendVal("SM_" + whichVal.stepMax.getNameVal(), cv.getStepMax(), true);
    		}
    	}
	}
	
	public void computeNetStep (String path  ) throws IOException {
		
		File[] files = new File(path).listFiles();	
		for ( File file : files ) {		// System.out.println(file);
    		FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String lineIndex = br.readLine();
    		String lineParams = br.readLine() ;
    		String[] startParams  , nameStartParams ;
    		
    		try {
    			startParams = lineParams.split(";") ;
        		nameStartParams = new String[] {  "Da" , "Db"  ,"f" ,"k" ,"numStartSeed" ,"sizeGrid" ,"stepStore"} ;
        			
    		} catch (NullPointerException e) {
				continue; 
			}
    		
    		double
	    		f = Double.parseDouble(startParams[2]),
	    		k = Double.parseDouble(startParams[3]) ;
    		
    		String lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				lineStr = br.readLine() ;
    		
    		double[] fk = new double[] { f , k } ;
    		
    	//	System.out.println(f + " " + k );
    		value v = vs.getValue( f , k );// 		System.out.println(v.getRef()[0] + " " + v.getRef()[1] + " " + v.getListVals());
    		cv = new computeValue(vs, br); 	
    		
    		// compute values for degree distribution csv 
			if ( nameIndStr.equals(setNameFile.degreeDistribution.getNameVal())) {
				int a = 0 ;
        		while ( a < 7 ) {
        			v.appendVal(nameStartParams[a],  Double.parseDouble(startParams[a]), true );
        			a++ ; 
	        	} 
        		cv.computeStepValues(cv, lineStr, stepRange, v,new whichVal[] {
	        			whichVal.nodeCount ,
	        			whichVal.nodeCountNo2d 	} ) ;
        		v.appendVal("SM_" + whichVal.stepMax.getNameVal(), cv.getStepMax(), true);
    		}
		
			// compute values for edge csv
			if ( nameIndStr.equals(setNameFile.edgeCount.getNameVal())) {    	
    			cv.computeStepValues(cv, lineStr, stepRange, v,new whichVal[] {
    					whichVal.edgeCount  } ) ;					
    		}
    		
			// compute value for seed count csv 
        	if ( nameIndStr.equals(setNameFile.seedCount.getNameVal())) {
        		cv.computeStepValues(cv, lineStr, stepRange, v,new whichVal[] {
        				whichVal.seedCount
        		} ) ;
    		}
        	
        	// compute values for totalEdgeLength csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLength.getNameVal())) {
    			cv.computeStepValues(cv, lineStr, stepRange, v ,new whichVal[] {
	        			whichVal.totalEdgeLength 
    			} ) ;
    		}
    		
    		// compute values for totalEdgeLengthMST csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLengthMST.getNameVal())) {
    			cv.computeStepValues(cv, lineStr, stepRange, v,new whichVal[] {
	        			whichVal.totalEdgeLengthMST  
    			} ) ;
    		}
		}
		
		// compute values for combined array values 
    	for ( value v : vs.getListValues() ) {
    		try {
	    		int pos = 0 ;	//		System.out.println(v.getRef()[0] + " " + v.getRef()[1] + " " + v.getListVals());
	    		double stepMax = v.getListValues("SM_stepMax").get(0)  ;  		
	
	    		Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ;
	    		while ( pos < stepMax / stepRange - 1  ) {
	    			double totEdLen = mapIdVals.get("Step100_totalEdgeLength").get(pos) ;
	        		double totEdLenMST = mapIdVals.get("Step100_totalEdgeLengthMST").get(pos) ; 
	        		double edgeCount = mapIdVals.get("Step100_edgeCount").get(pos) ; 
	        		double nodeCount = mapIdVals.get("Step100_nodeCount").get(pos) ; 
	        			
	        		v.appendVal("Step" + stepRange + "_efficacity", totEdLenMST / totEdLen , true);
	        		v.appendVal("Step" + stepRange + "_averageDegree", 2 * edgeCount / nodeCount , true );
	 
	    			pos++ ;
	    		}
			
	    		// set namVals in list 
	    		Map<String , ArrayList<Double>> map = v.getMapIdVals();
	    		for ( String s : map.keySet()) {
	    			if ( ! listNameIndicators.contains(s))
	    				listNameIndicators.add(s);
	    		}
	    	} catch (NullPointerException e) {
				
			}
    	}
    }
	
// METHODS ------------------------------------------------------------------------------------------------------------------------------------------
	private static double getValFromValue ( value v , whichVal whichVal) {
		try {
			Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ;
			ArrayList<Double> arr = mapIdVals.get("SM_" + whichVal.getNameVal()) ;
			return arr.get(arr.size() - 1 );
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	private static double getValFromValue ( value v , String ind ) {
		try {
			Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ;
			ArrayList<Double> arr = mapIdVals.get(ind) ;
			return arr.get(0);
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	private static double getValFromValue ( value v , whichVal whichVal , int posStep ) {
		try {
			Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ;
			ArrayList<Double> arr = mapIdVals.get("Step"+stepRange+"_" + whichVal.getNameVal()) ;	
			return arr.get(posStep);
		} catch (NullPointerException e) {
			return 0;
		}
	}
	
	public valueSet getValueSet () {
		return vs ;
	}
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<String> getListNameIndicators () {
		return listNameIndicators ;
	}
	public double getStepRange () {
		return stepRange ;
	}
}
