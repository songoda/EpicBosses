package com.songoda.epicbosses.skills.interfaces;

import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.types.CustomSkillElement;

import java.util.List;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 16-Dec-18
 */
public interface ICustomSkillHandler extends ISkillHandler<CustomSkillElement> {

    boolean doesUseMultiplier();

    IOtherSkillDataElement getOtherSkillData();

    List<ICustomSettingAction> getOtherSkillDataActions(Skill skill, CustomSkillElement customSkillElement);

    String getSkillName();

}
