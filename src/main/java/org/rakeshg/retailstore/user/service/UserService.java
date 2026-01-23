package org.rakeshg.retailstore.user.service;

import org.rakeshg.retailstore.user.model.User;

public interface UserService {
    boolean userExistsByPhone(String phone);
    User createOwnerUser(String phone);
    boolean storeExistByUserId(Long id);
    User getUserById(Long id);
    void attachStore(Long userId, Long storeId, String owner);
    User updateUserProfile(String userId);
}
