package webscada.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
//import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "event")
public class Event extends AEntity<Integer> {

	@Column(name = "dateTime") // время события
	private Date dateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dev_id", referencedColumnName = "id")
	private Dev dev;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	private TypeEvent eventType;

	// TODO время квитирования
	@Column(name = "chDate")
	private Date chDate;

}
