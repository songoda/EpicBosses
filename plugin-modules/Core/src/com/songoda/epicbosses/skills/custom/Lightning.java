package com.songoda.epicbosses.skills.custom;

import com.songoda.epicbosses.holder.ActiveBossHolder;
import com.songoda.epicbosses.skills.CustomSkillHandler;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.interfaces.ICustomSettingAction;
import com.songoda.epicbosses.skills.interfaces.IOtherSkillDataElement;
import com.songoda.epicbosses.skills.types.CustomSkillElement;
import org.bukkit.entity.LivingEntity;

import java.util.List;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 11-Nov-18
 */
public class Lightning extends CustomSkillHandler {

    @Override
    public boolean doesUseMultiplier() {
        return false;
    }

    @Override
    public IOtherSkillDataElement getOtherSkillData() {
        return null;
    }

    @Override
    public List<ICustomSettingAction> getOtherSkillDataActions(Skill skill, CustomSkillElement customSkillElement) {
        return null;
    }

    @Override
    public void castSkill(Skill skill, CustomSkillElement customSkillElement, ActiveBossHolder activeBossHolder, List<LivingEntity> nearbyEntities) {
        nearbyEntities.forEach(livingEntity -> livingEntity.getWorld().strikeLightningEffect(livingEntity.getEyeLocation()));
    }
}
