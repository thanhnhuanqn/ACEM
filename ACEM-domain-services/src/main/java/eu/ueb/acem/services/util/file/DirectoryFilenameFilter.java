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
package eu.ueb.acem.services.util.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * File Filter for Directory.
 * @author rlorthio
 *
 */
public class DirectoryFilenameFilter implements FilenameFilter {

    /**
	 * Constructor.
	 */
	public DirectoryFilenameFilter() {
		super();
	}
	
	@Override
	public boolean accept(final File dir, final String name) {
        boolean retour = false;
        if (dir.isDirectory()) {
        	retour = true;
        }
		return retour;
	}
}
