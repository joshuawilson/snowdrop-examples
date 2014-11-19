package org.jboss.snowdrop.samples.sportsclub.ws;

import org.jboss.snowdrop.samples.sportsclub.payment.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

/**
 * @author Marius Bogoevici
 */

@Endpoint
public class PaymentNotificationHandler {

    @Autowired
    private PaymentProcessor paymentProcessor;

    @PayloadRoot(localPart = "notifyPayment", namespace = "http://ws.sportsclub.samples.snowdrop.jboss.org/")
    public PaymentResponse notifyPayment(PaymentRequest paymentNotification) {
        Long paymentId = paymentProcessor.processPayment(paymentNotification.getAccountNumber(), paymentNotification.getAmount());
        return new PaymentResponse(paymentId);
    }

}
