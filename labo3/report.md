---
title: "SYM-1-B - Labo 3"
author:
    - "Christopher MEIER"
    - "Guillaume MILANI"
    - "Daniel PALUMBO"
date:           "22 décembre 2017"
geometry:       "margin=1in"
papersize:      "a4"
monofont:       "Inconsolata"
documentclass:  "report"
lang:           "fr"
---

# Introduction

# NFC

## Mise en situation

> *Sachant que les collaborateurs de l’entreprise UBIQOMP SA se déplacent en véhiculant des informations précieuses dans leurs dispositifs informatiques mobiles (munis de dispositifs de lecture NFC), et qu’ils sont amenés à se rendre dans des zones à risque, un expert a fait les estimations suivantes :*

> - *La probabilité de vol d’un mobile par une personne malintentionnée et capable d’utiliser les données à des fins préjudiciables pour la société est de 1%.*
- *La probabilité que le mot de passe puisse être découvert, soit par analyse des traces de doigts sur l’écran, soit par observation en cours d’utilisation est de 4%.*
- *La probabilité de vol du porte-clés est de 0.1%.*
- *Environ 10% des criminels susceptibles d’accéder aux données du mobile savent que le porte-clés permet l’accès à ce dernier.*

## Questions

### Partie 1

> *Quelle est la probabilité moyenne globale que des données soient perdues, dans le cas où il faut la balise ET le mot de passe, ainsi que dans le cas où il faut la balise OU le mot de passe (on négligera dans le calcul la probabilité de l’intersection des deux ensembles), ou encore le cas où seule la balise est nécessaire ?*

Soit les événements suivants:

- A = le mobile est volé par une personne malintentionnée et capable d’utiliser les données à des fins préjudiciables pour la société
- B = le mot de passe est découvert
- C = la balise (sur le porte-clés) est volée
- D = le criminel sait que le porte-clés donne accès aux mobiles

Nous avons donc les probabilités suivantes:

- $P(A) = 0.01$
- $P(B) = 0.04$
- $P(C) = 0.001$
- $P(D | A) = 0.1$

Pour les prochains calculs, nous supposons que toutes les probabilités sont indépendantes.

**Balise ET mot de passe**

$P(D\cap B \cap C) = P(D) \cdot P(B) \cdot P(C) = P(D | A) \cdot P(A) \cdot P(B) \cdot P(C) = 0.1 \cdot 0.01 \cdot 0.04 \cdot 0.001 = 0.00000004$

Le résultat est négligeable en ce qui concerne la perte de données protégées par un mot de passe et un tag NFC.

**Balise OU mot de passe**

$P((D \cap C) \cup (A \cap B)) = P(S) \cdot P(C) + P(A) \cdot P(B) = P(D | A) \cdot P(A) \cdot P(C) + P(A) \cdot P(B) = 0.1 \cdot 0.01 \cdot 0.001 + 0.01 \cdot 0.04 = 0.0004 + 0.000001 = 0.000401$

**Seulement la balise**

$P(A) \cdot P(C) \cdot P(D | A) = 0.01 * 0.001 * 0.1 = 0.000001$

> *Si l’on envoie cent collaborateurs en déplacement, quel est le risque encouru de vol de données sensibles ?*

Pour le calcul de cette information, nous devons :

1) Calculer la probabilité que le cas n'arrive pas
2) Multiplier les probabilités suivants le nombre de collaborateurs. nous obtenons ainsi la probabilité que ce cas n'arrive pas pour x collaborteurs
3) Calculer la probabilité que ce cas arrive pour x collaborateurs

**Balise ET mot de passe**

1) $1-0.00000004 = 0.99999996$
2) $0.99999996^{100} = 0.999996$
3) $1-0.999996 = 0.0000039$

**Balise OU mot de passe**

$1-(1-0.000401)^{100} = 0.03931$

**Seulement la balise**

$1-(1-0.000001)^{100} = 0.000099$

### Partie 2

> *Mettez vos conclusions en rapport avec l’inconfort subjectif de chaque solution.*

