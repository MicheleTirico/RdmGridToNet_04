package dataMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import dataAnalysis.computeValue.whichVal;
import dataAnalysis.exportCsv;
import dataAnalysis.valueSet;
import dataAnalysis.value ; 
public class exportMatrix {
	
	private computeMatrix cm ;
	private whichVal[] wv ;
	private String[] header , valStr  ;
	private String path , nameFile ;
	private valueSet vs ;
	private exportCsv ec ;
	private ArrayList<exportCsv> ecStep = new ArrayList<exportCsv> () ;
	
	private  ArrayList<String> valList = new  ArrayList<String> () ;
	
 	public exportMatrix ( computeMatrix cm , String path , String nameFile) throws IOException {
		this.cm = cm ;
		this.path = path ;
		this.nameFile = nameFile ; 
		vs = cm.getValueSet();
		ec = new exportCsv(path, null, nameFile);
		ec.createFile();
 	}
 	
 	public exportMatrix ( computeMatrix cm , String path ) throws IOException {
		this.cm = cm ;
		this.path = path ;
		this.nameFile = nameFile ; 
		vs = cm.getValueSet();
 	}
	
	
	public void setValuesToExport ( ArrayList<String> valList ) {
		this.valList = valList ;
	}
	
	public void exportValStep (String initNameFile , int stepMax) throws IOException {
		for ( String ind : valList) {
			ec = new exportCsv(path, null, initNameFile + ind );
			ec.createFile();
			setHeaderStep(ec , stepMax ) ;
			int numVals = (int) (stepMax / cm.getStepRange() ) +  2  ;
			String[] line = new String [numVals] ;
			for ( value v : vs.getListValues() ) {
				Map <String , ArrayList<Double>> map = v.getMapIdVals();	//	System.out.println(map);
				double [] ref = v.getRef();
				line[0] = Double.toString(ref[0]) ;
				line[1] = Double.toString(ref[1]) ;	
				ArrayList<Double> vals = v.getListValues(ind);
				int pos = 0 ;
				while ( pos < numVals  - 2 ) {
					String val ;
					try {
						val = Double.toString(vals.get(pos)) ;
					} catch (IndexOutOfBoundsException | NullPointerException e) {
						val = "0.0";
					}
					line[ pos + 2 ] = val ;
					pos ++ ;
				}
				ec.writeLine(line, ';' ) ;
			}
			ecStep.add(ec) ;
		}
	}
	
	public void exportVal ( ) throws IOException {
		setHeader();
		String[] line = new String [valList.size() + 2 ] ;
		for ( value v : vs.getListValues() ) {
			Map <String , ArrayList<Double>> map = v.getMapIdVals();	//	System.out.println(map);
			double [] ref = v.getRef();
			line[0] = Double.toString(ref[0]) ;
			line[1] = Double.toString(ref[1]) ;
			int pos = 2 ;
			for ( String valName : valList ) {
				ArrayList<Double> arr = map.get(valName) ; 				//	System.out.println(valName);
				double val ;
				try {
					val = arr.get(0);
				} catch (NullPointerException e) {
					val = 0 ;
				}
				line[pos] = Double.toString(val);	
				pos++ ;
			}
			ec.writeLine(line, ';' ) ;
		}
	}

	public void close () throws IOException {
		ec.close();
		closeStep();
	}
	
	private void closeStep ( ) throws IOException {
		ecStep.stream().forEach(ec -> {
			try {
				ec.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} );
	}

	private void setHeader () throws IOException {
		header = new String[ valList.size() + 2] ;
		header[0] = "f" ;
		header[1] = "k" ;
		int pos = 2 ;
		for ( String val : valList ) {
			header[pos] = val ;
			pos++ ;
		}
		ec.writeLine(header, ';' ) ;
	}
	
	private void setHeaderStep ( exportCsv ec  , int stepMax) throws IOException {
		double rangeStep = cm.getStepRange();
		int numCell =  (int) (stepMax / rangeStep)    ;
		header = new String[ numCell + 2 ] ;
		header[0] = "f" ;
		header[1] = "k" ; 
		int pos = 1 ;
		while ( pos <= numCell ) {
			header[pos + 1 ] = Double.toString(pos * rangeStep ) ;
			pos++ ;
		}
		ec.writeLine(header, ';');
	}
	
	
	

}
