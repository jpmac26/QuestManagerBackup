package com.SkyIsland.QuestManager.UI.Menu.Action;

import org.bukkit.ChatColor;

import com.SkyIsland.QuestManager.Player.Party;
import com.SkyIsland.QuestManager.Player.QuestPlayer;

/**
 * Boots a player from a party
 * @author Skyler
 *
 */
public class BootFromPartyAction implements MenuAction {
	
	private Party party;
	
	private QuestPlayer other;
	
	public BootFromPartyAction(Party party, QuestPlayer other) {
		this.party = party;
		this.other = other;
	}
	
	@Override
	public void onAction() {
		party.removePlayer(other, ChatColor.DARK_RED + "You've been kicked from the party" + ChatColor.RESET);
	}

}
