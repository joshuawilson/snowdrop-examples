package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * @author Marius Bogoevici</a>
 */
@Embeddable
public class Name implements Serializable {

    private static final long serialVersionUID = -2471594116346509511L;

    @Basic
    private String firstName;

    @Basic
    private String middleName;

    @Basic
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
