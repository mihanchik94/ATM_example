## О проекте:
Тестовое задание по демонстрации возможности занесения или списания денежных средств со счета.

Приложение принимает по REST запрос вида:
```
 POST api/v1/wallet
{
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}
```
И после этого выполняет логику по изменению счета в базе данных.

Также приложением предусмотрена операция по получению баланса кошелька:
```
GET api/v1/wallets/{WALLET_UUID}
```
Неверные запросы возвращают пользователю сообщения об ошибке.

Приложение предполагает работу в конкурентной среде (до 1000 RPS на 1 кошелек).

Сборка приложения осуществляется через Docker-compose


### Технологии

__Java 17,__
__Maven,__
__Hibernate,__
__PostgreSQL,__
__Spring boot,__
__Liquibase,__
__Junit,__
__Docker__

![](https://img.shields.io/badge/Spring%20Boot-g)
![](https://img.shields.io/badge/Liquibase-red)
![](https://img.shields.io/badge/PostgreSQL-blue)
![](https://img.shields.io/badge/Docker-g)
![](https://img.shields.io/badge/Maven-red)
![](https://img.shields.io/badge/Hibernate-blue)

### Реализация и взаимодействие с приложением
