PRIO
===
1. Messages
2. Base Evolutions valeurs par défaut
3. Base contraintes
4. Ecran mesure/evenements perso à replacer
5. Spring
6. Cache image
7. Evols Admin à recenser

BACKLog
===
ADM: Ecran Administration
--
 - utilisateurs
 - Profils

Webflow
===
REC0 home
REC1.1 Inspection/selection
REC1.2 Inspection/monitor
REC1.3 Inspection/zoom
REC2.1 Modele/selection
REC2.2 modele/edition
REC2.3 modele/PU
REC2.4 modele/events
REC2.5 modele/verdicts
REC2.6 modele/sortingverdicts
REC2.7 modele/customEvents
REP0 home
REP1.1 inspection/list
REP1.2 inspection/detail
REP1.3 inspection/docclient

ADM1 USER
ADM2

Valeurs par défaut
====

def_rule_value.default:boolean

def_proces_unit_parameter.defaultvalue: string

Wizard
====

Collection de formules à N arguments (TODO)




CONTRAINTES
===========

remettre les contraintes DEF
---
  * insérer les PU par des insert or update
  * ajouter des updates available=false plutot que des suppression

 1.les requetes

INSERT INTO aa_def_process_unit(CODE, LABEL, AVAILABLE) VALUES
('pu_au_localisation', 'localisation', 1),
('pu_fu_erosion', 'erosion', 1),
ON DUPLICATE KEY UPDATE label=VALUES(label), available=VALUES(available);

2. les tables à modifier
available sur def_process_unit_parameter dev_event def_event_data



remettre les contraintes CP
---
- Ajouter une suppression des inspectionsSession
- Ajouter une suppression "HARD" des modeles
- mettre des removed sur les modèles "draft"

1.contraintes
ALTER TABLE CP_EVENT ADD CONSTRAINT FK_CP_EVENT_ELEMENT_EVENT_ID FOREIGN KEY (ELEMENT_EVENT_ID) REFERENCES PI_ELEMENT_EVENT (ID);
2. colonne removed sur
pi_element pi_process_unit pi_custom_event_property




TODO gestion des dossiers
---
Archiver les dossier inutiles batchs et modeles:
- Ajouter une suppression des dossiers qui ne sont pas associés