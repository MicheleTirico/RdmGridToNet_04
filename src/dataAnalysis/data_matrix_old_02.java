package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class data_matrix_old_02 {

	private static String folder ;
	protected static String path = "D:\\java_data_analysis\\data_test";
	protected static double Da ;
	protected static double Db, f, k,  numStartSeed, sizeGrid , stepStore;
 
	
	Map<double[] , ArrayList<Double>> mapFKdata = new HashMap<double[] , ArrayList<Double>>() ;

	static ArrayList<Double> listF = new  ArrayList<Double> () ;
	static ArrayList<Double> listK = new  ArrayList<Double> ();
	
	static ArrayList<double[]> listFK = new  ArrayList<double[]> () ;
	
	static File[] files = new File(path).listFiles();
	
    public static void main(String[] args) throws IOException  {
    	
    	for ( File file : files ) {
    		// System.out.println(file);
    		FileReader fr = new FileReader(file);
    		BufferedReader br = new BufferedReader(fr);
    		
    		String lineIntedex = br.readLine();
    		String lineParams = br.readLine() ;
    		String[] startParams = lineParams.split(";") ;
    		
    		// get params 
    		Da = Double.parseDouble(startParams[0]);
    		Db = Double.parseDouble(startParams[1]) ;
    		f = Double.parseDouble(startParams[2]);
    		k = Double.parseDouble(startParams[3]) ;
    		numStartSeed= Double.parseDouble(startParams[4]) ;
    		sizeGrid= Double.parseDouble(startParams[5]) ;
    		stepStore= Double.parseDouble(startParams[6]) ;
    	
    	

    		int posLine = 0 ;
    		String lineIndicator = br.readLine() ,
    				line = br.readLine() ;

    		boolean isFinList = false , 
    				isKinList = false ,
    				isFKinList = false ;
    		
    		if (listF.contains(f) ) 
    			isFinList = true;
    			
    		if ( listK.contains(k))
    			isFinList = true ;
    		
    		if ( isFinList && isKinList ) {
    			isFKinList = true ;
    			double[] fk = new double[] {f,k} ;
    			
    		}
    		
    		double[] fk = new double[] {f,k} ;
    		
    		
    		while(line!=null) {
    			
        	       
                line = br.readLine();
                
                try {
                	String[] lineStr = line.split(";");
             //   	System.out.println(posLine + " " + lineStr[0]);
                	
                	posLine++ ;
          
                }catch (NullPointerException e) {
        			// TODO: handle exception
        		}
                
            } 
            br.close();
    	}
        
	

        System.out.println("size listK " + listK.size());
        System.out.println("size listF " + listF.size());
    }
}
