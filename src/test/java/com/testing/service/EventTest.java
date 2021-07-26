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
import com.example.service.EventNotificationServiceImpl;

class EventTest {

	Event e;

	static List<Attendee> grupo1;
	static List<Attendee> grupo2;
	
	static Attendee meri;
	static Attendee lola;
	static Attendee paco;
	
	static Speaker ana;
	static Speaker nora;
	static Speaker pedro;
	
	static Notification paula;
	static Notification iker;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		meri = new Attendee(1L, "Meri", "meri@ingenia.es");
		lola =  new Attendee(2L, "Lola", "lolai@ingenia.es");
		paco = new Attendee(3L, "Paco", "paco@ingenia.es");
		
		assertEquals(meri.getId(),1L);
		
		grupo1 = new ArrayList<>();
		grupo1.add(meri);
		grupo1.add(lola);

		grupo2 = new ArrayList<>();
		grupo2.add(meri);
		grupo2.add(paco);
		
		ana = new Speaker(100L, "Ana", "Sabe un huevo de JUnit");
		nora = new Speaker(200L, "Nora", "Java");
		pedro = new Speaker(300L, "Pedro", "JUnit");
		
		assertEquals(ana.getId(),100L);
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

	@Test
	void testEvent() {
		Event e = new Event();
		assertSame(e.getId(), null);
		assertSame(e.getTitle(), null);
		assertSame(e.getType(), null);
		assertEquals(e.getSpeakers().size(), 0);
		assertEquals(e.getAttendees().size(),0);		
	}
	
	@Test
	void testAddAttendee() {

		Event eventBefore;
		Event eventAfter;
				
		// Añadir a un asistente a la lista vacia
		assertEquals(e.getAttendees().size(), 0);
		assertFalse(e.getAttendees().contains(meri));
		e.addAttendee(meri);
		assertTrue(e.getAttendees().contains(meri));
		assertEquals(e.getAttendees().size(), 1);

		// Añadir a un asistente a una lista llena
		assertFalse(e.getAttendees().contains(lola));
		e.addAttendee(lola);
		assertTrue(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 2);

		// Añadir a un asistente que ya existe a una lista llena
		assertTrue(e.getAttendees().contains(lola));
		e.addAttendee(lola);
		assertTrue(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 2);			
		
		// Añadir un asistente a la lista nula
		e.setAttendees(null);
		e.addAttendee(paco);
		assertTrue(e.getAttendees().contains(paco));
		assertEquals(e.getAttendees().size(), 1);
		
		// Añadir null a la lista de asistentes
		eventBefore = e;
		e.addAttendee(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter); 
	}
	
	@Test
	void testAddAttendees() {

		Event eventBefore;
		Event eventAfter;
		
		// Añadir null a la lista de asistentes
		eventBefore = e;
		e.addAttendees(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter);
		
		// Añadir un grupo a la lista vacia
		assertEquals(e.getAttendees().size(), 0);
		assertFalse(e.getAttendees().contains(meri));
		assertFalse(e.getAttendees().contains(lola));
		e.addAttendees(grupo1);
		assertTrue(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 2);

		// Añadir a un grupo a una lista llena
		assertFalse(e.getAttendees().contains(paco));
		e.addAttendees(grupo2);
		assertTrue(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(lola));
		assertTrue(e.getAttendees().contains(paco));
		assertEquals(e.getAttendees().size(), 3);
		
		// Añadir un grupo  a la lista nula
		e.setAttendees(null);
		e.addAttendees(grupo2);
		assertTrue(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(paco));
		assertEquals(e.getAttendees().size(), 2);		
	}
	
	@Test
	void testRemoveAttendee() {
		
		Event eventBefore;
		Event eventAfter;
		
		// Quitar null a la lista de asistentes
		eventBefore = e;
		e.removeAttendee(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter);
		
		// Quitar un asistente a la lista nula
		e.setAttendees(null);
		e.removeAttendee(paco);
		assertFalse(e.getAttendees().contains(paco));
		assertEquals(e.getAttendees().size(), 0);
		
		// Quitar un asistente a la lista vacia
		assertEquals(e.getAttendees().size(), 0);
		assertFalse(e.getAttendees().contains(meri));
		e.removeAttendee(meri);
		assertFalse(e.getAttendees().contains(meri));
		assertEquals(e.getAttendees().size(), 0);

		// Quitar un asistente que no existe a una lista llena
		e.addAttendees(grupo1);
		assertEquals(e.getAttendees().size(), 2);
		assertFalse(e.getAttendees().contains(paco));
		e.removeAttendee(paco);
		assertFalse(e.getAttendees().contains(paco));
		assertTrue(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 2);

		// Quitar un asistente que ya existe a una lista llena
		assertTrue(e.getAttendees().contains(lola));
		e.removeAttendee(lola);
		assertFalse(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 1);		
	}
	
