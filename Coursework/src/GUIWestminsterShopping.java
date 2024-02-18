import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Stream;

public class GUIWestminsterShopping extends JFrame { // GUI class for the Westminster Shopping Center Customer Interface
    private JTable productTable;
    private DefaultTableModel tableModel;

    private JTextArea textArea;

    public GUIWestminsterShopping() { // Constructor for GUIWestminsterShopping
        // Set up the main frame
        setTitle("Westminster Shopping Center");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //  main panel with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        //  subpanels for each section
        JPanel sectionPanel1 = createSectionPanel();
        JPanel subPanel1_1 = createSubPanel1_1();
        JPanel subPanel1_2 = createSubPanel1_2();
        JPanel subPanel1_3 = createSubPanel1_3();
        // Set up GridLayout for Section 1 with 1 row and 3 columns
        sectionPanel1.setLayout(new GridLayout(1, 3));

        // Add subpanels to Section 1
        sectionPanel1.add(subPanel1_1);
        sectionPanel1.add(subPanel1_2);
        sectionPanel1.add(subPanel1_3);

        JPanel sectionPanel2 = createTablePanel();
        JPanel sectionPanel3 = createTextAreaPanel();
        JPanel sectionPanel4 = createButtonPanel();

        // Add subpanels to the main panel
        mainPanel.add(sectionPanel1);
        mainPanel.add(sectionPanel2);
        mainPanel.add(sectionPanel3);
        mainPanel.add(sectionPanel4);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        // Load products and update the table
        loadProductsAndUpdateTable();

        // Make the frame visible
        setVisible(true);
    }

    private JPanel createSectionPanel() {  //create section panel
        JPanel sectionPanel = new JPanel();

        return sectionPanel;
    }

    private JPanel createSubPanel1_1() {
        JPanel subPanel = new JPanel();
        JLabel label = new JLabel("Select product category");
        subPanel.add(Box.createHorizontalGlue()); // add space to the right

        // use flow layout to center the label
        subPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        subPanel.add(label);
        return subPanel;
    }

    private JPanel createSubPanel1_2() { //subpanel1_2 this SubPanel contains the dropDown menu
        JPanel subPanel = new JPanel();
        //  a dropdown menu is created
        String[] categories = {"All", "Electronics", "Clothing"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);

        // actionListener to the dropdown
        categoryDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryDropdown.getSelectedItem();

                //load all products or filter based on the selected category
                if(selectedCategory.equals("All")){
                    loadProductsAndUpdateTable();
                }
                else {
                    filterAndLoadProducts(selectedCategory); // this method will filter the table according to clothing and electronics
                }
            }
        });

        // Use FlowLayout to center the dropdown menu
        subPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        subPanel.add(categoryDropdown);
        return subPanel;
    }

    private JPanel createSubPanel1_3() { //this button contains the shopping cart button which will direct the user to the shopping cart
        JPanel subPanel = new JPanel();
        // create a button for the shopping cart
        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setPreferredSize(new Dimension(50, 30));
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  new frame for the shopping cart
                createShoppingCartFrame();
            }
        });
        // set the button to the top right corner
        subPanel.setLayout(new BorderLayout());
        subPanel.add(shoppingCartButton, BorderLayout.NORTH);
        return subPanel;
    }

    private JPanel createTablePanel() {   //table is created in the panel 2
        JPanel tablePanel = new JPanel();
        // create a table and add it to the table panel
        productTable = createProductTable();

        // Add a ListSelectionListener to handle row selection
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                displayDetailsInTextArea(selectedRow);
            }
        });

        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }


    private JTable createProductTable() {   //creating table columns
        String[] columnNames = {"Product ID", "Name", "Category", "Price(lkr)", "Info"};
        // Initialize tableModel here
        tableModel = new DefaultTableModel(columnNames, 0);
        return new JTable(tableModel);


    }

    private void loadProductsAndUpdateTable() {    //loading products from the westminsterShoppingManager class
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.loadProductsFromFile();

        //  update the table
        updateTable(shoppingManager.getProductList());
    }

    // Method to update the table with loaded products
    private void updateTable(List<Product> products) {
        // Ensure that tableModel is initialized
        if (tableModel == null) {
            System.out.println("Error: tableModel is not initialized.");
            return;
        }
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Populate the table with loaded products
        for (Product product : products) {
            Object[] rowData;

            String category = ""; // Initialize category as an empty string

            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                category = "Electronics";   // add category to the table
                rowData = new Object[]{electronics.getProductId(), electronics.getProductName(), category, electronics.getPrice(), "Brand: " + electronics.getBrand() + ", Warranty: " + electronics.getWarranty() + " years"};
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                category = "Clothing"; // Set category to "Clothing" for clothing products
                rowData = new Object[]{clothing.getProductId(), clothing.getProductName(), category, clothing.getPrice(), "Size: " + clothing.getSize() + ", Colour: " + clothing.getColour()};
            } else {
                rowData = new Object[]{product.getProductId(), product.getProductName(), category, product.getPrice(), "Info"};
            }

            tableModel.addRow(rowData);  // add data to the table
        }
    }

    private JPanel createTextAreaPanel() {  // add the panel 3 that holds the product details
        JPanel textAreaPanel = new JPanel();
        textArea = new JTextArea(10, 40); // a text are is created.
        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane);
        return textAreaPanel;
    }

    private void displayDetailsInTextArea(int selectedRow){
        // Retrieve details from the selected row
        String productDetails = "";

        for (int i = 0; i < productTable.getColumnCount(); i++) {
            String columnName = productTable.getColumnName(i);
            String cellValue = productTable.getValueAt(selectedRow, i).toString();
            productDetails += columnName + ": " + cellValue + "\n";
        }

        // Set the details in the JTextArea
        textArea.setText(productDetails);

    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        // Create a button for adding to the shopping cart
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> {
            // Add to shopping cart functionality here
            JOptionPane.showMessageDialog(null, "Product added to the shopping cart!");
        });
        buttonPanel.add(addToCartButton);
        return buttonPanel;
    }

    private void filterAndLoadProducts(String category){
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.loadProductsFromFile();

        // filter products based on the selected category
        List<Product> filteredProducts = shoppingManager.filterProductsByCategory(category);

        //update the table with filtered products
        updateTable(filteredProducts);

    }
    private void createShoppingCartFrame() {   // creating the shopping cart frame when the user click the shopping cart button this frame is opened
        // create a new frame for the shopping cart
        JFrame shoppingCartFrame = new JFrame("shopping Cart");
        shoppingCartFrame.setSize(400, 300);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));

        //create subpanels for each section
        JPanel shoppingCartTablePanel = createShoppingCartTablePanel();
        JPanel blankPanel = createBlankPanel();

        //add subpanels to the main panel
        mainPanel.add(shoppingCartTablePanel);
        mainPanel.add(blankPanel);

        //Add the main panel to the frame
        shoppingCartFrame.getContentPane().add(mainPanel);

        //make frame visible
        shoppingCartFrame.setVisible(true);
    }

    private JPanel createShoppingCartTablePanel(){  // creating table for the shopping cart
        JPanel tablePanel = new JPanel();
        //create a table for the shopping cart
        JTable shoppingCartTable = createShoppingCartTable();
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);
        tablePanel.add(scrollPane);
        return tablePanel;
    }
    private JTable createShoppingCartTable(){
        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        return new JTable(model);
    }
    private JPanel createBlankPanel(){
        return new JPanel();
    }




    }

