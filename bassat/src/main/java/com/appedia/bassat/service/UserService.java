package com.appedia.bassat.service;

import com.appedia.bassat.domain.User;

/**
 *
 * @author Muz Omar
 */
public interface UserService {

    /**
     *
     * @param emailAddress
     * @return
     */
    User getUserByEmail(String emailAddress);

}