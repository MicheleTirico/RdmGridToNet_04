package run;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.graphstream.graph.Graph;

import dataAnalysis.analyzeNetwork;
import dataAnalysis.handleReComputeSim;
import dataAnalysis.storeInfo;
import dataAnalysis.indicatorSet.indicator;

import layers.*;
import run.framework.handleLimitBehaviur;
import run.framework.typeComp;
import run.framework.typeDiffusion;
import run.framework.typeInit;
import run.framework.typeRadius;
import run.framework.typeVectorField;

public class runThread_randomSeed extends Thread implements parameters {

	private int th ; 
	public runThread_randomSeed(int th) {
		this.th = th; 
	}

	private double [] arrayF = framework.getArrayVals(minFeed, maxFeed, incremFeed);
	private double [] arrayK = framework.getArrayVals(minKill , maxKill , incremKill );
	private ArrayList<double[]> params = framework.getListParams(false , arrayF, arrayK);
	private ArrayList<double[]> paramsVisited = new ArrayList<double[]>();
	static int pos = 0 ;
	public void run ( ) { 
		
		while ( paramsVisited.size() < params.size() ) {
			try { 
				double[] fk = params.get(pos);
	
				paramsVisited.add(fk);
				pos++;
				double f = fk[0], 
						k = fk[1];	//	System.out.println(f + " " + k);
				
				NumberFormat nf = NumberFormat.getNumberInstance();
				nf.setMaximumFractionDigits(3);
				String nameFile = "f-"+ nf.format(f)+"_k-"+ nf.format(k)+"_";
				
				int numIndComputed = handleReComputeSim.getNumFile ( nameFile , path + "analysisNet/") ;
		
				if ( numIndComputed == 5 ) {
					System.out.println(nameFile + " already computed ");
					continue ;	
				}
				
			//	System.out.println("start " + nameFile + " " + new Date().toString() );
				
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
				
				// vector field Rd
				vectorField vfRd = new vectorField(lRd, 1, 1 , sizeGridX, sizeGridY, typeVectorField.slopeDistanceRadius) ;
				vfRd.setSlopeParameters( 1 , r, alfa, true , typeRadius.circle);
			
				// layer Seed
				vectorField[] vfs = new vectorField[] { vfRd   } ; 
				layerSeed lSeed = new layerSeed( vfs, 
						new double[] { 1	
						} ) ;																																				
																								
				// layer net
				layerNet lNet = new layerNet("net") ;
				lNet.setLayers( bks, lSeed, lRd, lMl);
				lSeed.setLayers(lNet, bks, lRd);
			//	framework.initCircle(lSeed, lRd, new double[] {1,1} , numNodes,new int[] { sizeGridX/2 , sizeGridY/2 }, radiusNet);
				framework.initMultiRandomCircle(lSeed, lRd,  new double[] {perturVal0,perturVal1}, numNodes, radiusRd , radiusNet , numInit );
			//	framework.initRandomSeed(lSeed, lRd, lNet,  new double[] {1,1} , 200, 20);
				lNet.setLengthEdges("length" , true );
				
				Graph netGr = lNet.getGraph();
				
				// Initialize simplify network
				symplifyNetwork simNet = new symplifyNetwork(runSimNet, netGr);
				simNet.init( stepToAnalyze);
				Graph simNetGr = simNet.getGraph() ;
	
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
					// TODO: handle exception
				}
							
				int t = 0 ; 	//		System.out.print("steps : " );
				lNet.seedHasReachLimit = false ;	
		
				while ( t <= stepMax // 	&& ! lSeed.getListSeeds().isEmpty() 
						&& lNet.seedHasReachLimit == false 
						&& lRd.getHasReachBord() == false
						) {	// 	if ( t / (double) stepToPrint - (int)(t / (double) stepToPrint ) < 0.0001) System.out.println( nameFile +" " + "step: " + t);
				
					// update layers
					lRd.updateLayer();
					lMl.updateLayer();
					lNet.updateLayers( 0 ,true,1);
				
					
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
				System.out.println("finish " + nameFile + " " + new Date().toString()  + " step " + t + " seed " + lSeed.getListSeeds().size() + " node " + lNet.getGraph().getNodeCount());
			
				// close files	
				try {
					analNet.closeFileWriter();
					analSimNet.closeFileWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			}
			catch (Exception ex) {
				// TODO: handle exception
			}
		}
	}

	public static void main ( String[] args ) throws FileNotFoundException, UnsupportedEncodingException {
		
		storeInfo sI = new storeInfo(path, "infoSim") ;
		sI.addLine("// Simulation ---------------------------------------------");

		sI.addLines(new String[] {
				"//Simulation info ----------------" ,
				"number of thread : " + numThread ,
				"step max , store , analysis : " + stepMax + " " + stepToStore + " " + stepToAnalyze ,
				"increm f , k : " +  incremFeed + " " + incremKill ,
				"min and max feed : " + minFeed + " " + maxFeed   ,
				"min and max kill : " + minKill + " " + maxKill ,
				"number of simulation : " + numberOfSimulations ,
				" ",
				"// RD info --------------------" ,
				"size of grid x ,y : " +sizeGridX + " " + sizeGridY , 
				"val initialization : " + initVal0 + " "  + initVal1 ,
				"val perturbation : " + perturVal0 + " " + perturVal1 ,
				"diffusion Da , Db , type diffusion : " + Da + " " + Db +" "+tyDif ,
				"cech values of Rd when reach bord, delta to ceck: " + ceckReachBord +" , " +  deltaCech ,
				" ",
				"// vectorField info --------------" ,
				" " ,
				"// seed and network init ------",
				" number of initial nodes : " + numNodes ,
		});
		sI.createInfo();
		
		
		System.out.println("size of grid x ,y : " +sizeGridX + " " + sizeGridY ); 
		System.out.println("step max , store , analysis : " + stepMax + " " + stepToStore + " " + stepToAnalyze );
		System.out.println("increm f , k : " +  incremFeed + " " + incremKill);
		System.out.println("min and max feed : " + minFeed + " " + maxFeed ) ;
		System.out.println("min and max kill : " + minKill + " " + maxKill );
		System.out.println("//----------------------------------------------------" + "\n");
	
		int a = 0 ;
		while ( a < numThread ) {
			runThread_randomSeed m = new runThread_randomSeed(a++);
			m.start();	
			
		}
			
		
			
	}
	
	
	
	
}
