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
package eu.ueb.acem.dal.bleu.neo4j;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
public interface BesoinRepository extends GraphRepository<BesoinNode>, RelationshipOperationsRepository<BesoinNode> {

	// renvoie les besoins associés au besoin dont le nom est passé en paramètre
	@Query("start besoin=node:Besoin(nom={0}) " +
			"match (besoin)&lt;-[r:aPourBesoinEnfant]-(autreBesoin) " +
			"return autreBesoin")
	Iterable<Besoin> getBesoinsEnfants(String nom);

	// renvoie les besoins associés au besoin dont le nom est passé en paramètre
	@Query("start besoin=node:Besoin(nom={0}) " +
			"match (besoin)&lt;-[r:aPourReponse]-(reponse) " +
			"return reponse")
	Iterable<Reponse> getReponses(String nom);

}
