<h1 align="center">Projet DevOps</h1>

  <div align="center">
    <strong>Bachelor 3 Ynov Ingésup Lyon</strong>
    <br />
    <a href="https://github.com/ebbane/Projet_DevOps"><strong>Explore le repo</strong></a>
    <br />
    <br />
    <a href="https://github.com/ebbane/Projet_DevOps/tree/develop/jenkins">Jenkins</a>
    ·
    <a href="https://github.com/ebbane/Projet_DevOps/tree/develop/terraform">Terraform</a>
    ·
    <a href="https://github.com/ebbane/Projet_DevOps/tree/develop/ansible">Ansible</a>
  </div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table des matières</summary>
  <ol>
    <li>
      <a href="#a-propos-du-projet">A propos du projet</a>
      <ul>
        <li><a href="#construit-avec">Construit avec</a></li>
      </ul>
    </li>
    <li>
      <a href="#commencer">Commencer</a>
      <ul>
        <li><a href="#prérequis">Prérequis</a></li>
        <li><a href="#installations">Installations</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#status">Statut</a></li>
    <li><a href="#ameliorations">Améliorations</a></li>
    <li><a href="#commande-utiles">Commandes utiles</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#remerciements">Remerciements</a></li>
  </ol>
</details>




## A propos du projet
<div align="center">
        <img src="https://www.cyberfella.co.uk/wp-content/uploads/2020/03/devops-fig8-1024x527.png"/>
</div>
<p align="center"><i>Pipeline DevOps</i></p>


Le but de ce projet est de mettre en pratique l’ensemble des connaissances acquises
pendant les 4 journées de formation, afin de réaliser une infrastructure de staging pour une
application java (fournie).

Le rendu attendu est un projet git de type monorepo (contenant le code IaC
terraform, CaC ansible et les pipelines de déploiements jenkins (dans des dossiers différents
pour la clarté)). Ce projet dois tendre à respecter le plus possible le “git flow”.
La propreté des commits et du code (ansible, jenkins et terraform) est primordiale dans la
notation du projet.


<div align="center">
        <img src="https://blog.engineering.publicissapient.fr/wp-content/uploads/2018/03/Gitflow.png"/>
</div>
<p align="center"><i>GitFlow</i></p>

C'est pourquoi :

* Chaque dossier porte le nom de son contenu
* Le projet est composé de plusieurs branches, de Merge Request, de rebase, squash etc...
* La partie docker (`Dockerfile`, `docker-compose.yml`) se trouve à la racine du projet pour ne mettre en place qu'une image docker qui regroupe l'ensemble des fonctionnalités

<p align="right">(<a href="#top">Retour en haut</a>)</p>



### Construit avec

Ce projet est construit avec les logiciels, et applications suivantes :

