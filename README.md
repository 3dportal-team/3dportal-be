## Local deployment

If you want to deploy server part locally installation of JRE 11+, Maven 3.8.2 and Docker is needed. Also: 

1. ```LOCAL_IP``` must be set to your local ip. (If all components are at the same host you can set it to 'localhost')
2. ```YOOKASSA_SHOP_ID```, ```YOOKASSA_API_SECRET``` properties are required for yookassa service connection in .env file
3. 3dportal.p12 with generated key is required in yookassa service. You can generate it with ```keytool -genkeypair -alias 3dportal -keyalg RSA 
-keysize 2048 -storetype PKCS12 -keystore 3dportal.p12 -validity 3650``` in the keystore path (see server.ssl.key-store property. By default, it is /resources/keystore/3dportal.p12)
4. Run ```mvn clean install``` from root directory
5. Run ```docker-compose up --build ```

