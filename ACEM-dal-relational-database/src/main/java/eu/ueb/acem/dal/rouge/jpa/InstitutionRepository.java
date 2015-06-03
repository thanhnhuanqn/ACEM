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
package eu.ueb.acem.dal.rouge.jpa;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.jpa.GenericRepository;
import eu.ueb.acem.domain.beans.rouge.jpa.InstitutionEntity;

/**
 * @author Grégoire Colbert
 * @since 2015-06-03
 * 
 */
public interface InstitutionRepository extends GenericRepository<InstitutionEntity> {

	@Override
	@Query("SELECT count(*) FROM Institution WHERE id = :id")
	Long count(@Param("id") Long id);

	@Override
	@Query("SELECT d FROM Institution d WHERE d.name = :name")
	Iterable<InstitutionEntity> findByName(@Param("name") String name);

	// TODO : à réécrire
	//@Query(value = "MATCH (p:Person)-[:worksForOrganisation]->(o:Organisation)-[*0..2]->(:Organisation)-[:possessesResource|:accessesResource|:supportsResource]->(r:Resource)<-[:supportsResource]-(s:Institution) WHERE id(p)=({personId}) RETURN s")
	@Query("SELECT rc FROM ResourceCategory rc, Person u WHERE rc.id = :categoryId AND u.id = :personId")
	Set<InstitutionEntity> getSupportServicesForPerson(@Param("personId") Long personId);

}
