package com.github.alwaysdarkk.particles.command;

import com.github.alwaysdarkk.particles.particle.cache.ParticleCache;
import com.github.alwaysdarkk.particles.particle.data.CustomParticle;
import com.github.alwaysdarkk.particles.user.cache.ParticleUserCache;
import com.github.alwaysdarkk.particles.user.data.ParticleUser;
import com.github.alwaysdarkk.particles.user.repository.ParticleUserRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ParticleStopCommand implements CommandExecutor {

    private final ParticleUserCache userCache;
    private final ParticleUserRepository userRepository;
    private final ParticleCache particleCache;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        final ParticleUser user = this.userCache.get(player.getName());
        if (user == null) return false;

        if (!user.hasActiveParticle()) {
            player.sendMessage("§cYou don't have any active particle.");
            return false;
        }

        final String particleIdentifier = user.getParticle();
        final CustomParticle particle = this.particleCache.get(particleIdentifier);
        if (particle == null) return false;

        user.setParticle(null);
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                String.format("lp user %s permission set %s false", player.getName(), particle.getPermission()));
        this.userRepository.updateOne(user);

        player.sendMessage("§aNice! You have removed your active particle.");
        return true;
    }
}