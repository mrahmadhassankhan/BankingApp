package com.example.ahmad_banking.Database;

public class TransactionHelperClass {


    public String getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    String recipientID;
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





    public TransactionHelperClass(String userID,String recipientID, int amount, String Message){
        this.userID = userID;
        this.recipientID =recipientID;
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
