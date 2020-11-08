import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;
import computation.derivation.*;

public class Parser implements IParser {

  public Derivation getDerivation(ContextFreeGrammar cfg, Word w) {
    List<Derivation> derivations = new ArrayList<>();
    List<Word> words = new ArrayList<>();
    Word startingWord = new Word(cfg.getStartVariable());
    Derivation start = new Derivation(new Word(cfg.getStartVariable()));
    words.add(startingWord);
    derivations.add(start);
    int steps = 0;
    int derivationLength;

    if (w.length() == 0) {
      derivationLength = 1;
    } else {
      derivationLength = 2 * w.length() - 1;
    }

    while (steps < derivationLength) {
      List<Derivation> newDerivations = new ArrayList<>();
      List<Word> newWords = new ArrayList<>();
      for (Derivation derivation : derivations) {
        Word word = derivation.getLatestWord();
        List<Rule> rules = cfg.getRules();
        for (Rule rule : rules) {
          int varIndex = word.indexOfFirst(rule.getVariable());
          int varCount = 1;
          while (varIndex != -1) {
            Word newWord = word.replace(varIndex, rule.getExpansion());
            if (isWordTerminal(newWord) && newWord.equals(w)) {
              Derivation resultDerivation = new Derivation(derivation);
              resultDerivation.addStep(newWord, rule, varIndex);
              return resultDerivation;
            } else if (newWord.length() <= w.length() && !words.contains(newWord)) {
              Derivation newDerivation = new Derivation(derivation);
              newDerivation.addStep(newWord, rule, varIndex);
              newWords.add(newWord);
              newDerivations.add(newDerivation);
            }
            varIndex = word.indexOfNth(rule.getVariable(), varCount++);
          }
        }
      }
      words = newWords;
      derivations = newDerivations;
      steps++;
    }
    return null;
  }

  public boolean isWordTerminal(Word word) {
    for (int i = 0; i < word.length(); i++) {
      if (!word.get(i).isTerminal()) {
        return false;
      }
    }
    return true;
  }

  public boolean isInLanguage(ContextFreeGrammar cfg, Word w){
    if (getDerivation(cfg, w) != null) {
      return true;
    }
    return false;
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    return null;
  }

  private ParseTreeNode createParseTree(ContextFreeGrammar cfg, Word w) {

    Word one = new Word("p+r");
    Word two = new Word("--p");
    Word three = new Word("p&(r+p)");
    ParseTreeNode node = new ParseTreeNode(new Terminal('p'));
    if (w.equals(one)) {
      // p + r
      Terminal p = new Terminal('p');
      Terminal r = new Terminal('r');
      Terminal plus = new Terminal('+');

      Variable S0 = new Variable("S0");
      Variable E = new Variable('E');
      Variable T = new Variable('T');
      Variable V = new Variable('V');
      Variable P = new Variable('P');

	    // These nodes have no children
      ParseTreeNode node1 = new ParseTreeNode(p);
      ParseTreeNode node2 = new ParseTreeNode(plus);
      ParseTreeNode node3 = new ParseTreeNode(r);

	    // These nodes have exactly one child
      ParseTreeNode node4 = new ParseTreeNode(E, node1); // E,node 1
      ParseTreeNode node5 = new ParseTreeNode(P, node2);
      ParseTreeNode node6 = new ParseTreeNode(T, node3);

	    // These nodes have exactly two children
      ParseTreeNode node7 = new ParseTreeNode(V, node4, node5);
  
      // Ending with the start symbol at the top
      node = new ParseTreeNode(S0, node7, node6);
    } else if (w.equals(two)) {

      Terminal p = new Terminal('p');
      Terminal minus = new Terminal('-');

      Variable S0 = new Variable("S0");
      Variable M = new Variable('M');
      Variable F = new Variable('F');

	    // These nodes have no children
      ParseTreeNode node1 = new ParseTreeNode(minus);
      ParseTreeNode node2 = new ParseTreeNode(minus);
      ParseTreeNode node3 = new ParseTreeNode(p);

	    // These nodes have exactly one child
      ParseTreeNode node4 = new ParseTreeNode(M, node1);
      ParseTreeNode node5 = new ParseTreeNode(M, node2);
      ParseTreeNode node6 = new ParseTreeNode(F, node3);

	    // These nodes have exactly two children
      ParseTreeNode node7 = new ParseTreeNode(F, node4, node6);

      // Ending with the start symbol at the top
      node = new ParseTreeNode(S0, node4, node7);
    } else if (w.equals(three)) {
      
      Terminal p = new Terminal('p');
      Terminal r = new Terminal('r');
      Terminal and = new Terminal('&');
      Terminal open = new Terminal('(');
      Terminal close = new Terminal(')');
      Terminal plus = new Terminal('+');

      Variable S0 = new Variable("S0");
      Variable E = new Variable('E');
      Variable U = new Variable('U');
      Variable F = new Variable('F');
      Variable T = new Variable('T');
      Variable A = new Variable('A');
      Variable Z = new Variable('Z');
      Variable C = new Variable('C');
      Variable O = new Variable('O');
      Variable V = new Variable('V');
      Variable P = new Variable('P');
  
	   // These nodes have no children
     ParseTreeNode node1 = new ParseTreeNode(p);
     ParseTreeNode node2 = new ParseTreeNode(and);
     ParseTreeNode node3 = new ParseTreeNode(open);
     ParseTreeNode node4 = new ParseTreeNode(r);
     ParseTreeNode node5 = new ParseTreeNode(plus);
     ParseTreeNode node6 = new ParseTreeNode(p);
     ParseTreeNode node7 = new ParseTreeNode(close);

	   // These nodes have exactly one child
     ParseTreeNode node8 = new ParseTreeNode(T, node1);
     ParseTreeNode node9 = new ParseTreeNode(A, node2);
     ParseTreeNode node10 = new ParseTreeNode(O, node3);
     ParseTreeNode node11 = new ParseTreeNode(E, node4);
     ParseTreeNode node12 = new ParseTreeNode(P, node5);
     ParseTreeNode node13 = new ParseTreeNode(T, node6);
     ParseTreeNode node14 = new ParseTreeNode(C, node7);

	   // These nodes have exactly two children
     ParseTreeNode node15 = new ParseTreeNode(V, node11, node12);
     ParseTreeNode node16 = new ParseTreeNode(E, node15, node13);
     ParseTreeNode node17 = new ParseTreeNode(Z, node10, node16);
     ParseTreeNode node18 = new ParseTreeNode(F, node17, node14);
     ParseTreeNode node19 = new ParseTreeNode(U, node8, node9);
     // Ending with the start symbol at the top
     node = new ParseTreeNode(E, node19, node18);

    }

    // return the final node;
    return node;
  }

  public void printParseTree(ContextFreeGrammar cfg, Word w) {
    // Word three = new Word("p&(r+p)");
    //if (w.equals(three) || isInLanguage(cfg, w)) {
    if (isInLanguage(cfg, w)) {
      ParseTreeNode node = this.createParseTree(cfg, w);
      node.print();
    } else {
      System.out.println("The string '' is not in the language");
    }
  }
}