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
  @ pour le même répertoire, mais est-ce une bonne pratique?
  @/ pour la racine

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


Position
--------
Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
or the coordinates relative to the screen:

Bounds boundsInScreen = node.localToScreen(node.getBoundsInLocal());


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

Comparaison HTAML
-----------------

placeholder-> promptText



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
une meilleure architecture
 - Pattern Vue FXML + controller Java + CSS OK
 - FXML: un template déclaratif, avec un plugin qui signale les erreurs de binding.
 - la couche "modèle"
basés sur des property avec des bind et des listeners, pratique pour binder des champs de formulaires, et pour de la prog asynchrone par exemple.

Des améliorations par rapport à Swing:
 - un WebView vraiment utilisable, utilisant Webkit
 - Des effets de transitions sur les fenêtres


Les moins
---
CSS: out of date, inflexible, pas homogène, incompatible CSS3 et pas débuggable à chaud. Une perte de temps énorme sur la mise en forme graphique.
Lent: tout est dans le Thread graphique, ça n'avance pas.
- les attributs CSS ne sont pas homogenes: -fx-text-fill sur Label mais -fx-fill sur Text
 - les noms de classes ne sont pas homogenes par rapport aux types (pas de classes sur certains composants)

Des composants pas homogène:
- ComboBox:
- le doubleBinding se fait sur valueProperty: pas super homogène avec le selectedItem
- TreeTableView n'utilise pas des colonnes observables comme le TableView

Des API pas complètes:
Le TreeItem est un objet plutôt qu'une interface, le modèle TreeModel de Swing était mieux. TreeItem devrais être un property, pas contenir un property.

Des bugs d'affichages:

Des régressions par rapport à swing
 - pas d'openGL intégré



les bugList impressionante: cf §	bug-list

 tips
 ===
  navigation dans le code
 Java Objets UI
 --------------
 component.lookup() et getParent()



Lib utilisées
=====
org.testfx:testfx-junit:4.0.
de.codecentric.centerdevice:javafxsvg
com.aircrack:afterburner.fx
org.aerofx
org.controlsfx:controlsfx:8.40.10
com.github.sommeri:less4j:1.17.

NB: nécessité d'utiliser au minimum:
 - une injection
 - un mécanisme custom de retaillage
 - controlsfx pour des ui plus avancée
 - fluentAPI pour les promesses avancées


Pres
===

http://www.slideshare.net/alexandercasall/javafx-pitfalls

voir l'utilisation de fluentAPI

Junit
=====

Pas grand chose pour du junit, juste des solutions custom:

http://stackoverflow.com/questions/18429422/basic-junit-test-for-javafx-8


la lib retenue:


https://github.com/TestFX/TestFX
utilisée par javafxsvg

Bug-List
=======


Des bugs constatés
 - Bugs combobox:
   comboBox pas déselectionnable
   Bug: Impossible de sélectionner "null" (fix en Java 9) https://bugs.openjdk.java.net/browse/JDK-8134923
   selectedItem est un  readOnlyProperty  : http://stackoverflow.com/questions/26909488/javafx-binding-combobox-itemsproperty
   la fenêtre dépasse: https://javafx-jira.kenai.com/browse/RT-40302
 - Bug suppression TreeTableView https://bugs.openjdk.java.net/browse/JDK-8090177
 - javaFX FXMLLoader sur des projets différents: les chemins relatifs ne fonctionnent pas.
 - Sur un popin_popover, si on eleve 2 fois le même node des children, ça ^plante, le contains n'est pas à jour non plus, obligé de wrapper pour voir s'il est déjà enlevé.

Des non-bug mais API inconsistante-insuffisante
 - comboBox.setEditable(false) ne sert pas à rendre comboBox non editabel (contrairement à TextField.setEditable): devrais s'appeler allowUnlistedValue
 _ controlsfx.validation est très inssuffisantes: ne gère pas les tableview editable, ne gère pas les flag dirty



Des bugs à résoudre en Java9...

 - Support des CSS attribute selector https://bugs.openjdk.java.net/browse/JDK-8090340
 - TableTreeView Régression entre 1.8.0_77 et 1.8.0_92 https://bugs.openjdk.java.net/browse/JDK-8156049 http://stackoverflow.com/questions/36917220/javafx-treetableview-leaves-icons-behind-when-collapsing

 Des NPE en rafales
 https://bugs.openjdk.java.net/browse/JDK-8109406
