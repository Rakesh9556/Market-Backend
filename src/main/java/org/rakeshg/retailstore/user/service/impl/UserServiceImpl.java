package org.rakeshg.retailstore.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.enums.UserRole;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.user.model.User;
import org.rakeshg.retailstore.user.repository.UserRepository;
import org.rakeshg.retailstore.user.service.UserService;
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
    public User createOwnerUser(String phone) {
        if(userExistsByPhone(phone)) {
            throw new BusinessException("User already exists", "USER_ALREADY_EXISTS");
        }

        // Create store
        User user = User.builder()
                .phone(phone)
                .name(null)
                .storeId(null)
                .role(UserRole.OWNER)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public boolean storeExistByUserId(Long id) {
        return userRepository.existsByIdAndStoreIdIsNotNull(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found", "USER_NOT_FOUND"));
    }

    @Override
    public void attachStore(Long userId, Long storeId, String owner) {

        // Load user
        User user = getUserById(userId);

        // Validate user state
        if(!Boolean.TRUE.equals(user.getActive())) {
            throw new BusinessException("Inactive user", "USER_INACTIVE");
        }

        if(user.getStoreId() != null) {
            throw new BusinessException("Store already onboards", "STORE_ALREADY_EXISTS");
        }

        // update the storeId field in user entity
        user.setStoreId(storeId);
        user.setName(owner);
        userRepository.save(user);
    }

    @Override
    public User updateUserProfile(String userId) {
        return null;
    }
}

