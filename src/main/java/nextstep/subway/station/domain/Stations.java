package nextstep.subway.station.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import nextstep.subway.section.domain.Section;

/**
 *
 * @author heetaek.kim
 */
public final class Stations {

	private final List<Station> stations;

	public Stations(List<Section> sections) {
		stations = new LinkedList<>();
		for (Section s : sections) {
			s.addStationsTo(stations);
		}
	}

	public List<Station> getStations() {
		return Collections.unmodifiableList(stations);
	}
}
