package pl.wsb.fitnesstracker.training.api;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.util.Date;

/**
 * Reprezentacja danych treningu używana do komunikacji między warstwami aplikacji.
 *
 * <p>
 * Obiekt DTO (Data Transfer Object) zawierający podstawowe informacje o pojedynczym treningu,
 * takie jak czas rozpoczęcia, zakończenia, typ aktywności i powiązany użytkownik.
 * Używany głównie w warstwie API do przesyłania danych bez ujawniania szczegółów implementacyjnych encji.
 * </p>
 */

@Getter
@Setter
public class TrainingDto {
    private Long id;
    private UserDto user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

    public TrainingDto(Long id, UserDto user, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}
