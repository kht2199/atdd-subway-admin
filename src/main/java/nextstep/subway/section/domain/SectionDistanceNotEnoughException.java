package nextstep.subway.section.domain;

import nextstep.subway.common.exception.ConflictException;

/**
 *
 * @author heetaek.kim
 */
public class SectionDistanceNotEnoughException extends ConflictException {
	public SectionDistanceNotEnoughException(int base, int append) {
		super(String.format("origin: %d, append: %s", base, append));
	}
}
