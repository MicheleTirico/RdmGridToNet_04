package dataAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test extends data_matrix{

	
	static File[] f = new File ( path ).listFiles() ;
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(f[0]);
	    scanner.useDelimiter(",");
	    int line = 0 ;
	    while(scanner.hasNextLine()  ){
	    	
	    	String l = scanner.nextLine() ;
	    	System.out.println(line + " " + l) ;
	    	line++ ;
	    	
	    //    System.out.println(line);
	        
	    }
	    scanner.close();
	}
	
}
