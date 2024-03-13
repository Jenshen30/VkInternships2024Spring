# Прокси сервер на Spring
(author: Качмар Евгений Миронович)

В данном тестовом задании необходимо было написать прокси сервер, 
который транслирует запросы пришедшие к нему на https://jsonplaceholder.typicode.com/guide/.

При этом:
- запросы кэшируются при помощи LoadingCache из небезызвестной Guava.
- ведется логирование обращений к прокси серверу на h2 database.
- имеются необходимые роли (ROLE_ADMIN, ROLE_POST, ROLE_USER, ROLE_ALBUMS)

Написаны тесты проверяющие как
- работает авторизация пользователей с какой-то ролью.
- логируются действия пользователя.
- работают другие запросы, кроме get 

Аудиты записываются, только в том случае, если они идут в сеть и берут json.

Можно записывать их и тогда, когда они тыкаются в LoadingCache, но я решил,
что лучше трекать наличие редиректа на https://jsonplaceholder.typicode.com/guide/.
Чем просто смотреть сколько обращений было к нашему прокси серверу.


При этом с url'ов /audits/** не записываются в бд, это можно сделать,
добавив функцию RedirectionService#auditTracking в соответствующие обработчики.

## Про тесты.
Чтобы корректно работал DBTest нужно запустить CreateTable,
для инициализации иаблички audits в бд. 

Так же создан DestructTable, который удалит таблицу audits,
если это необходимо.