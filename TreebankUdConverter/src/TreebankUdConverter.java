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

public class TreebankUdConverter 
{
	private static ArrayList<DependencyNode> dependencySentences;
	private static ArrayList<ArrayList<String>> treebankLines;
	private static String path = "/Users/bcmpbell/Documents/TestSentence2.txt";
	private static ArrayList<TreeNode> sentenceNodes;
	private static ArrayList<TreeNode> sentenceNodesFieldModified;
	private static ArrayList<TreeNode> sentenceNodesClipped;
	private static ArrayList<TreeNode> sentenceNodesFunctionDetermined;
	private static ArrayList<TreeNode> sentenceNodesTransformed;
	private static ArrayList<ArrayList<ArrayList<String>>> conllSentences;
	private static StructureTransformer structureTransformerInstance;
	private static PosConverter posConverterInstance;
	private static int sentenceIndex = 0;
	
	public static void main(String[] args) 
	{
		treebankLines = readExportFile(path);
		sentenceNodes = new ArrayList<TreeNode>();
		sentenceNodesFieldModified = new ArrayList<TreeNode>();
		sentenceNodesClipped = new ArrayList<TreeNode>();
		sentenceNodesFunctionDetermined = new ArrayList<TreeNode>(); //*
		sentenceNodesTransformed = new ArrayList<TreeNode>();
		dependencySentences = new ArrayList<DependencyNode>();
		conllSentences = new ArrayList<ArrayList<ArrayList<String>>>();
		structureTransformerInstance = StructureTransformer.getInstance();
		posConverterInstance = PosConverter.getInstance();
		
		for (int i=0; i<treebankLines.size(); i++)
		{
			sentenceIndex = 0;
			TreeNode currentSentence = treeBuilder(treebankLines.get(i), i);
			sentenceNodes.add(currentSentence);
		}
		
		sentenceIndex = 0;
		
		for (int i=0; i<sentenceNodes.size(); i++)
		{
			TreeNode currentNode = makeDeepCopy(sentenceNodes.get(i));
			TreeNode fKoordModNode = topoFieldModifier(currentNode);
			sentenceNodesFieldModified.add(fKoordModNode);
		}
		
		for (int i=0; i<sentenceNodesFieldModified.size(); i++)
		{
			TreeNode currentNode = makeDeepCopy(sentenceNodesFieldModified.get(i));
			TreeNode clippedNode = clipTree(currentNode);
			sentenceNodesClipped.add(clippedNode);
		}
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			TreeNode currentNode = sentenceNodesClipped.get(i);
			zuLabeler(currentNode);
		}
		
		for (int i=0; i<sentenceNodesClipped.size(); i++)
		{
			TreeNode currentNode = makeDeepCopy(sentenceNodesClipped.get(i));
			sentenceNodesFunctionDetermined.add(functionDeterminer(currentNode));
		}
		
