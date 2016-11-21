import java.util.HashMap;

public class PosConverter 
{
	private static PosConverter instance;
	private HashMap<String, String> posMap;
	
	public PosConverter() 
	{
		posMap = new HashMap<String, String>();
		populateMap();
	}

	public static PosConverter getInstance () 
	{
	    if (PosConverter.instance == null) 
	    {
	    	PosConverter.instance = new PosConverter();
	    }
	    return PosConverter.instance;
	 }
	
	private void populateMap()
	{
		posMap.put("ADJA", "ADJ");
		posMap.put("ADJD", "ADJ");
		posMap.put("ADV", "ADV");
		posMap.put("APPR", "ADP");
		posMap.put("APPRART", "ADP");
		posMap.put("APPO", "ADP");
		posMap.put("APZR", "ADP");
		posMap.put("ART", "ART");
		posMap.put("CARD", "NUM");
		posMap.put("FM", "X");
		posMap.put("ITJ", "INTJ");
		posMap.put("KOUI", "SCONJ");
		posMap.put("KOUS", "SCONJ");
		posMap.put("KON", "CONJ");
		posMap.put("KOKOM", "CONJ");
		posMap.put("NN", "NOUN");
		posMap.put("NE", "PROPN");
		posMap.put("PDS", "PRON");
		posMap.put("PDAT", "PRON");
		posMap.put("PIS", "PRON");
		posMap.put("PIAT", "PRON");
		posMap.put("PIDAT", "PRON");
		posMap.put("PPER", "PRON");
		posMap.put("PPOSS", "PRON");
		posMap.put("PPOSAT", "PRON");
		posMap.put("PRELS", "PRON");
		posMap.put("PRELAT", "PRON");
		posMap.put("PRF", "PRON");
		posMap.put("PWS", "PRON");
		posMap.put("PWAT", "PRON");
		posMap.put("PWAV", "PRON");
		posMap.put("PROP", "PART");
		posMap.put("PTKZU", "PART");
		posMap.put("PTKNEG", "PART");
		posMap.put("PTKVZ", "PART");
		posMap.put("PTKANT", "PART");
		posMap.put("PTKA", "PART");
		posMap.put("TRUNC", "PART");
		posMap.put("VVFIN", "VERB");
		posMap.put("VVIMP", "VERB");
		posMap.put("VVINF", "VERB");
		posMap.put("VVIZU", "VERB");
		posMap.put("VVFIN", "VERB");
		posMap.put("VVPP", "VERB");
		posMap.put("VAFIN", "VERB");
		posMap.put("VAIMP", "VERB");
		posMap.put("VAPP", "VERB");
		posMap.put("VAINF", "VERB");
		posMap.put("VMFIN", "VERB");
		posMap.put("VMINF", "VERB");
		posMap.put("VMPP", "VERB");
		posMap.put("XY", "X");
		posMap.put("$,", "PUNCT");
		posMap.put("$.", "PUNCT");
		posMap.put("$(", "PUNCT");
	}

	public HashMap<String, String> getPosMap() 
	{
		return posMap;
	}
}