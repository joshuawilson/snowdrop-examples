package org.jboss.snowdrop.samples.sportsclub.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PersonRepository;
import org.springframework.stereotype.Component;

/**
 * @author Marius Bogoevici</a>
 */
@Component
public class HibernatePersonRepository extends HibernateRepository<Person, Long> implements PersonRepository {

    public HibernatePersonRepository() {
        super(Person.class);
    }

}
