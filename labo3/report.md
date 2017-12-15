---
title: "SYM-1-B - Labo 2"
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

> *- La probabilité de vol d'un mobile par une personne malintentionnée et capable d'utiliser les données à des fins préjudiciables pour la société est de 1%.
- La probabilité que le mot de passe puisse être découvert, soit par analyse des traces de doigts sur l'écran, soit par observation en cours d'utilisation est de 4%.
- La probabilité de vol du porte-clés est de 0.1%.
- Environ 10% des criminels susceptibles d'accéder aux données du mobile sait que le porte- clés permet l’accès au mobile.*

## Questions

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

Chacune des solutions proposées possédent des avantages et des inconvénients dans l'utilisation et le traitement des données et de l'application. Dans le cas de l'utilisation d'un mot de passe, l'utilisateur se doit de l'entrer réguliérement avant de pouvoir accéder à une zone critique de celle-ci. En comparaison de la balise NFC, l'utilisateur se doit de sortir son porte-clés et "scanner" la balise au dos du téléphone portable ce qui implique qu'il doit l'avoir sur soit. Comme nous ne savons pas quel peut être l'impact d'un vol de données, les trois cas précédent doivent être pris en compte et la solution à privilégier dépendra de l'utilisation et de la nature des données.

**Mot de passe et balise**

L'avantage de cette solution est qu'elle est la plus sécurisée des trois. En effet, la probabilité d'un vol de données sensibles est la plus basse. Cependant, elle est la plus contraignante car l'utilisateur doit entrer son mot de passe qui sur un téléphone portable peut être pénible surtout si ce mot de passe contient des caractères spéciaux (de plus l'utilisateur peut devoir le rentrer plus d'un fois d'affilié s'il se trompe) et sortir son porte-clés. Cette sécurisation est donc à privilégier si les informations traitées sont de grande importance et que l'utilisateur n'accède que rarement à ces données afin de ne pas le gêner.

**Mot de passe ou balise**

Probablement la solution la moins contraignante, mais également la moins sécurisée et qui donc possède la probabilité la plus élevée de vol de données. La sécurité de cette solution serait à peu près comparable à celle d'avoir besoin que du mot de passe pour accéder aux données. Cette solution pourrait donc être privilégiée dans le cas où l'utilisateur veut accéder rapidement à des données de faibles importances afin d'augmenter sa productivité et son confort d'utilisation.

**Seulement la balise**

La dernière solution offre une probabilité relativement faible de vol de données et possède l'avantage de la 2ème solution. En effet, il suffit simplement à l'utilisateur de "scanner" son badge pour accéder aux données. Cette solution serait donc à privilégier dans le cas où l'utilisateur veut accéder souvent à des informations sensibles et dont l'utilisation de la première solution serait trop contraignant.


> *Peut-on améliorer la situation en introduisant un contrôle des informations d'authentification par un serveur éloigné (transmission d'un hash SHA256 du mot de passe et de la balise NFC) ? Si oui, à quelles conditions ? Quels inconvénients ?*

Non, si le voleur possède le mot de passe et/ou le tag NFC, les credentials récupérés seront corrects et donc l'authentification réussira, peu importe si on hash le mot de passe ou le tag. Cependant, si le serveur distant donne un accès limité dans le temps ou simplement une accréditation de validité de la connexion tant que le vol n'a pas été rapporté. Dans ce cas, le voleur n'aura qu'un temps d'accès limité aux données (environ le temps que le vol soit annoncé et que les credentials soitent invalidés). Mais une telle solution aurait un avantage si :

- l'utilisateur s'est rapidement rendu compte du vol
- il a la possibilité d'annoncer le vol (si on lui a pris son téléphone portable, difficile de contacter les personnes à prévenir)
- les personnes s'occupant de la sécurité ont un accès rapide aux fonctionnalités de bloquage de credentials.

> *Proposer une stratégie permettant à la société UBIQOMP SA d'améliorer grandement son bilan sécuritaire, en détailler les inconvénients pour les utilisateurs et pour la société.*

Sachant que les informations véhiculées sont précieuses, il est donc souhaitable de privilégier la sécurité plutôt que le confort des utilisateurs. Nous pouvons donc exclure la 2ème solution, car comme dit précédemment, elle possède la plus haute probabilité de vol de données. 

Parmi les deux solutions restantes, c'est au choix de l'entreprise. Les avantages et inconvénients ont été exposés précédemment et bien que la probabilité de la première solution est la meilleure, la troisième solution n'en reste pas moins acceptable. 

