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
package eu.ueb.acem.domain.beans.neo4j.vert;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

import eu.ueb.acem.domain.beans.neo4j.AbstractNode;
import eu.ueb.acem.domain.beans.vert.PhysicalSpace;

/**
 * The Spring Data Neo4j implementation of PhysicalSpace abstract domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
@NodeEntity
@TypeAlias("PhysicalSpace")
public abstract class PhysicalSpaceNode extends AbstractNode implements PhysicalSpace {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -1689641655515811666L;

	public PhysicalSpaceNode() {
	}

	@Override
	public int compareTo(PhysicalSpace o) {
		return this.getName().compareTo(o.getName());
	}

}
