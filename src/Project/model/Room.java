package Project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private long roomId;
    private int size;
    private int floor;
    private String standard;
    private String name;
    private double pricePerNight;
    private List<Booking> bookingsList = new ArrayList<>();

    public long getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public List<Booking> getBookingsList() {
        return bookingsList;
    }

    public Room(long roomId, int size, int floor, String standard, String name, double pricePerNight) {
        this.size = size;
        this.floor = floor;
        this.standard = standard;
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.roomId = roomId;
    }

    public boolean checkBookingIfPossible(Booking booking){
        return bookingsList.stream().noneMatch(
                book -> (booking.getDateFrom().isAfter(book.getDateFrom()) && booking.getDateFrom().isBefore(book.getDateTo()) ||
                        booking.getDateTo().isAfter(book.getDateFrom()) && booking.getDateTo().isBefore(book.getDateTo())) && !booking.getBookingId().equals(book.getBookingId()));
    }

    public boolean roomIsFree(LocalDate dateFrom, LocalDate dateTo){
        return bookingsList.stream().noneMatch(book -> dateFrom.isAfter(book.getDateFrom()) && dateFrom.isBefore(book.getDateTo()) ||
                dateTo.isAfter(book.getDateFrom()) && dateTo.isBefore(book.getDateTo()) || dateFrom.isEqual(book.getDateFrom())||dateTo.isEqual(book.getDateTo()));
    } /*Cały warunek jest negowany przez metodę noneMatch(), co oznacza, że zwróci wartość true, jeśli nie ma żadnej rezerwacji w bookingsList, która by kolidowała z podanymi datami dateFrom i dateTo. W przeciwnym razie zwróci wartość false.

Metoda roomIsFree zwraca wartość logiczną (boolean) oznaczającą, czy pokój jest wolny w podanym zakresie dat.*/

    public boolean priceMatch(Double priceMin, Double priceMax){
        System.out.println("Cena pokoju " + pricePerNight);
        return pricePerNight >= priceMin && pricePerNight <= priceMax;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String serialize() {
        return "" + roomId + "," + size + "," + floor + "," + standard + "," + name + "," + pricePerNight;
    }

    public void setBookings(List<Booking> roomBookings) {
        this.bookingsList = roomBookings;
    }
}