Chacune des solutions proposées possède des avantages et des inconvénients dans l’utilisation et le traitement des données et de l’application. Dans le cas de l’utilisation d’un mot de passe, l’utilisateur se doit de l’entrer régulièrement avant de pouvoir accéder à une zone critique de celle-ci. En comparaison de la balise NFC, l’utilisateur se doit de sortir son porte-clés et "scanner" la balise au dos du téléphone portable ce qui implique qu’il doit l’avoir sur soi. Comme nous ne savons pas quel peut être l’impact d’un vol de données, les trois cas précédents doivent être pris en compte et la solution à privilégier dépendra de l’utilisation et de la nature des données.

**Mot de passe et balise**

L’avantage d’une solution demandant à la fois un mot de passe et une balise est la sécurité. En effet, c’est pour elle que la probabilité d’un vol de données sensibles est la plus basse. Cependant, elle est aussi la plus contraignante, car l’utilisateur doit entrer son mot de passe (ce qui, sur un téléphone portable, peut être pénible. Surtout si ce mot de passe contient des caractères spéciaux ou encore s’il se trompe et qu’il doit le rentrer plus d’une fois d’affilié) et sortir son porte-clés. Cette manière de sécuriser est donc à privilégier si les informations traitées sont très sensibles et que l’utilisateur n’y accède que rarement (afin de ne pas le gêner).

**Mot de passe ou balise**

Probablement la solution la moins contraignante consiste à demander soit un mot de passe soit une balise, mais c’est également la moins sécurisée. Elle possède la plus élevée des probabilités d’un vol de données. Cette solution serait privilégiée dans le cas où l’utilisateur veut accéder rapidement à des données de faibles importances afin d’augmenter sa productivité et son confort d’utilisation.

**Seulement la balise**

Cette dernière solution n’a besoin que d’une balise et elle offre une probabilité relativement faible d’un vol de données. Elle possède l’avantage de la 2e solution. En effet, il suffit simplement à l’utilisateur de "scanner" son badge pour accéder aux données. Cette solution serait donc à privilégier dans le cas où l’utilisateur veut accéder souvent à des informations relativement sensibles et dont l’utilisation de la première solution serait trop contraignante.

### Partie 3

> *Peut-on améliorer la situation en introduisant un contrôle des informations d’authentification par un serveur éloigné (transmission d’un hash SHA256 du mot de passe et de la balise NFC) ? Si oui, à quelles conditions ? Quels inconvénients ?*

Non, dans un contexte où les données sont stockées en locale avec un chiffrement dont la clé est envoyée par le serveur, si le voleur possède le mot de passe et/ou le tag NFC, les credentials récupérés seront corrects et donc l’authentification réussira. Peu importe si l’on hash le mot de passe ou le tag, la clé sera envoyée au voleur et il pourra déchiffrer les données. Cependant, si le serveur distant donne un accès limité dans le temps ou simplement une accréditation de validité de la connexion tant que le vol n’a pas été rapporté, dans ce cas le voleur n’aura qu’un temps d’accès limité aux données (environ le temps que le vol soit annoncé et que les credentials soient invalidés). Mais une telle solution aurait un avantage si :

- l’utilisateur s’est rapidement rendu compte du vol
- il a la possibilité d’annoncer le vol (si on lui a pris son téléphone portable, difficile de contacter les personnes à prévenir)
- les personnes s’occupant de la sécurité ont un accès rapide aux fonctionnalités de blocage de credentials.

De plus, dans un contexte où les collaborateurs se trouvent dans une zone hors-réseau, ils ne pourront pas accéder aux données car le serveur distant n'est pas accessible et donc la mobilité du système perds tout son intérêt.

### Partie 4

> *Proposer une stratégie permettant à la société UBIQOMP SA d’améliorer grandement son bilan sécuritaire, en détailler les inconvénients pour les utilisateurs et pour la société.*

Sachant que les informations véhiculées sont précieuses, il est souhaitable de privilégier la sécurité plutôt que le confort des utilisateurs. Nous pouvons donc exclure la 2e solution, car comme dit précédemment, elle possède la plus haute probabilité de vol de données.

Parmi les deux solutions restantes, c’est au choix de l’entreprise. Les avantages et inconvénients ont été exposés précédemment et bien que la sécurité offerte par la première solution soit la meilleure, la troisième solution n’en reste pas moins acceptable.

