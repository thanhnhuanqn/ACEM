package eu.ueb.acem.services.tests;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Before
	public void before() {
		organisationsService.deleteAllInstitutions();
		assertEquals(new Long(0), organisationsService.countInstitutions());

		usersService.deleteAllPersons();
		assertEquals(new Long(0), usersService.countPersons());

		usersService.deleteAllTeachers();
		assertEquals(new Long(0), usersService.countTeachers());

		scenariosService.deleteAllScenarios();
		assertEquals(new Long(0), scenariosService.countScenarios());

		scenariosService.deleteAllPedagogicalActivities();
		assertEquals(new Long(0), scenariosService.countPedagogicalActivities());
	}

	@After
	public void after() {
		before();
	}

	/**
	 * GetScenariosWithAuthor
	 */
	@Transactional
	@Test
	public final void t01_TestGetScenariosWithAuthor() {
		Institution institution = organisationsService.createInstitution("University of Music", "UoM", null);
		
		Teacher teacher = usersService.createTeacher("Grégoire COLBERT", "gcolbert");
		teacher.addWorksForOrganisations(institution);
		teacher = usersService.updateTeacher(teacher);

		PedagogicalScenario scenario1 = scenariosService.createScenario(teacher, "Study of the G-clef",
				"Make the learners able to read out loud the notes written in the G-clef");

		PedagogicalActivity pedagogicalActivity1 = scenariosService
				.createPedagogicalActivity("Introduction to the western musical notation");
		scenario1.addPedagogicalActivity(pedagogicalActivity1);

		PedagogicalActivity pedagogicalActivity2 = scenariosService
				.createPedagogicalActivity("Reading a sequence of D and E");
		scenario1.addPedagogicalActivity(pedagogicalActivity2);

		PedagogicalActivity pedagogicalActivity3 = scenariosService
				.createPedagogicalActivity("Reading a sequence of E and F");
		scenario1.addPedagogicalActivity(pedagogicalActivity3);

		PedagogicalActivity pedagogicalActivity4 = scenariosService
				.createPedagogicalActivity("Reading a sequence of F and G");
		scenario1.addPedagogicalActivity(pedagogicalActivity4);

		scenario1 = scenariosService.updateScenario(scenario1);

		PedagogicalScenario scenario1bis = scenariosService.retrieveScenario(scenario1.getId());
		
		assertEquals(4, scenario1bis.getPedagogicalActivities().size());

	}

}
