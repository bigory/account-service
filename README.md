# Account-service. Сервис для перевода денег между пользователями

## Описание
Сервис предоставляет возможность обмена валюты 4 типов:  USD, BYN, RUB, EUR. 
Есть возможность получения всех транзакций, по определенному счету, по пользователю.
Сущности сервиса:
```
User:
        Long id;
        String username;
        String documentNumber;
        DocumentType documentType;
Account:
        Long id;
        BigDecimal balance;
        CurrencyType currencyType;
        User user;
Transaction:
        String id;
        BigDecimal amount;
        TransactionType transactionType;
        LocalDateTime transactionTime;
        Account account;
```

### REST API для обмена валюты

```http://localhost:8080```

```http transfer request
POST /api/v1/service/transfer
  {
    "accountIdFrom":"1",
    "accountIdTo":"5",
    "currencyType":"USD",
    "amount":"500"
}
``` 

```http transaction request
GET /api/v1/service/transactions
GET /api/v1/service/transactions/{id}
GET /api/v1/service/transactions/account/{id}
GET /api/v1/service/transactions/user/{id}
```

```
 {
        "id": "a9e786bb-7b7b-4632-90ff-9c378d09f146",
        "amount": 500.00,
        "transactionType": "REFILL",
        "transactionTime": "2024-01-24T21:27:40.299874",
        "account": {
            "id": 5,
            "balance": 1000.00,
            "currencyType": "USD",
            "user": {
                "id": 4,
                "username": "Ivan",
                "documentNumber": "DL000222",
                "documentType": "DRIVER_LICENCE"
            }
        }
    },
    {
        "id": "bce449f7-0584-4c79-a34f-60b21c9db8c3",
        "amount": 500.00,
        "transactionType": "REFILL",
        "transactionTime": "2024-01-25T09:40:10.447252",
        "account": {
            "id": 5,
            "balance": 1000.00,
            "currencyType": "USD",
            "user": {
                "id": 4,
                "username": "Ivan",
                "documentNumber": "DL000222",
                "documentType": "DRIVER_LICENCE"
            }
        }
    }
]
```

### Состояние
Для хранения данных о пользователях, счетах, используется хранилище PostgreSQL.

## Окружение разработки
Для сборки и запуска ./docker-compose up