# Code-barres

## Question

> *Comparer la technologie à code-barres et la technologie NFC, du point de vue d’une utilisation dans des applications pour smartphones, dans une optique :*
>
> - *Professionnelle (Authentification, droits d’accès, clés de chiffrage)*
- *Grand public (Billetterie, contrôle d’accès, e-paiement)*
- *Ludique (Preuves d’achat, publicité, etc.)*
- *Financier (Coûts pour le déploiement de la technologie, possibilités de recyclage, etc.)*

**Professionnelle**

Dans une optique professionnelle, la technologie NFC est préférable à une reconnaissance optique. Lorsqu’un utilisateur lit un code-barres, ce dernier est visible par tous et facilement copiable, même de loin, par toute personne possédant un appareil photo. De plus, la carte ou le bout de papier sur lequel le code-barres est imprimé peut être facilement perdu. A l’inverse, la balise NFC peut être attachée au porte-clés et un appareil doit se situer à quelques centimètres pour la copier.

**Grand public**

Dans l’optique grand publique, nous nous devons de prendre en compte différents points que nous considérons comme importants et qui sont :

- L’authentification permanente
- L’authentification temporaire
- La réalité augmentée
- Les marques de téléphones portables

Si nous considérons l’authentification permanente, la technologie NFC a vu un succès grandissant au niveau des modes de paiement pour de petits montants ou les abonnements (principalement lié aux transports publics). En effet, elle peut supplanter les cartes de crédit et les cartes à puce électroniques permettant ainsi le paiement mobile qui complète ou même remplace ces systèmes. Nous pouvons citer comme exemple, Google wallet, qui permet aux utilisateurs de stocker des cartes de crédit et des informations sous forme de carte de fidélité dans un portefeuille virtuel, puis d’utiliser un appareil compatible NFC sur des terminaux acceptant les transactions MasterCard PayPass. Certains pays sont même allés plus loin et utilisent à grande échelle la technologie NFC, comme la Chine dont les autobus possèdent presque tous cette technologie faisant office de billetterie ou de scan d’abonnements. Enfin, la technologie peut également être utilisée en tant que serrure électronique pour ouvrir une porte, comme la démontré la compagnie Yale Lock et qui permettrait un contrôle des accès à un immeuble ou une section dans un bâtiment. Bien que cette technologie offre des avantages de par son accès à courte proximité, il n’en est pas moins que la sécurité et la confidentialité des données restent un sujet en plein débat. En effet, une des craintes est qu’un utilisateur malveillant pourrait lire les fréquences radio émises en passant proche du téléphone ou du portefeuille de la victime contenant des cartes de crédit NFC. De même, ces utilisateurs malveillants pourraient accrocher un lecteur par-dessus la serrure électronique afin de collecter des informations sur les applications contenues dans un téléphone portable (application Postfinance, de banque, etc.). Il n’en reste pas moins que la technologie NFC est plus difficile à falsifier qu’un simple code-barres.

Si nous considérons l’authentification temporaire, la technologie NFC a tendance à poser un problème de coûts versus son utilité comparé à la technologie à code-barres. En effet, les dernières années ont montré que les code-barres, qu’ils soient imprimés ou affichés sur un écran, sont bien moins chers à placer en grand nombre et offrent une certaine faciliter en tant que preuve d’achat. Nous les voyons souvent sur les produits (emballage alimentaire, jouets, etc.) vendus au quotidien dans les grandes surfaces ou les boutiques ou en remplacement des billets classiques pour des festivals par exemple. Les code-barres sont également beaucoup utilisé dans le domaine hospitalier afin identifier un patient, la technologie NFC étant trop coûteuse à mettre en place. De par leur simplicité et son coût de production, la technologie à code-barre permet un gain d'argent et de temps considérable comparé à NFC.

Si nous considérons la réalité augmentée, le domaine est en pleine expansion. L’application de la virtualisation du monde réel dans le domaine de l’échange d’information par nos sens est une avancée dont la technologie à code-barres a su plaire au grand public. Comme exemple, nous avons les musées qui, de plus en plus, place de tag NFC ou des QR codes sur les panneaux d’informations liés à des objets exposés pour informer plus en détail l’utilisateur sur leurs histoires. La technologie à code-barres est également utilisée pour informer l’utilisateur sur l’horaire des transports publics ou l’itinéraire de ceux-ci.

