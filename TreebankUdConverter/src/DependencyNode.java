import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class DependencyNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6119428969091924401L;
	private ArrayList<DependencyNode> dependents;
	private String line = null;
	private HashMap<String, String> nodeData = new HashMap<String, String>();
	private String rel = ""; //relation to head
	private DependencyNode head;
	private String pos = "";
	private int wordNumber = 0;
	private String lemma = "";
	boolean apprArt = false;
	String apprArtForm = "";
	String pronType = "";
	String morphCase = "";
	String number = "";
	String gender = "";
	String mood = "";
	String tense = "";
	String person = "";
	String verbForm = "";
	String definite = "";
	String polarity = "";
	String poss = "";
	String polite = "";
	String foreign = "";
	String reflex = "";
	String voice = "";
	String numtype = "";
	TreeWord word;

	public DependencyNode(String wLine, DependencyNode wHead, String wRel, TreeWord word) 
	{
		line = wLine;
		rel = wRel;
		head = wHead;
		dependents = new ArrayList<DependencyNode>();
		if (!(wRel.equals("N/A")))
			extractNodeData(line);
		this.word = word;
	}
	
	public void setRel(String wRel)
	{
		rel = wRel;
	}
	
	public String getRel()
	{
		return rel;
	}
	
	public void setPos(String wPos)
	{
		pos = wPos;
	}
	
	public String getPos()
	{
		return pos;
	}
	
	public void setHead(DependencyNode wHead)
	{
		head = wHead;
	}
	
	public DependencyNode getHead()
	{
		return head;
	}

	public void addDependent(DependencyNode node)
	{
		dependents.add(node);
	}
	
	public void setSubNodes(ArrayList<DependencyNode> nodes)
	{
		dependents = nodes;
	}
	
	public ArrayList<DependencyNode> getSubNodes()
	{
		return dependents;
	}
	
	public String getLine()
	{
		return line;
	}
	
	public HashMap<String, String> getNodeData()
	{
		return nodeData;
	}
	
	private void extractNodeData(String line)
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
			nodeData.put(keyValue[0], keyValue[1].replace("EQUALS_SIGN", "="));
	    }
	    String lemma = nodeData.get("lemma");
	    String pos = nodeData.get("pos");
	    this.pos = pos;
	    if (lemma != null && lemma.contains("%passiv"))
	    {
	    	this.pos = pos + "_PASS";
	    }
	    else if (lemma != null && (lemma.equals("lassen") || lemma.equals("bekommen")))
	    {
	    	this.pos = pos + "_LASSEN";
	    }
	    else if (lemma != null && (lemma.startsWith("kein") || lemma.startsWith("Kein")) && pos.equals("ADV"))
	    {
	    	this.pos = "ADV_NEG";
	    	polarity = "negative";
	    }
	    else if (lemma == null)
	    {
	    	lemma = nodeData.get("form");
	    }
	    this.lemma = lemma;
	    String number[] = nodeData.get("xml:id").split("_");
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
	
	public void setLemma(String lemma) 
	{
		this.lemma = lemma;
	}

	public String getLemma() 
	{
		return lemma;
	}

	public TreeWord getWord() 
	{
		return word;
	}

	public boolean isApprArt() 
	{
		return apprArt;
	}

	public void setApprArt(boolean apprArt) 
	{
		this.apprArt = apprArt;
	}

	public String getApprArtForm() 
	{
		return apprArtForm;
	}

	public void setApprArtForm(String apprArtForm) 
	{
		this.apprArtForm = apprArtForm;
	}

	public String getPronType() {
		return pronType;
	}

	public String getMorphCase() {
		return morphCase;
	}

	public String getNumber() {
		return number;
	}

	public String getGender() {
		return gender;
	}

	public String getMood() {
		return mood;
	}

	public String getTense() {
		return tense;
	}

	public String getPerson() {
		return person;
	}

	public String getVerbForm() {
		return verbForm;
	}

	public String getDefinite() {
		return definite;
	}

	public String getPolarity() {
		return polarity;
	}

	public String getPoss() {
		return poss;
	}

	public String getPolite() {
		return polite;
	}

	public String getForeign() {
		return foreign;
	}

	public String getReflex() {
		return reflex;
	}

	public String getVoice() {
		return voice;
	}

	public String getNumtype() {
		return numtype;
	}
}