package dataMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import dataAnalysis.valueSet;
import dataAnalysis.value;;

public class run {
	
	static String pathNet = "C:\\Users\\frenz\\data\\data\\data_circle\\analysisNet" ,//C:\\Users\\frenz\\data\\data\\test06\\analysisNet" ,
			pathNetSim = "C:\\Users\\frenz\\data\\data\\data_circle\\analysisSimNet" ,
			pathToStore = "C:\\Users\\frenz\\data\\data\\store_circle" ;

	private static computeMatrix  cm = new computeMatrix() , 
			cmStep = new computeMatrix();
	
	public static void main ( String[] args ) throws IOException {
		
		// compute step max 
		runStepMax(true);
		
		// compute matrix step 
		runStep(false);
	}
	
	private static void runStepMax (boolean run ) throws IOException {
		if (run) {
			System.out.println("start stepMax");
			cm.initMatrix(0.005, 0.095, 0.001);
			cm.computeNet(pathNet) ;
			cm.computeNetSim(pathNetSim);
			
			exportMatrix em = new exportMatrix(cm, pathToStore, "circle_stepMax") ;
			ArrayList<String> listInd = cm.getListNameIndicators() ; //  new ArrayList<String >( Arrays.asList("SM_stepMax" ,"SM_nodeCountNo2d" ) ) ;
			em.setValuesToExport( listInd ) ;
			
			em.exportVal();
			em.close();
			System.out.println("finish stepMax");
		}	
		
	}
	
	private static void runStep (boolean run ) throws IOException {
		if (run) {
			cmStep.setStepRange(100);
			System.out.println("start step " + cmStep.getStepRange());
			cmStep.initMatrix(0.005, 0.095, 0.001);
		
			System.out.println("start net step " );
			cmStep.computeNetStep(pathNet);
			System.out.println("finish net step " );
			
			System.out.println("start net sim step " );
			cmStep.computeNetSimStep(pathNetSim);
			System.out.println("finish net sim step " );
			
			exportMatrix emStep = new exportMatrix(cmStep, pathToStore);
			ArrayList<String> listIndStepAll = cmStep.getListNameIndicators() ; 
			ArrayList<String> listIndStep = new  ArrayList<String> (Arrays.asList(
					"Step100_totalEdgeLength", "SM_stepMax", "Step100_edgeCount", "Step100_averageDegree", 
					"Step100_nodeCountNo2d", "Step100_totalEdgeLengthMST", "Step100_nodeCount",  "Step100_seedCount", "Step100_efficacity") ) ;
			emStep.setValuesToExport(listIndStep);
			
			emStep.exportValStep("" , 5000);
			emStep.close();
			System.out.println("finish step " + cmStep.getStepRange());	
		}
	}
			

}
