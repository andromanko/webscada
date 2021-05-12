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
@Table(name = "dev_type")
public class DevType extends AEntity<Integer> {

	@Column(name = "type")
	private String devType;

	@Column(name = "description")
	private String description;

	@Column(name = "url")
	private String url;

	@OneToMany(mappedBy = "type_id", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Dev> devs;
}
