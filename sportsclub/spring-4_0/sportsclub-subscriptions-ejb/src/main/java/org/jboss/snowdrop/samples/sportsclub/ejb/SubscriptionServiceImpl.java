package org.jboss.snowdrop.samples.sportsclub.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.BillingType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.MembershipRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.AccountSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.InvoiceSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.PersonSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
import org.springframework.beans.factory.access.el.SpringBeanELResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class SubscriptionServiceImpl implements SubscriptionService
{

   @Autowired
   private AccountRepository accountRepository;

   @Autowired
   private MembershipRepository membershipRepository;

   public List<Account> findAccountsBySubscriberName(String name, int minIndex, int maxIndex)
   {
      PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
      personSearchCriteria.setName(name);
      AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
      accountSearchCriteria.setPersonSearchCriteria(personSearchCriteria);
      accountSearchCriteria.setActiveOnly(true);
      accountSearchCriteria.setRange(new Range(minIndex, maxIndex));
      return accountRepository.findByCriteria(accountSearchCriteria);
   }

   public List<Account> findAccountsBySubscriberName(String name, int minIndex, int maxIndex, boolean currentInvoice)
   {
      PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
      personSearchCriteria.setName(name);
      InvoiceSearchCriteria invoiceSearchCriteria = new InvoiceSearchCriteria(new Date());
      invoiceSearchCriteria.setExistingInvoice(currentInvoice);
      AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
      accountSearchCriteria.setPersonSearchCriteria(personSearchCriteria);
      accountSearchCriteria.setInvoiceSearchCriteria(invoiceSearchCriteria);
      accountSearchCriteria.setActiveOnly(true);
      accountSearchCriteria.setRange(new Range(minIndex, maxIndex));
      return accountRepository.findByCriteria(accountSearchCriteria);
   }

   public int countAccountsBySubscriberName(String name)
   {
      PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
      personSearchCriteria.setName(name);
      AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
      accountSearchCriteria.setActiveOnly(true);
      accountSearchCriteria.setPersonSearchCriteria(personSearchCriteria);
      return new Long(accountRepository.countByCriteria(accountSearchCriteria)).intValue();
   }

   public Account createAccount(Person person, String membershipCode, BillingType billingType)
   {
      Account account = new Account();
      account.setSubscriber(person);
      account.setClosed(false);
      membershipRepository.findById(membershipCode);
      account.setMembership(membershipRepository.findById(membershipCode));
      account.setBillingType(billingType);
      account.setCreationDate(new Date());
      account = accountRepository.save(account);
      return account;
   }

   public Account findAccountById(Long id)
   {
      return accountRepository.findById(id);
   }

   public List<String> getMembershipTypes()
   {
      return membershipRepository.findAllMembershipCodes();
   }

   public void closeAccount(Account account)
   {
      account.setClosed(true);
      account.setCloseDate(new Date());
      accountRepository.save(account);
   }

   public void updateAccount(Account account)
   {
      accountRepository.save(account);
   }
}
