package webscada.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="events")
public class Event extends AEntity<Integer> {
	
	@Column(name="dateTime")//время события
	private Date dateTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dev_id", referencedColumnName = "id")
    private Dev dev_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user_id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private TypesEvents event_id;	

	
//	@Column(name="evalue1")//переменная 1
//	private int evalue1;
//	
//	@Column(name="evalue2")//переменная 2
//	private int evalue2;
//	
//	@Column(name="ewarning")//признак предупроеждения события
//	private boolean ewarning;
//	
//	@Column(name="ealarm") //признак аварийного события
//	private boolean ealarm;
	
	@Column(name="chDate") //время квитирования
	private Date chDate;
	
//	OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade =CascadeType.PERSIST, orphanRemoval=true)
//	private List<Events> pets;

}

