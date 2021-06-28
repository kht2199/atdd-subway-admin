package nextstep.subway.section.dto;

import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.domain.Section;
import nextstep.subway.station.dto.StationResponse;

public class SectionResponse {

	private Long id;

	private int distance;

	private LineResponse line;

	private StationResponse upStation;

	private StationResponse downStation;

	protected SectionResponse() {
	}

	public SectionResponse(Section section) {
		this.id = section.getId();
		this.distance = section.getDistance();
		this.line = LineResponse.of(section.getLine());
		this.upStation = StationResponse.of(section.getUpStation());
		this.downStation = StationResponse.of(section.getDownStation());
	}

	public static SectionResponse of(Section section) {
		return new SectionResponse(section);
	}

	public Long getId() {
		return id;
	}

	public int getDistance() {
		return distance;
	}

	public LineResponse getLine() {
		return line;
	}

	public StationResponse getUpStation() {
		return upStation;
	}

	public StationResponse getDownStation() {
		return downStation;
	}
}
