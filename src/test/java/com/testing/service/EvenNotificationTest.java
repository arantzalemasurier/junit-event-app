package com.testing.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Attendee;
import com.example.Event;
import com.example.EventType;
import com.example.Notification;
import com.example.Speaker;
import com.example.service.EventNotificationService;
import com.example.service.EventNotificationServiceImpl;

class EvenNotificationTest {
	
	Event e;

	static List<Attendee> grupo1;
	static List<Attendee> grupo2;
	
	static Attendee meri;
	static Attendee lola;
	static Attendee paco;
	
	static Speaker ana;
	static Speaker nora;
	static Speaker pedro;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		meri = new Attendee(1L, "Meri", "meri@ingenia.es");
		lola =  new Attendee(2L, "Lola", "lolai@ingenia.es");
		paco = new Attendee(3L, "Paco", "paco@ingenia.es");
		
		grupo1 = new ArrayList<>();
		grupo1.add(meri);
		grupo1.add(lola);

		grupo2 = new ArrayList<>();
		grupo2.add(meri);
		grupo2.add(paco);
		
		ana = new Speaker(100L, "Ana", "Sabe un huevo de JUnit");
		nora = new Speaker(200L, "Nora", "Java");
		pedro = new Speaker(300L, "Pedro", "JUnit");	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		e = new Event(1L, "Evento de test", EventType.TECH, new EventNotificationServiceImpl());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	EventNotificationServiceImpl en = new EventNotificationServiceImpl();

	@Test
	void testAnnounce() {

		en.announce(null);
		
		en.announce(e);
		assertFalse(isNotification(e,"The next big event is coming!"));

		e.addAttendees(grupo1);
		en.announce(e);
		assertTrue(isNotification(e,"The next big event is coming!"));
		
		e.setAttendees(null);
		assertEquals(e.getAttendees(), null);			
	}

	private Boolean isNotification(Event e, String m) {
		Boolean esta;
		
		esta = false;
		for (Attendee a : e.getAttendees()) {
			for (Notification n : a.getNotifications()) {
				esta = esta || n.getMessage().equals(m);
			}
		}
		return esta;
	}	
	
	private Boolean isNotification(Attendee a, String m) {
		Boolean esta;
		
		esta = false;
		for (Notification n : a.getNotifications()) {
			esta = esta || n.getMessage().equals(m);
		}				
		return esta;
	}
		
	@Test
	void testConfirmAttendance() {

		en.confirmAttendance(null, paco);
		en.confirmAttendance(e, null);

		assertFalse(isNotification(meri,"Dear Attendee, your subscription to the event has been confirmed successfully."));
		en.confirmAttendance(e, meri);
		assertTrue(isNotification(meri,"Dear Attendee, your subscription to the event has been confirmed successfully."));
		en.confirmAttendance(e, meri);
		assertTrue(isNotification(meri,"Dear Attendee, your subscription to the event has been confirmed successfully."));
		assertFalse(isNotification(meri,"The next big event is coming!"));
	}

}
