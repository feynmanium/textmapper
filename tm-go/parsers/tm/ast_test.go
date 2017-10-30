package tm_test

import (
	"bytes"
	"fmt"
	"testing"

	"github.com/inspirer/textmapper/tm-parsers/tm"
	"github.com/inspirer/textmapper/tm-parsers/tm/ast"
)

const testInput = `
language abc(go);
lang = "abc"

:: lexer
eoi:        /%%.*(\r?\n)?/
whitespace: /[\n\r\t ]+/                      (space)

qqq = /q1/

'q': /{qqq}/

:: parser

%input a;

a : 'q'+ ;
`

func TestParser(t *testing.T) {
	l := new(tm.Lexer)
	p := new(tm.Parser)
	b := ast.NewBuilder(testInput)

	l.Init(testInput)
	p.Init(b.AddError, b.Add)
	if err := p.ParseInput(l); err != nil {
		t.Errorf("cannot parse %q: %v", testInput, err)
	}
	root, err := b.Root()
	if err != nil {
		t.Errorf("cannot retrieve root: %v", err)
	}
	if root.Type() != tm.Input {
		t.Errorf("b.Root() = %v, want: tm.Input", root.Type())
	}
	input := ast.Input{root}
	var buf bytes.Buffer

	for _, lp := range input.Lexer().LexerPart() {
		switch lp := lp.(type) {
		case *ast.Lexeme:
			fmt.Fprintf(&buf, "token %v\n", lp.Name().Text())
		}
	}
	// TODO check "buf"
	t.Log(string(buf.Bytes()))
}