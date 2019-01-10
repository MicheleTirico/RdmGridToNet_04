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

public class test_02 extends data_matrix {

    public static void main(String[] args) throws IOException  {
    	File file = files[0] ;
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
		
		Map<double[] , ArrayList<Double>> mapFKdata = new HashMap<double[] , ArrayList<Double>>() ;
	
		double[] fk = new double[] {f,k} ;
		
		
		
		int posLine = 0 ;
		String lineIndicator = br.readLine() ,
				line = br.readLine() ;
		
		while(line!=null) {
			
    	       
            line = br.readLine();
            
            try {
            	String[] lineStr = line.split(";");
            	System.out.println(posLine + " " + lineStr[0]);
            	
            	posLine++ ;
      
            }catch (NullPointerException e) {
    			// TODO: handle exception
    		}
            
        } 
        br.close();
	}
    

    

    
}

