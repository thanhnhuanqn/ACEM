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
package eu.ueb.acem.domain.beans.jpa.bleu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jpa.gris.TeacherEntity;
import eu.ueb.acem.domain.beans.jpa.violet.TeachingUnitEntity;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The Spring Data JPA implementation of PedagogicalScenario domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "PedagogicalScenario")
@XmlRootElement(name = "pedagogicalScenarios")
public class PedagogicalScenarioEntity extends PedagogicalUnitEntity implements PedagogicalScenario {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -512829316498046439L;

	@Lob
	private String evaluationModes;

	private Boolean published;

	@JsonBackReference
	@OneToMany(targetEntity = TeachingUnitEntity.class, mappedBy = "pedagogicalScenario")
	private Set<TeachingUnit> teachingUnits = new HashSet<TeachingUnit>(0);

	@JsonManagedReference
	@ManyToMany(targetEntity = TeacherEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "Teachers_PedagogicalScenarios")
	private Set<Teacher> authors = new HashSet<Teacher>(0);

	@JsonBackReference
	@OneToMany(targetEntity = PedagogicalSequenceEntity.class, mappedBy = "pedagogicalScenario")
	private Set<PedagogicalSequence> pedagogicalSequences = new HashSet<PedagogicalSequence>(0);

	@OneToOne(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY)
	private PedagogicalScenario nextPedagogicalScenario;

	@OneToOne(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY, mappedBy = "nextPedagogicalScenario")
	private PedagogicalScenario previousPedagogicalScenario;

	public PedagogicalScenarioEntity() {
		published = false;
	}

	public PedagogicalScenarioEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalScenarioEntity(String name, String objective) {
		this(name);
		setObjective(objective);
	}

	@Override
	public String getEvaluationModes() {
		return evaluationModes;
	}

	@Override
	public void setEvaluationModes(String evaluationModes) {
		this.evaluationModes = evaluationModes;
	}

	@Override
	public Set<Teacher> getAuthors() {
		return authors;
	}

	@Override
	public void setAuthors(Set<Teacher> authors) {
		this.authors = authors;
	}

	@Override
	public Boolean isPublished() {
		return published;
	}

	@Override
	public void setPublished(Boolean published) {
		this.published = published;
	}

	@Override
	public Set<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	@Override
	public void setTeachingUnits(Set<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

	@Override
	public Set<PedagogicalSequence> getPedagogicalSequences() {
		return pedagogicalSequences;
	}

	@Override
	public void setPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences) {
		this.pedagogicalSequences = pedagogicalSequences;
	}

	@Override
	public PedagogicalScenario getPreviousPedagogicalScenario() {
		return previousPedagogicalScenario;
	}

	@Override
	public void setPreviousPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		this.previousPedagogicalScenario = pedagogicalScenario;
		if (pedagogicalScenario != null
				&& ((pedagogicalScenario.getNextPedagogicalScenario() == null) || ((pedagogicalScenario
						.getNextPedagogicalScenario() != null) && (!pedagogicalScenario.getNextPedagogicalScenario()
						.equals(this))))) {
			pedagogicalScenario.setNextPedagogicalScenario(this);
		}
	}

	@Override
	public PedagogicalScenario getNextPedagogicalScenario() {
		return nextPedagogicalScenario;
	}

	@Override
	public void setNextPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		this.nextPedagogicalScenario = pedagogicalScenario;
		if (pedagogicalScenario != null
				&& ((pedagogicalScenario.getPreviousPedagogicalScenario() == null) || ((pedagogicalScenario
						.getPreviousPedagogicalScenario() != null) && (!pedagogicalScenario
						.getPreviousPedagogicalScenario().equals(this))))) {
			pedagogicalScenario.setPreviousPedagogicalScenario(this);
		}
	}

}
