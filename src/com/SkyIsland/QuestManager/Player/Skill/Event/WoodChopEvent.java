package com.SkyIsland.QuestManager.Player.Skill.Event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.SkyIsland.QuestManager.Player.QuestPlayer;
import com.SkyIsland.QuestManager.Player.Skill.QualityItem;

public class WoodChopEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	private QuestPlayer player;
	
	private QualityItem result;
	
	private int difficulty;
	
	private double timingModifier;
	
	private double amountModifier;
	
	private double hitsModifier;
	
	private double swingTimeModifier;
	
	private double qualityModifier;
	
	private boolean isCancelled;
	
	public WoodChopEvent(QuestPlayer player, QualityItem result, int difficulty) {
		this.player = player;
		this.result = result;
		this.difficulty = difficulty;
		this.isCancelled = false;
		
		this.timingModifier = this.amountModifier = this.hitsModifier = this.swingTimeModifier
				= this.qualityModifier = 1.0;
	}

	public double getTimingModifier() {
		return timingModifier;
	}

	public void setTimingModifier(double timingModifier) {
		this.timingModifier = timingModifier;
	}

	public double getAmountModifier() {
		return amountModifier;
	}

	public void setAmountModifier(double amountModifier) {
		this.amountModifier = amountModifier;
	}

	public double getHitsModifier() {
		return hitsModifier;
	}

	public void setHitsModifier(double hitsModifier) {
		this.hitsModifier = hitsModifier;
	}

	public double getSwingTimeModifier() {
		return swingTimeModifier;
	}

	public void setSwingTimeModifier(double swingTimeModifier) {
		this.swingTimeModifier = swingTimeModifier;
	}

	public double getQualityModifier() {
		return qualityModifier;
	}

	public void setQualityModifier(double qualityModifier) {
		this.qualityModifier = qualityModifier;
	}

	public QuestPlayer getPlayer() {
		return player;
	}

	/**
	 * Returns a copy of the result item. <b>Note:</b> Changes to the quality here are not supported, and will
	 * not be carried over. To affect the quality of the result, use {@link #setQualityModifier(double)}
	 * @return
	 */
	public QualityItem getResult() {
		return result;
	}

	public int getDifficulty() {
		return difficulty;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}
	
}
