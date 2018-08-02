package net.aminecraftdev.custombosses.utils.command.attributes;

import java.lang.annotation.*;

/**
 * Created by LukeBingham on 03/04/2017.
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Suggest {

    String value();

}
