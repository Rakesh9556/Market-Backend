package org.rakeshg.retailstore.store.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;
import org.rakeshg.retailstore.store.store.model.Store;
import org.rakeshg.retailstore.store.store.service.OnboardingService;
import org.rakeshg.retailstore.store.store.service.StoreService;
import org.rakeshg.retailstore.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardServiceImpl implements OnboardingService {

    private final StoreService storeService;
    private final UserService userService;

    @Transactional
    @Override
    public void onboardStore(Long userId, OnboardStoreCommand command) {

        // Check if store already exist for the user
        boolean alreadyOnboarded = userService.storeExistByUserId(userId);
        if(alreadyOnboarded) {
            throw new BusinessException("Store already onboarded", "STORE_ALREADY_EXISTS");
        }

        // Create store
        Store store = storeService.createStore(command);

        // Attach store to user
        userService.attachStore(userId, store.getId(), command.getOwner());

    }
}
