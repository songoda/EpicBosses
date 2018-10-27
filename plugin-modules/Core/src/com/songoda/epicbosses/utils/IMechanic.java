package com.songoda.epicbosses.utils;

import com.songoda.epicbosses.entity.BossEntity;
import com.songoda.epicbosses.holder.ActiveBossHolder;

/**
 * @author Charles Cullen
 * @version 1.0.0
 * @since 02-Jun-18
 */
public interface IMechanic {

    boolean applyMechanic(BossEntity bossEntity, ActiveBossHolder activeBossHolder);

}