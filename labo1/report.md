---
title: "SYM - Labo 1"
author:
    - "Guillaume Milani"
	- "Christopher MEIER"
	- "Daniel PALUMBO"
date: "02 octobre 2017"
geometry: "margin=1in"
papersize: "a4"
monofont: "Inconsolata"
---

# Questions

## 1)

> *Comment organiser les textes pour obtenir une application multi-langues
(français, allemand, italien, langue par défaut : anglais) ? Que se passe-t-il
si une traduction est manquante ?*

Il faut créer un dossier se nommant `values-{country code}` dans les ressources.
Ce dossier contiendra un fichier `strings.xml`. La langue par défaut est celle
contenue dans le fichier `strings.xml` dans le sous-dossier `values` dans les
ressources.

Android détermine à l’exécution de l’application et en fonction de la langue de
l’OS, la langue à utiliser.

Lien vers cette information : [android developer languages](https://developer.android.com/training/basics/supporting-devices/languages.html)

## 2)

> *Dans  l’exemple  fourni,  sur  le  dialogue  pop-up,  nous  affichons  
l’icône android.R.drawable.ic_dialog_alert , disponible dans le SDK Android mais
qui n’est pas très bien adaptée visuellement à notre utilisation.
Nous souhaitons la remplacer avec notre propre icône, veuillez indiquer comment
procéder. Dans quel(s) dossier(s) devons-nous ajouter cette image ? Décrivez
brièvement la logique derrière la gestion des ressources de type « image » sur
Android.*

> *Info : Google met à disposition des icônes open source dans le style
« Material Design » utilisé actuellement sur Android : https://design.google.com/icons/*

Il faut ajouter l'icone dans *drawable* puis modifier le code dans la boîte de
dialogue d'erreur :

```
alertbd.setIcon(R.drawable.ic_highlight_off_black_24dp);
```

## 3)

> *Lorsque le login est réussi, vous êtes censé chaîner une autre Activity en
utilisant un Intent. Si je presse le bouton "Back" de l'interface Android, que
puis-je constater ? Comment faire pour que l'application se comporte de manière
plus logique ?*

Il me renvoi sur le menu du téléphone alors qu'il serait plus logique de me
renvoyer sur la page de login de l'application. Afin d'obtenir ce résultat il
suffit d'ajouter cette méthode dans la classe `DisplayActivity` :

```
@Override
public void onBackPressed() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    this.finish();
}
```

## 4)

> *On pourrait imaginer une situation où cette seconde Activity fournit un
résultat (par exemple l’IMEI ou une autre chaîne de caractères) que nous
voudrions récupérer dans l'Activity de départ. Comment procéder ?*

Nous pouvons utiliser la méthode *setResult()* qui permet d'envoyer un résultat
à l'activité :

1) Créer un nouvelle activité dans l'activité principale

    ```
    Intent intent2 = new Intent(this, Activity2.class);
    startActivityForResult(intent2, 1);
    ```

2) Dans l'activité créée, on récupère la valeure souhaitée et on la place dans
un intent que l'on renvoie à l'activité principale.

    ```
    Intent intent = new Intent();
    intent.putExtra("editTextValue","value_here")
    setResult(RESULT_OK, intent);
    finish();
    ```

3) Récupération du résultat

    ```
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String str = data.getStringExtra("editTextValue");
            }
        }
    }
    ```

## 5)

> *Vous noterez que la méthode getDeviceId() du TelephonyManager, permettant
d’obtenir l’IMEI du téléphone, est dépréciée depuis la version 26 de l’API.
Veuillez discuter de ce que cela implique lors du développement et de présenter
une façon d’en tenir compte avec un exemple de code.*



## 6)

> *Dans l’activité de login, en plaçant le téléphone (ou l’émulateur) en mode
paysage (landscape), nous constatons que les 2 champs de saisie ainsi que le
bouton s’étendent sur toute la largeur de l’écran. Veuillez réaliser un layout
spécifique au mode paysage qui permet un affichage mieux adapté et indiquer
comment faire pour qu’il soit utilisé à l’exécution.*

Il suffit d'aller dans le fichier `AndroidManifest.xml` et de rajouter le code
ci-dessous dans l'activité contenant le login.

```
android:screenOrientation="landscape"
```

## 7)

> *Le layout de l’interface utilisateur de l’activité de login qui vous a été
fourni a été réalisé avec un LinearLayout à la racine. Nous vous demandons de
réaliser un layout équivalent utilisant cette fois-ci un RelativeLayout.*

`authent_with_relative_layout.xml`

## 8)

> *Implémenter dans votre code les méthodes onCreate(), onStart(), onResume(),
onPause(), onStop(), etc... qui marquent le cycle de vie d'une application
Android, et tracez leur exécution. Décrivez brièvement à quelles occasions ces
méthodes sont invoquées. Si vous aviez (par exemple) une connexion Bluetooth
(ou des connexions bases de données, ou des capteurs activés) ouverte dans votre
Activity, que faudrait-il peut-être faire, à votre avis (nous ne vous demandons
pas de code ici) ?*
