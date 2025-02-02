## Table of Contents

- [List Accounts (No signin required)](#get-accounts)
- [Transfer from external to this bank (#No signin required)](#transfer-external)
- [Sign Up (No signin required)](#sign-up)
- [Sign In](#sign-in)
- [Create a bank account](#create-bank-account)
- [Deposit money to bank account](#deposit)
- [Withdraw money to from account](#withdraw)
- [Transfer money between bank accounts](#transfer)
- [Transfer money to an external api](#transfer-to-an-external-api)
- [Delete account](#delete)
- [Sign Out](#sign-out)

## Get Accounts

GET request to https://bank-3uzd.onrender.com/api/v1/accounts -
Get an array of users.

Example Response:

```json
 [
  {
    "id": 2,
    "username": "andrejs",
    "name": "Andrejs",
    "surname": "Matvejevs",
    "bankAccounts": [
      {
        "id": "MATAND_002822152a35",
        "transactions": [
          {
            "id": 13,
            "amount": 1000.00,
            "fromId": "",
            "toId": "MATAND_002822152a35"
          }
        ],
        "balance": 1000.00
      }
    ]
  }
]
```

## Transfer external

To transfer money from external source to a bank account from the bank make a
POST request to https://bank-3uzd.onrender.com/api/v1/transfer/external

Example REQUEST:

```json
{
  "fromAccountNumber": "External account number",
  "toAccountNumber": "MATAND_002822152a35",
  "amount": 100
}
```

Example RESPONSE:

```json
{
  "message": "Success"
}
```

## Sign Up

To sign up (create a user account) make a POST
request to https://bank-3uzd.onrender.com/signup

USER CONSTRAINTS:

```java

@NotBlank
@Size(max = 20)
@Column
private String username;

@NotBlank
@Size(max = 20)
@Column
private String name;

@NotBlank
@Size(max = 20)
@Column
private String surname;

@NotBlank
@Size(max = 120)
@Column
private String password;
```

Example REQUEST:

```json
{
  "username": "andrejs",
  "name": "Andrejs",
  "surname": "Matvejevs",
  "password": "andrejs"
}
```

Example RESPONSE:

```json
{
  "message": "User registered successfully!"
}
```

## Sign In

To sign in make a POST
request to https://bank-3uzd.onrender.com/signin - if successful, you will get a httponly jwt cookie **andrejs**, future
requests should automatically have this cookie attached

Example REQUEST:

```json
{
  "username": "andrejs",
  "password": "andrejs"
}
```

Example RESPONSE:

```json
{
  "id": 2,
  "username": "andrejs"
}
```

## Create Bank Account

To create a new bank account, make an empty POST
request to https://bank-3uzd.onrender.com/api/v1/account 

Example RESPONSE:
```json
{
  "id": "MATAND_cd5c23a38016",
  "transactions": null,
  "balance": 0.00
}
```

## Deposit

To deposit to bank account, make a PUT
request to https://bank-3uzd.onrender.com/api/v1/account/actions/deposit

Example REQUEST:
```json
{
    "toAccountNumber":"MATAND_cd5c23a38016",
    "amount":1000
}
```

Example RESPONSE:
```json
{
    "id": "MATAND_cd5c23a38016",
    "transactions": [
        {
            "id": 15,
            "amount": 1000,
            "fromId": "",
            "toId": "MATAND_cd5c23a38016"
        }
    ],
    "balance": 1000.00
}
```

## Withdraw

To deposit to a new bank account, make a PUT
request to https://bank-3uzd.onrender.com/api/v1/account/actions/withdraw

Example REQUEST:
```json
{
    "toAccountNumber":"MATAND_cd5c23a38016",
    "amount":100
}
```

Example RESPONSE:
```json
{
  "id": "MATAND_cd5c23a38016",
  "transactions": [
    {
      "id": 15,
      "amount": 1000.00,
      "fromId": "",
      "toId": "MATAND_cd5c23a38016"
    },
    {
      "id": 16,
      "amount": -100,
      "fromId": "MATAND_cd5c23a38016",
      "toId": ""
    }
  ],
  "balance": 900.00
}
```

## Transfer

To transfer money between bank accounts, make a POST
request to https://bank-3uzd.onrender.com/api/v1/account/transfer

Example REQUEST:
```json
{
  "fromAccountNumber":"MATAND_cd5c23a38016",
  "toAccountNumber": "MATAND_002822152a35",
  "amount":100
}
```

Example RESPONSE:
```json
{
  "headers": {},
  "body": {
    "message": "Success"
  },
  "statusCode": "OK",
  "statusCodeValue": 200
}
```

## Transfer To An External API

To transfer money between bank accounts, make a POST
request to https://bank-3uzd.onrender.com/api/v1/account/transfer

Example REQUEST:
```json
{
  "fromAccountNumber":"MATAND_cd5c23a38016",
  "toAccountNumber": "MATAND_002822152a35",
  "amount":100,
  "apiUrl": "https://bank-3uzd.onrender.com/api/v1/transfer/external"
}
```

Example RESPONSE:
```json
{
  "headers": {},
  "body": {
    "message": "Success"
  },
  "statusCode": "OK",
  "statusCodeValue": 200
}
```

## Delete Account

To delete your account, make an empty DELETE
request to https://bank-3uzd.onrender.com/api/v1/account

Example RESPONSE:
```json
{
  "message": "Success"
}
```

## Sign Out

To sign out from your account, make an empty POST
request to https://bank-3uzd.onrender.com/signout

Example RESPONSE:
```json
{
  "message": "You've been signed out!"
}
```