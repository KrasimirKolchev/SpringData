package com.usersystem.models;

import com.usersystem.customAnnotations.password.ValidPassword;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private LocalDate registeredOn;
    private LocalDate lastTimeLoggedIn;
    private int age;
    private boolean isDeleted;
    private Town bornTown;
    private Town livingTown;
    private Set<User> friends;
    private Set<Album> albums;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "username", nullable = false, unique = true)
    @Length(min=4, max=30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    @ValidPassword(message = "Invalid password!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @Pattern(regexp = "^([a-zA-Z0-9]+[-_\\.]?[a-zA-Z0-9]+)@([a-zA-Z]+-?[a-zA-Z]+)(\\.[a-zA-Z]+[-]?[a-zA-Z]+)+$"
            , message = "Invalid Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "registered_on", nullable = false)
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Column(name = "last_time_logged_in", nullable = false)
    public LocalDate getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDate lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column(name = "age")
    @Min(value = 1, message = "Age is too low!")
    @Max(value = 120, message = "Age is too high!")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "is_deleted", nullable = false)
    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Town getLivingTown() {
        return livingTown;
    }

    public void setLivingTown(Town livingTown) {
        this.livingTown = livingTown;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
