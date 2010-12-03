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
package org.textway.lapg.common;

import java.io.File;
import java.io.IOException;

import org.textway.lapg.api.ProcessingStatus;
import org.textway.lapg.api.ProcessingStrategy;
import org.textway.templates.bundle.DefaultTemplateLoader;
import org.textway.templates.bundle.IBundleLoader;
import org.textway.templates.storage.FileBasedResourceLoader;

public class FileBasedStrategy implements ProcessingStrategy {

	public void createFile(String name, String contents, ProcessingStatus status) {
		try {
			// FIXME encoding, newline
			new GeneratedFile(name, contents, "utf8", false).create();
		} catch (IOException e) {
			status.report(ProcessingStatus.KIND_ERROR, "cannot create file `" + name + "': " + e.getMessage());
		}
	}

	public IBundleLoader createTemplateLoader(String path) {
		File folder = new File(path);
		if (folder.isDirectory()) {
			// FIXME charset
			return new DefaultTemplateLoader(new FileBasedResourceLoader(new File[] { folder }, "utf8"));
		}
		return null;
	}
}