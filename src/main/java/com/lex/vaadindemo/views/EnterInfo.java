/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lex.vaadindemo.views;

import com.lex.vaadindemo.data.Department;
import com.lex.vaadindemo.data.NewSessionBean;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;

/**
 *
 * @author valik
 */
@Named("enterinfo")
public class EnterInfo extends VerticalLayout implements Button.ClickListener {
    @EJB
    NewSessionBean bean;
    
    
    
    private Button saveBtn;
    private TextField deptName;
    private TextArea deptDesc;
    private TextField deptLocation;
    private BeanFieldGroup<Department> departmentGroup = new BeanFieldGroup<Department>(Department.class);
    
    
    public void init() {
        bean = new NewSessionBean();
        
        prepareFields();
        prepareButtons();
        departmentGroup.setItemDataSource(new BeanItem<Department>(new Department()));
    }
    
    
    
    private void prepareFields() {
        deptName = new TextField("Department Name:");
        deptDesc = new TextArea("Department Description:");
        deptLocation = new TextField("Department Location:");
        addComponent(deptName);
        addComponent(deptDesc);
        addComponent(deptLocation);
        
        departmentGroup.bind(deptName, "deptName");
        departmentGroup.bind(deptDesc, "deptDesc");
        departmentGroup.bind(deptLocation, "deptLocation");
        
    }
    
    private void prepareButtons() {
        saveBtn = new Button("Save");
        saveBtn.addClickListener(this);
        addComponent(saveBtn);
        
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            departmentGroup.commit();
            Department department = departmentGroup.getItemDataSource().getBean();
            
            
            Notification.show("Button clicked" + department.getDeptDesc());
            System.out.println(department);
            bean.saveData(department);
            
        } catch (FieldGroup.CommitException ex) {
            Logger.getLogger(EnterInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
