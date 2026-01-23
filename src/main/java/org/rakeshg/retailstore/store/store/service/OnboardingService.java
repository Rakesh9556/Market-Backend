package org.rakeshg.retailstore.store.store.service;

import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;

public interface OnboardingService {
    void onboardStore(Long userId, OnboardStoreCommand command);
}
