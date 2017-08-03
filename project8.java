//Calvin Ly
//Project8
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class project8 extends JFrame {
    // Text file info
    private JTextField jtfFilename = new JTextField();
    private JTextArea jtaFile = new JTextArea();

    // JDBC and table info
    private JComboBox jcboDriver = new JComboBox(new String[] {
            "com.mysql.jdbc.Driver", "sun.jdbc.odbc.JdbcOdbcDriver",
            "oracle.jdbc.driver.OracleDriver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"});
    private JComboBox jcboURL = new JComboBox(new String[] {
            "jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;"});
    private JTextField jtfUsername = new JTextField();
    private JPasswordField jtfPassword = new JPasswordField();
    private JTextField jtfTableName = new JTextField();

    private JButton jbtViewFile = new JButton("View File");
    private JButton jbtCopy = new JButton("Copy");
    private JLabel jlblStatus = new JLabel();

    public project8() {
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(new JLabel("Filename"), BorderLayout.WEST);
        jPanel1.add(jbtViewFile, BorderLayout.EAST);
        jPanel1.add(jtfFilename, BorderLayout.CENTER);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.setBorder(new TitledBorder("Source Text File"));
        jPanel2.add(jPanel1, BorderLayout.NORTH);
        jPanel2.add(new JScrollPane(jtaFile), BorderLayout.CENTER);

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new GridLayout(5, 0));
        jPanel3.add(new JLabel("JDBC Driver"));
        jPanel3.add(new JLabel("Database URL"));
        jPanel3.add(new JLabel("Username"));
        jPanel3.add(new JLabel("Password"));
        jPanel3.add(new JLabel("Table Name"));

        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridLayout(5, 0));
        jcboDriver.setEditable(true);
        jPanel4.add(jcboDriver);
        jcboURL.setEditable(true);
        jPanel4.add(jcboURL);
        jPanel4.add(jtfUsername);
        jPanel4.add(jtfPassword);
        jPanel4.add(jtfTableName);

        JPanel jPanel5 = new JPanel();
        jPanel5.setLayout(new BorderLayout());
        jPanel5.setBorder(new TitledBorder("Target Database Table"));
        jPanel5.add(jbtCopy, BorderLayout.SOUTH);
        jPanel5.add(jPanel3, BorderLayout.WEST);
        jPanel5.add(jPanel4, BorderLayout.CENTER);

        add(jlblStatus, BorderLayout.SOUTH);
        add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                jPanel2, jPanel5), BorderLayout.CENTER);

        jbtViewFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                showFile();
            }
        });

        jbtCopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    copyFile();
                }
                catch (Exception ex) {
                    jlblStatus.setText(ex.toString());
                }
            }
        });
    }

    /** Display the file in the text area */
    private void showFile() {
        Scanner input = null;
        try {
            // Use a Scanner to read text from the file
            input = new Scanner(new File(jtfFilename.getText().trim()));

            // Read a line and append the line to the text area
            while (input.hasNext())
                jtaFile.append(input.nextLine() + '\n');
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + jtfFilename.getText());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (input != null) input.close();
        }
    }

    private void copyFile() throws Exception {
        // Load the JDBC driver
        Class.forName(((String)jcboDriver.getSelectedItem()).trim());
        System.out.println("Driver loaded");

        // Establish a connection
        Connection conn = DriverManager.getConnection
                (((String)jcboURL.getSelectedItem()).trim(),
                        jtfUsername.getText().trim(),
                        String.valueOf(jtfPassword.getPassword()).trim());
        System.out.println("Database connected");

        // Read each line from the text file and insert it to the table
        insertRows(conn);
    }

    private void insertRows(Connection connection) {
        // Build the SQL INSERT statement
        String sqlInsert = "insert into " + jtfTableName.getText() +"(ssn, firstname, lastname, mi, birthdate, street, phone, zip)"
                + " values (";

        // Use a Scanner to read text from the file
        Scanner input = null;

        // Get file name from the text field
        String filename = jtfFilename.getText().trim();
        String datastring = null;
        try {
            // Create a scanner
            input = new Scanner(new File(filename));
            //datastring=input.nextLine();
            //datastring.split("\t");
            //input.useDelimiter("\t");
            // Create a statement
            Statement statement = connection.createStatement();

            System.out.println("Driver major version? " +
                    connection.getMetaData().getDriverMajorVersion());

            // Determine if batchUpdatesSupported is supported
            boolean batchUpdatesSupported = false;

            try {
                if (connection.getMetaData().supportsBatchUpdates()) {
                    batchUpdatesSupported = true;
                    System.out.println("batch updates supported");
                }
                else {
                    System.out.println("The driver " +
                            "does not support batch updates");
                }
            }
            catch (UnsupportedOperationException ex) {
                System.out.println("The operation is not supported");
            }

            // Determine if the driver is capable of batch updates
            if (batchUpdatesSupported) {
                // Read a line and add the insert table command to the batch
                while (input.hasNext()) {
                    statement.addBatch(sqlInsert + input.useDelimiter("\t") + ")");

                }

                statement.executeBatch();

                jlblStatus.setText("Batch updates completed");
            }
            else {
                // Read a line and execute insert table command
                while (input.hasNext()) {
                    statement.executeUpdate(sqlInsert + input.useDelimiter("\t") + ")");
                }

                jlblStatus.setText("Single row update completed");
            }
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + filename);
        }
        finally {
            if (input != null) input.close();
        }
    }

    public static void main(String args[]) {
        JFrame frame = new project8();
        frame.setTitle("project8");
        frame.setSize(700, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}