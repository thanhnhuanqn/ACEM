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
package eu.ueb.acem.services;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;

/**
 * @author Grégoire Colbert
 * @since 2015-07-16
 * 
 */
public interface TeachingModesService extends Serializable {

	Long countTeachingModes();

	TeachingMode createTeachingMode(String name, String description);

	TeachingMode retrieveTeachingMode(Long id, boolean initialize);

	TeachingMode updateTeachingMode(TeachingMode teachingMode);

	Boolean deleteTeachingMode(Long id);

}
