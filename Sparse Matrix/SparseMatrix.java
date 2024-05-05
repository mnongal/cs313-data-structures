import java.io.*;
import java.util.*;

public class SparseMatrix {
    private LinkedList<Element>[] rows;
    private LinkedList<Element>[] columns;

    private class Element {
        private int value;
        private int row;
        private int column;

        public Element(int value, int row, int column) {
            this.value = value;
            this.row = row;
            this.column = column;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }
    }

    public SparseMatrix(int rows, int columns) {
        this.rows = new LinkedList[rows];
        this.columns = new LinkedList[columns];
        for (int i = 0; i < rows; i++) {
            this.rows[i] = new LinkedList<>();
        }
        for (int j = 0; j < columns; j++) {
            this.columns[j] = new LinkedList<>();
        }
    }

    public SparseMatrix add(SparseMatrix B) {
        if (this.rows.length != B.rows.length || this.columns.length != B.columns.length) {
            throw new IllegalArgumentException("Matrices dimensions do not match for addition");
        }

        SparseMatrix result = new SparseMatrix(this.rows.length, this.columns.length);

        for (int i = 0; i < this.rows.length; i++) {
            LinkedList<Element> rowA = this.rows[i];
            LinkedList<Element> rowB = B.rows[i];

            Iterator<Element> itA = rowA.iterator();
            Iterator<Element> itB = rowB.iterator();
            Element elemA = itA.hasNext() ? itA.next() : null;
            Element elemB = itB.hasNext() ? itB.next() : null;

            while (elemA != null || elemB != null) {
                if (elemA == null) {
                    result.addElement(new Element(elemB.value, i, elemB.column));
                    elemB = itB.hasNext() ? itB.next() : null;
                } else if (elemB == null) {
                    result.addElement(new Element(elemA.value, i, elemA.column));
                    elemA = itA.hasNext() ? itA.next() : null;
                } else if (elemA.column < elemB.column) {
                    result.addElement(new Element(elemA.value, i, elemA.column));
                    elemA = itA.hasNext() ? itA.next() : null;
                } else if (elemA.column > elemB.column) {
                    result.addElement(new Element(elemB.value, i, elemB.column));
                    elemB = itB.hasNext() ? itB.next() : null;
                } else {
                    int sum = elemA.value + elemB.value;
                    if (sum != 0) {
                        result.addElement(new Element(sum, i, elemA.column));
                    }
                    elemA = itA.hasNext() ? itA.next() : null;
                    elemB = itB.hasNext() ? itB.next() : null;
                }
            }
        }
        return result;
    }

    private void addElement(Element e) {
        if (rows[e.getRow()] == null) {
            rows[e.getRow()] = new LinkedList<>();
        }
        rows[e.getRow()].add(e);

        if (columns[e.getColumn()] == null) {
            columns[e.getColumn()] = new LinkedList<>();
        }
        columns[e.getColumn()].add(e);
    }


    public void mult(int c) {
        for (LinkedList<Element> rowList : rows) {
            for (Element e : rowList) {
                e.setValue(e.getValue() * c);
            }
        }
    }

    public void read(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        boolean setSize = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\D+");
            if (!setSize) {
                int rowsCount = Integer.parseInt(parts[1]); // m value
                int columnsCount = Integer.parseInt(parts[2]); // n value
                this.rows = new LinkedList[rowsCount];
                this.columns = new LinkedList[columnsCount];
                for (int i = 0; i < rowsCount; i++) {
                    this.rows[i] = new LinkedList<>();
                }
                for (int j = 0; j < columnsCount; j++) {
                    this.columns[j] = new LinkedList<>();
                }
                setSize = true;
            } else {
                int row = Integer.parseInt(parts[0]);
                int column = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                addElement(new Element(value, row, column));
            }
        }
        scanner.close();
    }




    public void write(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(this.rows.length + " " + this.columns.length);
        writer.newLine();

        for (LinkedList<Element> rowList : rows) {
            for (Element e : rowList) {
                writer.write(e.getRow() + "," + e.getColumn() + " = " + e.getValue());
                writer.newLine();
            }
        }
        writer.close();
    }
    public SparseMatrix mult(SparseMatrix B) {
        if (this.columns.length != B.rows.length) {
            throw new IllegalArgumentException("Matrices dimensions do not match for multiplication");
        }

        SparseMatrix result = new SparseMatrix(this.rows.length, B.columns.length);

        for (int i = 0; i < this.rows.length; i++) {
            for (Element a : this.rows[i]) {
                int aColumn = a.getColumn();
                LinkedList<Element> bColumnList = B.columns[aColumn];
                for (Element b : bColumnList) {
                    int bColumn = b.getColumn();
                    int existingValue = result.getElement(i, bColumn);
                    int newValue = existingValue + a.getValue() * b.getValue();
                    result.setElement(i, bColumn, newValue);
                }
            }
        }

        return result;
    }

    private int getElement(int row, int column) {
        for (Element e : rows[row]) {
            if (e.getColumn() == column) {
                return e.getValue();
            }
        }
        return 0;
    }

    private void setElement(int row, int column, int value) {
        Iterator<Element> iterator = rows[row].iterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (e.getColumn() == column) {
                if (value == 0) {
                    iterator.remove();
                    return;
                } else {
                    e.setValue(value);
                    return;
                }
            }
        }
        if (value != 0) {
            rows[row].add(new Element(value, row, column)); 
        }
    }

}
