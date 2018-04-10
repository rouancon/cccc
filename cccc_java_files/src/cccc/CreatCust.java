/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cccc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author rouancon
 */
public class CreatCust extends javax.swing.JPanel {

    /**
     * Creates new form CreatCust
     */
    public CreatCust() {
        initComponents();
    }

    //variable declarations
    java.awt.Frame frame;
    Connection openConnection;
    int eId;
    int rId;
    int pId = 0;
    Date custSince;
    String name;
    String username;
    String password;
    String phone;
    String email;
    String street;
    String city;
    String state;
    String zip;
    boolean newsletter;
    String bName;
    String ccNum;
    int expM;
    int expY;
    int cvv;
    String bStreet;
    String bCity;
    String bState;
    String bZip;
    java.sql.Date currDate = java.sql.Date.valueOf(LocalDate.now());
    java.sql.Date pStart = java.sql.Date.valueOf(LocalDate.now());
    Calendar c = Calendar.getInstance();
    Date pEnd;
    List<String> packageList = new ArrayList<String>();
    
    public CreatCust(java.awt.Frame parent, Connection connection, int eId, int rId) {
        initComponents();
        frame = parent;
        openConnection = connection;
        this.eId = eId;
        this.rId = rId;
        
        //set pkg end date (1 year from now)
        c.setTime(java.sql.Date.valueOf(LocalDate.now()));
        c.add(Calendar.YEAR, 1);
        Date endDate = new Date(c.getTimeInMillis());
        pEnd = endDate;
        
        try{
            //Pull the dropdown for packages
            String query = "CALL regional_packages(?);";
            CallableStatement stmt = openConnection.prepareCall(query);
            stmt.setInt(1, rId);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            String pkgName;
            packageList.add("<Select>");
            while (rs.next()){
                pkgName = rs.getString("p_name");
                packageList.add(pkgName);
            }
        }catch(Exception e){
            throw new IllegalStateException("error",e);
        }
        
        //Set up the package list
        String[] packageNames = packageList.toArray(new String[packageList.size()]);
        packages.setModel(new javax.swing.DefaultComboBoxModel(packageNames));
        packages.setSelectedIndex(0);
    }
    
    public ResultSet getEmployeeInfo(int eId)
    {
        try{
            String query = "CALL get_e_data(" + eId + ")";
            CallableStatement estmt = openConnection.prepareCall(query);
            ResultSet rs2 = estmt.executeQuery();
            ResultSetMetaData metadata = rs2.getMetaData();
            int columnCount = metadata.getColumnCount();    
            while (rs2.next()) {
                String row = "";
                for (int i = 1; i <= columnCount; i++) {
                    row += rs2.getString(i) + ", ";          
                }
            }
            rs2.first();
            return rs2;
        }
        catch(Exception e){
           throw new IllegalStateException("error",e);
        }
    }
    
