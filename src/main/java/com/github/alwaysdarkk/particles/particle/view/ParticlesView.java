package com.github.alwaysdarkk.particles.particle.view;

import com.github.alwaysdarkk.particles.hook.VaultHook;
import com.github.alwaysdarkk.particles.particle.cache.ParticleCache;
import com.github.alwaysdarkk.particles.particle.data.CustomParticle;
import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.github.alwaysdarkk.particles.user.repository.ParticleUserRepository;
import com.github.alwaysdarkk.particles.util.NumberFormatter;
import com.github.alwaysdarkk.particles.util.item.impl.ItemStackBuilder;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewSlotClickContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class ParticlesView extends View {

    private final ParticleCache particleCache;
    private final ParticleUserCache userCache;
    private final ParticleUserRepository userRepository;
    private final VaultHook vaultHook;

    public ParticlesView(
            ParticleCache particleCache,
            ParticleUserCache userCache,
            ParticleUserRepository userRepository,
            VaultHook vaultHook) {
        super(6, "Particles");
        this.setCancelOnClick(true);
        this.particleCache = particleCache;
        this.userCache = userCache;
        this.userRepository = userRepository;
        this.vaultHook = vaultHook;
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        final Player player = context.getPlayer();
        final ParticleUser user = this.userCache.get(player.getName());
        if (user == null) return;

        this.particleCache.values().stream().filter(Objects::nonNull).forEach(particle -> {
            context.slot(particle.getSlot())
                    .onRender(render -> render.setItem(buildIcon(particle, player, user)))
                    .onClick(handleClick(particle, player, user));
        });
    }

    private ItemStack buildIcon(@NotNull CustomParticle particle, @NotNull Player player, @NotNull ParticleUser user) {
        final ItemStackBuilder itemStackBuilder = new ItemStackBuilder(particle.getMaterial())
                .displayName(particle.getDisplayName())
                .lore(particle.getDescription())
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (!user.hasParticle(particle.getIdentifier())) {
            final double price = particle.getPrice();
            itemStackBuilder.addLore(
                    "",
                    "§f Price: §7" + NumberFormatter.format(price),
                    "",
                    vaultHook.has(player.getName(), price) ? "§aClick to buy" : "§cYou don't have money.");
        } else {
            itemStackBuilder.addLore(
                    "", user.isCurrent(particle.getIdentifier()) ? "§aClick to un-equip." : "§aClick to equip.");
        }

        return itemStackBuilder.build();
    }

    private Consumer<ViewSlotClickContext> handleClick(
            @NotNull CustomParticle particle, @NotNull Player player, @NotNull ParticleUser user) {
        return handler -> {
            this.close();

            if (!user.hasParticle(particle.getIdentifier())) {
                final double price = particle.getPrice();
                if (!vaultHook.has(player.getName(), price)) {
                    player.sendMessage(String.format(
                            "§cYou need §f%s §c of money to buy this particle.", NumberFormatter.format(price)));
                    return;
                }

                user.addParticle(particle.getIdentifier());
                this.userRepository.updateOne(user);

                player.sendMessage("§aNice! Use /particles to equip your new particle.");
                return;
            }

            if (user.hasActiveParticle()) {
                final String oldParticleIdentifier = user.getParticle();
                final CustomParticle oldParticle = this.particleCache.get(oldParticleIdentifier);
                if (oldParticle == null) return;

                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        String.format("lp user %s permission set %s false", player.getName(), oldParticle.getPermission()));
                user.setParticle(null);
                this.userRepository.updateOne(user);

                player.sendMessage("§aNice! You un-equipped the particle " + oldParticle.getDisplayName());

                if (!particle.getIdentifier().equalsIgnoreCase(oldParticleIdentifier)) {
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            String.format("lp user %s permission set %s true", player.getName(), particle.getPermission()));
                    user.setParticle(particle.getIdentifier());
                    this.userRepository.updateOne(user);

                    player.sendMessage("§aNice! You equipped the particle " + particle.getDisplayName());
                }

                return;
            }

            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    String.format("lp user %s permission set %s true", player.getName(), particle.getPermission()));
            user.setParticle(particle.getIdentifier());
            this.userRepository.updateOne(user);

            player.sendMessage("§aNice! You equipped the particle " + particle.getDisplayName());
        };
    }
}