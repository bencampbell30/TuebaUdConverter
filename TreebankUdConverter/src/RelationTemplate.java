import java.util.ArrayList;

/*
 * used as a template to represent a certain node/relation, which can be used to check if such exists in the tree
 */
public class RelationTemplate 
{
	private ArrayList<String> function;
	private ArrayList<String> nodeName;
	private ArrayList<String> pos;
	//needs variables are used to indicate which of function, NodeName, and pos are needed to be equal to the compared relation in order to have a match
	private boolean needsFunction;
	private boolean needsNodeName;
	private boolean needsPos;
	
	public RelationTemplate(ArrayList<String> fun, ArrayList<String> name, ArrayList<String> p, boolean needsFunction, boolean needsName, boolean needsPos) 
	{
		function = fun;
		nodeName = name;
		pos = p;
		this.needsFunction = needsFunction;
		this.needsNodeName = needsName;
		this.needsPos = needsPos;
	}

	public ArrayList<String> getFunction()
	{
		return function;
	}
	
	public ArrayList<String> getNodeName()
	{
		return nodeName;
	}
	
	public ArrayList<String> getPos()
	{
		return pos;
	}
	
	public boolean needsFunction()
	{
		return needsFunction;
	}
	
	public boolean needsNodeName()
	{
		return needsNodeName;
	}
	
	public boolean needsPos()
	{
		return needsPos;
	}
}