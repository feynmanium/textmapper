// generated by Textmapper; DO NOT EDIT

package test

import (
	"fmt"
)

type NodeType int

type Listener func(t NodeType, offset, endoffset int)

const (
	NoType NodeType = iota
	Test            // (Declaration)+
	Negation
	Block // Negation? (Declaration)*
	Int
	TestClause
	Empty1
	Decl1 // (Identifier)+
	Decl2
	Int7
	Int9
	MultiLineComment
	SingleLineComment
	InvalidToken
	Identifier
	NodeTypeMax
)

var nodeTypeStr = [...]string{
	"NONE",
	"Test",
	"Negation",
	"Block",
	"Int",
	"TestClause",
	"Empty1",
	"Decl1",
	"Decl2",
	"Int7",
	"Int9",
	"MultiLineComment",
	"SingleLineComment",
	"InvalidToken",
	"Identifier",
}

func (t NodeType) String() string {
	if t >= 0 && int(t) < len(nodeTypeStr) {
		return nodeTypeStr[t]
	}
	return fmt.Sprintf("node(%d)", t)
}

var Declaration = []NodeType{
	Block,
	Decl1,
	Decl2,
	Empty1,
	Int,
	TestClause,
}

var TokenSet = []NodeType{
	Identifier,
}

var ruleNodeType = [...]NodeType{
	0,          // Declaration_list : Declaration_list Declaration
	0,          // Declaration_list : Declaration
	Test,       // Test : Declaration_list
	0,          // Declaration : Decl1
	0,          // Declaration : Decl2
	Block,      // Declaration : '{' '-' '-' Declaration_list '}'
	Block,      // Declaration : '{' '-' '-' '}'
	Block,      // Declaration : '{' '-' Declaration_list '}'
	Block,      // Declaration : '{' '-' '}'
	Block,      // Declaration : '{' Declaration_list '}'
	Block,      // Declaration : '{' '}'
	Int,        // Declaration : IntegerConstant '[' ']'
	Int,        // Declaration : IntegerConstant
	TestClause, // Declaration : 'test' '{' setof_not_((eoi | '.') | '}')_optlist '}'
	0,          // Declaration : 'test' '(' empty1 ')'
	0,          // setof_not_((eoi | '.') | '}') : invalid_token
	0,          // setof_not_((eoi | '.') | '}') : WhiteSpace
	0,          // setof_not_((eoi | '.') | '}') : SingleLineComment
	0,          // setof_not_((eoi | '.') | '}') : Identifier
	0,          // setof_not_((eoi | '.') | '}') : IntegerConstant
	0,          // setof_not_((eoi | '.') | '}') : 'test'
	0,          // setof_not_((eoi | '.') | '}') : 'decl1'
	0,          // setof_not_((eoi | '.') | '}') : 'decl2'
	0,          // setof_not_((eoi | '.') | '}') : '{'
	0,          // setof_not_((eoi | '.') | '}') : '('
	0,          // setof_not_((eoi | '.') | '}') : ')'
	0,          // setof_not_((eoi | '.') | '}') : '['
	0,          // setof_not_((eoi | '.') | '}') : ']'
	0,          // setof_not_((eoi | '.') | '}') : ','
	0,          // setof_not_((eoi | '.') | '}') : ':'
	0,          // setof_not_((eoi | '.') | '}') : '-'
	0,          // setof_not_((eoi | '.') | '}') : '->'
	0,          // setof_not_((eoi | '.') | '}') : backtrackingToken
	0,          // setof_not_((eoi | '.') | '}') : error
	0,          // setof_not_((eoi | '.') | '}') : MultiLineComment
	0,          // setof_not_((eoi | '.') | '}')_optlist : setof_not_((eoi | '.') | '}')_optlist setof_not_((eoi | '.') | '}')
	0,          // setof_not_((eoi | '.') | '}')_optlist :
	0,          // empty1 :
	0,          // QualifiedName : Identifier
	0,          // QualifiedName : QualifiedName '.' Identifier
	Decl1,      // Decl1 : 'decl1' '(' QualifiedName ')'
	Decl2,      // Decl2 : 'decl2'
}
