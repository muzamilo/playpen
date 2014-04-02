package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.Account;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface AccountMapper {

    /**
     *
     * @param userId
     * @return
     */
    List<Account> getUserAccounts(long userId);

}
