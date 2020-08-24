package src.client.gui;

import src.client.Client;
import src.commands.Command_Remove_By_Id;
import src.commands.Command_Update_By_Id;
import src.elements.Product;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.xml.bind.ValidationException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

public class CollectionTableModel extends DefaultTableModel {

    private final int COLUMNS_AMOUNT = 16;
    private static ArrayList<Product> data;
    private Client client;
    private Localizer localizer;
    private boolean blocked;

    public CollectionTableModel(Client client, Localizer localizer) {
          data = new ArrayList<>();
          this.client = client;
          this.localizer = localizer;
          blocked = false;
         // for (int i = 0; i < data.size(); i++) {
           //   data.add(new String[getColumnCount()]);
          //}
    }

    @Override
    synchronized public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "id";
            case 1: return (String) localizer.getBundle().getObject("name");
            case 2: return (String) localizer.getBundle().getObject("coordinateX");
            case 3: return (String) localizer.getBundle().getObject("coordinateY");
            case 4: return (String) localizer.getBundle().getObject("creationDate");
            case 5: return (String) localizer.getBundle().getObject("price");
            case 6: return (String) localizer.getBundle().getObject("partNumber");
            case 7: return (String) localizer.getBundle().getObject("unitOfMeasure");
            case 8: return (String) localizer.getBundle().getObject("personName");
            case 9: return (String) localizer.getBundle().getObject("personHeight");
            case 10: return (String) localizer.getBundle().getObject("personEyeColor");
            case 11: return (String) localizer.getBundle().getObject("locationName");
            case 12: return (String) localizer.getBundle().getObject("locationX");
            case 13: return (String) localizer.getBundle().getObject("locationY");
            case 14: return (String) localizer.getBundle().getObject("locationZ");
            case 15: return (String) localizer.getBundle().getObject("creator");
        }
        return null;
    }

    @Override
    synchronized public int getRowCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    synchronized public int getColumnCount() {
        return COLUMNS_AMOUNT;
    }

    @Override
    synchronized public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = data.get(rowIndex);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localizer.getLocale());

        switch (columnIndex) {
            case 0: return product.getId();
            case 1: return product.getName();
            case 2: return String.format(localizer.getLocale(), "%.1f", product.getCoordinates().getX());
            case 3: return String.format(localizer.getLocale(), "%.1f", product.getCoordinates().getY());
            case 4: return product.getCreationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(localizer.getLocale()));
            case 5: return formatter.format(product.getPrice());
            case 6: return product.getPartNumber();
            case 7: return localizer.getBundle().getObject(product.getUnitOfMeasure().toString());
            case 8: return product.getOwner().getName();
            case 9: return product.getOwner().getHeight();
            case 10: return localizer.getBundle().getObject(product.getOwner().getEyeColor().toString());
            case 11: return product.getOwner().getLocation().getName();
            case 12: return product.getOwner().getLocation().getX();
            case 13: return product.getOwner().getLocation().getY();
            case 14: return product.getOwner().getLocation().getZ();
            case 15: return product.getHost();
        }

        return null;
    }

    @Override
    synchronized public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Product product = data.get(rowIndex);
            if (client.getUser().getLogin().equals(product.getHost())) {
                String value = (String) aValue;
                switch (columnIndex) {
                    case 1:
                        product.setName(value);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 2:
                        product.getCoordinates().setX(Float.parseFloat(value.replace(',', '.')));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 3:
                        product.getCoordinates().setY(Double.parseDouble(value.replace(',', '.')));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 5:
                        product.setPrice(Long.parseLong(value.replaceAll("[^0-9]", "")));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 6:
                        product.setPartNumber(value);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 7:
                        String unitOfMeasure = (String) localizer.getBundle().getObject(value);
                        product.setUnitOfMeasure(unitOfMeasure);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 8:
                        product.getOwner().setName(value);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 9:
                        product.getOwner().setHeight(Integer.parseInt(value));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;

                    case 10:
                        String color = (String) localizer.getBundle().getObject(value);
                        product.getOwner().setEyeColor(color);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;

                    case 11:
                        product.getOwner().getLocation().setName(value);
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 12:
                        product.getOwner().getLocation().setX(Long.parseLong(value));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 13:
                        product.getOwner().getLocation().setY(Long.parseLong(value));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                    case 14:
                        product.getOwner().getLocation().setZ(Integer.parseInt(value));
                        client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                        break;
                }
            }
        } catch (ValidationException | ClassCastException ex) {
            System.out.println("The entered value is incorrect.");
        }
    }

    @Override
    synchronized public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    Product getRow(int rowIndex) {
        return data.get(rowIndex);
    }

    int getRowIndex(int id) {
        int index = 0;
        for (Product p : data) {
            if (p.getId() == id) {
                return index;
            }
            index++;
        }
       return -1;
    }

    void filterBySelectedCell(int columnIndex, int rowIndex) {
        try {
            switch (columnIndex) {
                case 1:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getName().equals(Product.getName()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 2:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getCoordinates().getX().equals(Product.getCoordinates().getX()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 3:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getCoordinates().getY().equals(Product.getCoordinates().getY()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 4:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getCreationDate().equals(Product.getCreationDate()))
                            .collect(Collectors.toCollection(ArrayList::new));

                case 5:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getPrice().equals(Product.getPrice()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 6:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getPartNumber().equals(Product.getPartNumber()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 7:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getUnitOfMeasure().equals(Product.getUnitOfMeasure()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 8:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getName().equals(Product.getOwner().getName()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 9:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getHeight().equals(Product.getOwner().getHeight()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 11:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getLocation().getName().equals(Product.getOwner().getLocation().getName()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 12:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getLocation().getX().equals(Product.getOwner().getLocation().getX()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 13:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getLocation().getY().equals(Product.getOwner().getLocation().getY()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 14:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getOwner().getLocation().getZ() == Product.getOwner().getLocation().getZ())
                            .collect(Collectors.toCollection(ArrayList::new));
                    break;

                case 15:
                    data = data.stream()
                            .filter(Product -> data.get(rowIndex).getHost().equals(Product.getHost()))
                            .collect(Collectors.toCollection(ArrayList::new));
            }
            blocked = true;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("The element in table not found");
        }
    }

    void sortByColumn(int columnIndex, int mode) {
        blocked = true;
        switch (columnIndex) {
            case 0:
                Collections.sort(data);
                break;

            case 1:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getName().compareTo(p2.getName());
                    }
                });
                break;

            case 2:
                Collections.sort(data, new Comparator<Product>() {
                public int compare(Product p1, Product p2) {
                    if (p1.getCoordinates().getX() > p2.getCoordinates().getX()) {
                        return 1;
                    } else if (p1.getCoordinates().getX().equals(p2.getCoordinates().getX())) {
                        return 0;
                    }
                    return -1;
                }
            });
                break;

            case 3:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getCoordinates().getY() > p2.getCoordinates().getY()) {
                            return 1;
                        } else if (p1.getCoordinates().getY().equals(p2.getCoordinates().getY())) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 4:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getCreationDate().compareTo(p2.getCreationDate());
                    }
                });
                break;

            case 5:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getPrice() > p2.getPrice()) {
                            return 1;
                        } else if (p1.getPrice().equals(p2.getPrice())) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 6:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getPartNumber().compareTo(p2.getPartNumber());
                    }
                });
                break;

            case 7:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getUnitOfMeasure().compareTo(p2.getUnitOfMeasure());
                    }
                });
                break;

            case 8:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getOwner().getName().compareTo(p2.getOwner().getName());
                    }
                });
                break;

            case 9:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getOwner().getHeight() > p2.getOwner().getHeight()) {
                            return 1;
                        } else if (p1.getOwner().getHeight().equals(p2.getOwner().getHeight())) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 10:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getOwner().getEyeColor().compareTo(p2.getOwner().getEyeColor());
                    }
                });
                break;

            case 11:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getOwner().getLocation().getName().compareTo(p2.getOwner().getLocation().getName());
                    }
                });
                break;

            case 12:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getOwner().getLocation().getX() > p2.getOwner().getLocation().getX()) {
                            return 1;
                        } else if (p1.getOwner().getLocation().getX().equals(p2.getOwner().getLocation().getX())) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 13:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getOwner().getLocation().getY() > p2.getOwner().getLocation().getY()) {
                            return 1;
                        } else if (p1.getOwner().getLocation().getY().equals(p2.getOwner().getLocation().getY())) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 14:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        if (p1.getOwner().getLocation().getZ() > p2.getOwner().getLocation().getZ()) {
                            return 1;
                        } else if (p1.getOwner().getLocation().getZ() == p2.getOwner().getLocation().getZ()) {
                            return 0;
                        }
                        return -1;
                    }
                });
                break;

            case 15:
                Collections.sort(data, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getHost().compareTo(p2.getHost());
                    }
                });
                break;
        }
        if (mode == 1) {
            data.sort(Comparator.reverseOrder());
        }
    }

    void addDate(Product product) {
        data.add(product);
    }

    void deleteRow(int rowIndex) {
        if (client.getUser().getLogin().equals(data.get(rowIndex).getHost())) {
            client.sendRequest(new Command_Remove_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), data.get(rowIndex).getId()));
            data.remove(rowIndex);
        }
    }

    public void updateAll(ArrayList<Product> products) {
        data = products;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void unblock() {
        blocked = false;
    }
}
