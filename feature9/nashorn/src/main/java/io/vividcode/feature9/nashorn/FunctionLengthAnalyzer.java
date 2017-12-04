package io.vividcode.feature9.nashorn;

import com.google.common.collect.Lists;
import io.vavr.Tuple2;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import jdk.nashorn.api.tree.CompilationUnitTree;
import jdk.nashorn.api.tree.FunctionDeclarationTree;
import jdk.nashorn.api.tree.FunctionExpressionTree;
import jdk.nashorn.api.tree.IdentifierTree;
import jdk.nashorn.api.tree.Parser;
import jdk.nashorn.api.tree.SimpleTreeVisitorES5_1;

public class FunctionLengthAnalyzer {

  public List<Tuple2<String, Long>> analyze(final URL url) throws IOException {
    final Parser parser = Parser.create();
    final CompilationUnitTree tree = parser.parse(url, System.out::println);
    final List<Tuple2<String, Long>> result = Lists.newArrayList();
    tree.accept(new SimpleTreeVisitorES5_1<Void, Void>() {
      @Override
      public Void visitFunctionDeclaration(
          final FunctionDeclarationTree node,
          final Void r) {
        result.add(new Tuple2<>(
            node.getName().getName(),
            node.getBody().getEndPosition()
                - node.getBody().getStartPosition()));
        return super.visitFunctionDeclaration(node, r);
      }

      @Override
      public Void visitFunctionExpression(
          final FunctionExpressionTree node,
          final Void r) {
        final String name = Optional.ofNullable(node.getName())
            .map(IdentifierTree::getName)
            .orElse("anonymous_"
                + node.getBody().getStartPosition());
        result.add(new Tuple2<>(
            name,
            node.getBody().getEndPosition()
                - node.getBody().getStartPosition()));
        return super.visitFunctionExpression(node, r);
      }
    }, null);
    Collections.sort(result,
        Collections.reverseOrder(Comparator.comparingLong(Tuple2::_2)));
    return result;
  }

  public static void main(final String[] args) throws IOException {
    final List<Tuple2<String, Long>> result =
        new FunctionLengthAnalyzer().analyze(
            new URL("https://code.jquery.com/jquery-3.2.1.js"));
    result.forEach(tuple2 ->
        System.out.printf("%30s -> %s%n", tuple2._1, tuple2._2));
  }
}
