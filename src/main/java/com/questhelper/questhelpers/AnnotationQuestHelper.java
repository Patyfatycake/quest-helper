package com.questhelper.questhelpers;

import com.questhelper.Step;
import com.questhelper.panel.PanelDetails;
import com.questhelper.steps.QuestStep;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AnnotationQuestHelper extends BasicQuestHelper
{
	@Override
	public Map<Integer, QuestStep> loadSteps()
	{
		setupSteps();

		Map<Integer, QuestStep> steps = new HashMap<>();

		for (Field field : this.getClass().getDeclaredFields())
		{
			Step annotation = field.getAnnotation(Step.class);
			if (annotation == null)
			{
				continue;
			}

			if (QuestStep.class.isAssignableFrom(field.getType()))
			{
				try
				{
					field.setAccessible(true);
					steps.put(annotation.pos(), (QuestStep) field.get(this));
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				log.warn("Annotated field \'" + field.getName() + "\' is not assignable.");
			}
		}
		return steps;
	}

	public abstract void setupSteps();
}
