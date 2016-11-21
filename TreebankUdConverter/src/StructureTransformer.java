import java.util.ArrayList;
import java.util.HashMap;

public class StructureTransformer 
{
	private static StructureTransformer instance;
	
	private StructureTransformer() {}

	public static StructureTransformer getInstance () 
	{
	    if (StructureTransformer.instance == null) 
	    {
	    	StructureTransformer.instance = new StructureTransformer();
	    }
	    return StructureTransformer.instance;
	 }
	
	public ArrayList<String> convertDependencies(ArrayList<RelationTriplet> deps)
	{
		ArrayList<String> newDependencies = new ArrayList<String>();
		
		for (int i=0; i<deps.size(); i++)
		{
			newDependencies.add("REPLACEME");
		}
		
		Transformations instance = Transformations.getInstance();
		
		ArrayList<RelationTemplate> currentTemplate = null;
		boolean testEquivalence = false;
		
		//SPECIALLY HANDLED CASES
		
		//KONJ,KONJ,...:HD,conj,... --> First KONJ becomes head
		//*****************************************************
		currentTemplate = instance.getKONJKONJ_hdconj();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		
		if (testEquivalence)
		{
			boolean foundFirstKONJ = false;
			for (int i=0; i<deps.size(); i++)
			{
				if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME"))
				{
					if (!foundFirstKONJ)
					{
						foundFirstKONJ = true;
						newDependencies.set(i, "HD");
					}
					else
					{
						newDependencies.set(i, "conj");
					}
				}
			}
		}
		
		//[PRED,,N*],[PRED-MOD,SIMPX,]:[PRED,,N*],acl - 261
		//*************************************************
		currentTemplate = instance.getPREDPREDMOD_PREDacl();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		
		if (testEquivalence)
		{
			for (int i=0; i<deps.size(); i++)
			{
				if (isMatch(deps.get(i), currentTemplate.get(1)) && newDependencies.get(i).equals("REPLACEME"))
				{
					newDependencies.set(i, "acl");
				}
			}
		}
		
		//[PRED,,],[PRED-MOD,SIMPX,]:[PRED,,],advcl
		//*****************************************
		currentTemplate = instance.getPREDPREDMOD_PREDadvcl();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		
		if (testEquivalence)
		{
			for (int i=0; i<deps.size(); i++)
			{
				if (isMatch(deps.get(i), currentTemplate.get(1)) && newDependencies.get(i).equals("REPLACEME"))
				{
					newDependencies.set(i, "advcl");
				}
			}
		}
		
		// APP,...:HD,(appos,...)
		// *****************************************************
		currentTemplate = instance.getAPPAPP_HDappos();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);

		if (testEquivalence) 
		{
			boolean foundFirstAPP = false;
			for (int i = 0; i < deps.size(); i++) 
			{
				if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME")) 
				{
					if (!foundFirstAPP) 
					{
						foundFirstAPP = true;
						newDependencies.set(i, "HD");
					} else 
					{
						newDependencies.set(i, "appos");
					}
				}
			}
		}
		
		// AUTOMATICALLY HANDLED MULIPLE TRANSFORMATION CASES
		ArrayList<TransformationPair> transformationMultiplePairs = instance.getAutoProcessedMultipleTemplates();

		for (int i = 0; i < transformationMultiplePairs.size(); i++) 
		{
			TransformationPair currentPair = transformationMultiplePairs.get(i);
			currentTemplate = currentPair.getOldRelation();
			ArrayList<String> newDeps = currentPair.getNewDependencies();
			testEquivalence = checkStructureEquivalence(deps, currentTemplate);
			boolean headSet = false; // Make sure head is only set once

			if (testEquivalence) 
			{
				for (int j = 0; j < currentTemplate.size(); j++) 
				{
					RelationTemplate currentTemplateTriplet = currentTemplate.get(j);
					for (int k = 0; k < deps.size(); k++) 
					{
						RelationTriplet currentTriplet = deps.get(k);
						if (isMatch(currentTriplet, currentTemplateTriplet)
								&& newDependencies.get(k).equals("REPLACEME")) 
						{
							if (!(newDeps.get(j).equals("HD")) || !headSet)
								newDependencies.set(k, newDeps.get(j));
							if (newDeps.get(j).equals("HD"))
								headSet = true;
						}
					}
				}
				break;
			}
		}
		
