package com.github.alwaysdarkk.particles.particle.parser;

import com.destroystokyo.paper.ParticleBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class ParticleBuilderParser {

    private final ConfigurationSection section;

    public ParticleBuilder parse() {
        final AtomicReference<ParticleBuilder> particleBuilder = new AtomicReference<>();
        this.section.getKeys(false).stream().filter(Objects::nonNull).forEach(key -> {
            final Particle particle = Particle.valueOf(section.getString("type"));
            final String[] offsetCoordinates = section.getString("offset").split(",");
            final double x = Double.parseDouble(offsetCoordinates[0]),
                    y = Double.parseDouble(offsetCoordinates[1]),
                    z = Double.parseDouble(offsetCoordinates[2]);

            particleBuilder.set(new ParticleBuilder(particle)
                    .count(section.getInt("count"))
                    .offset(x, y, z));
        });

        return particleBuilder.get();
    }
}