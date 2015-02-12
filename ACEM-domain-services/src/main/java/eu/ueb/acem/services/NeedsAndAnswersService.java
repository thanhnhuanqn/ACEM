/**
 *     Copyright Grégoire COLBERT 2013
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
package eu.ueb.acem.services;

import java.util.Collection;

import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.Resource;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface NeedsAndAnswersService {

	Boolean deleteNode(Long id);
	
	Long countNeeds();

	PedagogicalNeed createPedagogicalNeed(String name);

	Collection<PedagogicalNeed> retrieveNeedsAtRoot();

	PedagogicalNeed retrievePedagogicalNeed(Long id, boolean initialize);

	PedagogicalNeed updatePedagogicalNeed(PedagogicalNeed need);

	Boolean deleteNeed(Long id);

	Long countAnswers();

	PedagogicalAnswer createPedagogicalAnswer(String name);

	PedagogicalAnswer retrievePedagogicalAnswer(Long id, boolean initialize);

	PedagogicalAnswer updatePedagogicalAnswer(PedagogicalAnswer answer);

	Boolean deleteAnswer(Long id);

	PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent);

	void saveNeedName(Long id, String newName);
	
	void changeParentOfNeedOrAnswer(Long id, Long idNewParent);

	PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed);

	void saveAnswerName(Long id, String newName);

	Collection<PedagogicalScenario> getScenariosRelatedToAnswer(Long id);

	Collection<Resource> getResourcesRelatedToAnswer(Long id);

	Boolean associateAnswerWithResourceCategory(Long answerId, Long toolCategoryId);

	Boolean dissociateAnswerWithResourceCategory(Long answerId, Long toolCategoryId);

}
