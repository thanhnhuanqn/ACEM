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

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.dal.rouge.CommunityDAO;
import eu.ueb.acem.dal.rouge.TeachingDepartmentDAO;
import eu.ueb.acem.dal.rouge.InstitutionDAO;
import eu.ueb.acem.dal.rouge.AdministrativeDepartmentDAO;
import eu.ueb.acem.domain.beans.rouge.Communaute;
import eu.ueb.acem.domain.beans.rouge.Composante;
import eu.ueb.acem.domain.beans.rouge.Etablissement;
import eu.ueb.acem.domain.beans.rouge.Organisation;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.domain.beans.rouge.neo4j.CommunauteNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.ComposanteNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.EtablissementNode;
import eu.ueb.acem.domain.beans.rouge.neo4j.ServiceNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@org.springframework.stereotype.Service("organisationsService")
public class OrganisationsServiceImpl implements OrganisationsService {

	private static final Logger logger = LoggerFactory.getLogger(OrganisationsServiceImpl.class);

	@Autowired
	private CommunityDAO communityDAO;

	@Autowired
	private InstitutionDAO institutionDAO;

	@Autowired
	private TeachingDepartmentDAO teachingDepartmentDAO;

	@Autowired
	private AdministrativeDepartmentDAO administrativeDepartmentDAO;

	@Override
	public Long countCommunities() {
		return communityDAO.count();
	}

	@Override
	public Long countInstitutions() {
		return institutionDAO.count();
	}

	@Override
	public Long countAdministrativeDepartments() {
		return administrativeDepartmentDAO.count();
	}

	@Override
	public Long countTeachingDepartments() {
		return teachingDepartmentDAO.count();
	}

	@Override
	public Communaute createCommunity(String name, String shortname) {
		return communityDAO.create(new CommunauteNode(name, shortname));
	}

	@Override
	public Etablissement createInstitution(String name, String shortname) {
		return institutionDAO.create(new EtablissementNode(name, shortname));
	}

	@Override
	public Service createAdministrativeDepartment(String name, String shortname) {
		return administrativeDepartmentDAO.create(new ServiceNode(name, shortname));
	}

	@Override
	public Composante createTeachingDepartment(String name, String shortname) {
		return teachingDepartmentDAO.create(new ComposanteNode(name, shortname));
	}

	@Override
	public Organisation retrieveOrganisation(Long idOrganisation) {
		logger.info("retrieveOrganisation({})", idOrganisation);
		Organisation organisation = null;
		if (communityDAO.exists(idOrganisation)) {
			logger.info("organisation found using communityDAO");
			organisation = communityDAO.retrieveById(idOrganisation);
		}
		else if (institutionDAO.exists(idOrganisation)) {
			logger.info("organisation found using institutionDAO");
			organisation = institutionDAO.retrieveById(idOrganisation);
		}
		else if (administrativeDepartmentDAO.exists(idOrganisation)) {
			logger.info("organisation found using administrativeDepartmentDAO");
			organisation = administrativeDepartmentDAO.retrieveById(idOrganisation);
		}
		else if (teachingDepartmentDAO.exists(idOrganisation)) {
			logger.info("organisation found using teachingDepartmentDAO");
			organisation = teachingDepartmentDAO.retrieveById(idOrganisation);
		}
		return organisation;
	}

	@Override
	public Communaute retrieveCommunity(Long id) {
		return communityDAO.retrieveById(id);
	}

	@Override
	public Etablissement retrieveInstitution(Long id) {
		return institutionDAO.retrieveById(id);
	}

	@Override
	public Service retrieveAdministrativeDepartment(Long id) {
		return administrativeDepartmentDAO.retrieveById(id);
	}

	@Override
	public Composante retrieveTeachingDepartment(Long id) {
		return teachingDepartmentDAO.retrieveById(id);
	}

	@Override
	public Collection<Organisation> retrieveAllOrganisations() {
		Collection<Organisation> organisations = new ArrayList<Organisation>();
		organisations.addAll(communityDAO.retrieveAll());
		organisations.addAll(institutionDAO.retrieveAll());
		organisations.addAll(administrativeDepartmentDAO.retrieveAll());
		organisations.addAll(teachingDepartmentDAO.retrieveAll());
		return organisations;
	}

	@Override
	public Collection<Communaute> retrieveAllCommunities() {
		return communityDAO.retrieveAll();
	}

	@Override
	public Collection<Etablissement> retrieveAllInstitutions() {
		return institutionDAO.retrieveAll();
	}

	@Override
	public Collection<Service> retrieveAllAdministrativeDepartments() {
		return administrativeDepartmentDAO.retrieveAll();
	}

	@Override
	public Collection<Composante> retrieveAllTeachingDepartments() {
		return teachingDepartmentDAO.retrieveAll();
	}

	@Override
	public Organisation updateOrganisation(Organisation organisation) {
		Organisation updatedOrganisation = null;
		if (organisation instanceof Communaute) {
			updatedOrganisation = updateCommunity((Communaute) organisation);
		}
		else if (organisation instanceof Etablissement) {
			updatedOrganisation = updateInstitution((Etablissement) organisation);
		}
		else if (organisation instanceof Service) {
			updatedOrganisation = updateAdministrativeDepartment((Service) organisation);
		}
		else if (organisation instanceof Composante) {
			updatedOrganisation = updateTeachingDepartment((Composante) organisation);
		}
		return updatedOrganisation;
	}

