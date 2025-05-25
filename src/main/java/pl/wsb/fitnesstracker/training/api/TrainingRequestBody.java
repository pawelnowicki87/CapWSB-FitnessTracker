package pl.wsb.fitnesstracker.training.api;

import lombok.Getter;
import lombok.Setter;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

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
