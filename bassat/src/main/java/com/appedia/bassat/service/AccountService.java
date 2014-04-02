package com.appedia.bassat.service;

import com.appedia.bassat.domain.Account;
import com.appedia.bassat.domain.User;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface AccountService {

    /**
     *
     * @param userId
     * @return
     */
    List<Account> getUserAccounts(long userId);

    /**
     *
     * @param user
     * @param identifier
     * @return
     */
    public boolean hasAccount(User user, String identifier);

}
