package nmt.minecraft.QuestManager.NPC;

import java.util.UUID;

import nmt.minecraft.QuestManager.QuestManagerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public abstract class NPC implements ConfigurationSerializable, Listener {
	
	/**
	 * Cache value for saving lookup times for entities
	 */
	private Entity entity;
	
	/**
	 * The actual ID of the entity we're monitoring
	 */
	protected UUID id;
	
	protected String name;
	
	protected NPC() {
		Bukkit.getPluginManager().registerEvents(this, QuestManagerPlugin.questManagerPlugin);
	}
	
	/**
	 * Returns the entity this NPC is attached to.<br />
	 * This method attempts to save cycles by caching the last known entity to
	 * represent our UUID'd creature. If the cache is no longer valid, an entire
	 * sweep of worlds and entities is performed to lookup the entity.
	 * @return The entity attached to our UUID, or NULL if none is found
	 */
	public Entity getEntity() {
		if (entity != null && !entity.isDead() && entity.getUniqueId().equals(id)) {
			//still cached
			return entity;
		}
		
		//cache has expired (new entity ID, etc) so grab entity
		for (World w : Bukkit.getWorlds())
		for (Entity e : w.getEntities()) {
			if (e.getUniqueId().equals(id)) {
				entity = e;
				return e;
			}
		}
		
		//unable to find entity!
		return null;
		
	}
	
	/**
	 * Register an entity to this NPC. This method also updates the ID of this npc
	 * @param entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
		this.id = entity.getUniqueId();
	}
	
	/**
	 * Specify the ID used for this entity
	 * @param id
	 */
	public void setID(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getUniqueId().equals(id)) {
			e.setCancelled(true);
			this.interact(e.getPlayer());
		}
	}
	
	protected abstract void interact(Player player);
}