		//AUTOMATICALLY HANDLED SINGLE TRANSFORMATION CASES
		ArrayList<TransformationPair> transformationSinglePairs = instance.getAutoProcessedSingleTemplates();
		
		for (int i=0; i<transformationSinglePairs.size(); i++)
		{
			TransformationPair currentPair = transformationSinglePairs.get(i);
			currentTemplate = currentPair.getOldRelation();
			ArrayList<String> newDeps = currentPair.getNewDependencies();
			testEquivalence = checkStructureEquivalence(deps, currentTemplate);
			boolean headSet = false; //Make sure head is only set once
			
			if (testEquivalence)
			{
				for (int j=0; j<currentTemplate.size(); j++)
				{
					RelationTemplate currentTemplateTriplet = currentTemplate.get(j);
					for (int k=0; k<deps.size(); k++)
					{
						RelationTriplet currentTriplet = deps.get(k);
						if (isMatch(currentTriplet, currentTemplateTriplet) && newDependencies.get(k).equals("REPLACEME"))
						{
							if (!(newDeps.get(j).equals("HD")) || !headSet)
								newDependencies.set(k, newDeps.get(j));
							if (newDeps.get(j).equals("HD"))
								headSet = true;
						}
					}
				}
			}
		}
		
		//IF ALL ELSE FAILS
		
