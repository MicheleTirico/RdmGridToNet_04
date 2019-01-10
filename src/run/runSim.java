package run;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Date;
import org.graphstream.graph.Graph;
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
		
		double [] fk = framework.getRdType(RdmType.chaos ) ;
		double f = fk[0], 		k = fk[1];	//	System.out.println(f + " " + k);
//		f = 0.03 ; k = 0.065 ;
		
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
		vectorField vfRd = new vectorField(lRd, 1, 1 , sizeGridX, sizeGridY, typeVectorField.gravity ) ;
		vfRd.setSlopeParameters( 1 , r, alfa, true , typeRadius.circle);
	
		vectorField vfBumps = new vectorField(lBumps,  1, 1 , sizeGridX, sizeGridY, typeVectorField.minVal);
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
		framework.initCircle(lSeed, lRd, new double[] {1,1} , numNodes,new int[] { sizeGridX/2 , sizeGridY/2 }, radiusNet);
	//	framework.initMultiRandomCircle(lSeed, lRd,  new double[] {perturVal0,perturVal1}, numNodes, radiusRd , radiusNet , numInit );
	//	framework.initRandomSeed(lSeed, lRd, lNet,  new double[] {1,1} , 200, 20);
		lNet.setLengthEdges("length" , true );
		
		Graph netGr = lNet.getGraph();
	 
		
		netGr.display(false) ;
		vizLayerCell vizlRd = new vizLayerCell(lRd, 1);
	//	vizLayerCell vizBump = new vizLayerCell(lBumps, 0);
		vizLayerCell vizParab = new vizLayerCell(lParab, 0) ;
		vizParab.step();
	
		// setup viz netGraph
		handleVizStype netViz = new handleVizStype( netGr ,stylesheet.manual , "seed", 1) ;
		netViz.setupIdViz(false , netGr, 20 , "black");
		netViz.setupDefaultParam (netGr, "black", "black", 5 , 0.5 );
		netViz.setupVizBooleanAtr(true, netGr, "black", "red" , false , false ) ;
		netViz.setupFixScaleManual( true , netGr, sizeGridX , 0);
	
		
		int t = 0 ; 	//		System.out.print("steps : " );
		// lNet.seedHasReachLimit = false ;	
				
		while ( t <= stepMax // 	&& ! lSeed.getListSeeds().isEmpty() 
		//		&& lNet.seedHasReachLimit == false 
		//		&& lRd.getHasReachBord() == false
				) {	//	
			if ( t / (double) stepToPrint - (int)(t / (double) stepToPrint ) < 0.0001) {
				System.out.println( nameFile +" " + "step: " + t + " seeds " + lSeed.getNumSeeds());
				
			}
			
			// update layers
			lRd.updateLayer();
			lMl.updateLayer();
			lNet.updateLayers( 0 ,true,1);	
			vizlRd.step();
			t++ ;
		}
		System.out.println("finish " + nameFile + " " + new Date().toString()  + " step " + t + " seed " + lSeed.getListSeeds().size() + " node " + lNet.getGraph().getNodeCount()+ "\n");		
	}
}
