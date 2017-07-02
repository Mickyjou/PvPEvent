package de.mickyjou.plugins.pvpevent.utils;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import de.mickyjou.plugins.pvpevent.events.PlayerWarningsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Mickyjou on 22.01.2017.
 */
public class StatsGetter {

    private UUID uuid;

    public StatsGetter(Player p) {
        this.uuid = p.getUniqueId();
    }

    public StatsGetter(OfflinePlayer p) {
        this.uuid = p.getUniqueId();
    }

    public StatsGetter(UUID uuid) {
        this.uuid = uuid;
    }

    public PlayerDataStore getPlayerStore() {
        return PvPEventPlugin.getPDSService().getStore(uuid);
    }

    public int getKills() {
        return getPlayerStore().get("kills") != null ? Integer.valueOf(getPlayerStore().get("kills")) : 0;
    }

    public void setTeam(String team) {
        getPlayerStore().put("team", team);
    }

    public boolean isBanned() {
        return getPlayerStore().get("banned") != null ? Boolean.valueOf(getPlayerStore().get("banned")) : false;
    }

    public void banPlayer() {
        getPlayerStore().put("banned", String.valueOf(true));
    }


    public void setTeamMate(UUID uuid) {
        getPlayerStore().put("teammate", String.valueOf(uuid));
    }

    public UUID getTeamMate() {
        return UUID.fromString(getPlayerStore().get("teammate"));
    }


    public boolean hasTeam() {
        if (getPlayerStore().get("team") != null) return true;
        return false;
    }

    public String getTeam() {
        return getPlayerStore().get("team");
    }

    public boolean isAlive() {
        if (getPlayerStore().get("alive") == null) {
            return true;
        }
        return getPlayerStore().get("alive") == "true" ? true : false;
    }


    public void addKill() {
        getPlayerStore().put("kills", String.valueOf(getKills() + 1));
    }

    public void addWarning() {
        Bukkit.getPluginManager().callEvent(new PlayerWarningsChangeEvent(Bukkit.getOfflinePlayer(uuid), getWarnings(), getWarnings() + 1));
        getPlayerStore().put("warnings", String.valueOf(getWarnings() + 1));
    }

    public void removeWarning() {
        if (getWarnings() >= 1) {
            Bukkit.getPluginManager().callEvent(new PlayerWarningsChangeEvent(Bukkit.getOfflinePlayer(uuid), getWarnings(), getWarnings() - 1));
            getPlayerStore().put("warnings", String.valueOf(getWarnings() - 1));
        }
    }

    ;

    public int getWarnings() {
        return getPlayerStore().get("warnings") == null ? 0 : Integer.valueOf(getPlayerStore().get("warnings"));
    }

    public int getSessions() {
        return getPlayerStore().get("sessions") == null ? 0 : Integer.valueOf(getPlayerStore().get("sessions"));
    }

    public void setSessions(int sessions) {
        getPlayerStore().put("sessions", String.valueOf(sessions));
    }

    public void setLobby(boolean inLobby) {
        getPlayerStore().put("lobby", String.valueOf(inLobby));
    }

    public boolean isInLobby() {
        return getPlayerStore().get("lobby") != null ? Boolean.valueOf(getPlayerStore().get("lobby")) : false;
    }

    public void saveInventory(Inventory inventory) {
        getPlayerStore().put("inventory", ItemSerialization.inventoryToString(inventory));
    }

    public Inventory getInventory(Player p) {
            return ItemSerialization.stringToInventory(getPlayerStore().get("inventory"));
    }

    public void set(String string1, String string2) {
        getPlayerStore().put(string1, string2);
    }

    public void set(String string1, int string2) {
        getPlayerStore().put(string1, String.valueOf(string2));
    }

    public void set(String string1, double string2) {
        getPlayerStore().put(string1, String.valueOf(string2));
    }


    public String get(String string1) {
        return getPlayerStore().get(string1) != null ? getPlayerStore().get(string1) : null;
    }


    public void saveSurvivalStats(Player p) {
        saveInventory(p.getInventory());
        set("level", p.getLevel());
        set("food", p.getFoodLevel());
        set("health", p.getHealth());
        set("location", Utils.getStringLocation(p.getLocation()));


    }

    public void setSurvivalStats(Player p) {

        StatsGetter stats = new StatsGetter(p);
        stats.setLobby(false);

        if (get("level") == null) {
            p.getInventory().clear();
            p.setExp(0);
            p.setLevel(0);
            p.setFoodLevel(20);
            p.setHealth(20);
            p.teleport(new Location(Bukkit.getWorld("world"), 0, 80, 0));
            return;
        }

        p.getInventory().clear();
        p.getInventory().setContents(getInventory(p).getContents());
        p.setLevel(Integer.valueOf(get("level")));
        p.setFoodLevel(Integer.valueOf(get("food")));
        p.setHealth(Double.parseDouble(get("health")));
        p.teleport(Utils.getLocationString(get("location")));

    }


    public void addSurivalItem(ItemStack toAdd, Player p) {
        Inventory inv = getInventory(p);
        inv.addItem(toAdd);
        saveInventory(inv);
    }


}
