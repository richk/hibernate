package org.hibernate.tutorial;

import org.hibernate.Session;

import java.util.*;

import org.hibernate.tutorial.domain.Event;
import org.hibernate.tutorial.domain.Person;
import org.hibernate.tutorial.util.HibernateUtil;

public class EventManager {
  public static void main(String[] args) {
    EventManager mgr = new EventManager();
      if (args[0].equals("store")) {
        mgr.createAndStoreEvent("My Event", new Date());
      }
      else if (args[0].equals("list")) {
        List events = mgr.listEvents();
        for (int i = 0; i < events.size(); i++) {
          Event theEvent = (Event) events.get(i);
          System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
        }
      }
      else if (args[0].equals("addpersontoevent")) {
        Long eventId = mgr.createAndStoreEvent("My Event", new Date());
        Long personId = mgr.createAndStorePerson("Foo", "Bar");
        mgr.addPersonToEvent(personId, eventId);
        System.out.println("Added person " + personId + " to event " + eventId);
      }
      else if (args[0].equals("addemailtoperson")) {
        Long personId = mgr.createAndStorePerson("Foo1", "Bar1");
        mgr.addEmailToPerson(personId,"foo1@bar1.com");
        System.out.println("Added email foo1@bar1.com to person " + personId);
      }
      HibernateUtil.getSessionFactory().close();
  }
  
  private Long createAndStoreEvent(String title, Date theDate) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Event theEvent = new Event();
    theEvent.setTitle(title);
    theEvent.setDate(theDate);
    session.save(theEvent);
    session.getTransaction().commit();
    return theEvent.getId();
  }

  private Long createAndStorePerson(String firstname, String lastname) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Person thePerson = new Person();
    thePerson.setFirstname(firstname);
    thePerson.setLastname(lastname);
    session.save(thePerson);
    session.getTransaction().commit();
    return thePerson.getId();
  }
  
  private List listEvents() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List result = session.createQuery("from Event").list();
    session.getTransaction().commit();
    return result;
  }

  private void addPersonToEvent(Long personId, Long eventId) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Person aPerson = (Person) session.load(Person.class, personId);
    Event anEvent = (Event) session.load(Event.class, eventId);
    aPerson.getEvents().add(anEvent);
    session.getTransaction().commit();
  }

  private void addEmailToPerson(Long personId, String emailAddress) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Person aPerson = (Person) session.load(Person.class, personId);
    // adding to the emailAddress collection might trigger a lazy load of the collection
    aPerson.getEmailAddresses().add(emailAddress);
    session.getTransaction().commit();
  }
}