	@Override
	public Communaute updateCommunity(Communaute community) {
		return communityDAO.update(community);
	}

	@Override
	public Etablissement updateInstitution(Etablissement institution) {
		return institutionDAO.update(institution);
	}

	@Override
	public Service updateAdministrativeDepartment(Service administrativeDepartment) {
		return administrativeDepartmentDAO.update(administrativeDepartment);
	}

	@Override
	public Composante updateTeachingDepartment(Composante teachingDepartment) {
		return teachingDepartmentDAO.update(teachingDepartment);
	}

	@Override
	public Boolean deleteOrganisation(Long id) {
		// TODO : rewrite this function using exists() on each DAO
		boolean success = deleteCommunity(id);
		if (!success) {
			success = deleteInstitution(id);
		}
		if (!success) {
			success = deleteAdministrativeDepartment(id);
		}
		if (!success) {
			success = deleteTeachingDepartment(id);
		}
		return success;
	}

	@Override
	public Boolean deleteCommunity(Long id) {
		if (communityDAO.exists(id)) {
			communityDAO.delete(communityDAO.retrieveById(id));
		}
		return (!communityDAO.exists(id));
	}

	@Override
	public Boolean deleteInstitution(Long id) {
		if (institutionDAO.exists(id)) {
			institutionDAO.delete(institutionDAO.retrieveById(id));
		}
		return (!institutionDAO.exists(id));
	}

	@Override
	public Boolean deleteAdministrativeDepartment(Long id) {
		if (administrativeDepartmentDAO.exists(id)) {
			administrativeDepartmentDAO.delete(administrativeDepartmentDAO.retrieveById(id));
		}
		return (!administrativeDepartmentDAO.exists(id));
	}

	@Override
	public Boolean deleteTeachingDepartment(Long id) {
		if (teachingDepartmentDAO.exists(id)) {
			teachingDepartmentDAO.delete(teachingDepartmentDAO.retrieveById(id));
		}
		return (!teachingDepartmentDAO.exists(id));
	}

	@Override
	public void deleteAllCommunities() {
		communityDAO.deleteAll();
	}

	@Override
	public void deleteAllInstitutions() {
		institutionDAO.deleteAll();
	}

	@Override
	public void deleteAllAdministrativeDepartments() {
		administrativeDepartmentDAO.deleteAll();
	}

	@Override
	public void deleteAllTeachingDepartments() {
		teachingDepartmentDAO.deleteAll();
	}

	@Override
	public Boolean associateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		logger.info("in associateCommunityAndInstitution");
		Communaute community = communityDAO.retrieveById(idCommunity);
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		community.addInstitution(institution);
		community = communityDAO.update(community);
		institution = institutionDAO.update(institution);
		return (community.getInstitutions().contains(institution));
	}

	@Override
	public Boolean dissociateCommunityAndInstitution(Long idCommunity, Long idInstitution) {
		logger.info("in dissociateCommunityAndInstitution");
		Communaute community = communityDAO.retrieveById(idCommunity);
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		community.removeInstitution(institution);
		community = communityDAO.update(community);
		institution = institutionDAO.update(institution);
		return (!community.getInstitutions().contains(institution));
	}

	@Override
	public Boolean associateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		logger.info("in associateInstitutionAndAdministrativeDepartment");
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		Service administrativeDepartment = administrativeDepartmentDAO.retrieveById(idAdministrativeDepartment);
		institution.addAdministrativeDepartment(administrativeDepartment);
		institution = institutionDAO.update(institution);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);
		return (institution.getAdministrativeDepartments().contains(administrativeDepartment));
	}

	@Override
	public Boolean dissociateInstitutionAndAdministrativeDepartment(Long idInstitution, Long idAdministrativeDepartment) {
		logger.info("in dissociateInstitutionAndAdministrativeDepartment");
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		Service administrativeDepartment = administrativeDepartmentDAO.retrieveById(idAdministrativeDepartment);
		institution.removeAdministrativeDepartment(administrativeDepartment);
		institution = institutionDAO.update(institution);
		administrativeDepartment = administrativeDepartmentDAO.update(administrativeDepartment);
		return (!institution.getAdministrativeDepartments().contains(administrativeDepartment));
	}

	@Override
	public Boolean associateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		logger.info("in associateInstitutionAndTeachingDepartment");
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		Composante teachingDepartment = teachingDepartmentDAO.retrieveById(idTeachingDepartment);
		institution.addTeachingDepartment(teachingDepartment);
		institution = institutionDAO.update(institution);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);
		return (institution.getTeachingDepartments().contains(teachingDepartment));
	}

	@Override
	public Boolean dissociateInstitutionAndTeachingDepartment(Long idInstitution, Long idTeachingDepartment) {
		logger.info("in dissociateInstitutionAndTeachingDepartment");
		Etablissement institution = institutionDAO.retrieveById(idInstitution);
		Composante teachingDepartment = teachingDepartmentDAO.retrieveById(idTeachingDepartment);
		institution.removeTeachingDepartment(teachingDepartment);
		institution = institutionDAO.update(institution);
		teachingDepartment = teachingDepartmentDAO.update(teachingDepartment);
		return (!institution.getTeachingDepartments().contains(teachingDepartment));
	}

}
