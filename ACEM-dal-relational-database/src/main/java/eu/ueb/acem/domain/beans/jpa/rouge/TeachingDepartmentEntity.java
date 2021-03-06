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
package eu.ueb.acem.domain.beans.jpa.rouge;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ueb.acem.domain.beans.jpa.violet.TeachingUnitEntity;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The Spring Data JPA implementation of TeachingDepartment domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "TeachingDepartment")
@XmlRootElement(name = "teachingDepartments")
public class TeachingDepartmentEntity extends OrganisationEntity implements TeachingDepartment {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4325895384017753521L;

	private String name;

	@ManyToMany(targetEntity = InstitutionEntity.class, fetch = FetchType.LAZY, mappedBy = "teachingDepartments")
	private Set<Institution> institutions = new HashSet<Institution>(0);

	@OneToMany(targetEntity = TeachingUnitEntity.class, fetch = FetchType.LAZY, mappedBy = "teachingDepartment")
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	public TeachingDepartmentEntity() {
	}

	public TeachingDepartmentEntity(String name, String shortname, String iconFileName) {
		this();
		setName(name);
		setShortname(shortname);
		setIconFileName(iconFileName);
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
	public Set<Institution> getInstitutions() {
		return institutions;
	}

	@Override
	public void setInstitutions(Set<Institution> institutions) {
		this.institutions = institutions;
	}

	@Override
	public Set<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	@Override
	public void setTeachingUnits(Set<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

}
