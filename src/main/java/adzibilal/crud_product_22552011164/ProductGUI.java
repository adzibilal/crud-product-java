/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adzibilal.crud_product_22552011164;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author adzib
 */
public class ProductGUI extends JFrame {

    private ProductDAO productDAO;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JTextField namaField, qtyField, priceField;
    private int selectedProductId;

    public ProductGUI() {
        productDAO = new ProductDAO();

        setTitle("Tugas CRUD Product Management - 22552011164");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel Utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel Input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        JLabel nameLabel = new JLabel("Nama");
        namaField = new JTextField();
        JLabel qtyLabel = new JLabel("Qty");
        qtyField = new JTextField();
        JLabel priceLabel = new JLabel("Price");
        priceField = new JTextField();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        inputPanel.add(nameLabel);
        inputPanel.add(qtyLabel);
        inputPanel.add(priceLabel);
        inputPanel.add(namaField);
        inputPanel.add(qtyField);
        inputPanel.add(priceField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        // Panel Tabel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Qty", "Price"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Mengaktifkan seleksi baris pada tabel
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int row = productTable.getSelectedRow();
            if (row != -1) {
                selectedProductId = (int) productTable.getValueAt(row, 0);
                namaField.setText((String) productTable.getValueAt(row, 1));
                qtyField.setText(String.valueOf(productTable.getValueAt(row, 2)));
                priceField.setText(String.valueOf(productTable.getValueAt(row, 3)));
            }
        });

        // Memuat data produk dari database dan menampilkan di tabel
        loadProductData();

        // Menambahkan panel-panel ke panel utama
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Menampilkan panel utama pada frame
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void loadProductData() {
        // Membersihkan tabel sebelum memuat data
        tableModel.setRowCount(0);

        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            Object[] rowData = {product.getId(), product.getNama(), product.getQty(), product.getPrice()};
            tableModel.addRow(rowData);
        }
    }

    private void addProduct() {
        String nama = namaField.getText();
        String qtyText = qtyField.getText();
        String priceText = priceField.getText();

        if (nama.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty = Integer.parseInt(qtyText);
        double price = Double.parseDouble(priceText);

        Product product = new Product(0, nama, qty, price);
        int productId = productDAO.addProduct(product); // Mengambil ID produk yang dihasilkan

        if (productId != 0) {
            product.setId(productId); // Menetapkan ID produk yang dihasilkan ke objek produk

            Object[] rowData = {product.getId(), product.getNama(), product.getQty(), product.getPrice()};
            tableModel.addRow(rowData);

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        String nama = namaField.getText();
        String qtyText = qtyField.getText();
        String priceText = priceField.getText();

        if (nama.isEmpty() || qtyText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tolong isi semua inputan.", "Form Tidak Boleh Kosong", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty = Integer.parseInt(qtyText);
        double price = Double.parseDouble(priceText);

        Product product = new Product(selectedProductId, nama, qty, price);
        productDAO.updateProduct(product);

        int selectedRowIndex = productTable.getSelectedRow();
        tableModel.setValueAt(product.getId(), selectedRowIndex, 0);
        tableModel.setValueAt(product.getNama(), selectedRowIndex, 1);
        tableModel.setValueAt(product.getQty(), selectedRowIndex, 2);
        tableModel.setValueAt(product.getPrice(), selectedRowIndex, 3);

        clearFields();
    }

    private void deleteProduct() {
        int option = JOptionPane.showConfirmDialog(this, "Kamu Yakin Ingin Menghapus Barang Ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            productDAO.deleteProduct(selectedProductId);

            int selectedRowIndex = productTable.getSelectedRow();
            tableModel.removeRow(selectedRowIndex);

            clearFields();
        }
    }

    private void clearFields() {
        namaField.setText("");
        qtyField.setText("");
        priceField.setText("");
        productTable.clearSelection();
        selectedProductId = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductGUI();
            }
        });
    }
}
