package Project.service;

import Project.model.Booking;
import Project.model.Room;
import Project.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class BookingService {
    private List<Booking> bookingList;

    public BookingService() {
        bookingList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "bookings"));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                LocalDate dateFrom = LocalDate.of(Integer.parseInt(data[5]), Integer.parseInt(data[4]), Integer.parseInt(data[3]));
                LocalDate dateTo = LocalDate.of(Integer.parseInt(data[8]), Integer.parseInt(data[7]), Integer.parseInt(data[6]));
                bookingList.add(new Booking(Long.parseLong(data[0]), dateFrom, dateTo, data[1], Boolean.parseBoolean(data[10]), Double.parseDouble(data[9]), Boolean.parseBoolean(data[11]), Long.parseLong(data[2])));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    List<Booking> getUserBookings(User user) {
        return user.getBookings(bookingList);
    }

    void extendBooking(Long bookingId, Room room, User user, Long daysExtended){
        Booking booking = bookingList.stream().filter(book -> book.getBookingId().equals(bookingId)).findFirst().get();
        Booking bookingTmp = booking.extendBooking(daysExtended);
        if(room.checkBookingIfPossible(bookingTmp) &&
                booking.canUpdateBooking(user)) {
            booking.update(bookingTmp);
        } else throw new RuntimeException("Nie można przedłużyć pobytu!");
        saveBookings();
    }

    void createBooking(LocalDate dateFrom, LocalDate dateTo, User user, Double totalPrice, Long roomId){

        Long bookingId= bookingList.stream().map(Booking::getBookingId).max(Long::compareTo).orElse(0L) + 1;;
        bookingList.add(new Booking(dateFrom, dateTo, user.getLogin(), totalPrice, roomId, bookingId));
        saveBookings();
    }

    public List<Booking> getBookings() {
        return bookingList;
    }

    void saveBookings() {
        String bookings = bookingList.stream().map(Booking::serialize).collect(Collectors.joining("\n"));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("bookings"));
            writer.write(bookings);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
