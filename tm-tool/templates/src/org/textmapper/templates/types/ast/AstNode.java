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
package org.textmapper.templates.types.ast;

import org.textmapper.templates.types.TypesTree.TextSource;

public abstract class AstNode implements IAstNode {
	
	protected TextSource fInput;
	protected int fStart;
	protected int fEnd;

	public AstNode(TextSource input, int start, int end) {
		this.fStart = start;
		this.fEnd = end;
		this.fInput = input;
	}

	public int getOffset() {
		return fStart;
	}

	public int getEndOffset() {
		return fEnd;
	}

	public TextSource getInput() {
		return fInput;
	}

	public String toString() {
		return fInput == null ? "" : fInput.getText(fStart, fEnd);
	}

	//public abstract void accept(Visitor v);
}

