
public class RelationTriplet 
{
	private String function;
	private String nodeName;
	private String pos;
	//Add also variables for new
	
	public RelationTriplet(String fun, String name, String p) 
	{
		function = fun;
		nodeName = name;
		pos = p;
	}

	public String getFunction()
	{
		return function;
	}
	
	public String getNodeName()
	{
		return nodeName;
	}
	
	public String getPos()
	{
		return pos;
	}
}
