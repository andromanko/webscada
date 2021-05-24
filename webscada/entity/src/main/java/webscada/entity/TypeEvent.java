package webscada.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "type_event")
public class TypeEvent extends AEntity<Integer> {

	public static final int EVENT_PRESSURE_HIGH = 1;
	public static final int EVENT_PRESSURE_LOW = 2;
	public static final int EVENT_USER_REGISTER = 3;
	public static final int EVENT_USER_LOG_IN = 4;
	public static final int EVENT_USER_LOG_OUT = 5;
	public static final int USER_NOT_VALID = 6;
	public static final int EVENT_APPLICATION_STARTED = 7;
	public static final int EVENT_APPLICATION_STOPPED = 8;
	public static final int EVENT_LEVEL_HIGH = 9;
	public static final int EVENT_LEVEL_LOW = 10;

	@Column(name = "event_name")
	private String eventName;

	@OneToMany(mappedBy = "eventType", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Event> events;

	@OneToMany(mappedBy = "maxEventId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Value> maxValues;

	@OneToMany(mappedBy = "minEventId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Value> minValues;

	@Override
	public String toString() {
		return this.eventName;
	}

}
