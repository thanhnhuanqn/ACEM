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
package eu.ueb.acem.domain.beans.jaune;

import java.io.Serializable;
import java.util.Set;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalSession;

/**
 * The ResourceCategory interface is used to group implementations of the
 * {@link Resource} interface. Resource categories are more stable entities than
 * concrete resources (categories are less likely to be deleted than plain
 * resources), and that's why this interface is so full of collections.
 * 
 * @author Grégoire Colbert
 * @since 2014-04-09
 */
public interface ResourceCategory extends Serializable, Comparable<ResourceCategory> {

	Long getId();

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

	Set<Resource> getResources();

	void setResources(Set<Resource> resources);

	Set<PedagogicalAnswer> getAnswers();

	void setAnswers(Set<PedagogicalAnswer> answers);

	String getIconFileName();

	void setIconFileName(String iconFileName);

	Set<PedagogicalSession> getPedagogicalSessions();

	void setPedagogicalSessions(Set<PedagogicalSession> pedagogicalSessions);

	Set<PedagogicalActivity> getPedagogicalActivities();

	void setPedagogicalActivities(Set<PedagogicalActivity> pedagogicalActivities);

}
