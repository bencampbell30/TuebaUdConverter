import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6915228480907274901L;
	private ArrayList<TreeNode> subNodes;
	private ArrayList<TreeWord> words;
	private String line = null;
	private HashMap<String, String> nodeData = new HashMap<String, String>();
	private String dependency = null;
	private String category = null;
	
	public TreeNode(String wWord) 
	{
		line = wWord;
		subNodes = new ArrayList<TreeNode>();
		words = new ArrayList<TreeWord>();
		extractNodeData(line);
			
	}

	public void addSubNode(TreeNode node)
	{
		subNodes.add(node);
	}
	
	public void addWord(TreeWord word)
	{
		words.add(word);
	}
	
	public void setSubNodes(ArrayList<TreeNode> nodes)
	{
		subNodes = nodes;
	}
	
	public ArrayList<TreeNode> getSubNodes()
	{
		return subNodes;
	}
	
	public ArrayList<TreeWord> getWords()
	{
		return words;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public HashMap<String, String> getNodeData()
	{
		return nodeData;
	}
	
	public void setDependency(String dep)
	{
		dependency = dep;
	}
	
	public String getDependency()
	{
		return dependency;
	}
	
	public void setCategory(String cat)
	{
		category = cat;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	private void extractNodeData(String line)
	{
		line = line.replace("<", "");
		line = line.replace("/>", "");
		line = line.replace(">", "");
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken(); //get rid of node/sentence marker
	    while (st.hasMoreTokens()) 
	    {
	    	String current = st.nextToken();
	    	String[] keyValue = current.split("=");
	    	keyValue[1] = keyValue[1].replace("\"", "");
			nodeData.put(keyValue[0], keyValue[1]);
	    }
	    dependency = nodeData.get("func");
	    category = nodeData.get("cat");
	}
}
