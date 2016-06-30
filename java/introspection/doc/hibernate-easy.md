Hibernate
=========


Simplifier le binding ORM
===
Pourquoi simplifier? Je trouvais cela stupide d'être obligé de redonner le nom de colonne sur chaque bind de colonne, alors qu'on donne en général (presque) le même nom, en convertissant CamelCase en snake_case.
 (Presque le même nom parce qu'on a un préfixe en nom de table, je vais trouver un moyen de le gérer.)
 
De plus en maintenance, si on change le nom de colonne, il faut changer aussi le mapping (+ les getters setters, mais ceci est une autre histoire, voir Xtend ou Lombok)

avant:

	package your.application.model

	@Entity
	@Table(name="prefix_person")
	public class Person{
	
	  @Column(name="first_name")
	  private String firstName;
	  
	  @ManyToOne
	  @JoinColumn(name = "COMPANY_ID", foreignKey = @ForeignKey(name = "FK_PERSON_COMPANY"))
	  private Company company
	  
	  @OneToMany (mappedBy = "person")
	  private List<Adress> adresses;

après:
	
	package your.application.model.prefix
	
	@Entity public class Person{
	
	  @Column
	  private String firstName;
	  
	  @ManyToOne
	  private Company company
	  
	  @OneToMany (mappedBy = "person")
	  private List<Adress> adresses;   


J'utilise un bout d'algo pour convertir CamelCase -> snake_case, et j'ai ajouté le dernier nom de package pour gérer les préfixes en base, ce qui est courant (inévitable) dans les normes de base de données, afin d'éviter que les classes ne s'apellent PrefixQuelqueChose. 

Note sur le CamelCase, pour que ça fonctionne, les acronymes doivent uniquement commencer par une majuscule, exemple DvdPlayer. C'est nécessaire pour insérer le _ au bon endroit -> dvd_player. cf discussion ici :http://stackoverflow.com/questions/2236807/java-naming-convention-with-acronyms/2236843#comment62658151_2236843

Il ne reste plus qu'à coder des classes Hibernate qui vont faire ces conversions, voir dans le paragraphe ci-dessous:


Générer automatiquement les noms de colonnes de table, de contraintes
===
    
La physical naming strategy, de laquelle je suis parti (voir exemple stackoverflow) Pour nommer les Tables et colonnes en Camel-Case:    
    
    /*
     * http://stackoverflow.com/questions/32437202/improvednamingstrategy-no-longer-working-in-hibernate-5
     */
    @SuppressWarnings ("serial")
    public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl implements Serializable {
    
      public static final PhysicalNamingStrategyImpl INSTANCE = new PhysicalNamingStrategyImpl();
    
      @Override
      public Identifier toPhysicalTableName (Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
      }
    
      @Override
      public Identifier toPhysicalColumnName (Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
      }
    
      public static String addUnderscores (String name) {
        //"prefer the JPA entity name, if specified"
        if (isJpaName(name)){
          return name.toUpperCase();
        }
        return toSnakeCase(name.replace(".", "")).toUpperCase();
      }

      private static boolean isJpaName (String name) {
        return name.contains("_") && name.equals(name.toUpperCase());
      }
    
      public static String toSnakeCase (String name) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
          if (Character.isUpperCase(name.charAt(i))) {
            if (i > 0) {
              buffer.append('_');
            }
            buffer.append(Character.toLowerCase(name.charAt(i)));
          }
          else {
            buffer.append(name.charAt(i));
          }
        }
        return buffer.toString();
      }
    }
    
 Note: Vous pouvez redéfinir au besoin la méthode `addUnderscores`, notez le petit "hack" `isJpaName()` si vous voulez définir votre nom manuellement, ça permet d'avoir des annotation de type `@column(name="NOM_TORDU") private String leVraiNom;`

Et en plus la naming Strategy, qui nous permet de nommer la foreign key et d'avoir accès au nom de package (au besoin) :

    /**
     * ImplicitNamingStrategyComponentPathImpl
     * 
     * @author pdemanget
     * @version 19 avr. 2016
     */
    @SuppressWarnings ("serial")
    public class ForeignKeyNamingStrategy extends ImplicitNamingStrategyComponentPathImpl{
    
      @Override
      public Identifier determineForeignKeyName (ImplicitForeignKeyNameSource source) {
        //System.out.println("SOURCE ====================== "+source);
        String value= "FK_"+source.getTableName().getText()+"_"+source.getColumnNames().stream().map(elt->elt.getText()).collect(Collectors.joining("_"));
        return Identifier.toIdentifier(value.toUpperCase());
      }
      
      @Override
      protected String transformEntityName(EntityNaming entityNaming) {RV
        String name = entityNaming.getEntityName();
        return name.substring(name.lastIndexOf('.', name.lastIndexOf('.')-1)+1);
      }
    }
    
    
Et enfin brancher toutes ces joyeuses strategy sur hibernate soit dans le persistence.xml, soit en Java, mon init est en Java :
 
     persistenceProperties.setProperty("hibernate.physical_naming_strategy", PhysicalNamingStrategyImpl.class.getName());
     persistenceProperties.setProperty("hibernate.implicit_naming_strategy", ForeignKeyNamingStrategy.class.getName());



     
 Et voilà!
 
 
Bonus: Génération SQL
===
J'ai décidé d'utiliser la génération SQL comme script de génération de base, même pour la prod malgré le fait que ce soit fortement déconseillé, je pourrai toujours rechanger après au besoin.
Je fais en sorte que le fichier de sortie soit lisible, si je change d'avis et que je le reprends à la main, je formatte le SQL. Le script est complet: on génère aussi les FK, et il est lançable en script: j'utilise flyway.


Voila l'algo:
 - On lance l'entityManager avec les options hibernate "drop-and-create" qui vont générer le fichier. méthode `generateSQL`
 - On ajoute les ; manquants en fin de ligne pour que ce soit lançable par flyway. méthode `readFileAppendLine`
 - On appelle un programme externe de PoorSQL "SQLFormatter.exe" qui fait un formattage "pretty" du SQL instruction `exec("SqlFormatter" ...)`
 - On copie le fichier dans flyway/v1/v1__createTables.SQL : `Files.copy(`
 
 Il faut donc installer SQLFormatter (GPL) dans la path avant lancement : cf.https://plus.google.com/+PhilippeDemanget/posts/bysBVvLwtjA
  cf. http://architectshack.com/PoorMansTSqlFormatter.ashx
  ou le lien direct http://architectshack.com/GetFile.aspx?Page=PoorMansTSqlFormatter&File=SqlFormatter.1.5.1.zip
 
 
 Ce qui est généré est propre et les tables sont triées par ordre alphabétique, le delta est donc facile à faire:
 
    CREATE TABLE prefix_person (
	   id BIGINT NOT NULL auto_increment
	   ,first_name VARCHAR(255)
	   ,company_id BIGINT
	   );
	   
	 ALTER TABLE prefix_person ADD CONSTRAINT FK_prefix_person_company_id FOREIGN KEY (company_id) REFERENCES company (id);

Voica la classe de génération:

    public class JpaSchemaExport {
    
      public static void readFileAppendLine (String file) {
        try {
          List<String> lines = Files.readAllLines(Paths.get(file));
          for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i) + ";");
          }
          Files.write(Paths.get(file), lines);
        } catch (IOException e) {
          e.printStackTrace();
        }
    
      }
    
      public static void main (String[] args) throws IOException {
        String create = "target/createTables.sql";
        String drop = "target/drop.sql";
        try {
          Files.delete(Paths.get(create));
          Files.delete(Paths.get(drop));
        } catch (Exception e) {
    
        }
        generateSQL("schema", create, drop);
        readFileAppendLine(create);
        readFileAppendLine(drop);
        
        String command = "SQLFormatter ";
        exec(command, create);
        
        String dest = "flyway/V1/V1__createTables.sql";
        Files.copy(Paths.get(create), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        
        exec(command, dest);
        System.exit(0);
      }
    
      private static void exec (String... commands) throws IOException {
        System.out.println("Execute >" + String.join(",", commands));
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        pb.start();
    
        System.out.println("Executed");
      }
    
      public static void generateSQL (String persistenceUnitName, String create, String drop) {
        System.out.println("Generating DDL create script to : " + create);
    
        final Properties persistenceProperties = new Properties();
    
        // XXX force persistence properties : remove database target
        persistenceProperties.setProperty(org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "");
        persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_DATABASE_ACTION, "none");
    
        // XXX force persistence properties : define create script target from metadata to destination
        // persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SCHEMAS, "true");
        persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_ACTION, "drop-and-create");
        persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SOURCE, "metadata");
        persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_CREATE_TARGET, create);
        persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_DROP_TARGET, drop);
    
        persistenceProperties.setProperty(AvailableSettings.JDBC_DRIVER, Driver.class.getName());
        persistenceProperties.setProperty(AvailableSettings.JDBC_URL,
            "jdbc:mysql://localhost/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        persistenceProperties.setProperty(AvailableSettings.JDBC_USER, "root");
        persistenceProperties.setProperty(AvailableSettings.JDBC_PASSWORD, "***");
    
        persistenceProperties.setProperty("hibernate.dialect", MySQLDialect.class.getName());
        persistenceProperties.setProperty("hibernate.physical_naming_strategy", PhysicalNamingStrategyImpl.class.getName());
        persistenceProperties.setProperty("hibernate.implicit_naming_strategy", ForeignKeyNamingStrategy.class.getName());
    
    
        Persistence.createEntityManagerFactory(persistenceUnitName, persistenceProperties);
    
      }
    
    }
