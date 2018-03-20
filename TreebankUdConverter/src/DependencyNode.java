import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

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
	private String apprArtForm = "";
	private String pronType = "";
	private String morphCase = "";
	private String number = "";
	private String gender = "";
	private String mood = "";
	private String tense = "";
	private String person = "";
	private String verbForm = "";
	private String definite = "";
	private String polarity = "";
	private String poss = "";
	private String polite = "";
	private String foreign = "";
	private String reflex = "";
	private String voice = "";
	private String numtype = "";
	private String topoField = "";
	private String namedEntity = "";
	private String totalNamedEntity = "";
	private String morph = "";
	private String typo = "";
	private String wsd = "";
	private boolean discourse = false;
	private TreeWord word;

	public DependencyNode(String wLine, DependencyNode wHead, String wRel, TreeWord word, String ne, NamedNodeMap nodeMap) 
	{
		line = wLine;
		rel = wRel;
		head = wHead;
		dependents = new ArrayList<DependencyNode>();
		if (!(wRel.equals("N/A")) && nodeMap != null)
			extractNodeData(nodeMap);
		this.word = word;
		namedEntity = ne;
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
	
	private void extractNodeData(NamedNodeMap nodeMap)
	{
		for (int i=0; i<nodeMap.getLength(); i++)
		{
			Node current = nodeMap.item(i);
			nodeData.put(current.getNodeName(), current.getNodeValue());
		}

	    String lemma = nodeData.get("lemma");
	    String pos = nodeData.get("pos");
	    String morph = nodeData.get("morph");
	    String form = nodeData.get("form");
	    String typo = nodeData.get("comment");
	    String wsd = nodeData.get("wsd-lexunits");
	    
	    this.morph = morph;
	    this.typo = typo;
	    this.wsd = wsd;
	    
	    this.pos = pos;
	    
	    if (morph != null)
	    {
	    	if (pos.startsWith("V"))
	    	{
	    		if (pos.equals("VVFIN") || pos.equals("VMFIN")  || pos.equals("VAFIN") || pos.equals("VVIMP") || pos.equals("VAIMP"))
	    		{
	    			verbForm = "Fin";
	    		}
	    		else if (pos.equals("VVINF") || pos.equals("VVIZU")  || pos.equals("VAINF") || pos.equals("VMINF"))
	    		{
	    			verbForm = "Inf";
	    		}
	    		else if (pos.endsWith("PPP"))
	    		{
	    			verbForm = "Part";
	    		}
	    		if (pos.endsWith("IMP"))
	    		{
	    			mood = "Imp";
	    		}
	    		if (morph.length() == 1)
	    		{
	    			if (morph.equals("s"))
					{
						number = "Sing";
					}
		    		else if (morph.equals("p"))
					{
						number = "Plur";
					}
	    		}
	    		else
	    		{
	    			if (morph.substring(0, 1).equals("1"))
					{
						person = "1";
					}
		    		else if (morph.substring(0, 1).equals("2"))
					{
						person = "2";
					}
		    		else if (morph.substring(0, 1).equals("3"))
					{
						person = "3";
					}
		    		if (morph.substring(1, 2).equals("s"))
					{
						number = "Sing";
					}
		    		else if (morph.substring(1, 2).equals("p"))
					{
						number = "Plur";
					}
		    		if (morph.substring(2, 3).equals("i"))
					{
						mood = "Ind";
					}
		    		else if (morph.substring(2, 3).equals("k"))
					{
						mood = "Sub";
					}
		    		if (morph.substring(3, 4).equals("s"))
					{
						tense = "Pres";
					}
		    		else if (morph.substring(3, 4).equals("t"))
					{
						tense = "Past";
					}
	    		}
	    	}
	    	if (morph.equals("s"))
			{
				morphCase = "Sing";
			}
	    	else if (morph.equals("p"))
			{
				morphCase = "Plur";
			}
	    	else if (morph.equals("n"))
			{
				morphCase = "Nom";
			}
	    	else if (morph.equals("a"))
			{
				morphCase = "Acc";
			}
	    	else if (morph.equals("d"))
			{
				morphCase = "Dat";
			}
	    	else if (morph.equals("g"))
			{
				morphCase = "Gen";
			}
	    	else if (morph.length() >= 3)
	    	{
	    		if (morph.substring(0, 1).equals("n"))
				{
					morphCase = "Nom";
				}
		    	else if (morph.substring(0, 1).equals("a"))
				{
					morphCase = "Acc";
				}
		    	else if (morph.substring(0, 1).equals("d"))
				{
					morphCase = "Dat";
				}
		    	else if (morph.substring(0, 1).equals("g"))
				{
					morphCase = "Gen";
				}
	    		
	    		if (morph.substring(1, 2).equals("s"))
				{
					number = "Sing";
				}
		    	else if (morph.substring(1, 2).equals("p"))
				{
		    		number = "Plur";
				}
	    		
		    	if (morph.substring(2, 3).equals("m"))
				{
					gender = "Masc";
				}
		    	else if (morph.substring(2, 3).equals("n"))
				{
		    		gender = "Neut";
				}
		    	else if (morph.substring(2, 3).equals("f"))
				{
		    		gender = "Fem";
				}
	    	}
	    }
	    
	    if (pos != null && pos.equals("ART"))
	    {
	    	pronType = "Art";
	    	if (lemma.equals("der") || lemma.equals("das") || lemma.equals("die"))
	    		definite = "Def";
	    	else if (lemma.equals("ein") || lemma.equals("eine"))
	    		definite = "Ind";
	    }
	    
	    if (pos != null && pos.startsWith("P") && !pos.startsWith("PT"))
	    {
	    	if (pos.startsWith("PI"))
	    	{
	    		definite = "Ind";
	    		pronType = "Ind";
	    	}
	    	else if (pos.startsWith("PD"))
	    	{
	    		pronType = "Dem";
	    	}
	    	else if (pos.startsWith("PPO"))
	    	{
	    		poss = "Yes";
	    	}
	    	else if (pos.startsWith("PPE"))
	    	{
	    		pronType = "Prs";
	    		if (lemma.equals("Sie") && morph.substring(3, 4).equals("2"))
	    			polite = "Yes";
	    	}
	    	else if (pos.startsWith("PW"))
	    	{
	    		pronType = "Int";
	    	}
	    	else if (pos.startsWith("PRE"))
	    	{
	    		pronType = "Rel";
	    	}
	    	else if (pos.equals("PRF"))
		    {
		    	reflex = "Yes";
		    	pronType = "Prs";
		    }
	    	if (lemma.startsWith("kein") || lemma.startsWith("Kein"))
	    	{
	    		pronType = "Neg";
	    		polarity = "Neg";
	    	}
	    }
	    
	    if (pos.equals("PTKNEG"))
	    {
	    	polarity = "Neg";
	    }
	    
	    if (pos != null && pos.equals("FM"))
	    {
	    	foreign = "Yes";
	    }
	    else if (pos != null && pos.equals("CARD"))
	    {
	    	numtype = "Card";
	    }
	    
	    if (lemma != null && lemma.contains("%passiv"))
	    {
	    	this.pos = pos + "_PASS";
	    	voice = "Pass";
	    }
	    else if (lemma != null && (lemma.equals("lassen") || lemma.equals("bekommen")))
	    {
	    	this.pos = pos + "_LASSEN";
	    }
	    else if (lemma != null && (lemma.startsWith("kein") || lemma.startsWith("Kein")) && pos.equals("ADV"))
	    {
	    	this.pos = "ADV_NEG";
	    	polarity = "Neg";
	    }
	    else if (lemma == null)
	    {
	    	lemma = nodeData.get("form");
	    }
	    
	    if (lemma.equals("#refl"))
	    {
	    	if (form.equals("mich") || form.equals("mir"))
	    	{
	    		lemma = "ich";
	    	}
	    	else if (form.equals("dich") || form.equals("dir"))
	    	{
	    		lemma = "du";
	    	}
	    	else if (form.equals("uns"))
	    	{
	    		lemma = "wir";
	    	}
	    	else if (form.equals("euch"))
	    	{
	    		lemma = "ihr";
	    	}
	    	else if (form.equals("sich"))
	    	{
	    		lemma = "er|es|sie";
	    	}
	    }
	    
	    this.lemma = lemma;
	    
	    String number[] = nodeData.get("xml:id").split("_");
	    wordNumber = Integer.parseInt(number[1]);
	}
	
	// For broken up Apprart
	public void setArtMorphInfo(String morph)
	{
		definite = "Def";
		pronType = "Art";
		if (morph.substring(0, 1).equals("n"))
		{
			morphCase = "Nom";
		}
    	else if (morph.substring(0, 1).equals("a"))
		{
			morphCase = "Acc";
		}
    	else if (morph.substring(0, 1).equals("d"))
		{
			morphCase = "Dat";
		}
    	else if (morph.substring(0, 1).equals("g"))
		{
			morphCase = "Gen";
		}
		
		if (morph.substring(1, 2).equals("s"))
		{
			number = "Sing";
		}
    	else if (morph.substring(1, 2).equals("p"))
		{
    		number = "Plur";
		}
		
    	if (morph.substring(2, 3).equals("m"))
		{
			gender = "Masc";
		}
    	else if (morph.substring(2, 3).equals("n"))
		{
    		gender = "Neut";
		}
    	else if (morph.substring(2, 3).equals("f"))
		{
    		gender = "Fem";
		}
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
	
	public void setMorphCase(String morphCase) {
		this.morphCase = morphCase;
	}

	public String getMorphCase() {
		return morphCase;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
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

	public String getTopoField() {
		return topoField;
	}

	public void setTopoField(String topoField) {
		this.topoField = topoField;
	}

	public String getNamedEntity() 
	{
		return namedEntity;
	}

	public void setNamedEntity(String namedEntity) 
	{
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

	public String getMorph() {
		return morph;
	}

	public void setMorph(String morph) {
		this.morph = morph;
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
	}

	public String getWsd() {
		return wsd;
	}

	public void setWsd(String wsd) {
		this.wsd = wsd;
	}
}