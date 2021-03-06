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
package eu.ueb.acem.dal.tests.common.gris;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.common.gris.PersonDAO;
import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;

/**
 * @author Grégoire Colbert
 * @since 2013-11-22
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractPersonDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractPersonDAOTest.class);

	@Inject
	private PersonDAO<Long, Teacher> teacherDAO;
	
	public AbstractPersonDAOTest() {

	}

	/**
	 * Create
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestCreateTeacher() {
		// We create our object
		Teacher teacher1 = teacherDAO.create("Pr. John Doe", "jdoe", "pass");

		// We check that "create" is idempotent (multiple calls must not
		// duplicate data)
		teacher1 = teacherDAO.create(teacher1);

		// There must exactly 1 object in the datastore
		assertEquals("There are more than one object in the datastore", new Long(1), teacherDAO.count());
	}

	/**
	 * RetrieveByLogin
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestRetrieveByLogin() {
		// We create our object
		Teacher teacher1 = teacherDAO.create("Grégoire Colbert", "gcolbert", "pass");
		// We create a new object in the datastore
		teacher1.setAdministrator(true);

		// We save our object
		teacher1 = teacherDAO.update(teacher1);

		// We don't need to initialize the associated collections for this test
		Person person1bis = teacherDAO.retrieveByLogin(teacher1.getLogin(), false);

		assertNotNull(person1bis);
		assertEquals(person1bis, teacher1);
		assertEquals(person1bis.getName(), teacher1.getName());
	}

}
