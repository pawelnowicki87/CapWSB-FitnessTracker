package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Encja reprezentująca użytkownika systemu.
 * Zawiera podstawowe dane identyfikacyjne oraz kontaktowe.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    /**
     * Unikalny identyfikator użytkownika (generowany automatycznie).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    /**
     * Imię użytkownika (pole wymagane).
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Nazwisko użytkownika (pole wymagane).
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Data urodzenia użytkownika.
     */
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    /**
     * Adres e-mail użytkownika (musi być unikalny).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Konstruktor tworzący nowego użytkownika.
     * Używany np. przy rejestracji lub inicjalizacji danych.
     *
     * @param firstName imię
     * @param lastName nazwisko
     * @param birthdate data urodzenia
     * @param email adres e-mail
     */
    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }
}
