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
package org.textway.lapg.test.oldparser;

import org.textway.lapg.api.SourceElement;
import org.textway.lapg.api.Lexem;

public class CLexem implements Lexem {

	private final int index;
	private final CSymbol sym;
	private final String regexp;
	private final CSourcePart action;
	private final int priority;
	private final int groups;

	private final String input;
	private final int line;

	public CLexem(int index, CSymbol sym, String regexp, CSourcePart action, int priority, int groups, String input, int line) {
		this.index = index;
		this.sym = sym;
		this.regexp = regexp;
		this.action = action;
		this.priority = priority;
		this.groups = groups;
		this.input = input;
		this.line = line;
	}

	public int getIndex() {
		return index;
	}

	public String getLocation() {
		return input + "," + line;
	}

	public CSymbol getSymbol() {
		return sym;
	}

	public String getRegexp() {
		return regexp;
	}

	public SourceElement getAction() {
		return action;
	}

	public int getKind() {
		return KIND_NONE;
	}

	public String getKindAsText() {
		return "none";
	}

	public Lexem getClassLexem() {
		return null;
	}

	public int getPriority() {
		return priority;
	}

	public int getGroups() {
		return groups;
	}

	public int getEndOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getText() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getResourceName() {
		// TODO Auto-generated method stub
		return null;
	}
}
