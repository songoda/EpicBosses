package com.songoda.epicbosses.skills.elements;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 13-Nov-18
 */
public class CustomMinionSkillElement {

    @Expose @Getter @Setter private List<String> minionsToSpawn;
    @Expose @Getter @Setter private Integer amount;

    public CustomMinionSkillElement(Integer amount, List<String> minionsToSpawn) {
        this.amount = amount;
        this.minionsToSpawn = minionsToSpawn;
    }

}