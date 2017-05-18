package net.hakugyokurou.aeb;

import net.hakugyokurou.aeb.quickstart.EventSubscriber;
import org.huajistudio.chessmaster.api.event.CancelableEvent;
import org.junit.Assert;
import org.junit.Test;

public class EventBusTest {
	class TestEvent extends CancelableEvent {
		String message;
	}

	@Test
	public void testBus() {
		EventBus bus = new EventBus();
		bus.register(new Object() {
			@EventSubscriber(priority = EventSubscriber.PRIORITY_HIGHEST)
			public void onMessage(TestEvent event) {
				event.message = "interesting";
			}
		});
		TestEvent event = new TestEvent();
		event.message = "gg";
		bus.post(event);
		Assert.assertEquals("interesting", event.message);
	}
}
