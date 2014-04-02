package com.appedia.bassat.persistence;

import com.appedia.bassat.domain.User;

/**
 *
 * @author Muz Omar
 */
public interface UserMapper {

    /**
     *
     * @param emailAddress
     * @return
     */
    User getUserByEmail(String emailAddress);

}
