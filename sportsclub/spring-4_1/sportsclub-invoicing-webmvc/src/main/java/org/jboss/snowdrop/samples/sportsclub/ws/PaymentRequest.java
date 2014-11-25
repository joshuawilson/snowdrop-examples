package org.jboss.snowdrop.samples.sportsclub.ws;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Marius Bogoevici
 */

@XmlRootElement(namespace = "http://ws.sportsclub.samples.snowdrop.jboss.org/", name = "notifyPayment")
@XmlType(name = "notifyPayment")
public class PaymentRequest {

    @XmlElement(name = "amount", namespace = "")
    private BigDecimal amount;

    @XmlElement(name = "accountNumber", namespace = "")
    private Long accountNumber;

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}
