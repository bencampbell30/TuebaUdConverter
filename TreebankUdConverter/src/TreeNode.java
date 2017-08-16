import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

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
	private String namedEntity = "";
	private String totalNamedEntity = "";
	
	public TreeNode(NamedNodeMap nodeMap, String ne, String wLine) 
	{
		line = wLine;
		subNodes = new ArrayList<TreeNode>();
		words = new ArrayList<TreeWord>();
		extractNodeData(nodeMap);
		namedEntity = ne;
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
	
	private void extractNodeData(NamedNodeMap nodeMap)
	{
		for (int i=0; i<nodeMap.getLength(); i++)
		{
			Node current = nodeMap.item(i);
			nodeData.put(current.getNodeName(), current.getNodeValue());
		}
	    dependency = nodeData.get("func");
	    category = nodeData.get("cat");
	}

	public String getNamedEntity() 
	{
		return namedEntity;
	}

	public void setNamedEntity(String namedEntity) {
		
		this.namedEntity = namedEntity;
	}

	public String getTotalNamedEntity() 
	{
		return totalNamedEntity;
	}

	public void setTotalNamedEntity(String totalNamedEntity)
	{
		this.totalNamedEntity = totalNamedEntity;
	}
}
