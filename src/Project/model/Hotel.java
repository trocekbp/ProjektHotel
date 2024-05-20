package Project.model;

import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    private List <Room> roomsList;

    public Hotel(List<Room> roomsList) {
        this.roomsList = roomsList;
    }

    public List<Room> findRoomsByPricesRange(double minPrice, double maxPrice){
        return roomsList.stream().filter(
                it -> it.getPricePerNight() >= minPrice && it.getPricePerNight() <= maxPrice
        ).collect(Collectors.toList());
    }

    public List<Room> findRoomsByFloor(int minFloor, int maxFloor){
        return roomsList.stream().filter(
                it -> it.getFloor() >= minFloor && it.getFloor() <= maxFloor
        ).collect(Collectors.toList());
    }

    public List<Room> findRoomsBySize(int minSize, int maxSize){
        return roomsList.stream().filter(
                it -> it.getSize() >= minSize && it.getSize() <= maxSize
        ).collect(Collectors.toList());
    }


}

