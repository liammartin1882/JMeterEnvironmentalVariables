package lm.zeb.jmeter.config;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.gui.util.HeaderAsPropertyRenderer;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.Data;
import org.apache.jorphan.gui.GuiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvironmentalVariablesGui extends AbstractConfigGui implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentalVariablesGui.class);
    private PowerTableModel tableModel;
    private JButton delete;
    private JTable table;

    public EnvironmentalVariablesGui() { this.init(); }

    @Override
    public String getStaticLabel() {
        return "Environmental Variables";
    }

    @Override
    public String getLabelResource() {
        return null;
    }

    @Override
    public TestElement createTestElement() {
        EnvironmentalVariables el = new EnvironmentalVariables();
        this.modifyTestElement(el);
        return el;
    }

    public void configure(TestElement el) {
        super.configure(el);
        if (el instanceof EnvironmentalVariables) {
            EnvironmentalVariables tp = (EnvironmentalVariables) el;
            JMeterProperty udv = tp.getEnvironmentalVariablesAsProperty();
            if (udv != null) {
                this.tableModel.clearData();
                PropertyIterator iter = ((EnvironmentalArguments)udv.getObjectValue()).iterator();
                while(iter.hasNext()){
                    JMeterProperty jMeterProperty = iter.next();
                    EnvironmentalArgument arg = (EnvironmentalArgument)jMeterProperty.getObjectValue();
                    this.tableModel.addRow(new Object[]{arg.getName(), arg.getProtocol(), arg.getUrl(), arg.getPort(), arg.getSelected()});
                }
                this.checkDeleteStatus();
            }
        }

    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        super.configureTestElement(testElement);
        if (testElement instanceof EnvironmentalVariables) {
            EnvironmentalVariables tp = (EnvironmentalVariables)testElement;
            tp.setEnvironmentalVariables((EnvironmentalArguments) this.createArguuments());
        }
    }

    private void init() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(this.makeBorder());
        this.add(this.makeTitlePanel(), "North");
        this.add(this.createTablePanel(), "Center");
        this.add(Box.createVerticalStrut(70), "West");
        this.add(this.createButtonPanel(), "South");
        this.table.revalidate();
    }

    public void clearGui() {
        super.clearGui();
    }

    private TestElement createArguuments() {
        EnvironmentalArguments arg = new EnvironmentalArguments();
        this.modifyArguments(arg);
        return arg;
    }

    private void modifyArguments(TestElement args) {
        GuiUtils.stopTableEditing(this.table);

        if(args instanceof EnvironmentalArguments){
            EnvironmentalArguments arguments = (EnvironmentalArguments)args;
            arguments.clear();
            Data model = this.tableModel.getData();

            model.reset();
            if(model != null){
                while(model.next()) {
                    String n = model.getColumnValue("Environment").toString();
                    String pr = model.getColumnValue("protocol").toString();
                    String u = model.getColumnValue("url").toString();
                    String p = model.getColumnValue("port").toString();
                    Boolean s = (Boolean)model.getColumnValue("Selected");
                    EnvironmentalArgument arg = new EnvironmentalArgument(n, pr, u, p, s, "=");
                    arguments.addArgument(arg);
                }
            }
        }

        super.configureTestElement(args);
    }

    protected void checkDeleteStatus() {
        if (this.tableModel.getRowCount() == 0) {
            this.delete.setEnabled(false);
        } else {
            this.delete.setEnabled(true);
        }
    }

    private Component createTablePanel() {
        this.tableModel = new PowerTableModel(new String[]{"Environment", "protocol", "url", "port", "Selected"}, new Class[]{String.class, String.class, String.class, String.class, Boolean.class});
        this.table = new JTable(this.tableModel);

        TableColumn tc = table.getColumnModel().getColumn(4);
        JCheckBox jcb = new JCheckBox();
        jcb.setActionCommand("selected");
        jcb.addActionListener(this);
        tc.setCellEditor(new DefaultCellEditor(jcb));

        this.table.getTableHeader().setDefaultRenderer(new HeaderAsPropertyRenderer());
        this.table.setSelectionMode(2);
        JMeterUtils.applyHiDPI(this.table);

        return this.makeScrollPane(this.table);
    }


    private JPanel createButtonPanel() {
        JButton add = new JButton(JMeterUtils.getResString("add"));
        add.setActionCommand("add");
        add.addActionListener(this);
        add.setEnabled(true);
        this.delete = new JButton(JMeterUtils.getResString("delete"));
        this.delete.setActionCommand("delete");
        this.delete.addActionListener(this);
        this.checkDeleteStatus();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(add);
        buttonPanel.add(this.delete);
        return buttonPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("delete")) {
            this.deleteArgument();
        } else if (action.equals("add")) {
            this.addArgument();
        } else if (action.equals("selected")) {
            this.selectArgument();
        }
    }

    protected void selectArgument() {
        int selectedRow = this.table.getSelectedRow();

        for(int i = 0; i <= this.table.getRowCount(); i++) {
            if(i != selectedRow) {
                this.table.setValueAt(Boolean.FALSE, i, 4);
            }
        }

        this.tableModel.fireTableDataChanged();
    }

    protected void addArgument() {
        GuiUtils.stopTableEditing(this.table);
        this.tableModel.addNewRow();
        this.tableModel.fireTableDataChanged();
        this.delete.setEnabled(true);
        int rowToSelect = this.tableModel.getRowCount() - 1;
        this.table.setRowSelectionInterval(rowToSelect, rowToSelect);
    }

    protected void deleteArgument() {
        GuiUtils.cancelEditing(this.table);
        int rowSelected = this.table.getSelectedRow();
        if (rowSelected >= 0) {
            this.tableModel.removeRow(rowSelected);
            this.tableModel.fireTableDataChanged();
            if (this.tableModel.getRowCount() == 0) {
                this.delete.setEnabled(false);
            } else {
                int rowToSelect = rowSelected;
                if (rowSelected >= this.tableModel.getRowCount()) {
                    rowToSelect = rowSelected - 1;
                }

                this.table.setRowSelectionInterval(rowToSelect, rowToSelect);
            }
        }
    }
}
