---
title: "SYM-1-B - Labo 3"
author:
    - "Guillaume MILANI"
    - "Christopher MEIER"
    - "Daniel PALUMBO"
date:           "17 décembre 2017"
geometry:       "margin=1in"
papersize:      "a4"
monofont:       "Inconsolata"
documentclass:  "report"
lang:           "fr"
---

# Introduction

# NFC

## Mise en situation

> *Sachant que les collaborateurs de l'entreprise UBIQOMP SA se déplacent en véhiculant des informations précieuses dans leurs dispositifs informatiques mobiles (munis de dispositifs de lecture NFC), et qu'ils sont amenés à se rendre dans des zones à risque, un expert a fait les estimations suivantes :*

> - *La probabilité de vol d'un mobile par une personne malintentionnée et capable d'utiliser les données à des fins préjudiciables pour la société est de 1%.*
- *La probabilité que le mot de passe puisse être découvert, soit par analyse des traces de doigts sur l'écran, soit par observation en cours d'utilisation est de 4%.*
- *La probabilité de vol du porte-clés est de 0.1%.*
- *Environ 10% des criminels susceptibles d'accéder aux données du mobile sait que le porte- clés permet l’accès au mobile.*

## Questions

### Partie 1

> *Quelle est la probabilité moyenne globale que des données soient perdues, dans le cas où il faut la balise ET le mot de passe, ainsi que dans le cas où il faut la balise OU le mot de passe (on négligera dans le calcul la probabilité de l’intersection des deux ensembles), ou encore le cas où seule la balise est nécessaire ?*

Soient les événements suivants:

- A = le mobile est volé par une personne malintentionnée et capable d'utiliser les données à des fins préjudiciables pour la société
- B = le mot de passe est découvert
- C = la balise (sur le porte-clés) est volée
- D = le criminel sait que le porte-clés donne accès aux mobile

Nous avons donc les probabilité suivantes:

- $P(A) = 0.01$
- $P(B) = 0.04$
- $P(C) = 0.001$
- $P(D | A) = 0.1$

Nous supposons que toutes les probabilités sont indépendantes. 

**Balise ET mot de passe**

$P(D\cap B \cap C) = P(D) \cdot P(B) \cdot P(C) = P(D | A) \cdot P(A) \cdot P(B) \cdot P(C) = 0.1 \cdot 0.01 \cdot 0.04 \cdot 0.001 = 0.00000004$

Le résultat est négligeable en ce qui concerne la perte de données protégées par un mot de passe et un tag NFC.

**Balise OU mot de passe**

$P((D \cap C) \cup (A \cap B)) = P(S) \cdot P(C) + P(A) \cdot P(B) = P(D | A) \cdot P(A) \cdot P(C) + P(A) \cdot P(B) = 0.1 \cdot 0.01 \cdot 0.001 + 0.01 \cdot 0.04 = 0.0004 + 0.000001 = 0.000401$

**Seulement la balise**

$P(A) \cdot P(C) \cdot P(D | A) = 0.01 * 0.001 * 0.1 = 0.000001$

> *si l'on envoie cent collaborateurs en déplacement, quel est le risque encouru de vol de données sensibles ?*

**Balise ET mot de passe**

$1-(1-0.00000004) ^ 100 = 0.0000039$

**Balise OU mot de passe**

$1-(1-0.000401) ^ 100 = 0.03931$

**Seulement la balise**

$1-(1-0.000001) ^ 100 = 0.000099$

### Partie 2

> *Mettez vos conclusions en rapport avec l'inconfort subjectif de chaque solution.*

Chacune des solutions proposées possédent des avantages et des inconvénients dans l'utilisation et le traitement des données et de l'application. Dans le cas de l'utilisation d'un mot de passe, l'utilisateur se doit de l'entrer réguliérement avant de pouvoir accéder à une zone critique de celle-ci. En comparaison de la balise NFC, l'utilisateur se doit de sortir son porte-clés et "scanner" la balise au dos du téléphone portable ce qui implique qu'il doit l'avoir sur soi. Comme nous ne savons pas quel peut être l'impact d'un vol de données, les trois cas précédent doivent être pris en compte et la solution à privilégier dépendra de l'utilisation et de la nature des données.

**Mot de passe et balise**

L'avantage d'une solution demandant à la fois un mot de passe et une balise est la sécurité. En effet, c'est pour elle que la probabilité d'un vol de données sensibles est la plus basse. Cependant, elle est aussi la plus contraignante car l'utilisateur doit entrer son mot de passe (ce qui, sur un téléphone portable, peut être pénible. Surtout si ce mot de passe contient des caractères spéciaux ou encore s'il se trompe et qu'il doit le rentrer plus d'un fois d'affilié) et sortir son porte-clés. Cette manière de sécuriser est donc à privilégier si les informations traitées sont très sensibles et que l'utilisateur n'y accède que rarement (afin de ne pas le gêner).

**Mot de passe ou balise**

Probablement la solution la moins contraignante consiste à demander soit un mot de passe soit une balise, mais c'est également la moins sécurisée. Elle possède la plus élevée des probabilités d'un vol de données. Cette solution serait privilégiée dans le cas où l'utilisateur veut accéder rapidement à des données de faibles importances afin d'augmenter sa productivité et son confort d'utilisation.

