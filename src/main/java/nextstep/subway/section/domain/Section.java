package nextstep.subway.section.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import nextstep.subway.common.BaseEntity;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;

@Entity
public class Section extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int distance;

	@ManyToOne
	private Line line;

	@OneToOne
	private Station upStation;

	@OneToOne
	private Station downStation;

	protected Section() {}

	public Section(Line line, Station upStation, Station downStation, int distance) {
		this.line = line;
		this.upStation = upStation;
		this.downStation = downStation;
		this.distance = distance;
	}

	public Station[] stations() {
		return new Station[]{
			upStation, downStation
		};
	}

	/**
	 * 직렬화하기 위해 사용하는 메서드이다.
	 * stations에 중복으로 Station이 포함되지 않도록 구현.
	 *
	 * @param stations 직렬화 하기 위한 List 인스턴스.
	 */
	public void addStationsTo(List<Station> stations) {
		if (stations.size() == 0) {
			stations.add(upStation);
		}
		stations.add(downStation);
	}

	public Long getId() {
		return id;
	}

	public Line getLine() {
		return line;
	}

	public int getDistance() {
		return distance;
	}

	public Station getUpStation() {
		return upStation;
	}

	public Station getDownStation() {
		return downStation;
	}

	@Override
	public String toString() {
		return "Section{" +
			"id=" + id +
			", distance=" + distance +
			", line=" + line +
			", upStation=" + upStation +
			", downStation=" + downStation +
			'}';
	}
}
