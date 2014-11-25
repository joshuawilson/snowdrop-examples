package org.jboss.spring.samples.sportsclub.invoicing.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Balance;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Invoice;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Payment;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.InvoiceRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class BillingServiceImpl implements BillingService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice generateInvoice(Account account) {
        Invoice invoice = new Invoice();
        invoice.setAccount(account);
        invoice.setAmount(account.getFeePerBillingPeriod());
        Date date = new Date();
        invoice.setIssueDate(date);
        invoice.setBillingPeriod(account.getBillingPeriodFor(date));
        invoice = invoiceRepository.save(invoice);
        Balance balance = account.getBalance();
        balance.debit(invoice.getAmount());
        accountRepository.save(account);
        return invoice;
    }

    public List<Invoice> getInvoices(Account account) {
        return invoiceRepository.findForAccount(account);
    }

    public List<Payment> getPayments(Account account) {
        return paymentRepository.findForAccount(account);
    }

}
