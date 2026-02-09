package org.rakeshg.retailstore.store.store.service;

import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;
import org.rakeshg.retailstore.store.store.dto.response.DashboardSummaryResponse;
import org.rakeshg.retailstore.store.store.model.Store;


public interface StoreService {
    Store createStore(OnboardStoreCommand command);
    DashboardSummaryResponse getSummary(Long storeId);
}
