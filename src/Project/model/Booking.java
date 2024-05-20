package Project.model;

import java.time.LocalDate;

public class Booking implements Serializable {
    static Long bookings = 0L;
    /*Podsumowując, ten kod definiuje statyczną zmienną o nazwie bookings typu Long i przypisuje jej początkową wartość równą zero. Ta zmienna może być używana wewnątrz klasy do śledzenia liczby rezerwacji, a wartość tej zmiennej będzie współdzielona przez wszystkie instancje tej klasy.*/

    private Long bookingId;
    private Long roomId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String clientLogin;
    private Boolean confirmed;
    private Double totalPrice;
    private Boolean paid;

    public Booking(LocalDate dateFrom, LocalDate dateTo, String clientLogin, Double totalPrice, Long roomId, Long bookingId) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientLogin = clientLogin;
        this.totalPrice = totalPrice;
        this.roomId = roomId;
        this.confirmed = false;
        this.paid = false;
        this.bookingId = bookingId;
    }

    public Booking(Booking other){
        this.dateFrom = other.dateFrom;
        this.dateTo = other.dateTo;
        this.clientLogin = other.clientLogin;
        this.confirmed = other.confirmed;
        this.totalPrice = other.totalPrice;
        this.paid = other.paid;
        this.bookingId = other.bookingId;
        this.roomId = other.roomId;
    }

    public Booking(Long bookingId, LocalDate dateFrom, LocalDate dateTo, String clientLogin, Boolean confirmed, Double totalPrice, Boolean paid, Long roomId) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.clientLogin = clientLogin;
        this.confirmed = confirmed;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.bookingId = bookingId;
        this.roomId = roomId;
        if(bookingId >= bookings)
            bookings = bookingId + 1;
    }

    public Long getRoomId() {
        return roomId;
    }

    public boolean canUpdateBooking(User updatingUser){

        return updatingUser.getLogin().equals(getClientLogin());
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Booking extendBooking(Long days){
        Booking tmpBooking = new Booking(this);
        tmpBooking.dateTo = dateTo.plusDays(days);
        return tmpBooking;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void update(Booking booking) {
        this.dateTo = booking.getDateTo();
        this.confirmed = false;
        this.paid = false;
    }

    @Override
    public String serialize() {
        return "" + bookingId + "," + clientLogin + "," + roomId + "," + dateFrom.getDayOfMonth() + "," + dateFrom.getMonthValue()+ "," +  dateFrom.getYear()
                + "," + dateTo.getDayOfMonth() + "," + dateTo.getMonthValue()+ "," + dateTo.getYear() + "," + totalPrice + "," + confirmed + "," + paid;
    }
}

