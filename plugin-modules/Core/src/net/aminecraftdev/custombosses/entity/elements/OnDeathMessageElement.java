package net.aminecraftdev.custombosses.entity.elements;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 21-Oct-18
 */
public class OnDeathMessageElement {

    @Expose @Getter @Setter private String message, positionMessage;
    @Expose @Getter @Setter private Integer radius, onlyShow;
}