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

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eu.ueb.acem.domain.beans.bleu.PedagogicalActivity;
import eu.ueb.acem.domain.beans.bleu.PedagogicalAnswer;
import eu.ueb.acem.domain.beans.bleu.PedagogicalNeed;
import eu.ueb.acem.domain.beans.bleu.PedagogicalScenario;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
import eu.ueb.acem.domain.beans.rouge.Community;
import eu.ueb.acem.domain.beans.rouge.Institution;

/**
 * @author Grégoire Colbert
 * @since 2015-06-16
 * 
 */
@Service("initDatabaseService")
public class InitDatabaseServiceImpl implements InitDatabaseService, Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2801713319770032150L;

	@Inject
	private UsersService usersService;

	@Inject
	private NeedsAndAnswersService needsAndAnswersService;

	@Inject
	private ResourcesService resourcesService;

	@Inject
	private OrganisationsService organisationsService;

	@Inject
	private ScenariosService scenariosService;

	@Override
	public void initDatabase() {

		// *************
		// ORGANISATIONS
		// *************
		Community ueb = organisationsService.createCommunity("Université européenne de Bretagne", "UEB", "logo-ueb.png");
		Community minesTelecom = organisationsService.createCommunity("Institut Mines Télécom", "MINES-TELECOM", "logo-institution-mines-telecom.png");

		Institution agrocampusOuest = organisationsService.createInstitution("Agrocampus Ouest", "AGROCAMPUS", "logo-agrocampusouest-transparent.png");
		Institution enscr = organisationsService.createInstitution("École Nationale Supérieure de Chimie de Rennes", "ENSCR", "logo-enscr.png");
		Institution ensRennes = organisationsService.createInstitution("École Normale Supérieure de Rennes", "ENS-RENNES", "logo-ens-rennes.png");
		Institution insaRennes = organisationsService.createInstitution("INSA Rennes", "INSA-RENNES", "logo-insa-rennes.png");
		Institution telecomBretagne = organisationsService.createInstitution("Télécom Bretagne", "TB", "logo-telecom-bretagne.png");
		Institution ubs = organisationsService.createInstitution("Université de Bretagne Sud", "UBS", "logo-ubs.png");
		Institution ubo = organisationsService.createInstitution("Université de Bretagne Occidentale", "UBO", "logo-ubo-transparent.png");
		Institution ur1 = organisationsService.createInstitution("Université de Rennes 1", "UR1", "logo-rennes1.png");
		Institution ur2 = organisationsService.createInstitution("Université Rennes 2", "UR2", "logo-rennes2-transparent.png");

		organisationsService.associateCommunityAndInstitution(ueb.getId(), agrocampusOuest.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), enscr.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), ensRennes.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), insaRennes.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), telecomBretagne.getId());
		organisationsService.associateCommunityAndInstitution(minesTelecom.getId(), telecomBretagne.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), ubs.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), ubo.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), ur1.getId());
		organisationsService.associateCommunityAndInstitution(ueb.getId(), ur2.getId());

		AdministrativeDepartment tb_dsi = organisationsService.createAdministrativeDepartment("DSI Télécom Bretagne", "TB-DSI", null);
		AdministrativeDepartment ur1_suptice = organisationsService.createAdministrativeDepartment("SUPTICE Rennes 1", "UR1-SUPTICE", "logo-rennes1-suptice.png");
		AdministrativeDepartment ur1_dsi = organisationsService.createAdministrativeDepartment("DSI Rennes 1", "UR1-DSI", "logo-rennes1-dsi.png");
		AdministrativeDepartment ur2_crea = organisationsService.createAdministrativeDepartment("Centre de Ressources et d'Études Audiovisuelles de Rennes 2", "UR2-CREA", "logo-rennes2-crea.png");

		organisationsService.associateInstitutionAndAdministrativeDepartment(ur1.getId(), ur1_suptice.getId());
		organisationsService.associateInstitutionAndAdministrativeDepartment(ur1.getId(), ur1_dsi.getId());
		organisationsService.associateInstitutionAndAdministrativeDepartment(ur2.getId(), ur2_crea.getId());

		// *******************
		// RESOURCE CATEGORIES
		// *******************
		ResourceCategory boitiersDeVote = resourcesService.createResourceCategory("Boitiers de vote", "Le boîtier de vote interactif est une solution de vote permettant aux participants d'une réunion ou d'un congrès de répondre à des questions en direct, grâce à des boîtiers de vote individuels.", "categorie-boitiers-de-vote.jpg");
		ResourceCategory videoprojecteurs = resourcesService.createResourceCategory("Vidéoprojecteurs", "Outils généralement mobiles permettant d'afficher du contenu par projection sur un mur.", "categorie-videoprojecteurs.jpg");
		ResourceCategory tableauxBlancsInteractifs = resourcesService.createResourceCategory("Tableaux blancs interactifs", "Le tableau blanc interactif (TBI), tableau numérique interactif (TNI) ou tableau pédagogique interactif (TPI) est un tableau sur lequel vous pouvez afficher le contenu d'un ordinateur et le contrôler directement du tableau à l'aide d'un crayon-souris et pour certains types de tableaux avec les doigts.", "categorie-tableaux-blancs-interactifs.jpg");
		ResourceCategory tablettesGraphiques = resourcesService.createResourceCategory("Tablettes graphiques", "Une tablette graphique est un périphérique informatique pour tracer à la main (graphisme, schéma, dessin, écriture manuscrite).", "categorie-tablettes-graphiques.jpg");
		ResourceCategory tablettesTactiles = resourcesService.createResourceCategory("Tablettes tactiles", "Une tablette tactile, tablette électronique, ardoise électronique, tablette numérique, ou tout simplement tablette, est un ordinateur portable ultraplat qui se présente sous la forme d'un écran tactile sans clavier et qui offre à peu près les mêmes fonctionnalités qu'un ordinateur personnel.", "categorie-tablettes-tactiles.jpg");
		ResourceCategory materielsEnregistrementAudio = resourcesService.createResourceCategory("Matériel d'enregistrement audio", "Microphones et enregistreurs numériques.", "categorie-materiels-enregistrement-audio.jpg");
		ResourceCategory materielsEnregistrementVideo = resourcesService.createResourceCategory("Matériel d'enregistrement vidéo", "Caméras vidéos et formations.", "categorie-materiels-enregistrement-video.jpg");

		ResourceCategory sallesDeCours = resourcesService.createResourceCategory("Salles de cours", "Une salle de cours, ou salle de classe, est une salle où l'on pratique l'enseignement.", "categorie-salles-de-cours.jpg");
		ResourceCategory sallesLaboratoiresDeLangues = resourcesService.createResourceCategory("Laboratoires de langues", "Un laboratoire de langues est une salle de classe comprenant un poste de maître et des postes d'élève équipés de matériel audio.", "categorie-laboratoires-de-langues.jpg");
		ResourceCategory sallesInformatiques = resourcesService.createResourceCategory("Salles informatiques", "Une salle informatique, ou salle multimédia, est une salle de classe comprenant des ordinateurs pour les étudiants.", "categorie-salles-informatiques.jpg");
		ResourceCategory sallesVisioConferences = resourcesService.createResourceCategory("Salles de visio-conférences", "Une salle de visioconférence est une salle spécifiquement dédiée à l'usage de la visioconférence.", "categorie-salles-visioconferences.jpg");
		ResourceCategory sallesVideoprojecteurs = resourcesService.createResourceCategory("Salles avec vidéo-projecteurs", "Une salle équipée d'un vidéo-projecteur fixe.", "categorie-salles-videoprojecteurs.jpg");
		ResourceCategory sallesAmphitheatresSonorises = resourcesService.createResourceCategory("Amphithéâtres sonorisés", "Un amphithéâtre équipé d'une sonorisation (microphone, enceintes).", "categorie-amphitheatres-sonorises.jpg");
		ResourceCategory sallesTeleAmphitheatres = resourcesService.createResourceCategory("Télé-amphithéâtres", "Un télé-amphithéâtre est un amphithéâtre équipé d'un système de visioconférence.", "categorie-teleamphitheatres.jpg");
		ResourceCategory sallesTeleTravauxDiriges = resourcesService.createResourceCategory("Salles de télé-travaux dirigés", "Une salle de télé-travaux dirigés est une salle de travaux dirigés équipée d'un tableau blanc interactif.", "categorie-salles-tele-travaux-diriges.jpg");
		ResourceCategory sallesImmersives = resourcesService.createResourceCategory("Salles immersives", "Une salle immersive est une salle équipée d'un système de visioconférence évolué où les intervenants sont pratiquement à leur taille réelle.", "categorie-salles-immersives.jpg");

		ResourceCategory captationVideo = resourcesService.createResourceCategory("Captation vidéo", "Logiciels permettant de s'enregistrer en vidéo.", null);
		ResourceCategory creationDeSitesWeb = resourcesService.createResourceCategory("Création de sites web", "Logiciels de création de sites web.", null);
		ResourceCategory evaluationCompetences = resourcesService.createResourceCategory("Evaluation des compétences", null, null);
		ResourceCategory outilsAuteursELearning = resourcesService.createResourceCategory("Outils auteurs eLearning", "Un outil auteur vous permet de mettre en place des contenus pédagogiques et de créer des exercices interactifs de formation à distance.", "categorie-outils-auteur.jpg");
		ResourceCategory gestionDocumentaireGED = resourcesService.createResourceCategory("Gestion documentaire (GED)", null, null);
		ResourceCategory reservationSalles = resourcesService.createResourceCategory("Réservation des salles", null, null);
		ResourceCategory distributionFichiers = resourcesService.createResourceCategory("Distribution de fichiers", null, null);
		ResourceCategory portFoliosEtudiants = resourcesService.createResourceCategory("Portfolios étudiants", null, null);
		ResourceCategory plateformesApprentissage = resourcesService.createResourceCategory("Plateformes d'apprentissage", "Une plateforme d'apprentissage est un portail web permettant la mise à disposition de modules de cours et qui offre des fonctions de collaboration et de communication entre les enseignants et les apprenants.", "categorie-plateformes-apprentissage.jpg");
		ResourceCategory hebergementDeVideos = resourcesService.createResourceCategory("Hébergement de vidéos", null, null);
		ResourceCategory classesVirtuelles = resourcesService.createResourceCategory("Classes virtuelles", "Une classe virtuelle est un outil de webconferencing qui permet l'organisation de cours synchrones à distance.", "categorie-classes-virtuelles.jpg");
		ResourceCategory reseauxSociaux = resourcesService.createResourceCategory("Réseaux sociaux", null, null);
		ResourceCategory creationEnquetes = resourcesService.createResourceCategory("Création d'enquêtes", null, null);
		ResourceCategory visioconferencesWebconferences = resourcesService.createResourceCategory("Visioconférences/webconférences", "Un système d'audio et visioconférence vous permet de gérer des réunions à distance.", "categorie-visioconferences.jpg");
		ResourceCategory antiplagiat = resourcesService.createResourceCategory("Anti-plagiat", null, null);
		ResourceCategory geolocalisation = resourcesService.createResourceCategory("Géolocalisation", null, null);
		ResourceCategory reservationVehicules = resourcesService.createResourceCategory("Réservation de véhicules", null, null);
		ResourceCategory ressourcesMultimedia = resourcesService.createResourceCategory("Ressources multimédia", "Les ressources multimédia vous permettent d'enrichir vos cours.", "categorie-ressources-multimedia.png");
		ResourceCategory ressourcesDocumentairesElectroniques = resourcesService.createResourceCategory("Ressources documentaires électroniques", null, null);
		ResourceCategory universitesNumeriquesThematiques = resourcesService.createResourceCategory("Universités numériques thématiques", null, null);
		ResourceCategory webTVs = resourcesService.createResourceCategory("Web TVs", "La Web TV ou webtélé, est la diffusion et la réception par une interface Web de signaux vidéo, ce qui permet aux internautes de regarder du contenu vidéo (c'est-à-dire télévisuel) à partir d'un navigateur Web.", "categorie-web-tv.jpg");

		// *********
		// RESOURCES
		// *********
		Resource chainEdit = resourcesService.createResource(outilsAuteursELearning, ur1, ur1_suptice, resourcesService.getResourceType_RESOURCE_TYPE_SOFTWARE(), "ChainEdit", "ressource-chainedit.png");
		Resource moodle2 = resourcesService.createResource(plateformesApprentissage, ur1, ur1_suptice, resourcesService.getResourceType_RESOURCE_TYPE_SOFTWARE(), "Moodle 2", "ressource-moodle.jpg");
		Resource viaELearning = resourcesService.createResource(classesVirtuelles, ueb, ur1_dsi, resourcesService.getResourceType_RESOURCE_TYPE_SOFTWARE(), "VIA eLearning", "ressource-svi-via.jpg");
		Resource tbiLorient = resourcesService.createResource(tableauxBlancsInteractifs, ubs, ubs, resourcesService.getResourceType_RESOURCE_TYPE_EQUIPMENT(), "Tableau blanc numérique (Maison de la Recherche, paquebot à Lorient)", null);
		Resource lairedu = resourcesService.createResource(ressourcesMultimedia, ur2, ur2_crea, resourcesService.getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE(), "L'Aire d'U", "ressource-lairedu.jpg");
		ueb = organisationsService.retrieveCommunity(ueb.getId(), true);
		lairedu.getOrganisationsHavingAccessToResource().add(ueb);
		ueb.getViewedResources().add(lairedu);
		ueb = organisationsService.updateCommunity(ueb);
		lairedu = resourcesService.updateResource(lairedu);
		Resource wikiRadio = resourcesService.createResource(ressourcesMultimedia, ueb, ueb, resourcesService.getResourceType_RESOURCE_TYPE_PEDAGOGICAL_AND_DOCUMENTARY_RESOURCE(), "WikiRadio", "ressource-wikiradio.png");
		Resource formationVisio = resourcesService.createResource(visioconferencesWebconferences, telecomBretagne, tb_dsi, resourcesService.getResourceType_RESOURCE_TYPE_PROFESSIONAL_TRAINING(), "Formation Visio (Inwicast, équipements)", null);
		Resource sallesImmersives18places = resourcesService.createResource(visioconferencesWebconferences, ueb, ueb, resourcesService.getResourceType_RESOURCE_TYPE_EQUIPMENT(), "Trois salles de téléprésence immersive de 18 places (1 à Lorient, 1 à Brest, 1 à Rennes", "ressource-salle-immersive.jpg");

		// *********
		// SCENARIOS
		// *********
		Teacher teacher = usersService.retrieveTeacherByLogin("admin");
		PedagogicalScenario pedagogicalScenario = scenariosService.createScenario(teacher, "Étude d'une théorie et argumentation", "Développer la capacité à s'approprier une problématique scientifique");
		pedagogicalScenario.setEvaluationModes("<span style=\"font-weight: bold;\">Travaux remis par les étudiants :</span> chaque étudiant aura remis un travail de groupe (synthèse d'un texte et note critique de lecture) et un travail individuel (feed-back à un autre groupe).<br> <span style=\"font-weight: bold;\"><br> Forme du feed-back :</span> le feed-back donné par l'enseignant aux étudiants est formulé à la demande ou de façon informelle via le forum de discussion du cours.<br> <br> <span style=\"font-weight: bold;\">Technologies utilisées :</span> forum de discussion<br> <br> <span style=\"font-weight: bold;\">Critères d'évaluation :</span><br> <ul><li>Pour le travail de groupe : précision et exhaustivité de la synthèse de lecture, clarté et structure de la comparaison des textes, articulation de l'argumentation;</li><li>Pour le travail individuel : clarté et structure du feed-back fourni, pertinence et précision des suggestions d'amélioration.</li></ul>");
		PedagogicalActivity activity1 = scenariosService.createPedagogicalActivity("Appropriation de la problématique");
		activity1.setPositionInScenario(1L);
		activity1.setObjective("À la fin de la séance, les étudiants seront capables de définir et d'expliquer les principales notions relatives à une théorie présentée en classe.");
		activity1.setInstructions("Les étudiants sont invités à poser des questions et à participer à de courtes discussions par petits groupes.");
		activity1.setDuration("Séance de cours de 2 heures en présentiel");
		activity1.getScenarios().add(pedagogicalScenario);
		pedagogicalScenario.getPedagogicalActivities().add(activity1);
		activity1 = scenariosService.updatePedagogicalActivity(activity1);

		PedagogicalActivity activity2 = scenariosService.createPedagogicalActivity("Comparaison d'articles scientifiques");
		activity2.setPositionInScenario(2L);
		activity2.setObjective("À la fin de cette activité, les étudiants seront capables de comparer des articles scientifiques et de participer à une discussion argumentée à propos des résultats de recherche présentés dans ces articles.");
		activity2.setInstructions("Les étudiants forment des groupes de deux et choisissent deux articles parmi une liste qui leur est proposée. Ils rédigent une courte synthèse de chaque article (500 mots) et élaborent une note argumentée (1000 mots) qui compare les deux articles du point de vue des notions théoriques qu'ils utilisent, de leur méthode de recherche respective et de leurs résultats. Ils partagent leur texte avec les autres étudiants dans un forum de discussion sur la plate-forme d'enseignement de l'institution.");
		activity2.setDuration("2 semaines à distance");
		activity2.getResourceCategories().add(plateformesApprentissage);
		plateformesApprentissage.getPedagogicalActivities().add(activity2);
		plateformesApprentissage = resourcesService.updateResourceCategory(plateformesApprentissage);
		activity2.getScenarios().add(pedagogicalScenario);
		pedagogicalScenario.getPedagogicalActivities().add(activity2);
		activity2 = scenariosService.updatePedagogicalActivity(activity2);

		PedagogicalActivity activity3 = scenariosService.createPedagogicalActivity("Capacité à argumenter");
		activity3.setPositionInScenario(3L);
		activity3.setObjective("À la fin de cette étape, les étudiants seront capables d'élaborer un feed-back argumenté et une critique constructive du travail de leurs collègues, et de tenir compte du feed-back qui leur est donné pour améliorer leur propre travail.");
		activity3.setInstructions("Chaque étudiant se voit attribuer la lecture d'une texte d'un autre groupe que le sien. Il lit le texte et rédige un résumé de 250 mots à l'attention des auteurs en précisant les points forts, les points à améliorer et des suggestions concrètes d'amélioration. Il partage son feed-back sur le forum de discussion de la plate-forme d'apprentissage.");
		activity3.setDuration("1 semaine à distance");
		activity3.getResourceCategories().add(plateformesApprentissage);
		plateformesApprentissage.getPedagogicalActivities().add(activity3);
		plateformesApprentissage = resourcesService.updateResourceCategory(plateformesApprentissage);
		activity3.getScenarios().add(pedagogicalScenario);
		pedagogicalScenario.getPedagogicalActivities().add(activity3);
		activity3 = scenariosService.updatePedagogicalActivity(activity3);
		
		pedagogicalScenario = scenariosService.updateScenario(pedagogicalScenario);

		// ******************
		// PEDAGOGICAL ADVICE
		// ******************
		PedagogicalNeed need1 = needsAndAnswersService.createPedagogicalNeed("Préparer un cours");

		PedagogicalNeed need1_1 = needsAndAnswersService.createPedagogicalNeed("Organiser un enseignement en présentiel", need1);
		PedagogicalAnswer answer1_1_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un amphithéâtre", need1_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_1_1.getId(), sallesAmphitheatresSonorises.getId());
		PedagogicalAnswer answer1_1_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une salle de visioconférence", need1_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_1_2.getId(), sallesVisioConferences.getId());
		PedagogicalAnswer answer1_1_3 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un labo de langues", need1_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_1_3.getId(), sallesLaboratoiresDeLangues.getId());
		PedagogicalAnswer answer1_1_4 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une salle de cours", need1_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_1_4.getId(), sallesDeCours.getId());
		PedagogicalAnswer answer1_1_5 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une salle informatique", need1_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_1_5.getId(), sallesInformatiques.getId());

		PedagogicalNeed need1_2 = needsAndAnswersService.createPedagogicalNeed("Organiser un enseignement à distance / hybride", need1);
		PedagogicalAnswer answer1_2_1 = needsAndAnswersService.createPedagogicalAnswer("Créer un espace de cours sur la plateforme d'apprentissage", need1_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_2_1.getId(), plateformesApprentissage.getId());
		PedagogicalAnswer answer1_2_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une salle immersive", need1_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_2_2.getId(), sallesImmersives.getId());
		PedagogicalAnswer answer1_2_3 = needsAndAnswersService.createPedagogicalAnswer("Utiliser la visioconférence", need1_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_2_3.getId(), visioconferencesWebconferences.getId());
		PedagogicalAnswer answer1_2_4 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une classe virtuelle", need1_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_2_4.getId(), classesVirtuelles.getId());

		PedagogicalNeed need1_3 = needsAndAnswersService.createPedagogicalNeed("Construire des supports de cours", need1);
		// answer1_3_1 == "Créer un espace de cours sur la plateforme d'apprentissage"
		need1_3 = needsAndAnswersService.retrievePedagogicalNeed(need1_3.getId(), true);
		need1_3.getAnswers().add(answer1_2_1);
		answer1_2_1.getNeeds().add(need1_3);
		answer1_2_1 = needsAndAnswersService.updatePedagogicalAnswer(answer1_2_1);
		need1_3 = needsAndAnswersService.updatePedagogicalNeed(need1_3);
		// -------

		PedagogicalNeed need1_4 = needsAndAnswersService.createPedagogicalNeed("Préparer un scénario", need1);
		PedagogicalAnswer answer1_4_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un outil d'aide à la scénarisation", need1_4); // TODO : link this answer to a resource category, or delete this answer
		PedagogicalAnswer answer1_4_2 = needsAndAnswersService.createPedagogicalAnswer("Trouver des activités pédagogiques", need1_4); // TODO : link this answer to a resource category, or delete this answer

		PedagogicalNeed need1_5 = needsAndAnswersService.createPedagogicalNeed("Se former et/ou être conseillé à la préparation d'un enseignement", need1);
		PedagogicalAnswer answer1_5_1 = needsAndAnswersService.createPedagogicalAnswer("Contacter le service d'appui pédagogique", need1_5); // TODO : link this answer to a administrative department?!
		PedagogicalAnswer answer1_5_2 = needsAndAnswersService.createPedagogicalAnswer("Consulter l'offre de formations TICE", need1_5); // TODO : link this answer to a resource category, or delete this answer

		PedagogicalNeed need1_6 = needsAndAnswersService.createPedagogicalNeed("Communiquer avec les étudiants", need1);
		PedagogicalAnswer answer1_6_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une liste de diffusion", need1_6); // TODO : link this answer to a resource category, or delete this answer
		PedagogicalAnswer answer1_6_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser le forum d'un espace de cours d'une plateforme d'apprentissage", need1_6);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_6_2.getId(), plateformesApprentissage.getId());

		PedagogicalNeed need1_7 = needsAndAnswersService.createPedagogicalNeed("Trouver des ressources complémentaires", need1);
		PedagogicalNeed need1_7_1 = needsAndAnswersService.createPedagogicalNeed("Trouver des ressources documentaires", need1_7);
		PedagogicalAnswer answer1_7_1_1 = needsAndAnswersService.createPedagogicalAnswer("Consulter le catalogue des ressources électroniques", need1_7_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_1_1.getId(), ressourcesDocumentairesElectroniques.getId());
		PedagogicalNeed need1_7_2 = needsAndAnswersService.createPedagogicalNeed("Trouver des ressources pédagogiques", need1_7);
		PedagogicalAnswer answer1_7_2_1 = needsAndAnswersService.createPedagogicalAnswer("Consulter les ressources de Canal-U", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_1.getId(), ressourcesMultimedia.getId());
		PedagogicalAnswer answer1_7_2_2 = needsAndAnswersService.createPedagogicalAnswer("Consulter les vidéos de l'Aire d'U", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_2.getId(), ressourcesMultimedia.getId());
		PedagogicalAnswer answer1_7_2_3 = needsAndAnswersService.createPedagogicalAnswer("Consulter les ressources ENVAM", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_3.getId(), ressourcesDocumentairesElectroniques.getId());
		PedagogicalAnswer answer1_7_2_4 = needsAndAnswersService.createPedagogicalAnswer("Consulter les ressources des UNT", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_4.getId(), ressourcesDocumentairesElectroniques.getId());
		PedagogicalAnswer answer1_7_2_5 = needsAndAnswersService.createPedagogicalAnswer("Consulter les podcasts de la WikiRadio", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_5.getId(), ressourcesMultimedia.getId());
		PedagogicalAnswer answer1_7_2_6 = needsAndAnswersService.createPedagogicalAnswer("Consulter un moteur de recherche de ressources", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_6.getId(), ressourcesDocumentairesElectroniques.getId());
		PedagogicalAnswer answer1_7_2_7 = needsAndAnswersService.createPedagogicalAnswer("Consulter les ressources de l'UEB C@mpus", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_7.getId(), ressourcesDocumentairesElectroniques.getId());
		PedagogicalAnswer answer1_7_2_8 = needsAndAnswersService.createPedagogicalAnswer("Consulter les podcasts d'iTunes U", need1_7_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer1_7_2_8.getId(), ressourcesMultimedia.getId());

		PedagogicalNeed need2 = needsAndAnswersService.createPedagogicalNeed("Animer un cours");
		PedagogicalNeed need2_1 = needsAndAnswersService.createPedagogicalNeed("Tutorer un cours à distance", need2);
		// answer2_1_1 == "Utiliser une classe virtuelle"
		need2_1 = needsAndAnswersService.retrievePedagogicalNeed(need2_1.getId(), true);
		need2_1.getAnswers().add(answer1_2_4);
		answer1_2_4.getNeeds().add(need2_1);
		answer1_2_4 = needsAndAnswersService.updatePedagogicalAnswer(answer1_2_4);
		need2_1 = needsAndAnswersService.updatePedagogicalNeed(need2_1);
		// -------
		// answer2_1_2 == "Utiliser la visioconférence"
		need2_1 = needsAndAnswersService.retrievePedagogicalNeed(need2_1.getId(), true);
		need2_1.getAnswers().add(answer1_2_3);
		answer1_2_3.getNeeds().add(need2_1);
		answer1_2_3 = needsAndAnswersService.updatePedagogicalAnswer(answer1_2_3);
		need2_1 = needsAndAnswersService.updatePedagogicalNeed(need2_1);
		// -------
		// answer2_1_3 == "Utiliser une salle immersive"
		need2_1 = needsAndAnswersService.retrievePedagogicalNeed(need2_1.getId(), true);
		need2_1.getAnswers().add(answer1_2_2);
		answer1_2_2.getNeeds().add(need2_1);
		answer1_2_2 = needsAndAnswersService.updatePedagogicalAnswer(answer1_2_2);
		need2_1 = needsAndAnswersService.updatePedagogicalNeed(need2_1);
		// -------
		// answer2_1_4 == "Utiliser le forum d'un espace de cours d'une plateforme d'apprentissage
		need2_1 = needsAndAnswersService.retrievePedagogicalNeed(need2_1.getId(), true);
		need2_1.getAnswers().add(answer1_6_2);
		answer1_6_2.getNeeds().add(need2_1);
		answer1_6_2 = needsAndAnswersService.updatePedagogicalAnswer(answer1_6_2);
		need2_1 = needsAndAnswersService.updatePedagogicalNeed(need2_1);
		// -------

		PedagogicalNeed need2_2 = needsAndAnswersService.createPedagogicalNeed("Partager des documents avec les étudiants", need2);
		PedagogicalNeed need2_2_1 = needsAndAnswersService.createPedagogicalNeed("Partager des documents en distanciel", need2_2);
		PedagogicalAnswer answer2_2_1_1 = needsAndAnswersService.createPedagogicalAnswer("Déposer les documents sur l'espace de cours d'une plateforme d'apprentissage", need2_2_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_1_1.getId(), plateformesApprentissage.getId());
		PedagogicalAnswer answer2_2_1_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une solution de GED", need2_2_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_1_2.getId(), gestionDocumentaireGED.getId());
		PedagogicalAnswer answer2_2_1_3 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une fonction d'envoi de fichiers volumineux", need2_2_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_1_3.getId(), distributionFichiers.getId());
		PedagogicalNeed need2_2_2 = needsAndAnswersService.createPedagogicalNeed("Partager des documents en présentiel", need2_2);
		PedagogicalAnswer answer2_2_2_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un TBI", need2_2_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_2_1.getId(), tableauxBlancsInteractifs.getId());
		PedagogicalAnswer answer2_2_2_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un vidéoprojecteur", need2_2_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_2_2.getId(), videoprojecteurs.getId());
		PedagogicalAnswer answer2_2_2_3 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une tablette graphique", need2_2_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_2_3.getId(), tablettesGraphiques.getId());
		PedagogicalAnswer answer2_2_2_4 = needsAndAnswersService.createPedagogicalAnswer("Utiliser une tablette numérique", need2_2_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_2_2_4.getId(), tablettesTactiles.getId());

		PedagogicalNeed need2_3 = needsAndAnswersService.createPedagogicalNeed("Interagir avec les étudiants", need2);
		PedagogicalNeed need2_3_1 = needsAndAnswersService.createPedagogicalNeed("Interagir avec les étudiants en présentiel", need2_3);
		PedagogicalAnswer answer2_3_1_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser des boitiers de vote", need2_3_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_3_1_1.getId(), boitiersDeVote.getId());
		PedagogicalNeed need2_3_2 = needsAndAnswersService.createPedagogicalNeed("Interagir avec les étudiants en distanciel", need2_3);
		// answer2_3_1_2 == "Utiliser une classe virtuelle"
		need2_3_2 = needsAndAnswersService.retrievePedagogicalNeed(need2_3_2.getId(), true);
		need2_3_2.getAnswers().add(answer1_2_4);
		answer1_2_4.getNeeds().add(need2_3_2);
		answer1_2_4 = needsAndAnswersService.updatePedagogicalAnswer(answer1_2_4);
		need2_3_2 = needsAndAnswersService.updatePedagogicalNeed(need2_3_2);
		// -------

		PedagogicalNeed need2_4 = needsAndAnswersService.createPedagogicalNeed("Faire collaborer les étudiants", need2);
		PedagogicalAnswer answer2_4_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un wiki", need2_4);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_4_1.getId(), plateformesApprentissage.getId());
		PedagogicalAnswer answer2_4_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser l'outil Glossaire d'une plateforme d'apprentissage", need2_4);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_4_2.getId(), plateformesApprentissage.getId());
		PedagogicalAnswer answer2_4_3 = needsAndAnswersService.createPedagogicalAnswer("Utiliser l'outil Base de données d'une plateforme d'apprentissage", need2_4);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_4_3.getId(), plateformesApprentissage.getId());
		PedagogicalAnswer answer2_4_4 = needsAndAnswersService.createPedagogicalAnswer("Enregistrer une émission sur la WikiRadio", need2_4);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_4_4.getId(), ressourcesMultimedia.getId());

		PedagogicalNeed need2_5 = needsAndAnswersService.createPedagogicalNeed("Enregistrer un cours en audio", need2);
		PedagogicalAnswer answer2_5_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser du matériel d'enregistrement audio", need2_5);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_5_1.getId(), materielsEnregistrementAudio.getId());

		PedagogicalNeed need2_6 = needsAndAnswersService.createPedagogicalNeed("Mettre à disposition un cours enregistré", need2);
		PedagogicalAnswer answer2_6_1 = needsAndAnswersService.createPedagogicalAnswer("Déposer son cours sur un serveur vidéo", need2_6);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_6_1.getId(), hebergementDeVideos.getId());
		PedagogicalAnswer answer2_6_2 = needsAndAnswersService.createPedagogicalAnswer("Déposer un podcast sur son espace de cours", need2_6);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_6_2.getId(), plateformesApprentissage.getId());

		PedagogicalNeed need2_7 = needsAndAnswersService.createPedagogicalNeed("Filmer un cours", need2);
		PedagogicalNeed need2_7_1 = needsAndAnswersService.createPedagogicalNeed("Filmer un cours de manière autonome", need2_7);
		PedagogicalAnswer answer2_7_1_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser du matériel d'enregistrement vidéo", need2_7_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_7_1_1.getId(), materielsEnregistrementVideo.getId());
		PedagogicalAnswer answer2_7_1_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un outil de podcast", need2_7_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer2_7_1_2.getId(), captationVideo.getId());
		PedagogicalNeed need2_7_2 = needsAndAnswersService.createPedagogicalNeed("Faire appel à un service audiovisuel", need2_7);
		PedagogicalAnswer answer2_7_2_1 = needsAndAnswersService.createPedagogicalAnswer("Contacter le service audiovisuel", need2_7_2);  // TODO : link this answer to a administrative department?!

		PedagogicalNeed need3 = needsAndAnswersService.createPedagogicalNeed("Évaluer un cours");
		PedagogicalNeed need3_1 = needsAndAnswersService.createPedagogicalNeed("Créer un questionnaire / sondage pour évaluer l'enseignement", need3);
		PedagogicalAnswer answer3_1_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un outil de création d'enquêtes", need3_1);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer3_1_1.getId(), creationEnquetes.getId());
		PedagogicalNeed need3_2 = needsAndAnswersService.createPedagogicalNeed("Gérer l'évaluation et la certification des compétences", need3);
		PedagogicalAnswer answer3_2_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un outil de certification", need3_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer3_2_1.getId(), evaluationCompetences.getId());
		PedagogicalAnswer answer3_2_2 = needsAndAnswersService.createPedagogicalAnswer("Utiliser l'outil Référentiel d'une plateforme d'apprentissage", need3_2);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer3_2_2.getId(), plateformesApprentissage.getId());
		PedagogicalNeed need3_3 = needsAndAnswersService.createPedagogicalNeed("Contrôler le plagiat dans les devoirs des étudiants", need3);
		PedagogicalAnswer answer3_3_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser un outil anti-plagiat", need3_3);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer3_3_1.getId(), antiplagiat.getId());
		PedagogicalNeed need3_4 = needsAndAnswersService.createPedagogicalNeed("Mettre en place une évaluation en ligne des étudiants", need3);
		PedagogicalAnswer answer3_4_1 = needsAndAnswersService.createPedagogicalAnswer("Utiliser l'outil Test/Devoir d'une plateforme d'apprentissage", need3_4);
		needsAndAnswersService.associateAnswerWithResourceCategory(answer3_4_1.getId(), plateformesApprentissage.getId());
	}

}
