package nextstep.subway.section;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.subway.AcceptanceTest;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.section.dto.SectionRequest;
import nextstep.subway.section.dto.SectionResponse;
import nextstep.subway.station.dto.StationRequest;
import nextstep.subway.station.dto.StationResponse;

@DisplayName("지하철 노선의 섹션 관련 기능")
public class SectionAcceptanceTest extends AcceptanceTest {

	StationResponse 강남역;

	StationResponse 광교역;

	LineResponse 신분당선;

	@BeforeEach
	public void setUp() {
		super.setUp();

		// given
		강남역 = postStation(new StationRequest("강남역"));
		광교역 = postStation(new StationRequest("광교역"));

		LineRequest createParams = new LineRequest(
			"신분당선", "bg-red-600", 강남역.getId(), 광교역.getId(), 7
		);

		신분당선 = postLine(createParams);
	}

	/**
	 * {@link nextstep.subway.line.ui.LineController#addSection(Long, SectionRequest)}
	 */
	@DisplayName("역_사이에_새로운_역을_등록할_경우, 노선에 구간을 등록한다. 강남역--4--(B)--3--광교역")
	@Test
	void 역_사이에_새로운_역을_등록할_경우() {
		// given
		StationResponse B역 = postStation(new StationRequest("B"));
		int distance = 4;

		// when
		// 지하철_노선에_지하철역_등록_요청
		SectionResponse newSection = postSection(신분당선, new SectionRequest(강남역.getId(), B역.getId(), distance));

		// then
		// 지하철_노선에_지하철역_등록됨
		assertThat(newSection)
			.matches(section -> section.getUpStation().getName().equals(강남역.getName()))
			.matches(section -> section.getDownStation().getName().equals(B역.getName()))
			.matches(section -> section.getDistance() == distance)
		;

		// when
		// 지하철 노선 조회시,
		LineResponse line = getLine(신분당선.getId());

		// then
		// 섹션 검증
		assertThat(line.getSections())
			.extracting(s -> s.getUpStation().getName(), s -> s.getDownStation().getName(), SectionResponse::getDistance)
			.containsExactly(
				tuple(강남역.getName(), B역.getName(), 4),
				tuple(B역.getName(), 광교역.getName(), 3)
			);
	}

	@DisplayName("새로운 역을 상행 종점으로 등록할 경우. (B)--4--강남역--7--광교역")
	@Test
	void 새로운_역을_상행_종점으로_등록할_경우() {
		// given
		StationResponse B역 = postStation(new StationRequest("B"));
		int distance = 3;

		// when
		// 지하철_노선에_지하철역_등록_요청
		SectionResponse newSection = postSection(신분당선, new SectionRequest(B역.getId(), 강남역.getId(), distance));

		// then
		// 지하철_노선에_지하철역_등록됨
		assertThat(newSection)
			.matches(section -> section.getUpStation().getName().equals(B역.getName()))
			.matches(section -> section.getDownStation().getName().equals(강남역.getName()))
			.matches(section -> section.getDistance() == distance)
		;

		// when
		// 지하철 노선 조회시,
		LineResponse line = getLine(신분당선.getId());

		// then
		// 섹션 검증
		assertThat(line.getSections())
			.extracting(s -> s.getUpStation().getName(), s -> s.getDownStation().getName(), SectionResponse::getDistance)
			.containsExactly(
				tuple(B역.getName(), 강남역.getName(), 4),
				tuple(강남역.getName(), 광교역.getName(), 7)
			);
	}

	@DisplayName("새로운 역을 하행 종점으로 등록할 경우. 강남역--7--광교역--3--(B)")
	@Test
	void 새로운_역을_하행_종점으로_등록할_경우() {
		// given
		StationResponse B역 = postStation(new StationRequest("B"));
		int distance = 3;

		// when
		// 지하철_노선에_지하철역_등록_요청
		SectionResponse newSection = postSection(신분당선, new SectionRequest(광교역.getId(), B역.getId(), distance));

		// then
		// 지하철_노선에_지하철역_등록됨
		assertThat(newSection)
			.matches(section -> section.getUpStation().getName().equals(광교역.getName()))
			.matches(section -> section.getDownStation().getName().equals(B역.getName()))
			.matches(section -> section.getDistance() == distance)
		;

		// when
		// 지하철 노선 조회시,
		LineResponse line = getLine(신분당선.getId());

		// then
		// 섹션 검증
		assertThat(line.getSections())
			.extracting(s -> s.getUpStation().getName(), s -> s.getDownStation().getName(), SectionResponse::getDistance)
			.containsExactly(
				tuple(강남역.getName(), 광교역.getName(), 7),
				tuple(광교역.getName(), B역.getName(), 3)
			);
	}

}
