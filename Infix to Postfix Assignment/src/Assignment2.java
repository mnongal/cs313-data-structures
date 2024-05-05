public class Assignment2
{
  public static void main(String[] args)
  {
    LinkedQueue<String> fileLines, lineTokens;
    InfixToPostfix itp;
    InputReader ir;
    String filename;

    filename = args[0];
    itp = new InfixToPostfix();
    ir = new InputReader(filename);
    fileLines = ir.readAndReturnFileLines();
    while (!fileLines.isEmpty())
    {
      lineTokens = TokenizeLine.generateTokenQueueFromLine(fileLines.dequeue());
      System.out.println(itp.convertInfixToPostfix(lineTokens));
    }
  }
}
