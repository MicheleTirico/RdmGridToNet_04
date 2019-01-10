package dataMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class computeValue {
	private valueSet vs ;
	private File f ;
	private  BufferedReader br ;
	private String[] lastLine = null  ;
	private double nodeCount = -1 , totEdLen = -1 , totEdLenMST = -1 ;
	
	private static String[] setNameInd = new String[] {"degreeDistribution" , "edgeCount" ,"seedCount" ,"totalEdgeLength" ,"totalEdgeLengthMST"} ;
	protected static enum nameInd {	degreeDistribution , edgeCount , seedCount ,totalEdgeLength ,totalEdgeLengthMST	}
	public static enum whichVal {
		stepMax, nodeCount ,nodeCountNo2d ,seedCount , averageDegree , 
		totalEdgeLength , totalEdgeLengthMST , edgeCount ;
		
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
		switch (wv) {
		case stepMax:
			v=  Double.parseDouble(lastLine[0]) ;
			break;
		case nodeCount :
			v = getNodeCount () ;
			break ;
		case nodeCountNo2d :
			v = getNodeCountNo2d ( ) ;
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
		default:
			break;
		}
		return v ; 
	}

// GET VALUES ---------------------------------------------------------------------------------------------------------------------------------------
	
	private double getNodeCountNo2d () throws IOException {
		if ( nodeCount < 0  )
			nodeCount = getNodeCount() ;
		double d2 = Double.parseDouble(lastLine[2] ) ;
		return nodeCount - d2 ; 
	}
	
	private double getNodeCount () throws IOException {
		int pos = 1 ;
		double val = 0 ;
		while ( pos < lastLine.length )
			val = val + Double.parseDouble(lastLine[pos++] ) ;
		nodeCount = val ;
		return val ;
	}
	
	/**
	 * get last line
	 * @return list of string 
	 * @throws IOException
	 */
	private String[] getLastLine ( ) throws IOException  {
		String[] lastLine = null ;
		String line = "" ;
		int posLine = 0 ;
		while(line!=null) {      
            line = br.readLine();  
            try {
            	lastLine = line.split(";");
            	posLine++ ;	
            } catch (NullPointerException e) {    //	e.printStackTrace();
            	br.close(); 	            //	return lastLine ;
            }     
		}
		return lastLine ;
	}

}
