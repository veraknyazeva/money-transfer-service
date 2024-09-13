# Курсовой проект "Сервис перевода денег"


## Описание проекта


REST-сервис.

Сервис предоставляет интерфейс для перевода денег с одной карты на другую по заранее описанной спецификации.

Работает с заранее подготовленным FRONT по адресу https://serp-ya.github.io/card-transfer/


## Запуск приложения



* Запуск производится с помощью Docker Compose
> docker-compose up -d


* Для совместимости с FRONT приложение работает на порту 5500

> http://localhost:5500/


## Пример запросов


* Пример POST запроса на инициализацию трансфера денежных средств


> Request URL: http://localhost:5500/transfer

> Accept: application/json, text/plain

> Content-Type: application/json;charset=UTF-8

> Request Payload :

> {

> &ensp; "cardFromNumber": "2222222222222222",

> &ensp; "cardToNumber": "2222222222222222",

> &ensp; "cardFromCVV": "232",

> &ensp; "cardFromValidTill": "12/23",

> &ensp; "amount": {

> &emsp; "currency": "RUR",

> &emsp; "value": 22200

> &ensp; }

> }

> Response:
> {
> &emsp; "operationId":"1"
> }

* Пример POST запроса на инициализацию трансфера денежных средств

> Request URL: http://localhost:5500/confirmOperation

> Accept: application/json, text/plain

> Content-Type: application/json;charset=UTF-8

> Request Payload :
> {
> &emsp; "code": "0000",
> &emsp; "operationId": "1"
> }
> Response:
> {
> &emsp; "operationId":"1"
> }