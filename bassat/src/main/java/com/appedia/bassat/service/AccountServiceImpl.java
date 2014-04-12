package com.appedia.bassat.service;

import com.appedia.bassat.domain.Account;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Muz Omar
 */
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     *
     * @param userId
     * @return
     */
    public List<Account> getUserAccounts(long userId) {
        return accountMapper.getUserAccounts(userId);
    }

    /**
     *
     * @param user
     * @param identifier
     * @return
     */
    public boolean checkUserHasAccount(User user, String identifier) {
        List<Account> accounts = getUserAccounts(user.getUserId());
        if (accounts != null) {
            for (Account account : accounts) {
                if (account.getIdentifier().equals(identifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param identifier
     * @param performPartialMatch
     * @return
     */
    public List<Account> getAccountsByIdentifier(String identifier, boolean performPartialMatch) {
        if (performPartialMatch) {
            return Arrays.asList(new Account[] { accountMapper.getAccountsByIdentifier(identifier) });
        } else {
            return accountMapper.getAccountsByPartialIdentifier(identifier);
        }
    }

}
