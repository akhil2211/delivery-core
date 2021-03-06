package org.delivery.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.delivery.core.entities.Person;
import org.delivery.core.entities.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonHandler {
  private static final List<PersonEntity> personList =
      Collections.synchronizedList(new ArrayList<>());

  static {
    PersonEntity entity1 = PersonEntity.builder().build();
    entity1.setEmail("akhil2211@gmail.com");
    entity1.setEmail("7699092812");
    personList.add(entity1);
    PersonEntity entity2 = PersonEntity.builder().build();
    entity2.setEmail("akhil2211@gmail.com");
    entity2.setEmail("7699092812");
    personList.add(entity2);
  }

  /**
   * Update status of the person either to mark it active or inactive
   * 
   * @param person
   * @return
   */

  public Optional<Person> updatePerson(Person person) {
    synchronized (personList) {
      for (PersonEntity personEntity : personList) {
        if (personEntity.getProfileId().equals(person.getProfileId())) {
          personEntity.setDeliveryIds(person.getDeliveryIds());
          personEntity.setActive(person.isActive());
          return Optional.of(person);
        }
      }
      return Optional.empty();
    }
  }

  /**
   * Get an inactive person who is not handling a delivery at the moment
   * 
   * @param status {@link OrderStatus}
   * @param size max size of the result
   * @return
   */

  public Optional<Person> getPersonByActive(boolean status) {
    synchronized (personList) {
      return personList.stream().filter(p -> !p.isActive()).findAny().map(Person::new);
    }
  }

  /**
   * Get a person by profile id
   * 
   * @param profileId
   * @return
   */

  public Optional<Person> getPersonById(String profileId) {
    synchronized (personList) {
      return personList.stream().filter(p -> p.getProfileId().equals(profileId)).findAny()
          .map(Person::new);
    }
  }

}