    private boolean checkNoneNull(){
        if(
            this.custName.getText().length() == 0 ||
            this.custUsername.getText().length() == 0 ||
            this.custPW.getText().length() == 0 ||
            this.custName1.getText().length() == 0 ||
            this.custPhone.getText().length() == 0 ||
            this.custEmail.getText().length() == 0 ||
            this.custBStreet.getText().length() == 0 ||
            this.custBCity.getText().length() == 0 ||
            this.custBState.getText().length() == 0 ||
            this.custBZip.getText().length() == 0 ||
            this.custSvcStreet.getText().length() == 0 ||
            this.custSvcCity.getText().length() == 0 ||
            this.custSvcState.getText().length() == 0 ||
            this.custSvcZip.getText().length() == 0 ||
            this.custCCNum.getText().length() == 0 ||
            this.custCCExpM.getText().length() == 0 ||
            this.custCCCVV.getText().length() == 0 ||
            this.custCCExpY.getText().length() == 0
        ){
            err_msg.setText("All Fields Required.");
            return false;
        }else if(
            (packages.getSelectedIndex() == 0)
        ){
            err_msg.setText("Package Selection Required");
            return false;
        }else if(
            (!this.custPhone.getText().matches("[0-9]+")) ||
            this.custPhone.getText().length() != 10
        ){
            err_msg.setText("Phone number must have 10 digits");
            return false;
        }else if(
            (!this.custBZip.getText().matches("[0-9]+")) ||
            this.custBZip.getText().length() != 5 ||
            (!this.custSvcZip.getText().matches("[0-9]+")) ||
            this.custSvcZip.getText().length() != 5
        ){
            err_msg.setText("Zip Code must have 5 digits");
            return false;
        }else if(
            (!this.custBState.getText().matches("[a-zA-Z]+")) ||
            this.custBState.getText().length() != 2 ||
            (!this.custSvcState.getText().matches("[a-zA-Z]+")) ||
            this.custSvcState.getText().length() != 2
        ){
            err_msg.setText("State may only have 2 Letters");
            return false;
        }else if(
            this.custCCNum.getText().length() != 16
        ){
            err_msg.setText("CC num must be 16 digits");
            return false;
        }else if(
            this.custCCExpM.getText().length() != 2 ||
            Integer.parseInt(this.custCCExpM.getText()) > 12 ||
            Integer.parseInt(this.custCCExpM.getText()) < 1 ||
            this.custCCExpY.getText().length() != 2
        ){
            err_msg.setText("Enter valid 2 digit dates");
            return false;
        }else if(
            this.custCCCVV.getText().length() != 4
        ){
            err_msg.setText("CVV must be 4 digits");
            return false;
        }else{
            return true;
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

        CardName3 = new javax.swing.JLabel();
        custPhone = new javax.swing.JTextField();
        CardName2 = new javax.swing.JLabel();
        custEmail = new javax.swing.JTextField();
        cNewsletter = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        cBillStreet1 = new javax.swing.JLabel();
        custBStreet = new javax.swing.JTextField();
        cBillCity1 = new javax.swing.JLabel();
        custBCity = new javax.swing.JTextField();
        cBillState1 = new javax.swing.JLabel();
        custBState = new javax.swing.JTextField();
        cBillZip1 = new javax.swing.JLabel();
        custBZip = new javax.swing.JTextField();
        cSave = new javax.swing.JButton();
        cCancel = new javax.swing.JButton();
        cBillState2 = new javax.swing.JLabel();
        custSvcState = new javax.swing.JTextField();
        cBillZip2 = new javax.swing.JLabel();
        custName1 = new javax.swing.JTextField();
        custSvcZip = new javax.swing.JTextField();
        custCCNum = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        custCCExpM = new javax.swing.JTextField();
        cBillStreet2 = new javax.swing.JLabel();
        custCCCVV = new javax.swing.JTextField();
        custSvcStreet = new javax.swing.JTextField();
        cBillCity2 = new javax.swing.JLabel();
        custSvcCity = new javax.swing.JTextField();
        custCCExpY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CardName4 = new javax.swing.JLabel();
        cardTitle2 = new javax.swing.JLabel();
        CardName5 = new javax.swing.JLabel();
        err_msg = new javax.swing.JLabel();
        cBillingTitle1 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        custName = new javax.swing.JTextField();
        cardTitle1 = new javax.swing.JLabel();
        custUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        packages = new javax.swing.JComboBox<>();
        custPW = new javax.swing.JTextField();
        cardTitle3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(595, 452));

        CardName3.setText("Phone:");

        CardName2.setText("Email:");

        cNewsletter.setText("Receive Newsletter");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Billing Address:");

        cBillStreet1.setText("Street:");

        cBillCity1.setText("City:");

        cBillState1.setText("State:");

        cBillZip1.setText("Zip:");

        cSave.setText("Save");
        cSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cSaveActionPerformed(evt);
            }
        });

        cCancel.setText("Cancel");
        cCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cCancelActionPerformed(evt);
            }
        });

        cBillState2.setText("State:");

        cBillZip2.setText("Zip:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Service Address");

        cBillStreet2.setText("Street:");

        cBillCity2.setText("City:");

        jLabel2.setText("/");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Name on Card:");

        jLabel3.setText("Format: MM / YY");

        CardName4.setText("Card Exp Date:");

        cardTitle2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle2.setText("Card Number:");

        CardName5.setText("CVV:");

        err_msg.setForeground(new java.awt.Color(255, 0, 0));
        err_msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cBillingTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cBillingTitle1.setText("Add A Customer");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Account Owner Name:");

        cardTitle1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle1.setText("Username:");

        jLabel1.setText("Select A Package:");

        packages.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        packages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packagesActionPerformed(evt);
            }
        });

        cardTitle3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle3.setText("Temp Password:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(cardTitle1)
                                    .addComponent(CardName3)
                                    .addComponent(CardName2)
                                    .addComponent(jLabel25)
                                    .addComponent(cardTitle2)
                                    .addComponent(CardName4)
                                    .addComponent(CardName5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(custCCCVV, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(custCCExpM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(custCCExpY, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(213, 213, 213)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cNewsletter)
                                                    .addComponent(custPW, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(custUsername, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(custName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(custPhone)
                                        .addComponent(custEmail)
                                        .addComponent(custName1)
                                        .addComponent(custCCNum)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(err_msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cCancel)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(packages, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cBillStreet1)
                                            .addComponent(cBillCity1)
                                            .addComponent(cBillState1)
                                            .addComponent(cBillZip1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(custBCity, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(custBState, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(custBZip, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(custBStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cBillStreet2)
                                                    .addComponent(cBillCity2)
                                                    .addComponent(cBillState2)
                                                    .addComponent(cBillZip2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(custSvcCity, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(custSvcState, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(custSvcZip, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(custSvcStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(83, 83, 83))))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cardTitle3)
                                        .addComponent(jLabel24)))))
                        .addGap(28, 28, 28))))
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(cBillingTitle1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cBillingTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(custName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardTitle1)
                            .addComponent(custUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(packages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CardName3)
                            .addComponent(custPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cNewsletter, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CardName2)
                    .addComponent(custEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(custName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardTitle2)
                    .addComponent(custCCNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CardName4)
                    .addComponent(custCCExpM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(custCCExpY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CardName5)
                    .addComponent(custCCCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardTitle3)
                    .addComponent(custPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillStreet1)
                            .addComponent(custBStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillCity1)
                            .addComponent(custBCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillState1)
                            .addComponent(custBState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillZip1)
                            .addComponent(custBZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillStreet2)
                            .addComponent(custSvcStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillCity2)
                            .addComponent(custSvcCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillState2)
                            .addComponent(custSvcState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillZip2)
                            .addComponent(custSvcZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(err_msg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cCancel)
                        .addComponent(cSave)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cSaveActionPerformed
        // Save pressed
        if(checkNoneNull()) {
            try {
                //Get pId from drop-down and DB
                String selectedPkgName = packages.getSelectedItem().toString();
                String getPid= "SELECT package_id_from_name(?) AS p_id;";
                CallableStatement stmt = openConnection.prepareCall(getPid);
                stmt.setString(1, selectedPkgName);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                pId = rs.getInt("p_id");
                
                name = this.custName.getText();
                bName = this.custName1.getText();
                username = custUsername.getText();
                password = custPW.getText();
                phone = this.custPhone.getText();
                email = this.custEmail.getText();
                street = this.custSvcStreet.getText();
                city = this.custSvcCity.getText();
                state = this.custSvcState.getText();
                zip = this.custSvcZip.getText();
                bStreet = this.custBStreet.getText();
                bCity = this.custBCity.getText();
                bState = this.custBState.getText();
                bZip = this.custBZip.getText();
                newsletter = this.cNewsletter.isSelected();
                ccNum = this.custCCNum.getText();
                expM = Integer.parseInt(this.custCCExpM.getText());
                expY = Integer.parseInt(this.custCCExpY.getText());
                cvv = Integer.parseInt(this.custCCCVV.getText());

                String query = "INSERT INTO customer SET c_cc_name=?, c_cc_number=?, c_cc_expiration_month=?, c_cc_expiration_year=?, c_cc_cvv=?, c_billing_street=?, c_billing_city=?, c_billing_state=?, c_billing_zip=?, c_street_address=?, c_city=?, c_state=?, c_zip=?, c_name=?, c_username=?, c_phone=?, c_email=?, c_newsletter=?, p_id=?, c_customer_since=?, r_id=?, c_package_start=?, c_package_end=?, c_password=?;";
                stmt = openConnection.prepareCall(query);
                stmt.setString(1, bName);
                stmt.setString(2, ccNum);
                stmt.setInt(3, expM);
                stmt.setInt(4, expY);
                stmt.setInt(5, cvv);
                stmt.setString(6, bStreet);
                stmt.setString(7, bCity);
                stmt.setString(8, bState);
                stmt.setInt(9, Integer.parseInt(bZip));
                stmt.setString(10, street);
                stmt.setString(11, city);
                stmt.setString(12, state);
                stmt.setInt(13, Integer.parseInt(zip));
                stmt.setString(14, name);
                stmt.setString(15, username);
                stmt.setString(16, phone);
                stmt.setString(17, email);
                stmt.setBoolean(18, newsletter);
                stmt.setInt(19,pId);
                stmt.setDate(20,currDate);
                stmt.setInt(21, rId);
                stmt.setDate(22,pStart);
                stmt.setDate(23,pEnd);
                stmt.setString(24, password);
                stmt.execute();

                ResultSet employeeInfo = getEmployeeInfo(eId);
                EmployeeHome EHome = new EmployeeHome(employeeInfo,openConnection,frame);
                this.setVisible(false);
                javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                f.setContentPane(EHome);
                EHome.setVisible(true);
                f.revalidate();
                f.repaint();
                
            } catch(Exception e) {
                throw new IllegalStateException("error",e);
            }
        }
    }//GEN-LAST:event_cSaveActionPerformed

    private void cCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cCancelActionPerformed
        // Cancel Pressed

        ResultSet employeeInfo = getEmployeeInfo(eId);
        EmployeeHome EHome = new EmployeeHome(employeeInfo,openConnection,frame);
        this.setVisible(false);
        javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        f.setContentPane(EHome);
        EHome.setVisible(true);
        f.revalidate();
        f.repaint();
    }//GEN-LAST:event_cCancelActionPerformed

    private void packagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packagesActionPerformed

    }//GEN-LAST:event_packagesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CardName2;
    private javax.swing.JLabel CardName3;
    private javax.swing.JLabel CardName4;
    private javax.swing.JLabel CardName5;
    private javax.swing.JLabel cBillCity1;
    private javax.swing.JLabel cBillCity2;
    private javax.swing.JLabel cBillState1;
    private javax.swing.JLabel cBillState2;
    private javax.swing.JLabel cBillStreet1;
    private javax.swing.JLabel cBillStreet2;
    private javax.swing.JLabel cBillZip1;
    private javax.swing.JLabel cBillZip2;
    private javax.swing.JLabel cBillingTitle1;
    private javax.swing.JButton cCancel;
    private javax.swing.JCheckBox cNewsletter;
    private javax.swing.JButton cSave;
    private javax.swing.JLabel cardTitle1;
    private javax.swing.JLabel cardTitle2;
    private javax.swing.JLabel cardTitle3;
    private javax.swing.JTextField custBCity;
    private javax.swing.JTextField custBState;
    private javax.swing.JTextField custBStreet;
    private javax.swing.JTextField custBZip;
    private javax.swing.JTextField custCCCVV;
    private javax.swing.JTextField custCCExpM;
    private javax.swing.JTextField custCCExpY;
    private javax.swing.JTextField custCCNum;
    private javax.swing.JTextField custEmail;
    private javax.swing.JTextField custName;
    private javax.swing.JTextField custName1;
    private javax.swing.JTextField custPW;
    private javax.swing.JTextField custPhone;
    private javax.swing.JTextField custSvcCity;
    private javax.swing.JTextField custSvcState;
    private javax.swing.JTextField custSvcStreet;
    private javax.swing.JTextField custSvcZip;
    private javax.swing.JTextField custUsername;
    private javax.swing.JLabel err_msg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JComboBox<String> packages;
    // End of variables declaration//GEN-END:variables
}