* [Docker](https://www.docker.com/)
* [Jenkins](https://www.jenkins.io/)
* [Terraform](https://www.terraform.io/)
* [Ansible](https://docs.ansible.com/)
* [Github](https://github.com/)
* [IntelliJ IDEA](https://www.jetbrains.com/fr-fr/idea/)


<p align="right">(<a href="#top">Retour en haut</a>)</p>




## Commencer

Mise en place du projet en local pour les utilisateurs

### Prérequis

Ce projet nécéssite quelques installation de pré-lancement.

* [docker desktop](https://docs.docker.com/desktop/)


### Installations

_Instructions à suivre pour le bon déroulement  des étapes du projet._

1. Lancer docker sur son environnement
2. Cloner le repo (ou déziper le dossier)
   ```sh
   git clone git@github.com:ebbane/Projet_DevOps.git
   ```
3. Entrer dans l'emplacement du dossier via un invite de commandes bash
   ```sh
   cd /votre_emplacement
   ```
4. Lancer la construction de l'image et du conteneur docker
   ```sh
   docker-compose up --build
   ```
5. Ouvrir votre explorer Internet à l'adresse suivante une fois le build terminé.
   _L'image dokcer build est basé sur une image jenkins qui tourne au port 80_

   [http://localhost](http://localhost/login?from=%2F)


<p align="right">(<a href="#top">Retour en haut</a>)</p>




## Usage

Une fois le build terminé et que vous êtes arrivé sur l'interface jenkins via votre navigateur vous devez vous y connecter via l'authentification suivante.
* Uilisateur = `admin`
* Mot de passe = `admin`

Une fois connecté vous trouverez trois dossier :
* CI (Continuous Integration): contient la pipeline pour contruire un jar
* CD (Continuous deploy): contient quatres pipeline a exécuter dans l'ordre suivant
    * SSH : pipeline générant une clé ssh sur l'instance. Une fois lancé il faut récupérer dans la console output la clé ssh
    * Terraform : pipeline permettant de créer une instance AWS. Nécéssite la clé ssh récupéreé précédément. `ATTENTION : merci de rajouter des backslash avant chaque slash de la clé. Si le build fail c'est qu'un backslash a été oublié`
    * Ansible : pipeline d'exploitation de l'instance AWS. Nécéssite l'host au format user@AWS_IP : l'address ip de l'instance se trouve en console output de la pipeline terraform.
    * Terraform-destroy : pipeline de destruction de l'instance une fois sont utilisation terminé.

Le playbook `monitor-playbook.yml` est un playbook créer à la suite du premier qui est utilisé par le deuxième utilisateur en provenance du cloud-init.
Son but était de vérifier le fonctionnement de l'application (jar). Je n'ai pas réussi à lancer deux taches ansible en même temps (malgré le paramètre async, poll).
Je pensais alors créer un autre pipeline juste pour le lancer en même temps que le premier, néanmoins jenkins n'est pas configurer pour lancer deux builds en même temps.

<p align="right">(<a href="#top">Retour en haut</a>)</p>



## Statut

- [x] Pipeline de build et de CI
- [x] Pipeline de IaC
- [ ] Pipeline de CaC
    - [x] playbook fonctionnel
    - [x] Découpage du playbook via ansible-galaxy

Pour une raison que j'ignore l'étape d'exécution du playbook ansible dans la pipeline ne fonctionne pas et fait ressortir l'erreur suivant `java.nio.file.AccessDeniedException: /usr/share/ansible@tmp`.
Je suis pourtant bien connecter avec l'utilisateur jenkins.
Néanmoins, lorsque l'on se connecte sur le cli de docker et que l'on exécute les commande suivante le playbook agis correctement :

1. Accéder au dossier contenant le playbook déclarer dans le `dockerFile`
   ```sh
   cd /usr/share/ansible
   ```
2. Lancer le playbook avec les hosts autorisé à le faire et leurs clés privé
   ```sh
    ansible-playbook playbook.yml -i hosts --private-key ~/.ssh/id_rsa
    ```
Je ne vois aucune différence entre la commande éxécuté manuellement via le cli de docker et celle de la pipeline ansible.

<p align="right">(<a href="#top">Retour en haut</a>)</p>




## Améliorations

Étant donné le résultat de la pipeline ansible le premier axe d'amélioration porte sur la résolution de ce problème.
Ensuite, il y a plusieurs choses à mettre en place pour simplifier l'expérience utilisateur :
* Copier la clé ssh d'une autre manière que celle existante
    * Supprimer l'intervention humaine
* Mise en place d'in inventaire dynamique fonctionnelle. Après avoir regardé et tester quelque configuration l'inventaire dynamique `EC2_inventory.yml` n'est q'un préquel.
* Une vérification du lancement de l'application au port 8080 plus performante que celle proposée


<p align="right">(<a href="#top">Retour en haut</a>)</p>



## Commandes utiles


1. Savoir l'état du port 22 de l'instance AWS
   ```ubuntu
     nc -vz ${AWS_INSTANCE_IP} 22

2. Se connecter en ssh sur une instance AWS
   ```sh
      ssh -i ${SSH_PRIVATE_KEY_PATH} ${CLOUD_INIT_USER}@${AWS_INSTANCE_IP}
    ```
3. Vérifier les images docker disponible
   ```sh
      docker images -a
    ```
4. Supprimer une image docker
   ```sh
      docker rmi <image_id>
    ```


<p align="right">(<a href="#top">Retour en haut</a>)</p>



## Contact

Ebbane DIET - ebbane.diet@ynov.com

Github : [@ebbane](https://github.com/ebbane)

<p align="right">(<a href="#top">Retour en haut</a>)</p>



## Remerciements

Documentations et références utiles à la réalisation de ce projet
* [Ansible - Interacts with webservices](https://docs.ansible.com/ansible/latest/collections/ansible/builtin/uri_module.html)
* [Running Terraform with Jenkins Pipelines](https://medium.com/@pb8226/running-terraform-with-jenkins-pipelines-f29a8cb861d4)
* [Docker - Dockerfile](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
* [Stackoverflow](https://stackoverflow.com/)
* [Inventaire dynamique](https://docs.ansible.com/ansible/latest/collections/amazon/aws/aws_ec2_inventory.html)

<p align="right">(<a href="#top">Retour en haut</a>)</p>

<div align="center">
        <img src="https://i.morioh.com/210621/2fef50c8.webp"/>
</div>
<p align="center"><i>Release 1.1.0 | 20/12/2021</i></p>
