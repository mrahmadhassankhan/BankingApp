package com.example.ahmad_banking.Database;

public class BankTransactionHelperClass {
    public String getBankAccNo() {
        return BankAccNo;
    }

    public void setBankAccNo(String BankAccNo) {
        this.BankAccNo = BankAccNo;
    }

    String BankAccNo;
    String message;
    int amount;
    String userID;






    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }





    public BankTransactionHelperClass(String userID,String BankAccNo, int amount, String Message){
        this.userID = userID;
        this.BankAccNo =BankAccNo;
        this.amount =amount;
        this.message = Message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
