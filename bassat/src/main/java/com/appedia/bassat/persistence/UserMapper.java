package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.User;

/**
 *
 * @author Muz Omar
 */
public interface UserMapper {

    /**
     *
     * @param userId
     * @return
     */
    User getUserById(long userId);

    /**
     *
     * @param emailAddress
     * @return
     */
    User getUserByEmail(String emailAddress);

}
