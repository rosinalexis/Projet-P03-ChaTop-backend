# Portail de Location Saisonnière

Ce projet est un portail en ligne permettant aux utilisateurs de rechercher des locations saisonnières et de contacter
les propriétaires pour louer des propriétés.

## Technologies Utilisées

- **Backend**: Java 17 avec Spring Boot 3.3.0
- **Base de Données**: MySQL
- **Documentation de l'API**: Swagger v3
- **Environnement de Développement Frontend**: Angular (mocké avec Mockoon)
- **Outils de Test**: Postman

## Fonctionnalités

- **Authentification**: Les utilisateurs peuvent s'inscrire, se connecter et se déconnecter.
- **Annonces de Locations**: Création, lecture, mise à jour et suppression d'annonces de locations.
- **Messagerie**: Envoi de messages entre locataires et propriétaires pour discuter des détails des locations.
- **Gestion des Utilisateurs**: Gestion des profils utilisateurs (nom, email, mot de passe).

## Prérequis

- JDK 17 ou supérieur
- Maven
- MySQL Server
- Postman (pour tester l'API)

## Installation et Configuration

1. **Clonage du Repository:**

   ```bash
   git clone <https://github.com/rosinalexis/Projet-P03-ChaTop-backend>
   cd Projet-P03-ChaTop-backend

2. **Configuration de la Base de Données:**

* Créez une base de données MySQL.
* Importez les informations du fichier `/db/chatop.sql`
* Configurez les informations de connexion dans src/main/resources/application.properties.

3. **Compilation et Exécution:**

   ```bash
   mvn clean package
   java -jar target/nom-du-jar.jar

4. **Accès à l'API:**

* L'API sera disponible à l'adresse : `http://localhost:3001/api`.

5. **Documentation de l'API:**

* Accédez à Swagger UI pour explorer et tester l'API: `http://localhost:3001/api/swagger-ui/`

## Usage

1. **Authentification:**

* Utilisez les endpoints pour vous inscrire, vous connecter et vous déconnecter.

2. **Gestion des Annonces:**

* Utilisez les endpoints pour créer, lire, mettre à jour et supprimer des annonces de locations.

3. **Messagerie:**

* Utilisez les endpoints pour envoyer et recevoir des messages entre locataires et propriétaires.

4. **Tests:**

* Utilisez Postman ou tout autre outil pour tester les endpoints de l'API selon les collections Postman fournies.

## Contribution

Les contributions sont les bienvenues ! Pour des suggestions d'améliorations, veuillez ouvrir une issue pour discuter
des changements proposés.

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
