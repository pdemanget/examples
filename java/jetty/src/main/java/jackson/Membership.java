package jackson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Membership {
  private String requestId;

  private List<List<String>> members;

  public String getRequestId () {
    return requestId;
  }

  public void setRequestId (String requestId) {
    this.requestId = requestId;
  }

  public List<List<String>> getMembers () {
    return members;
  }

  public void setMembers (List<List<String>> members) {
    this.members = members;
  }

  // Here is what I tried, This is my main method.

  public static void main (String args[]) throws  JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper ();

    // JSON from file to Object
    Membership user = mapper.readValue ("{      \"requestId\": 1,      \"members\": [          [\"Jason\"],          [\"Mike\"],          [\"Andy\"]      ]  }", Membership.class);
    System.out.println (user.getMembers ().get (0).get(0));

  }
}