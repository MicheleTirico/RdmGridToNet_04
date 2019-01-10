package dataAnalysis;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class storeInfo {

	private ArrayList<String> listOfLine = new ArrayList<String>();
	private String path , nameFile ;
	
	public storeInfo ( String path , String nameFile ) {
		this.path = path  ;
		this.nameFile= nameFile  ;
	}
	
	public void addLine ( String line ) {
		listOfLine.add(line);
	}
	
	public void addLines ( String[] lines ) {
		listOfLine.addAll(Arrays.asList(lines));
	}
	public void createInfo () throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path + nameFile +".txt", "UTF-8");
		for ( String line : listOfLine )
			writer.println(line); 
		writer.close();
	}
	
}
