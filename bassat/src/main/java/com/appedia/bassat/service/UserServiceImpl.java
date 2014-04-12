package com.appedia.bassat.service;

import com.appedia.bassat.domain.Account;
import com.appedia.bassat.domain.User;
import com.appedia.bassat.persistence.AccountMapper;
import com.appedia.bassat.persistence.UserMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Muz Omar
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(long userId) {
        return userMapper.getUserById(userId);
    }

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
