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

import org.codehaus.jackson.annotate.JsonBackReference;

import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.jpa.jaune.ResourceEntity;
import eu.ueb.acem.domain.beans.jpa.jaune.UseModeEntity;
import eu.ueb.acem.domain.beans.jpa.vert.PhysicalSpaceEntity;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * The Spring Data JPA implementation of Organisation domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Organisation")
@XmlRootElement(name = "organisations")
public abstract class OrganisationEntity extends AbstractEntity implements Organisation {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2531021468394665325L;

	private Long idSource;

	private String shortname;

	private String description;

	private String iconFileName;

	private String contactMode;

	@OneToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY, mappedBy = "organisationPossessingResource")
	@JsonBackReference
	private Set<Resource> possessedResources = new HashSet<Resource>(0);

	@OneToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY, mappedBy = "organisationSupportingResource")
	@JsonBackReference
	private Set<Resource> supportedResources = new HashSet<Resource>(0);

	@ManyToMany(targetEntity = ResourceEntity.class, fetch = FetchType.LAZY, mappedBy = "organisationsHavingAccessToResource")
	@JsonBackReference
	private Set<Resource> viewedResources = new HashSet<Resource>(0);

	@OneToMany(targetEntity = UseModeEntity.class, fetch = FetchType.LAZY, mappedBy = "referredOrganisation")
	@JsonBackReference
	private Set<UseMode> useModes = new HashSet<UseMode>(0);

	@ManyToMany(targetEntity = PhysicalSpaceEntity.class, fetch = FetchType.LAZY)
	private Set<PhysicalSpace> occupiedPhysicalSpaces = new HashSet<PhysicalSpace>(0);

	public OrganisationEntity() {
	}

	@Override
	public Long getIdSource() {
		return idSource;
	}

	@Override
	public void setIdSource(Long idSource) {
		this.idSource = idSource;
	}

	@Override
	public String getShortname() {
		return shortname;
	}

	@Override
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public String getContactMode() {
		return contactMode;
	}

	@Override
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	@Override
	public Set<Resource> getPossessedResources() {
		return possessedResources;
	}

	@Override
	public void setPossessedResources(Set<Resource> possessedResources) {
		this.possessedResources = possessedResources;
	}

	@Override
	public Set<Resource> getSupportedResources() {
		return supportedResources;
	}

	@Override
	public void setSupportedResources(Set<Resource> supportedResources) {
		this.supportedResources = supportedResources;
	}

	@Override
	public Set<Resource> getViewedResources() {
		return viewedResources;
	}

	@Override
	public void setViewedResources(Set<Resource> viewedResources) {
		this.viewedResources = viewedResources;
	}

	@Override
	public Set<UseMode> getUseModes() {
		return useModes;
	}

	@Override
	public void setUseModes(Set<UseMode> referringUseModes) {
		this.useModes = referringUseModes;
	}

	@Override
	public Set<PhysicalSpace> getOccupiedPhysicalSpaces() {
		return occupiedPhysicalSpaces;
	}

	@Override
	public void setOccupiedPhysicalSpaces(Set<PhysicalSpace> occupiedPhysicalSpaces) {
		this.occupiedPhysicalSpaces = occupiedPhysicalSpaces;
	}

	@Override
	public int compareTo(Organisation o) {
		return this.getName().compareTo(o.getName());
	}

}
