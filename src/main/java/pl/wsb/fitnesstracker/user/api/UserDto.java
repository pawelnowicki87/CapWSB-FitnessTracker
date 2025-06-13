package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) użytkownika wykorzystywany w komunikacji REST API.
 *
 * <p>
 * Zawiera podstawowe dane użytkownika, wykorzystywane przy tworzeniu,
 * pobieraniu oraz prezentacji danych użytkowników po stronie klienta.
 * </p>
 *
 * @param id        identyfikator użytkownika (może być null np. przy tworzeniu)
 * @param firstName imię użytkownika
 * @param lastName  nazwisko użytkownika
 * @param birthdate data urodzenia w formacie yyyy-MM-dd
 * @param email     adres e-mail użytkownika
 */
public record UserDto(
        @Nullable Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email
) {
}
