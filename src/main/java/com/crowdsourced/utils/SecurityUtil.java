package com.crowdsourced.utils;

import java.security.Principal;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import com.crowdsourced.wasteplatform.entity.User;
import com.crowdsourced.wasteplatform.exception.AppException;
import com.crowdsourced.wasteplatform.exception.ErrorCode;
import com.crowdsourced.wasteplatform.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final UserRepository userRepository;

    public User getLoginUser(Principal principal){
        String email = principal.getName();
        Optional<User> customerOptional = userRepository.findByEmail(email);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }else {
            throw new AppException(ErrorCode.VALIDATION,"Invalid token.");
        }

    }
}
