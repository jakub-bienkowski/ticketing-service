package com.example.ticketing.repository.services;

import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRepository implements Repository<Service, String> {

    private final Map<String, Service> services = new HashMap<>();

    @Override
    public void fillRepository(List<Service> items) {
        items.forEach(item -> services.put(item.getId(), item));
    }

    @Override
    public Service get(String id) {
        return services.get(id);
    }
}
