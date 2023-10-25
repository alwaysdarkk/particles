package com.github.alwaysdarkk.particles.user.cache;

import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.google.common.collect.Maps;
import lombok.experimental.Delegate;

import java.util.Map;

public class ParticleUserCache {

    @Delegate
    private final Map<String, ParticleUser> userMap = Maps.newHashMap();
}