Le dernier point abordé de cette section est les marques de téléphones portables. Ce point nous semble important, car bien que la technologie pour la lecture de code-barres se trouve dans presque tous les smartphones, ce n’est pas le cas, ou presque, pour la technologie NFC. Apple, qui possède une bonne part de marché dans la téléphonie mobile, n’a pas suivi le mouvement pour cette technologie. Cela ne fait que depuis l’iPhone 7 ou 7+ que la compagnie a décidé d’intégrer cette technologie, et avec un strict encadrement :

- l’utilisateur ne peut que lire les données contenues dans un tag NFC, pas d’écriture possible
- la lecture est limitée aux tags contenant des données NDEF,
- les fonctions du framework ne sont pas activées à l’échelle du système et l’utilisateur devra donc spécifiquement lancer une application qui prend en charge ce framework.

Ainsi, pour l’utilisation dans le domaine du commerce (information sur les produits, etc.) ou dans le monde de la culture (musées, etc.), cela ne devrait pas poser de problèmes à l’utilisateur, si bien sûr il possède une version supérieure ou égale à l’iPhone 7, mais dans le cas de paiement, cela ne semble pas possible. Cette technologie a donc également une limite de par son utilisation par certaines compagnies comparées à la technologie par code-barres.

**Ludique**

L'utilisation ludique des 2 technologies se défendent. Dans le domaine du sport ou du jeu, les entreprises/compagnies peuvent utilisés la technologie NFC ou à code-barres pour créer des cartes à collectionner contenant des informations sur leurs sportifs favoris ou sur des personnages fictifs. Cette technique permettrait d'attirer les collectionneurs et/ou de fidéliser de nouveaux clients. De plus, afin que ces cartes ne soient pas en abondance, elles pourraient être gagnées qu'en réussissant une série de défi ou durant une période limitée. Un autre exemple serait l'utilisation de code-barre par des entreprises ou par la ville en les plaçant dans des lieux spécifiques afin que les utilisateurs obtiennent un pokémon dans l'application Pokémon Go en les scannant, ceci à des fins publicitaires.

**Financier**

Du point de vue financier, les code-barres sont très largement moins couteux que les balises NFC. Néanmoins, seules les balises peuvent être recyclées. Afin d’éviter le remplacement des code-barres, il est possible d’utiliser une indirection (le code-barres contient une adresse URL fixe qui est redirigée automatiquement vers le contenu souhaité).

# Balise iBeacon

## Question

> *Les iBeacons sont très souvent présentés comme une alternative à NFC. Pouvez-vous commenter cette affirmation en vous basant sur 2-3 exemples de cas d’utilisations (use-cases) concrets (par exemple e-paiement, second facteur d’identification, accéder aux horaires à un arrêt de bus, etc.).*

La présence d’un iBeacon à un arrêt de bus permettrait l’envoi automatique de l’horaire aux personnes possédant l’application de la compagnie du bus. Ce n’est plus l’utilisateur qui va demander l’information (comme c’est le cas avec NFC ou un service web standard), mais l’information qui est envoyée (lorsque le iBeacon est détecté) à l’utilisateur.

Les iBeacons sont facilement falsifiables, il n’est donc pas recommandé de les utiliser dans des environnements sensibles. Cependant, une technologie de rotation UUID (le code d’identification de la balise est modifié périodiquement et de façon à ce que le prochain id ne soit prévisible que en possédant une clé de sécurité) permettrait une identification à deux facteurs. Lors de l’authentification, l’application enverrait également les informations de iBeacons à proximité et le serveur pourra vérifier si les informations sont valides.

# Capteurs

## Question

> *Une fois la manipulation effectuée, vous constaterez que les animations de la flèche ne sont pas fluides, il va y avoir un tremblement plus ou moins important même si le téléphone ne bouge pas. Veuillez expliquer quelle est la cause la plus probable de ce tremblement et donner une manière (sans forcément l’implémenter) d’y remédier.*
