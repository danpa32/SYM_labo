---
title: "SYM - Labo 1"
author:
    - "Guillaume Milani"
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


# Questions

## Traitement des erreurs

> *Les interfaces AsyncSendRequest et CommunicationEventListener utilisées au point 3.1 restent très
(et certainement trop) simples pour être utilisables dans une vraie application : que se passe-t-il si le
serveur n’est pas joignable dans l’immédiat ou s’il retourne un code HTTP d’erreur ? Vous pouvez par
exemple proposer une nouvelle version de ces deux interfaces pour vous aider à illustrer votre
réponse.*


## Authentification

> *Si une authentification par le serveur est requise, peut-on utiliser un protocole asynchrone ? Quelles
seraient les restrictions ? Peut-on utiliser une transmission différée ?*

Oui, la requête peut se faire via une authentification par "token" par exemple, rajouté dans les entêtes HTTP ou dans un cookie. Idem pour la transmission différée, la requête sera simplement transmise plus tard au serveur. Cependant, le token peut avoir une durée de vie limitée, il faudra donc peut-être en regénérer un et remplacer l'ancien par le nouveau dans la requête.

## Threads concurrents

> *Lors de l'utilisation de protocoles asynchrones, c'est généralement deux threads différents qui se
préoccupent de la préparation, de l'envoi, de la réception et du traitement des données. Quels
problèmes cela peut-il poser ?*

Les threads doivent se synchroniser car il se partage des données et doivent le faire sans générer des problèmes de concurrence afin de s'exécuter dans le bon ordre.
Il y a également le problème du thread dédié à l'interface graphique. Il faut éviter que l'interface ne se fige si l'action entreprise prend du temps. afin d'éviter ceci, les appels asynchrones permettent de lancer un travail en arrière-plan, puis de notifier l'utilisateur du(des) résulat(s). 

## Ecriture différée

> *Lorsque l'on implémente l'écriture différée, il arrive que l'on ait soudainement plusieurs transmissions
en attente qui deviennent possibles simultanément. Comment implémenter proprement cette
situation (sans réalisation pratique) ? Voici deux possibilités :*

> * *Effectuer une connexion par transmission différée*
  * *Multiplexer toutes les connexions vers un même serveur en une seule connexion de transport. Dans ce dernier cas, comment implémenter le protocole applicatif, quels avantages peut-on
espérer de ce multiplexage, et surtout, comment doit-on planifier les réponses du serveur
lorsque ces dernières s'avèrent nécessaires ?*

> *Comparer les deux techniques (et éventuellement d'autres que vous pourriez imaginer) et discuter des
avantages et inconvénients respectifs.*


## Transmission d’objets

> * *Quel inconvénient y a-t-il à utiliser une infrastructure de type REST/JSON n'offrant aucun
service de validation (DTD, XML-schéma, WSDL) par rapport à une infrastructure comme SOAP
offrant ces possibilités ? Y a-t-il en revanche des avantages que vous pouvez citer ?*
* *L’utilisation d’un mécanisme comme Protocol Buffers 6 est-elle compatible avec une
architecture basée sur HTTP ? Veuillez discuter des éventuelles avantages ou limitations par
rapport à un protocole basé sur JSON ou XML ?*

L'inconvénient est que le message doit être validé par le serveur. Avec un service de validation comme DTD, il est plus facile pour le serveur et pour le client de valider la structure des données reçues ou envoyées. Cependant, l'avantage du format JSON est qu'il est bien plus lgéer que le XML, ce qui permet d'obtenir de meilleures performances lors de la transmission.


## Transmission compressée

> *Quel gain peut-on constater en moyenne sur des fichiers texte (xml et json sont aussi du texte) en
utilisant de la compression du point 3.4 ? Vous comparerez vos résultats par rapport au gain théorique
d’une compression DEFLATE, vous enverrez aussi plusieurs tailles de contenu pour comparer.*



# Conclusion

