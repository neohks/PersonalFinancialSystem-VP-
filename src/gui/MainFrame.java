/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import custom.ComBoBoxCustom;
import database.DBAccess;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.util.Rotation;
import static database.DBAccess.*;
import database.DBConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.*;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author NKS
 */
public class MainFrame extends javax.swing.JFrame {

    private int currStatus = 1; //1 - Overview, 2 - Budget, 3 - Chart, 4 - Settings
    private static String currUserCatID;
    
    public MainFrame() {
        initComponents();
        lblCurrUser.setText(DBAccess.currentUser);
            
        cboxMonth.setModel(ComBoBoxCustom.comboBox.getModel());
        
        //Update and Initialise Budget Table Row 
        refreshBudgetTable();
        
        Date date = new Date();
        datePickerBudget.setDate(date);
        datePickerBudget.setFormats("yyyy-MM-dd");
        datePickerExpenditure.setDate(date);
        datePickerExpenditure.setFormats("yyyy-MM-dd");
        
        setLocationRelativeTo(null); //Locate your app in the middle of screen
    }
    
    /*Own Methods*/
    //Switch color of side nav bar
    void switchNav(JPanel nextSelect) {
        
        switch(currStatus) {
            case 1:
                overviewPanel.setVisible(false);
                overviewSelect.setBackground(new Color(0,190,240));
                nextSelect.setBackground(new Color(0,242,252));
                setTitle("Personal Finanace System - Overview");
                break;
            case 2:
                budgetPanel.setVisible(false);
                budgetSelect.setBackground(new Color(0,190,240));
                nextSelect.setBackground(new Color(0,242,252));
                setTitle("Personal Finanace System - Budget");
                break;
            case 3:
                chartPanel.setVisible(false);
                chartSelect.setBackground(new Color(0,190,240));
                nextSelect.setBackground(new Color(0,242,252));
                setTitle("Personal Finanace System - Chart");
                break;
            case 4:
                settingsPanel.setVisible(false);
                settingsSelect.setBackground(new Color(0,190,240));
                nextSelect.setBackground(new Color(0,242,252));
                setTitle("Personal Finanace System - Settings");
                break;
            default:
              // code block
                System.out.print("Not valid selection!");
          }   
            
    }
    
    void switchPanel() {
        
        Color selectedColor = new Color(0,190,240);
        
        if (overviewSelect.getBackground() == selectedColor)
            overviewPanel.setVisible(true);
        if (budgetSelect.getBackground() == selectedColor)
            budgetPanel.setVisible(true);
        if (chartSelect.getBackground() == selectedColor)
            chartPanel.setVisible(true);
        if (settingsSelect.getBackground() == selectedColor)
            settingsPanel.setVisible(true);
        
    }
    
    void displayCharts(String btnText) {
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        pieDataset.setValue("January", 200);
        pieDataset.setValue("Febraury", 400);
        pieDataset.setValue("March", 500);

        JFreeChart pieChart = ChartFactory.createPieChart("Overview", pieDataset, true, true, Locale.ENGLISH);
        PiePlot plotPie = (PiePlot) pieChart.getPlot();
        plotPie.setStartAngle(0);
        plotPie.setDirection(Rotation.CLOCKWISE);
        plotPie.setForegroundAlpha(0.5f);

        ChartPanel piePanel = new ChartPanel(pieChart);
        panelChart.removeAll();
        panelChart.add(piePanel, BorderLayout.CENTER);
        panelChart.validate();

    }
    
    public void resetFields(){
        txtFSource.setText("");
        txtFSumBudget.setText("");
        
        Date date = new Date();
        datePickerBudget.setDate(date);
        datePickerBudget.setFormats("yyyy-MM-dd");
        datePickerExpenditure.setDate(date);
        datePickerExpenditure.setFormats("yyyy-MM-dd");
        
        txtFPurpose.setText("");
        txtFSumExpenditure.setText("");
        buttonGrpCategory.clearSelection();
    }

    void refreshBudgetTable() {
        
        DBAccess.fetchOverviewTable();
        
        tableBudget.setModel(overviewTableModel);
        
        //Let Double sort accordingly
//        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(overviewTableModel);
////        {
////            @Override
////            public boolean isSortable(int column) {
////                if(column < 3)
////                    return false;
////                else 
////                    return true;
////            }
////        
////        };
//        tableBudget.setRowSorter(sorter);
//        
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
//    
//        int columnIndexToSort = 3; //Default Sort Desc Date
//        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
//
//        sorter.setSortKeys(sortKeys);
        
        
    }
    
