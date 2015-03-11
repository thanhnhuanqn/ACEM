/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.SortableTableBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalActivityViewBean;
import eu.ueb.acem.web.viewbeans.bleu.PedagogicalScenarioViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-19
 * 
 */
@Controller("myScenariosController")
@Scope("view")
public class MyScenariosController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 2943632466935430900L;

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyScenariosController.class);

	private PedagogicalScenarioViewBean selectedPedagogicalScenarioViewBean;

	private PedagogicalScenarioViewBean objectEditedPedagogicalScenarioViewBean;
	
	private PedagogicalActivityViewBean objectEditedPedagogicalActivityViewBean;

	@Inject
	private ScenariosService scenariosService;

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private SortableTableBean<PedagogicalScenarioViewBean> pedagogicalScenarioViewBeans;

	@Inject
	private PickListBean pickListBean;

	public MyScenariosController() {
		pedagogicalScenarioViewBeans = new SortableTableBean<PedagogicalScenarioViewBean>();
	}

	@PostConstruct
	public void init() {
		logger.info("Entering init()");
		Collection<PedagogicalScenario> scenariosOfCurrentUser = scenariosService.retrieveScenariosWithAuthor(getCurrentUser());
		pedagogicalScenarioViewBeans.getTableEntries().clear();
		for (PedagogicalScenario scenario : scenariosOfCurrentUser) {
			pedagogicalScenarioViewBeans.getTableEntries().add(new PedagogicalScenarioViewBean(scenario));
		}
		pedagogicalScenarioViewBeans.sortReverseOrder();
		logger.debug("Leaving init()");
	}

	@Override
	public String getPageTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(msgs.getMessage("MENU.MY_SCENARIOS", null, getCurrentUserLocale()));
		if (getSelectedPedagogicalScenarioViewBean() != null) {
			sb.append(" - ");
			sb.append(getSelectedPedagogicalScenarioViewBean().getName());
		}
		return sb.toString();
	}

	/*
	 * ****************** Pedagogical scenarios ********************
	 */
	public Long getSelectedScenarioId() {
		if (getSelectedPedagogicalScenarioViewBean() != null) {
			return getSelectedPedagogicalScenarioViewBean().getId();
		}
		else {
			return null;
		}
	}

	public void setSelectedScenarioId(Long scenarioId) {
		logger.info("Entering setSelectedScenarioId, scenarioId = {}", scenarioId);
		PedagogicalScenario scenario = scenariosService.retrievePedagogicalScenario(scenarioId,  true);
		if (scenario != null) {
			setSelectedPedagogicalScenarioViewBean(new PedagogicalScenarioViewBean(scenario));
		}
		logger.info("Leaving setSelectedScenarioId, scenarioId = {}", scenarioId);
	}

	public List<PedagogicalScenarioViewBean> getScenarioViewBeans() {
		return pedagogicalScenarioViewBeans.getTableEntries();
	}

	public PedagogicalScenarioViewBean getSelectedPedagogicalScenarioViewBean() {
		return selectedPedagogicalScenarioViewBean;
	}

	public void setSelectedPedagogicalScenarioViewBean(PedagogicalScenarioViewBean selectedScenarioViewBean) {
		this.selectedPedagogicalScenarioViewBean = selectedScenarioViewBean;
		if (this.selectedPedagogicalScenarioViewBean != null) {
			// We create the pedagogicalActivityViewBeans associated with the
			// selected scenario
			this.selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().clear();
			for (PedagogicalActivity pedagogicalActivity : this.selectedPedagogicalScenarioViewBean.getDomainBean()
					.getPedagogicalActivities()) {
				pedagogicalActivity = scenariosService.retrievePedagogicalActivity(pedagogicalActivity.getId(), true);
				PedagogicalActivityViewBean pedagogicalActivityViewBean = new PedagogicalActivityViewBean(
						pedagogicalActivity);
				this.selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().add(pedagogicalActivityViewBean);
				for (ResourceCategory resourceCategory : pedagogicalActivityViewBean.getDomainBean()
						.getResourceCategories()) {
					ToolCategoryViewBean toolCategoryViewBean = new ToolCategoryViewBean(resourceCategory);
					pedagogicalActivityViewBean.getToolCategoryViewBeans().add(toolCategoryViewBean);
				}
				Collections.sort(pedagogicalActivityViewBean.getToolCategoryViewBeans());
			}
			Collections.sort(this.selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans());
		}
	}

	public void onScenarioRowSelect(SelectEvent event) {
		setSelectedPedagogicalScenarioViewBean((PedagogicalScenarioViewBean) event.getObject());
	}

	public void deleteSelectedScenario() {
		if (selectedPedagogicalScenarioViewBean != null) {
			if (scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(selectedPedagogicalScenarioViewBean.getId(), getCurrentUser().getId())) {
				((Teacher)getCurrentUserViewBean().getDomainBean()).getScenarios().remove(selectedPedagogicalScenarioViewBean);
				pedagogicalScenarioViewBeans.getTableEntries().remove(selectedPedagogicalScenarioViewBean);
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_SCENARIO.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()),
						logger);
			}
			selectedPedagogicalScenarioViewBean = null;
		}
	}

	public void prepareCreatePedagogicalScenario() {
		objectEditedPedagogicalScenarioViewBean = new PedagogicalScenarioViewBean();
		selectedPedagogicalScenarioViewBean = null;
	}

	public void prepareModifyPedagogicalScenario() {
		objectEditedPedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(scenariosService.retrievePedagogicalScenario(
				selectedPedagogicalScenarioViewBean.getId(), true));
	}

	/*
	 * ****************** Create/modify scenario dialogs ***********
	 */
	public PedagogicalScenarioViewBean getObjectEditedPedagogicalScenarioViewBean() {
		return objectEditedPedagogicalScenarioViewBean;
	}

	public void onSavePedagogicalScenario() {
		if (objectEditedPedagogicalScenarioViewBean.getDomainBean()==null) {
			// Create
			PedagogicalScenario scenario = scenariosService.createScenario((Teacher) getCurrentUser(),
					objectEditedPedagogicalScenarioViewBean.getName(), objectEditedPedagogicalScenarioViewBean.getObjective());
			if (scenario != null) {
				PedagogicalScenarioViewBean pedagogicalScenarioViewBean = new PedagogicalScenarioViewBean(scenario);
				pedagogicalScenarioViewBeans.getTableEntries().add(pedagogicalScenarioViewBean);
				setSelectedPedagogicalScenarioViewBean(pedagogicalScenarioViewBean);
				MessageDisplayer.info(msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.TITLE",
						null, getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.CREATE_SCENARIO.CREATION_SUCCESSFUL.DETAILS", null, getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.error(msgs.getMessage("MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.TITLE", null,
						getCurrentUserLocale()), msgs.getMessage(
						"MY_SCENARIOS.CREATE_SCENARIO.CREATION_FAILED.DETAILS", null, getCurrentUserLocale()),
						logger);
			}
		}
		else {
			// Update
			selectedPedagogicalScenarioViewBean.setName(objectEditedPedagogicalScenarioViewBean.getName());
			selectedPedagogicalScenarioViewBean.setObjective(objectEditedPedagogicalScenarioViewBean.getObjective());
			selectedPedagogicalScenarioViewBean.setEvaluationModes(objectEditedPedagogicalScenarioViewBean.getEvaluationModes());
			selectedPedagogicalScenarioViewBean.setScenario(scenariosService.updateScenario(selectedPedagogicalScenarioViewBean.getDomainBean()));
			MessageDisplayer.info(
					msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
					msgs.getMessage("MY_SCENARIOS.SELECTED_SCENARIO.SAVE_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
		}
		pedagogicalScenarioViewBeans.sortReverseOrder();
	}
	
	/*
	 * ****************** Pedagogical activities *******************
	 */
	public PedagogicalActivityViewBean getObjectEditedPedagogicalActivityViewBean() {
		return objectEditedPedagogicalActivityViewBean;
	}

	public void prepareCreatePedagogicalActivity() {
		objectEditedPedagogicalActivityViewBean = new PedagogicalActivityViewBean();
		preparePicklistToolCategoryViewBeansForPedagogicalActivity(null);
	}

	public void prepareModifyPedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		objectEditedPedagogicalActivityViewBean = new PedagogicalActivityViewBean(scenariosService.retrievePedagogicalActivity(
				pedagogicalActivityViewBean.getId(), true));
		preparePicklistToolCategoryViewBeansForPedagogicalActivity(pedagogicalActivityViewBean);
	}

	public void preparePicklistToolCategoryViewBeansForPedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		List<ToolCategoryViewBean> allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
		for (ResourceCategory toolCategory : resourcesService.retrieveAllCategories()) {
			allToolCategoryViewBeans.add(new ToolCategoryViewBean(toolCategory));
		}
		pickListBean.getPickListEntities().getSource().clear();
		pickListBean.getPickListEntities().getSource().addAll(allToolCategoryViewBeans);
		pickListBean.getPickListEntities().getTarget().clear();
		if (pedagogicalActivityViewBean != null) {
			for (ToolCategoryViewBean toolCategoryViewBean : pedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				pickListBean.getPickListEntities().getSource().remove(toolCategoryViewBean);
				pickListBean.getPickListEntities().getTarget().add(toolCategoryViewBean);
			}
		}
	}

	public void onDeletePedagogicalActivity(PedagogicalActivityViewBean pedagogicalActivityViewBean) {
		if (pedagogicalActivityViewBean != null) {
			if (scenariosService.deletePedagogicalActivity(pedagogicalActivityViewBean.getDomainBean().getId())) {
				int positionOfDeletedActivity = selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().indexOf(pedagogicalActivityViewBean);
				selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().remove(pedagogicalActivityViewBean);
				// We renumber the remaining pedagogical activities of the selected scenario
				for (int i = positionOfDeletedActivity; i < selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().size(); i++) {
					PedagogicalActivityViewBean pedagogicalActivityViewBeanNeedingANewPosition = selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().get(i);
					PedagogicalActivity pedagogicalActivity = pedagogicalActivityViewBeanNeedingANewPosition.getDomainBean();
					pedagogicalActivity.setPositionInScenario(new Long(i+1));
					pedagogicalActivity = scenariosService.updatePedagogicalActivity(pedagogicalActivity);
					pedagogicalActivityViewBeanNeedingANewPosition.setDomainBean(pedagogicalActivity);
				}
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.info(
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("MY_SCENARIOS.DELETE_PEDAGOGICAL_ACTIVITY.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()));
			}
		}
	}

	public void onToolCategoryTransfer(TransferEvent event) {
		@SuppressWarnings("unchecked")
		List<ToolCategoryViewBean> listOfMovedViewBeans = (List<ToolCategoryViewBean>) event.getItems();
		for (ToolCategoryViewBean movedToolCategoryViewBean : listOfMovedViewBeans) {
			if (event.isAdd()) {
				objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans().add(movedToolCategoryViewBean);
			}
			else {
				objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans().remove(movedToolCategoryViewBean);
			}
		}
	}

	/*
	 * ****************** Create/modify activity dialog ************
	 */
	public PickListBean getPickListBean() {
		return pickListBean;
	}

	public void onSavePedagogicalActivity() {
		if (objectEditedPedagogicalActivityViewBean.getDomainBean()==null) {
			// Create
			PedagogicalActivity newPedagogicalActivity = scenariosService.createPedagogicalActivity(objectEditedPedagogicalActivityViewBean.getName());
			newPedagogicalActivity.setObjective(objectEditedPedagogicalActivityViewBean.getObjective());
			newPedagogicalActivity.setPositionInScenario(new Long(selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().size()+1));
			newPedagogicalActivity.setDuration(objectEditedPedagogicalActivityViewBean.getDuration());
			newPedagogicalActivity.setInstructions(objectEditedPedagogicalActivityViewBean.getInstructions());
			newPedagogicalActivity.getScenarios().add(selectedPedagogicalScenarioViewBean.getDomainBean());
			for (ToolCategoryViewBean toolCategoryViewBean : objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				newPedagogicalActivity.getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(newPedagogicalActivity);
			}
			objectEditedPedagogicalActivityViewBean.setDomainBean(scenariosService.updatePedagogicalActivity(newPedagogicalActivity));
		}
		else {
			// Update
			objectEditedPedagogicalActivityViewBean.getDomainBean().getResourceCategories().clear();
			for (ToolCategoryViewBean toolCategoryViewBean : objectEditedPedagogicalActivityViewBean.getToolCategoryViewBeans()) {
				objectEditedPedagogicalActivityViewBean.getDomainBean().getResourceCategories().add(toolCategoryViewBean.getDomainBean());
				toolCategoryViewBean.getDomainBean().getPedagogicalActivities().add(objectEditedPedagogicalActivityViewBean.getDomainBean());
			}
			objectEditedPedagogicalActivityViewBean.setDomainBean(scenariosService.updatePedagogicalActivity(objectEditedPedagogicalActivityViewBean.getDomainBean()));
		}
		selectedPedagogicalScenarioViewBean.getPedagogicalActivityViewBeans().set(
				objectEditedPedagogicalActivityViewBean.getPositionInScenario().intValue()-1,
				objectEditedPedagogicalActivityViewBean);
		MessageDisplayer.info(
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
				msgs.getMessage("MY_SCENARIOS.SELECTED_PEDAGOGICAL_ACTIVITY.SAVE_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
	}

}
