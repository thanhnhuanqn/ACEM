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
package eu.ueb.acem.dal.bleu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.dal.bleu.neo4j.EtapeRepository;
import eu.ueb.acem.domain.beans.bleu.Etape;
import eu.ueb.acem.domain.beans.bleu.neo4j.EtapeNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
@Repository("etapeDAO")
public class EtapeDAO implements DAO<Etape>{

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(EtapeDAO.class);

	@Autowired
	private EtapeRepository repository;
	
	public EtapeDAO() {
		
	}
	
	@Override
	public void create(Etape etape) {
		repository.save((EtapeNode) etape);
	}

	@Override
	public Etape retrieve(String nom) {
		return repository.findByPropertyValue("nom", nom);
	}

	@Override
	public Set<Etape> retrieveAll() {
		Iterable<EtapeNode> endResults = repository.findAll();
		Set<Etape> set = new HashSet<Etape>();
		if (endResults.iterator() != null) {
			Iterator<EtapeNode> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				set.add(iterator.next());
			}
		}
		return set;
	}
	
	@Override
	public Etape update(Etape etape) {
		return repository.save((EtapeNode) etape);
	}
	
	@Override
	public void delete(Etape etape) {
		repository.delete((EtapeNode) etape);
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
