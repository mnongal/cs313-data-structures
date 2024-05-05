import java.util.StringTokenizer;

public class TokenizeLine
{
  public static LinkedQueue<String> generateTokenQueueFromLine(String line)
  {
    LinkedQueue<String> lineTokens;
    StringTokenizer lineTokenizer;

    lineTokens = new LinkedQueue<>();
    lineTokenizer = new StringTokenizer(line, " ");
    while (lineTokenizer.hasMoreTokens())
    {
      lineTokens.enqueue(lineTokenizer.nextToken());
    }

    return lineTokens;
  }
}