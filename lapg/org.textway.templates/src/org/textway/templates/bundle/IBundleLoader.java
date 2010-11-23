/**
 * Copyright 2002-2010 Evgeny Gryaznov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.textway.templates.bundle;

import org.textway.templates.api.IProblemCollector;

/**
 * Abstraction of engine which is able to load templates.
 */
public interface IBundleLoader {

	/**
	 * Default templates extension
	 */
	public static final String BUNDLE_EXT = ".ltp";

	/**
	 * @param bundleName
	 *            qualified name of templates bundle (separated with dots)
	 * @return contents of the file with templates
	 */
	TemplatesBundle load(String bundleName, IProblemCollector collector);

	/**
	 * @return contents of the resource
	 */
	String loadResource(String resourceName, String extension);
}