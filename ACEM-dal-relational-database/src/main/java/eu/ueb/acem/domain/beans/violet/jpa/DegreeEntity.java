/**
 *     Copyright Université Européenne de Bretagne 2012-2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.domain.beans.violet.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.violet.Credit;
import eu.ueb.acem.domain.beans.violet.Degree;

/**
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Entity(name = "Degree")
@Table(name = "Degree")
public class DegreeEntity extends AbstractEntity implements Degree {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3867978682705242939L;

	@ManyToMany(targetEntity = CreditEntity.class, fetch = FetchType.LAZY, mappedBy = "degrees")
	private Set<Credit> credits = new HashSet<Credit>(0);
	
	private String name;

	public DegreeEntity() {
	}

	public DegreeEntity(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Set<Credit> getCredits() {
		return credits;
	}

	@Override
	public void setCredits(Set<Credit> credits) {
		this.credits = credits;
	}

	@Override
	public int compareTo(Degree o) {
		return this.getName().compareTo(o.getName());
	}

}
