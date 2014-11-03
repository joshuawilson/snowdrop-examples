package org.jboss.snowdrop.samples.sportsclub.reservations.webflow.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
public class AccountFilter extends AbstractExtendedDataModelHelper implements Serializable {

    private static final long serialVersionUID = -269525937840159759L;

    @Autowired
    private transient AccountService accountService;

    private Account selectedAccount;

    private String subscriberNameFragment;
    private Map<Long, Account> accountMap = new HashMap<Long, Account>();

    public AccountFilter() {
        super();
    }

    public void walk(FacesContext facesContext, DataVisitor dataVisitor, Range range, Object argument) {
        int firstResult = ((SequenceRange) range).getFirstRow();
        int maxResults = ((SequenceRange) range).getRows();
        List<Account> accounts = accountService.findAccounts(firstResult, maxResults, subscriberNameFragment);
        accountMap = new HashMap<Long, Account>();
        for (Account a : accounts) {
            Long id = a.getId();
            accountMap.put(id, a);
            dataVisitor.process(facesContext, id, argument);
        }
    }

    public Map<Long, ? extends Object> getDomainObjectMap() {
        return accountMap;
    }

    private Long getSelectedKey() {
        if (getSelection() == null || getSelection().size() == 0)
            return null;
        else
            return ((Long) getSelection().iterator().next());
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void accountSelected() {
        if (getSelectedKey() != null) {
            this.setSelectedAccount(((Account) getDomainObjectMap().get(getSelectedKey())));
        } else {
            this.setSelectedAccount(null);
        }
    }

    public Long getCurrentRowCount() {
        return accountService.countAccounts(subscriberNameFragment);
    }

    public void searchAccounts() {
        resetCurrentRowCount();
        getRowCount();
        setCurrentPage(1);
    }

    public String getSubscriberNameFragment() {
        return subscriberNameFragment;
    }

    public void setSubscriberNameFragment(String subscriberNameFragment) {
        this.subscriberNameFragment = subscriberNameFragment;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
