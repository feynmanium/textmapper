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
package org.textway.lapg.lalr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.textway.lapg.api.ParserConflict;
import org.textway.lapg.api.Rule;
import org.textway.lapg.api.Symbol;
import org.textway.lapg.api.ParserConflict.Input;
import org.textway.lapg.lalr.LalrConflict.InputImpl;

public class ConflictBuilder {

	public static final int NONE = 0;
	public static final int SHIFT = 1;
	public static final int REDUCE = 2;
	public static final int SYNTAXERR = 3;
	public static final int CONFLICT = 4;

	private final ConflictData[] nextconfl;
	private final List<ConflictData> conflicts;

	public ConflictBuilder(int nterms) {
		nextconfl = new ConflictData[nterms];
		conflicts = new ArrayList<ConflictData>();
	}

	public void clear() {
		conflicts.clear();
		Arrays.fill(nextconfl, null);
	}

	public boolean hasConflict(int termSym) {
		return nextconfl[termSym] != null && nextconfl[termSym].isConflict();
	}

	public void addReduce(int termSym, Symbol sym, int status, Rule rule, Rule originalRule) {
		if(nextconfl[termSym] == null) {
			conflicts.add(nextconfl[termSym] = new ConflictData(sym, originalRule == null));
			if(originalRule != null) {
				nextconfl[termSym].addReduce(ConflictBuilder.CONFLICT, originalRule);
			}
		}
		nextconfl[termSym].addReduce(status, rule);
	}

	public List<LalrConflict> getMergedConflicts(int state, Symbol[] input) {
		if(conflicts.isEmpty()) {
			return Collections.<LalrConflict>emptyList();
		}

		Map<Object, ConflictData> map = new HashMap<Object, ConflictData>();
		for(ConflictData c : conflicts) {
			Object key = c.getRulesAndKindKey();
			ConflictData data = map.get(key);
			if(data == null) {
				map.put(key, c);
			} else {
				data.addLinked(c);
			}
		}
		Input inp = new InputImpl(state, input);
		Collection<ConflictData> values = map.values();

		List<LalrConflict> result = new ArrayList<LalrConflict>(values.size());
		for(ConflictData data : values) {
			result.add(new LalrConflict(data.getKind(), data.getKindAsText(), inp, data.getSymbols(), data.getRules()));
		}
		Collections.sort(result);
		return result;
	}

	public static class ConflictData {

		private final Symbol termSym;
		private final boolean canShift;
		private final List<Rule> rules = new ArrayList<Rule>();
		private int status = NONE;

		private ConflictData linked = null;

		public ConflictData(Symbol termSym, boolean canShift) {
			this.termSym = termSym;
			this.canShift = canShift;
		}

		public Rule[] getRules() {
			return rules.toArray(new Rule[rules.size()]);
		}

		public Symbol[] getSymbols() {
			int len = 0;
			for(ConflictData curr = this; curr != null; curr = curr.linked) {
				len ++;
			}
			Symbol[] result = new Symbol[len];
			len = 0;
			for(ConflictData curr = this; curr != null; curr = curr.linked) {
				result[len++] = curr.termSym;
			}
			Arrays.sort(result, new Comparator<Symbol>() {
				public int compare(Symbol o1, Symbol o2) {
					return o1.getIndex() < o2.getIndex() ? -1 : (o1.getIndex() == o2.getIndex() ? 0 : 1);
				}
			});
			return result;
		}

		public int getKind() {
			if(status == CONFLICT) {
				return canShift ? ParserConflict.SHIFT_REDUCE : ParserConflict.REDUCE_REDUCE;
			}
			return ParserConflict.FIXED;
		}

		public String getKindAsText() {
			switch(status) {
			case SHIFT:
				return "resolved as shift";
			case REDUCE:
				return "resolved as reduce";
			case SYNTAXERR:
				return "resolved as syntax error";
			case CONFLICT:
				return canShift ? "shift/reduce" : "reduce/reduce";
			}
			return "<no conflict>";
		}

		public void addReduce(int newstatus, Rule rule) {
			if(status == NONE) {
				status = newstatus;
			} else if(status != CONFLICT && newstatus != status) {
				status = CONFLICT; // shift + reduce = conflict
			}
			rules.add(rule);
		}

		public void addLinked(ConflictData conflict) {
			conflict.linked = linked;
			linked = conflict;
		}

		public boolean isConflict() {
			return status == CONFLICT;
		}

		public Object getRulesAndKindKey() {
			return new RulesAndKindKey();
		}

		private class RulesAndKindKey {
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + (canShift ? 1231 : 1237);
				result = prime * result + ((rules == null) ? 0 : rules.hashCode());
				result = prime * result + status;
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) {
					return true;
				}
				if (!(obj instanceof RulesAndKindKey)) {
					return false;
				}
				ConflictData other = ((RulesAndKindKey) obj).getConflictData();
				if (canShift != other.canShift || status != other.status) {
					return false;
				}
				if (!rules.equals(other.rules)) {
					return false;
				}
				return true;
			}

			public ConflictData getConflictData() {
				return ConflictData.this;
			}
		}
	}
}