package de.mickyjou.plugins.pvpevent;

import de.craften.plugins.bkcommandapi.SubCommandHandler;
import de.craften.plugins.mcguilib.View;
import de.craften.plugins.mcguilib.ViewManager;
import de.mickyjou.plugins.pvpevent.commands.GenerateEventChunksCommand;
import de.mickyjou.plugins.pvpevent.commands.SchematicCommands;
import de.mickyjou.plugins.pvpevent.commands.SkillsCommand;
import de.mickyjou.plugins.pvpevent.listener.PlayerInteractListener;
import de.mickyjou.plugins.pvpevent.listener.PlayerMoveListener;
import de.mickyjou.plugins.pvpevent.listener.PortalCreateListener;
import de.mickyjou.plugins.pvpevent.shop.SkillsView;
import de.mickyjou.plugins.pvpevent.utils.SchematicManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PvPEventPlugin extends JavaPlugin {

    SchematicManager sm;
    public static String prefix = ChatColor.GOLD + "[PvPEvent] " + ChatColor.GRAY;
    static ViewManager vm;
    static SkillsView skw;

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        sm = new SchematicManager(this);
        loadFiles();
        vm = new ViewManager(this);
        skw = new SkillsView(vm);
        super.onEnable();

    }

    @Override
    public void onDisable() {

        super.onDisable();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    /**
     * Register all de.mickyjou.plugins.pvpevent.commands
     */
    public void registerCommands() {

        SubCommandHandler skillsCommandHandler = new SubCommandHandler("skills") {
            @Override
            protected void onInvalidCommand(CommandSender commandSender) {
                commandSender.sendMessage(ChatColor.RED + "Unknown command.");
            }

            @Override
            protected void onPermissionDenied(CommandSender commandSender, Command command, String[] strings) {
                commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            }
        };


        getCommand("schematic").setExecutor(new SchematicCommands(this));

        skillsCommandHandler.addHandlers(new SkillsCommand(this));
        getCommand("skills").setExecutor(skillsCommandHandler);

        getCommand("generate").setExecutor(new GenerateEventChunksCommand());

    }

    /**
     * register all Events
     */

    public void registerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PortalCreateListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerInteractListener(this), this);
    }

    private void loadFiles() {
        File file = new File(this.getDataFolder().getAbsolutePath() + "/schematics");
        if (!file.exists())
            file.mkdirs();

    }

    public static ViewManager getViewManager() {
        return vm;
    }

    public static SkillsView getSkillsView() {
        return skw;
    }

}
