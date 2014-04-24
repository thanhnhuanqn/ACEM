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
package eu.ueb.acem.domain.beans.jaune;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.ActivitePedagogique;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
public interface Ressource extends Serializable {

	Long getId();

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	Set<? extends ResourceCategory> getCategories();

	void setCategories(Set<? extends ResourceCategory> categories);

	void addCategory(ResourceCategory category);

	void removeCategory(ResourceCategory category);

	Set<? extends ModaliteUtilisation> getUseModes();

	void setUseModes(Set<? extends ModaliteUtilisation> useModes);

	Set<? extends ActivitePedagogique> getPedagogicalActivities();

	void setPedagogicalActivities(Set<? extends ActivitePedagogique> pedagogicalActivities);

	/*
	Boolean getFavoriteResource(Enseignant teacher);

	void setFavoriteResource(Enseignant teacher, Boolean favorite);

	Set<? extends Enseignant> getTeachersHavingThisAsFavoriteResource();

	void setTeachersHavingThisAsFavoriteResource(Set<? extends Enseignant> teachers);

	void addTeacherHavingThisAsFavoriteResource(Enseignant teacher);

	void removeTeacherHavingThisAsFavoriteResource(Enseignant teacher);
	*/

	int compareTo(Ressource o);
	
}
