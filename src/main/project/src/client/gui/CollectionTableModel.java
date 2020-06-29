package src.client.gui;

import src.elements.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CollectionTableModel extends AbstractTableModel {

    private int columnsAmount = 16;
    private static ArrayList<String[]> data;

    public CollectionTableModel() {
          data = new ArrayList<>();
         // for (int i = 0; i < data.size(); i++) {
           //   data.add(new String[getColumnCount()]);
          //}
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "id";
            case 1: return "Name";
            case 2: return "Coordinate X";
            case 3: return "Coordinate Y";
            case 4: return "Creation Date";
            case 5: return "Price";
            case 6: return "Part Number";
            case 7: return "Unit of Measure";
            case 8: return  "Person's Name";
            case 9: return "Person's Height";
            case 10: return "Person's Eye Color";
            case 11: return "Location's Name";
            case 12: return "Location_X";
            case 13: return "Location_Y";
            case 14: return "Location_Z";
            case 15: return "Creator";
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnsAmount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = data.get(rowIndex);
        return row[columnIndex];
    }

    public void addDate(String[] row) {
        if(row.length <= getColumnCount()) {
            data.add(row);
        }
    }

    public void updateAll(ArrayList<Product> products) {
        for (Product p : products) {
            String[] row = {
                    String.valueOf(p.getId()),
                    p.getName(),
                    String.valueOf(p.getCoordinates().getX()),
                    String.valueOf(p.getCoordinates().getY()),
                    String.valueOf(p.getCreationDate()),
                    String.valueOf(p.getPrice()),
                    p.getPartNumber(),
                    String.valueOf(p.getUnitOfMeasure()),
                    p.getOwner().getName(),
                    String.valueOf(p.getOwner().getHeight()),
                    String.valueOf(p.getOwner().getEyeColor()),
                    p.getOwner().getLocation().getName(),
                    String.valueOf(p.getOwner().getLocation().getX()),
                    String.valueOf(p.getOwner().getLocation().getY()),
                    String.valueOf(p.getOwner().getLocation().getZ()),
                    p.getHost()
            };
               addDate(row);
         }
    }
}
