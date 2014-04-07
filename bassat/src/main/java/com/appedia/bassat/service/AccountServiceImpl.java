package com.appedia.bassat.service;

import com.appedia.bassat.domain.Account;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
        System.out.println("Find user accounts for " + userId);
        List<Account> results = accountMapper.getUserAccounts(userId);
        System.out.println("Found " + results.size() + " results");
        return results;
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

}
