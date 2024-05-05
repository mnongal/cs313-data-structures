import java.util.LinkedList;

public class LinkedQueue<AnyType> implements Queue<AnyType>
{
  private LinkedList<AnyType> data;

  public LinkedQueue() { data = new LinkedList<>(); }

  public int size() { return data.size(); }

  public boolean isEmpty() { return data.isEmpty(); }

  public void enqueue(AnyType newValue) { data.add(newValue); }

  public AnyType first() { return data.get(0); }

  public AnyType dequeue() { return data.remove(0); }
}
