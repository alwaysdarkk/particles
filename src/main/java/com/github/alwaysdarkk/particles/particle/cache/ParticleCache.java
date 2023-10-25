package com.github.alwaysdarkk.particles.particle.cache;

import com.github.alwaysdarkk.particles.particle.data.CustomParticle;
import com.google.common.collect.Maps;
import lombok.experimental.Delegate;

import java.util.Map;

public class ParticleCache {

    @Delegate
    private final Map<String, CustomParticle> particleMap = Maps.newHashMap();
}