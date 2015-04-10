# MeetingOrganizerServer

### Serveri käivitamise juhised
Server on võimeline kasutama nii Postgres andmebaasi kui ka mälus olevat andmebaasi. 

#### Vajalikud asjad
* Maven. Hea seadistamise juhis siin http://www.mkyong.com/maven/how-to-install-maven-in-windows/. Linuxi peal järgida seda http://iambusychangingtheworld.blogspot.com/2014/04/install-and-configure-java-and-maven-in.html

#### Soovitatud asjad
* Eclipse (soovitavalt Spring Tool Suite)
* Eclipse m2e plugin (ilma selleta arvab Eclipse, et projekt on katki). Eclipse'ile lisamiseks "Install new software", lehekülg http://download.eclipse.org/technology/m2e/releases
* Postgres andmebaas

#### Projekti seadistamine
1. Tõmba alla giti repo
2.  Mälus oleva andmebaasi kasutamiseks peab minema faili src/main/resources/application.properties ja muutma rida `spring.profiles.active=default`. `default` asemele kirjuta `test`. Kohaliku andmebaasi kasutamiseks kirjuta `postgres`. 
3. webapp kaustas käsk `mvn spring-boot:run`
4. Nüüd peaks server olema ligipääsetav aadressil `localhost:8080`. Andmete nägemiseks võid proovida `localhost:8080`
5. Käsk `mvn eclipse:eclipse` genereerib Eclipse jaoks vajalikud failid
6. Nüüd saab teha `import existing projects`.

Serveri käivitamiseks on vaja ainult käsku `mvn spring-boot:run`


#### Andmebaasi setup
`test` ehk mälus olev andmebaas kasutab andmeid, mis on kirjas `import.sql` failis. Postgres pead ise tõmbama.  Nende paroolid ja muu info peab ühtima  `application-postgres.properties` kirjeldatuga. Andmebaasi tabelid tekitab server ise.  

Herokus töötab andmebaas `DATABASE_URL` kaudu, nii et soovi kõrval võib Heroku stiilis Postgres ühendamise stringi süsteemi keskonnamuutujatesse panna. Sellisel juhul ei pea `application.properties` failis midagi muutma. 

Selline string on näiteks `postgres://root:root@localhost:3306/test`.

#### Testide jooksutamine
Maveni abil testide jooksutamiseks on käsk `mvn test`.

Kui projekt on seadistatud ja andmebaas töötab, siis saab jooksutada teste, minnes eclips-is java/tests kausta peale vajutades paremat klõpsu ja valides rippmenüüst `Run As > JUnit test`. Pärast seda jooksutatakse kõik testid mis on olemas. 

## Heroku setup
Push: git push heroku master

Logide vaatamine: heroku logs
