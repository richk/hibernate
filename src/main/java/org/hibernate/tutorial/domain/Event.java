package org.hibernate.tutorial.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Event {
  private Long id;
  private String title;
  private Date date;
  private Set participants = new HashSet();
  
  public Event() {}
  
  public Long getId() {
    return id;
  }
  private void setId(Long id) {
    this.id = id;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  
  protected Set getParticipants() {
    return participants;
  }

  protected void setParticipants(Set participants) {
    this.participants = participants;
  }

  public void addToParticipants(Person person) {
    this.getParticipants().add(person);
    person.getEvents().add(this);
  }

  public void removeFromParticipants(Person person) {
    this.getParticipants().remove(person);
    person.getEvents().remove(this);
  }
}
