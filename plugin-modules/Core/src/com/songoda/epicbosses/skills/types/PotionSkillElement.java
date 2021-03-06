package com.songoda.epicbosses.skills.types;

import com.google.gson.annotations.Expose;
import com.songoda.epicbosses.holder.ActiveBossHolder;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.interfaces.ISkillHandler;
import com.songoda.epicbosses.utils.Debug;
import com.songoda.epicbosses.utils.potion.PotionEffectConverter;
import com.songoda.epicbosses.utils.potion.holder.PotionEffectHolder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 05-Nov-18
 */
public class PotionSkillElement implements ISkillHandler<PotionSkillElement> {

    @Expose
    private List<PotionEffectHolder> potions;

    private PotionEffectConverter potionEffectConverter;

    public PotionSkillElement(List<PotionEffectHolder> potions) {
        this.potions = potions;
        this.potionEffectConverter = new PotionEffectConverter();
    }

    @Override
    public void castSkill(Skill skill, PotionSkillElement customSkillElement, ActiveBossHolder activeBossHolder, List<LivingEntity> nearbyEntities) {
        List<PotionEffectHolder> potionElements = getPotions();

        if (this.potionEffectConverter == null) this.potionEffectConverter = new PotionEffectConverter();

        if (nearbyEntities == null || nearbyEntities.isEmpty()) return;
        if (potionElements == null) return;
        if (potionElements.isEmpty()) {
            Debug.SKILL_POTIONS_ARE_EMPTY.debug();
            return;
        }

        List<PotionEffect> potionEffects = new ArrayList<>();
        for (PotionEffectHolder potionElement : potionElements) {
            PotionEffect potionEffect = this.potionEffectConverter.from(potionElement);
            if (potionEffect != null) {
                potionEffects.add(potionEffect);
            } else {
                Debug.debugMessage("Tried to apply invalid potion effect: " + potionElement.getType());
            }
        }

        nearbyEntities.forEach(nearby -> potionEffects.forEach(nearby::addPotionEffect));
    }

    public List<PotionEffectHolder> getPotions() {
        return this.potions;
    }

    public void setPotions(List<PotionEffectHolder> potions) {
        this.potions = potions;
    }
}
