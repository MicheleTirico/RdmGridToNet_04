package dataAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test_createCsv {
	
	public static void main ( String[] args ) throws IOException {
		String path = "C:\\Users\\frenz\\data\\data\\test07" ,
				folder = "" ,
				nameFile = "test" ;
		exportCsv ec  = new exportCsv(path, folder, nameFile);
		
		ec.createFile();
		ec.writeLine(new String[] {"peppe" , "gino"} , ';' ) ;

		ec.close () ;
	}

}
