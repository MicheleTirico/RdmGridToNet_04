package dataAnalysis;

import java.io.File;

import dataAnalysis.indicatorSet.indicator;

public class handleReComputeSim {
	private analyzeNetwork aN ;
	String folder , nameFile , path ; 
	File f ;
	public handleReComputeSim (  String path , analyzeNetwork aN ) {
		this.path = path  ;
		this.aN = aN ; 
	}
	
	public handleReComputeSim (  String path ) {
		this.path = path  ;
	}
	
	public static boolean isAllIndicatorInDirectory (String nameFile , String path ) {
		indicator[] list = indicatorSet.getAllIndicators ();
		boolean test = false ;
		int pos = 0 ;
		while ( test == false && pos < list.length ) {
			String pathFile = path + nameFile+ list[pos] + ".csv" ;			
			File f = new File(pathFile); 
			if ( f.exists() )  
				test = true ;
			pos ++ ;
		}
		return test ;
	}
	
	public static boolean isOneOfIndicatorNotComputed ( String nameFile , String path ) {	
		System.out.println(getNumFile(nameFile , path));
		System.out.println(indicatorSet.getAllIndicators ().length );
		if ( getNumFile(nameFile , path) == indicatorSet.getAllIndicators ().length )
			return true ;
		else
			return false;
	}
	
	public static int getNumFile ( String nameFile , String path ) {
		int val = 0 ;
		indicator[] list = indicatorSet.getAllIndicators ();
		int pos = 0 ;
		while ( pos < list.length ) {
			String pathFile = path + nameFile+ list[pos] + ".csv" ;			
			File f = new File(pathFile); 
			if ( f.exists() )  
				val++ ;
			pos ++ ;
		}
		return val ;
	}
	
	public boolean isFileInDirectory ( String nameFile ) {
		f = new File(path + "/" + nameFile);
		if ( f.exists() == true ) 
			return true ;
		else 
			return false ;
	}
}
