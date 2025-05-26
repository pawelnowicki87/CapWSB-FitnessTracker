package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRequestBody;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserMapper;

/**
 * Komponent odpowiedzialny za mapowanie obiektów treningów pomiędzy warstwą
 * domenową (encje), DTO oraz ciałem żądania API.
 */
@Component
public class TrainingMapper {

    private final UserMapper userMapper;

    /**
     * Konstruktor z wstrzykiwaniem zależności mappera użytkownika.
     *
     * @param userMapper mapper obiektów użytkownika
     */
    public TrainingMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * Konwertuje encję treningu na obiekt DTO treningu.
     *
     * @param training encja treningu
     * @return obiekt DTO reprezentujący trening
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
     * Konwertuje obiekt DTO treningu na encję treningu, przypisaną do użytkownika.
     *
     * @param dto obiekt DTO treningu
     * @param user użytkownik przypisany do treningu
     * @return encja treningu
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
     * Konwertuje ciało żądania treningu na encję treningu, przypisaną do użytkownika.
     *
     * @param request ciało żądania treningu (TrainingRequestBody)
     * @param user użytkownik przypisany do treningu
     * @return encja treningu
     */
    public Training toEntity(TrainingRequestBody request, User user) {
        return new Training(user,
                request.getStartTime(),
                request.getEndTime(),
                request.getActivityType(),
                request.getDistance(),
                request.getAverageSpeed()
        );
    }
}
