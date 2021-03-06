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
package eu.ueb.acem.domain.beans.bleu;

import java.util.Set;

import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * The PedagogicalScenario domain bean interface.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
public interface PedagogicalScenario extends PedagogicalUnit {

	String getEvaluationModes();

	void setEvaluationModes(String evaluationModes);

	Set<Teacher> getAuthors();

	void setAuthors(Set<Teacher> authors);

	Boolean isPublished();

	void setPublished(Boolean published);

	Set<TeachingUnit> getTeachingUnits();

	void setTeachingUnits(Set<TeachingUnit> teachingUnits);

	Set<PedagogicalSequence> getPedagogicalSequences();

	void setPedagogicalSequences(Set<PedagogicalSequence> pedagogicalSequences);

	PedagogicalScenario getPreviousPedagogicalScenario();

	void setPreviousPedagogicalScenario(PedagogicalScenario pedagogicalScenario);

	PedagogicalScenario getNextPedagogicalScenario();

	void setNextPedagogicalScenario(PedagogicalScenario pedagogicalScenario);

}
