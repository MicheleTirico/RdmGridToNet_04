package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.apache.*;

import dataAnalysis.computeValue.whichVal; 
 	

public class computeValue {
	private valueSet vs ;
	private File f ;
	private  BufferedReader br ;
	private String[] lastLine = null  , lineToExport = null ; 
	private double nodeCount = -1 , totEdLen = -1 , totEdLenMST = -1 , stepMax = -1;
	
	private static String[] setNameInd = new String[] {"degreeDistribution" , "edgeCount" ,"seedCount" ,"totalEdgeLength" ,"totalEdgeLengthMST"} ;
	protected static enum nameInd {	degreeDistribution , edgeCount , seedCount ,totalEdgeLength ,totalEdgeLengthMST	}
	
	public static enum whichVal {
		stepMax, nodeCount ,nodeCountNo2d ,seedCount , averageDegree , 
		totalEdgeLength , totalEdgeLengthMST , edgeCount ,
		gini ;
		
		public String getNameVal ( ) {
			return this.toString() ;
		}
	}
	
	
	public computeValue ( valueSet vs , BufferedReader br ) {
		this.vs = vs ;
		this.br = br ; 
	}
	
	
	public double getValue (whichVal wv ) throws NumberFormatException, IOException { 	
		double v = 0 ;
		if ( lastLine == null )
			lastLine = getLastLine () ;
		try {
			switch (wv) {
			case stepMax:
				v =  Double.parseDouble(lastLine[0]) ;
				break;
			case nodeCount :
				v = getNodeCount () ;
				break ;
			case nodeCountNo2d :
				v = getNodeCountNo2d ( lastLine ) ;
				break ;
			case seedCount :
				v = Double.parseDouble(lastLine[1])  ;
				break ;
			case edgeCount :
				v = Double.parseDouble(lastLine[1])  ;
				break ;
			case totalEdgeLength :
				totEdLen = v = Double.parseDouble(lastLine[1]) ;
				break ;
			case totalEdgeLengthMST :
				totEdLenMST = v = Double.parseDouble(lastLine[1]) ;
				break ;
			case gini : 
				v = getGini () ;
			default:
				break;
			}
			return v ; 
		} catch (ArrayIndexOutOfBoundsException | NullPointerException  e) {
	//		e.printStackTrace();
			return 0 ;
		}
	}
	
	public void computeStepValues ( computeValue cv , String lineStr ,  int stepRange, value v , whichVal[] wv ) throws IOException {
		String[] lineArr = null ;    
		int pos = 0 ;
		while(lineStr!=null ) {      
            lineStr = br.readLine();  
            try {
            	pos ++ ;
            	lineArr = lineStr.split(";");
            	double step = Double.parseDouble(lineArr[0]); 
            	if ( step / (double) stepRange - (int)( step / (double) stepRange ) < 0.0001)  {  		
            		for ( whichVal val : wv )                    			
            	 		v.appendVal("Step"+stepRange+"_" + val.getNameVal(), cv.getValue(val ,lineArr) , true ); 	
            	}
            } catch (NullPointerException e) {
            	br.close(); 	           
            }
            stepMax = pos * 10  - 10   ;         
		}
	}
	
	public void computeStepValues ( String initName , computeValue cv , String lineStr ,  int stepRange, value v , whichVal[] wv ) throws IOException {
		String[] lineArr = null ;    
		int pos = 0 ;
		while(lineStr!=null ) {      
            lineStr = br.readLine();  
            try {
            	pos ++ ;
            	lineArr = lineStr.split(";");
            	double step = Double.parseDouble(lineArr[0]); 
            	if ( step / (double) stepRange - (int)( step / (double) stepRange ) < 0.0001)  {  		
            		for ( whichVal val : wv )                    			
            	 		v.appendVal(initName + val.getNameVal(), cv.getValue(val ,lineArr) , true ); 	
            	}
            } catch (NullPointerException e) {
            	br.close(); 	           
            }
            stepMax = pos * 10  - 10   ;         
		}
	}
	
	public double getValue ( whichVal wv , String[] lineArr ) throws IOException {
		double v = 0 ;
		try {
			switch (wv) {
			case stepMax:
				v=  Double.parseDouble(lineArr[0]) ;
				break;
			case nodeCount :
				v = getNodeCount (lineArr) ;
				break ;
			case nodeCountNo2d :
				v = getNodeCountNo2d ( lineArr ) ;
				break ;
			case seedCount :
				v = Double.parseDouble(lineArr[1])  ;
				break ;
			case edgeCount :
				v = Double.parseDouble(lineArr[1])  ;
				break ;
			case totalEdgeLength :
				totEdLen = v = Double.parseDouble(lineArr[1]) ;
				break ;
			case totalEdgeLengthMST :
				totEdLenMST = v = Double.parseDouble(lineArr[1]) ;
				break ;
			case gini : 
				v = getGini () ;
			default:
				break;
			}
		}catch (ArrayIndexOutOfBoundsException e) {		
		}
		return v ; 
	}

// GET VALUES ---------------------------------------------------------------------------------------------------------------------------------------
	private double getGini () {	
		return 0;
	}
	public double getStepMax ( ) {
		return stepMax ; 
	}
	
	private double getNodeCountNo2d (String [ ] lineArr ) throws IOException {
		if ( nodeCount < 0  )
			nodeCount = getNodeCount() ;
		try {
			double d2 = Double.parseDouble(lineArr[2] ) ;
			return nodeCount - d2 ; 
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0 ; 
		}
	}
	
	private double getNodeCount () throws IOException {
		int pos = 1 ;
		double val = 0 ;
		while ( pos < lastLine.length )
			val = val + Double.parseDouble(lastLine[pos++] ) ;
		nodeCount = val ;
		return val ;
	}
	
	private double getNodeCount (String[] line ) throws IOException {
		int pos = 1 ;
		double val = 0 ;
		while ( pos < line.length )
			val = val + Double.parseDouble(line[pos++] ) ;
		nodeCount = val ;
		return val ;
	}
	
	/**
	 * get last line
	 * @return list of string 
	 * @throws IOException
	 */
	public String[] getLastLine ( ) throws IOException  {
		String[] lastLine = null ;
		String line = "" ;
		int posLine = 0 ;
		while(line!=null) {      
            line = br.readLine();  
            try {
            	lastLine = line.split(";");
            	posLine++ ;	
            } catch (NullPointerException e) {    //	e.printStackTrace();
          //  	br.close(); 	            //	return lastLine ;
            }     
		}
		return lastLine ;
	}
	
	

}
