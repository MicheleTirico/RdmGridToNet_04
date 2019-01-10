package dataAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import dataAnalysis.data_matrix.nameInd;

public class computeStepMax_old extends data_matrix {

	public double getValue (  whichVal whichVal  ) throws IOException {
		
		double val = 0 ;
		
		String[] lastLine = getLastLine();

		switch (whichVal) {
		case stepMax:
			val = Double.parseDouble(lastLine[0] ) ;
			break;
		}
		return val ;
	}
	
	public double getValue (  whichVal whichVal , String[] lastLine  ) throws IOException {
		
		double val = 0 , nodeCount = -1 ;
		switch (whichVal) {
		case stepMax:
			val = Double.parseDouble(lastLine[0] ) ;
			break;
		case nodeCount :
			val = computeNodeCount ( lastLine ) ;
			nodeCount = val ;
			break ;
		case nodeCountNo2d :
			if ( nodeCount == -1 ) 
				nodeCount = computeNodeCount(lastLine)  ;
			val = nodeCount - Double.parseDouble(lastLine[2]);
			break ;
		case edgeCount : 
			val = Double.parseDouble(lastLine[1] );
			break ;
		}
		return val ;
	}
	
	private double computeNodeCount ( String[] lastLine ) {
		int pos = 1 ;
		double val = 0 ;
		while ( pos < lastLine.length )
			val = val + Double.parseDouble(lastLine[pos++] ) ;
		return val ;
	}
	
	
	public double[] getVals (  whichVal[] whichVal  ) throws IOException {
		
		double[] vals = new double[whichVal.length] ;
		String[] lastLine = getLastLine();
		int pos = 0 ;
		while ( pos < whichVal.length) {
			double val = getValue(whichVal[pos], lastLine) ;
			vals[pos] = val ;
			pos++ ;
		}
		return vals;
	}
	
	public ArrayList<Double> getValsList (  whichVal[] whichVal  ) throws IOException {
		ArrayList<Double>  vals = new ArrayList<Double>() ;
		String[] lastLine = getLastLine();
		int pos = 0 ;
		while ( pos < whichVal.length) {
			double val = getValue(whichVal[pos], lastLine) ;
			vals.add(val) ;
			pos++ ;
		}
		return vals;
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
	
	
}
