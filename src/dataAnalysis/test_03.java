package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class test_03 extends data_matrix {

	private static String folder ;
	protected static String path = "D:\\java_data_analysis\\data_test";
	protected static double Da , Db, f, k,  numStartSeed, sizeGrid , stepStore;
 
	
	Map<double[] , ArrayList<Double>> mapFKdata = new HashMap<double[] , ArrayList<Double>>() ;

	static ArrayList<Double> listF = new  ArrayList<Double> () ;
	static ArrayList<Double> listK = new  ArrayList<Double> ();
	static ArrayList<Integer> listId = new  ArrayList<Integer> ();
	static ArrayList<double[]> listFK = new  ArrayList<double[]> () ;
	
	static File[] files = new File(path).listFiles();
	static ArrayList< matrixValues> valuesStepMax = new ArrayList<matrixValues>();
	
    public static void main(String[] args) throws IOException  {
    	
    	for ( File file : files ) {
    		// System.out.println(file);
    		FileReader fr = new FileReader(file);
    		BufferedReader br = new BufferedReader(fr);
    		
    		String lineIndex = br.readLine();
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
    		

    		
    		if ( ! listF.contains(f) || ! listK.contains(k)) {
    			double[] fk = new double[] {f,k} ;
    			
    			listF.add(f);
    			listK.add(k) ;
    			listFK.add(fk) ;
    	
    		
    		}
    	}
    		
    	for ( double[] fk : listFK ) 
    		System.out.println(fk[0] + " "+ fk[1]);
    	
        System.out.println("size listF " + listF.size() + listF);
        System.out.println("size listK " + listK.size() + listK);
        System.out.println("size listFK " + listFK.size() +  listFK );
        System.out.println("size listid " + listId.size() + listId );
    }

    

    
}

