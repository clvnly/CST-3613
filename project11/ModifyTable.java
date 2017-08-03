//Calvin Ly
//Project 11
//Program is supposed to insert/update/delete data in DB when Save button is pressed.
//Restore button reads from the DB.
//Insert function only works when all fields are filled in. Empty fields will throw a NullPointerException.
//Delete function will delete in real-time.
//Delete and Update functions work.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.io.*;
import java.sql.*;
import java.util.Vector;

public class ModifyTable extends JApplet{
    static Connection connection = dbConnect.connect();

    // Create a table model
    private DefaultTableModel tablecreator() throws SQLException{
        Statement stmt=connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS;");
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
    private DefaultTableModel tableModel = tablecreator();
    // Create a table
    private JTable jTable1 = new JTable(tableModel);

    // Create buttons
    private JButton jbtAddRow = new JButton("Add New Row");
    private JButton jbtDeleteRow = new JButton("Delete Selected Row");
    private JButton jbtSave = new JButton("Save");
    private JButton jbtRestore = new JButton("Restore");

    // Create a combo box for selection modes
    private JComboBox jcboSelectionMode =
            new JComboBox(new String[] {"SINGLE_SELECTION",
                    "SINGLE_INTERVAL_SELECTION", "MULTIPLE_INTERVAL_SELECTION"});

    // Create check boxes
    private JCheckBox jchkRowSelectionAllowed =
            new JCheckBox("RowSelectionAllowed", true);
    private JCheckBox jchkColumnSelectionAllowed =
            new JCheckBox("ColumnSelectionAllowed", false);
    public void init(){

    }
    public void destroy(){
        try {
            addToDB();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ModifyTable() throws SQLException {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2));
        panel1.add(jbtAddRow);
        panel1.add(jbtDeleteRow);
        JPanel panel2 = new JPanel();
        panel2.add(jbtSave);
        panel2.add(jbtRestore);
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(5, 0));
        panel3.add(new JLabel("Selection Mode"), BorderLayout.WEST);
        panel3.add(jcboSelectionMode, BorderLayout.CENTER);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel4.add(jchkRowSelectionAllowed);
        panel4.add(jchkColumnSelectionAllowed);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(2, 1));
        panel5.add(panel3);
        panel5.add(panel4);

        JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout());
        panel6.add(panel1, BorderLayout.SOUTH);
        panel6.add(panel2, BorderLayout.CENTER);

        add(panel5, BorderLayout.NORTH);
        add(new JScrollPane(jTable1),
                BorderLayout.CENTER);
        add(panel6, BorderLayout.SOUTH);

        // Initialize table selection mode
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jbtAddRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable1.getSelectedRow() >= 0)
                    tableModel.insertRow(jTable1.getSelectedRow(), new java.util.Vector<String>());
                else
                    tableModel.addRow(new java.util.Vector<String>());

            }
        });


        jbtDeleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable1.getSelectedRow() >= 0)
                    tableModel.removeRow(jTable1.getSelectedRow());
            }
        });


        jbtSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addToDB();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        jbtRestore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //ObjectInputStream in = new ObjectInputStream(
                    //        new FileInputStream("tablemodel.dat"));
                    Statement stmt=connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS;");
                    ResultSetMetaData metaData = rs.getMetaData();

                    // names of columns
                    Vector<String> columnNames = new Vector<String>();
                    int columnCount = metaData.getColumnCount();
                    for (int column = 1; column <= columnCount; column++) {
                        columnNames.add(metaData.getColumnName(column));
                    }

                    // data of the table
                    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                    while (rs.next()) {
                        Vector<Object> vector = new Vector<Object>();
                        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                            vector.add(rs.getObject(columnIndex));
                        }
                        data.add(vector);
                    }
                    //Vector<String> rowData = (Vector<String>)in.readObject();
                    //Vector<String> columnNames =
                    //        (Vector<String>)in.readObject();
                    tableModel.setDataVector(data, columnNames);
                    //in.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jchkRowSelectionAllowed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTable1.setRowSelectionAllowed(
                        jchkRowSelectionAllowed.isSelected());
            }
        });

        jchkColumnSelectionAllowed.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jTable1.setColumnSelectionAllowed(
                                jchkColumnSelectionAllowed.isSelected());
                    }
                });

        jcboSelectionMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem =
                        (String)jcboSelectionMode.getSelectedItem();

                if (selectedItem.equals("SINGLE_SELECTION"))
                    jTable1.setSelectionMode(
                            ListSelectionModel.SINGLE_SELECTION);
                else if (selectedItem.equals("SINGLE_INTERVAL_SELECTION"))
                    jTable1.setSelectionMode(
                            ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                else if (selectedItem.equals("MULTIPLE_INTERVAL_SELECTION"))
                    jTable1.setSelectionMode(
                            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            }
        });
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println(""
                        + e.getType() + " "
                        + e.getFirstRow() + " "
                        + e.getLastRow() + " "
                        + e.getColumn());
                //addToDB(e.getLastRow());
                //if(e.getType()==0 || e.getType()==1)
                //    addToDB();
                if(e.getType()==-1){
                    deleteFromDB(e.getLastRow());
                }
            }
        });
    }
    private Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<String>();

        for (int i = 0; i < jTable1.getColumnCount(); i++)
            columnNames.add(jTable1.getColumnName(i));

        return columnNames;
    }
    public void addToDB() {
        try {
            int rows=jTable1.getRowCount()-1;
            Connection c = connection;
            connection.setAutoCommit(false);
            PreparedStatement p = c.prepareStatement("INSERT INTO students VALUES (?,?,?,?,?,?,?,?,?);");
            Statement statement = connection.createStatement();
            String queryString;
            // Execute a statement
            ResultSet resultSet;
            for(int row=0; row<=rows; row++)
            {
                queryString = "select ssn,lastName, firstName, mi, " +
                        "birthDate, street,zipcode,phone,deptid from Students " +
                        "where Students.ssn = '" + jTable1.getValueAt(row,0) + "';";
                resultSet = statement.executeQuery(queryString);
                if(resultSet.next()){
                    //update
                    p = c.prepareStatement("update students set firstname = '"+jTable1.getValueAt(row,1)+
                            "', mi = '"+jTable1.getValueAt(row,2)+"', lastname = '"+jTable1.getValueAt(rows, 3)+
                            "', birthdate = '"+jTable1.getValueAt(row, 4)+"', street = '"+jTable1.getValueAt(row, 5)+
                            "',phone = '"+jTable1.getValueAt(row, 6)+"',zipcode='"+jTable1.getValueAt(row, 7)+
                            "',deptid = '"+jTable1.getValueAt(row, 8)+"' where ssn = '"+jTable1.getValueAt(row, 0)+"';");
                    p.addBatch();
                    System.out.println("Update added.");
                    p = c.prepareStatement("INSERT INTO students VALUES (?,?,?,?,?,?,?,?,?);");
                }
                else{
                    //insert
                    p.setString(1,jTable1.getValueAt(row, 0).toString());
                    p.setString(2,jTable1.getValueAt(row, 1).toString());
                    p.setString(3,jTable1.getValueAt(row, 2).toString());
                    p.setString(4,jTable1.getValueAt(row, 3).toString());
                    p.setString(5,jTable1.getValueAt(row, 4).toString());
                    p.setString(6,jTable1.getValueAt(row, 5).toString());
                    p.setString(7,jTable1.getValueAt(row, 6).toString());
                    p.setString(8,jTable1.getValueAt(row, 7).toString());
                    p.setString(9,jTable1.getValueAt(row, 8).toString());
                    p.addBatch();
                    System.out.println("Insert added.");
                }
            }
            p.executeBatch();
            connection.commit();
            System.out.println("Changes committed.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteFromDB(int row){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM STUDENTS WHERE SSN = '"+jTable1.getValueAt(row,0)+"';");
            System.out.println("Row deleted.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    //Main method
    public static void main(String[] args) throws SQLException{
        ModifyTable applet = new ModifyTable();
        JFrame frame = new JFrame();
        //EXIT_ON_CLOSE == 3
        frame.setDefaultCloseOperation(3);
        frame.setTitle("ModifyTable");
        frame.getContentPane().add(applet, java.awt.BorderLayout.CENTER);
        applet.init();
        applet.start();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}