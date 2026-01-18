package org.rakeshg.retailstore.store.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.rakeshg.retailstore.common.exception.ResourceAlreadyExistException;
import org.rakeshg.retailstore.common.exception.ResourceNotFoundException;
import org.rakeshg.retailstore.store.store.command.CreateStoreCommand;
import org.rakeshg.retailstore.store.store.model.Store;
import org.rakeshg.retailstore.store.store.repository.StoreRepository;
import org.rakeshg.retailstore.store.store.service.StoreService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public boolean storeExistsByPhone(String phone) {
        return storeRepository.existsByPhone(phone);
    }


    @Override
    public Store createStore(CreateStoreCommand command) {

        // Check store exist by phone or not
        if(storeExistsByPhone(command.getPhone())) {
            throw new ResourceAlreadyExistException("Store already exist with phone");
        }

        // If not exist create store
        Store store = Store.builder()
                .name(command.getStoreName())
                .email(command.getEmail())
                .phone(command.getPhone())
                .address(command.getAddress())
                .active(true)
                .build();

        // Save store into db
        storeRepository.save(store);
        return store;
    }

    @Override
    public Store getStoreByPhone(String phone) {
        return storeRepository.findStoreByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }
}
