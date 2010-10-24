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
package net.sf.lapg.templates.ast;

import net.sf.lapg.templates.api.EvaluationContext;
import net.sf.lapg.templates.api.IEvaluationStrategy;
import net.sf.lapg.templates.api.ILocatedEntity;
import net.sf.lapg.templates.ast.AstTree.TextSource;

public abstract class Node implements ILocatedEntity {

	private final TextSource source;
	private final int offset, endoffset;

	protected Node(TextSource source, int offset, int endoffset) {
		this.source = source;
		this.offset = offset;
		this.endoffset = endoffset;
	}

	protected abstract void emit( StringBuffer sb, EvaluationContext context, IEvaluationStrategy env);

	public String getLocation() {
		return source.getLocation(offset);
	}

	public int getLine() {
		return source.lineForOffset(offset);
	}

	public int getOffset() {
		return offset;
	}

	public int getEndOffset() {
		return endoffset;
	}

	public TextSource getInput() {
		return source;
	}

	@Override
	public String toString() {
		return source.getText(offset, endoffset);
	}
}