/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Core;

/**
 *
 * @author DAT
 */
public class ProfitHelper {

    private Long pk_profit;

    private String sponsor;

    private String supporttype;

    private int amount;
    private String description;
    private String date_receive;

    private String yearreceive;
    private String monthreceive;

    public String getYearreceive() {
        return yearreceive;
    }

    public void setYearreceive(String yearreceive) {
        this.yearreceive = yearreceive;
    }

    public String getMonthreceive() {
        return monthreceive;
    }

    public void setMonthreceive(String monthreceive) {
        this.monthreceive = monthreceive;
    }

    public ProfitHelper() {
    }

    public ProfitHelper(String sponsor, String supporttype, int amount, String description, String date_receive, String yearreceive, String monthreceive) {
        this.sponsor = sponsor;
        this.supporttype = supporttype;
        this.amount = amount;
        this.description = description;
        this.date_receive = date_receive;

        this.yearreceive = yearreceive;
        this.monthreceive = monthreceive;
    }

    public ProfitHelper(Profit profit) {
        this.pk_profit = profit.getPk_profit();
        this.sponsor = profit.getSponsor().getName();
        this.supporttype = profit.getSupporttype().getTitle();
        this.amount = profit.getAmount();
        this.description = profit.getDescription();
        this.date_receive = profit.getDate_receive();

        this.yearreceive = profit.getYearreceive();
        this.monthreceive = profit.getMonthreceive();
    }

    public Long getPk_profit() {
        return pk_profit;
    }

    public void setPk_profit(Long pk_profit) {
        this.pk_profit = pk_profit;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getSupporttype() {
        return supporttype;
    }

    public void setSupporttype(String supporttype) {
        this.supporttype = supporttype;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_receive() {
        return date_receive;
    }

    public void setDate_receive(String date_receive) {
        this.date_receive = date_receive;
    }
}
