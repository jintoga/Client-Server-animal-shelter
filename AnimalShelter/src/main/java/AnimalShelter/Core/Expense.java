/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author DAT
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_expense;

    @Column(nullable = false, unique = false)
    private String product;
    private int price;
    private String organization;
    private String date_use;
    private String description;

    public Expense() {
    }

    public Expense(String product, int price, String organization, String date_use, String description) {
        this.product = product;
        this.price = price;
        this.organization = organization;
        this.date_use = date_use;
        this.description = description;
    }

    public void updateExpense(String product, int price, String organization, String date_use, String description) {
        this.product = product;
        this.price = price;
        this.organization = organization;
        this.date_use = date_use;
        this.description = description;
    }

    public Long getPk_expense() {
        return pk_expense;
    }

    public void setPk_expense(Long pk_expense) {
        this.pk_expense = pk_expense;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDate_use() {
        return date_use;
    }

    public void setDate_use(String date_use) {
        this.date_use = date_use;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
