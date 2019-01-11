package run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import dataAnalysis.computeValue;
import dataAnalysis.computeValue.whichVal;
import dataAnalysis.value;
import dataAnalysis.valueSet;
import dataAnalysis.valueSet.setNameFile;

public class computeMatrix {

	protected static String path = "D:\\dat\\data_analysis\\java" ; //C:\\Users\\frenz\\data\\data\\test02" ; //  "D:\\java_data_analysis\\data_test";
	static File[] files = new File(path).listFiles();	
	static BufferedReader br ;
	private static valueSet vs = new valueSet() ;
	static int id = 0 ;
	public static void main(String[] args) throws IOException  {
	    	
    	for ( File file : files ) {		//     		System.out.println(file);
    		FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String lineIndex = br.readLine();
    		String lineParams = br.readLine() ;
    		
    		String[] startParams = lineParams.split(";") ;
    		String[] nameStartParams = new String[] {  "Da" , "Db"  ,"f" ,"k" ,"numStartSeed" ,"sizeGrid" ,"stepStore"} ;
    		
    		double
	    		f = Double.parseDouble(startParams[2]),
	    		k = Double.parseDouble(startParams[3]) ;
    		
    		int posLine = 0 ;
    		String lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				line = br.readLine() ;
    		
    		double[] fk = new double[] { f , k } ;
    		value v = null ;
    		computeValue cv = new computeValue(vs, br); ;
    		
    		// create value if ref is not exist
    		if ( ! vs.isRefInSet(fk) ) 
    			v = vs.createValue(id++, fk );
    	
    		// compute values for degree distribution csv 
			if ( nameIndStr.equals(setNameFile.degreeDistribution.getNameVal())) {
				v = vs.getValue(id - 1 ) ;
				int a = 0 ;
				
        		while ( a < 7 ) {
        			v.appendVal(nameStartParams[a],  Double.parseDouble(startParams[a]));
        			a++ ; 
        		}
  	
	        	for ( whichVal wv : new whichVal[] {
	        			whichVal.stepMax ,
	        			whichVal.nodeCount ,
	        			whichVal.nodeCountNo2d ,	        			
	        	}  ) {
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv));
	        	} 
			}
			
			// compute values for edge csv
    		if ( nameIndStr.equals(setNameFile.edgeCount.getNameVal())) {
    			v = vs.getValue(id - 1 ) ;
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.edgeCount 
	        			} ) {
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv));
	        	} 
    		}
    		
			// compute value for seed count csv 
        	if ( nameIndStr.equals(setNameFile.seedCount.getNameVal())) {
        		v = vs.getValue(id - 1 ) ;
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.seedCount } ) {
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv));
	        	} 
        	}
        	
        	// compute values for totalEdgeLength csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLength.getNameVal())) {
    			v = vs.getValue(id - 1 ) ;
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.totalEdgeLength 
	        			} ) {
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv));
	        	} 
    		}
    		
    		// compute values for totalEdgeLengthMST csv
    		if ( nameIndStr.equals(setNameFile.totalEdgeLengthMST.getNameVal())) {
    			v = vs.getValue(id - 1 ) ;
        		for ( whichVal wv :  new whichVal[] {
	        			whichVal.totalEdgeLengthMST 
	        			} ) {
	        		v.appendVal("SM_" + wv.getNameVal(), cv.getValue(wv));
	        	} 
    		}	
    	}    
    	
    	// compute values for combined array values 
    	for ( value v : vs.getListValues() ) {
    		Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ; 	//	System.out.println(v.getId() + " " + mapIdVals);
    		
    		double totEdLen = getValFromValue(v, whichVal.totalEdgeLength);	
    		double totEdLenMST = getValFromValue( v , whichVal.totalEdgeLengthMST);		
    		double edgeCount = getValFromValue( v , whichVal.edgeCount) ; 
    		double nodeCount = getValFromValue(v, whichVal.nodeCount) ;
    		
    	// 	System.out.println(v.getId() + " " + nodeCount + " " + edgeCount );
    		v.appendVal("SM_efficacity", totEdLenMST / totEdLen);
    		v.appendVal("SM_averageDegree", 2 * edgeCount / nodeCount);
    	}
    	
    	for ( value v : vs.getListValues() ) {
    		Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ; 
    		System.out.println(v.getId() + " " + mapIdVals);
    	}
    }
	
	private static String[] getLastLine ( ) throws IOException {
		String[] lastLine = null ;
		String line = "" ;
		int posLine = 0 ;
		while(line!=null) {      
            line = br.readLine();  
            try {
            	lastLine = line.split(";");
            	posLine++ ;	
            } catch (NullPointerException e) {
			 br.close(); 			
            }     
		}
		return lastLine ;
	}
	

	private static double getValFromValue ( value v , whichVal whichVal) {
		Map <String, ArrayList<Double>> mapIdVals =  v.getMapIdVals() ;
		ArrayList<Double> arr = mapIdVals.get("SM_" + whichVal.getNameVal()) ;
		return arr.get(arr.size() - 1 );
	}
	
	
}
