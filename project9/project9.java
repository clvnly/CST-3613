import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.sql.*;

public class project9 extends JApplet{

    private static JFrame frame;
    private JTextField jtfSSN = new JTextField(10);
    private JTextField jtfFirstName = new JTextField(25);
    private JTextField jtfMI = new JTextField(2);
    private JTextField jtfLastName = new JTextField(25);
    private JTextField jtfBirthDate = new JTextField(15);
    private JTextField jtfStreet = new JTextField(25);
    private JTextField jtfPhone = new JTextField(11);
    private JTextField jtfZipcode = new JTextField(5);
    private JTextField jtfDeptID = new JTextField(4);

    // Declare and create buttons
    private JButton jbtView = new JButton("View");
    private JButton jbtInsert = new JButton("Insert");
    private JButton jbtUpdate = new JButton("Update");
    private JButton jbtClear = new JButton("Clear");
    //Toolbar assets
    private JButton toolbarView = new JButton(
            new ImageIcon(getClass().getResource("/image/view.png")));
    private JButton toolbarInsert = new JButton(
            new ImageIcon(getClass().getResource("/image/insert.png")));
    private JButton toolbarUpdate = new JButton(
            new ImageIcon(getClass().getResource("/image/update.png")));
    //Popup Menu Assets
    private JPopupMenu jPopupMenu1 = new JPopupMenu();
    private JMenuItem jmiView = new JMenuItem("View",
            new ImageIcon(getClass().getResource("/image/view.png")));
    private JMenuItem jmiInsert = new JMenuItem("Insert",
            new ImageIcon(getClass().getResource("/image/insert.png")));
    private JMenuItem jmiUpdate = new JMenuItem("Update",
            new ImageIcon(getClass().getResource("/image/update.png")));
    private JMenuItem jmiExit = new JMenuItem("Exit");
    private JTextArea jTextArea1 = new JTextArea();
    //Menu Assets
    private JMenuItem menuView, menuInsert, menuUpdate;
    private Connection connection;
    public void init() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("Driver loaded");
            // Connect to a database
            connection = DriverManager.getConnection
                    ("jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;" , "cst3613", "e369");
            //System.out.println("Database connected");
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void destroy() {
        try{
            connection.close();
            System.out.println("Database closed");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // Initialize user interface
    public project9() {
        //Menu Items
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);

        // Add menu "Operation" to menu bar
        JMenu operationMenu = new JMenu("Edit");
        operationMenu.setMnemonic('E');
        jmb.add(operationMenu);


        // Add menu items with mnemonics to menu "Operation"
        operationMenu.add(menuView= new JMenuItem("View", 'V'));
        ImageIcon view = new ImageIcon(getClass().getResource("/image/view.png"));
        menuView.setIcon(view);
        operationMenu.add(menuInsert = new JMenuItem("Insert", 'I'));
        ImageIcon insert = new ImageIcon(getClass().getResource("/image/insert.png"));
        menuInsert.setIcon(insert);
        operationMenu.add(menuUpdate = new JMenuItem("Update", 'U'));
        ImageIcon update = new ImageIcon(getClass().getResource("/image/update.png"));
        menuUpdate.setIcon(update);

        // Set keyboard accelerators
        menuView.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuInsert.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        menuUpdate.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        //End menu items

        //Toolbar items
        JToolBar jToolBar1 = new JToolBar("My Tool Bar");
        jToolBar1.setFloatable(true);
        jToolBar1.add(toolbarView);
        jToolBar1.add(toolbarInsert);
        jToolBar1.add(toolbarUpdate);

        toolbarView.setToolTipText("View");
        toolbarInsert.setToolTipText("Insert");
        toolbarUpdate.setToolTipText("Update");

        toolbarView.setBorderPainted(false);
        toolbarInsert.setBorderPainted(false);
        toolbarUpdate.setBorderPainted(false);

        add(jToolBar1, BorderLayout.NORTH);
        //End toolbar items
        //Popup Menu assets
        jPopupMenu1.add(jmiView);
        jPopupMenu1.add(jmiInsert);
        jPopupMenu1.add(jmiUpdate);
        jPopupMenu1.add(jmiExit);

        add(new JScrollPane(jTextArea1), BorderLayout.CENTER);

        jmiView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewButton();
            }
        });
        jmiInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertButton();
            }
        });
        jmiUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
            }
        });
        jmiExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        jTextArea1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // For Motif
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) { // For Windows
                showPopup(e);
            }
        });
        // Set properties on the text fields
        toolbarView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewButton();
            }
        });
        toolbarInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertButton();
            }
        });
        toolbarUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
            }
        });
        jtfBirthDate.setHorizontalAlignment(JTextField.RIGHT);

        // Panel p1 to hold labels and text fields
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(10, 2));
        p1.add(new Label("S.S.N:"));
        p1.add(jtfSSN);
        //jtfSSN.setDocument(new JTextFieldLimit(10));
        p1.add(new Label("Last Name:"));
        p1.add(jtfLastName);
        //jtfLastName.setDocument(new JTextFieldLimit(25));
        p1.add(new Label("First Name:"));
        p1.add(jtfFirstName);
        //jtfFirstName.setDocument(new JTextFieldLimit(25));
        p1.add(new Label("M.I."));
        p1.add(jtfMI);
        //jtfMI.setDocument(new JTextFieldLimit(2));

        p1.add(new Label("Birthdate(mm/dd/yyyy):"));
        p1.add(jtfBirthDate);
        //jtfBirthDate.setDocument(new JTextFieldLimit(15));
        p1.add(new Label("Street:"));
        p1.add(jtfStreet);
        //jtfStreet.setDocument(new JTextFieldLimit(25));

        p1.add(new Label("Zipcode:"));
        p1.add(jtfZipcode);
        //jtfZipcode.setDocument(new JTextFieldLimit(5));
        p1.add(new Label("Phone Number(Numbers Only):"));
        p1.add(jtfPhone);
        //jtfPhone.setDocument(new JTextFieldLimit(11));
        p1.add(new Label("Dept ID:"));
        p1.add(jtfDeptID);
        //jtfDeptID.setDocument(new JTextFieldLimit(4));

        p1.setBorder(new
                TitledBorder("Enter Student Information"));

        // Panel p2 to hold the button
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p2.add(jbtView);
        p2.add(jbtInsert);
        p2.add(jbtUpdate);
        p2.add(jbtClear);
        // Add the components to the applet
        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

        // View Button listener
        jbtView.addActionListener(new ActionListener() {
            @Override // Handler for the "View" button
            public void actionPerformed(ActionEvent e) {
                viewButton();
            }
        });

        // Insert Button listener
        jbtInsert.addActionListener(new ActionListener() {
            @Override // Handler for the "Insert" button
            public void actionPerformed(ActionEvent e) {
                insertButton();
            }
        });

        // Update Button listener
        jbtUpdate.addActionListener(new ActionListener() {
            @Override // Handler for the "Update" button
            public void actionPerformed(ActionEvent e) {
                updateButton();
            }
        });
        jbtClear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                jtfSSN.setText("");
                jtfLastName.setText("");
                jtfFirstName.setText("");
                jtfMI.setText("");
                jtfBirthDate.setText("");
                jtfStreet.setText("");
            }
        });
        p1.addMouseListener(new popupListener());
        p2.addMouseListener(new popupListener());
        jmb.addMouseListener(new popupListener());
        jToolBar1.addMouseListener(new popupListener());
        jTextArea1.addMouseListener(new popupListener());
    }
    class popupListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            showPopup(e);
        }
    }
    private void showPopup(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger())
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
    }
    private void viewButton(){
        // Get Student Information from DB
        // Create a statement
        if (jtfSSN.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(frame, "To view student information, please enter SSN.");
        }
        else
        {
            try {
                Statement statement = connection.createStatement();
                String queryString = "select ssn,lastName, firstName, mi, " +
                        "birthDate, street,zipcode,phone,deptid from Students " +
                        "where Students.ssn = '" + jtfSSN.getText() + "'";
                // Execute a statement
                ResultSet resultSet = statement.executeQuery(queryString);

                while (resultSet.next())
                {
                    jtfLastName.setText(resultSet.getString(2));
                    jtfFirstName.setText(resultSet.getString(3));
                    jtfMI.setText(resultSet.getString(4));
                    jtfBirthDate.setText(resultSet.getString(5));
                    jtfStreet.setText(resultSet.getString(6));
                    jtfZipcode.setText(resultSet.getString(7));
                    jtfPhone.setText(resultSet.getString(8));
                    jtfDeptID.setText(resultSet.getString(9));
                }
                resultSet.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } //end of if
    }
    private void insertButton(){
        // Get Student Information from UI
        // ssn, firstname, lastname, mi is required field. If missing, show message.
        if (checkRequiredInfo())
        {
            // Check if the SSN exit already
            if (checkSSN())
                JOptionPane.showMessageDialog(frame, "A student with SSN already exist.");
            else
            {
                try {
                    Statement statement = connection.createStatement();
                    String queryString = "insert into students (ssn, lastname, firstname, mi, birthdate, street)" +
                            "values ('" + jtfSSN.getText().trim() + "','" + jtfLastName.getText().trim() + "','" + jtfFirstName.getText().trim()
                            + "','" + jtfMI.getText().trim() + "','" + jtfBirthDate.getText().trim() + "','" + jtfStreet.getText().trim() + "')";
                    statement.executeUpdate(queryString);
                    JOptionPane.showMessageDialog(null, "Insert completed.");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        }
        else
            JOptionPane.showMessageDialog(frame, "To add student information, please enter at least SSN, Last Name, First Name, and MI.");

    }
    private void updateButton(){
        // Get Student Information from UI
        // To update, SSN is required and lastname, firstname, MI can't be null.
        if (checkRequiredInfo())
        {
            // Check if the SSN exit already
            if (!checkSSN())
                JOptionPane.showMessageDialog(frame, "The student with SSN does not exist.");
            else
            {
                try{
                    Statement statement = connection.createStatement();
                    String queryString = "update students set lastname = '" + jtfLastName.getText().trim() + "', firstname = '" + jtfFirstName.getText().trim() +
                                "', mi = '" + jtfMI.getText().trim() + "', street = '" + jtfStreet.getText().trim() + "'from students where ssn = " + jtfSSN.getText().trim();
                    statement.executeUpdate(queryString);
                    JOptionPane.showMessageDialog(null, "Update completed.");
                    statement.close();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        else
            JOptionPane.showMessageDialog(frame, "To update student information, please enter at least SSN, Last Name, First Name, and MI.");

        // Update student information in DB

    }

    private boolean checkRequiredInfo(){
        if (jtfSSN.getText().trim().equals("") || jtfLastName.getText().trim().equals("")
                || jtfFirstName.getText().trim().equals("") || jtfMI.getText().trim().equals(""))
            return false;
        else
            return true;
    }

    private boolean checkSSN(){

        try {
            Statement statement = connection.createStatement();
            String queryString = "select ssn,lastName, firstName, mi, " +
                    "birthDate, street,zipcode,phone,deptid from Students " +
                    "where Students.ssn = '" + jtfSSN.getText() + "'";
            // Execute a statement
            ResultSet resultSet = statement.executeQuery(queryString);

            if (resultSet.next())
            {
                resultSet.close();
                return true;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }
    // Main method
    public static void main(String[] args) {
        // Create a frame
        frame = new JFrame("Project7");
        // Create an instance of MortgageApplet
        project9 applet = new project9();

        // Add the applet instance to the frame
        frame.add(applet, BorderLayout.CENTER);

        // Invoke init() and start()
        applet.init();
        applet.start();

        // Display the frame
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}