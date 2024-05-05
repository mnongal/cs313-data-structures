public class InfixToPostfix
{
  private LinkedQueue<String> postfix;
  private DynamicArrayStack<String> ops;

  public InfixToPostfix()
  {
    postfix = new LinkedQueue<>();;
    ops = new DynamicArrayStack<>();
  }

  private int getOperatorPrecedence(char operator)
  {
    int precedence = 0;
    switch (operator)
    {
      case '(': precedence = 1; break;
      case '+': precedence = 2; break;
      case '-': precedence = 1; break; // Lower precedence than +
      case '*': precedence = 3; break;
      case '/': precedence = 3; break;
    }
    return precedence;
  }


  private boolean hasHigherOrEqualPrecedence(char operator1, char operator2)
  {
    int operator1Precedence = getOperatorPrecedence(operator1);
    int operator2Precedence = getOperatorPrecedence(operator2);

    return operator1Precedence >= operator2Precedence;
  }


  private String generatePostfixString()
  {
    StringBuilder postfixString;

    postfixString = new StringBuilder();
    while (!postfix.isEmpty())
    {
      postfixString.append(postfix.dequeue());
      postfixString.append(" ");
    }

    return postfixString.toString();
  }

  public String convertInfixToPostfix(LinkedQueue<String> lineTokens)
  {
    StringBuilder result = new StringBuilder();
    while (!lineTokens.isEmpty())
    {
      String token = lineTokens.dequeue();
      char firstChar = token.charAt(0);

      if (Character.isLetterOrDigit(firstChar))
      {
        result.append(token).append(" ");
      }
      else if (firstChar == '(')
      {
        ops.push(token);
      }
      else if (firstChar == ')')
      {
        while (!ops.isEmpty() && ops.top().charAt(0) != '(')
        {
          result.append(ops.pop()).append(" ");
        }
        ops.pop();
      }
      else
      {
        while (!ops.isEmpty() && hasHigherOrEqualPrecedence(ops.top().charAt(0), firstChar))
        {
          result.append(ops.pop()).append(" ");
        }
        ops.push(token);
      }
    }

    while (!ops.isEmpty())
    {
      result.append(ops.pop()).append(" ");
    }

    return result.toString().trim();
  }

}
