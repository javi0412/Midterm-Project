# Midterm-Project

Welcome to BankAPP, the banking application that has come to transform digital banking.

There are 4 types of accounts:

* **Checking**: regular account with a default minimum balance of 250 € and a monthy maintanenance fee of 12€.
* **Student checking:** account for people under 24 years old, no minimum balance neither monthly maintenance fee.
* **Savings**: account without monthly maintenance fee but with interest rate. The balance increases annually depending on the interest rate.
* **Credit Card**: account with credit limit and without maintenance fee and minimum balance. The balance increases monthly due to the interest rate. 

There are 3 types of users:
* **Account Holder**: AccountHolders have a username, password, name, date of birth and a primary address. They are able to access their own accounts and only
their accounts when passing the correct credentials using Basic Auth.
* **Admin**: admins only have username, password and name. Admins are able to access, create and modify all type of accounts (and its attributes) and users.
* **Third Parties**: third parties have a hashed key and name. They are added to the database by an admin and they can receive and send money to other accounts.

The operation of the program is detailed below in a logical order. There is a bankapp.sql file in resources folder, which contains all the necessary data to create
the schema and tables and to follow this guide (or can also be created with create command in application.properties file).

Firstly, the project shall be assembled and admins must be added to the database manually (password encrpyted using BCrypt).
```
INSERT INTO user(id, name, password, username) VALUES
('1','Luis','$2a$10$FKWDfJipctc6nQ2GUsejRe9K9W0.DpRG6nsMHIBdxDmQqhGH29ciW', 'username1'); -- passowrd:1234
```
## ADMIN ROUTES

### ACCOUNT HOLDERS ROUTES
Once an admin is created, we can proceed to create an AccountHOlder and use all related routes.

**CREATE ACCOUNT HOLDER** 
``` 
POST ROUTE: http://localhost:8080/admin/account-holder
Basic Auth: Username: 'username1' ; password: '1234'

Body:
 {
        "name": "Javier Garcia",
        "dateOfBirth": "1994-11-17",
        "primaryAddress": {
            "street": "Calle General",
            "city": "Madrid",
            "country": "Spain",
            "zipCode": "28025"
        },
        "username": "javi94", 
        "password": "$2a$10$WAtEJMNnpMR3F8rf2.wxsO2P9rVxShyZOLvkgm1whZLzc4YMrfHWy"
    } 
    Using the password util to introduced the password encrpyted ('123456' in this case)
```
We should create another account holder to have a few accounts to perform transactions
``` 
POST ROUTE: http://localhost:8080/admin/account-holder
Basic Auth: Username: 'username1' ; password: '1234'

Body:
 {
        "name": "Marina Garcia",
        "dateOfBirth": "1991-11-17",
        "primaryAddress": {
            "street": "Calle Ordoñez",
            "city": "Sevilla",
            "country": "Spain",
            "zipCode": "46658"
        },
        "username": "marina123", 
        "password": "$2a$10$WAtEJMNnpMR3F8rf2.wxsO2P9rVxShyZOLvkgm1whZLzc4YMrfHWy"
    } 
    Using the password util to introduced the password encrpyted ('123456' in this case)
```

**GET ALL ACCOUNT HOLDERS**

```
GET ROUTE: http://localhost:8080/admin/account-holder
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET ACCOUNT HOLDER BY ID**
```
GET ROUTE: http://localhost:8080/admin/account-holder/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

### CHECKING ROUTES

**CREATE CHECKING ACCOUNT** 

If this route is used to create a checking account for an account holer under 24 yo, it will automatically create an student account. 
``` 
POST ROUTE: http://localhost:8080/admin/checking
Basic Auth: Username: 'username1' ; password: '1234'
Body:
    {
        "balance": {
            "currency": "EUR",
            "amount": 6958.00
        },
        "primaryOwner": 2,
        "secretKey": "1200sd"
    }
    
There is no need to set up minimum balance, monthly maintenance fee.
Secondary owner is optional.
```
We create another checking account to other account holder
``` 
POST ROUTE: http://localhost:8080/admin/checking
Basic Auth: Username: 'username1' ; password: '1234'
Body:
    {
        "balance": {
            "currency": "EUR",
            "amount": 8000.00
        },
        "primaryOwner": 3,
        "secretKey": "1300r"
    }
    
```

