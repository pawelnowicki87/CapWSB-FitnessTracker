package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Serwis aplikacyjny odpowiedzialny za zarządzanie encjami {@link User}.
 *
 * <p>
 * Interfejs udostępnia operacje tworzenia, modyfikacji, usuwania oraz wyszukiwania użytkowników.
 * Implementacje tej klasy odpowiadają za logikę biznesową i współpracę z warstwą danych.
 * </p>
 */
public interface UserService {

    /**
     * Tworzy nowego użytkownika w systemie.
     * Obiekt nie powinien zawierać zdefiniowanego identyfikatora.
     *
     * @param user użytkownik do utworzenia
     * @return zapisany użytkownik z nadanym ID
     * @throws IllegalArgumentException jeśli użytkownik zawiera już ID
     */
    User createUser(User user);

    /**
     * Usuwa użytkownika o podanym identyfikatorze.
     * Operacja usuwa również wszystkie powiązane treningi.
     *
     * @param id identyfikator użytkownika do usunięcia
     * @throws IllegalArgumentException jeśli użytkownik nie istnieje
     */
    void deleteUser(Long id);

    /**
     * Aktualizuje dane istniejącego użytkownika.
     *
     * @param id identyfikator użytkownika do aktualizacji
     * @param updatedUser dane do nadpisania
     * @return zaktualizowany użytkownik
     * @throws IllegalArgumentException jeśli użytkownik nie zostanie odnaleziony
     */
    User updateUser(Long id, User updatedUser);

    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podany fragment (niewrażliwy na wielkość liter).
     *
     * @param fragment fragment adresu e-mail
     * @return lista użytkowników pasujących do wzorca
     */
    List<User> findByPartialEmail(String fragment);

    /**
     * Zwraca użytkowników starszych niż określony próg wieku,
     * czyli takich, których data urodzenia jest wcześniejsza niż wskazana.
     *
     * @param ageThreshold maksymalna dopuszczalna data urodzenia (np. LocalDate.now().minusYears(18))
     * @return lista użytkowników spełniających warunek wieku
     */
    List<User> findOlderThan(LocalDate ageThreshold);
}
