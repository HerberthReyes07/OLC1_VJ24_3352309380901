/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import exceptions.Error;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author herberthreyes
 */
public class ErrorTable extends javax.swing.JFrame {

    /**
     * Creates new form ErrorTable
     */
    private DefaultTableModel model;
    private ArrayList<String> descriptions = new ArrayList<>();

    public ErrorTable(LinkedList<Error> lexErrors, LinkedList<Error> syntaxErrors, LinkedList<Error> semanticErrors, String fileName) {
        initComponents();
        jLabel1.setText("Tabla de Errores: " + fileName);
        model = (DefaultTableModel) jTable1.getModel();
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(250);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(15);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(15);
        
        int num = 1;
        for (var a : lexErrors) {
            model.addRow(new Object[]{
                num,
                a.getType(),
                a.getDescription(),
                a.getLine(),
                a.getColumn()
            });
            descriptions.add(a.getDescription());
            num++;
        }
        //errores sintacticos
        for (var a : syntaxErrors) {
            model.addRow(new Object[]{
                num,
                a.getType(),
                a.getDescription(),
                a.getLine(),
                a.getColumn()
            });
            descriptions.add(a.getDescription());
            num++;
        }
        //errores semanticos
        for (var a : semanticErrors) {
            model.addRow(new Object[]{
                num,
                a.getType(),
                a.getDescription(),
                a.getLine(),
                a.getColumn()
            });
            descriptions.add(a.getDescription());
            num++;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("Tabla de errores");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Tipo", "Descripción", "Línea", "Columna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
        if (jTable1.getSelectedColumn() == 2) {
            int errorIndex = jTable1.getSelectedRow();
            JOptionPane.showMessageDialog(null, "Error #" + (errorIndex + 1) + ":\n" + descriptions.get(errorIndex), "Descripción", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jTable1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
