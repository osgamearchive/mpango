package net.sf.mpango.game.core.enums;

public enum ConstructionType {
	OTHER(0), CITY(1);

	Integer value;

	ConstructionType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public ConstructionType fromValue(Integer value) {
		for (ConstructionType val : ConstructionType.values()) {
			if (val.getValue().compareTo(value) == 0) {
				return val;
			}
		}
		return null;
	}
}
