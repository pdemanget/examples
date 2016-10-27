STYLE
=====
taille
--
shorthand notation for -fx-region-border at this time.

    -fx-min-height, -fx-pref-height, -fx-max-height
    -fx-min-width, -fx-pref-width, -fx-max-width

positionnement - marges
---
	-fx-border-width: 0 1 0 0;
	-fx-border-insets:*
	-fx-padding:20;


	-fx-spacing //HBox, VBox
	-fx-hgap // GridPane FlowPane
	-fx-vgap


	-fx-opacity
	-fx-rotate
	-fx-scale-x
	-fx-scale-y
	-fx-scale-z
	-fx-translate-x
	-fx-translate-y
	-fx-translate-z
	-fx-visibility

	-fx-indent; //TreeCell

		<GridPane.margin>
	        <Insets bottom="10.0" left="60.0" right="0.0" top="10.0"/>
	    </GridPane.margin>

Couleurs
--
Sur les "shape" == Text, Rectangle

    -fx-fill: #F00;

Sur les "Control" == Label, TextField

    -fx-text-fill: #F00;


Bordure
--

	-fx-border-width: 1;
	-fx-border-insets:*;
	-fx-border-color: #F00;

ou

    -fx-stroke: green; //Shape
    -fx-stroke-width: 5;
    -fx-stroke-dash-array: 12 2 4 2;
    -fx-stroke-dash-offset: 6;
    -fx-stroke-line-cap: butt;

Fontes
--
-fx-font, -fx-font-family, -fx-font-size, -fx-font-weight, -fx-font-style

    -fx-font-family: "Segoe UI Semibold";
	 -fx-font-size: 1em;
    -fx-font-weight: bold;
    -fx-text-fill:  #F00;
    -fx-opacity: 0.6;

background
---
-fx-background-color
-fx-background-insets
-fx-background-radius
-fx-background_image
-fx-background-position
-fx-background-repeat
-fx-background-size

Autres
---
-fx-cursor
-fx-text-alignment

Fonctionnement
=====
Fxml, CSS, Java equivalent
--
l'attribut CSS utilise le setter "spacing" devient "-fx-spacing".

    <HBox spacing="10"
or

    -fx-spacing:10px
or

    JavaFX attribute: HBox.spacingProperty() HBox.getSpacing() HBox.setSpacing(String)



 VBox

    -fx-spacing:2em; -fx-alignment:center;

GridPane
--------



Examples
========
2 type of text component - 2 styles (Shape and Control)
-----

    Text{
    	-fx-font-size: 16.0px;
        -fx-font-weight: bold;
        -fx-fill: #F00;
    }

    .Label{
    	-fx-text-fill:

    	-fx-content-display:right;
    	-fx-background-color: #FFF;
    	-fx-background-image: url('/style/arrows.svg'); /* une image en background */
    	-fx-background-size: 30px;
    	-fx-background-repeat: no-repeat;

    	-fx-graphic: url('/style/arrows.svg'); /* une image incluse */
    }
    box model
    	-fx-background-color: -linea-title-discret-header;
    	-fx-padding:20;
    	-fx-padding:5px;
    	-fx-spacing:10px;
    	-fx-border-color:#FFF;
    	-fx-border-width:2px;
    	-fx-border-insets: 5px;

Default working fonts
---
Segoe UI
Verdana


elements hierarchy
------------------
- Node
 - Shape
   - Text
 - Parent (children:Node)
  -Region
   - Pane (Layouts)
   - Control
	 -Label
	 -Toolbar (items:Node)



Détails
- Node
 - Shape
   - Text
 - Parent (children:Node)
  -Region
   - Pane (Layouts)
	- HBox
   - Control
	 -Label
	 -Toolbar (items:Node)



Styles CSS
----------
Un text et un label utilise des attributes CS différents (sic)
Text

     .value {
     	-fx-font-size: 16.0px;
         -fx-font-weight: bold;
         -fx-fill:-mts-value;
     }

Label

	-fx-text-fill:
	-fx-content-display:right;
	-fx-background-color: #FFF;
	-fx-background-image: url('/style/arrows.svg'); /* une image en background */
	-fx-background-size: 30px;
	-fx-background-repeat: no-repeat;

	-fx-graphic: url('/style/arrows.svg'); /* une image incluse */

box model
	-fx-background-color: -linea-title-discret-header;


	-fx-border-color: #FFF;
	-fx-border-width: 0 1 0 0;
	-fx-border-insets:*

	-fx-padding:20;

Rectangle jaune avec bordure verte

    .my-rect {
        -fx-fill: yellow;
        -fx-stroke: green;
        -fx-stroke-width: 5;
        -fx-stroke-dash-array: 12 2 4 2;
        -fx-stroke-dash-offset: 6;
        -fx-stroke-line-cap: butt;
    }


Component selectors

    list-view


Astuces
=======
fight against the space
---
Pour retrouver la propriété qui réjoute trop d'espace, c'est la concaténation de:

	-fx-border-width: 0 1 0 0;
	-fx-border-insets:*
	-fx-padding:20;

+ le spécifique du layout: gridbag spacing or anchor position ...

Styles avancés
---
 - derive ladder function: pour faire varier une couleur de base.
 - derive=augmente la luminosité (la baisse avec des -)
 - ladder= fait un rotation de la couleur sur une roue arc en ciel

Retrouver le CSS d'origine
---
 - modena.css: C'est le style Java par défaut des composant, à utiliser pour débugger le CSS: chercher modena.css fourni dans les sources de javafx.jar: on peut copier les attributs pour tout écraser
 - on peut aussi supprimer modena.css et faire le sien à la place.

CSS3
--

Partie du CSS3 supportée: @Import et
@font-face {
        font-family: 'sample';
        font-style: normal;
        font-weight: normal;
        src: local('sample'), url('http://font.samples/resources/sample.ttf';) format('truetype');
    }

C'est tout.


Exemples:
--

.table-view {
  -fx-table-cell-border-color: transparent;
}


http://stackoverflow.com/questions/21704598/how-to-show-grid-lines-for-rows-in-treetableview

.tree-table-row-cell {
    -fx-background-color: -fx-table-cell-border-color, -fx-control-inner-background;
    -fx-background-insets: 0, 0 0 1 0;
    -fx-padding: 0.0em;
    -fx-text-fill: -fx-text-inner-color;
}
.tree-table-row-cell:selected {
    -fx-background-color: -fx-focus-color, -fx-cell-focus-inner-border, -fx-focus-color;
    -fx-background-insets: 0, 1, 2;
}

.tree-table-row-cell:odd {
    -fx-background-color: -fx-table-cell-border-color, derive(-fx-control-inner-background,-5%);
    -fx-background-insets: 0, 0 0 1 0;
}

.tree-table-row-cell:selected:odd {
    -fx-background-color: -fx-focus-color, -fx-cell-focus-inner-border, -fx-focus-color;
    -fx-background-insets: 0, 1, 2;
}