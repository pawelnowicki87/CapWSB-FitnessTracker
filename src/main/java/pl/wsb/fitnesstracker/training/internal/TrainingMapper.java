package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRequestBody;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserMapper;


@Component
public class TrainingMapper {

    private final UserMapper userMapper;

    public TrainingMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
