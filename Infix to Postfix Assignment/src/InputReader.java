import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader
{
  BufferedReader br;

  public InputReader(String filename)
  {
    try
    {
      FileInputStream fstream = new FileInputStream(filename);
      br = new BufferedReader(new InputStreamReader(fstream));
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }

  public LinkedQueue<String> readAndReturnFileLines()
  {
    String line;
    LinkedQueue<String> fileLines;

    fileLines = new LinkedQueue<>();
    try
    {
      while ((line = br.readLine()) != null)
      {
        fileLines.enqueue(line);
      }
      br.close();
    }
    catch (IOException e)
    {
      System.out.println(e);
    }

    return fileLines;
  }
}
