package webscada.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "role")
public class Role extends AEntity<Integer> {

	@Column(name = "role")
	private String roleName;

	@Override
	public String toString() {
		return this.roleName.substring(5);
	}
}
