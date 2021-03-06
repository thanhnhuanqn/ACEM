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
package eu.ueb.acem.domain.beans.neo4j.jaune;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.neo4j.vert.PhysicalSpaceNode;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * The Spring Data Neo4j implementation of Equipment domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Equipment")
public class EquipmentNode extends ResourceNode implements Equipment {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 795694384595044050L;

	@Indexed
	private String name;
	
	private Integer quantity;

	private Boolean mobile;

	@RelatedTo(elementClass = PhysicalSpaceNode.class, type = "isStoredIn", direction = OUTGOING)
	private Set<PhysicalSpace> storageLocations = new HashSet<PhysicalSpace>(0);
	
	public EquipmentNode() {
		super();
	}

	public EquipmentNode(String name, String iconFileName) {
		this();
		setName(name);
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
	public Set<PhysicalSpace> getStorageLocations() {
		return storageLocations;
	}
	
	@Override
	public void setStorageLocations(Set<PhysicalSpace> storageLocations) {
		this.storageLocations = storageLocations;
	}
	
	@Override
	public Integer getQuantity() {
		return quantity;
	}

	@Override
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public Boolean isMobile() {
		return mobile;
	}

	@Override
	public void setMobile(Boolean isMobile) {
		this.mobile = isMobile;
	}

}
