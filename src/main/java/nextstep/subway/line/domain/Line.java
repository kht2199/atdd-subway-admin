package nextstep.subway.line.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nextstep.subway.common.BaseEntity;
import nextstep.subway.section.domain.Section;
import nextstep.subway.section.domain.SectionDistanceNotEnoughException;
import nextstep.subway.section.domain.SectionNotFoundContainsStationException;
import nextstep.subway.section.domain.Sections;
import nextstep.subway.station.domain.Station;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String color;

    @Embedded
    private Sections sections;

    protected Line() {}

    public Line(Station upStation, Station downStation, int distance, String name, String color) {
        this.name = name;
        this.color = color;
        this.sections = new Sections(this, upStation, downStation, distance);
    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
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

    public Section addStations(Station upStation, Station downStation, int distance) throws
            SectionNotFoundContainsStationException, SectionDistanceNotEnoughException {
        Section newSection = new Section(this, upStation, downStation, distance);
        return this.sections.add(newSection);
    }

    public List<Station> stations() {
        return sections.serializeStations();
    }

    public List<Section> sections() {
        return sections.sections();
    }
}
