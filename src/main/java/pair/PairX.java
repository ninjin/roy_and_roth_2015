package pair;

import java.util.List;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.quant.driver.QuantSpan;
import edu.illinois.cs.cogcomp.sl.core.IInstance;
import structure.Problem;
import structure.Schema;

public class PairX implements IInstance {
	
	public int problemId;
	public int quantIndex1;
	public int quantIndex2;
	public TextAnnotation ta;
	public List<QuantSpan> quantities;
	public List<Constituent> posTags;
	public List<Constituent> chunks;
	public List<Constituent> parse;
	public List<Constituent> dependency;
	public List<String> lemmas;
	public Schema schema;
	
	public PairX(Problem prob, int quantIndex1, int quantIndex2) {
		this.problemId = prob.id;
		this.quantIndex1 = quantIndex1;
		this.quantIndex2 = quantIndex2;
		this.ta = prob.ta;
		this.quantities = prob.quantities;
		this.posTags = prob.posTags;
		this.chunks = prob.chunks;
		this.schema = new Schema(prob);
		this.lemmas = prob.lemmas;
	}

}
