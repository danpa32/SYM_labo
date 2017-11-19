---
title: "SYM-1-B - Labo 2"
author:
    - "Guillaume MILANI"
    - "Christopher MEIER"
    - "Daniel PALUMBO"
date:           "19 novembre 2017"
geometry:       "margin=1in"
papersize:      "a4"
monofont:       "Inconsolata"
documentclass:  "report"
lang:           "fr"
---

# Introduction

Ce laboratoire a pour but de prendre en main le traitement asynchrone des requêtes HTTP lors du développement d'une application Android. Il permet d'implémenter différentes manières de traiter ces requêtes et de les comparer.

# Compléments d'information

## Traitement différé

Le fait de garder les requêtes différée uniquement en mémoire peut causer des problèmes si le système est arrêté ou que l'application est tuée. En effet dans ces cas de figures, des informations (peut-être importante) peuvent être perdues.

# Questions

## Traitement des erreurs

> *Les interfaces `AsyncSendRequest` et `CommunicationEventListener` utilisées au point 3.1 restent très (et certainement trop) simples pour être utilisables dans une vraie application : que se passe-t-il si le serveur n’est pas joignable dans l’immédiat ou s’il retourne un code HTTP d’erreur ? Vous pouvez par exemple proposer une nouvelle version de ces deux interfaces pour vous aider à illustrer votre réponse.*

Pour une vraie utilisation, il faudrait en premier temps pouvoir gérer les erreurs : il y aurait donc une méthode `handleErrorResponse(int statusCode, String response)` permettant au programme de se corriger dans le cas où il y aurait un problème.

## Authentification

> *Si une authentification par le serveur est requise, peut-on utiliser un protocole asynchrone ? Quelles seraient les restrictions ? Peut-on utiliser une transmission différée ?*

Oui, la requête peut se faire via une authentification par "token" par exemple, rajouté dans les entêtes HTTP ou dans un cookie. Idem pour la transmission différée, la requête sera simplement transmise plus tard au serveur. Cependant, le token peut avoir une durée de vie limitée, il faudra donc peut-être en regénérer un et remplacer l'ancien par le nouveau dans la requête.

Dans le cas où l'authentification requiert un couple nom d'utilsateur - mot de passe, il faudrait stocker de manière sécurisé ces informations pour pouvoir les utiliser au moment de la transmission effective.

## Threads concurrents

> *Lors de l'utilisation de protocoles asynchrones, c'est généralement deux threads différents qui se préoccupent de la préparation, de l'envoi, de la réception et du traitement des données. Quels problèmes cela peut-il poser ?*

Les threads doivent se synchroniser car ils se partagent des données et doivent le faire sans générer des problèmes de concurrence afin de s'exécuter dans le bon ordre.
Il y a également le problème du thread dédié à l'interface graphique. Il faut éviter que l'interface ne se fige si l'action entreprise prend du temps. Pour ce faire, les appels asynchrones permettent de lancer un travail en arrière-plan, puis de notifier l'utilisateur du(des) résulat(s). 

## Ecriture différée

> *Lorsque l'on implémente l'écriture différée, il arrive que l'on ait soudainement plusieurs transmissions en attente qui deviennent possibles simultanément. Comment implémenter proprement cette situation (sans réalisation pratique) ? Voici deux possibilités :*

> * *Effectuer une connexion par transmission différée*
  * *Multiplexer toutes les connexions vers un même serveur en une seule connexion de transport. Dans ce dernier cas, comment implémenter le protocole applicatif, quels avantages peut-on espérer de ce multiplexage, et surtout, comment doit-on planifier les réponses du serveur lorsque ces dernières s'avèrent nécessaires ?*

> *Comparer les deux techniques (et éventuellement d'autres que vous pourriez imaginer) et discuter des avantages et inconvénients respectifs.*

La technique de multiplexage permet de diminuer la charge ajoutée due aux protocoles TCP et HTTP, mais il demande une complexification assez importante de la manière de gérer les requêtes coté serveur. Si les requêtes contiennent des réponses, il y aussi une complexification de la gestion des réponses côté client.

L'établissement d'une connxion par transmission différée permet de garder un système simple mais au prix de tout la charge propre au protocoles.

## Transmission d’objets

> * *Quel inconvénient y a-t-il à utiliser une infrastructure de type REST/JSON n'offrant aucun service de validation (DTD, XML-schéma, WSDL) par rapport à une infrastructure comme SOAP offrant ces possibilités ? Y a-t-il en revanche des avantages que vous pouvez citer ?*
> * *L’utilisation d’un mécanisme comme Protocol Buffers 6 est-elle compatible avec une architecture basée sur HTTP ? Veuillez discuter des éventuelles avantages ou limitations par rapport à un protocole basé sur JSON ou XML ?*

L'inconvénient est que le message doit être validé par le serveur. Avec un service de validation comme DTD, il est plus facile pour le serveur et pour le client de valider la structure des données reçues ou envoyées. Cependant, l'avantage du format JSON est qu'il est bien plus léger que le XML, ce qui permet d'obtenir de meilleures performances lors de la transmission.

On peut tout à fait utiliser le contenu binaire de Protocol Buffer comme contenu du corps de la requête HTTP. L'avantage est diminution de la taille de la serialization, par contre lors du debugage le contenu n'est pas lisible en clair.

## Transmission compressée

> *Quel gain peut-on constater en moyenne sur des fichiers texte (xml et json sont aussi du texte) en utilisant de la compression du point 3.4 ? Vous comparerez vos résultats par rapport au gain théorique d’une compression DEFLATE, vous enverrez aussi plusieurs tailles de contenu pour comparer.*

Pour le contenu de grande taille on peut s'attendre à un ratio de compression jusqu'a 30% de la taille originelle. Cependant pour le contenu de très petite taille, la compression peut augmenter la taille de la requête. 

+-------------+------------+---------+
| Taille      | Compressé  | Ratio   |
+=============+============+=========+
| 4 bytes     | 6 bytes    | 150%    |
+-------------+------------+---------+
| 156 bytes   | 115 bytes  | 73.7%   |
+-------------+------------+---------+
| 3065 bytes  | 1222 bytes | 39.9%   |
+-------------+------------+---------+
| 18345 bytes | 5608 bytes | 30.6%   |
+-------------+------------+---------+

# Conclusion

Le développement des classes de bases permettant la gestion des requêtes asynchrones a occupé la plus grande partie du temps consacré à ce laboratoire et s'est avéré être le plus complexe. Ceci fait, nous avons pu développer plus rapidement les différentes implémentations utilisant cette fonctionnalité. Le laboratoire s'est bien déroulé, même si nous sommes conscients qu'il y aurait encore du travail pour pouvoir utiliser ces fonctionnalités dans une application en production.