=== English ===

21. System Restaurant. The customer places an order from the Menu.
The administrator confirms the order and sends it to the kitchen for execution.
The administrator creates a bill. The customer pays for it.

You need to build a web application that supports the following
functionality:
1. Based on the entities of the domain, create classes
describing them.
2. Classes and methods must have functionality that reflects their
names and must be properly structured by packages.
3. Information on the subject area is stored in the database. For access
use the JDBC API via the default connection pool or developed by yourself. As a DBMS
MySQL is recommended.
4. The application must support working with Cyrillic (to be
multilingual), including storing information in the database.
5. The code must be documented.
6. The application must be covered by unit tests.
7. When developing business logic, use sessions and filters, and
events in the system to handle using Log4j.
8. In the application, you must implement Pagination, Transaction depending on your project.
9. Using servlets and JSPs, implement the functionality,
proposed in the formulation of a specific problem.
10. Use JSTL in JSP pages
11. The application must respond correctly to all sorts of errors and exceptions 
(User should never see stack trace on front-end side).
12. The application must have an authorization and authentication system.

Installation Instructions:
1. Download / clone this repository.
2. Database dump is located in the src / dumpDB folder.
3. Start Tomcat.

Startup Instructions:
1. Authentication
1.1. as administrator: email: leshchuk.tetiana@gmail.com password: leshchukt
1.2. with client rights: email: client@client.com password: client


=== Russian ===

21. Система Ресторан. Клиент осуществляет заказ из Меню. 
Администратор подтверждает Заказ и отправляет его на кухню для исполнения. 
Администратор выставляет Счет. Клиент производит его оплату.

Необходимо построить веб-приложение, поддерживающую следующую
функциональность:
1. На основе сущностей предметной области создать классы их
описывающие.
2. Классы и методы должны иметь отражающую их функциональность
названия и должны быть грамотно структурированы по пакетам
3. Информацию о предметной области хранить в БД, для доступа
использовать API JDBC с использованием пула соединений,
стандартного или разработанного самостоятельно. В качестве СУБД
рекомендуется MySQL.
4. Приложение должно поддерживать работу с кириллицей (быть
многоязычной), в том числе и при хранении информации в БД.
5. Код должен быть документирован.
6. Приложение должно быть покрыто Юнит-тестами
7. При разработке бизнес логики использовать сессии и фильтры, и
события в системе обрабатывать с помощью Log4j.
8. В приложении необходимо реализовать Pagination, Transaction в
зависимости от Вашего проекта
9. Используя сервлеты и JSP, реализовать функциональности,
предложенные в постановке конкретной задачи.
10 . В страницах JSP применять библиотеку JSTL
11 . Приложение должно корректно реагировать на ошибки и исключения
разного рода (Пользователь никогда не должен видеть stack-trace на
стороне front-end).
12 . В приложении должна быть реализована система Авторизации и
Аутентификации

Инструкция по установке:
1. Загрузить/клонировать этот репозиторий.
2. Дамп базы даных находится в папке src/dumpDB.
3. Запустить Tomcat.

Инструкция по запуску:
1. Аутентификация
1.1. с правами администратора: email: leshchuk.tetiana@gmail.com password: leshchukt
1.2. с правами клиента:        email: client@client.com          password: client
