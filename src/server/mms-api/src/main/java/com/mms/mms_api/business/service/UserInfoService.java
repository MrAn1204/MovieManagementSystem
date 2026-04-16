package com.mms.mms_api.business.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;
import com.mms.mms_api.security.UserInfo;
import lombok.AllArgsConstructor;

/**
 * Bridges application users to Spring Security's {@link UserDetailsService} contract.
 */
@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {
    private UserRepository userRepository;

    /**
     * Loads security user details by username.
     *
     * @param username login username
     * @return Spring Security user details
     * @throws UsernameNotFoundException when the user cannot be resolved
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new ResourceNotFoundException("user.notFound");
        }

        return new UserInfo(user); 
    }
}