**GET ALL CHECKINGS**
```
GET ROUTE: http://localhost:8080/admin/checking
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET CHECKING BY ID**
```
GET ROUTE: http://localhost:8080/admin/checking/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```
### SAVINGS ROUTES

**CREATE SAVINGS ACCOUNT** 

``` 
POST ROUTE: http://localhost:8080/admin/savings
Basic Auth: Username: 'username1' ; password: '1234'
Body: {
    "balance": {
        "currency": "EUR",
        "amount": 1500.00
    },
    "interestRate": 0.01,
    "primaryOwner":2,
    "secondaryOwner": null,
    "secretKey": "sdfse"
}

Secondary owner is optional.
Interest rate and minimum balance taken as default, but can be setted between the limits.
```

**GET ALL SAVINGS**
```
GET ROUTE: http://localhost:8080/admin/savings
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET CHECKING BY ID**
```
GET ROUTE: http://localhost:8080/admin/savings/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```
### CREDIT CARD ROUTES

**CREATE CREDIT CARD ACCOUNT** 

``` 
POST ROUTE: http://localhost:8080/admin/credit-card
Basic Auth: Username: 'username1' ; password: '1234'
Body: {
    "balance": {
        "currency": "EUR",
        "amount": 5000.00
    },
    "primaryOwner": 2
}

Secondary owner is optional.
Interest rate and credit limit taken as default, but can be setted between the limits.
```

**GET ALL CREDIT CARD**
```
GET ROUTE: http://localhost:8080/admin/credit-card
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET CHECKING BY ID**
```
GET ROUTE: http://localhost:8080/admin/credit-card/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```
### GENERAL ACCOUNT ROUTES

**GET ALL ACCOUNTS**
```
GET ROUTE: http://localhost:8080/admin/account
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET ACCOUNT BY ID**
```
GET ROUTE: http://localhost:8080/admin/account/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**CHANGE STATUS**
```
PATCH ROUTE: http://localhost:8080/admin/account/change-status/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:
{
    "status": "FROZEN"
}
```

*CHANGE BALANCE**
```
PATCH ROUTE: http://localhost:8080/admin/account/balance/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body: 
        {
        "balance": {
            "currency": "EUR",
            "amount": 5000.00
            }
        }
```
### THIRD PARTY ROUTES

**CREATE THIRD PARTY**
``` 
POST ROUTE: http://localhost:8080/admin/third-party
Basic Auth: Username: 'username1' ; password: '1234'
Body:
{
    "name": "Alimentacion Pepe"
    
}
Hashed key is generated automatically and shown in response. It will be used to make transactions.
```

**GET ALL THIRD PARTIES**
```
GET ROUTE: http://localhost:8080/admin/third-party
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

**GET THIRD PARTY BY ID**
```
GET ROUTE: http://localhost:8080/admin/third-party/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```
### TRANSACTION ADMIN ROUTES

**GET ALL TRANSACTIONS**
```
GET ROUTE: http://localhost:8080/admin/transaction
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```
**GET TRANSACTIONS BY ID**
```
GET ROUTE: http://localhost:8080/admin/transaction/{id}
Basic Auth: Username: 'username1' ; password: '1234'
Body:none
```

## ACCOUNT HOLDER ROUTES

Account holders can only access their accounts and make transactions.

**ACCESS ACCOUNTS**
To access their accounts, account holders must use Basic Authorization with their credentials.
Also the fees and interests are checked when accessed.
```
GET ROUTE: http://localhost:8080/account
Basic Auth: Username: 'javi94' ; password: '123456'
Body:none
```

**CREATE TRANSACTION**

Account holders must use Basic Authorization with their credentials to send money from their accounts.
```
POST ROUTE: http://localhost:8080/transaction
Basic Auth: Username: 'javi94' ; password: '123456'
Body:
{
        "origenAccountId": 1,
        "destinationAccountId": 2,
        "description": "Reembolso",
        "amount": {
            "currency": "EUR",
            "amount": 50.00
        }, 
        "nameOwnerDestinationAccount":"Marina Garcia" 
}
```

## THIRD PARTY ROUTES
```
POST ROUTE: http://localhost:8080/third-party-transaction?hashedKey={hashedKey}  (HashedKey is provided in the creation of the third party)
No Auth needed
Body:
{
    "amount": 50.0, 
    "accountId": 2,
    "secretKey": "sdfse", 
    "transactionType": "SEND"
}
```
