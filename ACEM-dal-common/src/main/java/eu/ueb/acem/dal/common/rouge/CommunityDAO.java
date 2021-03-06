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
package eu.ueb.acem.dal.common.rouge;

import eu.ueb.acem.domain.beans.rouge.Community;

/**
 * This interface describes the methods expected from DAO instances that take
 * care of beans that implement the Community interface.
 * 
 * @param <ID>
 *            The type of the id property of objects stored in the database
 *            (typically java.lang.Long)
 * 
 * @author Grégoire Colbert
 * @since 2015-11-19
 */
public interface CommunityDAO<ID> extends OrganisationDAO<ID, Community> {

	/**
	 * Returns the Community instance that have a supannEtablissement property
	 * with the given value. There ought to be only one instance at most,
	 * because "supannEtablissement" is an external unique identifier.
	 * 
	 * @param supannEtablissement
	 *            A "supannEtablissement" value to match
	 * @return the Community with the given "supannEtablissement" value, or null
	 */
	Community retrieveBySupannEtablissement(String supannEtablissement);

}
