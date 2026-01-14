# Blackjack-WebFlux-API

Descripció


En aquest exercici pràctic, es crearà una API en Java amb Spring Boot per a un joc de Blackjack. L'API estarà dissenyada per connectar-se i gestionar informació en dues bases de dades diferents: MongoDB i MySQL. El joc de Blackjack s'implementarà amb totes les funcionalitats necessàries per jugar, com la gestió de jugadors, mans de cartes i regles del joc.

Aquesta aplicació haurà de ser provada i documentada adequadament; README.md, Swagger, etc.


Nivell 1


Implementació Bàsica:
Desenvolupament d'una aplicació reactiva amb Spring WebFlux

Una aplicació purament reactiva inclou l'elecció d'un enfocament reactiu, configuració de MongoDB reactiva, implementació de controladors i serveis reactius.


Gestió d'Excepcions Global

Implementa un GlobalExceptionHandler per gestionar les excepcions globalment a l'aplicació.


Configuració de Bases de Dades

Configura l'aplicació per utilitzar dos esquemes de bases de dades: MySQL i MongoDB.


Proves de Controlador i Servei

Implementa proves unitàries per almenys un controlador i un servei utilitzant JUnit i Mockito.


Documentació amb Swagger

Utilitza Swagger per generar documentació automàtica de l'API de l'aplicació


Passos a seguir:
Disseny de l'API: Defineix els diferents endpoints necessaris per gestionar un joc de Blackjack, incloent-hi la creació de partides, la realització de jugades, etc.
Connexió a les Bases de Dades: Configura la connexió a MongoDB i MySQL. Crea les entitats necessàries en Java per representar les dades del joc.
Proves Unitàries: Escriu proves unitàries per a cadascun dels endpoints i funcions principals de l'API utilitzant JUnit i Mockito. Verifica que l'API funcioni correctament i que les operacions a les bases de dades es realitzin de manera esperada. Testa com a mínim un servei i un controlador.

Endpoints per al joc:
Crear partida:

Mètode: POST

Descripció: Crea una nova partida de Blackjack.

Endpoint: /game/new

Cos de la sol·licitud (body): Nou nom del jugador.

Paràmetres d'entrada: Cap

Resposta exitosa: Codi 201 Created amb informació sobre la partida creada.


Obtenir detalls de partida:

Mètode: GET

Descripció: Obté els detalls d'una partida específica de Blackjack.

Endpoint: /game/{id}

Paràmetres d'entrada: Identificador únic de la partida (id)

Resposta exitosa: Codi 200 OK amb informació detallada sobre la partida.


Realitzar jugada:

Mètode: POST

Descripció: Realitza una jugada en una partida de Blackjack existent.

Endpoint: /game/{id}/play

Paràmetres d'entrada: Identificador únic de la partida (id), dades de la jugada (per exemple, tipus de jugada i quantitat apostada).

Resposta exitosa: Codi 200 OK amb el resultat de la jugada i l'estat actual de la partida.


Eliminar partida:

Mètode: DELETE

Descripció: Elimina una partida de Blackjack existent.

Endpoint: /game/{id}/delete

Paràmetres d'entrada: Identificador únic de la partida (id).

Resposta exitosa: Codi 204 No Content si la partida s'elimina correctament.


Obtenir rànquing de jugadors:

Mètode: GET

Descripció: Obtén el rànquing dels jugadors basat en el seu rendiment a les partides de Blackjack.

Endpoint: /ranking

Paràmetres d'entrada: Cap

Resposta exitosa: Codi 200 OK amb la llista de jugadors ordenada per la seva posició en el rànquing i la seva puntuació.


Canviar nom del jugador:

Mètode: PUT

Descripció: Canvia el nom d'un jugador en una partida de Blackjack existent.

Endpoint: /player/{playerId}

Cos de la sol·licitud (body): Nou nom del jugador.

Paràmetres d'entrada: identificador únic del jugador (playerId).

Resposta exitosa: Codi 200 OK amb informació actualitzada del jugador.

Nivell 2


Dockerització de l'Aplicació
Pas 1: Crear el fitxer Dockerfile a l'arrel del projecte

Pas 2: Crear el fitxer .dockerignore a l'arrel del projecte

Pas 3: Instal·lar Docker i iniciar sessió

Pas 4: Construir la imatge Docker

Pas 5: Executar la imatge Docker

Pas 6: Etiquetar la imatge Docker

Pas 7: Iniciar sessió a Docker Hub

Pas 8: Pujar la imatge a Docker Hub

Pas 9: Provar que la imatge funcioni

Nivell 3


Desplegament de l'aplicació
Pas 1: Iniciar sessió a Render

Pas 2: Crear un nou servei web

Pas 3: Proporcionar l'URL de la imatge de Docker

Pas 4: Provar que l'aplicació funciona en el servei web: Un cop desplegada l'aplicació, obre un navegador web i navega a l'URL proporcionada per Render per verificar que l'aplicació s'executa correctament.


Desplegament de l'aplicació amb GitHub
Pas 1: Preparar el teu Repositori a GitHub

Si encara no tens un repositori a GitHub, crea'l. Pots iniciar un repositori buit o clonar-ne un d'existent.
Configura el teu Projecte: Assegura't que el teu projecte tingui un Dockerfile que descrigui com construir la imatge Docker per a la teva aplicació.
Prova Localment: Abans de pujar el teu projecte a GitHub, assegura't que la teva imatge Docker funcioni correctament localment.

Pas 2: Pujar la teva Imatge Docker a GitHub Packages

Etiqueta la teva Imatge Docker: Abans de pujar la teva imatge a GitHub Packages, etiqueta-la amb l'adreça correcta per a GitHub Packages.
Iniciar Sessió a GitHub Packages
Pujar la Imatge a GitHub Packages

Pas 3: Configurar GitHub Actions per a Desplegament Automàtic a Render

Crear un Fitxer d'Accions de GitHub: A la carpeta del teu repositori de GitHub, crea un directori .github/workflows si encara no existeix. Després, crea un fitxer YAML dins d'aquest directori per definir el teu flux de treball. Per exemple, crea un fitxer anomenat deploy-to-render.yml.
Configurar Secrets a GitHub: RENDER_EMAIL: La teva adreça de correu electrònic registrada a Render. RENDER_PASSWORD: La teva contrasenya de Render.
Provar i Desplegar: Fer un commit a la branca principal del teu repositori hauria d'activar automàticament el flux de treball a GitHub Actions.
Important

Presta principal atenció als fitxers de configuració i a la ubicació de cadascun d'aquests fitxers.

Recursos




Per realitzar els diferents nivells, recorda que dins l'apartat "Spring Framework Avançat" tens diversos recursos que et serviran de guia.

->Spring Framework Avançat