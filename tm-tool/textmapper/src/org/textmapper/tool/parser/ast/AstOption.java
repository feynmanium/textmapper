/**
 * Copyright 2002-2013 Evgeny Gryaznov
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
package org.textmapper.tool.parser.ast;

import org.textmapper.tool.parser.LapgTree.TextSource;

public class AstOption extends AstNode implements AstOptionPart {

	private final String key;
	private final AstExpression value;

	public AstOption(String key, AstExpression value, TextSource source, int offset, int endoffset) {
		super(source, offset, endoffset);
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public AstExpression getValue() {
		return value;
	}

	public void accept(AbstractVisitor v) {
		if (!v.visit(this)) {
			return;
		}
		if (value != null) {
			value.accept(v);
		}
	}
}
