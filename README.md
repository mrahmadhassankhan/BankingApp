# Banking App

Welcome to the Banking App repository! This application is designed to facilitate various banking operations, including sending money between users, updating balances, sending money to a bank account, and currency conversion. The app is integrated with Google Firebase, and it utilizes three collections to store relevant data.

## Firebase Collections

1. **Bank Transactions:**
   - Fields:
     - `amount`: Amount of the transaction.
     - `bankAccNO`: Bank account number.
     - `message`: Transaction message.
     - `userID`: User ID associated with the transaction.

2. **Transactions:**
   - Fields:
     - `amount`: Amount of the transaction.
     - `message`: Transaction message.
     - `recipientID`: User ID of the recipient.
     - `userID`: User ID initiating the transaction.

3. **Users:**
   - Fields:
     - `balance`: User's current balance.
     - `email`: User's email address.
     - `firstname`: User's first name.
     - `lastname`: User's last name.
     - `password`: User's password (hashed for security).
     - `phoneNo`: User's phone number.
     - `userID`: Unique identifier for the user.

## App Features

The Banking App provides the following features:

- **Send Money:**
  - Users can send money to other users, updating balances accordingly.
  
- **Update Balance:**
  - The application automatically updates user balances after each transaction.

- **Send Money to Bank Account:**
  - Users can perform transactions involving bank accounts.

- **Currency Conversion:**
  - The app supports currency conversion functionality.

## Getting Started

To run the Banking App locally or integrate it into your own project, follow these steps:

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/mrahmadhassankhan/BankingApp.git
