package com.github.alwaysdarkk.particles.user.data;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Data
@Builder
public class ParticleUser {
    private final String name;

    @Builder.Default
    private final Set<String> particles = Sets.newHashSet();

    @Builder.Default
    private String particle = null;

    public boolean hasActiveParticle() {
        return this.particle != null;
    }

    public void addParticle(@NotNull String identifier) {
        this.particles.add(identifier);
    }

    public boolean hasParticle(@NotNull String identifier) {
        return this.particles.contains(identifier);
    }
    public boolean isCurrent(@NotNull String identifier) {
        if (!hasActiveParticle()) return false;
        return this.particle.equals(identifier);
    }
}