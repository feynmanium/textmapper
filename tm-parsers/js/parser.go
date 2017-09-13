// generated by Textmapper; DO NOT EDIT

package js

import (
	"fmt"
)

// ErrorHandler is called every time a parser is unable to process some part of the input.
// This handler can return false to abort the parser.
type ErrorHandler func(err SyntaxError) bool

type SyntaxError struct {
	Line      int
	Offset    int
	Endoffset int
}

func (e SyntaxError) Error() string {
	return fmt.Sprintf("syntax error at line %v", e.Line)
}

func (p *Parser) Parse(lexer *Lexer) error {
	return p.parse(5, 5459, lexer)
}

func lookaheadRule(lexer *Lexer, next, rule int32, lhs *stackEntry) {
	switch rule {
	case 3562:
		if lookahead(lexer, next, 0, 5453) {
			lhs.sym.symbol = 655 /* lookahead_StartOfArrowFunction */
		} else {
			lhs.sym.symbol = 156 /* lookahead_notStartOfArrowFunction */
		}
		return
	case 3563:
		if lookahead(lexer, next, 1, 5454) {
			lhs.sym.symbol = 333 /* lookahead_StartOfParametrizedCall */
		} else {
			lhs.sym.symbol = 289 /* lookahead_notStartOfParametrizedCall */
		}
		return
	case 3564:
		if lookahead(lexer, next, 4, 5457) {
			lhs.sym.symbol = 812 /* lookahead_StartOfMappedType */
		} else {
			lhs.sym.symbol = 804 /* lookahead_notStartOfMappedType */
		}
		return
	case 3565:
		if lookahead(lexer, next, 3, 5456) {
			lhs.sym.symbol = 818 /* lookahead_StartOfFunctionType */
		} else {
			lhs.sym.symbol = 797 /* lookahead_notStartOfFunctionType */
		}
		return
	case 3566:
		if lookahead(lexer, next, 2, 5455) {
			lhs.sym.symbol = 711 /* lookahead_StartsOfExtendsTypeRef */
		} else {
			lhs.sym.symbol = 710 /* lookahead_notStartsOfExtendsTypeRef */
		}
		return
	}
}

func AtStartOfArrowFunction(lexer *Lexer, next int32) bool {
	return lookahead(lexer, next, 0, 5453)
}

func AtStartOfParametrizedCall(lexer *Lexer, next int32) bool {
	return lookahead(lexer, next, 1, 5454)
}

func AtStartsOfExtendsTypeRef(lexer *Lexer, next int32) bool {
	return lookahead(lexer, next, 2, 5455)
}

func AtStartOfFunctionType(lexer *Lexer, next int32) bool {
	return lookahead(lexer, next, 3, 5456)
}

func AtStartOfMappedType(lexer *Lexer, next int32) bool {
	return lookahead(lexer, next, 4, 5457)
}

func lookahead(l *Lexer, next int32, start, end int16) bool {
	var lexer Lexer = *l
	var alloc2, alloc3 [8]int
	lexer.Stack = alloc2[:0]
	lexer.Opened = alloc3[:0]

	var allocated [64]stackEntry
	state := start
	stack := append(allocated[:0], stackEntry{state: state})

	for state != end {
		action := tmAction[state]
		if action < -2 {
			// Lookahead is needed.
			if next == noToken {
				next = lookaheadNext(&lexer, end, stack)
			}
			action = lalr(action, next)
		}

		if action >= 0 {
			// Reduce.
			rule := action
			ln := int(tmRuleLen[rule])

			var entry stackEntry
			entry.sym.symbol = tmRuleSymbol[rule]
			stack = stack[:len(stack)-ln]
			lookaheadRule(&lexer, next, rule, &entry)
			state = gotoState(stack[len(stack)-1].state, entry.sym.symbol)
			entry.state = state
			stack = append(stack, entry)

		} else if action == -1 {
			// Shift.
			if next == noToken {
				next = lookaheadNext(&lexer, end, stack)
			}
			state = gotoState(state, next)
			stack = append(stack, stackEntry{
				sym:   symbol{symbol: next},
				state: state,
			})
			if state != -1 && next != eoiToken {
				next = noToken
			}
		}

		if action == -2 || state == -1 {
			break
		}
	}

	return state == end
}

func lalr(action, next int32) int32 {
	a := -action - 3
	for ; tmLalr[a] >= 0; a += 2 {
		if tmLalr[a] == next {
			break
		}
	}
	return tmLalr[a+1]
}

