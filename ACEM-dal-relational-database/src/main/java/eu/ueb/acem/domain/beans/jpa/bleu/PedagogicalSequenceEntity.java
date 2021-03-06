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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSequence;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * The Spring Data JPA implementation of PedagogicalSequence domain bean.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-13
 * 
 */
@Entity(name = "PedagogicalSequence")
public class PedagogicalSequenceEntity extends PedagogicalUnitEntity implements PedagogicalSequence {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6801650792009270440L;

	@ManyToOne(targetEntity = PedagogicalScenarioEntity.class, fetch = FetchType.LAZY)
	private PedagogicalScenario pedagogicalScenario;

	@OneToMany(targetEntity = PedagogicalSessionEntity.class, fetch = FetchType.LAZY, mappedBy = "pedagogicalSequence")
	private Set<PedagogicalSession> pedagogicalSessions = new HashSet<PedagogicalSession>(0);

	@OneToMany(targetEntity = PedagogicalActivityEntity.class, fetch = FetchType.LAZY, mappedBy = "pedagogicalSequence")
	private Set<PedagogicalActivity> pedagogicalActivities = new HashSet<PedagogicalActivity>(0);

	@OneToOne(targetEntity = PedagogicalSequenceEntity.class, fetch = FetchType.LAZY)
	private PedagogicalSequence nextPedagogicalSequence;

	@OneToOne(targetEntity = PedagogicalSequenceEntity.class, fetch = FetchType.LAZY, mappedBy = "nextPedagogicalSequence")
	private PedagogicalSequence previousPedagogicalSequence;

	public PedagogicalSequenceEntity() {
	}

	public PedagogicalSequenceEntity(String name) {
		this();
		setName(name);
	}

	public PedagogicalSequenceEntity(String name, String objective) {
		this(name);
		setObjective(objective);
	}

	@Override
	public PedagogicalScenario getPedagogicalScenario() {
		return pedagogicalScenario;
	}

	@Override
	public void setPedagogicalScenario(PedagogicalScenario pedagogicalScenario) {
		this.pedagogicalScenario = pedagogicalScenario;
	}

	@Override
	public Set<PedagogicalSession> getPedagogicalSessions() {
		return pedagogicalSessions;
	}

	@Override
	public void setPedagogicalSessions(Set<PedagogicalSession> pedagogicalSessions) {
		this.pedagogicalSessions = pedagogicalSessions;
	}

	@Override
	public Set<PedagogicalActivity> getPedagogicalActivities() {
		return pedagogicalActivities;
	}

	@Override
	public void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities) {
		this.pedagogicalActivities = pedagogicalActivities;
	}

	@Override
	public PedagogicalSequence getPreviousPedagogicalSequence() {
		return previousPedagogicalSequence;
	}

	@Override
	public void setPreviousPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		this.previousPedagogicalSequence = pedagogicalSequence;
		if (pedagogicalSequence != null
				&& ((pedagogicalSequence.getNextPedagogicalSequence() == null) || ((pedagogicalSequence
						.getNextPedagogicalSequence() != null) && (!pedagogicalSequence.getNextPedagogicalSequence()
						.equals(this))))) {
			pedagogicalSequence.setNextPedagogicalSequence(this);
		}
	}

	@Override
	public PedagogicalSequence getNextPedagogicalSequence() {
		return nextPedagogicalSequence;
	}

	@Override
	public void setNextPedagogicalSequence(PedagogicalSequence pedagogicalSequence) {
		this.nextPedagogicalSequence = pedagogicalSequence;
		if (pedagogicalSequence != null
				&& ((pedagogicalSequence.getPreviousPedagogicalSequence() == null) || ((pedagogicalSequence
						.getPreviousPedagogicalSequence() != null) && (!pedagogicalSequence
						.getPreviousPedagogicalSequence().equals(this))))) {
			pedagogicalSequence.setPreviousPedagogicalSequence(this);
		}
	}

}
