package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Balance implements Serializable {

    private static final long serialVersionUID = 7440283939163691421L;

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void credit(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

    public void debit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }
}
