package pl.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs API służący do odczytu danych użytkowników.
 *
 * <p>
 * Udostępnia metody do pobierania pojedynczych użytkowników na podstawie ID
 * oraz listy wszystkich zarejestrowanych użytkowników.
 * </p>
 */

public interface UserProvider {

    Optional<User> getUser(Long userId);

    Optional<User> getUserByEmail(String email);

    List<User> findAllUsers();
}
