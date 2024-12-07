package com.example.musiclibrary.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String password;
    public enum Role {
        User(0), Librarian(1);
        private final int roleNum;
        Role(int roleNum) {
            this.roleNum = roleNum;
        }
        public int getRoleNum() {
            return roleNum;
        }
    }
    private Role role;
    private LocalDate membership_date;
    private String phone_number;
    private String address;
    private List<RentalDto> rentals;
    private List<ReservationDto> reservations;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<ActionDto> actions;
    public UserDto(String name, String email, String password, Role role, LocalDate membership_date, String phone_number, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.membership_date = membership_date;
        this.phone_number = phone_number;
        this.address = address;
    }
    public UserDto() {
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    @NotNull(message = "Введите ФИО!")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @NotNull(message = "Введите электронную почту!")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @NotNull(message = "Введите пароль!")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @NotNull(message = "Выберите роль!")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
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
    public List<RentalDto> getRentals() {
        return rentals;
    }
    public void setRentals(List<RentalDto> rentals) {
        this.rentals = rentals;
    }
    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public LocalDateTime getModified() {
        return modified;
    }
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
    @Override
    public String toString() {
        return "User {" +
                "user_id: " + id + ", " +
                "name: " + name + ", " +
                "email: " + email + ", " +
                "password: " + password + ", " +
                "role: " + role + ", " +
                "membership_date: " + membership_date + ", " +
                "phone_number: " + phone_number + ", " +
                "address: " + address + ", " +
                "rentals: " + rentals + ", " +
                "reservations: " + reservations + ", " +
                "created: " + created + ", " +
                "modified: " + modified + "}";
    }
}