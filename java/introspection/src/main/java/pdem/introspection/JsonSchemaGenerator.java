package pdem.introspection;

import java.lang.reflect.Field;
import java.util.List;

import fr.incore_systemes.si.mts.model.dto.AlarmMessage;
import fr.incore_systemes.si.mts.model.dto.MonitorInitMessage;

/**
 * Introspection for schema generator
 */
@SuppressWarnings ("rawtypes")
public class JsonSchemaGenerator {

  // result of the properties:
  public String process(Class clazz, int indent) {
    StringBuilder result = new StringBuilder();

    List<Field> fields = IntrospectorGadget.getInheritedFields(clazz);
    String separator = "";
    String sIndent = "\n";
    for (int i = 0; i < indent; i++) {
      sIndent += "  ";
    }
    String sIndent1=sIndent+"  ";
    for (Field field : fields) {
      if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
        result.append(sIndent);
        result.append(separator);
        result.append('"');
        result.append(field.getName());

        result.append("':{");
        result.append(sIndent1);

        if (field.getType().isEnum()) {
          result.append("'type':'string','enum':[");
          //TODO values
          result.append("]}\n");

        }
        else if (field.getType().isPrimitive() || field.getType().isAssignableFrom(Number.class)) {
          result.append("'type':'number'");
          result.append(sIndent);
          result.append("}");
        }
        else if (field.getType() == String.class) {
          result.append("'type':'string'");
          result.append(sIndent);
          result.append("}");
        }
        else if (field.getType().isArray() ||field.getType().isAssignableFrom(Iterable.class) ) {
          result.append("'type':'array',");
          result.append(sIndent1);
          result.append("'items':{");
          //TODO extract loopObjects and getSimpleType in 2 methods to avoid double identation mgt
          //TODO finish this is horribly wrong
          //result.append(process(field.get))

          result.append("}");
        }
        else {
          result.append("'type':'object',\n");
          result.append(sIndent1);
          result.append("'properties':{");
          result.append(process(field.getType(), indent + 2));
          result.append(sIndent1);
          result.append("}");
          result.append(sIndent);
          result.append("}");

        }
        separator = ",";

      }

    }
    return result.toString().replace('\'', '"');

  }

  public String procssFile(Class clazz) {
    return "{\n  '$schema': 'http://json-sc hema.org/draft-04/schema#',"
        + "\n  'type': 'object',"
        +"\n  'properties': {"
        .replace('\'', '"')
        + process(clazz, 2) + "\n  }\n}\n";
  }

  public void run(){
    String json = procssFile(AlarmMessage.class);
    System.out.println(json);
  }

  public static void main(String[] args) {
    new JsonSchemaGenerator().run();
  }

}
