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
    boolean checkUserHasAccount(User user, String identifier);

    /**
     *
     * @param identifier
     * @return
     */
    List<Account> getAccountsByIdentifier(String identifier, boolean performPartialMatch);

}
