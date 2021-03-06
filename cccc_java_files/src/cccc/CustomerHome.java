/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cccc;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author rouancon
 */
public class CustomerHome extends javax.swing.JPanel {

    /** Creates new form CustomerHome */
    public CustomerHome() {
        initComponents();
    }
    
    //variable declarations
    Frame parentFrame;
    Connection openConnection;
    int actNum;
    String custName;
    String regionName;
    String billName;
    String ccNum;
    String billStreet;
    String billCity;
    String billState;
    String billZip;
    String username;
    String password;
    Date customerSince;
    Date pkgStart;
    Date pkgEnd;
    String phone;
    String email;
    boolean newsletter;
    String street;
    String city;
    String state;
    String zip;
    int pkgId;
    String pkgName;
    int pkgPrice;
    String[] serviceType = {"none","none","none"};
    String[] services = {"none","none","none"};
    boolean isApt = false;
    Date aptTime;
    String aptTechName;
    int aptPrice;
    String aptNotes;
    
    //creates populated CustomerHome view
    public CustomerHome(Connection connection, int cId, JFrame parentFrame) {
        initComponents(); //initialize fields
        openConnection = connection; //pass in active connection
        
        //set the frame the JPanel is contained in
        this.parentFrame = parentFrame;
        Dimension d = new Dimension(650,575);
        parentFrame.setPreferredSize(d);
        parentFrame.pack();
        
        actNum = cId;
        getCustData(cId); //load/populate values from DB
    }
    
