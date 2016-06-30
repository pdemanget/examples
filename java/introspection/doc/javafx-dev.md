Tutorial JavaFX
===


References
===
Layout principaux borderpane, hbox, vbox, TilePane, FlowPane, gridPane

Control pagination


Tutorial
=====

Suivre le tutorial https://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm

1. Structure (hello world)
2. Element (form)
3. Design (CSS)
4. FXML
5. SceneBuilder 

1. Hello world structure
https://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm

Application -> Stage (window) -> Scene (Main container) -> Parent (superClass container)
main->launch (methode static)
stage.show() : displays the windows

2. Elements form:
Components used:
GridPane -|> Parent

3. CSS
scene.getStylesheets().add(String) -> adds a css file

4. FXML
Le Parent utilisé vient maintenant de FXMLLoader, et on a toujours le main qui appelle launch.
4.1 main-> FXMLLoader
4.2 fichier .fxml contient les import la référence au Controller
4.3: le Controller est un Pojo avec des annotation @FXML
4.4: on a toujours le fichier .css, mais on ajoute la référence dans le fxml:
  <stylesheets>
    <URL value="@app.css" />
  </stylesheets>
  @ pour le m^me répertoire, mais est-ce une bonne pratique?
  
4.5 le binding
onaction=#-> pointe sur la méthode @FXML
fx:id= -> pointe sur la variable @FXML du controller




    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
    
        Scene scene = new Scene(root, 300, 275);
    
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

	4.6 le double binding
	  @FXML
    protected void initialize(){
	
	}
En 3 secondes:
Syntaxe du FXML: binding et style
fx:id="Button" styleClass="defaultStyle"

Plus:
I18N

on charge le bundle: fxmlLoader.setResources(ResourceBundle.getBundle("bundles.MyBundle", locale));
puis FxmlLoader.load(getClass().getResource("view/monitor.fxml"), bundle); 
on utilise %code.aaa dans les libellés    

Architecture du projet

Faire son composant.

Personnaliser l'affichage des listes
CellValueFactory (formattage de texte) et CellFactory

Filtrer, trier les listes
http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
on wrappe l'observable List dans une SortedList et une FilteredList
+ on bind le tri de la tableview sur l'attribut de la sotedlist
FilteredList<Person> filteredData = new FilteredList<>(masterData, p -> true)
SortedList<Person> sortedData = new SortedList<>(filteredData);
// 4. Bind the SortedList comparator to the TableView comparator.
sortedData.comparatorProperty().bind(personTable.comparatorProperty());
// 5. Add sorted (and filtered) data to the table.
personTable.setItems(sortedData)

Responsive <768 >=768 >=992 >=1200
extreme-small-device
small-device
medium-device
large-device
#toolbar:small-device {
http://www.guigarage.com/2014/11/responsive-design-javafx/

Avancé
------
utilisation de xtend
http://blog.efftinge.de/2012/11/active-annotations-explained-javafx.html


Inclusions
----------
On peut nommer l'inclusion et getter son ui + son controller

<fx:include fx:id="embeddedRedView" source="RedView.fxml" />
 	@FXML
	private Parent embeddedRedView; //embeddedElement
	@FXML
	private RedView embeddedRedViewController; // $embeddedElement+Controller

Expression - litterals
----------------------

On peut créer des composants non graphiques:
<fx:define>
<fx:copy>
<fx:constant>
<fx:define>
    <Double fx:id="xHeight" fx:value="100" />
</fx:define>
${xHeight * 2}

On peut uiliser des expressions pour faire référence à dautres éléments identifiés.
onAction="#handleButtonAction"/
Expression Binding
<TextField fx:id="textField"/>
${textField.text}
<FXCollections fx:factory="observableArrayList">
    <String fx:value="A"/>
    <String fx:value="B"/>
    <String fx:value="C"/>
</FXCollections>

fx:value
<fx:script>

ResourceBundle
<Label text="%myText"/>

        <Image url="@my_image.png"/>

Exemple variable + expressions
------------------------------
	<fx:define>
		<Double fx:id="xHeight" fx:value="278" />
	</fx:define>

	<ImageView fx:id="verso" fitWidth="${xHeight*2}" fitHeight="$xHeight"
		GridPane.columnIndex="0" GridPane.rowIndex="0">
Layouts
-------

AnchorPane
----------
<BorderPane xmlns="http://javafx.com/javafx/8.0.51" xmlns:fx="http://javafx.com/fxml/1"
	fx:controller="fr.incore_systemes.lineavision.recettage.view.RecettageController" styleClass="application">
	<top>

		<AnchorPane styleClass="topbar" >
			<HBox AnchorPane.leftAnchor="0.0" alignment="BASELINE_RIGHT"> 
				<Label text="%title" styleClass="titlelogo">
					<graphic>
						<ImageView>
							<image>
								<Image url="@/style/logo.png" />
							</image>
						</ImageView>
					</graphic>
				</Label>
				<Label fx:id="version" styleClass="homeVersion" HBox.margin="25,25,25,25"/>
			</HBox>
			
			
			

TIPS
====
prefWidth="${35*u.em}" prefHeight="${25*u.em}"

			
			
Fonts
=====

Polices rondes
'Varela Round', 'Helvetica Neue', Helvetica, Roboto, Arial, sans-serif


Java 8
======
2 ou 3 tips pour maitriser Java8
Les lambda et les functional interface

Les method references. sur le static et sur les méthodes d'instance (et l'instance passée en paramètre) aussi basé sur les functional interface

