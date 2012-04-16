package mindless728.RealFluids;

import org.bukkit.Bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.Event;

public class RealFluidsListener implements Listener {
	RealFluids plugin;
	
	public RealFluidsListener(RealFluids p) throws NullPointerException {
		if(p == null)
			throw new NullPointerException();
	    plugin = p;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public final void onCustomEvent(Event event) {
	    if(event.getEventName().equals("RealFlowEvent") && (event instanceof RealFlowEvent))
			onRealFlow((RealFlowEvent)event);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onRealFlow(RealFlowEvent event) {
	}
}
