package com.appedia.bassat.service;

import com.appedia.bassat.domain.User;

import java.util.List;

/**
 *
 * @author Muz Omar
 */
public interface UserService {

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
