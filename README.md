## Руководство для локального запуска

Для локального запуска серверной части приложения необходима установка и наличие JRE 11+, Maven 3.8.2 и Docker
Далее:

1. Для подключения сервиса ЮКасса необходимо прописать в .env ```YOOKASSA_SHOP_ID``` и ```YOOKASSA_API_SECRET``` (см. [https://yookassa.ru/developers/using-api/interaction-format](https://))
2. Из корневой директории проекта выполнить `mvn clean install`
3. `docker-compose up --build `

Для запуска вне docker необходимо сменить LOCAL_IP на нужный ip
