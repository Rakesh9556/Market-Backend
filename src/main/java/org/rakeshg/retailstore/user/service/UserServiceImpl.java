package org.rakeshg.retailstore.user.service;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.enums.UserRole;
import org.rakeshg.retailstore.common.exception.business_exception.ResourceForbiddenException;
import org.rakeshg.retailstore.common.exception.ResourceAlreadyExistException;
import org.rakeshg.retailstore.common.exception.ResourceNotFoundException;
import org.rakeshg.retailstore.common.exception.business_exception.StoreAlreadyExistsException;
import org.rakeshg.retailstore.common.exception.business_exception.UserAlreadyExistsException;
import org.rakeshg.retailstore.common.exception.business_exception.UserNotFoundException;
import org.rakeshg.retailstore.user.model.User;
import org.rakeshg.retailstore.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean userExistsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User createOwnerUser(String phone, String name) {
        if(userExistsByPhone(phone)) {
            throw new UserAlreadyExistsException();
        }

        // Create store
        User user = User.builder()
                .phone(phone)
                .name(name)
                .storeId(null)
                .role(UserRole.OWNER)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserByPhone(String phone)  {
        return userRepository.findByPhone(phone)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void attachStore(Long userId, Long storeId) {

        // Load user
        User user = getUserById(userId);

        // Validate user state
        if(!Boolean.TRUE.equals(user.getActive())) {
            throw new ResourceForbiddenException("User is not active");
        }

        if(user.getStoreId() != null) {
            throw new StoreAlreadyExistsException();
        }

        // update the storeId field in user entity
        user.setStoreId(storeId);
        userRepository.save(user);
    }

    @Override
    public User updateUserProfile(String userId) {
        return null;
    }
}