func gotoState(state int16, symbol int32) int16 {
	min := tmGoto[symbol]
	max := tmGoto[symbol+1]

	if max-min < 32 {
		for i := min; i < max; i += 2 {
			if tmFromTo[i] == state {
				return tmFromTo[i+1]
			}
		}
	} else {
		for min < max {
			e := (min + max) >> 1 &^ int32(1)
			i := tmFromTo[e]
			if i == state {
				return tmFromTo[e+1]
			} else if i < state {
				min = e + 2
			} else {
				max = e
			}
		}
	}
	return -1
}

func (p *Parser) applyRule(rule int32, lhs *stackEntry, rhs []stackEntry) {
	switch rule {
	case 2681: // IterationStatement : 'for' '(' 'async' lookahead_notStartOfArrowFunction 'of' AssignmentExpression_In ')' Statement
		p.listener(IdentifierReference, rhs[2].sym.offset, rhs[2].sym.endoffset)
	case 2695: // IterationStatement_Await : 'for' '(' 'async' lookahead_notStartOfArrowFunction 'of' AssignmentExpression_Await_In ')' Statement_Await
		p.listener(IdentifierReference, rhs[2].sym.offset, rhs[2].sym.endoffset)
	case 2709: // IterationStatement_Yield : 'for' '(' 'async' lookahead_notStartOfArrowFunction 'of' AssignmentExpression_In_Yield ')' Statement_Yield
		p.listener(IdentifierReference, rhs[2].sym.offset, rhs[2].sym.endoffset)
	case 3562:
		if AtStartOfArrowFunction(p.lexer, p.next.symbol) {
			lhs.sym.symbol = 655 /* lookahead_StartOfArrowFunction */
		} else {
			lhs.sym.symbol = 156 /* lookahead_notStartOfArrowFunction */
		}
		return
	case 3563:
		if AtStartOfParametrizedCall(p.lexer, p.next.symbol) {
			lhs.sym.symbol = 333 /* lookahead_StartOfParametrizedCall */
		} else {
			lhs.sym.symbol = 289 /* lookahead_notStartOfParametrizedCall */
		}
		return
	case 3564:
		if AtStartOfMappedType(p.lexer, p.next.symbol) {
			lhs.sym.symbol = 812 /* lookahead_StartOfMappedType */
		} else {
			lhs.sym.symbol = 804 /* lookahead_notStartOfMappedType */
		}
		return
	case 3565:
		if AtStartOfFunctionType(p.lexer, p.next.symbol) {
			lhs.sym.symbol = 818 /* lookahead_StartOfFunctionType */
		} else {
			lhs.sym.symbol = 797 /* lookahead_notStartOfFunctionType */
		}
		return
	case 3566:
		if AtStartsOfExtendsTypeRef(p.lexer, p.next.symbol) {
			lhs.sym.symbol = 711 /* lookahead_StartsOfExtendsTypeRef */
		} else {
			lhs.sym.symbol = 710 /* lookahead_notStartsOfExtendsTypeRef */
		}
		return
	}
	nt := ruleNodeType[rule]
	if nt == 0 {
		return
	}
	p.listener(nt, lhs.sym.offset, lhs.sym.endoffset)
}

const errSymbol = 2

func canRecoverOn(symbol int32) bool {
	for _, v := range afterErr {
		if v == symbol {
			return true
		}
	}
	return false
}

// willShift checks if "symbol" is going to be shifted in the given state.
// This function does not support empty productions and returns false if they occur before "symbol".
func (p *Parser) willShift(stackPos int, state int16, symbol int32) bool {
	if state == -1 {
		return false
	}

	for state != p.endState {
		action := tmAction[state]
		if action < -2 {
			action = lalr(action, symbol)
		}

		if action >= 0 {
			// Reduce.
			rule := action
			ln := int(tmRuleLen[rule])
			if ln == 0 {
				// we do not support empty productions
				return false
			}
			stackPos -= ln - 1
			state = gotoState(p.stack[stackPos-1].state, tmRuleSymbol[rule])
		} else {
			return action == -1 && gotoState(state, symbol) >= 0
		}
	}
	return symbol == eoiToken
}

func (p *Parser) reportIgnoredTokens() {
	for _, c := range p.ignoredTokens {
		var t NodeType
		switch Token(c.symbol) {
		case MULTILINECOMMENT:
			t = MultiLineComment
		case SINGLELINECOMMENT:
			t = SingleLineComment
		case INVALID_TOKEN:
			t = InvalidToken
		default:
			continue
		}
		p.listener(t, c.offset, c.endoffset)
	}
	p.ignoredTokens = p.ignoredTokens[:0]
}
