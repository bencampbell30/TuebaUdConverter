import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public TreeWord(String wLine, String ne) 
	{
		line = wLine;
		getWordData(line);
		namedEntity = ne;
		depNode = new DependencyNode(wLine, null, "", this, ne);
	}
	
	private void getWordData(String line)
	{
		line = line.replace("<", "");
		line = line.replace("/>", "");
		line = line.replace(">", "");
		line = line.replace("\"=\"", "\"EQUALS_SIGN\"");
		ArrayList<String> matchList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
		Matcher regexMatcher = regex.matcher(line);
		line = line.replaceFirst("(comment=)\"(.*)\"", "");
	    while (regexMatcher.find()) 
	    {
	    	matchList.add(regexMatcher.group());
	    }
	    for (int i=1; i<matchList.size(); i++) 
	    {
	    	String current = matchList.get(i);
	    	String key = current.split("=")[0];
	    	i++;
	    	String value = matchList.get(i);
	    	value = value.substring(1, value.length()-1);
			wordData.put(key, value.replace("EQUALS_SIGN", "="));
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