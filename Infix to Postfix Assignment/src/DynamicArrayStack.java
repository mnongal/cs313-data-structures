public class DynamicArrayStack<AnyType> implements Stack<AnyType>
{
  public static final int DEFAULT_CAPACITY = 2;

  int topOfStack;
  AnyType[] data;

  public DynamicArrayStack()
  {
    this(DEFAULT_CAPACITY);
  }

  public DynamicArrayStack(int capacity)
  {
    topOfStack = -1;
    data = (AnyType[]) new Object[capacity];
  }

  public int size()
  {
    return topOfStack + 1;
  }

  public boolean isEmpty()
  {
    return (size() == 0);
  }

  protected void resize(int newCapacity)
  {
    AnyType[] newData = (AnyType[]) new Object[newCapacity];
    System.arraycopy(data, 0, newData, 0, size());
    data = newData;
  }

  public void push(AnyType newValue)
  {
    if (size() == data.length)
      resize(2 * data.length);
    data[++topOfStack] = newValue;
  }

  public AnyType top() throws IllegalStateException
  {
    if (isEmpty())
      throw new IllegalStateException("Stack is empty");
    return data[topOfStack];
  }

  public AnyType pop() throws IllegalStateException
  {
    if (isEmpty())
      throw new IllegalStateException("Stack is empty");
    AnyType topValue = data[topOfStack];
    data[topOfStack--] = null;
    if (size() > 0 && size() == data.length / 4)
      resize(data.length / 2);
    return topValue;
  }
}
