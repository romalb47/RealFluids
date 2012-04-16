/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mindless728.RealFluids;

import org.bukkit.Bukkit;

import org.bukkit.block.Block;
//import org.bukkit.block.BlockDamageLevel;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author colin
 */
public class RealFluidsBlockListener implements Listener {
    RealFluids plugin;
    
    RealFluidsBlockListener(RealFluids p) {
	plugin = p;
	Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
		RealFluidsBlock rfblock = plugin.getBlock(block.getLocation());
		//add flow event if block can flow
		if(block.getTypeId() == 8 || block.getTypeId() == 9 ||
			block.getTypeId() == 10 || block.getTypeId() == 11) {
			if(block.getTypeId() == 8 || block.getTypeId() == 9)
				rfblock.setLevel(plugin.waterStartLevel);
			else
				rfblock.setLevel(plugin.lavaStartLevel);
			plugin.addFlowEvent(rfblock);
		}
		//try to remove a flow event at that location if it can't
		else
			rfblock.setLevel(0);
			
		//check for block flow adjacent to block placement
		checkForAdjacentBlockUpdate(event.getBlock());
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFromTo(BlockFromToEvent event) {
	//cancel the flow event as it is not wanted
        event.setCancelled(true);
    }
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		checkForAdjacentBlockUpdate(event.getBlock());
	}
	
	private void checkForAdjacentBlockUpdate(Block b) {
		RealFluidsBlock rfb = plugin.getBlock(b.getLocation());
		int tempId = 0;
		//System.out.println("Block Damaged: "+event.getDamageLevel());
		//System.out.println("Block Broken: "+rfb.getTypeId());
		for(RealFluidsBlock block : plugin.getAdjacentBlocks(rfb)) {
			tempId = block.getTypeId();
			//System.out.println("Block Adjacent: "+tempId);
			if(tempId >= 8 && tempId <= 11) {
				plugin.addFlowEvent(block);
			}
		}
	}
}
