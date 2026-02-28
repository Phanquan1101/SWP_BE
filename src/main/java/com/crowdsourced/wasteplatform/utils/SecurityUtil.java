package com.crowdsourced.wasteplatform.utils;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

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
        String id = principal.getName();
        UUID userId = UUID.fromString(id);
        Optional<User> customerOptional = userRepository.findById(userId);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }else {
            throw new AppException(ErrorCode.VALIDATION,"Invalid token.");
        }

    }
}
