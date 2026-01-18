package org.rakeshg.retailstore.store.store.service;

import org.rakeshg.retailstore.store.store.command.CreateStoreCommand;
import org.rakeshg.retailstore.store.store.model.Store;


public interface StoreService {
    boolean storeExistsByPhone(String phone);
    Store createStore(CreateStoreCommand command);
    Store getStoreByPhone(String phone);
}