		//HD,[,,FM],[,,FM],...:HD,foreign,foreign,...
		// *****************************************************
		currentTemplate = instance.getHDFM_HDforeign();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);

		if (testEquivalence) 
		{
			for (int i = 0; i < deps.size(); i++) 
			{
				if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME")) 
				{
					newDependencies.set(i, "HD");
				}
				else if (isMatch(deps.get(i), currentTemplate.get(1)) && newDependencies.get(i).equals("REPLACEME")) 
				{
					newDependencies.set(i, "foreign");
				}
			}
		}
		
		//[,,FM],[,,FM],...:HD,foreign,...
		// *****************************************************
		currentTemplate = instance.getFMFM_HDforeign();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);

		if (testEquivalence) 
		{
			boolean foundFirstFM = false;
			for (int i = 0; i < deps.size(); i++) 
			{
				if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME")) 
				{
					if (!foundFirstFM) 
					{
						foundFirstFM = true;
						newDependencies.set(i, "HD");
					} 
					else 
					{
						newDependencies.set(i, "foreign");
					}
				}
			}
		}
		
		//[HD,,N*],[,SIMPX,]:acl ***
		// *****************************************************
		currentTemplate = instance.getN_SIMPX_acl();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		for (int i = 0; i < deps.size(); i++) 
		{
			if (isMatch(deps.get(i), currentTemplate.get(1)) && newDependencies.get(i).equals("REPLACEME")) 
			{
				newDependencies.set(i, "acl");
			}
		}
		
		//[,SIMPX,]:advcl ***
		// *****************************************************
		currentTemplate = instance.getN_SIMPX_acl();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		for (int i = 0; i < deps.size(); i++) 
		{
			if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME")) 
			{
				newDependencies.set(i, "advcl");
			}
		}
		
		//[,,NE],...:HD,(name,...)
		// *****************************************************
		currentTemplate = instance.getNE_HDname();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);

		if (testEquivalence) 
		{
			boolean foundFirstNE = false;
			for (int i = 0; i < deps.size(); i++) 
			{
				if (isMatch(deps.get(i), currentTemplate.get(0)) && newDependencies.get(i).equals("REPLACEME")) 
				{
					if (!foundFirstNE) 
					{
						foundFirstNE = true;
						newDependencies.set(i, "HD");
					} 
					else 
					{
						newDependencies.set(i, "name");
					}
				}
			}
		}
		
		//[,,N*]:appos
		// *****************************************************
		currentTemplate = instance.getN_APP();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		
		if (testEquivalence)
		{
			for (int j=0; j<currentTemplate.size(); j++)
			{
				RelationTemplate currentTemplateTriplet = currentTemplate.get(j);
				for (int k=0; k<deps.size(); k++)
				{
					RelationTriplet currentTriplet = deps.get(k);
					if (isMatch(currentTriplet, currentTemplateTriplet) && newDependencies.get(k).equals("REPLACEME"))
					{
						newDependencies.set(k, "appos");
					}
				}
			}
		}
		
		//[,NX,]:appos
		// *****************************************************
		currentTemplate = instance.getNX_APP();
		testEquivalence = checkStructureEquivalence(deps, currentTemplate);
		
		if (testEquivalence)
		{
			for (int j=0; j<currentTemplate.size(); j++)
			{
				RelationTemplate currentTemplateTriplet = currentTemplate.get(j);
				for (int k=0; k<deps.size(); k++)
				{
					RelationTriplet currentTriplet = deps.get(k);
					if (isMatch(currentTriplet, currentTemplateTriplet) && newDependencies.get(k).equals("REPLACEME"))
					{
						newDependencies.set(k, "appos");
					}
				}
			}
		}
		
		return newDependencies;
	}
	
	private boolean checkStructureEquivalence(ArrayList<RelationTriplet> treeNodes, ArrayList<RelationTemplate> treeStructureCompare)
	{
		boolean isEquivalent = true;
		HashMap<Integer, Integer> structureCount = new HashMap<Integer, Integer>(); //count the number of equivalent relations
		for (int i=0; i<treeStructureCompare.size(); i++)
		{
			RelationTemplate currentRelTemplate = treeStructureCompare.get(i);
			int currentCount = 0;
			{
				for (int j=0; j<treeStructureCompare.size(); j++)
				{
					RelationTemplate currentRelTemplateCompare = treeStructureCompare.get(j);
					if (currentRelTemplate.equals(currentRelTemplateCompare))
					{
						currentCount++;
					}
				}
			}
			structureCount.put(i, currentCount);
		}
		
		for (int i=0; i<treeStructureCompare.size(); i++)
		{
			RelationTemplate currentRelTemplate = treeStructureCompare.get(i);
			ArrayList<RelationTriplet> containedRelations = contains(treeNodes, currentRelTemplate);
			if (containedRelations.size() < structureCount.get(i))
			{
				isEquivalent = false;
				break;
			}
		}
		return isEquivalent;
	}
	
	private ArrayList<RelationTriplet> contains(ArrayList<RelationTriplet> relations, RelationTemplate relCompare)
	{
		ArrayList<RelationTriplet> containedRelations = new ArrayList<RelationTriplet>();
		for (int i=0; i<relations.size(); i++)
		{
			RelationTriplet currRel = relations.get(i);
			if (isMatch(currRel, relCompare))
			{
				containedRelations.add(currRel);
			}
		}
		return containedRelations;
	}
	
	private boolean isMatch (RelationTriplet rel, RelationTemplate relCompare)
	{
		boolean match = false;
		
		boolean needsFunction = relCompare.needsFunction();
		boolean needsNodeName = relCompare.needsNodeName();
		boolean needsPos = relCompare.needsPos();
		
		boolean functionMatch = !needsFunction;
		boolean nodeNameMatch = !needsNodeName;
		boolean posMatch = !needsPos;
		
		String function = rel.getFunction();
		String nodeName = rel.getNodeName();
		String pos = rel.getPos();
		
		ArrayList<String> compFunction = relCompare.getFunction();
		ArrayList<String> compNodeName = relCompare.getNodeName();
		ArrayList<String> compPos = relCompare.getPos();
		
		if (needsFunction && compFunction.contains(function))
			functionMatch = true;
		if (needsNodeName && compNodeName.contains(nodeName))
			nodeNameMatch = true;
		if (needsPos && compPos.contains(pos))
			posMatch = true;
		
		match = functionMatch && nodeNameMatch && posMatch;
		
		return match;
	}
}
