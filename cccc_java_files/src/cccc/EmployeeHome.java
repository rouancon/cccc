/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cccc;
import java.awt.Dimension;
import java.sql.*;
import java.util.*;
import java.awt.Frame;
import javax.swing.ImageIcon;

/**
 *
 * @author connorarnold
 */

public class EmployeeHome extends javax.swing.JPanel {

    /**
     * Creates new form EmployeeHome
     */
    public EmployeeHome() {
        initComponents();
    }
    String userId;
    Connection openConnection;
    ResultSet UserResultSet;
    Integer userRId;
    String employeeName;
    Frame parentFrame;
    ResultSet appointmentResultSet;
    public EmployeeHome(ResultSet passedResultSet, Connection passedConnection, Frame passedFrame) {
        initComponents();
        parentFrame = passedFrame;
        String pictureLocation = System.getProperty("user.dir") + "/logo.png";
        Dimension d = new Dimension(650,490);
        parentFrame.setPreferredSize(d);
        parentFrame.pack();
        icon.setIcon(new ImageIcon(pictureLocation));
        icon.updateUI();
        try
        {
            userId = passedResultSet.getString("e_id");
            eName.setText(passedResultSet.getString("e_name"));
            employeeName = passedResultSet.getString("e_name");
            userRId = passedResultSet.getInt("r_id");
            openConnection = passedConnection;
            String regionNameQuery = "Select r_id_to_name(?) as r_name";
            CallableStatement regionNameStatement = openConnection.prepareCall(regionNameQuery);
            regionNameStatement.setInt(1, userRId);
            ResultSet regionNameResult = regionNameStatement.executeQuery();
            regionNameResult.first();
            eBranch.setText(regionNameResult.getString("r_name"));
            UserRole.setText(passedResultSet.getString("e_role"));
            eSalary.setText(passedResultSet.getString("e_salary"));
            eStartDate.setText(passedResultSet.getString("e_start_date"));
            ePhone.setText(passedResultSet.getString("e_phone"));
            eEmail.setText(passedResultSet.getString("e_email"));
            String zip = passedResultSet.getString("e_zip");
            if (zip.length() == 4)
            {
                zip = "0" + zip;
            }
            String address = passedResultSet.getString("e_street_address") + "\n" + 
                    passedResultSet.getString("e_city") + ", " + passedResultSet.getString("e_state") + " " +
                    zip;

            UserAddress.setText(address);
            UserResultSet = passedResultSet;

            //Set up the emlpoyees list
            if (!UserRole.getText().equals("Manager"))
            {
                personalAppointmentTab.remove(employeesTab);
            }
            else
            {
                List<String> employeeRegionList = new ArrayList<String>();
                String employeeRegionQuery = "Select e_name from employee where r_id = ?";
                CallableStatement regionListStatement = openConnection.prepareCall(employeeRegionQuery);
                regionListStatement.setInt(1, userRId);
                ResultSet regionEmployeeResultSet = regionListStatement.executeQuery();
                String regionEmployeeName;
                while (regionEmployeeResultSet.next()){
                    regionEmployeeName = regionEmployeeResultSet.getString("e_name");
                    employeeRegionList.add(regionEmployeeName);
                }
                String[] regionEmployees = employeeRegionList.toArray(new String[employeeRegionList.size()]);
                employeeVisibleList.setListData(regionEmployees);
            }
            //Initialize the regional packages
            if (UserRole.getText().equals("Manager") || UserRole.getText().equals("Sales"))
            {
                List<String> packageRegionList = new ArrayList<String>();
                String packageRegionQuery = "CALL regional_packages(?)";
                CallableStatement regionListStatement = openConnection.prepareCall(packageRegionQuery);
                regionListStatement.setInt(1, userRId);
                ResultSet regionPackageResultSet = regionListStatement.executeQuery();
                String regionPackageName;
                while (regionPackageResultSet.next()){
                    regionPackageName = regionPackageResultSet.getString("p_name");
                    packageRegionList.add(regionPackageName);
                }
                String[] regionPackages = packageRegionList.toArray(new String[packageRegionList.size()]);
                packageVisibleList.setListData(regionPackages);
            }
            else
            {
                personalAppointmentTab.remove(packageTab);
            }
            
            // Initialize the appointment list
            if (UserRole.getText().equals("Manager") || UserRole.getText().equals("Tech"))
            {
                List<String> appointmentRegionList = new ArrayList<String>();
                String appointmentRegionQuery;
                CallableStatement regionListStatement;
                //Techs only see their appointments
                if (UserRole.getText().equals("Tech"))
                {
                    appointmentRegionQuery = "CALL regional_appointments_name(?,?);";
                    regionListStatement = openConnection.prepareCall(appointmentRegionQuery);
                    regionListStatement.setInt(1, userRId);
                    regionListStatement.setString(2,employeeName);
                }
                else
                {
                    appointmentRegionQuery = "CALL regional_appointments(?)";
                    regionListStatement = openConnection.prepareCall(appointmentRegionQuery);
                    regionListStatement.setInt(1, userRId);
                }
                ResultSet regionAppointmentResultSet = regionListStatement.executeQuery();
                appointmentResultSet = regionAppointmentResultSet;
                String regionAppointmentTime, 
                        regionAppointmentCustomer, 
                        regionAppointmentEmployee,
                        regionAppointmentAddress, 
                        regionAppointmentCity,
                        regionAppointmentState,
                        regionAppointmentZip,
                        regionAppointmentInfo;
                while (regionAppointmentResultSet.next()){
                    regionAppointmentTime = regionAppointmentResultSet.getString("a_time");
                    regionAppointmentCustomer = regionAppointmentResultSet.getString("c_name");
                    regionAppointmentEmployee = regionAppointmentResultSet.getString("e_name");
                    regionAppointmentAddress = regionAppointmentResultSet.getString("c_street_address");
                    regionAppointmentCity = regionAppointmentResultSet.getString("c_city");
                    regionAppointmentState = regionAppointmentResultSet.getString("c_state");
                    regionAppointmentZip = regionAppointmentResultSet.getString("c_zip");
                    if (regionAppointmentZip.length() == 4)
                    {
                        regionAppointmentZip = "0" + regionAppointmentZip;
                    }
                    regionAppointmentInfo = regionAppointmentTime + "       " + 
                            regionAppointmentEmployee + "       " + regionAppointmentCustomer + "       "
                            + regionAppointmentAddress + ", " + regionAppointmentCity + " " + regionAppointmentState
                            + ", " + regionAppointmentZip;
                    appointmentRegionList.add(regionAppointmentInfo);
                }
                String[] regionAppointments = appointmentRegionList.toArray(new String[appointmentRegionList.size()]);
                appointmentVisibleList.setListData(regionAppointments);
            }
            else
            {
                personalAppointmentTab.remove(appointmentTab);
            }
            
            //Set up the Customers list
            if (UserRole.getText().equals("Manager") || UserRole.getText().equals("Sales")|| UserRole.getText().equals("Accounting"))
            {
                List<String> customerRegionList = new ArrayList<String>();
                String customerRegionQuery = "CALL regional_customers(?)";
                CallableStatement regionListStatement = openConnection.prepareCall(customerRegionQuery);
                regionListStatement.setInt(1, userRId);
                ResultSet regionCustomerResultSet = regionListStatement.executeQuery();
                String regionCustomerName;
                while (regionCustomerResultSet.next()){
                    regionCustomerName = regionCustomerResultSet.getString("c_name");
                    customerRegionList.add(regionCustomerName);
                }
                String[] regionCustomers = customerRegionList.toArray(new String[customerRegionList.size()]);
                customerVisibleList.setListData(regionCustomers);
            }
            else
            {
                personalAppointmentTab.remove(customerTab);
            }
        
        }
        catch(Exception e)
        {
           throw new IllegalStateException("error",e); 
        }
    }
    
