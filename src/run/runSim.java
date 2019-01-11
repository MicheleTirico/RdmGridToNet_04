package run;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.graphstream.graph.Graph;

import dataAnalysis.analyzeNetwork;
import dataAnalysis.indicatorSet.indicator;
import layerViz.vizLayerCell;
import layers.*;
import netViz.handleVizStype;
import netViz.handleVizStype.stylesheet;
import run.framework.RdmType;
import run.framework.typeComp;
import run.framework.typeInit;
import run.framework.typeRadius;
import run.framework.typeVectorField;

public class runSim implements parameters {
	
	public static void main ( String[] args ) throws FileNotFoundException, UnsupportedEncodingException {
		
		double [] fk = framework.getRdType(RdmType.waves ) ;
		double f = fk[0], 		k = fk[1];	//	System.out.println(f + " " + k);
//		f = 0.005 ; k = 0.005 ;
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(3);
		String nameFile = "f-"+ nf.format(f)+"_k-"+ nf.format(k)+"_";
			
		System.out.println("start " + nameFile + " " + new Date().toString() );
		
		// bucket set
		bucketSet bks = new bucketSet(1, 1, sizeGridX, sizeGridY ); 
		bks.initializeBukets(); 

		// layer Rd
		layerCell lRd = new layerCell(1, 1, sizeGridX, sizeGridY ,2,5) ;
		lRd.initializeCostVal(new double[] {1,0});		
		
		lRd.setReachBord( true , deltaCech );
		lRd.setGsParameters(f , k , Da, Db, tyDif );
		
		// layer max local
		layerMaxLoc lMl = new layerMaxLoc(true,true, typeInit.test, typeComp.wholeGrid ,1) ;
		lMl.setLayers(lRd, bks);
		lMl.initializeLayer();
		
		// layer infinite paraboloid 
		layerCell lParab = new layerCell(1, 1, sizeGridX, sizeGridY ,3,3) ;
		lParab.initCells();
		lParab.setGridInValsLayer(lParab.getInfiniteParaboloid(0, .002000, .00200, new double[] {sizeGridX/2 ,sizeGridY/2 ,0} ), 0);
		
		// layer bumps
		layerCell lBumps = new layerCell(1, 1, sizeGridX, sizeGridY ,3,3) ;
		lBumps.initCells();		
		lBumps.setGridInValsLayer(lBumps.getBumbsFromPosition ( 1 , 1 , 2 ) , 0);		
		
		// vector field Rd
		vectorField vfRd = new vectorField(lRd, 1, 1 , sizeGridX, sizeGridY, typeVectorField.slopeDistanceRadius ) ;
		vfRd.setSlopeParameters( 1 , r, alfa, true , typeRadius.circle);
	
		vectorField vfBumps = new vectorField(lBumps,  1, 1 , sizeGridX, sizeGridY, typeVectorField.interpolation);
		vfBumps.setMinDirectionParameters(0);
		
		vectorField vfParab = new vectorField(lParab, 1, 1, sizeGridX , sizeGridY, typeVectorField.interpolation);
		vfParab.setInterpolationParameters( 0, 1, 2 );
		
		// layer Seed
		vectorField[] vfs = new vectorField[] { vfRd  , vfBumps } ; 
		layerSeed lSeed = new layerSeed( vfs, 
				new double[] { 1 , .0	
				} ) ;																																				
																						
		// layer net
		layerNet lNet = new layerNet("net") ;
		lNet.setLayers( bks, lSeed, lRd, lMl);
		lSeed.setLayers(lNet, bks, lRd);
	//	framework.initCircle(lSeed, lRd, new double[] {1,1} , numNodes,new int[] { sizeGridX/2 , sizeGridY/2 }, radiusNet);
	//	framework.initMultiRandomCircle(lSeed, lRd,  new double[] {perturVal0,perturVal1}, numNodes, radiusRd , radiusNet , numInit );
		framework.initRandomSeed(lSeed, lRd, lNet,  new double[] {1,1} , 200, 20);
		lNet.setLengthEdges("length" , true );
		
		Graph netGr = lNet.getGraph();
	
		// Initialize simplify network
		symplifyNetwork simNet = new symplifyNetwork(runSimNet, netGr);
		simNet.init( stepToAnalyze);
		Graph simNetGr = simNet.getGraph() ;
				
		netGr.display(false) ;
		vizLayerCell vizlRd = new vizLayerCell(lRd, 1);
	//	vizLayerCell vizBump = new vizLayerCell(lBumps, 0);
	//	vizLayerCell vizParab = new vizLayerCell(lParab, 0) ;
	//	vizParab.step();
	
		// setup viz netGraph
		handleVizStype netViz = new handleVizStype( netGr ,stylesheet.manual , "seed", 1) ;
		netViz.setupIdViz(false , netGr, 20 , "black");
		netViz.setupDefaultParam (netGr, "black", "black", 5 , 0.5 );
		netViz.setupVizBooleanAtr(true, netGr, "black", "red" , false , false ) ;
		netViz.setupFixScaleManual( true , netGr, sizeGridX , 0);
	

		analyzeNetwork analNet = null ,  
				analSimNet = null ;
		
		try {
			analNet = new analyzeNetwork(runAnalysisNet, false ,stepToAnalyze, netGr, path, "analysisNet", nameFile);
			indicator.normalDegreeDistribution.setFrequencyParameters(10, 0, 10);
			indicator.degreeDistribution.setFrequencyParameters(10, 0, 10); 
			analNet.setLayer(lSeed);
			Map mapNet = new TreeMap<>();
			
			mapNet.put("sizeGrid",  sizeGridX);
			mapNet.put("Da", Da);
			mapNet.put("Db", Db);				
			mapNet.put("f", f);
			mapNet.put("k", k);
			mapNet.put("numStartSeed",  numNodes);
			mapNet.put("stepStore" , stepToStore) ;
			analNet.setupHeader(false, mapNet);
			
			analNet.setIndicators(Arrays.asList(
					indicator.seedCount ,
					indicator.degreeDistribution,
					indicator.totalEdgeLength,
					indicator.edgeCount ,
					indicator.totalEdgeLengthMST
					));
			analNet.initAnalysis();
			
			// initialize analysis simplify network
			analSimNet = new analyzeNetwork(runAnalysisSimNet, false ,stepToAnalyze, simNetGr, path, "analysisSimNet", nameFile);		
			indicator.normalDegreeDistribution.setFrequencyParameters(10, 0, 10);
			indicator.degreeDistribution.setFrequencyParameters(10, 0, 10); 
			indicator.pathLengthDistribution.setFrequencyParameters(100, 0, 10 );
			Map mapSimNet = new TreeMap<>();
			
			mapSimNet.put("sizeGrid",  sizeGridX);
			mapSimNet.put("Da", Da);
			mapSimNet.put("Db", Db);				
			mapSimNet.put("f", f);
			mapSimNet.put("k", k);
			mapSimNet.put("numStartSeed",  numNodes);
			mapSimNet.put("stepStore" , stepToStore) ;
		
			analSimNet.setupHeader(false, mapSimNet);
			
			analSimNet.setIndicators(Arrays.asList(
					indicator.degreeDistribution ,
					indicator.pathLengthDistribution ,			
					indicator.edgeCount
					));
			analSimNet.initAnalysis();

		}
		catch (IOException e) {
		}
		
		
		int t = 0 ; 	//		System.out.print("steps : " );
		// lNet.seedHasReachLimit = false ;	
				
		while ( t <= stepMax // 	
			//	&& ! lSeed.getListSeeds().isEmpty() 
				&& lNet.seedHasReachLimit == false 
				&& lRd.getHasReachBord() == false
				) {	//	
			if ( t / (double) stepToPrint - (int)(t / (double) stepToPrint ) < 0.0001) {
				System.out.println( nameFile +" " + "step: " + t + " seeds " + lSeed.getNumSeeds());
				
			}
			
			// update layers
			lRd.updateLayer();
			lMl.updateLayer();
			lNet.updateLayers( 0 ,true,1);	
			vizlRd.step();
			

			// analysis network
			try {
				analNet.compute(t);
				simNet.compute(t);
				analSimNet.compute(t);
			} catch (Exception e) {
			//	e.printStackTrace();
			}	
			
			t++ ;
		}
		// close files	
		try {
			analNet.closeFileWriter();
			analSimNet.closeFileWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("finish " + nameFile + " " + new Date().toString()  + " step " + t + " seed " + lSeed.getListSeeds().size() + " node " + lNet.getGraph().getNodeCount()+ "\n");		
	}
}
