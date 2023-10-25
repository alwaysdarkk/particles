package com.github.alwaysdarkk.particles.particle.parser;

import com.destroystokyo.paper.ParticleBuilder;
import com.github.alwaysdarkk.particles.particle.cache.ParticleCache;
import com.github.alwaysdarkk.particles.particle.data.CustomParticle;
import com.github.alwaysdarkk.particles.util.ColorUtil;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;

@Data(staticConstructor = "of")
public class ParticleParser {

    private final FileConfiguration configuration;
    private final ParticleCache particleCache;

    public void parse() {
        final ConfigurationSection section = this.configuration.getConfigurationSection("particles");
        section.getKeys(false).stream().filter(Objects::nonNull).forEach(key -> {
            final ConfigurationSection particleSection = section.getConfigurationSection(key);
            final ParticleBuilderParser builderParser =
                    new ParticleBuilderParser(particleSection.getConfigurationSection("particle-builder"));

            final Material material = Material.valueOf(particleSection.getString("material"));
            final String displayName = ColorUtil.colored(particleSection.getString("display-name")),
                    permission = particleSection.getString("permission");
            final List<String> description = ColorUtil.colored(particleSection.getStringList("description"));
            final int slot = particleSection.getInt("slot");
            final double price = particleSection.getDouble("price");
            final ParticleBuilder particleBuilder = builderParser.parse();

            final CustomParticle customParticle = CustomParticle.builder()
                    .identifier(key)
                    .material(material)
                    .displayName(displayName)
                    .permission(permission)
                    .description(description)
                    .slot(slot)
                    .price(price)
                    .particleBuilder(particleBuilder)
                    .build();

            this.particleCache.put(customParticle.getIdentifier(), customParticle);
        });
    }
}