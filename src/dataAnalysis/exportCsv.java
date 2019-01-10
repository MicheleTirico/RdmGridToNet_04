package dataAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class exportCsv {
	
	private String path , nameFolder = null , nameFile ;
	private  Writer w ;
	private static final char DEFAULT_SEPARATOR = ',';
	private  FileWriter fw ; 
	
	public exportCsv ( String path , String nameFolder , String nameFile ) {
		this.path = path ;
	//	this.nameFolder = nameFolder ;
		File dir = new File (path);
		dir.mkdir();
		this.nameFile = nameFile ;
	}

// CREATE FILE --------------------------------------------------------------------------------------------------------------------------------------	
	public void createFile ( ) throws IOException {
		String completePath = path + "/" + nameFile + ".csv"; 
		fw = new FileWriter(completePath);
	}
	
// APPEND LINE --------------------------------------------------------------------------------------------------------------------------------------
    public void writeLine( List<String> values, char separators) throws IOException {
        writeLine(values, separators, ' ');
    } 
    
    public void writeLine( String[] values, char separators) throws IOException {
    	List<String> list = Arrays.asList(values);
        writeLine( list, separators, ' ');
    }
  
    public void close ( ) throws IOException {
    	fw.close();
    }
    
// INTERNAL METHODS ---------------------------------------------------------------------------------------------------------------------------------	
   private void writeLine (List<String> values, char separators , char customQuote ) throws IOException {
        boolean first = true ;
        if (separators == ' ') 
            separators = DEFAULT_SEPARATOR;      
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
        	if (!first) 
                sb.append(separators);
            if (customQuote == ' ') 
                sb.append(followCVSformat(value));
            else 
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            first = false;
        }
        sb.append("\n");
        fw.append(sb.toString());   
    } 
   
   private static String followCVSformat(String value) {
       String result = value;
       if (result.contains("\""))  
           result = result.replace("\"", "\"\"");
       return result;
   } 

}