https://bugs.openjdk.java.net/browse/JDK-8120279

Soumettre, chercher:

http://bugreport.java.com/
http://bugs.java.com/bugdatabase/
https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/bugreports.html
http://openjdk.java.net/contribute/

http://mail.openjdk.java.net/mailman/listinfo/openjfx-dev
 => openjfx-dev@openjdk.java.net
(par kleopatra/stackoverflow)


Links
=====

https://wiki.openjdk.java.net/display/OpenJFX/Performance+Tips+and+Tricks


SPRING
====

http://www.oracle.com/technetwork/articles/java/zonski-1508195.html


http://javaetmoi.com/2012/02/core-spring-3-0-certification-mock-exam/
un fichier au format PDF, les réponses sont en fin de document :
spring-certification-3-mock-exam-antoine
une version en ligne de l’examen publiée sur le site Skill Guru :
http://www.skill-guru.com/test/258/core-spring-3.0-certification-mock-exam
Descriptif de l’examen blanc

bootstrap
---
public void startSpring () {
    try {
      AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
      ctx.register(AppConfig.class);
      ctx.refresh();
      AutowireCapableBeanFactory autowireBeanFactory = ctx.getAutowireCapableBeanFactory();
      autowireBeanFactory.autowireBean(this);
//              RecettageManager runner = (RecettageManager) ctx.getBean("mainRunner");
//              runner.run();
//STOP/!\ pour le destroy      ctx.close();
    } catch (BeansException|IllegalStateException e) {
      logger.error("Application constructor error",e);
      throw e;
    }

  }

Injection des controleurs
---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TopLevel.fxml"));
            loader.setControllerFactory(cls -> context.getBean(cls));



Config
---

/**
 * Configuration Spring de l'application.
 *
 * @author pdemanget
 * @version 1.0.0, 9 sept. 2016
 */
@Configuration
@ComponentScan (basePackages = { "fr.incore_systemes.lineavision.recettage", "fr.incore_systemes.lineavision.common" })
@EnableTransactionManagement
public class AppConfig {

  @Bean
  UserService<?> userService() {
    return new UserService<DefRole>();
  }

  @Bean
  ConfigService<?> configService() throws JAXBException, IOException {
    String conf = RecettageConstants.PATH_SOFT_CONF;
    if (System.getProperty("conf") != null) {
      conf = System.getProperty("conf");
    }

    ConfigService<RecettageConfig> configService = new ConfigService<RecettageConfig>();
    RecettageConfig config = configService.readConfigurationFile("gui_configuration.xml", conf + "/gui_configuration.xml", RecettageConfig.class);
    if (System.getProperty("db.password") != null) {
      config.getDb().setPassword(System.getProperty("db.password"));
    }

    return configService;
  }

  private Properties setProperties(Properties persistenceProperties) {
    persistenceProperties.setProperty("hibernate.dialect", MySQLDialect.class.getName());
    persistenceProperties.setProperty("hibernate.physical_naming_strategy", PhysicalNamingStrategyImpl.class.getName());
    persistenceProperties.setProperty("hibernate.implicit_naming_strategy", ForeignKeyNamingStrategy.class.getName());
    persistenceProperties.setProperty("hibernate.current_session_context_class", "thread");

    persistenceProperties.setProperty("hibernate.generate_statistics", "true");
    persistenceProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
    persistenceProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
    persistenceProperties.setProperty("hibernate.cache.use_query_cache", "true");
    // setC3P0(persistenceProperties);
    return persistenceProperties;
  }

  @Bean
  DataSource dataSource(ConfigService<RecettageConfig> configService) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    DbConfig db = configService.getConfig().getDb();

    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUrl(db.getUrl());
    dataSource.setUser(db.getUser());
    dataSource.setPassword(db.getPassword());

    //    SimpleDriverDataSource dataSource = new SimpleDriverDataSource((Driver) Class.forName(db.getDriver()).newInstance(), db.getUrl(), db.getUser(),
    //        db.getPassword());

