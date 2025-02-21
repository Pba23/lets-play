# TODO List

## Étape 1 : Initialisation du projet
- [ ] Créer un projet Spring Boot avec les dépendances suivantes :
  - Spring Web
  - Spring Data MongoDB
  - Spring Security
  - Spring Boot DevTools
  - Spring Boot Starter Validation
  - JSON Web Token (JWT)
- [x] Configurer la base de données MongoDB dans `application.properties`.

---

## Étape 2 : Développement des modèles (Entities)
- [x] Créer les classes suivantes :
  - `User` avec les champs : id, name, email, password, role.
  - `Product` avec les champs : id, name, description, price, userId.

---

## Étape 3 : Développement des DTOs
- [x] Créer des classes DTO pour limiter les données exposées dans les réponses API :
  - `UserDTO` : id, name, email, role (pas de mot de passe).
  - `ProductDTO` : id, name, description, price, userId.

---

## Étape 4 : Création des repositories
- [x] Créer les interfaces suivantes :
  - `UserRepository` (héritant de `MongoRepository`).
  - `ProductRepository` (héritant de `MongoRepository`).

---

## Étape 5 : Services
- [ ] Implémenter les services :
  - `UserService` :
    - Gérer la logique métier pour les utilisateurs (CRUD).
    - Hasher et saler les mots de passe avec BCrypt.
    - Valider les entrées utilisateurs.
  - `ProductService` :
    - Gérer la logique métier pour les produits (CRUD).
    - Associer les produits à un utilisateur via `userId`.
  - `AuthService` :
    - Gérer l'authentification des utilisateurs avec JWT.
    - Générer des tokens JWT.
    - Vérifier les tokens JWT pour l'autorisation.

---

## Étape 6 : Configuration de la sécurité
- [ ] Configurer `Spring Security` dans `SecurityConfig.java` :
  - Permettre l'accès public à l'API "GET Products".
  - Exiger une authentification pour toutes les autres routes.
  - Ajouter des filtres pour valider les tokens JWT.
- [ ] Implémenter un système basé sur les rôles (`ROLE_USER`, `ROLE_ADMIN`).

---

## Étape 7 : Développement des contrôleurs
- [ ] Créer les endpoints RESTful pour les utilisateurs :
  - `POST /users` : Créer un utilisateur.
  - `GET /users/{id}` : Lire un utilisateur.
  - `PUT /users/{id}` : Mettre à jour un utilisateur.
  - `DELETE /users/{id}` : Supprimer un utilisateur.
- [ ] Créer les endpoints RESTful pour les produits :
  - `POST /products` : Créer un produit.
  - `GET /products` : Lire tous les produits (sans authentification).
  - `GET /products/{id}` : Lire un produit par son ID.
  - `PUT /products/{id}` : Mettre à jour un produit.
  - `DELETE /products/{id}` : Supprimer un produit.

---

## Étape 8 : Gestion des erreurs
- [ ] Implémenter un gestionnaire global d'exceptions pour renvoyer des réponses cohérentes en cas d'erreurs.
- [ ] Valider les entrées des utilisateurs avec les annotations `@Valid` et `@NotNull`.

---

## Étape 9 : Sécurité
- [ ] Hasher les mots de passe avec BCrypt.
- [ ] Protéger les API contre les injections MongoDB en validant les entrées.
- [ ] Masquer les informations sensibles (ex. : mots de passe) dans les réponses.
- [ ] Activer HTTPS.

---

## Étape 10 : Bonus
- [ ] Configurer des politiques CORS pour gérer les requêtes cross-domain.
- [ ] Implémenter un mécanisme de limitation de débit pour prévenir les attaques de force brute.

---

## Étape 11 : Tests
- [ ] Écrire des tests unitaires pour les services.
- [ ] Écrire des tests d'intégration pour les contrôleurs.
- [ ] S'assurer qu'aucun endpoint ne génère de réponse 5XX.

---

## Étape 12 : Documentation
- [ ] Ajouter des commentaires dans le code pour une meilleure lisibilité.
- [ ] Documenter les endpoints REST avec Swagger ou Postman.

---

## Étape 13 : Livraison
- [ ] Fournir un script ou des instructions claires pour exécuter le projet.
