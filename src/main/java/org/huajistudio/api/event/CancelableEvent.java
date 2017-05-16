package org.huajistudio.api.event;

import net.hakugyokurou.aeb.Cancelable;

public abstract class CancelableEvent implements Cancelable {
	private boolean cancelled = false;

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled() {
		this.cancelled = true;
	}
}
