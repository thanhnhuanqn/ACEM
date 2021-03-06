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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;

import javax.inject.Named;

import eu.ueb.acem.domain.beans.jaune.Software;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
@Named
public class SoftwareViewBean extends AbstractResourceViewBean<Software> implements Serializable {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -4282575138784718297L;

	public SoftwareViewBean() {
		super();
		setType("RESOURCE_TYPE_SOFTWARE");
	}

	public SoftwareViewBean(Software software) {
		this();
		setDomainBean(software);
	}

}
