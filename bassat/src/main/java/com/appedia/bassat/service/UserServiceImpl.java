package com.appedia.bassat.service;

import com.appedia.bassat.domain.User;
import com.appedia.bassat.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Muz Omar
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param emailAddress
     * @return
     */
    @Override
    public User getUserByEmail(String emailAddress) {
        return userMapper.getUserByEmail(emailAddress);
    }
}
