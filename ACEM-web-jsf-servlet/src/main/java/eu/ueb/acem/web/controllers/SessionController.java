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
package eu.ueb.acem.web.controllers;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import eu.ueb.acem.domain.beans.gris.Enseignant;
import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.auth.AuthenticatorService;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;
import eu.ueb.acem.web.viewbeans.gris.TeacherViewBean;

import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.commons.web.controllers.ExceptionController;

/**
 * A bean to memorize the context of the application.
 */
public class SessionController extends AbstractDomainAwareBean {

	/*
	 * ****************** PROPERTIES ********************
	 */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5936434246704000653L;

	/**
	 * The exception controller (called when logging in/out).
	 */
	private ExceptionController exceptionController;

	/**
	 * The authenticator.
	 */
	private AuthenticatorService authenticatorService;

	/**
	 * The CAS logout URL.
	 */
	private String casLogoutUrl;

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

	/**
	 * @see eu.ueb.acem.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.exceptionController, "property exceptionController of class " + this.getClass().getName()
				+ " can not be null");
		Assert.notNull(this.authenticatorService, "property authenticator of class " + this.getClass().getName()
				+ " can not be null");
	}

	/*
	 * ****************** CALLBACK ********************
	 */

	/*
	 * ****************** METHODS ********************
	 */
	
	public PersonViewBean getCurrentUserViewBean() {
		if (currentUserViewBean == null) {
			try {
				Personne user = getCurrentUser();
				if (user instanceof Enseignant) {
					currentUserViewBean = new TeacherViewBean((Enseignant) user);
				}
				else {
					currentUserViewBean = new PersonViewBean(user);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return currentUserViewBean;
	}

	/**
	 * @return the current user, or null if guest.
	 * @throws Exception
	 */
	@Override
	public Personne getCurrentUser() throws Exception {
		return authenticatorService.getUser();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + "#" + hashCode();
	}

	/**
	 * JSF callback.
	 * 
	 * @return a String.
	 * @throws IOException
	 */
	public String logout() throws IOException {
		if (ContextUtils.isPortlet()) {
			throw new UnsupportedOperationException("logout() should not be called in portlet mode.");
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String returnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
		String forwardUrl;
		Assert.hasText(casLogoutUrl, "property casLogoutUrl of class " + getClass().getName() + " is null");
		forwardUrl = String.format(casLogoutUrl, StringUtils.utf8UrlEncode(returnUrl));
		// note: the session beans will be kept even when invalidating
		// the session so they have to be reset (by the exception controller).
		// We invalidate the session however for the other attributes.
		request.getSession().invalidate();
		request.getSession(true);
		// calling this method will reset all the beans of the application
		exceptionController.restart();
		externalContext.redirect(forwardUrl);
		facesContext.responseComplete();
		return null;
	}

	/*
	 * ****************** ACCESSORS ********************
	 */

	/**
	 * @param exceptionController
	 *            the exceptionController to set
	 */
	public void setExceptionController(final ExceptionController exceptionController) {
		this.exceptionController = exceptionController;
	}

	/**
	 * @param authenticatorService
	 *            the authenticator to set
	 */
	public void setAuthenticatorService(final AuthenticatorService authenticatorService) {
		this.authenticatorService = authenticatorService;
	}

	/**
	 * @return the casLogoutUrl
	 */
	protected String getCasLogoutUrl() {
		return casLogoutUrl;
	}

	/**
	 * @param casLogoutUrl
	 *            the casLogoutUrl to set
	 */
	public void setCasLogoutUrl(final String casLogoutUrl) {
		this.casLogoutUrl = StringUtils.nullIfEmpty(casLogoutUrl);
	}

}