    //takes a customer ID and populates fields with stored values
    public void getCustData(int cId) {
        //pull customer data using passed in cid
        try{
            //get basic customer info
            String query = "CALL get_cust_info(?);";
            CallableStatement stmt = openConnection.prepareCall(query);
            stmt.setInt(1, cId);
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            custName = rs.getString("c_name");
            regionName = rs.getString("r_name");
            billName = rs.getString("c_cc_name");
            ccNum = rs.getString("c_cc_number");
            ccNum = ccNum.substring(ccNum.length() - 4); //only card last 4 digits
            ccNum = "***********" + ccNum;
            billStreet = rs.getString("c_billing_street");
            billCity = rs.getString("c_billing_city");
            billState = rs.getString("c_billing_state");
            billZip = Integer.toString(rs.getInt("c_billing_zip"));
            if(billZip.length() == 4){
                billZip = "0"+billZip;
            }
            username = rs.getString("c_username");
            password = rs.getString("c_password");
            customerSince = rs.getDate("c_customer_since");
            pkgStart = rs.getDate("c_package_start");
            pkgEnd = rs.getDate("c_package_end");
            phone = rs.getString("c_phone");
            email = rs.getString("c_email");
            newsletter = rs.getBoolean("c_newsletter");
            street = rs.getString("c_street_address");
            city = rs.getString("c_city");
            state = rs.getString("c_state");
            zip = Integer.toString(rs.getInt("c_zip"));
            if(zip.length() == 4){
                zip = "0"+zip;
            }
            pkgId = rs.getInt("p_id");
            pkgName = rs.getString("p_name");
            pkgPrice = rs.getInt("p_price");
            
            //get package services data
            query = "CALL get_cust_pkgs(?);";
            stmt = openConnection.prepareCall(query);
            stmt.setInt(1, 10);
            rs = stmt.executeQuery();
            
            int i = 0;
            while(rs.next()) {
                serviceType[i] = rs.getString("s_type");
                services[i] = rs.getString("s_name");
                i++;
            }
            
            //get any appointment data
            query = "CALL get_cust_apt(?);";
            stmt = openConnection.prepareCall(query);
            stmt.setInt(1, cId);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                isApt = true;
                aptTime = rs.getDate("a_time");
                aptTechName = rs.getString("e_name");
                aptPrice = rs.getInt("a_price");
                aptNotes = rs.getString("a_notes");
            }
            
            //POPULATE FIELDS
            //home tab
            cName.setText(custName);
            cActNum.setText(Integer.toString(actNum));
            cSinceDate.setText(customerSince.toString());
            cSvcStreet.setText(street);
            cSvcCity.setText(city);
            cSvcState.setText(state);
            cSvcZip.setText(zip);
            cUsername.setText(username);
            cEmail.setText(email);
            cPhone.setText(phone);
            if(!newsletter){
                cEmailSub.setText("Unsubscribed");
            } else {
                cEmailSub.setText("Subscribed");
            }
            //myServices tab
            cPkgName.setText(pkgName);
            cPkgExpDate.setText(pkgEnd.toString());
            cSvc1.setText(services[0]);
            cSvc2.setText(services[1]);
            cSvc3.setText(services[2]);
            cSvcStreet2.setText(street);
            cSvcCity2.setText(city);
            cSvcState2.setText(state);
            cSvcZip2.setText(zip);
            if(isApt){
                cADate.setText(aptTime.toString());
                cATech.setText(aptTechName);
                cAPrice.setText(Integer.toString(aptPrice));
                cANotes.setText(aptNotes);
            }
            //billing tab
            cardName.setText(billName);
            cCCNum.setText(ccNum);
            cBillStreet.setText(billStreet);
            cBillCity.setText(billCity);
            cBillState.setText(billState);
            cBillZip.setText(billZip);
            cPkgName1.setText(pkgName);
            cPkgPrice1.setText("$" + Integer.toString(pkgPrice) + "/mo");
            cDate1.setText(pkgEnd.toString());
            //contact tab
            cRegion.setText(regionName);
            
        }catch(Exception e){
           throw new IllegalStateException("error",e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        cName2 = new javax.swing.JLabel();
        cEmailField1 = new javax.swing.JLabel();
        cEmail1 = new javax.swing.JLabel();
        CardName5 = new javax.swing.JLabel();
        cMenu = new javax.swing.JTabbedPane();
        cHome = new javax.swing.JPanel();
        cProfile = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cActNum = new javax.swing.JLabel();
        cSvcStreet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cSvcCity = new javax.swing.JLabel();
        cSvcState = new javax.swing.JLabel();
        cSvcZip = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        cUsername = new javax.swing.JLabel();
        cChangePwd = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        cEmailField = new javax.swing.JLabel();
        cEmail = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cSinceDate = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        cEmailField2 = new javax.swing.JLabel();
        cEmailSub = new javax.swing.JLabel();
        cEmailField3 = new javax.swing.JLabel();
        cPhone = new javax.swing.JLabel();
        cEditInfo = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        myServices = new javax.swing.JPanel();
        cBillingTitle = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        cPkgName = new javax.swing.JLabel();
        cardTitle = new javax.swing.JLabel();
        cSvc1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cSvcStreet2 = new javax.swing.JLabel();
        cSvcCity2 = new javax.swing.JLabel();
        cSvcState2 = new javax.swing.JLabel();
        cSvcZip2 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cADate = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cATech = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cAPrice = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        cPkgExpDate = new javax.swing.JLabel();
        cSvc2 = new javax.swing.JLabel();
        cSvc3 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cANotes = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cBilling = new javax.swing.JPanel();
        cDate1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cPkgPrice1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cPkgName1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        cBillZip = new javax.swing.JLabel();
        cBillState = new javax.swing.JLabel();
        cBillCity = new javax.swing.JLabel();
        cBillStreet = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        cEditBilling = new javax.swing.JButton();
        cCCNum = new javax.swing.JLabel();
        cardTitle1 = new javax.swing.JLabel();
        cardName = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cBillingTitle1 = new javax.swing.JLabel();
        contactUs = new javax.swing.JPanel();
        cBillStreet2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        cRegion = new javax.swing.JLabel();
        cardTitle2 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cBillingTitle2 = new javax.swing.JLabel();
        Logout = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Account Information");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Account Number:");

        cName2.setText("OwnerName");

        cEmailField1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cEmailField1.setText("Email:");

        cEmail1.setText("email@domain.com");

        CardName5.setText("SVC1");

        setMinimumSize(new java.awt.Dimension(650, 450));

        cProfile.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cProfile.setText("Your Profile");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Account Name:");

        cName.setText("OwnerName");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Account Number:");

        cActNum.setText("actNum");

        cSvcStreet.setText("1234 Some Street");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Service Address:");

        cSvcCity.setText("City,");

        cSvcState.setText("State");

        cSvcZip.setText("ZipCode");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Account Information");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Login Information");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Username:");

        cUsername.setText("OwnerName");

        cChangePwd.setText("Change Password");
        cChangePwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cChangePwdActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Contact Information");

        cEmailField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cEmailField.setText("Email:");

        cEmail.setText("email@domain.com");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Customer Since:");

        cSinceDate.setText("Date");

        cEmailField2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cEmailField2.setText("Newsletter:");

        cEmailSub.setText("Subscribed");

        cEmailField3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cEmailField3.setText("Phone:");

        cPhone.setText("1234567890");

