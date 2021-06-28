package nextstep.subway.line.dto;

import nextstep.subway.line.domain.Line;
import nextstep.subway.section.domain.SectionResponse;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.dto.StationResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;
    private List<SectionResponse> sections;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected LineResponse() {
    }

    public LineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
        this.color = line.getColor();
        this.stations = line.stations().stream()
            .map(StationResponse::of)
            .collect(Collectors.toList());
        this.sections = line.sections().stream()
            .map(SectionResponse::new)
            .collect(Collectors.toList());
        this.createdDate = line.getCreatedDate();
        this.modifiedDate = line.getModifiedDate();
    }

    public static LineResponse of(Line line) {
        return new LineResponse(line);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public List<SectionResponse> getSections() {
        return sections;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
