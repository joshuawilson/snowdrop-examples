package org.jboss.snowdrop.samples.sportsclub.ws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Marius Bogoevici
 */
@XmlRootElement(namespace = "http://ws.sportsclub.samples.snowdrop.jboss.org/", name = "notifyPaymentResponse")
@XmlType(name = "notifyPaymentResponse")
public class PaymentResponse {

    @XmlElement(name = "return")
    private Long paymentId;

    public PaymentResponse() {
    }

    public PaymentResponse(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
