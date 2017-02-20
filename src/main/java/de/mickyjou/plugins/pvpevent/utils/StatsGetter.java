package de.mickyjou.plugins.pvpevent.utils;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.mickyjou.plugins.pvpevent.PvPEventPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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


    public UUID getTeamMate(UUID uuid) {
        UUID toReturn = null;
        if (hasTeam()) {
            String team = getTeam();
            for (EventTeam all : Utils.getAllTeams()) {
                if (all.getName().equalsIgnoreCase(team)) {
                    if (all.getPlayers()[0].getUniqueId() == uuid) {
                        toReturn = all.getPlayers()[1].getUniqueId();
                    } else {
                        toReturn = all.getPlayers()[0].getUniqueId();
                    }
                }
            }

        }
        return toReturn;
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
        getPlayerStore().put("warnings", String.valueOf(getWarnings() + 1));
    }

    public int getWarnings() {
        return getPlayerStore().get("warnings") == null ? 0 : Integer.valueOf(getPlayerStore().get("warnings"));
    }

    public int getSessions() {
        return getPlayerStore().get("sessions") == null ? 0 : Integer.valueOf(getPlayerStore().get("sessions"));
    }

    public void setSessions(int sessions) {
        getPlayerStore().put("sessions", String.valueOf(sessions));
    }
}
