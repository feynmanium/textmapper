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
package net.sf.lapg.test.oldparser;

import java.util.List;

import net.sf.lapg.templates.api.ILocatedEntity;

public class CInputDef implements ILocatedEntity {

	private final CSymbol[] symbols;

	private final String input;
	private final int line;

	public CInputDef(List<CSymbol> symbols, String input, int line) {
		this.symbols = symbols.toArray(new CSymbol[symbols.size()]);
		this.input = input;
		this.line = line;
	}

	public CSymbol[] getSymbols() {
		return symbols;
	}

	public String getLocation() {
		return input + "," + line;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("input");
		sb.append("=[");
		boolean notfirst = false;
		for (CSymbol s : symbols) {
			if (notfirst) {
				sb.append(", ");
			} else {
				notfirst = true;
			}
			sb.append(s.getName());
		}
		sb.append("]");
		return sb.toString();
	}
}
