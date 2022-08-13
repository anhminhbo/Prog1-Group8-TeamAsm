package tableFormatter;

import java.util.ArrayList;

public class TableFormatterService {
    private static final int spaceBetween = 6;
    private final String[] labels;
    private final ArrayList<String[]> rows = new ArrayList<>();
    private final ArrayList<Integer> maxLengthsOfEachColumn = new ArrayList<>();

    public TableFormatterService(String[] labels) {
        this.labels = labels;
    }

    public void addRows(String[] row) {
        rows.add(row);
    }

    public void display() {
        ArrayList<Integer> listOfColumnsLength = maximumLengthOfColumn();
        printTableRow(listOfColumnsLength);
        printDelimiter();
    }

    private void printTableRow(ArrayList<Integer> listOfColumnsLength) {
        printDelimiter();
        for (int i = 0; i < labels.length; i++) {
            if (i == 0) {
                System.out.print(labels[i]);
                continue;
            }
            int distance =
                    listOfColumnsLength.get(i - 1)
                            - labels[i - 1].length()
                            + spaceBetween
                            + labels[i].length();
            System.out.printf("%" + distance + "s", labels[i]);
        }
        System.out.println();
        printDelimiter();
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).length; j++) {
                int distance = 0;
                if (j != 0) {
                    distance =
                            listOfColumnsLength.get(j - 1)
                                    - rows.get(i)[j - 1].length()
                                    + spaceBetween
                                    + rows.get(i)[j].length();
                    System.out.printf("%" + distance + "s", rows.get(i)[j]);
                } else {
                    System.out.print(rows.get(i)[j]);
                }
            }
            if (i != rows.size() - 1) {
                System.out.println();
                printDelimiter();
            } else {
                System.out.println();
            }
        }
    }

    private void printDelimiter() {
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private ArrayList<Integer> maximumLengthOfColumn() {
        int maxLen = 0;
        for (int i = 0; i < labels.length; i++) {
            maxLen = Math.max(labels[i].length(), maxLen);
            for (String[] row : rows) {
                maxLen = Math.max(row[i].length(), maxLen);
            }
            maxLengthsOfEachColumn.add(maxLen);
            maxLen = 0;
        }

        return maxLengthsOfEachColumn;
    }
}
