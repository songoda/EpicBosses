package net.aminecraftdev.custombosses.utils.entity.handlers;

import net.aminecraftdev.custombosses.utils.Versions;
import net.aminecraftdev.custombosses.utils.entity.ICustomEntityHandler;
import net.aminecraftdev.custombosses.utils.version.VersionHandler;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 01-Jun-18
 */
public class WitherSkeletonHandler implements ICustomEntityHandler {

    private VersionHandler versionHandler = new VersionHandler();

    @Override
    public LivingEntity getBaseEntity(String entityType, Location spawnLocation) {
        if(this.versionHandler.getVersion().isHigherThanOrEqualTo(Versions.v1_11_R1)) {
            return (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.WITHER_SKELETON);
        }

        Skeleton skeleton = (Skeleton) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.SKELETON);
        skeleton.setSkeletonType(Skeleton.SkeletonType.WITHER);

        return skeleton;
    }
}