package baseline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import structure.Node;
import structure.Problem;
import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.sl.core.AbstractInferenceSolver;
import edu.illinois.cs.cogcomp.sl.core.IInstance;
import edu.illinois.cs.cogcomp.sl.core.IStructure;
import edu.illinois.cs.cogcomp.sl.core.SLProblem;
import edu.illinois.cs.cogcomp.sl.util.WeightVector;

public class OrderInfSolver extends AbstractInferenceSolver implements
Serializable {

	private static final long serialVersionUID = 5253748728743334706L;
	private FeatureManager featGen;
	
	public OrderInfSolver(FeatureManager featGen) throws Exception {
		this.featGen = featGen;
	}

	@Override
	public IStructure getBestStructure(WeightVector weight, IInstance ins)
			throws Exception {
		return getLossAugmentedBestStructure(weight, ins, null);
	}

	@Override
	public IStructure getLossAugmentedBestStructure(WeightVector weight,
			IInstance ins, IStructure goldStructure) throws Exception {
		ProbX prob = (ProbX) ins;
		List<Integer> relevant = new ArrayList<>();
		for(Node node : prob.prob.expr.getLeaves()) {
			relevant.add(node.quantIndex);
		}
		if(relevant.size() != 2) {
			System.out.println("Something wrong"+prob);
		}
		OrderY y = new OrderY(new IntPair(
				relevant.get(0), relevant.get(1)));
		double bestScore = weight.dotProduct(featGen.getFeatureVector(ins, y));
		OrderY bestY = y;
		y = new OrderY(new IntPair(
				relevant.get(1), relevant.get(0)));
		if(bestScore < weight.dotProduct(featGen.getFeatureVector(ins, y))) {
			bestScore = weight.dotProduct(featGen.getFeatureVector(ins, y));
			bestY = y;
		}
		return bestY;
	}

	@Override
	public float getLoss(IInstance ins, IStructure gold, IStructure pred) {
		if(gold.toString().equals(pred.toString())) {
			return 0.0f;
		}
		return 1.0f;
	}
	
	public static SLProblem getSP(List<Problem> problemList) throws Exception{
		SLProblem problem = new SLProblem();
		for(Problem prob:problemList) {
			List<Integer> relevant = new ArrayList<>();
			for(Node node : prob.expr.getLeaves()) {
				relevant.add(node.quantIndex);
			}
			if(relevant.size() != 2) {
				System.out.println("Something wrong");
			}
			ProbX x = new ProbX(prob, null);
			OrderY y = new OrderY(new IntPair(
					relevant.get(0), relevant.get(1)));
			problem.addExample(x, y);
		}
		return problem;
	}
}
