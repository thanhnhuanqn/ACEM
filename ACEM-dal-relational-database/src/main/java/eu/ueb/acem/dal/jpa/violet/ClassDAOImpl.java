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
package eu.ueb.acem.dal.jpa.violet;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.common.violet.ClassDAO;
import eu.ueb.acem.dal.jpa.AbstractDAO;
import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.jpa.violet.ClassEntity;
import eu.ueb.acem.domain.beans.violet.Class;

/**
 * The Spring Data JPA implementation of ClassDAO.
 * 
 * @author Grégoire Colbert
 * @since 2015-06-04
 * 
 */
@Repository("classDAO")
public class ClassDAOImpl extends AbstractDAO<Class, ClassEntity> implements ClassDAO<Long> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 2767789852508953247L;

	@Inject
	private ClassRepository repository;

	@Override
	protected final GenericRepository<ClassEntity> getRepository() {
		return repository;
	}

	@Override
	protected final void initializeCollections(Class entity) {
		if (entity != null) {
			entity.getCourse();
			entity.getPedagogicalScenario();
			entity.getLocation();
		}
	}

	@Override
	public Class create(String name) {
		return super.create(new ClassEntity(name));
	}

}
