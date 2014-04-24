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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.primefaces.event.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.viewbeans.PickListBean;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.jaune.ResourceViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

@Controller("usersController")
@Scope("view")
public class UsersController extends AbstractContextAwareController {

	private static final long serialVersionUID = -977386846045010683L;

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private List<PersonViewBean> personViewBeans;

	private PersonViewBean selectedUserViewBean;

	@Autowired
	private PickListBean pickListBean;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	public OrganisationsController organisationsController;
	
	public UsersController() {
		personViewBeans = new ArrayList<PersonViewBean>();
	}

	@PostConstruct
	public void initUsersController() {
		logger.debug("initUsersController");

		personViewBeans.clear();
		Set<Personne> persons = usersService.getPersons();
		for (Personne person : persons) {
			PersonViewBean personViewBean = new PersonViewBean(person);
			personViewBeans.add(personViewBean);
			for (Organisation organisation : person.getWorksForOrganisations()) {
				personViewBean.addOrganisationViewBean(organisationsController.getOrganisationViewBeanFromId(organisation.getId()));
			}
		}
	}
	
	public PickListBean getPickListBean() {
		return pickListBean;
	}
	
	public List<PersonViewBean> getPersonViewBeans() {
		return personViewBeans;
	}

	public void setPersonViewBeans(List<PersonViewBean> personViewBeans) {
		this.personViewBeans = personViewBeans;
	}

	public PersonViewBean getSelectedUserViewBean() {
		return selectedUserViewBean;
	}

	public void setSelectedUserViewBean(PersonViewBean selectedUserViewBean) {
		this.selectedUserViewBean = selectedUserViewBean;
	}

	public void preparePicklistOrganisationViewBeans() {
		logger.info("preparePicklistOrganisationViewBeans");
		if (getSelectedUserViewBean() != null) {
			pickListBean.getPickListEntities().getSource().clear();
			pickListBean.getPickListEntities().getSource().addAll(organisationsController.getOrganisationViewBeans().values());
			pickListBean.getPickListEntities().getTarget().clear();
			for (OrganisationViewBean organisationViewBean : organisationsController.getOrganisationViewBeans().values()) {
				logger.info("organisationViewBean={}",organisationViewBean.getDomainBean().getName());
				if (getSelectedUserViewBean().getDomainBean().getWorksForOrganisations().contains(organisationViewBean.getDomainBean())) {
					pickListBean.getPickListEntities().getSource().remove(organisationViewBean);
					pickListBean.getPickListEntities().getTarget().add(organisationViewBean);
				}
			}
		}
	}

	public void setAdministrator(PersonViewBean personViewBean) {
		logger.debug("setAdministrator({})", personViewBean.getAdministrator());
		personViewBean.getDomainBean().setAdministrator(personViewBean.getAdministrator());
		personViewBean.setDomainBean(usersService.updatePerson(personViewBean.getDomainBean()));
	}

	public void toggleFavoriteResourceForCurrentUser(ResourceViewBean resourceViewBean) {
		logger.info("toggleFavoriteResourceForCurrentUser, resource name = {}", resourceViewBean.getName());
		
		try {
			if (getCurrentUser() instanceof Enseignant) {
				Enseignant teacher = (Enseignant)getCurrentUser();
				if (usersService.toggleFavoriteResourceForTeacher(teacher.getId(), resourceViewBean.getDomainBean().getId())) {
					if (teacher.getFavoriteResources().contains(resourceViewBean.getDomainBean())) {
						teacher.removeFavoriteResource(resourceViewBean.getDomainBean());
					}
					else {
						teacher.addFavoriteResource(resourceViewBean.getDomainBean());
					}
					resourceViewBean.setFavoriteResource(teacher.getFavoriteResources().contains(resourceViewBean.getDomainBean()));
				}
				else {
					logger.error("Couldn't toggle {} as a favorite resource of teacher {}", resourceViewBean.getName(), teacher.getLogin());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onTransfer(TransferEvent event) {
		logger.info("onTransfer");
		@SuppressWarnings("unchecked")
		List<OrganisationViewBean> listOfMovedViewBeans = (List<OrganisationViewBean>) event.getItems();
		try {
			for (OrganisationViewBean movedOrganisationViewBean : listOfMovedViewBeans) {
				if (event.isAdd()) {
					logger.info("We should associate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					if (usersService.associateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId())) {
						selectedUserViewBean.getDomainBean().addWorksForOrganisations(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.addOrganisationViewBean(movedOrganisationViewBean);
						logger.info("association successful");
					}
					else {
						logger.info("association failed");
					}
				}
				else {
					logger.debug("We should dissociate {} and {}", movedOrganisationViewBean.getName(), getSelectedUserViewBean()
							.getName());
					if (usersService.dissociateUserWorkingForOrganisation(getSelectedUserViewBean().getId(),
							movedOrganisationViewBean.getId())) {
						selectedUserViewBean.getDomainBean().removeWorksForOrganisations(movedOrganisationViewBean.getDomainBean());
						selectedUserViewBean.removeOrganisationViewBean(movedOrganisationViewBean);
						logger.info("dissociation successful");
					}
					else {
						logger.info("dissociation failed");
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("onTransfer exception = ", e);
		}
	}
	
	/*-
	 * TODO : implement this method so that the worksForOrganisation relationships are not redundant with
	 * the associations of the various organisations. 
	 * Example : if the user works for UNIV, and UNIV is associated with COMMUNITY, then when we say
	 * the user works for UNIV, we should disable COMMUNITY (because he implicitly can access resources of
	 * COMMUNITY through the UNIV->COMMUNITY association). And so there is no need to state that he works
	 * for COMMUNITY too.
	 */
	public Boolean isDisabledInPickList(OrganisationViewBean organisationViewBean) {
		return false;
		/*
		if (organisationViewBean instanceof CommunityViewBean) {
			if (currentUserViewBean.getOrganisationViewBeans().contains(organisationViewBean)) {
				return false;
			}
			else {
				
			}
		}
		*/
	}
	
	public void onDeleteOrganisation() {
		logger.info("onDeleteOrganisation, organisation={}",organisationsController.getCurrentOrganisationViewBean().getDomainBean().getName());
		for (PersonViewBean personViewBean : personViewBeans) {
			personViewBean.removeOrganisationViewBean(organisationsController.getCurrentOrganisationViewBean());
			personViewBean.getDomainBean().removeWorksForOrganisations(organisationsController.getCurrentOrganisationViewBean().getDomainBean());
		}
	}
	
}