    return dataSource;
  }


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
    emfBean.setDataSource(dataSource);
    emfBean.setJpaProperties(setProperties(new Properties()));

    return emfBean;
  }
  //
  //
  //  PlatformTransactionManager transactionManagerDataSource(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
  //    return new JpaTransactionManager(entityManagerFactoryBean.getObject());
  //  }

  /**
   *
   * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-one-configuration/
   *
   * marche pas:
   * https://dzone.com/articles/transaction-configuration-jpa
   *
   * @param entityManagerFactoryBean
   * @return
   */
  @Bean
  PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

    EntityManager em = entityManagerFactory.createEntityManager();
    Query query = em.createNativeQuery("select 1");
    Object singleResult = query.getSingleResult();

    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

}


Injection
--
	@Component
	public class ErrorService ...

    @Inject
	 private ErrorService errorService;



Hibernate
=========
http://www.journaldev.com/2980/hibernate-ehcache-hibernate-second-level-cache
http://blog.jhades.org/setup-and-gotchas-of-the-hibernate-second-level-and-query-caches/
1. dépendance hibernate ehcache
2. ehcache.xml
3. properties hibernate activation
4. annotation / TYPE et requetes:

hibernate.cache.region.factory_class


Excel
=====
remettre impression à 100%: mise en page - échelle


STACKS
======
NPE sur drawTexture (Video RAM overflow)
java.lang.NullPointerException
       at com.sun.prism.d3d.D3DTexture.getContext(D3DTexture.java:84)
       at com.sun.prism.d3d.D3DTexture.update(D3DTexture.java:207)
       at com.sun.prism.d3d.D3DTexture.update(D3DTexture.java:151)
       at com.sun.prism.impl.BaseContext.flushMask(BaseContext.java:109)
       at com.sun.prism.impl.BaseContext.drawQuads(BaseContext.java:118)
       at com.sun.prism.impl.VertexBuffer.flush(VertexBuffer.java:98)
       at com.sun.prism.impl.BaseContext.flushVertexBuffer(BaseContext.java:101)
       at com.sun.prism.impl.ps.BaseShaderContext.checkState(BaseShaderContext.java:648)
       at com.sun.prism.impl.ps.BaseShaderContext.validateTextureOp(BaseShaderContext.java:583)
       at com.sun.prism.impl.ps.BaseShaderContext.validateTextureOp(BaseShaderContext.java:500)
       at com.sun.prism.impl.BaseGraphics.drawTexture(BaseGraphics.java:419)
       at com.sun.prism.impl.ps.BaseShaderGraphics.drawTexture(BaseShaderGraphics.java:139)
       at com.sun.javafx.sg.prism.NGImageView.renderContent(NGImageView.java:123)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGImageView.doRender(NGImageView.java:103)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.renderForClip(NGNode.java:2294)
       at com.sun.javafx.sg.prism.NGNode.renderRectClip(NGNode.java:2188)
       at com.sun.javafx.sg.prism.NGNode.renderClip(NGNode.java:2214)
       at com.sun.javafx.sg.prism.CacheFilter.impl_renderNodeToCache(CacheFilter.java:671)
       at com.sun.javafx.sg.prism.CacheFilter.render(CacheFilter.java:575)
       at com.sun.javafx.sg.prism.NGNode.renderCached(NGNode.java:2358)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2044)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2053)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.sg.prism.NGGroup.renderContent(NGGroup.java:235)
       at com.sun.javafx.sg.prism.NGRegion.renderContent(NGRegion.java:576)
       at com.sun.javafx.sg.prism.CacheFilter.impl_renderNodeToCache(CacheFilter.java:675)
       at com.sun.javafx.sg.prism.CacheFilter.render(CacheFilter.java:575)
       at com.sun.javafx.sg.prism.NGNode.renderCached(NGNode.java:2358)
       at com.sun.javafx.sg.prism.NGNode.doRender(NGNode.java:2044)
       at com.sun.javafx.sg.prism.NGNode.render(NGNode.java:1945)
       at com.sun.javafx.tk.quantum.ViewPainter.doPaint(ViewPainter.java:477)
       at com.sun.javafx.tk.quantum.ViewPainter.paintImpl(ViewPainter.java:330)
       at com.sun.javafx.tk.quantum.PresentingPainter.run(PresentingPainter.java:91)
       at java.util.concurrent.Executors$RunnableAdapter.call(Unknown Source)
       at java.util.concurrent.FutureTask.runAndReset(Unknown Source)
       at com.sun.javafx.tk.RenderJob.run(RenderJob.java:58)
       at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
       at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
       at com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:125)
       at java.lang.Thread.run(Unknown Source)

       com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (6 251 875 > 4 194 304). You can change this value on the server by setting the 'max_allowed_packet' variable.


