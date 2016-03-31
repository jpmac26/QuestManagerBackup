package com.SkyIsland.QuestManager.Player.Skill;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.SkyIsland.QuestManager.QuestManagerPlugin;
import com.SkyIsland.QuestManager.Player.Skill.Event.CombatEvent;
import com.SkyIsland.QuestManager.UI.Menu.Action.ForgeAction;

public class TwoHandedSkill extends Skill implements Listener {
	
	public static final String configName = "TwoHanded.yml";

	public Type getType() {
		return Skill.Type.COMBAT;
	}

	@Override
	public int getStartingLevel() {
		return startingLevel;
	}
	
	@Override
	public String getConfigKey() {
		return "Two_Handed";
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof TwoHandedSkill);
	}
	
	private int startingLevel;
	
	private int levelRate;
	
	private int apprenticeLevel;
	
	public TwoHandedSkill() {
		File configFile = new File(QuestManagerPlugin.questManagerPlugin.getDataFolder(), 
				QuestManagerPlugin.questManagerPlugin.getPluginConfiguration().getSkillPath() + configName);
		YamlConfiguration config = createConfig(configFile);
		
		if (!config.getBoolean("enabled", true)) {
			return;
		}
		
		this.levelRate = config.getInt("startingLevel", 0);
		this.levelRate = config.getInt("levelsperdamageincrease", 10);
		this.apprenticeLevel = config.getInt("apprenticeLevel", 15);
		
		Bukkit.getPluginManager().registerEvents(this, QuestManagerPlugin.questManagerPlugin);
	}
	
	private YamlConfiguration createConfig(File configFile) {
		if (configFile.exists()) {
			YamlConfiguration defaultConfig = new YamlConfiguration();
			
			defaultConfig.set("enabled", true);
			defaultConfig.set("startingLevel", 0);
			defaultConfig.set("levelsperdamageincrease", 10);
			defaultConfig.set("apprenticeLevel", 15);
			
			try {
				defaultConfig.save(configFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return defaultConfig;
		}
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		return config;
	}
	
	@EventHandler
	public void onCombat(CombatEvent e) {
		Player p = e.getPlayer().getPlayer().getPlayer();
		
		if (!ForgeAction.Repairable.isRepairable(p.getInventory().getItemInMainHand().getType())
				|| (p.getInventory().getItemInOffHand() != null && p.getInventory().getItemInOffHand().getType() != Material.AIR)) {
			System.out.println("Not two-handing!");
			return;
		}
		
		int lvl = e.getPlayer().getSkillLevel(this);
		
		//reduce chance to hit if level under apprentice level
		boolean causeMiss = false;
		if (lvl < apprenticeLevel) {
			//3% per level under apprentice -- up to 45%
			int miss = 3 * (apprenticeLevel - lvl); 
			int roll = Skill.random.nextInt(100);
			if (roll <= miss) {
				e.setMiss(true);
				System.out.println("Chance to miss: " + miss + " / 100 [" + roll + "]");
				causeMiss = true;
			}
		}
		
		//just increase damage based on level
		//every n levels, one more damage
		e.setModifiedDamage(e.getModifiedDamage() + (lvl / levelRate));
		
		this.perform(e.getPlayer(), causeMiss); //only get a 'cause miss' if this skill caused it 
		
	}
	
}