    void updateOverviewLabels() {
        
        labelBalanceCurrency.setText("RM"+getBalance());
        
        labelShoppingCurrency.setText("RM"+getExpensesCat("C0002"));
        labelFoodDrinksCurrency.setText("RM"+getExpensesCat("C0003"));
        labelBillsUtitlitiesCurrency.setText("RM"+getExpensesCat("C0004"));
        labelOthersCurrency.setText("RM"+getExpensesCat("C0005"));
        labelTotalCostCurrency.setText("RM"+getExpenditure());
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGrpCategory = new javax.swing.ButtonGroup();
        dialogEditBudgetTable = new javax.swing.JDialog();
        panelDialog = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnDeleteUserCat = new javax.swing.JButton();
        btnUpdateUserCat = new javax.swing.JButton();
        txtFDiaSourP = new javax.swing.JTextField();
        lblDiaCat = new javax.swing.JLabel();
        lblDiaSourP = new javax.swing.JLabel();
        txtFDiaCatName = new javax.swing.JTextField();
        lblDiaIncCos = new javax.swing.JLabel();
        lblDiaDate = new javax.swing.JLabel();
        dtPickDia = new org.jdesktop.swingx.JXDatePicker();
        spinIncCost = new javax.swing.JSpinner();
        mainPanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        overviewSelect = new javax.swing.JPanel();
        logoOverview = new javax.swing.JLabel();
        labelOverview = new javax.swing.JLabel();
        budgetSelect = new javax.swing.JPanel();
        logoBudget = new javax.swing.JLabel();
        labelBudget = new javax.swing.JLabel();
        chartSelect = new javax.swing.JPanel();
        logoChart = new javax.swing.JLabel();
        labelChart = new javax.swing.JLabel();
        settingsSelect = new javax.swing.JPanel();
        logoSettings = new javax.swing.JLabel();
        settingsLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        userPanel = new javax.swing.JPanel();
        lblUserIcon = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblCurrUser = new javax.swing.JLabel();
        layeredPanel = new javax.swing.JLayeredPane();
        overviewPanel = new javax.swing.JPanel();
        scrollBudgetTable = new javax.swing.JScrollPane();
        tableBudget = new javax.swing.JTable() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (isCellSelected(row, column)) {
                    System.out.println(row);

                }
                return c;
            }

        };
        panelBalance = new javax.swing.JPanel();
        labelBalance = new javax.swing.JLabel();
        labelBalanceCurrency = new javax.swing.JLabel();
        panelShopping = new javax.swing.JPanel();
        labelShopping = new javax.swing.JLabel();
        labelShoppingCurrency = new javax.swing.JLabel();
        panelFoodDrinks = new javax.swing.JPanel();
        labelFoodDrinks = new javax.swing.JLabel();
        labelFoodDrinksCurrency = new javax.swing.JLabel();
        panelBillsUtilities = new javax.swing.JPanel();
        labelBillsUtitlies = new javax.swing.JLabel();
        labelBillsUtitlitiesCurrency = new javax.swing.JLabel();
        panelOthers = new javax.swing.JPanel();
        labelOthers = new javax.swing.JLabel();
        labelOthersCurrency = new javax.swing.JLabel();
        labelTotalCost = new javax.swing.JLabel();
        labelTotalCostCurrency = new javax.swing.JLabel();
        budgetPanel = new javax.swing.JPanel();
        addExpenditurePanel = new javax.swing.JPanel();
        lblPurpose = new javax.swing.JLabel();
        txtFPurpose = new javax.swing.JTextField();
        txtFSumExpenditure = new javax.swing.JTextField();
        lblSumExpenditure = new javax.swing.JLabel();
        lblDateExpenditure = new javax.swing.JLabel();
        datePickerExpenditure = new org.jdesktop.swingx.JXDatePicker();
        radioBtnShopping = new javax.swing.JRadioButton();
        radioBtnFD = new javax.swing.JRadioButton();
        radioBtnBU = new javax.swing.JRadioButton();
        radioBtnOthers = new javax.swing.JRadioButton();
        btnSubmitExpen = new javax.swing.JButton();
        lblCategory = new javax.swing.JLabel();
        addBudgetPanel = new javax.swing.JPanel();
        lblSource = new javax.swing.JLabel();
        lblSumBudget = new javax.swing.JLabel();
        lblDateBudget = new javax.swing.JLabel();
        txtFSource = new javax.swing.JTextField();
        txtFSumBudget = new javax.swing.JTextField();
        btnSubmitBudget = new javax.swing.JButton();
        datePickerBudget = new org.jdesktop.swingx.JXDatePicker();
        chartPanel = new javax.swing.JPanel();
        panelChart = new javax.swing.JPanel();
        panelSelection = new javax.swing.JPanel();
        btnShow = new javax.swing.JButton();
        cboxMonth = new javax.swing.JComboBox<>();
        lblMonth = new javax.swing.JLabel();
        lblYear = new javax.swing.JLabel();
        cboxYear = new javax.swing.JComboBox<>();
        settingsPanel = new javax.swing.JPanel();
        panelEditPass = new javax.swing.JPanel();
        lblOldPassword = new javax.swing.JLabel();
        passFOldPass = new javax.swing.JPasswordField();
        passFNewPass = new javax.swing.JPasswordField();
        lblNewPassword = new javax.swing.JLabel();
        btnSubmitPass = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();

        dialogEditBudgetTable.setTitle("Edit Table");
        dialogEditBudgetTable.setMinimumSize(new java.awt.Dimension(610, 590));
        dialogEditBudgetTable.setPreferredSize(new java.awt.Dimension(610, 595));
        dialogEditBudgetTable.setResizable(false);
        dialogEditBudgetTable.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                dialogEditBudgetTableWindowLostFocus(evt);
            }
        });
        dialogEditBudgetTable.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dialogEditBudgetTableWindowClosing(evt);
            }
        });

        panelDialog.setBackground(new java.awt.Color(136, 239, 222));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDeleteUserCat.setText("Delete");
        btnDeleteUserCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserCatActionPerformed(evt);
            }
        });

        btnUpdateUserCat.setText("Update");
        btnUpdateUserCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateUserCatActionPerformed(evt);
            }
        });

        txtFDiaSourP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblDiaCat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiaCat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiaCat.setText("Category :");

        lblDiaSourP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiaSourP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiaSourP.setText("Source/Purpose : ");

        txtFDiaCatName.setBackground(new java.awt.Color(204, 204, 204));
        txtFDiaCatName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtFDiaCatName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFDiaCatName.setEnabled(false);

        lblDiaIncCos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiaIncCos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiaIncCos.setText("Income/Cost : ");

        lblDiaDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiaDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiaDate.setText("Date : ");

        javax.swing.GroupLayout panelDialogLayout = new javax.swing.GroupLayout(panelDialog);
        panelDialog.setLayout(panelDialogLayout);
        panelDialogLayout.setHorizontalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelDialogLayout.createSequentialGroup()
                            .addComponent(lblDiaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtFDiaCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                            .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblDiaIncCos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblDiaDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dtPickDia, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spinIncCost, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDialogLayout.createSequentialGroup()
                        .addComponent(lblDiaSourP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFDiaSourP, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDialogLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateUserCat, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteUserCat, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addGap(66, 66, 66))
        );
        panelDialogLayout.setVerticalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFDiaCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaSourP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFDiaSourP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaIncCos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinIncCost, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtPickDia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteUserCat)
                    .addComponent(btnUpdateUserCat)
                    .addComponent(btnCancel))
                .addGap(61, 61, 61))
        );

        txtFDiaCatName.setDisabledTextColor(Color.DARK_GRAY);

        javax.swing.GroupLayout dialogEditBudgetTableLayout = new javax.swing.GroupLayout(dialogEditBudgetTable.getContentPane());
        dialogEditBudgetTable.getContentPane().setLayout(dialogEditBudgetTableLayout);
        dialogEditBudgetTableLayout.setHorizontalGroup(
            dialogEditBudgetTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogEditBudgetTableLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        dialogEditBudgetTableLayout.setVerticalGroup(
            dialogEditBudgetTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogEditBudgetTableLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Personal Finanace System - Overview");
        setName("mainFrame"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainPanel.setPreferredSize(new java.awt.Dimension(930, 560));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidePanel.setBackground(new java.awt.Color(83, 154, 231));

        overviewSelect.setBackground(new java.awt.Color(0, 242, 252));
        overviewSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                overviewSelectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                overviewSelectMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                overviewSelectMousePressed(evt);
            }
        });

        logoOverview.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logoOverview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoOverview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vectors/overview-35.png"))); // NOI18N

        labelOverview.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelOverview.setForeground(new java.awt.Color(30, 33, 122));
        labelOverview.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelOverview.setText("Overview");

        javax.swing.GroupLayout overviewSelectLayout = new javax.swing.GroupLayout(overviewSelect);
        overviewSelect.setLayout(overviewSelectLayout);
        overviewSelectLayout.setHorizontalGroup(
            overviewSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overviewSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoOverview, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOverview, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        overviewSelectLayout.setVerticalGroup(
            overviewSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(overviewSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(overviewSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelOverview, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoOverview, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        budgetSelect.setBackground(new java.awt.Color(0, 190, 240));
        budgetSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                budgetSelectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                budgetSelectMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                budgetSelectMousePressed(evt);
            }
        });

        logoBudget.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logoBudget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoBudget.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vectors/budget-35.png"))); // NOI18N

        labelBudget.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelBudget.setForeground(new java.awt.Color(30, 33, 122));
        labelBudget.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelBudget.setText("Budget");

        javax.swing.GroupLayout budgetSelectLayout = new javax.swing.GroupLayout(budgetSelect);
        budgetSelect.setLayout(budgetSelectLayout);
        budgetSelectLayout.setHorizontalGroup(
            budgetSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        budgetSelectLayout.setVerticalGroup(
            budgetSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(budgetSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chartSelect.setBackground(new java.awt.Color(0, 190, 240));
        chartSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chartSelectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chartSelectMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                chartSelectMousePressed(evt);
            }
        });

        logoChart.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logoChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vectors/chart-35.png"))); // NOI18N

        labelChart.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelChart.setForeground(new java.awt.Color(30, 33, 122));
        labelChart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelChart.setText("Charts");

        javax.swing.GroupLayout chartSelectLayout = new javax.swing.GroupLayout(chartSelect);
        chartSelect.setLayout(chartSelectLayout);
        chartSelectLayout.setHorizontalGroup(
            chartSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoChart, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelChart, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        chartSelectLayout.setVerticalGroup(
            chartSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chartSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelChart, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoChart, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsSelect.setBackground(new java.awt.Color(0, 190, 240));
        settingsSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsSelectMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsSelectMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                settingsSelectMousePressed(evt);
            }
        });

        logoSettings.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logoSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vectors/settings-35.png"))); // NOI18N

        settingsLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        settingsLabel.setForeground(new java.awt.Color(30, 33, 122));
        settingsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        settingsLabel.setText("Settings");

        javax.swing.GroupLayout settingsSelectLayout = new javax.swing.GroupLayout(settingsSelect);
        settingsSelect.setLayout(settingsSelectLayout);
        settingsSelectLayout.setHorizontalGroup(
            settingsSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        settingsSelectLayout.setVerticalGroup(
            settingsSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settingsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userPanel.setBackground(new java.awt.Color(0, 204, 204));
        userPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        userPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUserIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vectors/account.png"))); // NOI18N
        userPanel.add(lblUserIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 156, -1));

        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Welcome");
        userPanel.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 136, 22));

        lblCurrUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userPanel.add(lblCurrUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 136, 22));

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(overviewSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(budgetSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(overviewSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(budgetSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(chartSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(settingsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        mainPanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 530));

        layeredPanel.setLayout(new java.awt.CardLayout());

        overviewPanel.setBackground(new java.awt.Color(255, 255, 255));
        overviewPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableBudget.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Source/Purpose", "Category", "Income/Cost", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableBudget.setEnabled(false);
        tableBudget.getTableHeader().setReorderingAllowed(false);
        tableBudget.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBudgetMouseClicked(evt);
            }
        });
        scrollBudgetTable.setViewportView(tableBudget);
        //Allow column sort
        //tableBudget.setAutoCreateRowSorter(true);

        overviewPanel.add(scrollBudgetTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 130, 570, 340));

        panelBalance.setBackground(new java.awt.Color(233, 251, 255));
        panelBalance.setForeground(new java.awt.Color(30, 33, 122));

        labelBalance.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelBalance.setForeground(new java.awt.Color(30, 33, 122));
        labelBalance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBalance.setText("Balance");

        labelBalanceCurrency.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelBalanceCurrency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBalanceCurrency.setText("$$$");

        javax.swing.GroupLayout panelBalanceLayout = new javax.swing.GroupLayout(panelBalance);
        panelBalance.setLayout(panelBalanceLayout);
        panelBalanceLayout.setHorizontalGroup(
            panelBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelBalanceCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBalanceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(199, 199, 199))
        );
        panelBalanceLayout.setVerticalGroup(
            panelBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelBalanceCurrency)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        labelBalanceCurrency.setText("RM"+getBalance());

        overviewPanel.add(panelBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 14, 570, 100));

        panelShopping.setBackground(new java.awt.Color(233, 251, 255));

        labelShopping.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelShopping.setForeground(new java.awt.Color(30, 33, 122));
        labelShopping.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelShopping.setText("Shopping");

        labelShoppingCurrency.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelShoppingCurrency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelShoppingCurrency.setText("$$$");

        javax.swing.GroupLayout panelShoppingLayout = new javax.swing.GroupLayout(panelShopping);
        panelShopping.setLayout(panelShoppingLayout);
        panelShoppingLayout.setHorizontalGroup(
            panelShoppingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelShoppingCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelShopping, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelShoppingLayout.setVerticalGroup(
            panelShoppingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShoppingLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelShopping)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelShoppingCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        labelShoppingCurrency.setText("RM"+getExpensesCat("C0002"));

        overviewPanel.add(panelShopping, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 14, 180, -1));

        panelFoodDrinks.setBackground(new java.awt.Color(233, 251, 255));

        labelFoodDrinks.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelFoodDrinks.setForeground(new java.awt.Color(30, 33, 122));
        labelFoodDrinks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFoodDrinks.setText("Food & Drinks");

        labelFoodDrinksCurrency.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelFoodDrinksCurrency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFoodDrinksCurrency.setText("$$$");

        javax.swing.GroupLayout panelFoodDrinksLayout = new javax.swing.GroupLayout(panelFoodDrinks);
        panelFoodDrinks.setLayout(panelFoodDrinksLayout);
        panelFoodDrinksLayout.setHorizontalGroup(
            panelFoodDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelFoodDrinks, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addComponent(labelFoodDrinksCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelFoodDrinksLayout.setVerticalGroup(
            panelFoodDrinksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFoodDrinksLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelFoodDrinks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFoodDrinksCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        labelFoodDrinksCurrency.setText("RM"+getExpensesCat("C0003"));

        overviewPanel.add(panelFoodDrinks, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 132, 180, -1));

        panelBillsUtilities.setBackground(new java.awt.Color(233, 251, 255));

        labelBillsUtitlies.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelBillsUtitlies.setForeground(new java.awt.Color(30, 33, 122));
        labelBillsUtitlies.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBillsUtitlies.setText("Bills & Utilities");

        labelBillsUtitlitiesCurrency.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelBillsUtitlitiesCurrency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBillsUtitlitiesCurrency.setText("$$$");

        javax.swing.GroupLayout panelBillsUtilitiesLayout = new javax.swing.GroupLayout(panelBillsUtilities);
        panelBillsUtilities.setLayout(panelBillsUtilitiesLayout);
        panelBillsUtilitiesLayout.setHorizontalGroup(
            panelBillsUtilitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBillsUtitlies, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addComponent(labelBillsUtitlitiesCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBillsUtilitiesLayout.setVerticalGroup(
            panelBillsUtilitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBillsUtilitiesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelBillsUtitlies)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelBillsUtitlitiesCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        labelBillsUtitlitiesCurrency.setText("RM"+getExpensesCat("C0004"));

        overviewPanel.add(panelBillsUtilities, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 250, 180, -1));

        panelOthers.setBackground(new java.awt.Color(233, 251, 255));

        labelOthers.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelOthers.setForeground(new java.awt.Color(30, 33, 122));
        labelOthers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelOthers.setText("Others");

        labelOthersCurrency.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelOthersCurrency.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelOthersCurrency.setText("$$$");

        javax.swing.GroupLayout panelOthersLayout = new javax.swing.GroupLayout(panelOthers);
        panelOthers.setLayout(panelOthersLayout);
        panelOthersLayout.setHorizontalGroup(
            panelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelOthersCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelOthers, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        panelOthersLayout.setVerticalGroup(
            panelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOthersLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(labelOthers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOthersCurrency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        labelOthersCurrency.setText("RM"+getExpensesCat("C0005"));

        overviewPanel.add(panelOthers, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 368, 180, 102));

        labelTotalCost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalCost.setText("Total Cost :");
        overviewPanel.add(labelTotalCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, 86, 26));

        labelTotalCostCurrency.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelTotalCostCurrency.setText("$$$");
        overviewPanel.add(labelTotalCostCurrency, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 86, 26));
        labelTotalCostCurrency.setText("RM"+getExpenditure());

        layeredPanel.add(overviewPanel, "card3");

        budgetPanel.setBackground(new java.awt.Color(255, 255, 255));

        addExpenditurePanel.setBackground(new java.awt.Color(233, 251, 255));
        addExpenditurePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Add Expenditure", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(30, 33, 122))); // NOI18N

        lblPurpose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPurpose.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPurpose.setText("Purpose : ");

        lblSumExpenditure.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSumExpenditure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSumExpenditure.setText("Sum : ");

        lblDateExpenditure.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDateExpenditure.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDateExpenditure.setText("Date : ");

        radioBtnShopping.setBackground(new java.awt.Color(233, 251, 255));
        buttonGrpCategory.add(radioBtnShopping);
        radioBtnShopping.setText("Shopping");
        radioBtnShopping.setOpaque(false);

        radioBtnFD.setBackground(new java.awt.Color(233, 251, 255));
        buttonGrpCategory.add(radioBtnFD);
        radioBtnFD.setText("Food & Drinks");
        radioBtnFD.setOpaque(false);

        radioBtnBU.setBackground(new java.awt.Color(233, 251, 255));
        buttonGrpCategory.add(radioBtnBU);
        radioBtnBU.setText("Bills & Utilities");
        radioBtnBU.setOpaque(false);

        radioBtnOthers.setBackground(new java.awt.Color(233, 251, 255));
        buttonGrpCategory.add(radioBtnOthers);
        radioBtnOthers.setText("Others");
        radioBtnOthers.setOpaque(false);

        btnSubmitExpen.setText("Submit");
        btnSubmitExpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitExpenActionPerformed(evt);
            }
        });

        lblCategory.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCategory.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCategory.setText("Category : ");

        javax.swing.GroupLayout addExpenditurePanelLayout = new javax.swing.GroupLayout(addExpenditurePanel);
        addExpenditurePanel.setLayout(addExpenditurePanelLayout);
        addExpenditurePanelLayout.setHorizontalGroup(
            addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addExpenditurePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSubmitExpen)
                .addGap(138, 138, 138))
            .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                        .addComponent(lblPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                        .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addExpenditurePanelLayout.createSequentialGroup()
                                .addComponent(lblSumExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFSumExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addExpenditurePanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                                        .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(radioBtnFD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(radioBtnShopping, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(radioBtnBU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(radioBtnOthers, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                                        .addComponent(lblDateExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)
                                        .addComponent(datePickerExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(27, Short.MAX_VALUE))))
        );
        addExpenditurePanelLayout.setVerticalGroup(
            addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFSumExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSumExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePickerExpenditure, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addExpenditurePanelLayout.createSequentialGroup()
                        .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioBtnShopping)
                            .addComponent(radioBtnBU))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(addExpenditurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioBtnFD)
                            .addComponent(radioBtnOthers))))
                .addGap(30, 30, 30)
                .addComponent(btnSubmitExpen)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addBudgetPanel.setBackground(new java.awt.Color(233, 251, 255));
        addBudgetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Add Deposit", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(30, 33, 122))); // NOI18N

        lblSource.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSource.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSource.setText("Source : ");

        lblSumBudget.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSumBudget.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSumBudget.setText("Sum : ");

        lblDateBudget.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDateBudget.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDateBudget.setText("Date : ");

        btnSubmitBudget.setText("Submit");
        btnSubmitBudget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitBudgetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addBudgetPanelLayout = new javax.swing.GroupLayout(addBudgetPanel);
        addBudgetPanel.setLayout(addBudgetPanelLayout);
        addBudgetPanelLayout.setHorizontalGroup(
            addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addComponent(lblSource, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(txtFSource))
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addComponent(lblSumBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(txtFSumBudget))
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addComponent(lblDateBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addBudgetPanelLayout.createSequentialGroup()
                                .addComponent(btnSubmitBudget)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(datePickerBudget, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))
                .addGap(19, 19, 19))
        );
        addBudgetPanelLayout.setVerticalGroup(
            addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSource, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFSource, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSumBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFSumBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePickerBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnSubmitBudget)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout budgetPanelLayout = new javax.swing.GroupLayout(budgetPanel);
        budgetPanel.setLayout(budgetPanelLayout);
        budgetPanelLayout.setHorizontalGroup(
            budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, budgetPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(addBudgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(addExpenditurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        budgetPanelLayout.setVerticalGroup(
            budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(budgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addExpenditurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBudgetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        layeredPanel.add(budgetPanel, "card4");

        chartPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelChart.setBackground(new java.awt.Color(204, 255, 255));
        panelChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelChart.setLayout(new java.awt.BorderLayout());

        panelSelection.setBackground(new java.awt.Color(136, 239, 222));
        panelSelection.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnShow.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnShow.setText("Show");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        cboxMonth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April" }));
        cboxMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxMonthActionPerformed(evt);
            }
        });

        lblMonth.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonth.setText("Month : ");

        lblYear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblYear.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblYear.setText("Year : ");

        cboxYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020", "2021", "2022" }));
        cboxYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSelectionLayout = new javax.swing.GroupLayout(panelSelection);
        panelSelection.setLayout(panelSelectionLayout);
        panelSelectionLayout.setHorizontalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelSelectionLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(lblMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126))
        );
        panelSelectionLayout.setVerticalGroup(
            panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelectionLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(cboxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSelection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        layeredPanel.add(chartPanel, "card2");

        settingsPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelEditPass.setBackground(new java.awt.Color(233, 251, 255));
        panelEditPass.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Change Password", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        panelEditPass.setPreferredSize(new java.awt.Dimension(355, 280));

        lblOldPassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOldPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOldPassword.setText("Old Password : ");

        passFNewPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFNewPassActionPerformed(evt);
            }
        });

        lblNewPassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNewPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNewPassword.setText("New Password : ");

        btnSubmitPass.setText("Submit");
        btnSubmitPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEditPassLayout = new javax.swing.GroupLayout(panelEditPass);
        panelEditPass.setLayout(panelEditPassLayout);
        panelEditPassLayout.setHorizontalGroup(
            panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditPassLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditPassLayout.createSequentialGroup()
                        .addGroup(panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passFOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditPassLayout.createSequentialGroup()
                        .addComponent(btnSubmitPass)
                        .addGap(138, 138, 138))))
        );
        panelEditPassLayout.setVerticalGroup(
            panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditPassLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passFOldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(panelEditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passFNewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnSubmitPass)
                .addGap(39, 39, 39))
        );

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(panelEditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(settingsPanelLayout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(btnLogout)))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        settingsPanelLayout.setVerticalGroup(
            settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsPanelLayout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(panelEditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnLogout)
                .addGap(54, 54, 54))
        );

        layeredPanel.add(settingsPanel, "card5");

        mainPanel.add(layeredPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 820, 530));

        bottomPanel.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        mainPanel.add(bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 1000, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void budgetSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_budgetSelectMousePressed
        // TODO add your handling code here:
        switchNav(budgetSelect);
        budgetPanel.setVisible(true);
        
        currStatus = 2;
    }//GEN-LAST:event_budgetSelectMousePressed

    private void budgetSelectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_budgetSelectMouseEntered
        // TODO add your handling code here:
        if (currStatus != 2)
            budgetSelect.setBackground(new Color(0,242,252));
        
    }//GEN-LAST:event_budgetSelectMouseEntered

    private void budgetSelectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_budgetSelectMouseExited
        // TODO add your handling code here:
        if (currStatus != 2)
            budgetSelect.setBackground(new Color(0,190,240));
    }//GEN-LAST:event_budgetSelectMouseExited

    private void chartSelectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chartSelectMouseEntered
        // TODO add your handling code here:
        if (currStatus != 3)
            chartSelect.setBackground(new Color(0,242,252));
    }//GEN-LAST:event_chartSelectMouseEntered

    private void chartSelectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chartSelectMouseExited
        // TODO add your handling code here:
        if (currStatus != 3)
            chartSelect.setBackground(new Color(0,190,240));
    }//GEN-LAST:event_chartSelectMouseExited

    private void settingsSelectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsSelectMouseEntered
        // TODO add your handling code here:
        if (currStatus != 4)
            settingsSelect.setBackground(new Color(0,242,252));
    }//GEN-LAST:event_settingsSelectMouseEntered

    private void settingsSelectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsSelectMouseExited
        // TODO add your handling code here:
        if (currStatus != 4)
            settingsSelect.setBackground(new Color(0,190,240));
    }//GEN-LAST:event_settingsSelectMouseExited

    private void overviewSelectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_overviewSelectMouseEntered
        // TODO add your handling code here:
        if (currStatus != 1)
            overviewSelect.setBackground(new Color(0,242,252));
    }//GEN-LAST:event_overviewSelectMouseEntered

    private void overviewSelectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_overviewSelectMouseExited
        // TODO add your handling code here:
        if (currStatus != 1)
            overviewSelect.setBackground(new Color(0,190,240));
    }//GEN-LAST:event_overviewSelectMouseExited

    private void overviewSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_overviewSelectMousePressed
        // TODO add your handling code here:
        
        switchNav(overviewSelect);

        updateOverviewLabels();
        
        overviewPanel.setVisible(true);
        currStatus = 1;
        
        
    }//GEN-LAST:event_overviewSelectMousePressed

    private void chartSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chartSelectMousePressed
        // TODO add your handling code here:
        switchNav(chartSelect);
        currStatus = 3;
        
        chartPanel.setVisible(true);
    }//GEN-LAST:event_chartSelectMousePressed

    private void settingsSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsSelectMousePressed
        // TODO add your handling code here:
        switchNav(settingsSelect);
        currStatus = 4;
        
        settingsPanel.setVisible(true);
    }//GEN-LAST:event_settingsSelectMousePressed

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        String month = (String)cboxMonth.getSelectedItem();
        String year = (String)cboxYear.getSelectedItem();
        
