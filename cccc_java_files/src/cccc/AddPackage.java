/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cccc;
import java.sql.*;
import java.util.*;
import java.awt.Frame;
/**
 *
 * @author connorarnold
 */
public class AddPackage extends javax.swing.JPanel {

    /**
     * Creates new form AddPackage
     */
    public AddPackage() {
        initComponents();
    }

    Connection openConnection;
    EmployeeHome home;
    Frame parentFrame;
    Integer userRId;
    Integer userId;
    public AddPackage(Connection passedConnection, EmployeeHome managerHome, Frame passedFrame, Integer passedRId, Integer passedId) 
    {
        parentFrame = passedFrame;
        initComponents();
        openConnection = passedConnection;
        home = managerHome;
        userRId = passedRId;
        userId = passedId;
        try{    
            //set Phone list
            String phoneListQuery = "Select s_name from service where s_type = 'Phone'";
            CallableStatement phoneListStmt = openConnection.prepareCall(phoneListQuery);
            ResultSet phoneListRs = phoneListStmt.executeQuery();
            List<String> phoneList = new ArrayList<String>();
            String phoneName;
            phoneList.add("None");
            while (phoneListRs.next()){
                phoneName = phoneListRs.getString("s_name");
                phoneList.add(phoneName);
            }
            String[] phoneNames = phoneList.toArray(new String[phoneList.size()]);
            packagePhone.setModel(new javax.swing.DefaultComboBoxModel(phoneNames));
            
            //set Internet
            String internetListQuery = "Select s_name from service where s_type = 'Internet'";
            CallableStatement internetListStmt = openConnection.prepareCall(internetListQuery);
            ResultSet internetListRs = internetListStmt.executeQuery();
            List<String> internetList = new ArrayList<String>();
            String internetName;
            internetList.add("None");
            while (internetListRs.next()){
                internetName = internetListRs.getString("s_name");
                internetList.add(internetName);
            }
            String[] internetNames = internetList.toArray(new String[internetList.size()]);
            packageInternet.setModel(new javax.swing.DefaultComboBoxModel(internetNames));
            
             //set Cable
            String cableListQuery = "Select s_name from service where s_type = 'Cable'";
            CallableStatement cableListStmt = openConnection.prepareCall(cableListQuery);
            ResultSet cableListRs = cableListStmt.executeQuery();
            List<String> cableList = new ArrayList<String>();
            String cableName;
            cableList.add("None");
            while (cableListRs.next()){
                cableName = cableListRs.getString("s_name");
                cableList.add(cableName);
            }
            String[] cableNames = cableList.toArray(new String[cableList.size()]);
            packageCable.setModel(new javax.swing.DefaultComboBoxModel(cableNames));
            
            
        }
        catch(Exception e)
        {
           throw new IllegalStateException("error",e); 
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

        jLabel1 = new javax.swing.JLabel();
        packageName = new javax.swing.JTextField();
        packageInternet = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        packageCable = new javax.swing.JComboBox<>();
        packagePhone = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        packageCost = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();

        jLabel1.setText("Package Name:");

        packageName.setText("Name");
        packageName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packageNameActionPerformed(evt);
            }
        });

        packageInternet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Internet Service:");

        jLabel3.setText("Cable Service:");

        packageCable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        packagePhone.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Phone Service");

        jLabel5.setText("Package Cost:");

        packageCost.setText("0");
        packageCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packageCostActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Add");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateButton)
                    .addComponent(packageCable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packagePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packageInternet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packageCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(packageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(packageInternet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(packageCable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(packagePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(packageCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(cancelButton))
                .addGap(127, 127, 127))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void packageNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packageNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_packageNameActionPerformed

    private void packageCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packageCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_packageCostActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        f.setContentPane(home);
        home.setVisible(true);
        f.revalidate();
        f.repaint();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        try{
            //Add Validation
            String phone = packagePhone.getSelectedItem().toString();
            String cable = packageCable.getSelectedItem().toString();
            String internet = packageInternet.getSelectedItem().toString();
            String name = packageName.getText().toString();
            Integer cost = Integer.parseInt(packageCost.getText().toString());
            
            //Create the package
            String createPackage = "Insert Into package (p_name,p_price) Values (?,?)";
            CallableStatement createPackageStmt = openConnection.prepareCall(createPackage);
            createPackageStmt.setString(1,name);
            createPackageStmt.setInt(2,cost);
            createPackageStmt.execute();
                        
            //Get the packages id
            String findPackageQuery = "Select package_id_from_name(?) p_id;";
            CallableStatement findPackageStmt = openConnection.prepareCall(findPackageQuery);
            findPackageStmt.setString(1,name);
            ResultSet foundPackage = findPackageStmt.executeQuery();
            foundPackage.first();
            Integer packageId = Integer.parseInt(foundPackage.getString("p_id"));
            
            //Create package_available
            String createPackageAvailable = "Insert Into package_available (r_id,p_id) Values (?,?)";
            CallableStatement createPackageAvailableStmt = openConnection.prepareCall(createPackageAvailable);
            createPackageAvailableStmt.setInt(1,userRId);
            createPackageAvailableStmt.setInt(2,packageId);
            createPackageAvailableStmt.execute();
            
            //Add the services
            if (phone != "None")
            {
               
                String findPhoneQuery = "Select service_id_from_name(?) s_id;";
                CallableStatement findPhoneStmt = openConnection.prepareCall(findPhoneQuery);
                findPhoneStmt.setString(1,phone);
                ResultSet foundPhone = findPhoneStmt.executeQuery();
                foundPhone.first();
                Integer phoneId = Integer.parseInt(foundPhone.getString("s_id"));
                
                String addPhone = "Insert Into package_service (p_id,s_id) Values (?,?)";
                CallableStatement addPhoneStmt = openConnection.prepareCall(addPhone);
                addPhoneStmt.setInt(1,packageId);
                addPhoneStmt.setInt(2,phoneId);
                addPhoneStmt.execute();
            }
            
            if (cable != "None")
            {
               
                String findCableQuery = "Select service_id_from_name(?) s_id;";
                CallableStatement findCableStmt = openConnection.prepareCall(findCableQuery);
                findCableStmt.setString(1,cable);
                ResultSet foundCable = findCableStmt.executeQuery();
                foundCable.first();
                Integer cableId = Integer.parseInt(foundCable.getString("s_id"));
                
                String addCable = "Insert Into package_service (p_id,s_id) Values (?,?)";
                CallableStatement addCableStmt = openConnection.prepareCall(addCable);
                addCableStmt.setInt(1,packageId);
                addCableStmt.setInt(2,cableId);
                addCableStmt.execute();
            }
            
            if (internet != "None")
            {
               
                String findInternetQuery = "Select service_id_from_name(?) s_id;";
                CallableStatement findInternetStmt = openConnection.prepareCall(findInternetQuery);
                findInternetStmt.setString(1,internet);
                ResultSet foundInternet = findInternetStmt.executeQuery();
                foundInternet.first();
                Integer internetId = Integer.parseInt(foundInternet.getString("s_id"));
                
                String addInternet = "Insert Into package_service (p_id,s_id) Values (?,?)";
                CallableStatement addInternetStmt = openConnection.prepareCall(addInternet);
                addInternetStmt.setInt(1,packageId);
                addInternetStmt.setInt(2,internetId);
                addInternetStmt.execute();
            }
            
            ResultSet employeeInfo = getEmployeeInfo(userId);
            EmployeeHome userHome = new EmployeeHome(employeeInfo,openConnection, parentFrame);
            this.setVisible(false);
            javax.swing.JFrame f = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            f.setContentPane(userHome);
            userHome.setVisible(true);
            f.revalidate();
            f.repaint();
        }
        catch(Exception e)
        {
           throw new IllegalStateException("error",e); 
        }
    }//GEN-LAST:event_updateButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<String> packageCable;
    private javax.swing.JTextField packageCost;
    private javax.swing.JComboBox<String> packageInternet;
    private javax.swing.JTextField packageName;
    private javax.swing.JComboBox<String> packagePhone;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
