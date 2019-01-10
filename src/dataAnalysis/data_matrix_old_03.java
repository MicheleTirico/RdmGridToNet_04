package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import scala.languageFeature.reflectiveCalls;

public class data_matrix_old_03 {

	protected static double Da , Db, f, k,  numStartSeed, sizeGrid , stepStore;
	protected static String path = "D:\\java_data_analysis\\data_test";
	
	private static String[] setNameInd = new String[] {"degreeDistribution" , "edgeCount" ,"seedCount" ,"totalEdgeLength" ,"totalEdgeLengthMST"} ;
	protected static enum nameInd {	degreeDistribution , edgeCount , seedCount ,totalEdgeLength ,totalEdgeLengthMST	}
	protected static enum whichVal {
		stepMax, nodeCount ,nodeCountNo2d ,seedCount , averageDegree , 
		totalEdgeLength , totalEdgeLengthMST , edgeCount ;
		
		public String getNameVal ( ) {
			return this.toString() ;
		}
	}
	
	static ArrayList<Double> listF = new  ArrayList<Double> () ;
	static ArrayList<Double> listK = new  ArrayList<Double> ();
	static ArrayList<Integer> listId = new  ArrayList<Integer> ();
	static ArrayList<double[]> listFK = new  ArrayList<double[]> () ;
	
	static File[] files = new File(path).listFiles();
	static ArrayList< matrixValues > valuesStepMax = new ArrayList<matrixValues>();
	
	static BufferedReader br ;
    public static void main(String[] args) throws IOException  {
    	
    	for ( File file : files ) {		// System.out.println(file);
    		FileReader fr = new FileReader(file);
    		br = new BufferedReader(fr);
    		
    		String lineIndex = br.readLine();
    		String lineParams = br.readLine() ;
    		String[] startParams = lineParams.split(";") ;
    		
    		// get params 
    		Da = Double.parseDouble(startParams[0]);
    		Db = Double.parseDouble(startParams[1]) ;
    		f = Double.parseDouble(startParams[2]);
    		k = Double.parseDouble(startParams[3]) ;
    		numStartSeed = Double.parseDouble(startParams[4]) ;
    		sizeGrid = Double.parseDouble(startParams[5]) ;
    		stepStore = Double.parseDouble(startParams[6]) ;
    	
    		int posLine = 0 ;
    		String lineIndicator = br.readLine() ,
    				nameIndStr = lineIndicator.split(";")[0],
    				line = br.readLine() ;
    		
    		boolean  isFKinList = false;
    		
    		if ( ! listF.contains(f) || ! listK.contains(k)) {
    			isFKinList = true ;
    			int id = valuesStepMax.size() ;
    			double[] fk = new double[] {f,k} ;
    			ArrayList<Double> commonParams = new ArrayList<Double>( Arrays.asList( Da , Db,  numStartSeed, sizeGrid , stepStore )) ;	
    			matrixValues mv = new matrixValues(id, fk, commonParams , new ArrayList<Double>(), new ArrayList<String>());
    			
    			if (! listF.contains(f))
    				listF.add(f);
    			if (! listK.contains(k))
    				listK.add(k);
    			
    			listFK.add(fk);
    			listId.add(id);
    			valuesStepMax.add(mv);	
    			computeStepMax_old csm = new computeStepMax_old();
    			
    			
    			// compute for degree distribution
        		if ( nameIndStr.equals(setNameInd[0])) {
        			whichVal[] wv = new whichVal [] { 
        					whichVal.stepMax, 
        					whichVal.nodeCount ,
        					whichVal.nodeCountNo2d} ;
        		
        			ArrayList<String> n = mv.getNameVals1() ;
        			n.addAll(getListNameVals(wv)) ;
        			
        			ArrayList<Double> vals = csm.getValsList(wv) ;	
        			mv.setvals1(vals);	
        			mv.setNameVals1(n);
        		}
        		System.out.println(nameIndStr);
        		if ( nameIndStr.equals(setNameInd[1])) {
        			System.out.println(nameIndStr);
        			
        		}
        	
    		}
    		
    	    	       
    	}
        
    	for ( matrixValues mv : valuesStepMax ) {
//    		System.out.println(mv.getvals0()) ;
//    		System.out.println(mv.getNameVals1()) ;
//    		System.out.println(mv.getvals1()) ;
    	}
   
        System.out.println("size listF " + listF.size() + listF);
        System.out.println("size listK " + listK.size() + listK);
        System.out.println("size listFK " + listFK.size() +  listFK );
        System.out.println("size listid " + listId.size() + listId );
    }
    
    private static String[] getNameVals (whichVal[] wv ) {
    	int pos = 0 ;
		String [ ] nameVals = new String[10] ;
    	for ( whichVal w : wv  ) 
			nameVals[pos++] = w.getNameVal();
    	return nameVals ;
    }
    
    private static ArrayList<String> getListNameVals (whichVal[] wv ) {
    	ArrayList<String> nameVals = new ArrayList<String>()  ;
    	for ( whichVal w : wv  ) 
			nameVals.add(w.getNameVal());
    	return nameVals ;
    }
}