**Seulement la balise**

Cette dernière solution n'a besoin que d'une balise et elle offre une probabilité relativement faible d'un vol de données. Elle possède l'avantage de la 2ème solution. En effet, il suffit simplement à l'utilisateur de "scanner" son badge pour accéder aux données. Cette solution serait donc à privilégier dans le cas où l'utilisateur veut accéder souvent à des informations relativement sensibles et dont l'utilisation de la première solution serait trop contraignant.

### Partie 3

> *Peut-on améliorer la situation en introduisant un contrôle des informations d'authentification par un serveur éloigné (transmission d'un hash SHA256 du mot de passe et de la balise NFC) ? Si oui, à quelles conditions ? Quels inconvénients ?*

Non, si le voleur possède le mot de passe et/ou le tag NFC, les credentials récupérés seront corrects et donc l'authentification réussira, peu importe si on hash le mot de passe ou le tag. Cependant, si le serveur distant donne un accès limité dans le temps ou simplement une accréditation de validité de la connexion tant que le vol n'a pas été rapporté. Dans ce cas, le voleur n'aura qu'un temps d'accès limité aux données (environ le temps que le vol soit annoncé et que les credentials soitent invalidés). Mais une telle solution aurait un avantage si :

- l'utilisateur s'est rapidement rendu compte du vol
- il a la possibilité d'annoncer le vol (si on lui a pris son téléphone portable, difficile de contacter les personnes à prévenir)
- les personnes s'occupant de la sécurité ont un accès rapide aux fonctionnalités de bloquage de credentials.

**!!! Problème car les données sensibles sont stockées en local !!!**

### Partie 4

> *Proposer une stratégie permettant à la société UBIQOMP SA d'améliorer grandement son bilan sécuritaire, en détailler les inconvénients pour les utilisateurs et pour la société.*

Sachant que les informations véhiculées sont précieuses, il est souhaitable de privilégier la sécurité plutôt que le confort des utilisateurs. Nous pouvons donc exclure la 2ème solution, car comme dit précédemment, elle possède la plus haute probabilité de vol de données. 

Parmi les deux solutions restantes, c'est au choix de l'entreprise. Les avantages et inconvénients ont été exposés précédemment et bien que la sécurité offerte par la première solution est la meilleure, la troisième solution n'en reste pas moins acceptable. 

# Codes-barres

## Question

> *Comparer la technologie à codes-barres et la technologie NFC, du point de vue d'une utilisation dans des applications pour smartphones, dans une optique :*
>
> - *Professionnelle (Authentification, droits d’accès, clés de chiffrage)*
- *Grand public (Billetterie, contrôle d’accès, e-paiement)*
- *Ludique (Preuves d'achat, publicité, etc.)*
- *Financier (Coûts pour le déploiement de la technologie, possibilités de recyclage, etc.)*

**Professionnelle**

Dans optique professionnelle, la technologie NFC est préférable à un reconnaissance optique. Lorsqu'un utilisateur lit un code-barre, ce dernier est visible par tous et facilement copiable, même de loin, par tout personne possédant un appareil photo. De plus la carte ou le bout de papier sur lequel le code-barre est imprimé peut être facilement perdu. A l'inverse, la balise NFC peut être attachée au porte-clés et un appareil doit se situer à quelques centimètres pour la copier.

**Grand public**

???

**Ludique**

???

**Financier**

Du point de vue financier, les codes-barres sont très largement moins couteux que les balises NFC. Néanmoins seul les balises peuvent-être recyclées. Afin d'éviter le remplacement des codes-barres, il est possible d'utiliser une indirection (le code barre contient une adresse URL fixe qui est redirigé automatiquement vers le contenu souhaité).

# Balise iBeacon

## Question

> *Les iBeacons sont très souvent présentés comme une alternative à NFC. Pouvez-vous commenter cette affirmation en vous basant sur 2-3 exemples de cas d’utilisations (use-cases) concrets (par exemple e-paiement, second facteur d’identification, accéder aux horaires à un arrêt de bus, etc.).*

La présence d'un iBeacon à un arrêt de bus, permettrait l'envoi automatique de l'horaire aux personnes possédant l'application de la compagnie du bus. Ce n'est plus l'utilisateur qui va demander l'information (comme c'est le cas avec NFC ou un service web standard) mais l'information qui est envoyé (lorsque le iBeacon est détecté) à l'utilisateur.

Les iBeacons sont facilement falsifiable il n'est donc pas recommandé de les utiliser dans des environnements sensibles. Cependant une technology de rotation UUID (le code d'identification de la balise et modifié périodiquement et de façon à ce que le prochain id ne soit prévisible que en possédant une clé de sécurité) permettrait une identification à deux facteur. Lors de l'authentification l'application enverrait également les infos de iBeacons à proximité et le serveur pourra vérifier si les informations sont valides.

# Capteurs

## Question

> *Une fois la manipulation effectuée, vous constaterez que les animations de la flèche ne sont pas fluides, il va y avoir un tremblement plus ou moins important même si le téléphone ne bouge pas. Veuillez expliquer quelle est la cause la plus probable de ce tremblement et donner une manière (sans forcément l’implémenter) d’y remédier.*
