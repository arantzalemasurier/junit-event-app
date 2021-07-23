package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.Attendee;
import com.example.Event;
import com.example.EventType;
import com.example.service.EventNotificationServiceImpl;


public class EvenNotifyTest {
	
	/**
	 *  VERSION SIN MOCKS
	 */
	
	@Test
	void testNotify() {
		
		EventNotificationServiceImpl notificationService = new EventNotificationServiceImpl();
		
		Event event = new Event(1L, "Title", EventType.MARKETING, notificationService);
		
		Attendee asistente = new Attendee(1L, "meri", "");
		event.addAttendee(asistente);;
		
		assertEquals(0, asistente.getNotifications().size());
		
		event.notifyAssistants();
		
		assertEquals(1, asistente.getNotifications().size());
		assertEquals("The next big event is coming!", asistente.getNotifications().get(0).getMessage());;
		
	}
}

