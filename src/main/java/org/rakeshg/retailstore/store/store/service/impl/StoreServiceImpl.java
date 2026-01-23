package org.rakeshg.retailstore.store.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.store.store.command.OnboardStoreCommand;
import org.rakeshg.retailstore.store.store.model.Store;
import org.rakeshg.retailstore.store.store.repository.StoreRepository;
import org.rakeshg.retailstore.store.store.service.StoreService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Store createStore(OnboardStoreCommand command) {

        Store store = Store.builder()
                .name(command.getStoreName())
                .address(command.getAddress())
                .active(true)
                .build();

        // Save store into db
        storeRepository.save(store);
        return store;
    }
}
