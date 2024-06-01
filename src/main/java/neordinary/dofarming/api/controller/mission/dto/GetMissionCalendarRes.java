package neordinary.dofarming.api.controller.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetMissionCalendarRes {

    // 캘린더 정보(사진, id)
    private List<CalendarDto> calendar;

    // 오늘 미션 정보
    private GetMissionByDateRes todayMission;
}
