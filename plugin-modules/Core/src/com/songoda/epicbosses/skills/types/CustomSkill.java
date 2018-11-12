package com.songoda.epicbosses.skills.types;

import com.google.gson.annotations.Expose;
import com.songoda.epicbosses.CustomBosses;
import com.songoda.epicbosses.skills.Skill;
import com.songoda.epicbosses.skills.elements.CustomSkillElement;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 05-Nov-18
 */
public abstract class CustomSkill extends Skill {

    @Getter private final CustomBosses plugin;

    public CustomSkill(CustomBosses plugin) {
        this.plugin = plugin;
    }

    @Expose @Getter @Setter private CustomSkillElement custom;

}