package com.example.ticketing.repository.carriages;

import com.example.ticketing.model.inventory.Carriage;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.SeatClass;
import com.example.ticketing.repository.RepositoryInitializer;

import java.util.List;

public class CarriageRepositoryInitializer implements RepositoryInitializer<CarriageRepository> {

    private final CarriageRepository carriageRepository;

    public CarriageRepositoryInitializer(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    @Override
    public void initialize() {
        final List<Carriage> carriages = this.buildCarriages();
        this.carriageRepository.fillRepository(carriages);

        System.out.println("Carriage repository initialized with " + carriages.size() + " carriages");
    }

    private List<Carriage> buildCarriages() {
        return List.of(
                new Carriage("A", List.of(
                        new Seat("1", SeatClass.FIRST_CLASS),
                        new Seat("2", SeatClass.FIRST_CLASS),
                        new Seat("3", SeatClass.SECOND_CLASS),
                        new Seat("4", SeatClass.SECOND_CLASS),
                        new Seat("11", SeatClass.FIRST_CLASS),
                        new Seat("12", SeatClass.FIRST_CLASS))),
                new Carriage("H", List.of(
                        new Seat("1", SeatClass.FIRST_CLASS),
                        new Seat("2", SeatClass.FIRST_CLASS),
                        new Seat("3", SeatClass.FIRST_CLASS),
                        new Seat("4", SeatClass.FIRST_CLASS))),
                new Carriage("N", List.of(
                        new Seat("5", SeatClass.SECOND_CLASS),
                        new Seat("6", SeatClass.SECOND_CLASS),
                        new Seat("7", SeatClass.SECOND_CLASS),
                        new Seat("8", SeatClass.SECOND_CLASS))),
                new Carriage("T", List.of(
                        new Seat("11", SeatClass.SECOND_CLASS),
                        new Seat("12", SeatClass.SECOND_CLASS),
                        new Seat("13", SeatClass.SECOND_CLASS),
                        new Seat("14", SeatClass.SECOND_CLASS),
                        new Seat("15", SeatClass.FIRST_CLASS),
                        new Seat("13", SeatClass.FIRST_CLASS))));
    }

}