    public boolean isNumber(String s)
    {
        for (Integer i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        personalAppointmentTab = new javax.swing.JTabbedPane();
        homeTab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        eName = new javax.swing.JLabel();
        eBranch = new javax.swing.JLabel();
        UserRole = new javax.swing.JLabel();
        eSalary = new javax.swing.JLabel();
        eStartDate = new javax.swing.JLabel();
        ePhone = new javax.swing.JLabel();
        eEmail = new javax.swing.JLabel();
        UserAddress = new javax.swing.JLabel();
        UpdateInfoButton = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jLabel17 = new javax.swing.JLabel();
        employeesTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        employeeVisibleList = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        AddEmployeeButton = new javax.swing.JButton();
        packageTab = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        addPackageButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        packageVisibleList = new javax.swing.JList<>();
        appointmentTab = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        appointmentVisibleList = new javax.swing.JList<>();
        addAppointmentButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        customerTab = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        customerVisibleList = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        icon = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        jLabel1.setText("Name");

        jLabel2.setText("Branch");

        jLabel3.setText("Position");

        jLabel4.setText("Salary");

        jLabel5.setText("Start Date");

        jLabel6.setText("Phone");

        jLabel7.setText("Email");

        jLabel8.setText("Address");

        eName.setText("eName");

        eBranch.setText("eBranch");

        UserRole.setText("ePosition");

        eSalary.setText("eSalary");

        eStartDate.setText("eStartDate");

        ePhone.setText("ePhone");

        eEmail.setText("eEmail");

        UserAddress.setText("eAddress");

        UpdateInfoButton.setBackground(new java.awt.Color(51, 51, 255));
        UpdateInfoButton.setLabel("Update Info");
        UpdateInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateInfoButtonActionPerformed(evt);
            }
        });

        jLabel17.setText("Personal Information");

        javax.swing.GroupLayout homeTabLayout = new javax.swing.GroupLayout(homeTab);
        homeTab.setLayout(homeTabLayout);
        homeTabLayout.setHorizontalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeTabLayout.createSequentialGroup()
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homeTabLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(homeTabLayout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(38, 38, 38)
                        .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserAddress)
                            .addComponent(eEmail)
                            .addComponent(ePhone)
                            .addComponent(eStartDate)
                            .addComponent(eName)
                            .addComponent(eBranch)
                            .addComponent(eSalary)
                            .addComponent(UserRole)))
                    .addGroup(homeTabLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(UpdateInfoButton)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        homeTabLayout.setVerticalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(eName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(eBranch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(UserRole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(eSalary))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(eStartDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ePhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(eEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(UserAddress))
                .addGap(18, 18, 18)
                .addComponent(UpdateInfoButton)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        personalAppointmentTab.addTab("Home", homeTab);

        employeeVisibleList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        employeeVisibleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeVisibleListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(employeeVisibleList);

        jLabel9.setText("Regional Employees");

        AddEmployeeButton.setText("Add");
        AddEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEmployeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeesTabLayout = new javax.swing.GroupLayout(employeesTab);
        employeesTab.setLayout(employeesTabLayout);
        employeesTabLayout.setHorizontalGroup(
            employeesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeesTabLayout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addGroup(employeesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9)
                        .addGroup(employeesTabLayout.createSequentialGroup()
                            .addComponent(AddEmployeeButton)
                            .addGap(22, 22, 22)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(262, Short.MAX_VALUE))
        );
        employeesTabLayout.setVerticalGroup(
            employeesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeesTabLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(AddEmployeeButton)
                .addGap(19, 19, 19))
        );

        personalAppointmentTab.addTab("Employees", employeesTab);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 41, Short.MAX_VALUE)
        );

        addPackageButton.setText("Add Package");
        addPackageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPackageButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPackageButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(addPackageButton)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel10.setText("Regional Packages");

        packageVisibleList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        packageVisibleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                packageVisibleListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(packageVisibleList);

        javax.swing.GroupLayout packageTabLayout = new javax.swing.GroupLayout(packageTab);
        packageTab.setLayout(packageTabLayout);
        packageTabLayout.setHorizontalGroup(
            packageTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, packageTabLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
            .addGroup(packageTabLayout.createSequentialGroup()
                .addGroup(packageTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(packageTabLayout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(packageTabLayout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        packageTabLayout.setVerticalGroup(
            packageTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(packageTabLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(packageTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(packageTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        personalAppointmentTab.addTab("Packages", packageTab);

        jLabel11.setText("Upcoming Appointments");

        appointmentVisibleList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        appointmentVisibleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appointmentVisibleListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(appointmentVisibleList);

        addAppointmentButton.setText("Add Appointment");
        addAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAppointmentButtonActionPerformed(evt);
            }
        });

        jLabel13.setText("Date");

        jLabel14.setText("Technician");

        jLabel15.setText("Customer");

        jLabel16.setText("Address");

        javax.swing.GroupLayout appointmentTabLayout = new javax.swing.GroupLayout(appointmentTab);
        appointmentTab.setLayout(appointmentTabLayout);
        appointmentTabLayout.setHorizontalGroup(
            appointmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointmentTabLayout.createSequentialGroup()
                .addGroup(appointmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(appointmentTabLayout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel11))
                    .addGroup(appointmentTabLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(appointmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appointmentTabLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(82, 82, 82)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addGap(28, 28, 28))
                            .addGroup(appointmentTabLayout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(addAppointmentButton)))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        appointmentTabLayout.setVerticalGroup(
            appointmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointmentTabLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(appointmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addAppointmentButton)
                .addGap(22, 22, 22))
        );

        personalAppointmentTab.addTab("Appointments", appointmentTab);

        jLabel12.setText("Regional Customers");

        customerVisibleList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(customerVisibleList);

        jButton2.setText("Add Customer");

        javax.swing.GroupLayout customerTabLayout = new javax.swing.GroupLayout(customerTab);
        customerTab.setLayout(customerTabLayout);
        customerTabLayout.setHorizontalGroup(
            customerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerTabLayout.createSequentialGroup()
                .addGroup(customerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerTabLayout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jButton2))
                    .addGroup(customerTabLayout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(customerTabLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel12)))
                .addContainerGap(232, Short.MAX_VALUE))
        );
        customerTabLayout.setVerticalGroup(
            customerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerTabLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(23, 23, 23))
        );

        personalAppointmentTab.addTab("Customers", customerTab);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icon.setText("             ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(personalAppointmentTab, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(Logout))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(icon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(personalAppointmentTab, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logout)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void AddEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEmployeeButtonActionPerformed
        // TODO add your handling code here:
        try{
            AddEmployee addEmployeeObj = new AddEmployee(openConnection,this,Integer.parseInt(userId),userRId,parentFrame);
            javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(addEmployeeObj);
            addEmployeeObj.setVisible(true);
            frame.repaint();
            frame.revalidate();
        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_AddEmployeeButtonActionPerformed

    private void employeeVisibleListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeVisibleListMouseClicked
        // TODO add your handling code here:
        try{
            javax.swing.JList list = (javax.swing.JList)evt.getSource();
            if (evt.getClickCount() == 2)
            {
                String findEmployeeQuery = "Select * from employee where e_name = ?";
                CallableStatement findEmployeeStmt = openConnection.prepareCall(findEmployeeQuery);
                String selectedEmployee = employeeVisibleList.getSelectedValuesList().get(0);
                findEmployeeStmt.setString(1,selectedEmployee);
                ResultSet foundEmployee = findEmployeeStmt.executeQuery();
                ManagerEditEmp managerEditObject = new ManagerEditEmp(openConnection,this,foundEmployee,Integer.parseInt(userId),parentFrame);
                javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(managerEditObject);
                managerEditObject.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_employeeVisibleListMouseClicked

    private void UpdateInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateInfoButtonActionPerformed
        // TODO add your handling code here:
        try{
            EditEmp editSelf = new EditEmp(openConnection,this,UserResultSet,parentFrame);
            javax.swing.JFrame Frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            Frame.setContentPane(editSelf);
            editSelf.setVisible(true);
            Frame.repaint();
            Frame.revalidate();

        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_UpdateInfoButtonActionPerformed

    private void addPackageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPackageButtonActionPerformed
        // TODO add your handling code here:
        AddPackage addPackageObject = new AddPackage(openConnection,this,parentFrame, userRId, Integer.parseInt(userId));
        javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(addPackageObject);
        addPackageObject.setVisible(true);
        frame.repaint();
        frame.revalidate();
        
    }//GEN-LAST:event_addPackageButtonActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // return back to a new instance of login screen
        try{
            openConnection.close();
            Cccc app = new Cccc();
            parentFrame.dispose();
            app.run();
        }catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void packageVisibleListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_packageVisibleListMouseClicked
        // TODO add your handling code here:
        try{
            javax.swing.JList list = (javax.swing.JList)evt.getSource();
            if (evt.getClickCount() == 2)
            {
                String findPackageQuery = "Select package_id_from_name(?) p_id;";
                CallableStatement findPackageStmt = openConnection.prepareCall(findPackageQuery);
                String selectedPackage = packageVisibleList.getSelectedValuesList().get(0);
                findPackageStmt.setString(1,selectedPackage);
                ResultSet foundPackage = findPackageStmt.executeQuery();
                foundPackage.first();
                String packageId = foundPackage.getString("p_id");
                EditPackage editPackageObject = new EditPackage(openConnection,this,foundPackage,Integer.parseInt(packageId),parentFrame,userRId, Integer.parseInt(userId));
                javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(editPackageObject);
                editPackageObject.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_packageVisibleListMouseClicked

    private void addAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAppointmentButtonActionPerformed
        try{
            AddAppointment addAppointmentObj = new AddAppointment(openConnection,this,Integer.parseInt(userId),userRId,parentFrame);
            javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(addAppointmentObj);
            addAppointmentObj.setVisible(true);
            frame.repaint();
            frame.revalidate();
        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_addAppointmentButtonActionPerformed

    private void appointmentVisibleListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appointmentVisibleListMouseClicked
        // TODO add your handling code here:
        try{
            javax.swing.JList list = (javax.swing.JList)evt.getSource();
            if (evt.getClickCount() == 2)
            {
                Integer index  = appointmentVisibleList.getSelectedIndex();
                appointmentResultSet.first();
                appointmentResultSet.getInt("a_id");
                for (Integer i = 0; i < index; i++)
                    appointmentResultSet.next();
                Integer appointmentId = appointmentResultSet.getInt("a_id");
                String appointmentQuery = "Call get_appointment_info(?)";
                CallableStatement appointmentStmt = openConnection.prepareCall(appointmentQuery);
                appointmentStmt.setInt(1,appointmentId);
                ResultSet appointmentRs = appointmentStmt.executeQuery();
                EditAppointment editAppointmentObject = new EditAppointment(openConnection,this,appointmentRs,Integer.parseInt(userId),parentFrame, userRId);
                javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(editAppointmentObject);
                editAppointmentObject.setVisible(true);
                frame.repaint();
                frame.revalidate();
            }
        }
        catch(Exception e){
            throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_appointmentVisibleListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddEmployeeButton;
    private javax.swing.JButton Logout;
    private static transient javax.swing.JButton UpdateInfoButton;
    private javax.swing.JLabel UserAddress;
    private javax.swing.JLabel UserRole;
    private javax.swing.JButton addAppointmentButton;
    private javax.swing.JButton addPackageButton;
    private javax.swing.JPanel appointmentTab;
    private javax.swing.JList<String> appointmentVisibleList;
    private javax.swing.JPanel customerTab;
    private javax.swing.JList<String> customerVisibleList;
    private javax.swing.JLabel eBranch;
    private javax.swing.JLabel eEmail;
    private javax.swing.JLabel eName;
    private javax.swing.JLabel ePhone;
    private javax.swing.JLabel eSalary;
    private javax.swing.JLabel eStartDate;
    private javax.swing.JList<String> employeeVisibleList;
    private javax.swing.JPanel employeesTab;
    private javax.swing.JPanel homeTab;
    private javax.swing.JLabel icon;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel packageTab;
    private javax.swing.JList<String> packageVisibleList;
    private javax.swing.JTabbedPane personalAppointmentTab;
    // End of variables declaration//GEN-END:variables
}
