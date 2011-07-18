package net.sf.mpango.common.directory.enums;

/**
 * @author aplause
 * 
 */
public enum StateEnum {

	CREATED(0), ACTIVE(1), INACTIVE(2), DELETED(3);

	Integer value;

	StateEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public StateEnum fromValue(Integer value) {
		for (StateEnum val : StateEnum.values()) {
			if (val.getValue().compareTo(value) == 0) {
				return val;
			}
		}
		return null;
	}
}
