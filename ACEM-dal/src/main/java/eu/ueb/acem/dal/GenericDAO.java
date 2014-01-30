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
package eu.ueb.acem.dal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 * TODO : this class is not used for now, because Spring 3 doesn't allow the autowiring of generics.
 * 	It is solved in Spring 4 :
 * 	 - http://www.jayway.com/2013/11/03/spring-and-autowiring-of-generic-types/
 *   - https://spring.io/blog/2013/12/03/spring-framework-4-0-and-java-generics
 *   but for now, we cannot use Spring 4 because Spring Data Neo4j isn't yet compatible.
 */
//@Repository("genericDAO")
public class GenericDAO<ID extends Serializable, E, N extends E> implements DAO<Long, E> {

	// @Autowired
	protected GenericRepository<N> repository;

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public E create(E entity) {
		return (E) repository.save((N) entity);
	}

	@Override
	public E retrieveById(Long id) {
		return (id != null) ? (E) repository.findOne(id) : null;
	}

	@Override
	public E retrieveByName(String name) {
		return (E) repository.findByPropertyValue("name", name);
	}

	@Override
	public Collection<E> retrieveAll() {
		Iterable<N> endResults = repository.findAll();
		Collection<E> collection = new HashSet<E>();
		if (endResults.iterator() != null) {
			Iterator<N> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public E update(E entity) {
		return (E) repository.save((N) entity);
	}

	@Override
	public void delete(E entity) {
		repository.delete((N) entity);
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
