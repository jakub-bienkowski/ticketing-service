package com.example.ticketing.repository.carriages;

import com.example.ticketing.model.inventory.Carriage;
import com.example.ticketing.repository.ModelRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarriageRepository implements ModelRepository<Carriage, String> {

        private final Map<String, Carriage> carriages = new HashMap<>();

        @Override
        public void fillRepository(List<Carriage> items) {
            items.forEach(item -> carriages.put(item.id(), item));
        }

        @Override
        public Carriage get(String id) {
            return carriages.get(id);
        }
}
