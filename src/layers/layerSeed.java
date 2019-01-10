package layers;

import java.util.ArrayList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import run.framework;

public class layerSeed extends framework {
	
	ArrayList <seed> seeds = new ArrayList<seed> ();
	private layerNet lNet ;
	private layerCell lRd ;
	private bucketSet bks ;
	private vectorField[] vectorFields ;
	private double[] vectorIncrem ;
	
	protected int idNodeInt , idEdgeInt ; 
	protected String idNode, idEdge  ;
	
	public layerSeed () {
		this( null ,null  ) ;
	}
			
	public void setLayers ( layerNet lNet , bucketSet bks , layerCell lRd ) {
		this.lNet = lNet ;
		this.bks = bks ;
		this.lRd = lRd; 
	}
	public layerSeed (  vectorField[] vectorFields , double[] vectorIncrem  ) {
		this.vectorFields = vectorFields ;
		this.vectorIncrem = vectorIncrem ;
	}
	
	public void initSeedCircle ( int numNodes , double radius , double centerX , double centerY) {		
 		Graph graph = lNet.getGraph() ;
		double angle = 2 * Math.PI / numNodes ;		
		int nodeCount = graph.getNodeCount() ;
		
		Node old = null  ;
		ArrayList<Node> listNodes = new ArrayList<Node>();
		
		for ( idNodeInt = nodeCount  ; idNodeInt <nodeCount + numNodes ; idNodeInt++ ) {
			
			double 	coordX = radius * Math.cos( idNodeInt * angle ) ,
					coordY = radius * Math.sin( idNodeInt * angle ) ;
					
			idNode =  Integer.toString(idNodeInt) ;
			graph.addNode(idNode) ;
			Node n = graph.getNode(idNode);
			
			n.addAttribute("xyz", centerX + coordX ,  centerY + coordY , 0 );
			listNodes.add(n);
			this.createSeed(centerX + coordX, centerY + coordY , n);
	
			try {
				idEdge = Integer.toString(idEdgeInt+1 );
				Edge e = graph.addEdge(idEdge, n, old);
				e.addAttribute("length", getDistGeom(n,old));
				idEdgeInt++;
			} catch (NullPointerException e)  {			//		e.printStackTrace();
			
			} 
			old = n ;
		}
		if ( numNodes > 2 ) {
			idEdge = Integer.toString(idEdgeInt+1 );
			Edge e = graph.addEdge(idEdge, old, listNodes.get(0));
			e.addAttribute("length", getDistGeom(listNodes.get(0),old));
			idEdgeInt++;
		}		
	//	System.out.println(bks.getListBucket().size());
		for ( Node n : graph.getEachNode()) {
	//		System.out.println(n.getId());
			bks.putNode(n);	
		}
		lNet.idNodeInt = idNodeInt ;
		lNet.idEdgeInt = idEdgeInt ; 
	}
	
// REMOVE SEED METHODS ------------------------------------------------------------------------------------------------------------------------------
	public void removeSeed ( seed s ) {
		seeds.remove(s) ;
	}
	
	public void removeSeed ( seed s , boolean removeSeedFromCell ) {
		seeds.remove(s) ;
		lRd.getCell(s).setIsTest(1, false);
	}
	
	public void removeSeed ( seed s , boolean removeSeedFromCell , layers.layerCell.typeNeighbourhood typeNeighbourhood , layerCell lCell ) {
		seeds.remove(s) ;
		cell c = lCell.getCell(s) ;
		c.setIsTest(1, false);
		for ( cell ce : lCell.getListNeighbors(typeNeighbourhood, c.getX(), c.getY()))
			ce.setIsTest(1, false);
	}

// CREATE SEED METHODS ------------------------------------------------------------------------------------------------------------------------------
	public void createSeed ( double X , double Y ) {
		seeds.add( new seed(X, Y, 0, 0 , null) ) ;
	}
	
	public void createSeed ( double X , double Y , Node n  ) {
		n.addAttribute("xyz", X , Y  , 0 );
		seeds.add( new seed(X, Y, 0, 0 , n) ) ;
	}

	public void createSeed ( double X , double Y , Node n , boolean setSeedInCell , layerCell lCell  ) {
		n.addAttribute("xyz", X , Y  , 0 );
		seed s = new seed(X, Y, 0, 0 , n )  ;
		seeds.add(s) ;
		setSeedInCell(s, setSeedInCell , lCell );
	}

	public void createSeed(seed s ) {
		seeds.add(s);
	}
	
	public void setSeedInCell ( seed s , boolean setSeedInCell , layerCell lCell )  {
		try {
			cell c = lCell.getCell(s);
			c.setIsTest(1, setSeedInCell);
		} catch (NullPointerException e) {
		}
	}
	
// GET METHODS --------------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<seed> getListSeeds () {
		return seeds;		
	}
	
	public int getNumSeeds () {
		return seeds.size();
	}
	
	public ArrayList<Node> getListNodeWithSeed ( ) {
		ArrayList<Node> list = new ArrayList<Node> ();
		for ( seed s : seeds) {
			Node n = s.getNode();
			if ( ! list.contains(n))
				list.add(s.getNode());
		}
		return list;
	}
		
	public vectorField[] getListVectorFields() {
		return vectorFields ;
	}
	
	public double[] getVectorIncrem  ( ) {
		return vectorIncrem ;
	}
	

	
}
	
