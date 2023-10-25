package com.github.alwaysdarkk.particles.command;

import com.github.alwaysdarkk.particles.particle.view.ParticlesView;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ParticlesCommand implements CommandExecutor {

    private final ViewFrame viewFrame;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        this.viewFrame.open(ParticlesView.class, (Player) sender);
        return true;
    }
}