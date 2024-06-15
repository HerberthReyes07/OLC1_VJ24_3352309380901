/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.FileController;
import backend.Interpreter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import exceptions.Error;

/**
 *
 * @author herberthreyes
 */
public class MainInterface extends javax.swing.JFrame {

    private FileController fileController = new FileController();
    private JTextArea currentTextArea;
    private ArrayList<String> outputs = new ArrayList<>();
    private ArrayList<LinkedList<Error>> arrayLexErrors = new ArrayList<>();
    private ArrayList<LinkedList<Error>> arraySyntaxErrors = new ArrayList<>();
    private ArrayList<LinkedList<Error>> arraySemanticErrors = new ArrayList<>();
    private LinkedList<Error> lexErrors;
    private LinkedList<Error> syntaxErrors;
    private LinkedList<Error> semanticErrors;

    /**
     * Creates new form MainInterface
     */
    public MainInterface() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 21)); // NOI18N
        jLabel1.setText("Entrada");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 21)); // NOI18N
        jLabel2.setText("Consola");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Ubuntu Light", 1, 18)); // NOI18N

        jMenu1.setText("Archivo");

        jMenuItem8.setText("Ejecutar");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Cerrar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pestañas");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ejecutar");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu3MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");

        jMenuItem5.setText("Errores");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setText("Generar AST");
        jMenu4.add(jMenuItem6);

        jMenuItem7.setText("Tabla de Símbolos");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        jFileChooser1.setVisible(true);
        int res = jFileChooser1.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            String input;
            try {
                input = fileController.readFile(jFileChooser1.getSelectedFile());
                addNewTab(jFileChooser1.getSelectedFile().getName(), input);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del archivo nuevo:", "Nuevo Archivo", JOptionPane.QUESTION_MESSAGE);
        if (name != null) {
            if (name.isBlank()) {
                JOptionPane.showMessageDialog(null, "No puede nombrar el archivo de esa manera", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int tabCount = jTabbedPane1.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                String tabTitle = jTabbedPane1.getTitleAt(i);
                if (tabTitle.equals(name + ".jc")) {
                    JOptionPane.showMessageDialog(null, "No puede nombrar el archivo de esa manera ya que hay un archivo abierto con ese nombre", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            addNewTab(name, null);
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() != 0) {
            outputs.remove(jTabbedPane1.getSelectedIndex());
            arrayLexErrors.remove(jTabbedPane1.getSelectedIndex());
            arraySyntaxErrors.remove(jTabbedPane1.getSelectedIndex());
            arraySemanticErrors.remove(jTabbedPane1.getSelectedIndex());
            this.lexErrors = new LinkedList<>();
            this.syntaxErrors = new LinkedList<>();
            this.semanticErrors = new LinkedList<>();
            jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo abierto para ser cerrado", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        if (selectedIndex >= 0) {
            String name = jTabbedPane1.getTitleAt(selectedIndex);
            String text = getCurrentTextArea().getText();
            fileController.saveFile(name, text);
            JOptionPane.showMessageDialog(null, "Los cambios realizados se han guardado exitosamente", "Archivo guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo abierto para guardar", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            currentTextArea = getCurrentTextArea();
            textAreaCaretUpdate(currentTextArea);
            jTextArea2.setText(outputs.get(jTabbedPane1.getSelectedIndex()));
            this.lexErrors = arrayLexErrors.get(jTabbedPane1.getSelectedIndex());
            this.syntaxErrors = arraySyntaxErrors.get(jTabbedPane1.getSelectedIndex());
            this.semanticErrors = arraySemanticErrors.get(jTabbedPane1.getSelectedIndex());
        } else {
            jTextField1.setText("");
            jTextArea2.setText("");
        }

    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        
        if (!this.lexErrors.isEmpty() || !this.syntaxErrors.isEmpty() || !this.semanticErrors.isEmpty()) {
            ErrorTable errorTable = new ErrorTable();
            errorTable.setLexErrors(this.lexErrors);
            errorTable.setSyntaxErrors(this.syntaxErrors);
            errorTable.setSemanticErrors(this.semanticErrors);
            errorTable.fillTable();
            errorTable.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún error para mostrar", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MousePressed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_jMenu3MousePressed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getTabCount() > 0) {
            Interpreter interpreter = new Interpreter();
            interpreter.setCode(currentTextArea.getText());
            interpreter.interpret();

            String output = interpreter.getConsole() + "\n";

            this.lexErrors = interpreter.getLexErrors();
            this.syntaxErrors = interpreter.getSyntaxErrors();
            this.semanticErrors = interpreter.getSemanticErrors();
            
            //errores lexicos
            for (var a : this.lexErrors) {
                output += "---> Error: " + a.getType() + " - " + a.getDescription() + " en línea: " + a.getLine() + " y columna: " + a.getColumn() + "\n";
            }
            //errores sintacticos
            for (var a : this.syntaxErrors) {
                output += "---> Error: " + a.getType() + " - " + a.getDescription() + " en línea: " + a.getLine() + " y columna: " + a.getColumn() + "\n";
            }
            //errores semanticos
            for (var a : this.semanticErrors) {
                output += "---> Error: " + a.getType() + " - " + a.getDescription() + " en línea: " + a.getLine() + " y columna: " + a.getColumn() + "\n";
            }

            outputs.set(jTabbedPane1.getSelectedIndex(), output);
            jTextArea2.setText(outputs.get(jTabbedPane1.getSelectedIndex()));
            
            arrayLexErrors.set(jTabbedPane1.getSelectedIndex(), this.lexErrors);
            arraySyntaxErrors.set(jTabbedPane1.getSelectedIndex(), this.syntaxErrors);
            arraySemanticErrors.set(jTabbedPane1.getSelectedIndex(), this.semanticErrors);
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo para ser ejecutado", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void textAreaCaretUpdate(JTextArea textArea) {
        int linea = 0;
        int columna = 0;
        try {
            linea = textArea.getDocument().getRootElements()[0].getElementIndex(textArea.getCaretPosition());
            columna = textArea.getCaretPosition() - textArea.getDocument().getRootElements()[0].getElement(linea).getStartOffset();
        } catch (Exception ex) {
        }
        jTextField1.setText("Línea: " + (linea + 1) + " Columna: " + (columna + 1));
    }

    private JTextArea getCurrentTextArea() {
        int selectedIndex = jTabbedPane1.getSelectedIndex();
        JScrollPane jScrollPane = (JScrollPane) jTabbedPane1.getComponentAt(selectedIndex);

        for (int i = 0; i < jScrollPane.getComponentCount(); i++) {
            if (jScrollPane.getComponent(i) instanceof JViewport) {
                JViewport jViewport = (JViewport) jScrollPane.getComponent(i);
                for (int j = 0; j < jViewport.getComponentCount(); j++) {
                    if (jViewport.getComponent(i) instanceof JTextArea) {
                        JTextArea selectedTextArea = (JTextArea) jViewport.getComponent(i);
                        return selectedTextArea;
                    }
                }
            }
        }
        return null;
    }

    private void addNewTab(String name, String input) {

        outputs.add("");
        arrayLexErrors.add(new LinkedList<>());
        arraySyntaxErrors.add(new LinkedList<>());
        arraySemanticErrors.add(new LinkedList<>());
        
        JTextArea textArea = new JTextArea();

        Document doc = textArea.getDocument();
        textArea.setFont(new java.awt.Font("Ubuntu Mono", 1, 20));
        // Reemplazando tabs con dos espacos
        ((AbstractDocument) doc).setDocumentFilter(new DocumentFilter() {
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                super.insertString(fb, offset, text.replace("\t", "  "), attrs);
            }
        });

        int tabCount = jTabbedPane1.getTabCount();
        currentTextArea = textArea;

        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                textAreaCaretUpdate(textArea);
            }
        });

        if (input == null) {
            jTabbedPane1.addTab(name + ".jc", new JScrollPane(textArea));
            jTabbedPane1.setSelectedIndex(tabCount);
        } else {
            textArea.setText(input);
            jTabbedPane1.addTab(name, new JScrollPane(textArea));
            jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
