package com.SkyIsland.QuestManager.UI.Menu.Action;

import com.SkyIsland.QuestManager.Configuration.Utils.YamlWriter;
import com.SkyIsland.QuestManager.Player.PlayerOptions;
import com.SkyIsland.QuestManager.Player.QuestPlayer;

/**
 * Toggles a specific player option
 * @author Skyler
 *
 */
public class TogglePlayerOptionAction implements MenuAction {
	
	private QuestPlayer player;
	
	private PlayerOptions.Key key;
	
	private static final String resultMessage = "%s is now set to %s";
	
	public TogglePlayerOptionAction(QuestPlayer player, PlayerOptions.Key key) {
		this.player = player;
		this.key = key;
	}
	
	@Override
	public void onAction() {
		//Set the player option. Then update icon
		
		if (!player.getPlayer().isOnline()) {
			return;
			//something fishy happened...
		}
		
		player.getOptions().setOption(key, !player.getOptions().getOption(key));
		
		//do another lookup to ensure reported is correct
		boolean ret = player.getOptions().getOption(key);
		
		player.getPlayer().getPlayer().sendMessage(String.format(resultMessage, 
				YamlWriter.toStandardFormat(key.name()),
				(ret ? "on" : "off")
				));
		
	}

}
