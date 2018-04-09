/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cccc;

import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author rouancon
 */
public class EmployeeEditCust extends javax.swing.JPanel {

    /**
     * Creates new form EmployeeEditCust
     */
    public EmployeeEditCust() {
        initComponents();
    }
    
    //variable declarations
    java.awt.Frame frame;
    Connection openConnection;
    int id;
    int eId;
    Date custSince;
    String name;
    String username;
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
    
    public EmployeeEditCust(java.awt.Frame parent, Connection connection, int cId, int eId) {
        initComponents();
        frame = parent;
        id = cId;
        openConnection = connection;
        this.eId = eId;
        
        //get the current values from the DB
        try{
            String query = "CALL get_cust_info(?);";
            CallableStatement stmt = openConnection.prepareCall(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            name = rs.getString("c_name");
            username = rs.getString("c_username");
            phone = rs.getString("c_phone");
            email = rs.getString("c_email");
            street = rs.getString("c_street_address");
            city = rs.getString("c_city");
            state = rs.getString("c_state");
            zip = Integer.toString(rs.getInt("c_zip"));
            if (zip.length() == 4)
            {
                zip = "0" + zip;
            }
            bStreet = rs.getString("c_billing_street");
            bCity = rs.getString("c_billing_city");
            bState = rs.getString("c_billing_state");
            bZip = Integer.toString(rs.getInt("c_billing_zip"));
            if (bZip.length() == 4)
            {
                bZip = "0" + bZip;
            }
            newsletter = rs.getBoolean("c_newsletter");
            bName = rs.getString("c_cc_name");
            custSince = rs.getDate("c_customer_since");
            
        }catch(Exception e){
            throw new IllegalStateException("error",e);
        }
        
        //set the fields with info from DB
        this.actNum.setText(Integer.toString(id));
        this.custName.setText(name);
        this.custUsername.setText(username);
        this.custPhone.setText(phone);
        this.custEmail.setText(email);
        this.custSvcStreet.setText(street);
        this.custSvcCity.setText(city);
        this.custSvcState.setText(state);
        this.custSvcZip.setText(zip);
        this.custBStreet.setText(bStreet);
        this.custBCity.setText(bCity);
        this.custBState.setText(bState);
        this.custBZip.setText(bZip);
        this.cNewsletter.setSelected(newsletter);
        this.custName1.setText(bName);
        this.custDate.setText(custSince.toString());
    }
    
    private boolean checkNoneNull(){
        if(
            this.custName.getText() == null ||
            this.custUsername.getText() == null ||
            this.custName1.getText() == null ||
            this.custPhone.getText() == null ||
            this.custEmail.getText() == null ||
            this.custBStreet.getText() == null ||
            this.custBCity.getText()== null ||
            this.custBState.getText() == null ||
            this.custBZip.getText() == null ||
            this.custSvcStreet.getText() == null ||
            this.custSvcCity.getText()== null ||
            this.custSvcState.getText() == null ||
            this.custSvcZip.getText() == null
        ){
            err_msg.setText("All Fields Required. CC Info may be left blank.");
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
            this.custCCNum.getText().length() != 0 ||
            this.custCCExpM.getText().length() != 0 ||
            this.custCCCVV.getText().length() != 0 ||
            this.custCCExpY.getText().length() != 0
        ){
            if(
                this.custCCNum.getText().length() > 16 ||
                this.custCCNum.getText().length() < 14 ||
                this.custCCExpM.getText().length() != 2 ||
                this.custCCCVV.getText().length() != 4 ||
                this.custCCExpY.getText().length() != 2
            ){
                err_msg.setText("Invalid Card Entry");
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    
    public ResultSet getEmployeeInfo(int eId)
    {
        try{
            String query = "Call get_e_data(" + eId + ")";
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cBillingTitle1 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        custName = new javax.swing.JTextField();
        cardTitle1 = new javax.swing.JLabel();
        custUsername = new javax.swing.JTextField();
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
        jLabel1 = new javax.swing.JLabel();
        actNum = new javax.swing.JLabel();
        cBillState2 = new javax.swing.JLabel();
        custSvcState = new javax.swing.JTextField();
        cBillZip2 = new javax.swing.JLabel();
        custSvcZip = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cBillStreet2 = new javax.swing.JLabel();
        custSvcStreet = new javax.swing.JTextField();
        cBillCity2 = new javax.swing.JLabel();
        custSvcCity = new javax.swing.JTextField();
        custName1 = new javax.swing.JTextField();
        custCCNum = new javax.swing.JTextField();
        custCCExpM = new javax.swing.JTextField();
        custCCCVV = new javax.swing.JTextField();
        custCCExpY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CardName4 = new javax.swing.JLabel();
        cardTitle2 = new javax.swing.JLabel();
        CardName5 = new javax.swing.JLabel();
        err_msg = new javax.swing.JLabel();
        resetPwd = new javax.swing.JButton();
        chgPkg = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        custDate = new javax.swing.JLabel();
        deleteEmployeeButton3 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(595, 452));

        cBillingTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cBillingTitle1.setText("View/Edit Customer Information");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Account Owner Name:");

        custName.setText("ContainsCustName");

        cardTitle1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle1.setText("Username:");

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

        jLabel1.setText("Account Number:");

        actNum.setText("4");

        cBillState2.setText("State:");

        cBillZip2.setText("Zip:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Service Address");

        cBillStreet2.setText("Street:");

        cBillCity2.setText("City:");

        custName1.setText("ContainsCustName");

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

        resetPwd.setText("Reset Password");
        resetPwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPwdActionPerformed(evt);
            }
        });

        chgPkg.setText("Change Package");

        jLabel4.setText("Customer Since:");

        custDate.setText("01/01/2019");

        deleteEmployeeButton3.setText("Delete Employee");
        deleteEmployeeButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmployeeButton3ActionPerformed(evt);
            }
        });

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
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(custUsername, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(custName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addComponent(custPhone)
                                        .addComponent(custEmail)
                                        .addComponent(custName1)
                                        .addComponent(custCCNum))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(custCCCVV, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(custCCExpM, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(custCCExpY, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel1)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(actNum, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(cNewsletter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resetPwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(chgPkg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addComponent(custDate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(62, 62, 62)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
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
                                    .addComponent(custSvcStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cBillingTitle1)
                        .addGap(189, 189, 189))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(deleteEmployeeButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(err_msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cCancel)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteEmployeeButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cBillingTitle1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(custName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(actNum))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cardTitle1)
                                    .addComponent(custUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cNewsletter))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CardName3)
                                    .addComponent(custPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CardName2)
                                    .addComponent(custEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(resetPwd)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(custName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cardTitle2)
                                    .addComponent(custCCNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(chgPkg)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CardName4)
                            .addComponent(custCCExpM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(custCCExpY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CardName5)
                            .addComponent(custCCCVV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(custDate))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cCancel)
                            .addComponent(cSave)
                            .addComponent(err_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cSaveActionPerformed
        // Save pressed
        if(checkNoneNull()) {
            try {
                name = this.custName.getText();
                bName = this.custName1.getText();
                username = custUsername.getText();
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
                
                if(
                    this.custCCNum.getText().length() != 0 ||
                    this.custCCExpM.getText().length() != 0 ||
                    this.custCCCVV.getText().length() != 0 ||
                    this.custCCExpY.getText().length() != 0
                ){
                    ccNum = this.custCCNum.getText();
                    expM = Integer.parseInt(this.custCCExpM.getText());
                    expY = Integer.parseInt(this.custCCExpY.getText());
                    cvv = Integer.parseInt(this.custCCCVV.getText());
                    
                    String query = "UPDATE customer SET c_cc_name=?, c_cc_number=?, c_cc_expiration_month=?, c_cc_expiration_year=?, c_cc_cvv=?, c_billing_street=?, c_billing_city=?, c_billing_state=?, c_billing_zip=?, c_street_address=?, c_city=?, c_state=?, c_zip=?, c_name=?, c_username=?, c_phone=?, c_email=?, c_newsletter=? WHERE c_id=?;";
                    CallableStatement stmt = openConnection.prepareCall(query);
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
                    stmt.setInt(19, id);
                    stmt.executeUpdate();
                    
                    ResultSet employeeInfo = getEmployeeInfo(eId);
                    EmployeeHome EHome = new EmployeeHome(employeeInfo,openConnection,frame);
                    this.setVisible(false);
                    javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                    f.setContentPane(EHome);
                    EHome.setVisible(true);
                    f.revalidate();
                    f.repaint();
                }else{
                    String query = "UPDATE customer SET c_cc_name=?, c_billing_street=?, c_billing_city=?, c_billing_state=?, c_billing_zip=?, c_street_address=?, c_city=?, c_state=?, c_zip=?, c_name=?, c_username=?, c_phone=?, c_email=?, c_newsletter=? WHERE c_id=?;";
                    CallableStatement stmt = openConnection.prepareCall(query);
                    stmt.setString(1, bName);
                    stmt.setString(2, bStreet);
                    stmt.setString(3, bCity);
                    stmt.setString(4, bState);
                    stmt.setInt(5, Integer.parseInt(bZip));
                    stmt.setString(6, street);
                    stmt.setString(7, city);
                    stmt.setString(8, state);
                    stmt.setInt(9, Integer.parseInt(zip));
                    stmt.setString(10, name);
                    stmt.setString(11, username);
                    stmt.setString(12, phone);
                    stmt.setString(13, email);
                    stmt.setBoolean(14, newsletter);
                    stmt.setInt(15, id);
                    stmt.executeUpdate();
                    
                    ResultSet employeeInfo = getEmployeeInfo(eId);
                    EmployeeHome EHome = new EmployeeHome(employeeInfo,openConnection,frame);
                    this.setVisible(false);
                    javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                    f.setContentPane(EHome);
                    EHome.setVisible(true);
                    f.revalidate();
                    f.repaint();
                }
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

    private void resetPwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPwdActionPerformed
        //generate, store, and display a temp password
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String tempPW = buffer.toString();
        
        try{
            String query = "UPDATE customer SET c_password=? WHERE c_id=?;";
            CallableStatement stmt = openConnection.prepareCall(query);
            stmt.setString(1, tempPW);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Success! New Temporary Password is: " + tempPW, "Password Reset", JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e) {
                throw new IllegalStateException("error",e);
        }
    }//GEN-LAST:event_resetPwdActionPerformed

    private void deleteEmployeeButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEmployeeButton3ActionPerformed
        try{
            String deleteCustQuery  = "DELETE FROM customer WHERE c_id = ?";
            CallableStatement deleteCustStatement = openConnection.prepareCall(deleteCustQuery);
            deleteCustStatement.setInt(1, id);
            deleteCustStatement.execute();

            ResultSet employeeInfo = getEmployeeInfo(eId);
            EmployeeHome EHome = new EmployeeHome(employeeInfo,openConnection,frame);
            this.setVisible(false);
            javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            f.setContentPane(EHome);
            EHome.setVisible(true);
            f.revalidate();
            f.repaint();
        }
        catch(Exception E){
            String Msg = "" + E;
            String ErrorMsg = Msg.split(":")[1];
            System.out.println(ErrorMsg);
            if (ErrorMsg.equals(" Cannot delete or update a parent row"))
            {
                ErrorMsg = "Employee has open Appointments. Cannot Delete Them";
                System.out.println(ErrorMsg);
            }
            err_msg.setText(ErrorMsg);
            err_msg.setVisible(true);
        }
    }//GEN-LAST:event_deleteEmployeeButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CardName2;
    private javax.swing.JLabel CardName3;
    private javax.swing.JLabel CardName4;
    private javax.swing.JLabel CardName5;
    private javax.swing.JLabel actNum;
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
    private javax.swing.JButton chgPkg;
    private javax.swing.JTextField custBCity;
    private javax.swing.JTextField custBState;
    private javax.swing.JTextField custBStreet;
    private javax.swing.JTextField custBZip;
    private javax.swing.JTextField custCCCVV;
    private javax.swing.JTextField custCCExpM;
    private javax.swing.JTextField custCCExpY;
    private javax.swing.JTextField custCCNum;
    private javax.swing.JLabel custDate;
    private javax.swing.JTextField custEmail;
    private javax.swing.JTextField custName;
    private javax.swing.JTextField custName1;
    private javax.swing.JTextField custPhone;
    private javax.swing.JTextField custSvcCity;
    private javax.swing.JTextField custSvcState;
    private javax.swing.JTextField custSvcStreet;
    private javax.swing.JTextField custSvcZip;
    private javax.swing.JTextField custUsername;
    private javax.swing.JButton deleteEmployeeButton;
    private javax.swing.JButton deleteEmployeeButton1;
    private javax.swing.JButton deleteEmployeeButton2;
    private javax.swing.JButton deleteEmployeeButton3;
    private javax.swing.JLabel err_msg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton resetPwd;
    // End of variables declaration//GEN-END:variables
}
