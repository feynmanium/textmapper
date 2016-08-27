/**
 * Copyright (c) 2010-2016 Evgeny Gryaznov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.textmapper.idea.facet;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State(name = "Textmapper",
		storages = {
				@Storage(file = StoragePathMacros.PROJECT_FILE),
				@Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/textmapper.xml",
						scheme = StorageScheme.DIRECTORY_BASED)
		})
public class TmProjectSettings extends TmSettings implements PersistentStateComponent<TmConfigurationBean> {
	public static TmSettings getInstance(Project project) {
		return ServiceManager.getService(project, TmProjectSettings.class);
	}

	public TmConfigurationBean getState() {
		return tmConfigurationBean;
	}

	public void loadState(TmConfigurationBean state) {
		XmlSerializerUtil.copyBean(state, tmConfigurationBean);
	}
}