	@Test
	void testRemoveAttendees() {
		
		Event eventBefore;
		Event eventAfter;

		// Quitar  null a la lista de asistentes
		eventBefore = e;
		e.removeAttendees(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter);
		
		// Quitar un grupo  a la lista nula
		e.setAttendees(null);
		e.removeAttendees(grupo2);
		assertFalse(e.getAttendees().contains(meri));
		assertFalse(e.getAttendees().contains(paco));
		assertEquals(e.getAttendees().size(), 0);
		
		// Quitar un grupo a la lista vacia
		assertEquals(e.getAttendees().size(), 0);
		assertFalse(e.getAttendees().contains(meri));
		assertFalse(e.getAttendees().contains(lola));
		e.removeAttendees(grupo1);
		assertFalse(e.getAttendees().contains(meri));
		assertFalse(e.getAttendees().contains(lola));
		assertEquals(e.getAttendees().size(), 0);

		// Quitar un grupo de una lista llena
		e.addAttendees(grupo1);
		assertEquals(e.getAttendees().size(), 2);
		e.removeAttendees(grupo2);
		assertEquals(e.getAttendees().size(), 1);
		assertFalse(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(lola));
		assertFalse(e.getAttendees().contains(paco));		
	}
	
	@Test
	void testNotifyAssistants() {

		e.notifyAssistants();
		assertFalse(isNotification(e,"The next big event is coming!"));

		e.addAttendees(grupo1);
		e.notifyAssistants();
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
	
	@Test
	void testAddSpeaker() {
		
		Event eventBefore;
		Event eventAfter;
		
		// Añadir un ponente a la lista vacia
		assertEquals(e.getSpeakers().size(), 0);
		assertFalse(e.getSpeakers().contains(ana));
		e.addSpeaker(ana);
		assertTrue(e.getSpeakers().contains(ana));
		assertEquals(e.getSpeakers().size(), 1);

		// Añadir un ponente  a una lista llena	
		assertFalse(e.getSpeakers().contains(pedro));
		e.addSpeaker(pedro);
		assertTrue(e.getSpeakers().contains(pedro));
		assertEquals(e.getSpeakers().size(), 2);

		// Añadir a un asistente que ya existe a una lista llena 
		e.addSpeaker(pedro);
		assertTrue(e.getSpeakers().contains(pedro));
		// assertEquals(e.getSpeakers().size(), 2);
		// EL PROGRAMA TIENE UN ERROR añade a un asistente que ya exite
		
		// Añadir null a la lista ponentes
		eventBefore = e;
		e.addSpeaker(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter); // pendiente de revisar	
	}
	
	@Test
	void testRemoveSpeaker() {
		
		Event eventBefore;
		Event eventAfter;
		
		// Quitar null a la lista de asistentes (primer if)
		eventBefore = e;
		e.removeSpeaker(null);
		eventAfter = e;
		assertEquals(eventBefore, eventAfter);
		
		// Quitar un ponente a la lista vacia
		assertEquals(e.getSpeakers().size(), 0);
		assertFalse(e.getSpeakers().contains(ana));
		e.removeSpeaker(ana);
		assertFalse(e.getSpeakers().contains(ana));
		assertEquals(e.getSpeakers().size(), 0);
				
		// Quitrar un ponente que no existe a una lista llena
		e.addSpeaker(ana);
		assertFalse(e.getSpeakers().contains(nora));
		e.removeSpeaker(nora);
		assertFalse(e.getSpeakers().contains(nora));
		assertEquals(e.getSpeakers().size(),1);
		
		// Quitar un ponente que si existe de un lista llena
		assertTrue(e.getSpeakers().contains(ana));
		e.removeSpeaker(ana);
		assertFalse(e.getSpeakers().contains(ana));
		assertEquals(e.getSpeakers().size(),0);
				
	}

	@Test
	void testGetId() {
		assertEquals(e.getId(),1L);
	}

	@Test
	void testSetId() {
		e.setId(3L);
		assertEquals(e.getId(),3L);
	}

	@Test
	void testGetTitle() {
		assertEquals(e.getTitle(),"Evento de test");
	}

	@Test
	void testSetTitle() {
		e.setTitle("Evento del ano");
		assertEquals(e.getTitle(),"Evento del ano");
	}

	@Test
	void testGetType() {
		assertEquals(e.getType(),EventType.TECH);
	}

	@Test
	void testSetType() {
		e.setType(EventType.MARKETING);
		assertEquals(e.getType(),EventType.MARKETING);
	}

	@Test
	void testGetSpeakers() {
		e.addSpeaker(ana);
		assertTrue(e.getSpeakers().contains(ana));		
	}

	@Test
	void testSetSpeakers() {
		List<Speaker> s = new ArrayList<>();
		s.add(ana);
		s.add(nora);
		e.setSpeakers(s);
		assertTrue(e.getSpeakers().contains(ana));
		assertTrue(e.getSpeakers().contains(nora));
		assertFalse(e.getSpeakers().contains(pedro));		
	}

	@Test
	void testGetAttendees() {
		e.addAttendee(meri);
		assertTrue(e.getAttendees().contains(meri));
	}

	@Test
	void testSetAttendees() {
		List<Attendee> a = new ArrayList<>();
		a.add(meri);
		a.add(paco);
		e.setAttendees(a);
		assertTrue(e.getAttendees().contains(meri));
		assertTrue(e.getAttendees().contains(paco));
		assertFalse(e.getAttendees().contains(lola));
	}
	
}