		TreeNode test = sentenceNodes.get(0);
		TreeNode test1 = sentenceNodesFieldModified.get(0);
		TreeNode test2 = sentenceNodesClipped.get(0);
		TreeNode test4 = sentenceNodesFunctionDetermined.get(0);
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			transformFKONJ(currentNode);
		}
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			removeKonj1(currentNode);
		}
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = sentenceNodesFunctionDetermined.get(i);
			setKonj2Head(currentNode);
		}
		
		for (int i=0; i<sentenceNodesFunctionDetermined.size(); i++)
		{
			TreeNode currentNode = makeDeepCopy(sentenceNodesFunctionDetermined.get(i));
			sentenceNodesTransformed.add(transformDependencies(currentNode));
		}
		
		for (int i=0; i<sentenceNodesTransformed.size(); i++)
		{
			TreeNode currentNode = makeDeepCopy(sentenceNodesTransformed.get(i));
			dependencySentences.add(extractDepStructure(currentNode, true, null));
		}
		
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			convertPos(currentNode);
		}
		
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			setHead(currentNode);
		}
		
		ArrayList<HashMap<Integer, DependencyNode>> orderedSentences = new ArrayList<HashMap<Integer, DependencyNode>>();
		for (int i=0; i<dependencySentences.size(); i++)
		{
			DependencyNode currentNode = dependencySentences.get(i);
			HashMap<Integer, DependencyNode> currentSentence = new HashMap<Integer, DependencyNode>();
			currentSentence = putNodesInOrder(currentNode, currentSentence);
			orderedSentences.add(currentSentence);
		}
		
		ArrayList<ArrayList<DependencyNode>> arrayOrderedSentences = new ArrayList<ArrayList<DependencyNode>>();
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
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			breakUpApprArt(currentSentence);
		}
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			for (int j=0; j<currentSentence.size(); j++)
			{
				DependencyNode currentNode = currentSentence.get(j);
				currentNode.setWordNumber(j);
			}
		}
		
		for (int i=0; i<arrayOrderedSentences.size(); i++)
		{
			ArrayList<DependencyNode> currentSentence = arrayOrderedSentences.get(i);
			ArrayList<ArrayList<String>> sentence = convertNodesToText(currentSentence);
			conllSentences.add(sentence);
		}
		
		String directory = "/Users/bcmpbell/Documents";
		printSentences(directory);
		
		DependencyNode test5 = dependencySentences.get(0);
		System.out.println("Finished");
	}
	
	private static TreeNode treeBuilder(ArrayList<String> sentence, int currentSentence)
	{
		boolean finished = false;
		String currentLine = sentence.get(sentenceIndex);
		TreeNode current = new TreeNode(currentLine);
		while (!finished)
		{
			sentenceIndex++;
			currentLine = sentence.get(sentenceIndex);
			
			if (currentLine.contains("<node") || currentLine.contains("<sentence"))
			{
				current.addSubNode(treeBuilder(treebankLines.get(currentSentence), currentSentence));
			}
			else if (currentLine.contains("<word"))
			{
				current.addWord(new TreeWord(currentLine));
			}
			else if (currentLine.contains("</node") || currentLine.contains("</sentence"))
			{
				finished = true;
			}
		}
		return current;
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
				if (nodeName.equals("VF") || nodeName.equals("LK") || nodeName.equals("MF") || nodeName.equals("MFE") || nodeName.equals("VC") || nodeName.equals("VCE") || nodeName.equals("NF")
						 || nodeName.equals("LV") || nodeName.equals("C") || nodeName.equals("KOORD") || nodeName.equals("FKOORD") || nodeName.equals("PARORD") || 
						 (nodeName.equals("FKONJ") && nodeFunction.equals("-")))
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
			if (!(subWords.isEmpty()) && subWords.get(0).getWordData().get("pos").equals("PTKZU"))
			{
				if (subWords.get(1).getWordData().get("lemma").equals("lassen"))
				{
					subWords.get(0).setPos("PTKZU-LASS");
				}
				if (subWords.get(1).getWordData().get("lemma").contains("%passiv"))
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
					if (currentFunction.equals("KONJ") || currentFunction.equals("HD") || currentFunction.equals("APP"))
					{
						foundHeadOrKonjOrApp = true;
						break;
					}
				}
			}
			if (!foundHeadOrKonjOrApp && lastAdj != null)
			{
				lastAdj.setDependency("HD");
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
				System.out.println("PARATAXIS");
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
					boolean hasKokom = false;
					for (int j=0; j<subWords.size(); j++)
					{
						TreeWord currentWord = subWords.get(j);
						if (currentWord.getPos().equals("KOKOM"))
						{
							hasKokom = true;
							break;
						}
					}
					if (hasKokom)
					{
						currentSubNode.setDependency("NMOD");
					}
					else if (morph != null)
					{
						if (morph.substring(0, 1).equals("n"))
						{
							currentSubNode.setDependency("NOM");
						}
						else if (morph.substring(0, 1).equals("a"))
						{
							currentSubNode.setDependency("ACC");
						}
						else if (morph.substring(0, 1).equals("d"))
						{
							currentSubNode.setDependency("DAT");
						}
						else if (morph.substring(0, 1).equals("g"))
						{
							currentSubNode.setDependency("GEN");
						}
						else if (morph.substring(0, 1).equals("*"))
						{
							currentSubNode.setDependency("AMBIG");
						}
						else
						{
							currentSubNode.setDependency("UNK-N");
						}
					}
				}
				else
				{
					currentSubNode.setDependency("UNK");
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
						System.out.println("HASKOKOM");
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
				if (pos.startsWith("$"))
				{
					currentWord.setDependency("PUNCT");
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
	private static TreeNode transformFKONJ(TreeNode node)
	{
		//Check structure
		TreeNode transformed = node;
		ArrayList<TreeNode> subNodes = transformed.getSubNodes();
		ArrayList<TreeWord> words = transformed.getWords();
		boolean hdFound = false;
		boolean konj1 = false;
		boolean konj2 = false;
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode = subNodes.get(i);
			if (currentSubNode.getDependency().equals("HD") || currentSubNode.getDependency().equals("VC-HD"))
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
		
		boolean match = hdFound && konj1 && konj2;
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
			transformFKONJ(subNodes.get(i));
		}
		
		return node;
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
	
	// Set a HD node for KONJ2 subnodes if not available
	private static TreeNode setKonj2Head (TreeNode node)
	{
		TreeNode headSet = node;
		ArrayList<TreeNode> subNodes = headSet.getSubNodes();
		
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode current = subNodes.get(i);
			String nodeFunction = current.getDependency();
			if (nodeFunction.equals("KONJ2"))
			{
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
						}
					}
					if (!foundOV)
					{
						subSubNodes.get(subNodes.size()-1).setCategory("HD");
					}
				}
			}
		}
		for (int j=0; j<subNodes.size(); j++)
		{
			setKonj2Head(subNodes.get(j));
		}
		return headSet;
	}
	
	private static TreeNode transformDependencies(TreeNode node)
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
			transformDependencies(currentSubNode);
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
		for (int i=0; i<subNodes.size(); i++)
		{
			TreeNode currentSubNode= subNodes.get(i);
			currentSubNode.setDependency(newDeps.get(i));
		}
		int newDepsIndex = subNodes.size();
		for (int i=0; i<subWords.size(); i++)
		{
			TreeWord currentWord = subWords.get(i);
			currentWord.setDependency(newDeps.get(newDepsIndex));
			newDepsIndex++;
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
			current = new DependencyNode("ROOT", null, "N/A", null);
			current.setSubNodes(headWordFinder(treeNode));
			ArrayList<DependencyNode> rootDependents = current.getSubNodes();
			
			//Set dependency relation for all immediate children of the root to "ROOT"
			for (int i=0; i<rootDependents.size(); i++)
			{
				DependencyNode currentDepSubNode = rootDependents.get(i);
				currentDepSubNode.setRel("ROOT");
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
				current = headNode;
				for (int i=0; i<heads.size(); i++)
				{
					if ((!(current.getSubNodes().contains(heads.get(i)))) && (!(current.equals(heads.get(i)))))
					{
						current.addDependent(heads.get(i));
					}
				}
				for (int i=0; i<subNodes.size(); i++)
				{
					TreeNode currentSubNode = subNodes.get(i);
					extractDepStructure(currentSubNode, false, current);
				}
				for (int i=0; i<words.size(); i++)
				{
					TreeWord currentWord = words.get(i);
					DependencyNode currentWordDepNode = currentWord.getDepNode();
					if (currentWordDepNode.getRel().equals(""))
					{
						currentWordDepNode.setRel(currentWord.getDependency());
					}
					if((!(heads.get(0).getSubNodes().contains(currentWordDepNode))) && (!(heads.get(0).equals(currentWordDepNode))) && (!(heads.contains(currentWordDepNode))))
					{
						heads.get(0).addDependent(currentWordDepNode);
					}
				}
			}
			else if (heads.size() == 1)
			{
				current = heads.get(0);
				for (int i=0; i<subNodes.size(); i++)
				{
					TreeNode currentSubNode = subNodes.get(i);
					ArrayList<DependencyNode> currentSubNodeHeads = headWordFinder(currentSubNode);
					if (!(((current.equals(currentSubNodeHeads.get(0))) || (currentSubNodeHeads.size() > 1) || (currentSubNodeHeads.contains(current)))))
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
		if (!(current.getRel().equals("ROOT")))
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
				}
				else if (form.endsWith("r"))
				{
					art = "der";
					artLemma = "die";
				}
				else if (form.endsWith("s"))
				{
					art = "das";
					artLemma = "das";
				}
				else if (form.endsWith("n"))
				{
					art = "den";
					artLemma = "der";
				}
				DependencyNode nodeArt = new DependencyNode(currentNode.getLine(), currentNode.getHead(), "det", null);
				nodeArt.getNodeData().put("lemma", artLemma);
				nodeArt.setLemma(artLemma);
				nodeArt.getNodeData().put("form", art);
				nodeArt.setPos("ART");
				nodeArt.getNodeData().put("pos", "ART");
				
				nodeArt.getHead().addDependent(nodeArt);
				
				currentNode.getNodeData().put("form", prep);
				currentNode.setRel("case");
				currentNode.setPos("ADP");
				currentNode.getNodeData().put("pos", "APPR");
				
				newSentence.add(i+1, nodeArt);
			}
		}
	}
	
	private static ArrayList<ArrayList<String>> convertNodesToText(ArrayList<DependencyNode> sentence)
	{
		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		
		for (int i=0; i<sentence.size(); i++)
		{
			ArrayList<String> columns = new ArrayList<String>();
			DependencyNode node = sentence.get(i);
			String wordIndex = Integer.toString(node.getWordNumber());
			String form = node.getNodeData().get("form");
			String lemma = node.getLemma();
			String upostag = node.getPos();
			String xpostag = node.getNodeData().get("pos");
			String feats = "_";
			String head = "0";
			if (node.getHead() != null)
			{
				head = Integer.toString(node.getHead().getWordNumber());
			}
			String depRel = node.getRel();
			String deps = "_";
			String misc = "_";
			
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
	
	private static void printSentences(String directory)
	{
		String fileName = directory + "/convertedSentences.txt";
		
		BufferedWriter writer = null;
		try
		{
		    writer = new BufferedWriter(new FileWriter(fileName));
		    
		    for (int i=0; i<conllSentences.size(); i++)
		    {
		    	ArrayList<ArrayList<String>> currentSentence = conllSentences.get(i);
		    	writer.write(Integer.toString(i + 1));
		    	writer.write("\n");
		    	writer.write("\n");
		    	for (int j=1; j<currentSentence.size(); j++)
		    	{
		    		ArrayList<String> currentWord = currentSentence.get(j);
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
	
	private static ArrayList<ArrayList<String>> readExportFile(String path)
	{
		BufferedReader br = null;
		ArrayList<ArrayList<String>> sentences = new ArrayList<ArrayList<String>>();

		try
		{

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));
			ArrayList<String> currentSentence = new ArrayList<String>();
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				currentSentence.add(sCurrentLine);
				if (sCurrentLine.contains("</sentence"))
				{
					sentences.add(currentSentence);
					currentSentence = new ArrayList<String>();
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