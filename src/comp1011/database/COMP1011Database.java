package comp1011.database;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Thomas Herr (200325519)
 */
public class COMP1011Database {
    public static final String DBUrl = "jdbc:mysql://sql.computerstudi.es:3306/gc200325519?zeroDateTimeBehavior=convertToNull";
    public static String username = "gc200325519", password = "L^cW3GW*";
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Table Mouse Event Lab");
        frame.setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel(new FlowLayout());
        JPanel southPanel = new JPanel(new GridLayout(2,6));
        
        JLabel lblId = new JLabel("Id");
        JTextField id = new JTextField(5);
        id.setEditable(false);
        JLabel lblFirstName = new JLabel("First Name");
        JTextField firstName = new JTextField(25);
        firstName.setEditable(false);
        JLabel lblLastName = new JLabel("Last Name");
        JTextField lastName = new JTextField(25);
        lastName.setEditable(false);
        JLabel lblEmail = new JLabel("Email");
        JTextField email = new JTextField(20);
        email.setEditable(false);
        JLabel lblGender = new JLabel("Gender");
        JTextField gender = new JTextField(1);
        gender.setEditable(false);
        JLabel lblSalary = new JLabel("Salary");
        JTextField salary = new JTextField(20);
        salary.setEditable(false);
        
        southPanel.add(lblId);
        southPanel.add(id);
        southPanel.add(lblFirstName);
        southPanel.add(firstName);
        southPanel.add(lblLastName);
        southPanel.add(lastName);
        southPanel.add(lblEmail);        
        southPanel.add(email);
        southPanel.add(lblGender);
        southPanel.add(gender);
        southPanel.add(lblSalary);
        southPanel.add(salary);
        
        final JLabel lblSelectedItem = new JLabel();
        final JTextField txtSelectedItem = new JTextField();
       
        final String QRY = "SELECT * FROM Employee";
        
        Statement stat = null;
        ResultSet rs = null;
        Connection conn = null;
        
        //Login Window 
        JPanel loginPanel = new JPanel(new FlowLayout());
        JLabel lblUsername = new JLabel("User Name: ");
        JLabel lblPassword = new JLabel("Password: ");
        JTextField txtUserName = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        
        loginPanel.add(lblUsername);
        loginPanel.add(txtUserName);
        loginPanel.add(lblPassword);
        loginPanel.add(txtPassword);
        
        //boolean variable for loop control
        boolean dbCredCheck = false;
        int dbLoopCounter = 0;
        
        do {
            String[] buttonOptions = new String[]{"Ok", "Cancel"};
            JOptionPane.showOptionDialog(null, loginPanel, "Enter Password", 
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
                    buttonOptions, buttonOptions[0]);
            
            try {
                //Create the connection object based on the entered credentials
                conn = DriverManager.getConnection(DBUrl, username, password);
                dbCredCheck = true;
                
            } catch(Exception error) {
                JOptionPane.showMessageDialog(null, "Database Credentials Invalid. Please Try Again.");
                dbCredCheck = false;
                dbLoopCounter++;
                
                if(dbLoopCounter>=2) {
                    JOptionPane.showMessageDialog(null, "Failed to connect to database. Exiting.");
                    System.exit(0);
                }
            }
            
            
        } while(!dbCredCheck && dbLoopCounter<=3);
        
        try {
            //What if conn is null
            if(conn!=null) {
                //Create the statement object that will manipulate the database object
                stat = conn.createStatement();
                rs = stat.executeQuery(QRY);
                
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                //Display the data into a JTable
                DefaultTableModel tblModel = buildTBModel(rs);
                final JTable table = new JTable(tblModel);
      
                table.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        
                        Vector<Object> elementAt = (Vector<Object>) model.getDataVector().elementAt(table.rowAtPoint(e.getPoint()));
                        id.setText(elementAt.get(0).toString());
                        firstName.setText(elementAt.get(1).toString());
                        lastName.setText(elementAt.get(2).toString());
                        email.setText(elementAt.get(3).toString());
                        gender.setText(elementAt.get(4).toString());
                        salary.setText(elementAt.get(5).toString());
                    }
                });
                
                
                tablePanel.add(table);
                frame.add(tablePanel, BorderLayout.NORTH);
                frame.add(southPanel, BorderLayout.SOUTH);
                frame.setSize(400, 200);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        } catch(SQLException error) {
            
        }
        
    }
    
    public static DefaultTableModel buildTBModel(ResultSet rs) throws SQLException {
           ResultSetMetaData metaData = rs.getMetaData(); 
           
           //Get Column names and store in a vector
           Vector<String> columnNames = new Vector<String>();
           int columnCount = metaData.getColumnCount();
           
           for(int i=1; i<=columnCount; i++) {
               columnNames.add(metaData.getColumnName(i));
           }
           
           //create the vector to hold the data (Vector of Vectors)
           Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
           
           while(rs.next())
           {
               //This will store each row
               Vector<Object> rowVector = new Vector<Object>();
               
               //Loop through the result set and get each object
               for(int colIndex=1; colIndex<=columnCount; colIndex++)
               {
                    rowVector.add(rs.getObject(colIndex));
               }  
               tableData.add(rowVector);
           }
           
           //Return
           return new DefaultTableModel(tableData, columnNames);
    }
    
    public static void insert() throws SQLException {
        Statement stat = null;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(DBUrl, username, password);
            stat = conn.createStatement();
            String sql = "INSERT INTO EMPLOYEES (`firstName`,`lastName`,`age`,`gender`) VALUES(`Joe`,`Smith`,`40`,`male`)";
            
        } catch(SQLException error) {
            
        } catch(Exception err) {
            
        } finally {
            conn.close();
            stat.close();
        }
    }
    
}
