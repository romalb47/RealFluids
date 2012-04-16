package mindless728.RealFluids;

import org.bukkit.Bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.util.HashMap;

public class RealFluidsPlayerListener implements Listener {
	RealFluids plugin;
	ItemStack stack;

	public RealFluidsPlayerListener(RealFluids p) {
		plugin = p;
		stack = null;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		RealFluidsBlock block = plugin.getBlock(event.getBlockClicked().getRelative(event.getBlockFace()).getLocation());
		if(event.getBucket() == Material.LAVA_BUCKET)
			block.setLevel(plugin.lavaStartLevel);
		else if(event.getBucket() == Material.WATER_BUCKET)
			block.setLevel(plugin.waterStartLevel);
		plugin.addFlowEvent(block);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		RealFluidsBlock block = plugin.getBlock(event.getBlockClicked().getLocation());
		block.setLevel(0);
		plugin.addFlowEvent(block);
		for(RealFluidsBlock rfb : plugin.getAdjacentBlocks(block)) {
			plugin.addFlowEvent(rfb);
		}
	}

	//@TODO grab when a player logs in
	//@TODO grab when a player logs out
}
