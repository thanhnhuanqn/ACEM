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
package eu.ueb.acem.dal.neo4j.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.rouge.InstitutionDAO;
import eu.ueb.acem.dal.neo4j.AbstractDAO;
import eu.ueb.acem.dal.neo4j.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.neo4j.rouge.InstitutionNode;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * The Spring Data Neo4j implementation of OrganisationDAO for Institution
 * domain beans.
 * 
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@Repository("institutionDAO")
public class InstitutionDAOImpl extends AbstractDAO<Institution, InstitutionNode> implements
		InstitutionDAO<Long> {

	/**
	 * FOr serialization.
	 */
	private static final long serialVersionUID = -1248475351876837707L;

	@Inject
	private InstitutionRepository repository;

	@Override
	protected final GenericRepository<InstitutionNode> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Institution entity) {
		if (entity != null) {
			neo4jOperations.fetch(entity.getPossessedResources());
			neo4jOperations.fetch(entity.getViewedResources());
			neo4jOperations.fetch(entity.getUseModes());
			neo4jOperations.fetch(entity.getCommunities());
			neo4jOperations.fetch(entity.getAdministrativeDepartments());
			neo4jOperations.fetch(entity.getTeachingDepartments());
		}
	}

	@Override
	public Collection<Institution> retrieveSupportServicesForPerson(Person person) {
		Iterable<InstitutionNode> endResults = repository.getSupportServicesForPerson(person.getId());
		Collection<Institution> collection = new HashSet<Institution>();
		if (endResults.iterator() != null) {
			Iterator<InstitutionNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Institution create(String name, String shortname, String iconFileName) {
		return super.create(new InstitutionNode(name, shortname, iconFileName));
	}

	@Override
	public Institution retrieveBySupannEtablissement(String supannEtablissement) {
		return repository.findBySupannEtablissement(supannEtablissement);
	}

}
