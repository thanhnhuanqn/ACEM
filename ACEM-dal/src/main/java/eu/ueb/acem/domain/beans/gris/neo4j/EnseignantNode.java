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
package eu.ueb.acem.domain.beans.gris.neo4j;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import eu.ueb.acem.domain.beans.bleu.Scenario;
import eu.ueb.acem.domain.beans.bleu.neo4j.ScenarioNode;
import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.domain.beans.jaune.neo4j.RessourceNode;
import eu.ueb.acem.domain.beans.violet.SeanceDeCours;
import eu.ueb.acem.domain.beans.violet.neo4j.SeanceDeCoursNode;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@NodeEntity
@TypeAlias("Teacher")
public class EnseignantNode extends PersonneNode implements Enseignant {

	private static final long serialVersionUID = -3193454107919543890L;

	@RelatedTo(elementClass = RessourceNode.class, type = "hasFavorite", direction = OUTGOING)
	@Fetch
	private Set<RessourceNode> favoriteResources;

	@RelatedTo(elementClass = SeanceDeCoursNode.class, type = "leadsClass", direction = OUTGOING)
	@Fetch
	private Set<SeanceDeCoursNode> seancesDeCours;

	@RelatedTo(elementClass = ScenarioNode.class, type = "authorsScenario", direction = OUTGOING)
	@Fetch
	private Set<ScenarioNode> scenarios;

	public EnseignantNode() {
	}

	public EnseignantNode(String name, String login) {
		super(name, login);
	}

	@Override
	public Set<? extends Ressource> getFavoriteResources() {
		return favoriteResources;
	}

	@Override
	public Set<? extends SeanceDeCours> getTeachingClasses() {
		return seancesDeCours;
	}

	@Override
	public Set<? extends Scenario> getScenarios() {
		return scenarios;
	}

	@Override
	public void addAuthor(Scenario scenario) {
		scenarios.add((ScenarioNode)scenario);
	}

	@Override
	public void removeAuthor(Scenario scenario) {
		scenarios.remove(scenario);
	}

}
