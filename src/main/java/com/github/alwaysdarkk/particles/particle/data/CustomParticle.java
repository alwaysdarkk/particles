package com.github.alwaysdarkk.particles.particle.data;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Material;

import java.util.List;

@Data
@Builder
public class CustomParticle {

    private final String identifier, displayName, permission;
    private final Material material;

    @Builder.Default
    private final List<String> description = Lists.newArrayList();

    private final int slot;
    private final double price;
    private final ParticleBuilder particleBuilder;
}