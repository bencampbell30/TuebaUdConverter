import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class TreeWord implements Serializable
{	
	private static final long serialVersionUID = 7756858844217202501L;
	private HashMap<String, String> wordData = new HashMap<String, String>();
	private DependencyNode depNode = null;
	private String line = null;
	private String dependency = null;
	private String pos = "";
	private String lemma = "";
	private int wordNumber = 0;
	private String topoField = "";
	private String namedEntity = "";
	private String totalNamedEntity = "";
	private String morph = "";
	private String typo = "";
	private String wsd = "";
	private boolean discourse = false;
	
	public TreeWord(NamedNodeMap nodeMap, String ne, String wLine) 
	{
		line = wLine;
		getWordData(nodeMap);
		namedEntity = ne;
		depNode = new DependencyNode(wLine, null, "", this, ne, nodeMap);
	}
	
	private void getWordData(NamedNodeMap nodeMap)
	{
		for (int i=0; i<nodeMap.getLength(); i++)
		{
			Node current = nodeMap.item(i);
			wordData.put(current.getNodeName(), current.getNodeValue());
		}
	    
	    dependency = wordData.get("func");
	    String lemma = wordData.get("lemma");
	    String pos = wordData.get("pos");
	    String morph = wordData.get("morph");
	    String typo = wordData.get("comment");
	    String wsd = wordData.get("wsd-lexunits");
	    
	    this.morph = morph;
	    this.typo = typo;
	    this.wsd = wsd;
	    
	    this.pos = pos;
	    if (lemma != null && lemma.contains("%passiv"))
	    {
	    	this.pos = pos + "_PASS";
	    }
	    else if (lemma != null && (lemma.startsWith("kein") || lemma.startsWith("Kein")) && (pos.equals("PIAT")))
	    {
	    	dependency = "NEG_DET";
	    }
	    else if (lemma != null && (lemma.startsWith("kein") || lemma.startsWith("Kein")) && pos.equals("ADV"))
	    {
	    	dependency = "NEG_ADV";
	    	this.pos = "ADV_NEG";
	    }
	    else if (lemma != null && (lemma.equals("lassen") || lemma.equals("bekommen")))
	    {
	    	this.pos = pos + "_LASSEN";
	    }
	    else if (lemma == null)
	    {
	    	lemma = wordData.get("form");
	    }
	    this.lemma = lemma;
	    String number[] = wordData.get("xml:id").split("_");
	    wordNumber = Integer.parseInt(number[1]);
	}
	
	public void setWordNumber(int wordNumber) 
	{
		this.wordNumber = wordNumber;
	}

	public int getWordNumber() 
	{
		return wordNumber;
	}
	
	public void setDependency(String dep)
	{
		dependency = dep;
	}
	
	public String getDependency()
	{
		return dependency;
	}
	
	public void setPos(String wPos)
	{
		pos = wPos;
	}
	
	public String getPos()
	{
		return pos;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public DependencyNode getDepNode()
	{
		return depNode;
	}
	
	public HashMap<String, String> getWordData()
	{
		return wordData;
	}

	public String getLemma() 
	{
		return lemma;
	}

	public String getTopoField() 
	{
		return topoField;
	}

	public void setTopoField(String topoField) 
	{
		this.topoField = topoField;
		depNode.setTopoField(topoField);
	}

	public String getNamedEntity() 
	{
		return namedEntity;
	}

	public void setNamedEntity(String namedEntity) 
	{
		this.namedEntity = namedEntity;
		depNode.setNamedEntity(namedEntity);
	}

	public String getTotalNamedEntity() 
	{
		return totalNamedEntity;
	}

	public void setTotalNamedEntity(String totalNamedEntity) 
	{
		this.totalNamedEntity = totalNamedEntity;
		depNode.setTotalNamedEntity(totalNamedEntity);
	}

	public String getTypo() {
		return typo;
	}

	public void setTypo(String typo) {
		this.typo = typo;
	}

	public boolean isDiscourse() {
		return discourse;
	}

	public void setDiscourse(boolean discourse) {
		this.discourse = discourse;
		depNode.setDiscourse(discourse);
	}

	public String getMorph() {
		return morph;
	}

	public void setMorph(String morph) {
		this.morph = morph;
	}
	
	public String getWsd() {
		return wsd;
	}

	public void setWsd(String wsd) {
		this.wsd = wsd;
	}
}