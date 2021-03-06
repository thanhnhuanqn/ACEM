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
package eu.ueb.acem.dal.jpa.jaune;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.jaune.ResourceDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jpa.jaune.EquipmentEntity;
import eu.ueb.acem.domain.beans.jpa.jaune.ResourceCategoryEntity;

/**
 * The Spring Data JPA implementation of ResourceDAO for Equipment domain beans.
 * 
 * @author Grégoire Colbert
 * @since 2015-05-29
 * 
 */
@Repository("equipmentDAO")
public class EquipmentDAO extends AbstractDAO<Equipment, EquipmentEntity> implements ResourceDAO<Long, Equipment> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 1107480638028610619L;

	@Inject
	private EquipmentRepository repository;

	@Override
	protected final GenericRepository<EquipmentEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Equipment entity) {
		if (entity != null) {
			entity.getCategories().size();
			entity.getOrganisationPossessingResource();
			entity.getOrganisationSupportingResource();
			entity.getOrganisationsHavingAccessToResource().size();
			entity.getUseModes().size();
			entity.getStorageLocations().size();
			entity.getDocumentations().size();
		}
	}

	/**
	 * Returns the categories containing at least one "Equipment" entity.
	 */
	@Override
	public Collection<ResourceCategory> retrieveCategories() {
		Iterable<ResourceCategoryEntity> endResults = repository.getCategories();
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	/**
	 * Returns the categories containing at least one "Equipment" entity that
	 * the given person can see.
	 */
	@Override
	public Collection<ResourceCategory> retrieveCategoriesForPerson(Person person) {
		Iterable<ResourceCategoryEntity> endResults = repository.getCategoriesForPerson(person.getId());
		Collection<ResourceCategory> collection = new HashSet<ResourceCategory>();
		if (endResults.iterator() != null) {
			Iterator<ResourceCategoryEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Collection<Equipment> retrieveAllWithCategory(ResourceCategory category) {
		Iterable<EquipmentEntity> endResults = repository.getEntitiesWithCategory(category.getId());
		Collection<Equipment> collection = new HashSet<Equipment>();
		if (endResults.iterator() != null) {
			Iterator<EquipmentEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Equipment entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Collection<Equipment> retrieveResourcesInCategoryForPerson(ResourceCategory category, Person person) {
		Iterable<EquipmentEntity> endResults = repository.getResourcesInCategoryForPerson(category.getId(),
				person.getId());
		Collection<Equipment> collection = new HashSet<Equipment>();
		if (endResults.iterator() != null) {
			Iterator<EquipmentEntity> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				Equipment entity = iterator.next();
				initializeCollections(entity);
				collection.add(entity);
			}
		}
		return collection;
	}

	@Override
	public Equipment create(String name, String iconFileName) {
		return super.create(new EquipmentEntity(name, iconFileName));
	}

}