        cEditInfo.setText("Edit Your Info");
        cEditInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cEditInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cHomeLayout = new javax.swing.GroupLayout(cHome);
        cHome.setLayout(cHomeLayout);
        cHomeLayout.setHorizontalGroup(
            cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cHomeLayout.createSequentialGroup()
                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(cHomeLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(cUsername)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cChangePwd)
                        .addGap(108, 108, 108))
                    .addGroup(cHomeLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cHomeLayout.createSequentialGroup()
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cName)
                                    .addComponent(cSinceDate)
                                    .addComponent(cActNum))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(cHomeLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(cHomeLayout.createSequentialGroup()
                                                .addComponent(cSvcCity)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cSvcState))
                                            .addComponent(cSvcStreet)
                                            .addComponent(cSvcZip))))
                                .addGap(202, 202, 202))
                            .addGroup(cHomeLayout.createSequentialGroup()
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cEmailField3)
                                    .addComponent(cEmailField))
                                .addGap(18, 18, 18)
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(cHomeLayout.createSequentialGroup()
                                        .addComponent(cEmail)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cEmailField2)
                                        .addGap(18, 18, 18)
                                        .addComponent(cEmailSub)
                                        .addGap(175, 175, 175))
                                    .addGroup(cHomeLayout.createSequentialGroup()
                                        .addComponent(cPhone)
                                        .addGap(513, 513, 513))))))
                    .addGroup(cHomeLayout.createSequentialGroup()
                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(cHomeLayout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(cProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cHomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cEditInfo)
                    .addComponent(jLabel27))
                .addGap(294, 294, 294))
        );
        cHomeLayout.setVerticalGroup(
            cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cProfile)
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cHomeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cSinceDate))
                        .addGap(21, 21, 21))
                    .addGroup(cHomeLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cHomeLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cSvcStreet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cSvcCity)
                                    .addComponent(cSvcState))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(cSvcZip))
                            .addGroup(cHomeLayout.createSequentialGroup()
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(cName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cActNum))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cUsername)
                    .addComponent(cChangePwd))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cEmailField)
                    .addComponent(cEmail)
                    .addComponent(cEmailField2)
                    .addComponent(cEmailSub))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cEmailField3)
                    .addComponent(cPhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(cEditInfo)
                .addGap(26, 26, 26)
                .addComponent(jLabel27))
        );

        cMenu.addTab("Home", cHome);

        cBillingTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cBillingTitle.setText("Enrolled Services");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Service Information");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Package Name:");

        cPkgName.setText("PkgName");

        cardTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle.setText("Services Included:");

        cSvc1.setText("SVC1");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Service Address:");

        cSvcStreet2.setText("1234 Some Street");

        cSvcCity2.setText("City,");

        cSvcState2.setText("State");

        cSvcZip2.setText("ZipCode");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Upcoming Appointments");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Appointment Day/Time:");

        cADate.setText("None");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Tech Name:");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Expected Price:");

        label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label.setText("Expiration Date:");

        cPkgExpDate.setText("ExpDate");

        cSvc2.setText("SVC2");

        cSvc3.setText("SVC3");
        cSvc3.setMinimumSize(new java.awt.Dimension(0, 0));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Notes:");

        cANotes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cANotes.setText("None");
        cANotes.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Contact us to upgrade or change your services today!");

        javax.swing.GroupLayout myServicesLayout = new javax.swing.GroupLayout(myServices);
        myServices.setLayout(myServicesLayout);
        myServicesLayout.setHorizontalGroup(
            myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myServicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator6)
                            .addGroup(myServicesLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(myServicesLayout.createSequentialGroup()
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label)
                                    .addComponent(jLabel12))
                                .addGap(28, 28, 28)
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cPkgName)
                                    .addComponent(cPkgExpDate)))
                            .addComponent(cardTitle)
                            .addGroup(myServicesLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(cSvc1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(myServicesLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(myServicesLayout.createSequentialGroup()
                                        .addComponent(cSvcCity2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cSvcState2))
                                    .addComponent(cSvcStreet2)
                                    .addComponent(cSvcZip2))))
                        .addGap(167, 167, 167))
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7)
                            .addComponent(jLabel14)
                            .addGroup(myServicesLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, myServicesLayout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addGap(18, 18, 18))
                                        .addGroup(myServicesLayout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addGap(32, 32, 32)))
                                    .addGroup(myServicesLayout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(24, 24, 24)))
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cADate)
                                    .addComponent(cATech, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(cAPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(93, 93, 93)
                                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(myServicesLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(cANotes, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel25))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, myServicesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cBillingTitle)
                .addGap(252, 252, 252))
            .addGroup(myServicesLayout.createSequentialGroup()
                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cSvc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cSvc2)))
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        myServicesLayout.setVerticalGroup(
            myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myServicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cBillingTitle)
                .addGap(13, 13, 13)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cSvcStreet2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cSvcCity2)
                            .addComponent(cSvcState2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cSvcZip2))
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cPkgName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label)
                            .addComponent(cPkgExpDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cardTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cSvc1)))
                .addGap(1, 1, 1)
                .addComponent(cSvc2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cSvc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cADate)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cATech, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(myServicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cAPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addGap(23, 23, 23))
                    .addGroup(myServicesLayout.createSequentialGroup()
                        .addComponent(cANotes, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        cMenu.addTab("MyServices", myServices);

        cDate1.setText("Date");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Expiration Date:");

        cPkgPrice1.setText("$Price");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Package Price:");

        cPkgName1.setText("Package Name");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Current Package:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Upcoming Billing Status");

        cBillZip.setText("ZipCode");

        cBillState.setText("State");

        cBillCity.setText("City,");

        cBillStreet.setText("1234 Some Street");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Billing Address:");

        cEditBilling.setText("Edit Billing Info");
        cEditBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cEditBillingActionPerformed(evt);
            }
        });

        cCCNum.setText("***********2736");

        cardTitle1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle1.setText("Card Number:");

        cardName.setText("CardName");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Name on Card:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("Billing Information");

        cBillingTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cBillingTitle1.setText("Billing");

        javax.swing.GroupLayout cBillingLayout = new javax.swing.GroupLayout(cBilling);
        cBilling.setLayout(cBillingLayout);
        cBillingLayout.setHorizontalGroup(
            cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cBillingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cBillingTitle1)
                .addGap(289, 289, 289))
            .addGroup(cBillingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cBillingLayout.createSequentialGroup()
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9)
                            .addGroup(cBillingLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(cBillingLayout.createSequentialGroup()
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cBillingLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(cardTitle1))
                                .addGap(18, 18, 18)
                                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cCCNum)
                                    .addComponent(cardName)))
                            .addGroup(cBillingLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(cEditBilling)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(cBillingLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(cBillingLayout.createSequentialGroup()
                                        .addComponent(cBillCity)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cBillState))
                                    .addComponent(cBillStreet)
                                    .addComponent(cBillZip))))
                        .addGap(167, 167, 167))
                    .addGroup(cBillingLayout.createSequentialGroup()
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8)
                            .addGroup(cBillingLayout.createSequentialGroup()
                                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addGroup(cBillingLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cBillingLayout.createSequentialGroup()
                                                    .addComponent(jLabel20)
                                                    .addGap(18, 18, 18))
                                                .addGroup(cBillingLayout.createSequentialGroup()
                                                    .addComponent(jLabel19)
                                                    .addGap(32, 32, 32)))
                                            .addGroup(cBillingLayout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addGap(24, 24, 24)))
                                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cDate1)
                                            .addComponent(cPkgPrice1)
                                            .addComponent(cPkgName1))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        cBillingLayout.setVerticalGroup(
            cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cBillingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cBillingTitle1)
                .addGap(13, 13, 13)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cBillingLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cBillStreet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cBillCity)
                            .addComponent(cBillState))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cBillZip))
                    .addGroup(cBillingLayout.createSequentialGroup()
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(cardName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cardTitle1)
                            .addComponent(cCCNum))
                        .addGap(14, 14, 14)
                        .addComponent(cEditBilling)))
                .addGap(44, 44, 44)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cPkgName1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cPkgPrice1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cBillingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cDate1))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        cMenu.addTab("Billing", cBilling);

        cBillStreet2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cBillStreet2.setText("123-456-0789");

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Our Number:");

        cRegion.setText("regionName");

        cardTitle2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardTitle2.setText("Region:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("Contact Information");

        cBillingTitle2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cBillingTitle2.setText("Contact Us");

        javax.swing.GroupLayout contactUsLayout = new javax.swing.GroupLayout(contactUs);
        contactUs.setLayout(contactUsLayout);
        contactUsLayout.setHorizontalGroup(
            contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contactUsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cBillingTitle2)
                .addGap(289, 289, 289))
            .addGroup(contactUsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contactUsLayout.createSequentialGroup()
                        .addGroup(contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator11)
                            .addGroup(contactUsLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(contactUsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cardTitle2)
                        .addGap(32, 32, 32)
                        .addComponent(cRegion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                        .addGroup(contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addGroup(contactUsLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(cBillStreet2)))
                        .addGap(167, 167, 167))))
        );
        contactUsLayout.setVerticalGroup(
            contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contactUsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cBillingTitle2)
                .addGap(13, 13, 13)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contactUsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardTitle2)
                        .addComponent(cRegion))
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cBillStreet2)
                .addContainerGap(268, Short.MAX_VALUE))
        );

        cMenu.addTab("Contact Us", contactUs);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cccc/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(234, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(119, 119, 119)
                .addComponent(Logout)
                .addGap(32, 32, 32))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(Logout)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        cMenu.getAccessibleContext().setAccessibleName("Home");
    }// </editor-fold>//GEN-END:initComponents

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

    private void cChangePwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cChangePwdActionPerformed
        // open a new instance of ChangePassword
        ChangePassword newPwd = new ChangePassword(parentFrame, true, openConnection, 'c', actNum);
        newPwd.setLocationRelativeTo(parentFrame);
        newPwd.setVisible(true);
        getCustData(actNum);
    }//GEN-LAST:event_cChangePwdActionPerformed

    private void cEditInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cEditInfoActionPerformed
        // open a new instance of EditCust
        EditCust editInfo = new EditCust(parentFrame, true, openConnection, actNum);
        editInfo.setLocationRelativeTo(parentFrame);
        editInfo.setVisible(true);
        getCustData(actNum);
    }//GEN-LAST:event_cEditInfoActionPerformed

    private void cEditBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cEditBillingActionPerformed
        // open a new instance of edit billing
        EditBilling editBill = new EditBilling(parentFrame, true, openConnection, actNum);
        editBill.setLocationRelativeTo(null);
        editBill.setVisible(true);
        getCustData(actNum);
    }//GEN-LAST:event_cEditBillingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CardName5;
    private javax.swing.JButton Logout;
    private javax.swing.JLabel cADate;
    private javax.swing.JLabel cANotes;
    private javax.swing.JLabel cAPrice;
    private javax.swing.JLabel cATech;
    private javax.swing.JLabel cActNum;
    private javax.swing.JLabel cBillCity;
    private javax.swing.JLabel cBillState;
    private javax.swing.JLabel cBillStreet;
    private javax.swing.JLabel cBillStreet2;
    private javax.swing.JLabel cBillZip;
    private javax.swing.JPanel cBilling;
    private javax.swing.JLabel cBillingTitle;
    private javax.swing.JLabel cBillingTitle1;
    private javax.swing.JLabel cBillingTitle2;
    private javax.swing.JLabel cCCNum;
    private javax.swing.JButton cChangePwd;
    private javax.swing.JLabel cDate1;
    private javax.swing.JButton cEditBilling;
    private javax.swing.JButton cEditInfo;
    private javax.swing.JLabel cEmail;
    private javax.swing.JLabel cEmail1;
    private javax.swing.JLabel cEmailField;
    private javax.swing.JLabel cEmailField1;
    private javax.swing.JLabel cEmailField2;
    private javax.swing.JLabel cEmailField3;
    private javax.swing.JLabel cEmailSub;
    private javax.swing.JPanel cHome;
    private javax.swing.JTabbedPane cMenu;
    private javax.swing.JLabel cName;
    private javax.swing.JLabel cName2;
    private javax.swing.JLabel cPhone;
    private javax.swing.JLabel cPkgExpDate;
    private javax.swing.JLabel cPkgName;
    private javax.swing.JLabel cPkgName1;
    private javax.swing.JLabel cPkgPrice1;
    private javax.swing.JLabel cProfile;
    private javax.swing.JLabel cRegion;
    private javax.swing.JLabel cSinceDate;
    private javax.swing.JLabel cSvc1;
    private javax.swing.JLabel cSvc2;
    private javax.swing.JLabel cSvc3;
    private javax.swing.JLabel cSvcCity;
    private javax.swing.JLabel cSvcCity2;
    private javax.swing.JLabel cSvcState;
    private javax.swing.JLabel cSvcState2;
    private javax.swing.JLabel cSvcStreet;
    private javax.swing.JLabel cSvcStreet2;
    private javax.swing.JLabel cSvcZip;
    private javax.swing.JLabel cSvcZip2;
    private javax.swing.JLabel cUsername;
    private javax.swing.JLabel cardName;
    private javax.swing.JLabel cardTitle;
    private javax.swing.JLabel cardTitle1;
    private javax.swing.JLabel cardTitle2;
    private javax.swing.JPanel contactUs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel label;
    private javax.swing.JPanel myServices;
    // End of variables declaration//GEN-END:variables

}
