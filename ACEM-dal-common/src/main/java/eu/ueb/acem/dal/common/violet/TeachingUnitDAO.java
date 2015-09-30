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
package eu.ueb.acem.dal.common.violet;

import eu.ueb.acem.dal.common.DAO;
import eu.ueb.acem.domain.beans.violet.TeachingUnit;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of beans that implement the TeachingUnit interface.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2015-07-15
 */
public interface TeachingUnitDAO<ID, E extends TeachingUnit> extends DAO<ID, E> {

	/**
	 * Creates a new subclass E of TeachingUnit, with the given name.
	 * 
	 * @param name
	 *            A name for the new implementation of TeachingUnit
	 * @return the newly created TeachingUnit of class E
	 */
	E create(String name);

}
