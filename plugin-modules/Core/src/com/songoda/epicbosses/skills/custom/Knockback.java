package com.songoda.epicbosses.skills.custom;

import com.songoda.epicbosses.holder.ActiveBossHolder;
import com.songoda.epicbosses.skills.CustomSkillHandler;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.interfaces.ICustomSkillAction;
import com.songoda.epicbosses.skills.types.CustomSkillElement;
import com.songoda.epicbosses.utils.panel.base.ClickAction;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 11-Nov-18
 */
public class Knockback extends CustomSkillHandler {

    @Override
    public boolean doesUseMultiplier() {
        return true;
    }

    @Override
    public Map<String, Class<?>> getOtherSkillData() {
        return null;
    }

    @Override
    public List<ICustomSkillAction> getOtherSkillDataActions(Skill skill, CustomSkillElement customSkillElement) {
        return null;
    }

    @Override
    public void castSkill(Skill skill, CustomSkillElement customSkillElement, ActiveBossHolder activeBossHolder, List<LivingEntity> nearbyEntities) {
        Double multiplier = customSkillElement.getCustom().getMultiplier();

        if(multiplier == null) multiplier = 2.5;

        double finalMultiplier = multiplier;
        Location bossLocation = activeBossHolder.getLocation();

        nearbyEntities.forEach(livingEntity -> {
            Location throwLocation = livingEntity.getEyeLocation();
            Vector vector = throwLocation.toVector().subtract(bossLocation.toVector()).normalize().multiply(finalMultiplier);

            livingEntity.setVelocity(vector);
        });
    }
}
