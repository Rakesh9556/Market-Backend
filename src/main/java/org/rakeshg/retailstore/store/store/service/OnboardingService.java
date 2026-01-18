package org.rakeshg.retailstore.store.store.service;

import org.rakeshg.retailstore.store.store.command.CreateStoreCommand;

public interface OnboardingService {
    void onboardStore(Long userId, CreateStoreCommand command);
}
