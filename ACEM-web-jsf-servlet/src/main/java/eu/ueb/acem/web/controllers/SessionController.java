/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.controllers;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Person;
import eu.ueb.acem.domain.beans.gris.Teacher;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;

/**
 * A bean to memorize the context of the application.
 */
@Controller("sessionController")
@Scope("session")
public class SessionController extends AbstractDomainAwareBean {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -5936434246704000653L;
	
	/*
	 * ****************** PROPERTIES ********************
	 */
	private Authentication auth;

	private PersonViewBean currentUserViewBean;
	
	/*
	 * ****************** INIT ********************
	 */

	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	/*
	 * ****************** CALLBACK ********************
	 */

	/*
	 * ****************** METHODS ********************
	 */
	
	public PersonViewBean getCurrentUserViewBean() {
		if (currentUserViewBean == null) {
			Person user = getCurrentUser();
			if (user instanceof Teacher) {
				currentUserViewBean = new TeacherViewBean((Teacher) user);
			}
			else {
				currentUserViewBean = new PersonViewBean(user);
			}
		}
		return currentUserViewBean;
	}

	/**
	 * @return the current user, or null if guest.
	 * @throws Exception
	 */
	@Override
	public Person getCurrentUser() {
		if (this.auth == null) {
			this.auth = SecurityContextHolder.getContext().getAuthentication();
		}
		return getDomainService().getUser(auth.getName());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/*
	 * ****************** ACCESSORS ********************
	 */


}
