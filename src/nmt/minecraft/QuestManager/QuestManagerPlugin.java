package nmt.minecraft.QuestManager;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Provided API and Command Line interaction between enlisted quest managers and
 * the user. <br />
 * 
 * @author Skyler
 *
 */
public class QuestManagerPlugin extends JavaPlugin {
	
	public static QuestManagerPlugin questManagerPlugin;
	
	private List<QuestManager> managers;
	
}
