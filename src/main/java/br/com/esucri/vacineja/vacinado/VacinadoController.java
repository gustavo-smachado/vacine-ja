package br.com.esucri.vacineja.vacinado;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("vacinados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VacinadoController {
    
    @Inject
    private VacinadoService vacinadoService;
    
    @GET
    public List<Vacinado> findAll() {
        return this.vacinadoService.findAll();
    }
    
    @GET
    @Path("{id}")
    public Vacinado findById(@PathParam("id") Long id) {
        return this.vacinadoService.findById(id);
    }
    
    @POST
    public Vacinado add(Vacinado vacinado) {
        return this.vacinadoService.add(vacinado);
    }
    
    @PUT
    @Path("{id}") 
    public Vacinado update(@PathParam("id") Long id, Vacinado vacinado) {
        vacinado.setId(id);
        return this.vacinadoService.update(vacinado);        
    }
    
    @DELETE    
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        this.vacinadoService.remove(id);
    }
}
