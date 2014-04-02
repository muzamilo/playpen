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
        return accountMapper.getUserAccounts(userId);
    }

    /**
     *
     * @param user
     * @param identifier
     * @return
     */
    public boolean hasAccount(User user, String identifier) {
        List<Account> accounts = getUserAccounts(user.getUserId());
        return accounts != null && accounts.contains(identifier);
    }

}
