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
package eu.ueb.acem.dal.rouge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.rouge.neo4j.ServiceRepository;
import eu.ueb.acem.domain.beans.rouge.Service;
import eu.ueb.acem.domain.beans.rouge.neo4j.ServiceNode;

/**
 * @author Grégoire Colbert @since 2014-02-07
 * 
 */
@Repository("administrativeDepartmentDAO")
public class ServiceDAO implements DAO<Long, Service> {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ServiceDAO.class);

	@Autowired
	private ServiceRepository repository;

	public ServiceDAO() {

	}

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public Service create(Service entity) {
		return repository.save((ServiceNode) entity);
	}

	@Override
	public Service retrieveById(Long id) {
		return (id != null) ? repository.findOne(id) : null;
	}

	@Override
	public Service retrieveByName(String name) {
		return repository.findByPropertyValue("name", name);
	}

	@Override
	public Collection<Service> retrieveAll() {
		Iterable<ServiceNode> endResults = repository.findAll();
		Collection<Service> collection = new HashSet<Service>();
		if (endResults.iterator() != null) {
			Iterator<ServiceNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public Service update(Service entity) {
		return repository.save((ServiceNode) entity);
	}

	@Override
	public void delete(Service entity) {
		repository.delete((ServiceNode) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}