import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TreebankUdConverter 
{
	private static ArrayList<DependencyNode> dependencySentences;
	private static ArrayList<ArrayList<String>> treebankLines;
	private static String path = "";
	private static String outPath = "";
	private static ArrayList<TreeNode> sentenceNodes;
	private static ArrayList<TreeNode> sentenceNodesFieldModified;
	private static ArrayList<TreeNode> sentenceNodesClipped;
	private static ArrayList<TreeNode> sentenceNodesFunctionDetermined;
	private static ArrayList<TreeNode> sentenceNodesTransformed;
	private static ArrayList<ArrayList<DependencyNode>> arrayOrderedSentences;
	private static ArrayList<ArrayList<ArrayList<String>>> conllSentences;
	private static StructureTransformer structureTransformerInstance;
	private static PosConverter posConverterInstance;
	private static int sentenceIndex = 0;
	private static String currentArticleID = "";
	private static HashMap<Integer, String> newArticleMap;
	
	public static void main(String[] args) 
	{
		path = args[0];
		outPath = args[1];
		//path = "/Users/bcmpbell/Documents/TestSentence.txt";
		
		for (int i=0; i<21; i++)
		{
			chunkedProcess(i*5000+1, (i+1)*5000);
		}
		//chunkedProcess(1, 1000);
	}
	
	private static void chunkedProcess (int start, int end)
	{
		treebankLines = readExportFile(path, start, end);
		sentenceNodes = new ArrayList<TreeNode>();
		sentenceNodesFieldModified = new ArrayList<TreeNode>();
		sentenceNodesClipped = new ArrayList<TreeNode>();
		sentenceNodesFunctionDetermined = new ArrayList<TreeNode>();
		sentenceNodesTransformed = new ArrayList<TreeNode>();
		dependencySentences = new ArrayList<DependencyNode>();
		conllSentences = new ArrayList<ArrayList<ArrayList<String>>>();
		structureTransformerInstance = StructureTransformer.getInstance();
		posConverterInstance = PosConverter.getInstance();
		newArticleMap = new HashMap<Integer, String>();
		
		for (int i=0; i<treebankLines.size(); i++)
		{
			sentenceIndex = 0;
			TreeNode currentSentence = treeBuilder(treebankLines.get(i), i, "");
			sentenceNodes.add(currentSentence);
			System.out.println(i);
		}
		
		System.out.println("STAGE 1");
		
		for (int i=0; i<sentenceNodes.size(); i++)
		{
			addTopoFieldInfo(sentenceNodes.get(i), "");
		}
		
		System.out.println("STAGE 1.5");
		
		for (int i=0; i<sentenceNodes.size(); i++)
		{
			addNamedEntityInfo(sentenceNodes.get(i), "");
		}
		
		System.out.println("STAGE 1.75");
		
		for (int i=0; i<sentenceNodes.size(); i++)
		{
			addDiscourseInfo(sentenceNodes.get(i), false);
		}
		
		System.out.println("STAGE 2");
		
		sentenceIndex = 0;
		
		for (int i=0; i<sentenceNodes.size(); i++)
		{
			TreeNode fKoordModNode = topoFieldModifier(sentenceNodes.get(i));
			sentenceNodesFieldModified.add(fKoordModNode);
		}
		
		System.out.println("STAGE 3");
		
		for (int i=0; i<sentenceNodesFieldModified.size(); i++)
		{
			TreeNode clippedNode = clipTree(sentenceNodesFieldModified.get(i));
			sentenceNodesClipped.add(clippedNode);
		}
		
		System.out.println("STAGE 4");
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			TreeNode currentNode = sentenceNodesClipped.get(i);
			zuLabeler(currentNode);
		}
		
		System.out.println("STAGE 5");
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			TreeNode currentNode = sentenceNodesClipped.get(i);
			boolean match = true;
			
			while (match)
			{
				match = transformFKONJ(currentNode);
				removeKonj1(currentNode);
			}
		}
		
		System.out.println("STAGE 6");
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			TreeNode currentNode = sentenceNodesClipped.get(i);
			setHeadHeuristic(currentNode);
		}
		
		System.out.println("STAGE 7");
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			sentenceNodesFunctionDetermined.add(functionDeterminer(sentenceNodesClipped.get(i)));
		}
		
		System.out.println("STAGE 8");
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			setConjunct(currentNode);
		}
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			setMod(currentNode);
		}
		
		System.out.println("STAGE 9");
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			identifyCCompXComp(currentNode);
		}
		
		System.out.println("STAGE 10");
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			sentenceNodesTransformed.add(transformDependencies(sentenceNodesFunctionDetermined.get(i), true));
		}
		
		System.out.println("STAGE 11");
		
		for (int i=0; i<sentenceNodesTransformed.size(); i++)
		{
			dependencySentences.add(extractDepStructure(sentenceNodesTransformed.get(i), true, null));
		}
		
		System.out.println("STAGE 12");
		
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			convertPos(currentNode);
		}
		
		System.out.println("STAGE 13");
		
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			setHead(currentNode);
		}
		
		System.out.println("STAGE 14");
		
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			setObjects(currentNode);
		}
		
		System.out.println("STAGE 15");
		
		ArrayList<HashMap<Integer, DependencyNode>> orderedSentences = new ArrayList<HashMap<Integer, DependencyNode>>();
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			HashMap<Integer, DependencyNode> currentSentence = new HashMap<Integer, DependencyNode>();
			currentSentence = putNodesInOrder(currentNode, currentSentence);
			orderedSentences.add(currentSentence);
		}
		
		System.out.println("STAGE 16");
		
		arrayOrderedSentences = new ArrayList<ArrayList<DependencyNode>>();
		for (int i=0; i<orderedSentences.size(); i++)
		{
			HashMap<Integer, DependencyNode> currentSentence = orderedSentences.get(i);
			ArrayList<DependencyNode> currentSentenceNew = new ArrayList<DependencyNode>();
			for (int j=0; j<currentSentence.size(); j++)
			{
				DependencyNode currentNode = currentSentence.get(j);
				currentSentenceNew.add(currentNode);
			}
			arrayOrderedSentences.add(currentSentenceNew);
		}
		
		System.out.println("STAGE 17");
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			breakUpApprArt(currentSentence);
		}
		
		System.out.println("STAGE 18");
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			for (int j=0; j<currentSentence.size(); j++)
			{
				DependencyNode currentNode = currentSentence.get(j);
				currentNode.setWordNumber(j);
			}
		}
		
		System.out.println("STAGE 19");
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			setCCDependencies(currentSentence, currentSentence.get(0));
			for (int j=0; j<currentSentence.size(); j++)
			{
				DependencyNode current = currentSentence.get(j);
				if (current.getRel() != null && current.getRel().equals("cc"))
				{
					if (j+1 < currentSentence.size())
					{
						DependencyNode checkConj = currentSentence.get(j+1);
						if (checkConj.getRel().equals("conj"))
						{
							current.setHead(checkConj);
						}
					}
				}
			}
		}
		
		System.out.println("STAGE 20");
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			setPunctuationDependencies(currentSentence, currentSentence.get(0));
		}
		
		System.out.println("STAGE 21");
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			ArrayList<ArrayList<String>> sentence = convertNodesToText(currentSentence);
			conllSentences.add(sentence);
		}
		
		System.out.println("STAGE 22");
		
		printSentences(outPath, start);
		
		System.out.println("Finished");
	}
	
	private static TreeNode treeBuilder(ArrayList<String> sentence, int currentSentence, String namedEntity)
	{
		boolean finished = false;
		String currentLine = sentence.get(sentenceIndex);
		if (currentLine.contains("<text"))
		{
			HashMap<String, String> textData = new HashMap<String, String>();
			currentLine = currentLine.replace("<", "");
			currentLine = currentLine.replace("/>", "");
			currentLine = currentLine.replace(">", "");
			StringTokenizer st = new StringTokenizer(currentLine);
			st.nextToken(); //get rid of node/sentence marker
		    while (st.hasMoreTokens()) 
		    {
		    	String currentPair = st.nextToken();
		    	String[] keyValue = currentPair.split("=");
		    	keyValue[1] = keyValue[1].substring(1, keyValue[1].length()-1);
		    	textData.put(keyValue[0], keyValue[1]);
		    	currentArticleID = textData.get("origin");
		    }
		    sentenceIndex++;
		    currentLine = sentence.get(sentenceIndex);
		    newArticleMap.put(currentSentence, currentArticleID);
		}
		String currentLevelNe = namedEntity;
		TreeNode current = new TreeNode(currentLine, currentLevelNe);
		String lowerLevelNe = "";
		String neId = "";
		
		while (!finished)
		{
			sentenceIndex++;
			currentLine = sentence.get(sentenceIndex);
				
			if (currentLine.contains("<node") || currentLine.contains("<sentence"))
			{
				String ne = "";
				if (!lowerLevelNe.equals(""))
					ne = lowerLevelNe + "_" + neId;
				current.addSubNode(treeBuilder(treebankLines.get(currentSentence), currentSentence, ne));
			}
			else if (currentLine.contains("</ne>"))
			{
				lowerLevelNe = "";
				neId = "";
			}
			else if (currentLine.contains("<word"))
			{
				String ne = "";
				if (!lowerLevelNe.equals(""))
					ne = lowerLevelNe + "_" + neId;
				current.addWord(new TreeWord(currentLine, ne));
			}
			else if (currentLine.contains("</node") || currentLine.contains("</sentence"))
			{
				finished = true;
			}
			else if (currentLine.contains("<ne"))
			{
				HashMap<String, String> neData = new HashMap<String, String>();
				currentLine = currentLine.replace("<", "");
				currentLine = currentLine.replace("/>", "");
				currentLine = currentLine.replace(">", "");
				StringTokenizer st = new StringTokenizer(currentLine);
				st.nextToken(); //get rid of node/sentence marker
			    while (st.hasMoreTokens()) 
			    {
			    	String currentPair = st.nextToken();
			    	String[] keyValue = currentPair.split("=");
			    	keyValue[1] = keyValue[1].substring(1, keyValue[1].length()-1);
			    	neData.put(keyValue[0], keyValue[1]);
			    }
			    lowerLevelNe = neData.get("type");
			    neId = neData.get("xml:id").split("_")[1];
			}
		}
		return current;
	}
	
	//Add topo field information to word nodes
	private static void addTopoFieldInfo(TreeNode node, String currentFieldChain)
	{
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		ArrayList<TreeWord> words = node.getWords();
		
		for (int i=0; i<words.size(); i++)
		{
			TreeWord currentWord = words.get(i);
			currentWord.setTopoField(currentFieldChain);
		}
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			String nodeName = currentSubNode.getCategory();
			String fieldChain = currentFieldChain;
			
			if ((nodeName.equals("VF") || nodeName.equals("LK") || nodeName.equals("MF") || nodeName.equals("MFE") || nodeName.equals("VC") || nodeName.equals("VCE") || nodeName.equals("NF")
					 || nodeName.equals("LV") || nodeName.equals("C") || nodeName.equals("KOORD") || nodeName.equals("FKOORD") || nodeName.equals("PARORD") || nodeName.equals("FKONJ")))
			{
				if (!fieldChain.isEmpty())
					fieldChain = fieldChain + "-" + nodeName;
				else
					fieldChain = nodeName;
			}
			addTopoFieldInfo(currentSubNode, fieldChain);
		}
	}
	
	//Add discourse information to word nodes
		private static void addDiscourseInfo(TreeNode node, boolean dm)
		{
			ArrayList<TreeNode> subNodes = node.getSubNodes();
			ArrayList<TreeWord> words = node.getWords();
			boolean isDiscourse = dm;
			
			for (int i=0; i<words.size(); i++)
			{
				TreeWord currentWord = words.get(i);
				currentWord.setDiscourse(isDiscourse);
			}
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				String nodeName = currentSubNode.getCategory();
				
				if (nodeName.equals("DM") || dm)
				{
					isDiscourse = true;
				}
				else
				{
					isDiscourse = false;
				}
				addDiscourseInfo(currentSubNode, isDiscourse);
			}
		}
	
	//Recursively get Named Entity information
	private static void addNamedEntityInfo(TreeNode node, String currentNeChain)
	{
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		ArrayList<TreeWord> words = node.getWords();
		
		for (int i=0; i<words.size(); i++)
		{
			TreeWord currentWord = words.get(i);
			String namedEntity = currentWord.getNamedEntity();
			String neChain = currentNeChain;
			
			if (!neChain.isEmpty())
			{
				if (!namedEntity.isEmpty())
					neChain = neChain + "-" + namedEntity;
			}
			else
				neChain = namedEntity;
			
			currentWord.setTotalNamedEntity(neChain);
		}
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			String namedEntity = currentSubNode.getNamedEntity();
			String neChain = currentNeChain;
			
			if (!neChain.isEmpty())
			{
				if (!namedEntity.isEmpty())
					neChain = neChain + "-" + namedEntity;
			}
			else
				neChain = namedEntity;
			
			addNamedEntityInfo(currentSubNode, neChain);
		}
	}
	
	/*
	 * Before removing topological fields, use the information to make necessary structural changes
	 */
	private static TreeNode topoFieldModifier(TreeNode node)
	{
		TreeNode topoModifiedNode = node;
		HashMap<String, String> nodeData = topoModifiedNode.getNodeData();
		String nodeName = nodeData.get("cat");
		ArrayList<TreeNode> subNodes = topoModifiedNode.getSubNodes();
		ArrayList<TreeWord> words = topoModifiedNode.getWords();
		if (nodeName != null)
		{
			if (nodeName.equals("VC"))
			{
				for (int i=0; i<subNodes.size(); i++)
				{
					TreeNode currentSubNode = subNodes.get(i);
					if (currentSubNode.getDependency().equals("HD"))
					{
						currentSubNode.setDependency("VC-HD");
						currentSubNode.getNodeData().put("func", "VC-HD");
					}
				}
				for (int i=0; i<words.size(); i++)
				{
					TreeWord currentWord = words.get(i);
					if (currentWord.getDependency().equals("HD"))
					{
						currentWord.setDependency("VC-HD");
						currentWord.getWordData().put("func", "VC-HD");
					}
				}
			}
		}
		subNodes = topoModifiedNode.getSubNodes(); //get subNodes again after rearranging
		for (int i=0; i<subNodes.size(); i++)
		{
			topoFieldModifier(subNodes.get(i));
		}
		return topoModifiedNode;
	}
	
	/* 
	 * Get rid of nodes like MF, VF, ect, so that structure more closely resembles a dependency structure
	 */
	private static TreeNode clipTree(TreeNode node)
	{
		TreeNode clippedNode = node;
		ArrayList<TreeNode> subNodes = clippedNode.getSubNodes();
		ArrayList<TreeWord> words = clippedNode.getWords();
		
		boolean hasExtrNodes = true;
		
		while (hasExtrNodes) 
		{
			hasExtrNodes = false;
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode current = subNodes.get(i);
				HashMap<String, String> nodeData = current.getNodeData();
				String nodeName = nodeData.get("cat");
				String nodeFunction = nodeData.get("func");
				if ((nodeName.equals("VF") || nodeName.equals("LK") || nodeName.equals("MF") || nodeName.equals("MFE") || nodeName.equals("VC") || nodeName.equals("VCE") || nodeName.equals("NF")
						 || nodeName.equals("LV") || nodeName.equals("C") || nodeName.equals("KOORD") || nodeName.equals("FKOORD") || nodeName.equals("PARORD") || 
						 (nodeName.equals("FKONJ") && nodeFunction.equals("-"))) && !(nodeFunction.equals("KONJ") || nodeFunction.equals("KONJ1") || nodeFunction.equals("KONJ2")))
				{
					hasExtrNodes = true;
					ArrayList<TreeNode> subSubNodes = current.getSubNodes();
					ArrayList<TreeWord> subSubWords = current.getWords();
					subNodes.remove(i);
					for (int j=0; j<subSubNodes.size(); j++)
					{
						TreeNode currentSubSub = subSubNodes.get(j);
						subNodes.add(i, currentSubSub);
						i++;
					}
					for (int j=0; j<subSubWords.size(); j++)
					{
						TreeWord currentSubWord = subSubWords.get(j);
						words.add(currentSubWord);
						i++;
					}
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			clipTree(subNodes.get(i));
		}
		
		return clippedNode;
	}
	
	//Label subnodes of VXINF where PTKZU is the head, in order to find out important information about relevant infinitive
	private static TreeNode zuLabeler(TreeNode node)
	{
		TreeNode convertedNode = node;
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		ArrayList<TreeWord> subWords = node.getWords();
		
		if (convertedNode.getCategory() != null && convertedNode.getCategory().equals("VXINF"))
		{
			if ((subWords.size() > 1) && subWords.get(0).getWordData().get("pos").equals("PTKZU"))
			{
				if (subWords.get(1).getWordData().get("lemma").equals("lassen"))
				{
					subWords.get(0).setPos("PTKZU-LASS");
				}
				else if (subWords.get(1).getWordData().get("lemma").contains("%passiv"))
				{
					subWords.get(0).setPos("PTKZU-PASS");
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			zuLabeler(currentSubNode);
		}
		
		return convertedNode;
	}
	
	/*
	 * Determine functions for nodes that have only the function '-', in order to make conversion to dependency format easier
	 */
	private static TreeNode functionDeterminer(TreeNode node)
	{
		TreeNode funcAddedNode = node;
		ArrayList<TreeNode> subNodes = funcAddedNode.getSubNodes();
		ArrayList<TreeWord> words = funcAddedNode.getWords();
		boolean hasVcHd = false;
		boolean hasHd = false;
		TreeNode vcHdNode = null;
		
		String nodeCategory = node.getCategory();
		
		if ((nodeCategory != null) && (nodeCategory.equals("NX")))
		{
			boolean foundHeadOrKonjOrApp = false;
			TreeNode lastAdj = null;
			TreeWord trunc = null;
			
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				String currentFunction = currentSubNode.getDependency();
				String currentCategory = currentSubNode.getCategory();
				if (currentFunction.equals("KONJ") || currentFunction.equals("HD") || currentFunction.equals("APP"))
				{
					foundHeadOrKonjOrApp = true;
					break;
				}
				else if (currentCategory.equals("ADJX"))
				{
					lastAdj = currentSubNode;
				}
			}
			if (!foundHeadOrKonjOrApp)
			{
				for (int i=0; i<words.size(); i++)
				{
					TreeWord currentWord = words.get(i);
					String currentFunction = currentWord.getDependency();
					String currentPos = currentWord.getPos();
					if (currentFunction.equals("KONJ") || currentFunction.equals("HD") || currentFunction.equals("APP"))
					{
						foundHeadOrKonjOrApp = true;
						break;
					}
					else if (currentPos.equals("TRUNC"))
					{
						trunc = currentWord;
					}
				}
			}
			if (!foundHeadOrKonjOrApp)
			{
				if (trunc != null)
					trunc.setDependency("HD");
				else if (lastAdj != null && !lastAdj.getDependency().equals("--"))
				{
					lastAdj.setDependency("HD");
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			String currentFunction = currentSubNode.getDependency();
			if (currentFunction.equals("VC-HD"))
			{
				hasVcHd = true;
				vcHdNode = currentSubNode;
			}
			else if (currentFunction.equals("HD"))
			{
				hasHd = true;
			}
			else if (funcAddedNode.getCategory() != null && currentFunction.equals("--"))
			{
				currentSubNode.setDependency("PARA");
			}
			else if ((currentFunction.equals("-") || currentFunction.equals("--")) && !(currentSubNode.getCategory().equals("SIMPX") || currentSubNode.getCategory().equals("R-SIMPX") || currentSubNode.getCategory().equals("P-SIMPX")))
			{
				if (currentSubNode.getCategory().equals("NX"))
				{
					ArrayList<DependencyNode> heads = headWordFinder(currentSubNode);
					DependencyNode currentHead = heads.get(0);
					String morph = currentHead.getNodeData().get("morph");
					ArrayList<TreeWord> subWords = currentSubNode.getWords();
					for (int j=0; j<subWords.size(); j++)
					{
						TreeWord currentWord = subWords.get(j);
						if (currentWord.getPos().equals("KOKOM"))
						{
							currentSubNode.setDependency("OBL");
							break;
						}
					}
					if (morph != null)
					{
						if (morph.substring(0, 1).equals("g"))
						{
							currentSubNode.setDependency("GEN");
						}
					}
				}
			}
			else if (currentFunction.equals("PRED"))
			{
				ArrayList<TreeWord> currentSubNodeWords = currentSubNode.getWords();
				ArrayList<TreeNode> subSubNodes = currentSubNode.getSubNodes();
				boolean hasKokom = false;
				for (int j=0; j<currentSubNodeWords.size(); j++)
				{
					TreeWord currentSubNodeWord = currentSubNodeWords.get(j);
					String pos = currentSubNodeWord.getWordData().get("pos");
					if (pos.equals("KOKOM"))
					{
						currentSubNodeWord.setDependency("PRED-KOKOM");
						hasKokom = true;
						currentSubNode.setDependency("PREDWITHKOKOM");
					}
				}
				if (hasKokom)
				{
					TreeNode newNode = makeDeepCopy(currentSubNode);
					ArrayList<TreeWord> newNodeWords = newNode.getWords();
					for (int j=subSubNodes.size()-1; j>=0; j--)
					{
						TreeNode currentSubSubNode = subSubNodes.get(j);
						subSubNodes.remove(currentSubSubNode);
					}
					for (int j=currentSubNodeWords.size()-1; j>=0; j--)
					{
						TreeWord currentSubNodeWord = currentSubNodeWords.get(j);
						String pos = currentSubNodeWord.getWordData().get("pos");
						if (!(pos.equals("KOKOM")))
						{
							currentSubNodeWords.remove(currentSubNodeWord);
						}
					}
					for (int j=newNodeWords.size()-1; j>=0; j--)
					{
						TreeWord currentSubNodeWord = newNodeWords.get(j);
						String pos = currentSubNodeWord.getWordData().get("pos");
						if ((pos.equals("KOKOM")))
						{
							newNodeWords.remove(currentSubNodeWord);
						}
					}
					newNode.setDependency("HD");
					subSubNodes.add(newNode);
				}
			}
			functionDeterminer(currentSubNode);
		}
		for (int i=0; i<words.size(); i++)
		{
			TreeWord currentWord = words.get(i);
			String currentFunction = currentWord.getDependency();
			
			if (currentFunction.equals("-") || currentFunction.equals("--"))
			{
				String pos = currentWord.getWordData().get("pos");
				String morph = currentWord.getWordData().get("morph");
				
				if (pos.startsWith("$"))
				{
					currentWord.setDependency("PUNCT");
				}
				else if (morph != null)
				{
					if (morph.substring(0, 1).equals("g") && (pos.equals("PPOS") || pos.equals("PPOSAT") || pos.equals("PRELS") || pos.equals("PRELAT")))
					{
						currentWord.setDependency("GEN");
					}
				}
			}
		}
		if (hasVcHd && !hasHd)
		{
			vcHdNode.setDependency("HD");
			vcHdNode.getNodeData().put("func", "HD");
		}
		return funcAddedNode;
	}
	
	// Deal with structures where there is a HD and KONJ at the same level
	private static boolean transformFKONJ(TreeNode node)
	{
		//Check structure
		TreeNode transformed = node;
		ArrayList<TreeNode> subNodes = transformed.getSubNodes();
		ArrayList<TreeWord> words = transformed.getWords();
		boolean hdFound = false;
		boolean konj1 = false;
		boolean konj2 = false;
		boolean match = false;
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			if (currentSubNode.getDependency().equals("HD") || currentSubNode.getDependency().equals("VC-HD")  || currentSubNode.getDependency().equals("PRED") || currentSubNode.getDependency().equals("OV") || 
					currentSubNode.getDependency().equals("PREDWITHKOKOM"))
			{
				hdFound = true;
			}
			else if (currentSubNode.getDependency().equals("KONJ"))
			{
				if (!konj1)
					konj1 = true;
				else
					konj2 = true;
			}
		}
		for (int i=0; i<words.size(); i++)
		{
			TreeWord currentWord = words.get(i);
			if (currentWord.getDependency().equals("HD") || currentWord.getDependency().equals("VC-HD"))
			{
				hdFound = true;
			}
		}
		
		match = konj1 && konj2 && hdFound;
		konj1 = false;
		
		if (match)
		{
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				if (currentSubNode.getDependency().equals("KONJ"))
				{
					if (!konj1)
					{
						konj1 = true;
						currentSubNode.setDependency("KONJ1");
					}
					else
					{
						currentSubNode.setDependency("KONJ2");
					}
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			match = match || transformFKONJ(subNodes.get(i));
		}
		
		return match;
	}
	
	private static TreeNode removeKonj1 (TreeNode node)
	{
		TreeNode konj1clipped = node;
		ArrayList<TreeNode> subNodes = konj1clipped.getSubNodes();
		ArrayList<TreeWord> words = konj1clipped.getWords();
		
		boolean hasKonj1 = true;
		
		while (hasKonj1) 
		{
			hasKonj1 = false;
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode current = subNodes.get(i);
				String nodeFunction = current.getDependency();
				if (nodeFunction.equals("KONJ1"))
				{
					hasKonj1 = true;
					ArrayList<TreeNode> subSubNodes = current.getSubNodes();
					ArrayList<TreeWord> subSubWords = current.getWords();
					subNodes.remove(i);
					for (int j=0; j<subSubNodes.size(); j++)
					{
						TreeNode currentSubSub = subSubNodes.get(j);
						subNodes.add(i, currentSubSub);
						i++;
					}
					for (int j=0; j<subSubWords.size(); j++)
					{
						TreeWord currentSubWord = subSubWords.get(j);
						words.add(currentSubWord);
						i++;
					}
				}
			}
		}
		for (int i=0; i<subNodes.size(); i++)
		{
			removeKonj1(subNodes.get(i));
		}
		return konj1clipped;
	}
	
	// Set a HD node for subnodes if not available
	private static TreeNode setHeadHeuristic (TreeNode node)
	{
		TreeNode headSet = node;
		ArrayList<TreeNode> subNodes = headSet.getSubNodes();
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode current = subNodes.get(i);
			String nodeFunction = current.getDependency();
			boolean hasHD = false;
			ArrayList<TreeNode> subSubNodes = current.getSubNodes();
			ArrayList<TreeWord> subSubWords = current.getWords();
			for (int j=0; j<subSubNodes.size(); j++)
			{
				TreeNode currentSubSub = subSubNodes.get(j);
				if (currentSubSub.getDependency().equals("HD") || currentSubSub.getDependency().equals("VC-HD"))
				{
					hasHD = true;
				}
			}
			for (int j=0; j<subSubWords.size(); j++)
			{
				TreeWord currentSubWord = subSubWords.get(j);
				if (currentSubWord.getDependency().equals("HD") || currentSubWord.getDependency().equals("VC-HD"))
				{
					hasHD = true;
				}
			}
			if (!hasHD)
			{
				boolean foundOV = false;
				for (int j=0; j<subSubNodes.size(); j++)
				{
					TreeNode currentSubSub = subSubNodes.get(j);
					if (currentSubSub.getDependency().equals("OV"))
					{
						foundOV = true;
						currentSubSub.setDependency("HD");
						break;
					}
				}
				if (!foundOV)
				{
					boolean foundPred = false;
					for (int j=0; j<subSubNodes.size(); j++)
					{
						TreeNode currentSubSub = subSubNodes.get(j);
						if (currentSubSub.getDependency().equals("PRED"))
						{
							foundPred = true;
							currentSubSub.setDependency("HD");
							break;
						}
					}
					if (!foundPred)
					{
						boolean foundKonjOrApp = false;
						for (int j=0; j<subSubNodes.size(); j++)
						{
							TreeNode currentSubSub = subSubNodes.get(j);
							if (currentSubSub.getDependency().equals("KONJ") || currentSubSub.getDependency().equals("APP"))
							{
								foundKonjOrApp = true;
								break;
							}
						}
						if (!foundKonjOrApp && !subSubNodes.isEmpty() && nodeFunction.equals("KONJ2"))
						{
							subSubNodes.get(subSubNodes.size()-1).setDependency("HD");
						}
					}
				}
			}
		}
		for (int j=0; j<subNodes.size(); j++)
		{
			setHeadHeuristic(subNodes.get(j));
		}
		return headSet;
	}
	
	// Add ONK as child of ON, OAK as child of OA, etc...
	private static TreeNode setConjunct(TreeNode node)
	{
		TreeNode transformedNode = node;
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		
		boolean foundON = false;
		boolean foundONK = false;
		
		TreeNode onNode = null;
		ArrayList<TreeNode> onkNode = new ArrayList<TreeNode>();
		
		boolean foundOA = false;
		boolean foundOAK = false;
		
		TreeNode oaNode = null;
		ArrayList<TreeNode> oakNode = new ArrayList<TreeNode>();
		
		boolean foundOD = false;
		boolean foundODK = false;
		
		TreeNode odNode = null;
		ArrayList<TreeNode> odkNode = new ArrayList<TreeNode>();
		
		boolean foundOG = false;
		boolean foundOGK = false;
		
		TreeNode ogNode = null;
		ArrayList<TreeNode> ogkNode = new ArrayList<TreeNode>();
		
		boolean foundOpp = false;
		boolean foundOppK = false;
		
		TreeNode oppNode = null;
		ArrayList<TreeNode> oppKNode = new ArrayList<TreeNode>();
		
		boolean foundFopp = false;
		boolean foundFoppK = false;
		
		TreeNode foppNode = null;
		ArrayList<TreeNode> foppKNode = new ArrayList<TreeNode>();
		
		boolean foundOs = false;
		boolean foundOsK = false;
		
		TreeNode osNode = null;
		ArrayList<TreeNode> osKNode = new ArrayList<TreeNode>();
		
		boolean foundOadvp = false;
		boolean foundOadvpK = false;
		
		TreeNode oadvpNode = null;
		ArrayList<TreeNode> oadvpKNode = new ArrayList<TreeNode>();
		
		boolean foundOaMod = false;
		boolean foundOaModK = false;
		
		TreeNode oaModNode = null;
		ArrayList<TreeNode> oaModKNode = new ArrayList<TreeNode>();
		
		boolean foundVMod = false;
		boolean foundVModK = false;
		
		TreeNode vModNode = null;
		ArrayList<TreeNode> vModKNode = new ArrayList<TreeNode>();
		
		boolean foundMod = false;
		boolean foundModK = false;
		
		TreeNode modNode = null;
		ArrayList<TreeNode> modKNode = new ArrayList<TreeNode>();
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			if (currentSubNode.getDependency().equals("ON"))
			{
				foundON = true;
				onNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("ONK"))
			{
				foundONK = true;
				onkNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OA"))
			{
				foundOA = true;
				oaNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OAK"))
			{
				foundOAK = true;
				oakNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OD"))
			{
				foundOD = true;
				odNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("ODK"))
			{
				foundODK = true;
				odkNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OG"))
			{
				foundOG = true;
				ogNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OGK"))
			{
				foundOGK = true;
				ogkNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OS"))
			{
				foundOs = true;
				osNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OSK"))
			{
				foundOsK = true;
				osKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OPP"))
			{
				foundOpp = true;
				oppNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OPPK"))
			{
				foundOppK = true;
				oppKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("FOPP"))
			{
				foundFopp = true;
				foppNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("FOPPK"))
			{
				foundFoppK = true;
				foppKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OADVP"))
			{
				foundOadvp = true;
				oadvpNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OADVPK"))
			{
				foundOadvpK = true;
				oadvpKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OA-MOD"))
			{
				foundOaMod = true;
				oaModNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OA-MODK"))
			{
				foundOaModK = true;
				oaModKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("V-MOD"))
			{
				foundVMod = true;
				vModNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("V-MODK"))
			{
				foundVModK = true;
				vModKNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("MOD"))
			{
				foundMod = true;
				modNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("MODK"))
			{
				foundModK = true;
				modKNode.add(currentSubNode);
			}
		}
		
		if (foundON && foundONK)
		{
			for (int i=0; i<onkNode.size(); i++)
			{
				TreeNode current = onkNode.get(i);
				onNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOA && foundOAK)
		{
			for (int i=0; i<oakNode.size(); i++)
			{
				TreeNode current = oakNode.get(i);
				oaNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOD && foundODK)
		{
			for (int i=0; i<odkNode.size(); i++)
			{
				TreeNode current = odkNode.get(i);
				odNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOG && foundOGK)
		{
			for (int i=0; i<ogkNode.size(); i++)
			{
				TreeNode current = ogkNode.get(i);
				ogNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOpp && foundOppK)
		{
			for (int i=0; i<oppKNode.size(); i++)
			{
				TreeNode current = oppKNode.get(i);
				oppNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundFopp && foundFoppK)
		{
			for (int i=0; i<foppKNode.size(); i++)
			{
				TreeNode current = foppKNode.get(i);
				foppNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOs && foundOsK)
		{
			for (int i=0; i<osKNode.size(); i++)
			{
				TreeNode current = osKNode.get(i);
				osNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOadvp && foundOadvpK)
		{
			for (int i=0; i<oadvpKNode.size(); i++)
			{
				TreeNode current = oadvpKNode.get(i);
				oadvpNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOaMod && foundOaModK)
		{
			for (int i=0; i<oaModKNode.size(); i++)
			{
				TreeNode current = oaModKNode.get(i);
				oaModNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundMod && foundModK)
		{
			for (int i=0; i<modKNode.size(); i++)
			{
				TreeNode current = modKNode.get(i);
				modNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundVMod && foundVModK)
		{
			for (int i=0; i<vModKNode.size(); i++)
			{
				TreeNode current = vModKNode.get(i);
				vModNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			setConjunct(subNodes.get(i));
		}
		
		return transformedNode;
	}
	
	// Add ON-MOD as child of ON, etc.
	private static TreeNode setMod(TreeNode node)
	{
		TreeNode transformedNode = node;
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		
		boolean foundON = false;
		boolean foundONMOD = false;
		
		TreeNode onNode = null;
		ArrayList<TreeNode> onmodNode = new ArrayList<TreeNode>();
		
		boolean foundOA = false;
		boolean foundOAMOD = false;
		
		TreeNode oaNode = null;
		ArrayList<TreeNode> oamodNode = new ArrayList<TreeNode>();
		
		boolean foundOD = false;
		boolean foundODMOD = false;
		
		TreeNode odNode = null;
		ArrayList<TreeNode> odmodNode = new ArrayList<TreeNode>();
		
		boolean foundOG = false;
		boolean foundOGMOD = false;
		
		TreeNode ogNode = null;
		ArrayList<TreeNode> ogmodNode = new ArrayList<TreeNode>();
		
		boolean foundOadjp = false;
		boolean foundOadjpMOD = false;
		
		TreeNode oadjpNode = null;
		ArrayList<TreeNode> oadjpmodNode = new ArrayList<TreeNode>();
		
		boolean foundOadvp = false;
		boolean foundOadvpMOD = false;
		
		TreeNode oadvpNode = null;
		ArrayList<TreeNode> oadvpmodNode = new ArrayList<TreeNode>();
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			if (currentSubNode.getDependency().equals("ON"))
			{
				foundON = true;
				onNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("ON-MOD"))
			{
				foundONMOD = true;
				onmodNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OA"))
			{
				foundOA = true;
				oaNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OA-MOD"))
			{
				foundOAMOD = true;
				oamodNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OD"))
			{
				foundOD = true;
				odNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OD-MOD"))
			{
				foundODMOD = true;
				odmodNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OG"))
			{
				foundOG = true;
				ogNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OG-MOD"))
			{
				foundOGMOD = true;
				ogmodNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OADVP"))
			{
				foundOadvp = true;
				oadvpNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OADVP-MO"))
			{
				foundOadvpMOD = true;
				oadvpmodNode.add(currentSubNode);
			}
			else if (currentSubNode.getDependency().equals("OADJP"))
			{
				foundOadjp = true;
				oadjpNode = currentSubNode;
			}
			else if (currentSubNode.getDependency().equals("OADJP-MO"))
			{
				foundOadjpMOD = true;
				oadjpmodNode.add(currentSubNode);
			}
		}
		
		if (foundON && foundONMOD)
		{
			for (int i=0; i<onmodNode.size(); i++)
			{
				TreeNode current = onmodNode.get(i);
				onNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOA && foundOAMOD)
		{
			for (int i=0; i<oamodNode.size(); i++)
			{
				TreeNode current = oamodNode.get(i);
				oaNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOD && foundODMOD)
		{
			for (int i=0; i<odmodNode.size(); i++)
			{
				TreeNode current = odmodNode.get(i);
				odNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOG && foundOGMOD)
		{
			for (int i=0; i<ogmodNode.size(); i++)
			{
				TreeNode current = ogmodNode.get(i);
				ogNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOadvp && foundOadvpMOD)
		{
			for (int i=0; i<oadvpmodNode.size(); i++)
			{
				TreeNode current = oadvpmodNode.get(i);
				oadvpNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		if (foundOadjp && foundOadjpMOD)
		{
			for (int i=0; i<oadjpmodNode.size(); i++)
			{
				TreeNode current = oadjpmodNode.get(i);
				oadjpNode.addSubNode(current);
				subNodes.remove(current);
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			setMod(subNodes.get(i));
		}
		
		return transformedNode;
	}
	
	// Check whether OS has an internal subject, if so - ccomp
	private static TreeNode identifyCCompXComp(TreeNode node)
	{
		TreeNode transformed = node;
		ArrayList<TreeNode> subNodes = transformed.getSubNodes();
		if ((node.getDependency() != null) && (node.getDependency().equals("OS")))
		{
			boolean hasInternalSubj = false;
			
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				if (currentSubNode.getDependency().equals("ON"))
				{
					hasInternalSubj = true;
					break;
				}
			}
			if (!hasInternalSubj)
			{
				for (int i=0; i<subNodes.size(); i++)
				{
					TreeNode currentSubNode = subNodes.get(i);
					if (currentSubNode.getDependency().equals("KONJ"))
					{
						hasInternalSubj = checkForOn(currentSubNode);
						if (hasInternalSubj)
							break;
					}
				}
			}
			if (hasInternalSubj)
			{
				transformed.setDependency("OSC");
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			identifyCCompXComp(subNodes.get(i));
		}
		
		return transformed;
	}
	
	private static boolean checkForOn (TreeNode node)
	{
		ArrayList<TreeNode> subNodes = node.getSubNodes();
		boolean hasInternalSubj = false;
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			if (currentSubNode.getDependency().equals("ON"))
			{
				hasInternalSubj = true;
				break;
			}
		}
		if (!hasInternalSubj)
		{
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				if (currentSubNode.getDependency().equals("KONJ"))
				{
					hasInternalSubj = checkForOn(currentSubNode);
					if (hasInternalSubj)
						break;
				}
			}
		}
		return hasInternalSubj;
	}
	
	private static TreeNode transformDependencies(TreeNode node, boolean start)
	{
		TreeNode transformedNode = node;
		ArrayList<TreeNode> subNodes = transformedNode.getSubNodes();
		ArrayList<TreeWord> subWords = transformedNode.getWords();
		ArrayList<RelationTriplet> relationTriplets = new ArrayList<RelationTriplet>();
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode= subNodes.get(i);
			String function = currentSubNode.getDependency();
			String nodeName = currentSubNode.getCategory();
			ArrayList<DependencyNode> heads = headWordFinder(currentSubNode);
			DependencyNode currentHead = heads.get(0);
			String headPos = currentHead.getWord().getPos();
			RelationTriplet newTriplet = new RelationTriplet(function, nodeName, headPos);
			relationTriplets.add(newTriplet);
			transformDependencies(currentSubNode, false);
		}
		for (int i=0; i<subWords.size(); i++)
		{
			TreeWord currentWord = subWords.get(i);
			String function = currentWord.getDependency();
			String nodeName = "WORD";
			String pos = currentWord.getPos();
			RelationTriplet newTriplet = new RelationTriplet(function, nodeName, pos);
			relationTriplets.add(newTriplet);
		}
		
		ArrayList<String> newDeps = structureTransformerInstance.convertDependencies(relationTriplets);
		boolean hasHD = false;
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode= subNodes.get(i);
			currentSubNode.setDependency(newDeps.get(i));
			if (newDeps.get(i).equals("HD"))
			{
				hasHD = true;
			}
		}
		int newDepsIndex = subNodes.size();
		for (int i=0; i<subWords.size(); i++)
		{
			TreeWord currentWord = subWords.get(i);
			currentWord.setDependency(newDeps.get(newDepsIndex));
			if (newDeps.get(newDepsIndex).equals("HD"))
			{
				hasHD = true;
			}
			newDepsIndex++;
		}
		
		if (!hasHD && !start)
		{
			//Find leftmost non-punctuation element
			int leftmost = 9999999;
			boolean leftMostIsNode = false;
			TreeNode leftNode = null;
			TreeWord leftWord = null;
			
			for (int i=0; i<subWords.size(); i++)
			{
				TreeWord currentWord = subWords.get(i);
				if ((currentWord.getWordNumber() < leftmost) && !(currentWord.getPos().contains("$")))
				{
					leftmost = currentWord.getWordNumber();
					leftWord = currentWord;
				}
			}
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentNode = subNodes.get(i);
				int currentNodeNumber = headWordFinder(currentNode).get(0).getWordNumber();
				if (currentNodeNumber < leftmost)
				{
					leftmost = currentNodeNumber;
					leftNode = currentNode;
					leftMostIsNode = true;
				}
			}
			if (leftMostIsNode)
			{
				leftNode.setDependency("HD");
			}
			else if (leftWord != null)
			{
				leftWord.setDependency("HD");
			}
		}
		
		return transformedNode;
	}
	
	private static DependencyNode extractDepStructure(TreeNode treeNode, boolean start, DependencyNode headNode)
	{
		DependencyNode current = null;
		ArrayList<TreeNode> subNodes = treeNode.getSubNodes();
		ArrayList<TreeWord> words = treeNode.getWords();
		String depRel = treeNode.getDependency();
		
		if (start)
		{
			current = new DependencyNode("ROOT", null, "N/A", null, "");
			current.setSubNodes(headWordFinder(treeNode));
			ArrayList<DependencyNode> rootDependents = current.getSubNodes();
			
			//Set dependency relation for all immediate children of the root to "root"
			for (int i=0; i<rootDependents.size(); i++)
			{
				DependencyNode currentDepSubNode = rootDependents.get(i);
				if (!(currentDepSubNode.getNodeData().get("pos").contains("$")))
				{
					currentDepSubNode.setRel("root");
				}
				else
				{
					currentDepSubNode.setRel("PUNCT");
				}
			}
			
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode currentSubNode = subNodes.get(i);
				extractDepStructure(currentSubNode, false, current);
			}
		}
		else
		{
			ArrayList<DependencyNode> heads = headWordFinder(treeNode);
			if (heads.size() > 1)
			{
				System.out.println("MULTIPLE HEAD ERROR");
			}
			else if (heads.size() == 1)
			{
				current = heads.get(0);
				for (int i=0; i<subNodes.size(); i++)
				{
					TreeNode currentSubNode = subNodes.get(i);
					ArrayList<DependencyNode> currentSubNodeHeads = headWordFinder(currentSubNode);
					if (!((currentSubNodeHeads.size() > 1) || (currentSubNodeHeads.contains(current))))
					{
						current.addDependent(extractDepStructure(currentSubNode, false, current));
					}
					else
					{
						extractDepStructure(currentSubNode, false, current);
					}
				}
				for (int i=0; i<words.size(); i++)
				{
					TreeWord currentWord = words.get(i);
					DependencyNode currentWordDepNode = currentWord.getDepNode();
					if (currentWordDepNode.getRel().equals(""))
					{
						currentWordDepNode.setRel(currentWord.getDependency());
					}
					if((!(current.getSubNodes().contains(currentWordDepNode))) && (!(current.equals(currentWordDepNode))))
					{
						current.addDependent(currentWordDepNode);
					}
				}
			}
		}
		if (!(current.getRel().equals("root")))
		{
			current.setRel(depRel);
		}
		return current;
	}
	
	private static DependencyNode convertPos(DependencyNode node)
	{
		DependencyNode converted = node;
		ArrayList<DependencyNode> subNodes = converted.getSubNodes();
		for (int i=0; i<subNodes.size(); i++)
		{
			convertPos(subNodes.get(i));
		}
		HashMap<String, String> conversionTable = posConverterInstance.getPosMap();
		String currentPos = converted.getNodeData().get("pos");
		String newPos = conversionTable.get(currentPos);
		converted.setPos(newPos);
		return converted;
	}
	
	private static DependencyNode setHead(DependencyNode node)
	{
		DependencyNode converted = node;
		ArrayList<DependencyNode> subNodes = converted.getSubNodes();
		for (int i=0; i<subNodes.size(); i++)
		{
			subNodes.get(i).setHead(converted);
			setHead(subNodes.get(i));
		}
		return converted;
	}
	
	// Check for sentences that have indirect objects but no direct objects and change the "iobj" dependency to "obj"
	private static DependencyNode setObjects(DependencyNode node)
	{
		DependencyNode converted = node;
		ArrayList<DependencyNode> subNodes = converted.getSubNodes();
		ArrayList<String> deps = new ArrayList<String>();
		boolean foundObj = false;
		boolean foundDObj = false;
		boolean foundGObj = false;
		
		DependencyNode obj = null;
		DependencyNode gobj = null;
		
		for (int i=0; i<subNodes.size(); i++)
		{
			String currentRel = subNodes.get(i).getRel();
			deps.add(subNodes.get(i).getRel());
			if (currentRel.equals("obj"))
			{
				foundObj = true;
				break;
			}
			else if (currentRel.equals("iobj"))
			{
				if (subNodes.get(i).getMorphCase().equals("Dat") && !foundDObj)
				{
					foundDObj = true;
					obj = subNodes.get(i);
				}
				else if (subNodes.get(i).getMorphCase().equals("Gen") && !foundGObj)
				{
					foundGObj = true;
					gobj = subNodes.get(i);
				}
			}
		}
		
		if (!foundObj)
		{
			if (foundDObj)
			{
				obj.setRel("obj");
			}
			else if (foundGObj)
			{
				gobj.setRel("obj");
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			setObjects(subNodes.get(i));
		}
		
		return converted;
	}
	
	private static HashMap<Integer, DependencyNode> putNodesInOrder(DependencyNode node, HashMap<Integer, DependencyNode> sentence)
	{
		ArrayList<DependencyNode> subNodes = node.getSubNodes();
		int currentNumber = node.getWordNumber();
		sentence.put(currentNumber, node);
		
		for (int i=0; i<subNodes.size(); i++)
		{
			DependencyNode currentSubNode = subNodes.get(i);
			putNodesInOrder(currentSubNode, sentence);
		}
		
		return sentence;
	}
	
	//Break up APPRART (im, zum, etc.) into preposition and article
	private static void breakUpApprArt(ArrayList<DependencyNode> sentence)
	{
		ArrayList<DependencyNode> newSentence = sentence;
		for (int i=1; i<newSentence.size(); i++)
		{
			DependencyNode currentNode = newSentence.get(i);
			if ((currentNode.getNodeData().get("pos") != null) && (currentNode.getNodeData().get("pos").equals("APPRART")))
			{
				String form = currentNode.getNodeData().get("form");
				String prep = currentNode.getNodeData().get("lemma");
				String art = null;
				String artLemma = null;
				boolean canBeBrokenUp = false;
				
				if (form.endsWith("m"))
				{
					art = "dem";
					String morph = currentNode.getNodeData().get("morph");
					if (morph.endsWith("n"))
					{
						artLemma = "das";
					}
					else
					{
						artLemma = "der";
					}
					canBeBrokenUp = true;
				}
				else if (form.endsWith("r"))
				{
					art = "der";
					artLemma = "die";
					canBeBrokenUp = true;
				}
				else if (form.endsWith("s"))
				{
					art = "das";
					artLemma = "das";
					canBeBrokenUp = true;
				}
				else if (form.endsWith("n"))
				{
					art = "den";
					artLemma = "der";
					canBeBrokenUp = true;
				}
				else
				{
					currentNode.setRel("case");
				}
				
				if (canBeBrokenUp)
				{
					currentNode.setApprArt(true);
					currentNode.setApprArtForm(form);
					
					DependencyNode nodeArt = new DependencyNode(currentNode.getLine(), currentNode.getHead(), "det", null, "");
					nodeArt.getNodeData().put("lemma", artLemma);
					nodeArt.setLemma(artLemma);
					nodeArt.getNodeData().put("form", art);
					nodeArt.setPos("DET");
					nodeArt.getNodeData().put("pos", "ART");
					nodeArt.getNodeData().put("morph", currentNode.getNodeData().get("morph"));
					nodeArt.setArtMorphInfo(currentNode.getNodeData().get("morph"));
					nodeArt.setTopoField(currentNode.getTopoField());
					
					nodeArt.getHead().addDependent(nodeArt);
					
					currentNode.getNodeData().put("form", prep);
					currentNode.setRel("case");
					currentNode.setPos("ADP");
					currentNode.getNodeData().put("pos", "APPR");
					
					newSentence.add(i+1, nodeArt);
				}
			}
		}
	}
	
	// Change CC dependencies to preceding element
	private static void setCCDependencies(ArrayList<DependencyNode> sentence, DependencyNode node)
	{
		DependencyNode transformed = node;
		ArrayList<DependencyNode> subNodes = transformed.getSubNodes();
		
		if (node.getRel() != null && subNodes.size() > 0 && node.getRel().equals("conj"))
		{
			//Find subordinate clause extremities
			int leftExtremity = node.getWordNumber();
			int rightExtremity = node.getWordNumber();
			
			int leftTest = getExtremity(true, node);
			int rightTest = getExtremity(false, node);
			
			if (rightTest > rightExtremity)
				rightExtremity = rightTest;
			if (leftTest < leftExtremity)
				leftExtremity = leftTest;
			
			DependencyNode head = getHeadBetweenIndices(leftExtremity, rightExtremity, sentence);
			
			//Check for cc to the left
			if (leftExtremity > 0)
			{
				if (sentence.get(leftExtremity - 1).getPos() != null && sentence.get(leftExtremity - 1).getRel().equals("cc"))
				{
					sentence.get(leftExtremity - 1).setHead(head);
				}
				else
				{
					int i=1;
					while ((leftExtremity - i - 1 > 0) && sentence.get(leftExtremity - i).getPos() != null && sentence.get(leftExtremity - i).getNodeData().get("pos").equals("$("))
					{
						if (sentence.get(leftExtremity - i - 1).getPos() != null && sentence.get(leftExtremity - i - 1).getRel().equals("cc"))
						{
							sentence.get(leftExtremity - i - 1).setHead(head);
							break;
						}
						i++;
					}
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			setCCDependencies(sentence, subNodes.get(i));
		}
	}
	
	private static void setPunctuationDependencies(ArrayList<DependencyNode> sentence, DependencyNode root)
	{
		//Check subordinate clauses
		setSubClauseDependencies(sentence, root);
		
		//Check sentence internal
		setSentenceInternalDependencies(sentence);
		
		//Check cc,conj
		setConjPunctDependencies(sentence);
		
		//Add to head of sentence
		setPunctDepToHead(sentence);
	}
	
	//Set remaining punct nodes to have dependency as head of sentence
	private static void setPunctDepToHead(ArrayList<DependencyNode> sentence)
	{
		for (int i=0; i<sentence.size(); i++)
		{
			DependencyNode current = sentence.get(i);
			if (current.getPos() != null && current.getNodeData().get("pos").contains("$") && (current.getRel().equals("PUNCT") || current.getRel().equals("REPLACEME")))
			{
				DependencyNode head = null;
				if (i > 1)
				{
					for (int j=i-1; j>0; j--)
					{
						DependencyNode currentHeadCheck = sentence.get(j);
						if (!(current.getPos() == null || currentHeadCheck.getNodeData().get("pos").contains("$")))
						{
							head = getTopLevelHead(j, sentence);
							break;
						}
					}
					if (head == null)
					{
						head = getHeadBetweenIndices(1, sentence.size()-1, sentence);
					}
				}
				else
				{
					head = getHeadBetweenIndices(1, sentence.size()-1, sentence);
				}
				current.setHead(head);
				current.setRel("punct");
			}
		}
	}
	
	private static void setConjPunctDependencies(ArrayList<DependencyNode> sentence)
	{
		for (int i=0; i<sentence.size(); i++)
		{
			DependencyNode current = sentence.get(i);
			if (current.getPos() != null && current.getNodeData().get("pos").equals("$,") && (current.getRel().equals("PUNCT") || current.getRel().equals("REPLACEME")))
			{
				if (i+1 < sentence.size())
				{
					DependencyNode checkConj = sentence.get(i+1);
					if (checkConj.getRel().equals("conj"))
					{
						current.setHead(checkConj);
						current.setRel("punct");
					}
					else if (checkConj.getRel().equals("cc"))
					{
						current.setHead(checkConj.getHead());
						current.setRel("punct");
					}
				}
			}
		}
	}
	
	private static void setSentenceInternalDependencies(ArrayList<DependencyNode> sentence)
	{
		for (int i=0; i<sentence.size(); i++)
		{
			DependencyNode current = sentence.get(i);
			String form = current.getNodeData().get("form");
			
			if (current.getPos() != null && (current.getNodeData().get("pos").equals("$(") && current.getRel().equals("PUNCT") || current.getRel().equals("REPLACEME")))
			{
				int leftExtreme = i;
				int rightExtreme = 0;
				
				if (form.equals("\""))
				{
					rightExtreme = findPaired(leftExtreme, "\"", sentence);
				}
				else if (form.equals("'"))
				{
					rightExtreme = findPaired(leftExtreme, "'", sentence);
				}
				else if (form.equals("("))
				{
					rightExtreme = findPaired(leftExtreme, ")", sentence);
				}
				else if (form.equals("["))
				{
					rightExtreme = findPaired(leftExtreme, "]", sentence);
				}
				
				if (rightExtreme > 0)
				{
					DependencyNode head = getHeadBetweenIndices(leftExtreme, rightExtreme, sentence);
					sentence.get(leftExtreme).setHead(head);
					sentence.get(rightExtreme).setHead(head);
					sentence.get(leftExtreme).setRel("punct");
					sentence.get(rightExtreme).setRel("punct");
				}
			}
		}
	}
	
	private static int findPaired(int start, String match, ArrayList<DependencyNode> sentence)
	{
		int matchPosition = 0;
		for (int i=start+1; i<sentence.size(); i++)
		{
			DependencyNode currentNode = sentence.get(i);
			if (currentNode.getLemma().equals(match))
			{
				matchPosition = i;
				break;
			}
		}
		return matchPosition;
	}
	
	private static void setSubClauseDependencies(ArrayList<DependencyNode> sentence, DependencyNode node)
	{
		DependencyNode transformed = node;
		ArrayList<DependencyNode> subNodes = transformed.getSubNodes();
		
		if (node.getRel() != null && subNodes.size() > 0 && (node.getRel().equals("xcomp") || node.getRel().equals("ccomp") || node.getRel().equals("acl") || node.getRel().equals("acl:relcl") 
				 || node.getRel().equals("advcl") || node.getRel().equals("csubj")))
		{
			//Find subordinate clause extremities
			int leftExtremity = node.getWordNumber();
			int rightExtremity = node.getWordNumber();
			
			int leftTest = getExtremity(true, node);
			int rightTest = getExtremity(false, node);
			
			if (rightTest > rightExtremity)
				rightExtremity = rightTest;
			if (leftTest < leftExtremity)
				leftExtremity = leftTest;
			
			DependencyNode head = getHeadBetweenIndices(leftExtremity, rightExtremity, sentence);
			
			//Check for commas to the left and right
			if (leftExtremity > 0)
			{
				if (sentence.get(leftExtremity - 1).getPos() != null && sentence.get(leftExtremity - 1).getNodeData().get("pos").equals("$,"))
				{
					sentence.get(leftExtremity - 1).setHead(head);
					sentence.get(leftExtremity - 1).setRel("punct");
				}
				else
				{
					int i=1;
					while ((leftExtremity - i - 1 > 0) && sentence.get(leftExtremity - i).getPos() != null && sentence.get(leftExtremity - i).getNodeData().get("pos").equals("$("))
					{
						if (sentence.get(leftExtremity - i - 1).getPos() != null && sentence.get(leftExtremity - i - 1).getNodeData().get("pos").equals("$,"))
						{
							sentence.get(leftExtremity - i - 1).setHead(head);
							sentence.get(leftExtremity - i - 1).setRel("punct");
							break;
						}
						i++;
					}
				}
			}
			if (rightExtremity < sentence.size() - 1 && !(node.getRel().equals("conj")))
			{
				if (sentence.get(rightExtremity + 1).getPos() != null && sentence.get(rightExtremity + 1).getNodeData().get("pos").equals("$,"))
				{
					sentence.get(rightExtremity + 1).setHead(head);
					sentence.get(rightExtremity + 1).setRel("punct");
				}
				else
				{
					int i=1;
					while ((rightExtremity + i + 1 < sentence.size()) && sentence.get(rightExtremity + i).getPos() != null && sentence.get(rightExtremity + i).getNodeData().get("pos").equals("$("))
					{
						if (sentence.get(rightExtremity + i + 1).getPos() != null && sentence.get(rightExtremity + i + 1).getNodeData().get("pos").equals("$,"))
						{
							sentence.get(rightExtremity + i + 1).setHead(head);
							sentence.get(rightExtremity + i + 1).setRel("punct");
							break;
						}
						i++;
					}
				}
			}
		}
		
		for (int i=0; i<subNodes.size(); i++)
		{
			setSubClauseDependencies(sentence, subNodes.get(i));
		}
	}
	
	private static int getExtremity(boolean left, DependencyNode node)
	{
		int extremity = 0;
		if (left)
			extremity = 9999999;
		
		ArrayList<DependencyNode> subNodes = node.getSubNodes();
		for (int i=0; i<subNodes.size(); i++)
		{
			DependencyNode currentSubNode = subNodes.get(i);
			int wordNumber = currentSubNode.getWordNumber();
			String pos = currentSubNode.getNodeData().get("pos");
			
			if (!(pos == null || pos.equals("$,") || pos.equals("$.")))
			{
				if ((wordNumber > extremity) && !left)
					extremity = wordNumber;
				else if (wordNumber < extremity  && left)
					extremity = wordNumber;
			}
			
			int test = getExtremity(left, currentSubNode);
			
			if ((test > extremity) && !left)
				extremity = test;
			else if ((test < extremity) && left)
				extremity = test;
		}
		
		return extremity;
	}
	
	private static DependencyNode getHeadBetweenIndices(int leftExtreme, int rightExtreme, ArrayList<DependencyNode> sentence)
	{	
		while (sentence.get(leftExtreme).getNodeData().get("pos").contains("$"))
		{
			leftExtreme++;
			if (leftExtreme >= sentence.size())
			{
				return(sentence.get(rightExtreme - 1));
			}
		}
		
		DependencyNode test = sentence.get(leftExtreme);
		DependencyNode head = null;
		
		while ((test.getWordNumber() >= leftExtreme) && (test.getWordNumber() <= rightExtreme) && !(test.getLine().equals("ROOT")))
		{
			head = test;
			test = head.getHead();
		}
		
		return head;
	}
	
	private static DependencyNode getTopLevelHead(int nodePosition, ArrayList<DependencyNode> sentence)
	{
		DependencyNode test = sentence.get(nodePosition);
		DependencyNode head = null;
		
		while (!(test.getLine().equals("ROOT")))
		{
			head = test;
			test = head.getHead();
		}
		
		return head;
	}
	
	private static ArrayList<ArrayList<String>> convertNodesToText(ArrayList<DependencyNode> sentence)
	{
		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		boolean foundFirstRoot = false;
		String firstRootNum = "";
		boolean openDoubleQuote = false;
		boolean allPunctuation = true;
		
		//Check for all punctuation sentence
		for (int i=0; i<sentence.size(); i++)
		{
			DependencyNode node = sentence.get(i);
			String upostag = node.getPos();
			if (upostag != null && !(upostag.equals("PUNCT")))
			{
				allPunctuation = false;
				break;
			}
		}
		
		for (int i=0; i<sentence.size(); i++)
		{
			ArrayList<String> columns = new ArrayList<String>();
			DependencyNode node = sentence.get(i);
			String wordIndex = Integer.toString(node.getWordNumber());
			String form = node.getNodeData().get("form");
			String lemma = node.getLemma();
			String upostag = node.getPos();
			String xpostag = node.getNodeData().get("pos");
			String feats = "";
			String head = "0";
			String topoField = node.getTopoField();
			String namedEntity = node.getTotalNamedEntity();
			String morph = node.getMorph();
			String typo = node.getTypo();
			String wsd = node.getWsd();
			
			String nextPosTag = "";
			String nextForm = "";
			boolean spaceAfter = true;
			boolean nummodTrunc = false;
			boolean discourse = node.isDiscourse();
			
  			if (!(lemma.contains("%")) && upostag != null && upostag.equals("AUX") && !(lemma.contains("sein")))
  			{
  				upostag = "VERB";
  			}
  			else if (lemma.contains("%"))
			{
  				if (xpostag.equals("TRUNC"))
  				{
  					String truncPos = lemma.substring(lemma.length()-2, lemma.length());
  					if (truncPos.equals("%n"))
  					{
  						upostag = "NOUN";
  					}
  					else if (truncPos.equals("%a"))
  					{
  						upostag = "ADJ";
  					}
  					else if (truncPos.equals("%v"))
  					{
  						upostag = "VERB";
  					}
  					else if (truncPos.equals("%c"))
  					{
  						upostag = "NUM";
  						nummodTrunc = true;
  					}
  					else if (truncPos.equals("%p"))
  					{
  						upostag = "ADP";
  					}
  				}
				int index = lemma.indexOf("%");
				if (index > 0)
					lemma = lemma.substring(0, index);
			}
			
			if ((form != null) && !openDoubleQuote && form.equals("\""))
				openDoubleQuote = true;
			else if ((form != null) && openDoubleQuote && form.equals("\""))
				openDoubleQuote = false;
			
			if (i+1 < sentence.size())
			{
				nextPosTag = sentence.get(i+1).getPos();
				nextForm = sentence.get(i+1).getNodeData().get("form");
				
				if ((nextPosTag != null && form != null) && 
						(((nextPosTag.equals("PUNCT")) && (!nextForm.equals("(")) && (!nextForm.equals("[")) && (!(!openDoubleQuote && nextForm.equals("\"")))) ||
						((form.equals("\"") || nextForm.equals("\"")) && openDoubleQuote) ||
						(form.equals("/") || nextForm.equals("/")) ||
						(form.equals("(") || nextForm.equals(")")) ||
						(form.equals("[") || nextForm.equals("]"))))
					spaceAfter = false;
			}
			
			if (!node.getMorphCase().equals(""))
			{
				feats = feats + "Case=" + node.getMorphCase();
			}
			if (!node.getDefinite().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Definite=" + node.getDefinite();
			}
			if (!node.getForeign().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Foreign=" + node.getForeign();
			}
			if (!node.getGender().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Gender=" + node.getGender();
			}
			if (!node.getMood().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Mood=" + node.getMood();
			}
			if (!node.getNumber().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Number=" + node.getNumber();
			}
			if (!node.getNumtype().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "NumType=" + node.getNumtype();
			}
			if (!node.getPerson().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Person=" + node.getPerson();
			}
			if (!node.getPolarity().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Polarity=" + node.getPolarity();
			}
			if (!node.getPolite().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Polite=" + node.getPolite();
			}
			if (!node.getPoss().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Poss=" + node.getPoss();
			}
			if (!node.getPronType().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "PronType=" + node.getPronType();
			}
			if (!node.getReflex().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Reflex=" + node.getReflex();
			}
			if (!node.getTense().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Tense=" + node.getTense();
			}
			if (!node.getVerbForm().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "VerbForm=" + node.getVerbForm();
			}
			if (!node.getVoice().equals(""))
			{
				if (!feats.isEmpty())
					feats = feats + "|";
				feats = feats + "Voice=" + node.getVoice();
			}
			
			if (feats.isEmpty())
				feats = "_";
			
			if (node.getHead() != null)
			{
				head = Integer.toString(node.getHead().getWordNumber());
			}
			String depRel = node.getRel();
			
			if (nummodTrunc)
			{
				depRel = "nummod";
			}
			
			if (depRel != null && foundFirstRoot && depRel.equals("root"))
			{
				depRel = "parataxis";
				head = firstRootNum;
			}
			
			if (depRel != null && depRel.equals("root"))
			{
				foundFirstRoot = true;
				firstRootNum = wordIndex;
			}
			
			//Comment out to see where program could not determine dependencies
			if (depRel != null && (depRel.equals("HD") || depRel.equals("REPLACEME")))
			{
				depRel = "dep";
			}
			
			String deps = "_";
			String misc = "_";
			
			if (!topoField.isEmpty())
				misc = "TopoField=" + topoField;
			
			if (discourse)
			{
				if (misc.equals("_"))
					misc = "DM=Yes";
				else
					misc = "DM=Yes" + "|" + misc;
			}
			
			if (morph != null && !morph.isEmpty())
			{
				if (misc.equals("_"))
					misc = "Morph=" + morph;
				else
					misc = "Morph=" + morph + "|" + misc;
			}
			
			if (!namedEntity.isEmpty())
			{
				if (misc.equals("_"))
					misc = "NE=" + namedEntity;
				else
					misc = "NE=" + namedEntity + "|" + misc;
			}
			
			if (wsd != null && !wsd.isEmpty())
			{
				if (misc.equals("_"))
					misc = "WSD" + wsd;
				else
					misc = "WSD=" + wsd + "|" + misc;
			}
			
			if (!spaceAfter)
			{
				if (misc.equals("_"))
					misc = "SpaceAfter=No";
				else
					misc = "SpaceAfter=No|" + misc;
			}
			
			if (typo != null && !typo.isEmpty())
			{
				if (misc.equals("_"))
					misc = "Corr=" + typo;
				else
					misc = "Corr=" + typo + "|" + misc;
			}
			
			if (allPunctuation)
			{
				if (wordIndex.equals("1"))
				{
					depRel = "root";
					head = "0";
				}
				else
				{
					depRel = "punct";
					head = "1";
				}
			}
				
			columns.add(wordIndex);
			columns.add(form);
			columns.add(lemma);
			columns.add(upostag);
			columns.add(xpostag);
			columns.add(feats);
			columns.add(head);
			columns.add(depRel);
			columns.add(deps);
			columns.add(misc);
			
			lines.add(columns);
		}
		
		return lines;
	}
	
	private static void printSentences(String directory, int start)
	{
		String fileName = directory + "convertedSentences.txt";
		
		BufferedWriter writer = null;
		try
		{
		    writer = new BufferedWriter(new FileWriter(fileName, (start > 1)));
		    
		    for (int i=0; i<conllSentences.size(); i++)
		    {
		    	if (newArticleMap.get(i) != null)
		    	{
		    		writer.write("# newdoc id = " + newArticleMap.get(i));
		    		writer.write("\n");
		    	}
		    	ArrayList<ArrayList<String>> currentSentence = conllSentences.get(i);
		    	writer.write("# sent_id = s" + Integer.toString(i + start));
		    	System.out.println(Integer.toString(i + start));
		    	writer.write("\n");
		    	writer.write("# text = ");
		    	
		    	ArrayList<DependencyNode> currentNodeSentence = arrayOrderedSentences.get(i);
		    	
		    	for (int j=1; j<currentSentence.size(); j++)
		    	{
		    		ArrayList<String> currentWord = currentSentence.get(j);
		    		DependencyNode currentNode = currentNodeSentence.get(j);
		    		String word = currentWord.get(1);
		    		if (currentNode.isApprArt())
		    		{
		    			word = currentNode.getApprArtForm();
		    			j++;
		    			currentWord = currentSentence.get(j);
		    		}
		    		writer.write(word);
		    		
		    		if (!currentWord.get(9).contains("SpaceAfter=No"))
		    		{
		    			writer.write(" ");
		    		}
		    	}
		    	writer.write("\n");
		    	for (int j=1; j<currentSentence.size(); j++)
		    	{
		    		ArrayList<String> currentWord = currentSentence.get(j);
		    		DependencyNode currentNode = currentNodeSentence.get(j);
		    		if (currentNode.isApprArt())
		    		{
		    			String apprArtLine = currentNode.getWordNumber() + "-" + (currentNode.getWordNumber()+1) + "\t" + currentNode.getApprArtForm() + "\t_\t_\t_\t_\t_\t_\t_\t_";
		    			writer.write(apprArtLine);
		    			writer.write("\n");
		    		}
		    		for (int k=0; k<currentWord.size(); k++)
			    	{
			    		String currentFeature = currentWord.get(k);
			    		if (currentFeature == null)
			    			currentFeature = "NULL";
			    		writer.write(currentFeature);
			    		writer.write("\t");
			    	}
		    		writer.write("\n");
		    	}
		    	writer.write("\n");
		    }
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
		    try
		    {
		        if (writer != null)
		        	writer.close();
		    }
		    catch (IOException e)
		    {
		    	e.printStackTrace();
		    }
		}
	}
	
	private static ArrayList<DependencyNode> headWordFinder(TreeNode treeNode)
	{
		ArrayList<DependencyNode> heads = new ArrayList<DependencyNode>();
		ArrayList<TreeNode> subNodes = treeNode.getSubNodes();
		ArrayList<TreeWord> words = treeNode.getWords();
		boolean headFound = false;
		
		for (int i=0; i<words.size(); i++)
		{
			TreeWord current = words.get(i);
			String dependency = current.getDependency();
			if (dependency.equals("HD"))
			{
				heads.add(current.getDepNode());
				headFound = true;
				break;
			}
		}
		if (!headFound)
		{
			for (int i=0; i<subNodes.size(); i++)
			{
				TreeNode current = subNodes.get(i);
				String dependency = current.getDependency();
				if (dependency.equals("HD"))
				{
					heads = headWordFinder(current);
					headFound = true;
					break;
				}
			}
			if (!headFound)
			{
				for (int i=0; i<words.size(); i++)
				{
					TreeWord current = words.get(i);
					String dependency = current.getDependency();
					if (dependency.equals("VC-HD"))
					{
						heads.add(current.getDepNode());
						headFound = true;
						break;
					}
				}
				if (!headFound)
				{
					for (int i=0; i<subNodes.size(); i++)
					{
						TreeNode current = subNodes.get(i);
						String dependency = current.getDependency();
						if (dependency.equals("VC-HD"))
						{
							heads = headWordFinder(current);
							headFound = true;
							break;
						}
					}
				}
				if (!headFound)
				{
					for (int i=0; i<subNodes.size(); i++)
					{
						TreeNode current = subNodes.get(i);
						heads.addAll(headWordFinder(current));
					}
					for (int i=0; i<words.size(); i++)
					{
						TreeWord current = words.get(i);
						heads.add(current.getDepNode());
					}
				}
			}
		}
		
		return heads;
	}
	
	private static ArrayList<ArrayList<String>> readExportFile(String path, int start, int end)
	{
		BufferedReader br = null;
		ArrayList<ArrayList<String>> sentences = new ArrayList<ArrayList<String>>();

		try
		{

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));
			ArrayList<String> currentSentence = new ArrayList<String>();
			
			if (start == 1)
			{
				while (!((sCurrentLine = br.readLine()).contains("<body")));
			}
			else
			{
				while (!((sCurrentLine = br.readLine()).contains("<sentence xml:id=\"s" + Integer.toString(start) + "\">")));
				currentSentence.add(sCurrentLine);
			}
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if (sCurrentLine.contains("</body>") || sCurrentLine.contains("<sentence xml:id=\"s" + Integer.toString(end + 1) + "\">"))
				{
					break;
				}
				//else if (!(sCurrentLine.contains("</text>") || sCurrentLine.contains("<text")))
				else if (!(sCurrentLine.contains("</text>")))
				{
					currentSentence.add(sCurrentLine);
					if (sCurrentLine.contains("</sentence"))
					{
						sentences.add(currentSentence);
						currentSentence = new ArrayList<String>();
					}
				}
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
		return sentences;
	}
	
	private static TreeNode makeDeepCopy(TreeNode node)
	{
		TreeNode copyNode = node;
		try 
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(node);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			try 
			{
				copyNode = (TreeNode) new ObjectInputStream(bais).readObject();
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return copyNode;
	}
}