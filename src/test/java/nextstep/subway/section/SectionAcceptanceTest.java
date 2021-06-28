package nextstep.subway.section;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

	static Long 강남역_ID = 1L;

	StationResponse 광교역;

	static Long 광교역_ID = 2L;

	StationResponse B역;

	static Long B역_ID = 3L;

	LineResponse 신분당선;

	@BeforeEach
	public void setUp() {
		super.setUp();

		// given
		강남역 = postStation(new StationRequest("강남역"));
		광교역 = postStation(new StationRequest("광교역"));
		B역 = postStation(new StationRequest("B"));

		LineRequest createParams = new LineRequest(
			"신분당선", "bg-red-600", 강남역.getId(), 광교역.getId(), 7
		);

		신분당선 = postLine(createParams);
	}

	@ParameterizedTest(name = "{index}: {2}")
	@MethodSource(value = "methodSource")
	@DisplayName("새로운_역_등록")
	void 새로운_역_등록(SectionRequest request, Object[] section1, Object[] section2) {
		// when
		// 지하철_노선에_지하철역_등록_요청
		SectionResponse newSection = postSection(신분당선, request);

		// then
		// 지하철_노선에_지하철역_등록됨
		assertThat(newSection)
			.matches(section -> section.getUpStation().getId().equals(request.getUpStationId()))
			.matches(section -> section.getDownStation().getId().equals(request.getDownStationId()))
			.matches(section -> section.getDistance() == request.getDistance())
		;

		// when
		// 지하철 노선 조회시,
		LineResponse line = getLine(신분당선.getId());

		// then
		// 섹션 검증
		assertThat(line.getSections())
			.extracting(s -> s.getUpStation().getName(), s -> s.getDownStation().getName(), SectionResponse::getDistance)
			.containsExactly(
				tuple(section1[0], section1[2], section1[1]),
				tuple(section2[0], section2[2], section2[1])
			);
	}

	/**
	 * 강남--7--광교
	 */
	static Stream<Arguments> methodSource() {
		int distance = 4;
		return Stream.of(
			Arguments.of(
				new SectionRequest(강남역_ID, B역_ID, distance),
				new Object[]{"강남역", 4, "B역"}, new Object[]{"B역", 3, "광교역"},
				"역_사이에_새로운_역을_등록할_경우, 노선에 구간을 등록한다. 강남역--4--(B)--3--광교역"
			),
			Arguments.of(
				new SectionRequest(B역_ID, 강남역_ID, distance),
				new Object[]{"B역", 4, "강남역"}, new Object[]{"강남역", 7, "광교역"},
				"새로운 역을 상행 종점으로 등록할 경우. (B)--4--강남역--7--광교역"
			),
			Arguments.of(
				new SectionRequest(광교역_ID, B역_ID, distance),
				new Object[]{"강남역", 7, "광교역"}, new Object[]{"광교역", 4, "B역"},
				"새로운 역을 하행 종점으로 등록할 경우. 강남역--7--광교역--4--(B)"
			)
		);
	}
}
