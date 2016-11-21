import java.util.ArrayList;

public class TransformationPair 
{	
	ArrayList<RelationTemplate> oldRelation;
	ArrayList<String> newDependencies;
	
	public TransformationPair(ArrayList<RelationTemplate> rel, ArrayList<String> dep) 
	{
		oldRelation = rel;
		newDependencies = dep;
	}

	public ArrayList<String> getNewDependencies()
	{
		return newDependencies;
	}

	public ArrayList<RelationTemplate> getOldRelation() 
	{
		return oldRelation;
	}
}
