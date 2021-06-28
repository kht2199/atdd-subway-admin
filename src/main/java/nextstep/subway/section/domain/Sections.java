package nextstep.subway.section.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.Stations;

/**
 *
 * @author heetaek.kim
 */
@Embeddable
public class Sections {

	@OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private final List<Section> sections = new LinkedList<>();

	protected Sections() {}

	public Sections(Line line, Station upStation, Station downStation, int distance) {
		sections.add(new Section(line, upStation, downStation, distance));
	}

	public Stations stations() {
		return new Stations(sections);
	}

	/**
	 * TODO 새로운 섹션을 등록한다.
	 * 1. 유효성 검증
	 * 2. 삽입 가능한 케이스를 확인
	 * 3. 새로운 Section을 Line에 등록
	 * 4. Section의 거리/Station을 Merge
	 *
	 * Line에 등록되는 경우의 수
	 * 1. 가장 앞에 오는 경우.
	 * - 첫번째 section의 기존 upStation과 신규 downStation이 일치
	 * 2. 역의 가운데 오면서, 기존 downStation이 신규 upStation과 일치
	 * - 일치하는 Section의 뒤쪽에 새로운 Section을 삽입하고, 거리/Station을 Merge한다.
	 * 3. 역의 가운데 오면서, 기존 upStation이 신규 downStation과 일치
	 * - 일치하는 Section의 앞쪽에 새로운 Section을 삽입하고, 거리/Station을 Merge한다.
	 * 4. 역의 마지막에 오는 경우.
	 * - 마지막 section의 기존 downStation과 신규 upStation이 동일
	 */
	public Section addSection(Section newSection) {
		validateAddableSection(newSection);
		assert false;
		return newSection;
	}

	public List<Section> getSections() {
		return Collections.unmodifiableList(sections);
	}

	/**
	 * TODO Section 등록 가능한지 유효성 확인
	 * 1. UpStation, DownStation 중 하나만 포함 되어야함
	 * 2. 역간 거리가 유효해야함
	 */
	private void validateAddableSection(Section newSection) {
		this.validateContainsOnlyOneStation(newSection);
	}

	private void validateContainsOnlyOneStation(Section newSection) {
		assert false;
	}

}