Le type inféré par le passage par le type attendu, la "target" (en Java <=7 le type était inféré par les paramètres passés = la surcharge, mais le type attendu était hors analyse))
La grosse différence pour moi c'est qu'avant les types étaient tous explicites, (avec un timide passage par le diamond operator en java 7): on peut écrire une instruction seule ""new ArrayList<String>(); "" ça compile (c'est idiot mais ça compile) 
exemple avec Consumer Function Supplier
Consumer<Controller> lm = Controller::initialize; compile mais
 SelectionController::initialize; ne compile pas: il faut un type inféré pour que la référence de fonction soit inclue dans une instance d'interface.

    Function<TemplateFxBean,SimpleStringProperty> ll = TemplateFxBean::getLabel;
    Consumer<String> ln=System.out::println;
    Consumer<SelectionController> l0 = SelectionController::initialize;   
    BiConsumer<SelectionController,String> l1 = SelectionController::one;

 
xtend
=====
les équivalents en xtend ont encore plus d'inférence de type.

les + - en JFX
==============

les -
- Le CSS
 - les attributs CSS ne sont pas homogenes: -fx-text-fill sur Label mais -fx-fill sur Text
 - pas de sélecteur sur le nom du composant  + les noms de classes ne sont pas homogenes (pas de classes sur certains composants)

 tips
 ===
  navigation dans le code
 Java Objets UI
 --------------
 component.lookup() et getParent()
 

 

Ceylon
======

Transition
=====
SequentialTransition.play,  TimeLine FadeTransition
setOnFinished(

Les Shape
-fx-fill	<paint>	BLACK	 
-fx-smooth	<boolean>	true	 
-fx-stroke


Keyword composants
======
Flash message
dropdown

Old: combo, select, field, modal

Style transparent
http://stackoverflow.com/questions/25534204/how-do-i-create-a-javafx-transparent-stage-with-shadows-on-only-the-border
Drag
http://stackoverflow.com/questions/34547586/java-fx-rubberband-resize-bug


========
Nested controllers
@FXML private DialogController dialog1Controller;
@FXML private Window dialog2;
@FXML private DialogController dialog2Controller;
But studying the changes introduced in version 2.2 I found that everything can be easily solved by using <fx:root> tag (like this tutorial). I entered my component in FXML simply declaring it like this:

<HBox>
    <Dialog id="dialog1" text="Hello World!"/>
    <Dialog id="dialog2" text="Hello World!"/>
</HBox>

sandbox
=======
//    Tooltip tooltip = new Tooltip("TT");
//    Tooltip.install(chronoCanvas,tooltip);

    final ContextMenu contextMenu = new ContextMenu();
    contextMenu.setOnShowing(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent e) {
            System.out.println("showing");
        }
    });
    contextMenu.setOnShown(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent e) {
            System.out.println("shown");
        }
    });

    MenuItem item1 = new MenuItem("About");
    item1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.out.println("About");
        }
    });
    MenuItem item2 = new MenuItem("Preferences");
    item2.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.out.println("Preferences");
        }
    });
    contextMenu.getItems().addAll(item1, item2);
    chronoCanvas.setOnContextMenuRequested(e->{
      contextMenu.show(chronoCanvas.getScene().getWindow());
    });
    
	
Bind extreme
============

Property converter:

    StringConverter<Number> converter = new NumberStringConverter();
	
	
TODO wrap a bindable getter/setter in a property.


Components
==========

Les annotations
---

@NamedArg
@DefaultProperty


+/- (objectifs?) 
=====
Les plus
---
- Pattern Vue FXML + controller Java + CSS OK
- FXML: un template déclaratif, avec un plugin qui signale les erreurs de binding.
- la couche "modèle"
basés sur des property avec des bind et des listeners, pratique pour binder des champs de formulaires, et pour de la prog asynchrone par exemple.


Les moins
---
CSS: out of date, inflexible, pas homogène, incompatible CSS3 et pas débuggable à chaud. Une perte de temps énorme sur la mise en forme graphique.
Lent: tout est dans le Thread graphique, ça n'avance pas.
Des composants pas homogène: 
- ComboBox:   
  Bug: Impossible de sélectionner "null" (fix en Java 9) https://bugs.openjdk.java.net/browse/JDK-8134923
  selectedItem est un  readOnlyProperty  : http://stackoverflow.com/questions/26909488/javafx-binding-combobox-itemsproperty
  le doubleBinding se fait sur valueProperty: pas super homogène avec le selectedItem
  la fenêtre dépasse: https://javafx-jira.kenai.com/browse/RT-40302
- TreeTableView n'utilise pas des colonnes observables comme le TableView
Des API pas complètes:
Le TreeItem est un objet plutôt qu'une interface, le modèle TreeModel de Swing était mieux. TreeItem devrais être un property, pas contenir un property.

- Des bugs d'affichages (comboBox pas déselectionnable)
Bug suppression TreeTableView https://bugs.openjdk.java.net/browse/JDK-8090177


Lib utilisées
=====
org.testfx:testfx-junit:4.0.
de.codecentric.centerdevice:javafxsvg
com.aircrack:afterburner.fx
org.aerofx
org.controlsfx:controlsfx:8.40.10
com.github.sommeri:less4j:1.17.2

Junit
=====

Pas grand chose pour du junit, juste des solutions custom:

http://stackoverflow.com/questions/18429422/basic-junit-test-for-javafx-8


la lib retenue: 


https://github.com/TestFX/TestFX
utilisée par javafxsvg

Bug-List
=======
Des bugs à résoudre en Java9...

 - Support des CSS attribute selector https://bugs.openjdk.java.net/browse/JDK-8090340
 - TableTreeView Régression entre 1.8.0_77 et 1.8.0_92 https://bugs.openjdk.java.net/browse/JDK-8156049
 