BUGS
======

Transaction fail impossible à reprendre
----
si une transaction fail (dev due la reframe en copie, modif du code à chaud): Plus aucune transaction ne fonctionne, il faut fermer et rouvrir l'appliocation.

Examples
===
http://stackoverflow.com/questions/25837095/how-to-perform-a-combined-parallel-scaling-and-translation-animation-in-javafx-8

SQL
---
cascade delete : 238s pour 73+ 1_159+53_939+ 136_424 +579_546 lignes de données (inspection, document, verdict, event, event_data)

Sécurité
--
http://stackoverflow.com/questions/25906172/javafx-adding-access-role-support-in-fxml-without-subclassing-the-control
  //SecureRandom.getInstance()

Pool C3P0
---------
C3P0ConnectionProvider


Fonction interne
===
Fonctionnement interne d'un jdk, les outils, les lib en Java 8 (et inférieur jusqua ?)

ct.sym (contient un mini rt.jar)
cf.http://stackoverflow.com/questions/4065401/using-internal-sun-classes-with-javac
http://stackoverflow.com/questions/10314904/no-com-sun-tools-javac-in-jdk7
http://stackoverflow.com/questions/12019050/compile-a-java-file-with-a-java-program
https://github.com/trung/InMemoryJavaCompiler/tree/master/src/main/java/org/mdkt/compiler
http://stackoverflow.com/questions/12173294/compile-code-fully-in-memory-with-javax-tools-javacompiler


javac -> utilise le ct.sym

pour démonter les exe, les ouvrir en mode texte, en plus du binaire, ça contient la liste des jar, le main qui est lancé, et un XML de description:
jdb.exe: utilise sa-jdi.jar et tools.jar, ce dernier contient le main: com.sun.tools.example.debug.tty.TTY

javac.exe:tools.jar com.sun.tools.javac.Main

Pour les lancer sous eclipse, il faut inclure les jar dans le classpath( en + de la jre) et lancer le main trouvé dans l'exe

En ligne de commande, se placer dans le lib du jdk (pas la jre) et lancer
>java -cp tools.jar com.sun.tools.javac.Main
>java -cp tools.jar com.sun.tools.example.debug.tty.TTY

mieux avec JAVA_HOME qui pointe en racine du jdk:
java8 -cp %JAVA_HOME%/lib/tools.jar com.sun.tools.example.debug.tty.TTY

Enfin tout simplement pour le lancer depuis une de ses classes, après inclusion du rt.jar:
public class Javac {
  public static void main(String[] args) throws Exception {
    com.sun.tools.javac.Main.main(args);
  }
}

Si on tente de le lancer sans le tools.jar, ça ne va pas:
d:\>d:\opt\jre1.8.0_92\bin\java -jar javac.jar Test.java
Exception in thread "main" java.lang.NoClassDefFoundError: com/sun/tools/javac/Main
        at jdk.Javac.main(Javac.java:14)
Caused by: java.lang.ClassNotFoundException: com.sun.tools.javac.Main
        at java.net.URLClassLoader.findClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
        at java.lang.ClassLoader.loadClass(Unknown Source)
        ... 1 more



avec le tools.jar
java8 -cp %JAVA_HOME%/lib/tools.jar;./javac.jar jdk.Javac Test.java




Bidouiller le FXMLLoader
====
FXMLLoader:748

 private abstract class ValueElement extends Element {
        public String fx_id = null;

        @Override
        public void processStartElement() throws IOException {
            super.processStartElement();

            updateValue(constructValue());

            if (value instanceof Builder<?>) {
                processInstancePropertyAttributes();
            } else {
                processValue();
            }
        }

Cette ligne demande d'avoir un builder séparé pour les attributs.
            if (value instanceof Builder<?>) {
                processInstancePropertyAttributes();