package webscada.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "value")											
public class Value extends AEntity<Long> {

    @Column(name = "name")
    private String name;
      //TODO !!!NAMEING CONVENSION!!!
    //TODO сделать через enum ИЛИ прикрутить еще одну таблицу с возможными вариантами единиц измерений
    @Column(name = "units")
    private String units;    
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dev_id", referencedColumnName = "id")
    private Dev devId;
    
    @Column(name = "addr")
    private String addr;  
    
    @Column(name = "max")
    private float max;  
    
    @Column(name = "max_ms")
    private int maxMs;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "max_event_id", referencedColumnName = "id")
    private TypeEvent maxEventId;
 
    @Column(name = "min")
    private float min;  
    
    @Column(name = "min_ms")
    private int minMs;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "min_event_id", referencedColumnName = "id")
    private TypeEvent minEventId;
}
