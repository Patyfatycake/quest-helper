/*
 * Copyright (c) 2020, Patyfatycake <https://github.com/Patyfatycake/>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABI`LITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.questhelper.quests.junglepotion;

import com.questhelper.ItemCollections;
import com.questhelper.PanelDetail;
import com.questhelper.QuestDescriptor;
import com.questhelper.QuestHelperQuest;
import com.questhelper.Step;
import com.questhelper.Zone;
import com.questhelper.panel.PanelDetails;
import com.questhelper.questhelpers.AnnotationQuestHelper;
import com.questhelper.requirements.ItemRequirement;
import com.questhelper.requirements.Requirement;
import com.questhelper.requirements.conditional.ItemRequirementCondition;
import com.questhelper.requirements.conditional.ZoneCondition;
import com.questhelper.steps.ConditionalStep;
import com.questhelper.steps.DetailedQuestStep;
import com.questhelper.steps.NpcStep;
import com.questhelper.steps.ObjectStep;
import com.questhelper.steps.QuestStep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.ObjectID;
import net.runelite.api.coords.WorldPoint;

@QuestDescriptor(
	quest = QuestHelperQuest.JUNGLE_POTION
)
public class JunglePotion extends AnnotationQuestHelper
{
	//Items Required
	ItemRequirement grimySnakeWeed, snakeWeed, grimyArdrigal, ardrigal, grimySitoFoil, sitoFoil, grimyVolenciaMoss, volenciaMoss,
		roguesPurse, grimyRoguesPurse;

	ObjectStep getRoguePurseHerb;

	@Step(pos = 0)
	@PanelDetail(title = START_TITLE, position = 0)
	QuestStep startQuest;

	@Step(pos = 1)
	ObjectStep getSnakeWeed;

	@Step(pos = 2)
	ConditionalStep cleanAndReturnSnakeWeed;

	@Step(pos = 3)
	ObjectStep getArdrigal;

	@Step(pos = 4)
	ConditionalStep cleanAndReturnArdrigal;

	@Step(pos = 5)
	ObjectStep getSitoFoil;

	@Step(pos = 6)
	ConditionalStep cleanAndReturnSitoFoil;

	@Step(pos = 7)
	ObjectStep getVolenciaMoss;

	@Step(pos = 8)
	ConditionalStep cleanAndReturnVolenciaMoss;

	@Step(pos = 9)
	ConditionalStep getRoguesPurse;

	@Step(pos = 10)
	ConditionalStep cleanAndReturnRoguesPurse;

	@Step(pos = 11)
	QuestStep finishQuest;

	ObjectStep enterCave;

	ZoneCondition isUnderground;

	private static final String START_TITLE = "Starting quest";

	@Override
	public void setupSteps()
	{
		setupItemRequirements();
		setupZones();
		createSteps();
	}

	private void setupItemRequirements()
	{
		grimySnakeWeed = new ItemRequirement("Grimy Snake Weed", ItemID.GRIMY_SNAKE_WEED);
		grimySnakeWeed.setHighlightInInventory(true);
		snakeWeed = new ItemRequirement("Snake Weed", ItemID.SNAKE_WEED);

		grimyArdrigal = new ItemRequirement("Grimy Ardrigal", ItemID.GRIMY_ARDRIGAL);
		grimyArdrigal.setHighlightInInventory(true);
		ardrigal = new ItemRequirement("Ardrigal", ItemID.ARDRIGAL);

		grimySitoFoil = new ItemRequirement("Grimy Sito Foil", ItemID.GRIMY_SITO_FOIL);
		grimySitoFoil.setHighlightInInventory(true);
		sitoFoil = new ItemRequirement("Sito Foil", ItemID.SITO_FOIL);

		grimyVolenciaMoss = new ItemRequirement("Grimy Volencia Moss", ItemID.GRIMY_VOLENCIA_MOSS);
		grimyVolenciaMoss.setHighlightInInventory(true);
		volenciaMoss = new ItemRequirement("Volencia Moss", ItemID.VOLENCIA_MOSS);

		grimyRoguesPurse = new ItemRequirement("Grimy Rogues Purse", ItemID.GRIMY_ROGUES_PURSE);
		grimyRoguesPurse.setHighlightInInventory(true);
		roguesPurse = new ItemRequirement("Rogues Purse", ItemID.ROGUES_PURSE);
	}

	private void setupZones()
	{
		//2824,9462,0
		//2883, 9533, 0
		Zone undergroundZone = new Zone(new WorldPoint(2824, 9462, 0), new WorldPoint(2883, 9533, 0));
		isUnderground = new ZoneCondition(undergroundZone);
	}

	private void createSteps()
	{
		startQuest = talkToTrufitus("Talk to Trufitus in Tai Bwo Wannai on Karamja.");
		startQuest.addDialogSteps("It's a nice village, where is everyone?");
		startQuest.addDialogSteps("Me? How can I help?");
		startQuest.addDialogSteps("It sounds like just the challenge for me.");

		getSnakeWeed = new ObjectStep(this, ObjectID.MARSHY_JUNGLE_VINE, new WorldPoint(2763, 3044, 0),
			"Search a marshy jungle vine south of Tai Bwo Wannai for some snake weed.");
		getSnakeWeed.addText("If you want to do Zogre Flesh Eaters or Legends' Quest grab one for each as you will need them later.");
		cleanAndReturnSnakeWeed = getReturnHerbStep("Snake Weed", grimySnakeWeed, snakeWeed);

		getArdrigal = new ObjectStep(this, ObjectID.PALM_TREE_2577, new WorldPoint(2871, 3116, 0),
			"Search the palm trees north east of Tai Bwo Wannai for an Ardrigal herb.");
		getArdrigal.addText("If you want to do Zogre Flesh Eaters or Legends' Quest grab one for each as you will need them later.");
		cleanAndReturnArdrigal = getReturnHerbStep("Ardrigal", grimyArdrigal, ardrigal);

		getSitoFoil = new ObjectStep(this, ObjectID.SCORCHED_EARTH, new WorldPoint(2791, 3047, 0),
			"Search the scorched earth in the south of Tai Bwo Wannai for a Sito Foil herb.");
		cleanAndReturnSitoFoil = getReturnHerbStep("Sito Foil", grimySitoFoil, sitoFoil);

		getVolenciaMoss = new ObjectStep(this, ObjectID.ROCK_2581, new WorldPoint(2851, 3036, 0),
			"Search the rock for a Volencia Moss herb at the mine south east of Tai Bwo Wannai.");
		getVolenciaMoss.addText("If you plan on doing Fairy Tail I then take an extra.");
		cleanAndReturnVolenciaMoss = getReturnHerbStep("Volencia Moss", grimyVolenciaMoss, volenciaMoss);

		getRoguesPurse();
		returnRoguesPurse();

		finishQuest = talkToTrufitus("Talk to Trufitus to finish the quest.");
	}

	private QuestStep returnRoguesPurse()
	{
		cleanAndReturnRoguesPurse = getReturnHerbStep("Rogues Purse", grimyRoguesPurse, roguesPurse);
		cleanAndReturnRoguesPurse.addStep(isUnderground, new DetailedQuestStep(this, "Teleport out of the cave."));
		return cleanAndReturnRoguesPurse;
	}

	private ConditionalStep getReturnHerbStep(String herbName, ItemRequirement grimyHerb, ItemRequirement cleanHerb)
	{
		NpcStep returnHerb = talkToTrufitus("", cleanHerb);
		returnHerb.addDialogSteps("Of course!");
		DetailedQuestStep cleanGrimyHerb = new DetailedQuestStep(this, "", grimyHerb);

		ConditionalStep cleanAndReturnHerb = new ConditionalStep(this, cleanGrimyHerb, "Clean and return the " + herbName + " to Trufitus.");
		cleanAndReturnHerb.addStep(new ItemRequirementCondition(cleanHerb), returnHerb);
		return cleanAndReturnHerb;
	}

	private QuestStep getRoguesPurse()
	{
		enterCave = new ObjectStep(this, ObjectID.ROCKS_2584, new WorldPoint(2825, 3119, 0),
			"Enter the cave to the north by clicking on the rocks.");
		enterCave.addDialogStep("Yes, I'll enter the cave.");
		getRoguePurseHerb = new ObjectStep(this, 2583, "Get the Rogues Purse from the fungus covered wall in the underground dungeon.");
		getRoguePurseHerb.addText("If you are planning on doing Zogre Flesh Eaters then take an extra.");

		getRoguesPurse = new ConditionalStep(this, enterCave);
		getRoguesPurse.addStep(isUnderground, getRoguePurseHerb);

		getRoguesPurse.addSubSteps(enterCave);
		return getRoguesPurse;
	}

	private NpcStep talkToTrufitus(String text, Requirement... requirements)
	{
		return new NpcStep(this, NpcID.TRUFITUS, new WorldPoint(2809, 3085, 0), text, requirements);
	}

	@Override
	public List<String> getCombatRequirements()
	{
		ArrayList<String> reqs = new ArrayList<>();
		reqs.add("Surive against level 53 Jogres and level 46 Harpie Bug Swarms.");
		return reqs;
	}

	//Recommended
	@Override
	public List<ItemRequirement> getItemRecommended()
	{
		ArrayList<ItemRequirement> reqs = new ArrayList<>();
		ItemRequirement food = new ItemRequirement("Food", ItemCollections.getGoodEatingFood(), -1);

		reqs.add(food);
		reqs.add(new ItemRequirement("Antipoison", ItemCollections.getAntipoisons()));
		reqs.add(new ItemRequirement("Teleport to Karamja (Glory/house teleport)", ItemID.BRIMHAVEN_TELEPORT));
		return reqs;
	}

	@Override
	public List<PanelDetails> getPanels()
	{
		List<PanelDetails> steps = new ArrayList<>();

		PanelDetails startingPanel = new PanelDetails(START_TITLE,
			Collections.singletonList(startQuest));
		steps.add(startingPanel);

		PanelDetails snakeWeedPanel = new PanelDetails("Snake Weed",
			Arrays.asList(getSnakeWeed, cleanAndReturnSnakeWeed));
		steps.add(snakeWeedPanel);

		PanelDetails ardrigalPanel = new PanelDetails("Ardrigal",
			Arrays.asList(getArdrigal, cleanAndReturnArdrigal));
		steps.add(ardrigalPanel);

		PanelDetails sitoFoilpanel = new PanelDetails("Sito Foil",
			Arrays.asList(getSitoFoil, cleanAndReturnSitoFoil));
		steps.add(sitoFoilpanel);

		PanelDetails volcaniaMossPanel = new PanelDetails("Volcania Moss",
			Arrays.asList(getVolenciaMoss, cleanAndReturnVolenciaMoss));
		steps.add(volcaniaMossPanel);

		PanelDetails roguesPursePanel = new PanelDetails("Rogues Purse",
			Arrays.asList(enterCave, getRoguePurseHerb, cleanAndReturnRoguesPurse));
		steps.add(roguesPursePanel);

		return steps;
	}
}
