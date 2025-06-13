package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingRequestBody;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserMapper;

/**
 * Komponent odpowiedzialny za mapowanie danych treningowych
 * między warstwą domenową (encje), DTO a obiektami wejściowymi żądań API.
 */
@Component
public class TrainingMapper {

    private final UserMapper userMapper;

    /**
     * Inicjalizuje komponent z zależnością do mappera użytkownika.
     *
     * @param userMapper komponent mapujący dane użytkowników
     */
    public TrainingMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * Przekształca encję treningu na obiekt DTO.
     *
     * @param training encja treningu pobrana z bazy danych
     * @return obiekt {@link TrainingDto} gotowy do przesłania przez API
     */
    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    /**
     * Tworzy encję treningu na podstawie obiektu DTO i powiązanego użytkownika.
     * Może być używane do operacji zapisu lub aktualizacji.
     *
     * @param dto obiekt DTO zawierający dane treningu
     * @param user właściciel treningu
     * @return nowa instancja encji treningu
     */
    public Training toEntity(TrainingDto dto, User user) {
        return new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }

    /**
     * Tworzy encję treningu na podstawie danych wejściowych przesłanych w żądaniu.
     * Wymaga przekazania użytkownika, do którego trening ma zostać przypisany.
     *
     * @param request dane treningu pochodzące z ciała żądania
     * @param user użytkownik powiązany z treningiem
     * @return nowy obiekt encji treningu
     */
    public Training toEntity(TrainingRequestBody request, User user) {
        return new Training(
                user,
                request.getStartTime(),
                request.getEndTime(),
                request.getActivityType(),
                request.getDistance(),
                request.getAverageSpeed()
        );
    }
}
