package pdem.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import fr.incore_systemes.si.mts.model.dto.AlarmMessage;

/**
 * Introspection for schema generator
 */
@SuppressWarnings ("rawtypes")
public class JsonSchemaGenerator {

  private String indent(int n){
    StringBuilder result = new StringBuilder();
    result.append("\n");
    for(int i=0;i<n;i++){
      result.append("  ");
    }
    return result.toString();
  }

  /**
   * Adds the c properties: {type:"theType"...} to the Object type and recurse.
   */
  public String processObject(Class clazz, int indent) {
    StringBuilder result = new StringBuilder();

    List<Field> fields = IntrospectorGadget.getInheritedFields(clazz);
    String separator = "";
    String sIndent = indent(indent);
    for (Field field : fields) {
      if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
        result.append(sIndent);
        result.append(separator);
        result.append('"');
        result.append(field.getName());
        result.append("':");
        processType(indent, result, field);
        separator = ",";

      }

    }
    return result.toString().replace('\'', '"');

  }



  /**
   * Adds {type:"theType"...} to the builder
   */
  private void processType(int indent, StringBuilder result, Field field) {
    Class<?> clazz = field.getType();
    Type type = field.getGenericType();

    processType(indent, result, clazz,type );
  }

  private void processType(int indent, StringBuilder result, Class clazz, Type type) {
    String sIndent=indent(indent);
    String sIndent1= indent(indent+1);

    result.append("{");
    result.append(sIndent1);

    if (clazz.isEnum()) {
      result.append("'type':'string','enum':[");
      result.append(joinEnum(clazz.getEnumConstants(),sIndent1));
      result.append("]}\n");

    }
    else if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz)) {
      result.append("'type':'number'");
      result.append(sIndent);
      result.append("}");
    }
    else if (Date.class.isAssignableFrom(clazz)) {
      result.append("'type':'string'");
      result.append(sIndent1);
      result.append(",'format':'date-time'");
      result.append(sIndent);
      result.append("}");
    }
    else if (clazz == String.class) {
      result.append("'type':'string'");
      result.append(sIndent);
      result.append("}");
    }
    else if (clazz.isArray() ||Iterable.class.isAssignableFrom(clazz) ) {
      result.append("'type':'array',");
      result.append(sIndent1);
      result.append("'items':");

      processArray(indent+1, result, type);
      result.append(sIndent);
      result.append("}");
    }
    else {
      result.append("'type':'object',\n");
      result.append(sIndent1);
      result.append("'properties':{");
      result.append(processObject(clazz, indent + 2));
      result.append(sIndent1);
      result.append("}");
      result.append(sIndent);
      result.append("}");

    }
  }

  private String joinEnum(Object[] enumConstants, String indent) {
    StringBuilder res = new StringBuilder();
    String sepMutable=indent;
    final String sep=indent+",";
    for (int i=0;i<enumConstants.length;i++){
      res.append(sepMutable);
      res.append('"');
      res.append(enumConstants[i]);
      res.append('"');
      sepMutable = sep;
    }
    return res.toString();
  }

  private void processArray(int i, StringBuilder result, Type type) {
    ParameterizedType pType = (ParameterizedType) type;

    Class genericClass = IntrospectorGadget.getGenericParameterType(pType,0);
    processType(i,result, genericClass,genericClass);

  }

  public String procssFile(Class clazz) {
    return ("{\n  '$schema': 'http://json-schema.org/draft-04/schema#',"
        + "\n  'type': 'object',"
        +"\n  'properties': {")
        .replace('\'', '"')
        + processObject(clazz, 2) + "\n  }\n}\n";
  }

  public void run(){
    String json = procssFile(AlarmMessage.class);
    System.out.println(json);
  }

  public static void main(String[] args) {
    new JsonSchemaGenerator().run();
  }

}
