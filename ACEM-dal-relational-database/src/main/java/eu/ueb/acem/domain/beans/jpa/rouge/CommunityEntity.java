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
import javax.xml.bind.annotation.XmlRootElement;

import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * The Spring Data JPA implementation of Community domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Community")
@XmlRootElement(name = "communities")
public class CommunityEntity extends OrganisationEntity implements Community {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -9130069872778981291L;

	private String name;

	private String supannEtablissement;

	@ManyToMany(targetEntity = InstitutionEntity.class, fetch = FetchType.LAZY, mappedBy = "communities")
	private Set<Institution> institutions = new HashSet<Institution>(0);

	public CommunityEntity() {
		super();
	}

	public CommunityEntity(String name, String shortname, String iconFileName) {
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
	public String getSupannEtablissement() {
		return supannEtablissement;
	}

	@Override
	public void setSupannEtablissement(String supannEtablissement) {
		this.supannEtablissement = supannEtablissement;
	}

	@Override
	public Set<Institution> getInstitutions() {
		return institutions;
	}

	@Override
	public void setInstitutions(Set<Institution> institutions) {
		this.institutions = institutions;
	}

}
