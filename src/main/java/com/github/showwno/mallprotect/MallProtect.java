package com.github.showwno.mallprotect;

import com.github.showwno.mallprotect.Command.MallProtectCommand;
import com.github.showwno.mallprotect.Listener.ProtectEvent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MallProtect extends JavaPlugin{
    private MallProtectConfig mallConfig;

    @Override
    public void onEnable() {
        mallConfig = new MallProtectConfig(this);
        if(mallConfig.getLocationStructureList().size()!=0){
            new ProtectEvent(this);
        }
        new MallProtectCommand(this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    public void reloadPlugin(){
        HandlerList.unregisterAll();
        mallConfig.saveLocationsData();
        mallConfig = new MallProtectConfig(this);
        if(mallConfig.getLocationStructureList().size()!=0){
            new ProtectEvent(this);
        }
    }

    public MallProtectConfig getMallConfig() {
        return mallConfig;
    }
}
