package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.glassfish.grizzly.utils.Charsets;

/**
 * Root resource (exposed at "myresource" path)
 * http://localhost:8080/myapp/file/README.md
 * http://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 * 
 */
@Path("file")
public class FileRest {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     *
     * @return bytes
     * @throws IOException 
     */
    @GET()
    @Path("{path: .+}")
    public byte[] get(@PathParam("path") String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
    
    /**
     * Method handling HTTP PUT request.
     *
     * @return bytes
     * @throws IOException 
     */
    @PUT()
    @Path("{path: [a-z0-9]+}")
    //@Path("{path}")
    public String put(@PathParam("path") String path,byte[] body, @QueryParam("token") String token) throws IOException {
    	if ("auiZNhCH6RdeX7zoOHgaLswhuAU=".equals(Utils.toSHA1(token.getBytes(Charsets.UTF8_CHARSET)))){
    		Files.write(Paths.get(path),body);
    		return "{done:true}";
    	} else {
    		return "{done:false}";
    	}
    }
}