//        if(getExpensesCat("C0001",month,year)!=0)
//        {
//            pieDataset.setValue("Deposit", getExpensesCat("C0001",month,year));
//        }
            
        if(getExpensesCat("C0002",month,year)!=0)  
        {
            pieDataset.setValue("Shop", getExpensesCat("C0002",month,year));
        }

        if(getExpensesCat("C0003",month,year)!=0)  
        {
            pieDataset.setValue("Food Drinks", getExpensesCat("C0003",month,year));
        }

        if(getExpensesCat("C0004",month,year)!=0)  
        {
            pieDataset.setValue("Bills Utilities", getExpensesCat("C0004",month,year));
        }

        if(getExpensesCat("C0005",month,year)!=0)  
        {
            pieDataset.setValue("Others", getExpensesCat("C0005",month,year));
        }
        
        double sum = 0;
        for (int i = 0; i < pieDataset.getItemCount(); i++) {
            sum += (double)pieDataset.getValue(i);
        
        }
        
        //If all data is 0.0, this msg will prompt out
        if (sum == 0.0) {
            
            JOptionPane.showMessageDialog(new JFrame(), "No data found. Please reselect the month and year!");
            return; 
        }
        
        
        
        JFreeChart pieChart = ChartFactory.createPieChart("Monthly Expenses (RM)", pieDataset, true, true, Locale.ENGLISH);
        PiePlot plotPie = (PiePlot) pieChart.getPlot();
        plotPie.setStartAngle(0);
        plotPie.setDirection(Rotation.CLOCKWISE);
        plotPie.setForegroundAlpha(0.5f);

        ChartPanel piePanel = new ChartPanel(pieChart);
        panelChart.removeAll();
        panelChart.add(piePanel, BorderLayout.CENTER);
        panelChart.validate();
            
        
        
        
        
    }//GEN-LAST:event_btnShowActionPerformed

    private void passFNewPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFNewPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passFNewPassActionPerformed
    
    private void btnSubmitBudgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitBudgetActionPerformed
        // TODO add your handling code here:
        String source;
        double budget=0.00;
        Date date;
        source = txtFSource.getText();
        
        
        if (txtFSource.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please do not leave blank at Source!");
            return;
        }
        
        if (txtFSumBudget.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please do not leave blank at Budget Sum!");
            return;
        }
        
        try{
            budget = Double.parseDouble(txtFSumBudget.getText());
            try{
                date = datePickerBudget.getDate();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(date);
                
                DBAccess.insertBudget(source, budget, dateStr);

                refreshBudgetTable();
                resetFields();
                JOptionPane.showMessageDialog(new JFrame(), "Successfully submitted!");
                
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Please enter a valid date!", "Invalid Date", JOptionPane.WARNING_MESSAGE);
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter numerical value only!", "Invalid Budget", JOptionPane.WARNING_MESSAGE);
            //e.printStackTrace();
        }catch(Exception exceptions){
            exceptions.printStackTrace();
        }
        
    }//GEN-LAST:event_btnSubmitBudgetActionPerformed

    private void btnSubmitExpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitExpenActionPerformed
        // TODO add your handling code here:
        String purpose, category;
        double expenses;
        Date date;
        
        purpose = txtFPurpose.getText();
        
        if (txtFPurpose.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please do not leave blank at Source!");
            return;
        }
        
        if (txtFSumExpenditure.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Please do not leave blank at Budget Sum!");
            return;
        }
        if (!radioBtnShopping.isSelected() && !radioBtnFD.isSelected() 
                && !radioBtnBU.isSelected() && !radioBtnOthers.isSelected()) {
            
            JOptionPane.showMessageDialog(new JFrame(), "Please do not leave blank at Category!");
            return;
            
        }
        
        try{
            expenses = Double.parseDouble(txtFSumExpenditure.getText());
            try{
                date = datePickerExpenditure.getDate();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(date);
                
                if(radioBtnShopping.isSelected()){
                    category = "C0002";
                }else if(radioBtnFD.isSelected()){
                    category = "C0003";
                }else if(radioBtnBU.isSelected()){
                    category = "C0004";
                }else{
                    category = "C0005";
                }
               
                DBAccess.insertExpenditure(purpose, expenses, dateStr, category);
                
                //Update Budget Table Row
                refreshBudgetTable();
                resetFields();
                JOptionPane.showMessageDialog(new JFrame(), "Successfully submitted!");
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please enter a valid date!", "Invalid Date", JOptionPane.WARNING_MESSAGE);
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please enter numerical value only!", "Invalid Budget", JOptionPane.WARNING_MESSAGE);
            //e.printStackTrace();
        }catch(Exception exceptions){
            
        }
        
    }//GEN-LAST:event_btnSubmitExpenActionPerformed

    private void btnSubmitPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitPassActionPerformed
        // TODO add your handling code here:
        
        String pwOld = new String(passFOldPass.getPassword());
        String pwNew = new String (passFNewPass.getPassword());
        
        if(pwOld.equals(pwNew)){
//            System.out.println("New password cannot be same as old password!");
            JOptionPane.showMessageDialog(new JFrame(), "Old Password and New Password are the same! \nPlease check your password fields again.");
        }else{
            DBAccess.changePW(pwNew, pwOld);
            //boop - hanyangxd
        }
        
    }//GEN-LAST:event_btnSubmitPassActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        dispose();
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tableBudgetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBudgetMouseClicked
       
        int row = tableBudget.rowAtPoint(evt.getPoint());
       
        String[] dataRow = new String[4];
        
        if (evt.getClickCount() == 2) {
            try {
                
                //get each column data/value
                for(int x = 0; x < 4; x++) {
                    
                    String value = tableBudget.getValueAt(row, x).toString();
                    dataRow[x] = value;
                }
                
                txtFDiaCatName.setText(dataRow[1]);
                txtFDiaSourP.setText(dataRow[0]);
                spinIncCost.setValue(Double.parseDouble(dataRow[2]));
                
                Date dataDate = new SimpleDateFormat("yyyy-MM-dd").parse(dataRow[3]);
                dtPickDia.setDate(dataDate);
                
                currUserCatID = DBAccess.listUserCatID.get(row);
                
                System.out.println(row);
                
                
                this.setEnabled(false);
                dialogEditBudgetTable.setVisible(true);
                dialogEditBudgetTable.setLocationRelativeTo(null);
                dialogEditBudgetTable.setTitle("Manage " + txtFDiaCatName.getText() + " Table");
                
            } catch (ParseException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
            
    }//GEN-LAST:event_tableBudgetMouseClicked

    private void cboxMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboxMonthActionPerformed

    private void cboxYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboxYearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        DBConnection.close();
        System.out.println("Closing database connections");
        
    }//GEN-LAST:event_formWindowClosing

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:

        this.setEnabled(true);
        dialogEditBudgetTable.dispose();

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteUserCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserCatActionPerformed
        // TODO add your handling code here:

        DBAccess.deleteBudgetTableRowValue(currUserCatID);

        this.setEnabled(true);
        refreshBudgetTable();
        updateOverviewLabels();
        dialogEditBudgetTable.dispose();
    }//GEN-LAST:event_btnDeleteUserCatActionPerformed

    private void btnUpdateUserCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateUserCatActionPerformed
        // TODO add your handling code here:

        //Update data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(dtPickDia.getDate());

            
        Double incCost = Double.parseDouble(spinIncCost.getValue().toString());    
        
        //Ensure that categories value are +iv and -ive accordingly
        if ("Others".equals(txtFDiaCatName.getText()) || "Shop".equals(txtFDiaCatName.getText()) || ("Bills Utilities").equals(txtFDiaCatName.getText()) || ("Food Drinks").equals(txtFDiaCatName.getText()) )
            if (incCost > 0.0) {
                incCost *= -1;
            }
        if ("Deposit".equals(txtFDiaCatName.getText()))
            if (incCost < 0.0) {
                incCost *= -1;
            }
        
        DBAccess.updateBudgetTableRowValue(currUserCatID, txtFDiaSourP.getText(),
                incCost, dateStr);

        this.setEnabled(true);
        refreshBudgetTable();
        updateOverviewLabels();
        dialogEditBudgetTable.dispose();
    }//GEN-LAST:event_btnUpdateUserCatActionPerformed

    private void dialogEditBudgetTableWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogEditBudgetTableWindowClosing
        // TODO add your handling code here:
        this.setEnabled(true);
    }//GEN-LAST:event_dialogEditBudgetTableWindowClosing

    private void dialogEditBudgetTableWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dialogEditBudgetTableWindowLostFocus
        // TODO add your handling code here:
        dialogEditBudgetTable.requestFocus();
        
    }//GEN-LAST:event_dialogEditBudgetTableWindowLostFocus
    
    
    
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBudgetPanel;
    private javax.swing.JPanel addExpenditurePanel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDeleteUserCat;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnShow;
    private javax.swing.JButton btnSubmitBudget;
    private javax.swing.JButton btnSubmitExpen;
    private javax.swing.JButton btnSubmitPass;
    private javax.swing.JButton btnUpdateUserCat;
    private javax.swing.JPanel budgetPanel;
    private javax.swing.JPanel budgetSelect;
    private javax.swing.ButtonGroup buttonGrpCategory;
    private javax.swing.JComboBox<String> cboxMonth;
    private javax.swing.JComboBox<String> cboxYear;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JPanel chartSelect;
    private org.jdesktop.swingx.JXDatePicker datePickerBudget;
    private org.jdesktop.swingx.JXDatePicker datePickerExpenditure;
    private javax.swing.JDialog dialogEditBudgetTable;
    private org.jdesktop.swingx.JXDatePicker dtPickDia;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelBalance;
    private javax.swing.JLabel labelBalanceCurrency;
    private javax.swing.JLabel labelBillsUtitlies;
    private javax.swing.JLabel labelBillsUtitlitiesCurrency;
    private javax.swing.JLabel labelBudget;
    private javax.swing.JLabel labelChart;
    private javax.swing.JLabel labelFoodDrinks;
    private javax.swing.JLabel labelFoodDrinksCurrency;
    private javax.swing.JLabel labelOthers;
    private javax.swing.JLabel labelOthersCurrency;
    private javax.swing.JLabel labelOverview;
    private javax.swing.JLabel labelShopping;
    private javax.swing.JLabel labelShoppingCurrency;
    private javax.swing.JLabel labelTotalCost;
    private javax.swing.JLabel labelTotalCostCurrency;
    private javax.swing.JLayeredPane layeredPanel;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblCurrUser;
    private javax.swing.JLabel lblDateBudget;
    private javax.swing.JLabel lblDateExpenditure;
    private javax.swing.JLabel lblDiaCat;
    private javax.swing.JLabel lblDiaDate;
    private javax.swing.JLabel lblDiaIncCos;
    private javax.swing.JLabel lblDiaSourP;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblNewPassword;
    private javax.swing.JLabel lblOldPassword;
    private javax.swing.JLabel lblPurpose;
    private javax.swing.JLabel lblSource;
    private javax.swing.JLabel lblSumBudget;
    private javax.swing.JLabel lblSumExpenditure;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel logoBudget;
    private javax.swing.JLabel logoChart;
    private javax.swing.JLabel logoOverview;
    private javax.swing.JLabel logoSettings;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel overviewPanel;
    private javax.swing.JPanel overviewSelect;
    private javax.swing.JPanel panelBalance;
    private javax.swing.JPanel panelBillsUtilities;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelDialog;
    private javax.swing.JPanel panelEditPass;
    private javax.swing.JPanel panelFoodDrinks;
    private javax.swing.JPanel panelOthers;
    private javax.swing.JPanel panelSelection;
    private javax.swing.JPanel panelShopping;
    private javax.swing.JPasswordField passFNewPass;
    private javax.swing.JPasswordField passFOldPass;
    private javax.swing.JRadioButton radioBtnBU;
    private javax.swing.JRadioButton radioBtnFD;
    private javax.swing.JRadioButton radioBtnOthers;
    private javax.swing.JRadioButton radioBtnShopping;
    private javax.swing.JScrollPane scrollBudgetTable;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel settingsSelect;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JSpinner spinIncCost;
    private javax.swing.JTable tableBudget;
    private javax.swing.JTextField txtFDiaCatName;
    private javax.swing.JTextField txtFDiaSourP;
    private javax.swing.JTextField txtFPurpose;
    private javax.swing.JTextField txtFSource;
    private javax.swing.JTextField txtFSumBudget;
    private javax.swing.JTextField txtFSumExpenditure;
    private javax.swing.JPanel userPanel;
    // End of variables declaration//GEN-END:variables
}
