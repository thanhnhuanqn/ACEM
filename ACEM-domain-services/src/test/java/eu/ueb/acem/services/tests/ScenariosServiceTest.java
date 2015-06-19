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
package eu.ueb.acem.services.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.rouge.Institution;
import eu.ueb.acem.services.OrganisationsService;
import eu.ueb.acem.services.ScenariosService;
import eu.ueb.acem.services.UsersService;

/**
 * @author Grégoire Colbert
 * @since 2014-02-07
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/domain-services-test-context.xml")
public class ScenariosServiceTest extends TestCase {

	@Inject
	private ScenariosService scenariosService;

	@Inject
	private UsersService usersService;

	@Inject
	private OrganisationsService organisationsService;

	public ScenariosServiceTest() {
	}

	/**
	 * GetScenariosWithAuthor
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestGetScenariosWithAuthor() {
		Institution institution = organisationsService.createInstitution("University of Music", "UoM", null);
		
		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");
		teacher.getWorksForOrganisations().add(institution);
		teacher = usersService.updateTeacher(teacher);

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		PedagogicalActivity pedagogicalActivity1 = scenariosService
				.createPedagogicalActivity("Introduction to the western musical notation");
		scenario1.getPedagogicalActivities().add(pedagogicalActivity1);
		pedagogicalActivity1.getScenarios().add(scenario1);
		pedagogicalActivity1 = scenariosService.updatePedagogicalActivity(pedagogicalActivity1);

		PedagogicalActivity pedagogicalActivity2 = scenariosService
				.createPedagogicalActivity("Reading a sequence of D and E");
		scenario1.getPedagogicalActivities().add(pedagogicalActivity2);
		pedagogicalActivity2.getScenarios().add(scenario1);
		pedagogicalActivity2 = scenariosService.updatePedagogicalActivity(pedagogicalActivity2);

		PedagogicalActivity pedagogicalActivity3 = scenariosService
				.createPedagogicalActivity("Reading a sequence of E and F");
		scenario1.getPedagogicalActivities().add(pedagogicalActivity3);
		pedagogicalActivity3.getScenarios().add(scenario1);
		pedagogicalActivity3 = scenariosService.updatePedagogicalActivity(pedagogicalActivity3);

		PedagogicalActivity pedagogicalActivity4 = scenariosService
				.createPedagogicalActivity("Reading a sequence of F and G");
		scenario1.getPedagogicalActivities().add(pedagogicalActivity4);
		pedagogicalActivity4.getScenarios().add(scenario1);
		pedagogicalActivity4 = scenariosService.updatePedagogicalActivity(pedagogicalActivity4);

		scenario1 = scenariosService.updateScenario(scenario1);

		PedagogicalScenario scenario1bis = scenariosService.retrievePedagogicalScenario(scenario1.getId(), false);

		assertEquals(4, scenario1bis.getPedagogicalActivities().size());
	}

	/**
	 * DeleteScenarioWithAuthor
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t02_TestDeleteScenarioWithAuthor() {
		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert", "pass");

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		teacher.getScenarios().add(scenario1);
		scenario1.getAuthors().add(teacher);
		scenario1 = scenariosService.updateScenario(scenario1);
		teacher = usersService.updateTeacher(teacher);

		assertEquals("The scenario should exist because we created it.", new Long(1L), scenariosService.countScenarios());

		scenariosService.dissociateAuthorOrDeleteScenarioIfLastAuthor(scenario1.getId(), teacher.getId());
		
		assertEquals("The scenario shouldn't exist anymore because we deleted it.", new Long(0L), scenariosService.countScenarios());

		assertEquals("The teacher should still exists.", new Long(1L), usersService.countTeachers());
	}

}
