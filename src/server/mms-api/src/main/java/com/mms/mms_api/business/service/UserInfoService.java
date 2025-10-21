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

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new ResourceNotFoundException("user.notFound");
        }

        return new UserInfo(user); 
    }
}
