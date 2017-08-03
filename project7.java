//Calvin Ly
//CST 3613
//Applet with DB connection

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class project7 extends JApplet
{
    private JTextField SSNField = new JTextField(9);
    private JTextField LastNameField = new JTextField(25);
    private JTextField FirstNameField = new JTextField(25);
    private JTextField MiField = new JTextField(1);
    private JTextField AddressField = new JTextField(25);
    private JTextField BirthdateField = new JTextField(10);

    private JButton insertButton = new JButton("Insert");
    private JButton viewButton = new JButton("View");
    private JButton updateButton = new JButton("Update");
    private JButton clearButton = new JButton("Clear");

    private Statement stmt;
    private Connection connection;

    public void init() //building applet gui
    {
        setLayout(new BorderLayout());
        setSize(800,200);
        JPanel panel = new JPanel();
        panel.add(new JLabel("SSN"));
        panel.add(SSNField);
        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Last Name"));
        panel2.add(LastNameField);
        panel2.add(new JLabel("First Name"));
        panel2.add(FirstNameField);
        panel2.add(new JLabel("Mi"));
        panel2.add(MiField);
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Birth Date"));
        panel3.add(BirthdateField);
        panel3.add(new JLabel("Address"));
        panel3.add(AddressField);
        JPanel secondpanel = new JPanel();
        secondpanel.add(panel2);
        secondpanel.add(panel3);
        add(panel, BorderLayout.NORTH);
        add(secondpanel, BorderLayout.CENTER);

        initializeDB();
        insertButton.addActionListener(new InsertButton());
        viewButton.addActionListener(new ViewButton());
        updateButton.addActionListener(new UpdateButton());
        clearButton.addActionListener(new ClearButton());
        JPanel panelB = new JPanel();
        panelB.add(insertButton);
        panelB.add(viewButton);
        panelB.add(updateButton);
        panelB.add(clearButton);

        add(panelB, BorderLayout.SOUTH);
    }
    //connect to db
    private void initializeDB()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded");

            connection = DriverManager.getConnection("jdbc:sqlserver://s16988308.onlinehome-server.com:1433;databaseName=CUNY_DB;integratedSecurity=false;","cst3613","e369");
            System.out.println("Database connected");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    //insert button
    private class InsertButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                stmt = connection.createStatement();
                //checking for duplicate SSN
                String findSSN = "select * from students where ssn = " + SSNField.getText().trim();
                ResultSet rset = stmt.executeQuery(findSSN);
                if(rset.next())//if there is a result, message will display
                    JOptionPane.showMessageDialog(null, "Cannot insert, duplicate SSN found.");
                else
                {
                    //insert row
                    String queryString = "insert into students (ssn, lastname, firstname, mi, birthdate, street)" +
                            "values ('" + SSNField.getText().trim() + "','" + LastNameField.getText().trim() + "','" + FirstNameField.getText().trim()
                            + "','" + MiField.getText().trim() + "','" + BirthdateField.getText().trim() + "','" + AddressField.getText().trim() + "')";
                    stmt.executeUpdate(queryString);
                    JOptionPane.showMessageDialog(null, "Insert completed.");
                    rset.close();
                    stmt.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

    }
    //view button with popup
    private class ViewButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                stmt = connection.createStatement();
                String queryString = "select lastName,firstName,mi,birthdate,street from students where ssn = '" + SSNField.getText().trim() + "'";
                ResultSet rset = stmt.executeQuery(queryString);
                if(rset.next())
                {
                    String LastName = rset.getString(1);
                    String FirstName = rset.getString(2);
                    String Mi = rset.getString(3);
                    String Bday = rset.getString(4);
                    String Address = rset.getString(5);
                    //popup with student info
                    JOptionPane.showMessageDialog(null, SSNField.getText().trim() + " " + FirstName + " " + " " + LastName + " " + Mi + " " + Bday + " "+ Address);
                    //text fields will be replaced with the result info
                    LastNameField.setText(LastName);
                    FirstNameField.setText(FirstName);
                    MiField.setText(Mi);
                    BirthdateField.setText(Bday);
                    AddressField.setText(Address);
                }
                else
                    JOptionPane.showMessageDialog(null, "Not found.");
                rset.close();
                stmt.close();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    //update button
    private class UpdateButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try{
                stmt = connection.createStatement();
                String findSSN = "select * from students where ssn = " + SSNField.getText().trim();
                ResultSet rset = stmt.executeQuery(findSSN);
                if(rset.next())
                {
                    String queryString = "update students set lastname = '" + LastNameField.getText().trim() + "', firstname = '" + FirstNameField.getText().trim() +
                            "', mi = '" + MiField.getText().trim() + "', street = '" + AddressField.getText().trim() + "'from students where ssn = " + SSNField.getText().trim();
                    stmt.executeUpdate(queryString);
                    JOptionPane.showMessageDialog(null, "Update completed.");
                }
                else
                    JOptionPane.showMessageDialog(null,"Not found.");
                rset.close();
                stmt.close();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    //clear button
    private class ClearButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            SSNField.setText("");
            LastNameField.setText("");
            FirstNameField.setText("");
            MiField.setText("");
            BirthdateField.setText("");
            AddressField.setText("");
        }
    }

}