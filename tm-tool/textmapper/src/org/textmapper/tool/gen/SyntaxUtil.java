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
package org.textmapper.tool.gen;

import org.textmapper.lapg.api.ProcessingStatus;
import org.textmapper.lapg.api.TextSourceElement;
import org.textmapper.tool.compiler.TMGrammar;
import org.textmapper.tool.compiler.TMCompiler;
import org.textmapper.tool.parser.TMTree;
import org.textmapper.tool.parser.TMTree.TMProblem;
import org.textmapper.tool.parser.TMTree.TextSource;
import org.textmapper.tool.parser.ast.AstExpression;
import org.textmapper.tool.parser.ast.AstRoot;
import org.textmapper.templates.types.TypesRegistry;

public class SyntaxUtil {

	public static TMGrammar parseSyntax(TextSource input, ProcessingStatus status, TypesRegistry types) {
		TMTree<AstRoot> tree = TMTree.parseInput(input);
		TMGrammar result = null;
		if (!tree.hasErrors()) {
			result = new TMCompiler(tree, types).resolve();
		}
		if (tree.hasErrors()) {
			result = null;
			for (TMProblem s : tree.getErrors()) {
				status.report(lapgKindToProcessingKind(s.getKind()), s.getMessage(), new SourceElementAdapter(input, s));
			}
		}
		return result;
	}

	public static AstExpression parseExpression(String input, TypesRegistry registry) {
		TMTree<AstExpression> tree = TMTree.parseExpression(new TextSource("", input.toCharArray(), 1));
		if (!tree.hasErrors()) {
			return tree.getRoot();
		}
		return null;
	}

	private static int lapgKindToProcessingKind(int kind) {
		switch (kind) {
			case TMTree.KIND_FATAL:
				return ProcessingStatus.KIND_FATAL;
			case TMTree.KIND_WARN:
				return ProcessingStatus.KIND_WARN;
		}
		return ProcessingStatus.KIND_ERROR;
	}

	private static class SourceElementAdapter implements TextSourceElement {
		private final TMTree.TextSource source;
		private final TMProblem problem;

		public SourceElementAdapter(TMTree.TextSource source, TMProblem problem) {
			this.source = source;
			this.problem = problem;
		}

		@Override
		public int getEndOffset() {
			return problem.getEndOffset();
		}

		@Override
		public int getLine() {
			return source.lineForOffset(problem.getOffset());
		}

		@Override
		public String getText() {
			return source.getText(problem.getOffset(), problem.getEndOffset());
		}

		@Override
		public int getOffset() {
			return problem.getOffset();
		}

		@Override
		public String getResourceName() {
			return source.getFile();
		}
	}
}