package Project.service;

import Project.model.Booking;
import Project.model.Room;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsService {
    private List<Room> roomList;

    public RoomsService() {
        roomList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "rooms"));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                roomList.add(new Room(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), data[3], data[4], Double.parseDouble(data[5])));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Room getRoomByRoomId(Long roomId) {
        return roomList.stream().filter(room -> room.getRoomId() == roomId).findFirst().get();
    }

    public void createRoom(int guests, int floor, String standard, String name, Double price) {
        long nextRoomId = roomList.stream().map(r -> r.getRoomId()).max(Long::compare).orElse(0L) + 1;
        roomList.add(new Room(nextRoomId, guests, floor, standard, name, price));
        try {
            saveRooms();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Room> getRoomsByCriteria(LocalDate dateFrom, LocalDate dateTo, Double priceMin, Double priceMax, String standard, int guests) {
        return roomList.stream().filter(room ->
                room.priceMatch(priceMin, priceMax) && room.getStandard().equals(standard) && room.getSize() >= guests && room.roomIsFree(dateFrom, dateTo)
        ).collect(Collectors.toList());
    }

    public void update() {
        String serialized = roomList.stream().map(Room::serialize).reduce((acc, x) -> acc + "\n" + x).get();
        System.out.println(serialized);  //Room::serialize
    }/*Na strumieniu wywoływana jest metoda map(), która przekształca każdy obiekt typu Room na zserializowaną reprezentację tego pokoju. Wykorzystywany jest operator ::, który wskazuje na metodę serialize w klasie Room. Wywołanie Room::serialize oznacza, że dla każdego obiektu Room zostanie wywołana metoda serialize().
Następnie wywoływana jest metoda reduce(), która agreguje wszystkie zserializowane pokoje do jednego łańcucha znaków. Wartość początkowa (akumulator) jest reprezentowana przez parametr acc, a element strumienia jest reprezentowany przez parametr x. Wyrażenie lambda (acc, x) -> acc + "\n" + x dodaje znak nowej linii pomiędzy kolejnymi zserializowanymi pokojami.*/

    public void addBookingsToRooms(List<Booking> bookings) {
        roomList.forEach(room -> {
            List<Booking> roomBookings = bookings.stream().filter(booking -> booking.getRoomId() == room.getRoomId()).collect(Collectors.toList());
            room.setBookings(roomBookings);
        });
    }

    void saveRooms() throws IOException {
        String rooms = roomList.stream().map(Room::serialize).collect(Collectors.joining("\n"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("rooms"));
        writer.write(rooms);
        writer.close();
    }
}
