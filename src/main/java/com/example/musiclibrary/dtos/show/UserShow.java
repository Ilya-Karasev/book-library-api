package com.example.musiclibrary.dtos.show;
import com.example.musiclibrary.dtos.ActionDto;
import com.example.musiclibrary.dtos.UserDto;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;
import java.util.List;

public class UserShow extends RepresentationModel<UserShow> {
    private String name;
    private String email;
    private String password;
    private UserDto.Role role;
    private LocalDate membership_date;
    private String phone_number;
    private String address;
    private List<RentalShow> rentals;
    private List<ReservationShow> reservations;
    private List<ActionDto> actions;
    public UserShow(String name, String email, String password, UserDto.Role role, LocalDate membership_date, String phone_number, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.membership_date = membership_date;
        this.phone_number = phone_number;
        this.address = address;
    }
    public UserShow() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserDto.Role getRole() {
        return role;
    }
    public void setRole(UserDto.Role role) {
        this.role = role;
    }
    public LocalDate getMembership_date() {
        return membership_date;
    }
    public void setMembership_date(LocalDate membership_date) {
        this.membership_date = membership_date;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<RentalShow> getRentals() {
        return rentals;
    }
    public void setRentals(List<RentalShow> rentals) {
        this.rentals = rentals;
    }
    public List<ReservationShow> getReservations() {
        return reservations;
    }
    public void setReservations(List<ReservationShow> reservations) {
        this.reservations = reservations;
    }
    public List<ActionDto> getActions() {
        return actions;
    }
    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }
    @Override
    public String toString() {
        return "User {" +
                "name: " + name + ", " +
                "email: " + email + ", " +
                "password: " + password + ", " +
                "role: " + role + ", " +
                "membership_date: " + membership_date + ", " +
                "phone_number: " + phone_number + ", " +
                "address: " + address + ", " +
                "rentals: " + rentals + ", " +
                "reservations: " + reservations + "}";
    }
}