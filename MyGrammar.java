import computation.contextfreegrammar.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class MyGrammar {
	public static ContextFreeGrammar makeGrammar() {
			// Context-free grammar 
    Variable S0 = new Variable("S0");
    Variable E = new Variable('E');
    Variable T = new Variable('T');
    Variable F = new Variable('F');
    Variable P = new Variable('P');
    Variable A = new Variable('A');
    Variable O = new Variable('O');
    Variable C = new Variable('C');
    Variable M = new Variable('M');
    Variable V = new Variable('V');
    Variable U = new Variable('U');
    Variable Z = new Variable('Z');
    
    // startVariable S0 = new startVariable("S0");
    // HashSet<startVariable> variables = new HashSet<>();
    // variables.add(S0);
    
    HashSet<Variable> variables = new HashSet<>();
    variables.add(S0);
    variables.add(E);
    variables.add(T);
    variables.add(F);
    variables.add(P);
    variables.add(A);
    variables.add(O);
    variables.add(C);
    variables.add(M);
    variables.add(V);
    variables.add(U);
    variables.add(Z);
    

    Terminal r = new Terminal('r');
    Terminal p = new Terminal('p');
    Terminal plus = new Terminal('+'); 
    Terminal and = new Terminal('&');
    Terminal open = new Terminal('(');
    Terminal close = new Terminal(')');
    Terminal minus = new Terminal('-');
 

    HashSet<Terminal> terminals = new HashSet<>();
    terminals.add(r);
    terminals.add(p);
    terminals.add(plus);
    terminals.add(and);
    terminals.add(open);
    terminals.add(close);
    terminals.add(minus);
    
     // GRAMMAR RULES
    ArrayList<Rule> rules = new ArrayList<>();

    // S -> VT|UF|ZC|MF|p|r
    rules.add(new Rule(S0, new Word(V, T)));
    rules.add(new Rule(S0, new Word(U, F)));
    rules.add(new Rule(S0, new Word(Z, C)));
    rules.add(new Rule(S0, new Word(M, F)));
    rules.add(new Rule(S0, new Word(p)));
    rules.add(new Rule(S0, new Word(r)));

    // E -> VT|UF|ZC|MF|p|r
    rules.add(new Rule(E, new Word(V, T)));
    rules.add(new Rule(E, new Word(U, F)));
    rules.add(new Rule(E, new Word(Z, C)));
    rules.add(new Rule(E, new Word(M, F)));
    rules.add(new Rule(E, new Word(p)));
    rules.add(new Rule(E, new Word(r)));
    // T -> UF|ZC|MF|p|r
    rules.add(new Rule(T, new Word(U, F)));
    rules.add(new Rule(T, new Word(Z, C)));
    rules.add(new Rule(T, new Word(M, F)));
    rules.add(new Rule(T, new Word(p)));
    rules.add(new Rule(T, new Word(r)));
    // F -> ZC|MF|p|r
    rules.add(new Rule(F, new Word(Z, C)));
    rules.add(new Rule(F, new Word(M, F)));
    rules.add(new Rule(F, new Word(p)));
    rules.add(new Rule(F, new Word(r)));
    // V -> EP
    rules.add(new Rule(V, new Word(E, P)));
    // U -> TA
    rules.add(new Rule(U, new Word(T, A)));
    // Z -> OE
    rules.add(new Rule(Z, new Word(O, E)));
    // P -> +
    rules.add(new Rule(P, new Word(plus)));
    // A -> &
    rules.add(new Rule(A, new Word(and)));
    // O -> (
    rules.add(new Rule(O, new Word(open)));
    // C -> )
    rules.add(new Rule(C, new Word(close)));
    // M -> -
    rules.add(new Rule(M, new Word(minus)));


   // Pass rules to create a context free grammar
   ContextFreeGrammar cfg = new ContextFreeGrammar(variables, terminals, rules, S0);
   // Return the grammar
		return cfg;
	}
}
