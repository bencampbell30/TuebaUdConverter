import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;

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
	
	public TreeWord(String wLine) 
	{
		line = wLine;
		getWordData(line);
		depNode = new DependencyNode(wLine, null, "", this);
	}
	
	private void getWordData(String line)
	{
		line = line.replace("<", "");
		line = line.replace("/>", "");
		line = line.replace(">", "");
		line = line.replace("\"=\"", "\"EQUALS_SIGN\"");
		line = line.replaceFirst("(comment=)\"(.*)\"", "");
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken(); //get rid of node/sentence marker
	    while (st.hasMoreTokens()) 
	    {
	    	String current = st.nextToken();
	    	String[] keyValue = current.split("=");
	    	keyValue[1] = keyValue[1].substring(1, keyValue[1].length()-1);
			wordData.put(keyValue[0], keyValue[1].replace("EQUALS_SIGN", "="));
	    }
	    dependency = wordData.get("func");
	    String lemma = wordData.get("lemma");
	    String pos = wordData.get("pos");
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
}