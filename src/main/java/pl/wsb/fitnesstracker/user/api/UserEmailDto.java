package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * Uproszczony DTO użytkownika zawierający tylko identyfikator i adres e-mail.
 *
 * <p>
 * Wykorzystywany do zwracania podstawowych danych identyfikacyjnych,
 * np. w listach wyników wyszukiwania lub w relacjach między obiektami.
 * </p>
 *
 * @param id    identyfikator użytkownika
 * @param email adres e-mail użytkownika
 */
public record UserEmailDto(
        @Nullable Long id,
        String email
) {
}
