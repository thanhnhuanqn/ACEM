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
package eu.ueb.acem.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalAnswerDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalNeedDAO;
import eu.ueb.acem.dal.common.bleu.PedagogicalScenarioDAO;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

/**
 * Implementation of NeedsAndAnswersService.
 * 
 * @author Grégoire Colbert
 * @since 2013-11-20
 */
@Service("needsAndAnswersService")
public class NeedsAndAnswersServiceImpl implements NeedsAndAnswersService {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 3642737254578203234L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersServiceImpl.class);

	@Inject
	private PedagogicalNeedDAO<Long> needDAO;

	@Inject
	private PedagogicalAnswerDAO<Long> answerDAO;

	@Inject
	private DAO<Long, ResourceCategory> resourceCategoryDAO;

	@Inject
	private PedagogicalScenarioDAO<Long> pedagogicalScenarioDAO;

	public NeedsAndAnswersServiceImpl() {
	}

	@Override
	public Boolean deletePedagogicalNeedOrPedagogicalAnswer(Long id) {
		if (needDAO.exists(id)) {
			return deleteNeed(id);
		}
		else if (answerDAO.exists(id)) {
			return deleteAnswer(id);
		}
		else {
			return false;
		}
	}

	@Override
	public Long countNeeds() {
		return needDAO.count();
	}

	@Override
	public PedagogicalNeed createPedagogicalNeed(String name) {
		return needDAO.create(name);
	}

	@Override
	public PedagogicalNeed createPedagogicalNeed(String name, String description) {
		return needDAO.create(name, description);
	}

	@Override
	public PedagogicalNeed createPedagogicalNeed(String name, PedagogicalNeed parent) {
		PedagogicalNeed need = needDAO.create(name);
		if ((need != null) && (parent != null)) {
			need.getParents().add(parent);
			parent.getChildren().add(need);
			need = needDAO.update(need);
			parent = needDAO.update(parent);
		}
		return need;
	}

	@Override
	public PedagogicalNeed createPedagogicalNeed(String name, String description, PedagogicalNeed parent) {
		PedagogicalNeed need = needDAO.create(name, description);
		if ((need != null) && (parent != null)) {
			need.getParents().add(parent);
			parent.getChildren().add(need);
			need = needDAO.update(need);
			parent = needDAO.update(parent);
		}
		return need;
	}

	@Override
	public Collection<PedagogicalNeed> retrieveNeedsAtRoot() {
		return needDAO.retrieveNeedsAtRoot();
	}

	@Override
	public PedagogicalNeed retrievePedagogicalNeed(Long id, boolean initialize) {
		return needDAO.retrieveById(id, initialize);
	}

	@Override
	@Transactional
	public PedagogicalNeed updatePedagogicalNeed(PedagogicalNeed pedagogicalNeed) {
		return needDAO.update(pedagogicalNeed);
	}

	@Override
	@Transactional
	public Boolean deleteNeed(Long id) {
		if (needDAO.exists(id)) {
			needDAO.delete(needDAO.retrieveById(id));
		}
		return !needDAO.exists(id);
	}

	@Override
	public Long countAnswers() {
		return answerDAO.count();
	}

	@Override
	public PedagogicalAnswer createPedagogicalAnswer(String name) {
		return answerDAO.create(name);
	}

	@Override
	public PedagogicalAnswer createPedagogicalAnswer(String name, String description) {
		return answerDAO.create(name, description);
	}

	@Override
	public PedagogicalAnswer createPedagogicalAnswer(String name, PedagogicalNeed parent) {
		PedagogicalAnswer answer = answerDAO.create(name);
		if ((answer != null) && (parent != null)) {
			answer.getNeeds().add(parent);
			parent.getAnswers().add(answer);
			answer = answerDAO.update(answer);
			parent = needDAO.update(parent);
		}
		return answer;
	}

	@Override
	public PedagogicalAnswer createPedagogicalAnswer(String name, String description, PedagogicalNeed parent) {
		PedagogicalAnswer answer = answerDAO.create(name, description);
		if ((answer != null) && (parent != null)) {
			answer.getNeeds().add(parent);
			parent.getAnswers().add(answer);
			answer = answerDAO.update(answer);
			parent = needDAO.update(parent);
		}
		return answer;
	}

	@Override
	public PedagogicalAnswer retrievePedagogicalAnswer(Long id, boolean initialize) {
		return answerDAO.retrieveById(id, initialize);
	}

	@Override
	public PedagogicalAnswer updatePedagogicalAnswer(PedagogicalAnswer pedagogicalAnswer) {
		return answerDAO.update(pedagogicalAnswer);
	}

	@Override
	@Transactional
	public Boolean deleteAnswer(Long id) {
		if (answerDAO.exists(id)) {
			answerDAO.delete(answerDAO.retrieveById(id));
		}
		return !answerDAO.exists(id);
	}

	@Override
	@Transactional
	public PedagogicalNeed createOrUpdateNeed(Long id, String name, Long idParent) {
		PedagogicalNeed need = null;
		if (needDAO.exists(id)) {
			need = needDAO.retrieveById(id);
			if (need.getName() != name) {
				need.setName(name);
				need = needDAO.update(need);
			}
		} else {
			need = needDAO.create(name);
		}
		if (needDAO.exists(idParent)) {
			PedagogicalNeed parent = needDAO.retrieveById(idParent);
			if (!parent.getChildren().contains(need)) {
				need.getParents().add(parent);
				parent.getChildren().add(need);
				need = needDAO.update(need);
				parent = needDAO.update(parent);
			}
		}
		return need;
	}

	@Override
	@Transactional
	public void changeParentOfNeedOrAnswer(Long id, Long idNewParent) {
		logger.debug("entering changeParentOfNeedOrAnswer({}, {})", id, idNewParent);
		// The "visible root node" has id=null, but it is forbidden to move it.
		// Moreover, the id parameter must identify an existing entity.
		if ((id != null) && (needDAO.exists(id) || answerDAO.exists(id))) {
			PedagogicalNeed need = null;
			PedagogicalAnswer answer = null;
			Collection<PedagogicalNeed> parents = null;
			if (needDAO.exists(id)) {
				need = needDAO.retrieveById(id);
				parents = need.getParents();
			}
			else if (answerDAO.exists(id)) {
				answer = answerDAO.retrieveById(id);
				parents = answer.getNeeds();
			}
			// We remove the parents of the moved node
			if (! parents.isEmpty()) {
				logger.debug("The moved node has parents that we should unlink: {}", parents);
				Iterator<PedagogicalNeed> iterator = parents.iterator();
				while (iterator.hasNext()) {
					iterator.next();
					iterator.remove();
					logger.info("parent is unlinked");
				}
			} else {
				logger.debug("The moved node has no parents (must be a child of the root).");
			}
			// We have removed the parents of the moved node
			// Now we have to connect its new parent
			// Please note that the parent is always a PedagogicalNeed
			// according to the current domain model.
			if ((idNewParent != null) && (needDAO.exists(idNewParent))) {
				PedagogicalNeed newParent = needDAO.retrieveById(idNewParent);
				logger.debug("newParent={}", newParent);
				if (need != null) {
					newParent.getChildren().add(need);
					need.getParents().add(newParent);
				}
				else if (answer != null) {
					newParent.getAnswers().add(answer);
					answer.getNeeds().add(newParent);
				}
				newParent = needDAO.update(newParent);
			}
			else {
				logger.debug("The new parent is not a PedagogicalNeed (must be the root).");
			}
			// We have to update the need or the answer
			if (need != null) {
				need = needDAO.update(need);
				logger.debug("Need is updated");
			}
			else  if (answer != null) {
				answer = answerDAO.update(answer);
				logger.debug("Answer is updated");
			}
		}
		else {
			logger.error("There is no PedagogicalNeed, nor PedagogicalAnswer, with id={}", id);
		}
		logger.debug("leaving changeParentOfNeedOrAnswer({}, {})", id, idNewParent);
	}

	@Override
	@Transactional
	public PedagogicalAnswer createOrUpdateAnswer(Long id, String name, Long idAssociatedNeed) {
		PedagogicalAnswer answer = null;
		if (answerDAO.exists(id)) {
			answer = answerDAO.retrieveById(id);
			answer.setName(name);
			answer = answerDAO.update(answer);
		} else {
			answer = answerDAO.create(name);
		}
		if (needDAO.exists(idAssociatedNeed)) {
			PedagogicalNeed associatedNeed = needDAO.retrieveById(idAssociatedNeed);
			associatedNeed.getAnswers().add(answer);
			answer.getNeeds().add(associatedNeed);
			needDAO.update(associatedNeed);
			answerDAO.update(answer);
		}
		return answer;
	}

	@Override
	public Collection<PedagogicalScenario> retrieveScenariosRelatedToAnswer(Long id) {
		PedagogicalAnswer answer = answerDAO.retrieveById(id);
		Collection<PedagogicalScenario> scenarios = new HashSet<PedagogicalScenario>();
		if (answer!=null) {
			scenarios = pedagogicalScenarioDAO.retrieveScenariosAssociatedWithPedagogicalAnswer(answer);
		}
		return scenarios;
	}

	@Override
	public Boolean associateAnswerWithResourceCategory(Long answerId, Long toolCategoryId) {
		PedagogicalAnswer answer = answerDAO.retrieveById(answerId);
		ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId);

		answer.getResourceCategories().add(resourceCategory);
		resourceCategory.getAnswers().add(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return answer.getResourceCategories().contains(resourceCategory) && resourceCategory.getAnswers().contains(answer);
	}

	@Override
	public Boolean associateAnswerWithResourceCategory(PedagogicalAnswer answer, ResourceCategory resourceCategory) {
		answer.getResourceCategories().add(resourceCategory);
		resourceCategory.getAnswers().add(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return answer.getResourceCategories().contains(resourceCategory) && resourceCategory.getAnswers().contains(answer);
	}

	@Override
	public Boolean dissociateAnswerWithResourceCategory(Long answerId, Long toolCategoryId) {
		PedagogicalAnswer answer = answerDAO.retrieveById(answerId);
		ResourceCategory resourceCategory = resourceCategoryDAO.retrieveById(toolCategoryId);

		answer.getResourceCategories().remove(resourceCategory);
		resourceCategory.getAnswers().remove(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return !answer.getResourceCategories().contains(resourceCategory) && !resourceCategory.getAnswers().contains(answer);
	}

	@Override
	public Boolean dissociateAnswerWithResourceCategory(PedagogicalAnswer answer, ResourceCategory resourceCategory) {
		answer.getResourceCategories().remove(resourceCategory);
		resourceCategory.getAnswers().remove(answer);

		answer = answerDAO.update(answer);
		resourceCategory = resourceCategoryDAO.update(resourceCategory);
		return !answer.getResourceCategories().contains(resourceCategory) && !resourceCategory.getAnswers().contains(answer);
	}
	
}
