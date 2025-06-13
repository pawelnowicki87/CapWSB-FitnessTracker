package pl.wsb.fitnesstracker.training.api;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
/**
 * Model danych wejściowych treningu przekazywanych przez użytkownika w żądaniach API.
 *
 * <p>
 * Obiekt wykorzystywany do tworzenia lub modyfikowania treningów, zawierający dane takie jak
 * czas rozpoczęcia, czas zakończenia oraz typ aktywności. Stanowi warstwę pośrednią między
 * interfejsem użytkownika a logiką domenową aplikacji.
 * </p>
 */

@Getter
@Setter
public class TrainingRequestBody {
    private Long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
