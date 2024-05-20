package Project.service;

import Project.model.Booking;
import Project.model.Room;
import Project.model.User;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class MainService {

    private BookingService bookingService;
    private UsersService usersService;
    private RoomsService roomsService;

    public MainService(BookingService bookingService, UsersService usersService, RoomsService roomsService) {
        this.bookingService = bookingService;
        this.usersService = usersService;
        this.roomsService = roomsService;

        roomsService.addBookingsToRooms(bookingService.getBookings());

    }

    public List<User> getUsersList(){
        return usersService.getUserList();
    }

    public void createBooking(LocalDate dateFrom, LocalDate dateTo, User user, Double totalPrice, Long roomId){
        bookingService.createBooking(dateFrom, dateTo, user, totalPrice, roomId);
    }

    public void extendBooking(Long bookingId, Long roomId, User user, Long daysExtended){
        Room room = roomsService.getRoomByRoomId(roomId);
        bookingService.extendBooking(bookingId, room, user, daysExtended);
    }

    public List<Room> getRoomsByCriteria(LocalDate dateFrom, LocalDate dateTo, Double priceMin, Double priceMax, String standard, int guests){
        return roomsService.getRoomsByCriteria(dateFrom, dateTo, priceMin, priceMax, standard, guests);
    }

    public List<Booking> getUserBookings(User user) {
        return bookingService.getUserBookings(user);
    }

    public void setActiveUser(String userLogin) {
        usersService.setActiveUser(userLogin);
    }

    public void createNewRoom(int guests, int floor, String standard, String name, Double price){
        if(name.equals("")){
            JOptionPane.showMessageDialog(null,"Podaj Nazwe!");
        }else {
            roomsService.createRoom(guests, floor, standard, name, price);
        }
    }

    public void saveBookings(){
        bookingService.saveBookings();
    }

    public User getActiveUser(){
        return usersService.getActiveUser();
    }

}
