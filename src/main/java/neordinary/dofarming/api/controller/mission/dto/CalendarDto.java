package neordinary.dofarming.api.controller.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CalendarDto {
    private String month;
    private String date;
    private String image;
    private Boolean isSuccess;
}
