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
public class EditPackage extends javax.swing.JPanel {

    /**
     * Creates new form EditPackage
     */
    public EditPackage() {
        initComponents();
    }
    Connection openConnection;
    EmployeeHome home;
    ResultSet employeeResultSet;
    Integer packageId;
    Frame parentFrame;
    Integer userRId;
    Integer userId;
    public EditPackage(Connection passedConnection, EmployeeHome managerHome, ResultSet passedResultSet, int passedPackageId, Frame passedFrame, Integer passedRId, Integer passedId) 
    {
        parentFrame = passedFrame;
        initComponents();
        openConnection = passedConnection;
        home = managerHome;
        employeeResultSet = passedResultSet;
        packageId = passedPackageId;
        String des;
        Integer mid;
        userRId = passedRId;
        userId = passedId;
        try{       
            String packageQuery = "Select p_name, p_price from package where p_id = ?;";
            CallableStatement packageStmt = openConnection.prepareCall(packageQuery);
            packageStmt.setInt(1,packageId);
            ResultSet packageRS = packageStmt.executeQuery();
            packageRS.first();
            packageName.setText(packageRS.getString("p_name"));
            packagePrice.setText(String.valueOf(packageRS.getInt("p_price")));
            
            
            //get the phone
            String phoneQuery = "Call get_package_phone(?)";
            CallableStatement phoneStmt = openConnection.prepareCall(phoneQuery);
            phoneStmt.setInt(1,packageId);
            ResultSet phoneRS = phoneStmt.executeQuery();
            if (phoneRS.next() && phoneRS.getInt("s_price") != 0)
            {
                des = phoneRS.getString("s_description");
                mid = des.length()/2;
                String des1 = des.substring(0, mid);
                String middle = des.substring(mid,des.length()-1).split(" ")[0];
                des1 = des1 + middle;
                String des2 = des.substring(des1.length()+1);
                String[] description = {des1,des2};
                phoneCost.setText("Base Cost: " + String.valueOf(phoneRS.getInt("s_price")));
                phoneName.setText(phoneRS.getString("s_name"));
                phoneDescription.setText(description[0]);
                phoneDescription1.setText(description[1]);
            }
            else
            {
                phoneCost.setText("");
                phoneName.setText("None");
                phoneDescription.setText("");
                phoneDescription1.setText("");
            }
            
            //get the cable
            String cableQuery = "Call get_package_cable(?)";
            CallableStatement cableStmt = openConnection.prepareCall(cableQuery);
            cableStmt.setInt(1,packageId);
            ResultSet cableRS = cableStmt.executeQuery();
            if (cableRS.next() && cableRS.getInt("s_price") != 0)
            {
                des = cableRS.getString("s_description");
                mid = des.length()/2;
                String des1 = des.substring(0, mid);
                String middle = des.substring(mid,des.length()-1).split(" ")[0];
                des1 = des1 + middle;
                String des2 = des.substring(des1.length()+1);
                String[] description = {des1,des2};
                cableCost.setText("Base Cost: " + String.valueOf(cableRS.getInt("s_price")));
                cableName.setText(cableRS.getString("s_name"));
                cableDescription.setText(description[0]);
                cableDescription1.setText(description[1]);
            }
            else
            {
                cableCost.setText("");
                cableName.setText("None");
                cableDescription.setText("");
                cableDescription1.setText("");
            }
            
            //get the internet
            String internetQuery = "Call get_package_internet(?)";
            CallableStatement internetStmt = openConnection.prepareCall(internetQuery);
            internetStmt.setInt(1,packageId);
            ResultSet internetRS = internetStmt.executeQuery();
            if (internetRS.next() && internetRS.getInt("s_price") != 0)
            {
                des = internetRS.getString("s_description");
                mid = des.length()/2;
                String des1 = des.substring(0, mid);
                String middle = des.substring(mid,des.length()-1).split(" ")[0];
                des1 = des1 + middle;
                String des2 = des.substring(des1.length()+1);
                String[] description = {des1,des2};
                internetCost.setText("Base Cost: " + String.valueOf(internetRS.getInt("s_price")));
                internetName.setText(internetRS.getString("s_name"));
                internetDescription.setText(description[0]);
                internetDescription1.setText(description[1]);
            }
            else
            {
                internetCost.setText("");
                internetName.setText("None");
                internetDescription.setText("");
                internetDescription1.setText("");
            }
                                
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

        updateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        packageName = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        packagePrice = new javax.swing.JTextField();
        internetName = new javax.swing.JLabel();
        cableName = new javax.swing.JLabel();
        phoneName = new javax.swing.JLabel();
        internetDescription = new javax.swing.JLabel();
        cableDescription = new javax.swing.JLabel();
        phoneDescription = new javax.swing.JLabel();
        internetCost = new javax.swing.JLabel();
        cableCost = new javax.swing.JLabel();
        phoneCost = new javax.swing.JLabel();
        internetDescription1 = new javax.swing.JLabel();
        cableDescription1 = new javax.swing.JLabel();
        phoneDescription1 = new javax.swing.JLabel();

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Package Name:");

        jLabel2.setText("Internet Service:");

        jLabel3.setText("Cable Service:");

        jLabel4.setText("Phone Service");

        jLabel5.setText("Package Cost:");

        packageName.setText("jTextField1");
        packageName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packageNameActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        packagePrice.setText("jTextField1");
        packagePrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                packagePriceActionPerformed(evt);
            }
        });

        internetName.setText("jLabel6");

        cableName.setText("jLabel7");

        phoneName.setText("jLabel8");

        internetDescription.setText("jLabel9");

        cableDescription.setText("jLabel10");

        phoneDescription.setText("jLabel11");

        internetCost.setText("jLabel6");

        cableCost.setText("jLabel6");

        phoneCost.setText("jLabel6");

        internetDescription1.setText("jLabel9");

        cableDescription1.setText("jLabel10");

        phoneDescription1.setText("jLabel11");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(updateButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cancelButton)
                                        .addGap(120, 120, 120))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(packagePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(internetName)
                                                    .addComponent(internetCost)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel3))
                                                .addGap(24, 24, 24)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(packageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cableName)
                                                    .addComponent(phoneName)
                                                    .addComponent(cableCost)
                                                    .addComponent(phoneCost))))
                                        .addGap(56, 56, 56)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(phoneDescription1)
                                            .addComponent(internetDescription1)
                                            .addComponent(internetDescription)
                                            .addComponent(cableDescription)
                                            .addComponent(cableDescription1)
                                            .addComponent(phoneDescription))))
                                .addGap(36, 36, 36))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(deleteButton)))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(packageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(internetName)
                    .addComponent(jLabel2)
                    .addComponent(internetDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(internetCost)
                    .addComponent(internetDescription1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cableName)
                    .addComponent(jLabel3)
                    .addComponent(cableDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cableCost)
                    .addComponent(cableDescription1))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(phoneName)
                    .addComponent(phoneDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneCost)
                    .addComponent(phoneDescription1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(packagePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(cancelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addGap(80, 80, 80))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void packageNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packageNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_packageNameActionPerformed

    private void packagePriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_packagePriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_packagePriceActionPerformed

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
            String name = packageName.getText().toString();
            Integer cost = Integer.parseInt(packagePrice.getText().toString());
            String updatePackage = "Call update_package(?,?,?);";
            CallableStatement updatePackageStmt = openConnection.prepareCall(updatePackage);
            updatePackageStmt.setInt(1, packageId);
            updatePackageStmt.setString(2, name);
            updatePackageStmt.setInt(3, cost);
            updatePackageStmt.execute();
            


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
            String errorMessage = e.getMessage();
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        try{
            String deletePackageQuery  = "Delete FROM package_available WHERE p_id = ? AND r_id = ?";
            CallableStatement deletePackageStatement = openConnection.prepareCall(deletePackageQuery);
            deletePackageStatement.setInt(1,packageId);
            deletePackageStatement.setInt(2,userRId);
            deletePackageStatement.execute();
            
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
            String errorMessage = e.getMessage();
        }
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cableCost;
    private javax.swing.JLabel cableDescription;
    private javax.swing.JLabel cableDescription1;
    private javax.swing.JLabel cableName;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel internetCost;
    private javax.swing.JLabel internetDescription;
    private javax.swing.JLabel internetDescription1;
    private javax.swing.JLabel internetName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField packageName;
    private javax.swing.JTextField packagePrice;
    private javax.swing.JLabel phoneCost;
    private javax.swing.JLabel phoneDescription;
    private javax.swing.JLabel phoneDescription1;
    private javax.swing.JLabel phoneName;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
