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
package eu.ueb.acem.domain.beans.neo4j.violet;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.violet.Class;
import eu.ueb.acem.domain.beans.violet.Course;
import eu.ueb.acem.domain.beans.violet.Credit;

/**
 * The Spring Data Neo4j implementation of Course domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
@NodeEntity
@TypeAlias("Course")
public class CourseNode extends TeachingUnitNode implements Course {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4467389921550574916L;

	@Indexed
	private String name;

	@RelatedTo(elementClass = CreditNode.class, type = "coursePartOfCredit", direction = OUTGOING)
	private Credit credit;

	@RelatedTo(elementClass = ClassNode.class, type = "classPartOfCourse", direction = INCOMING)
	private Set<Class> classes;

	public CourseNode() {
	}

	public CourseNode(String name) {
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
	public Credit getCredit() {
		return credit;
	}

	@Override
	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	@Override
	public Set<Class> getClasses() {
		return classes;
	}

	@Override
	public void setClasses(Set<Class> classes) {
		this.classes = classes;
	}

}
