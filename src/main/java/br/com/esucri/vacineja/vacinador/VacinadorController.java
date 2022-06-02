package br.com.esucri.vacineja.vacinador;

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

@Path("vacinadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VacinadorController {
    
    @Inject
    private VacinadorService vacinadorService;
    
    @GET
    public List<Vacinador> findAll() {
        return this.vacinadorService.findAll();
    }
    
    @GET
    @Path("{id}")
    public Vacinador findById(@PathParam("id") Long id) {
        return this.vacinadorService.findById(id);
    }
    
    @POST
    public Vacinador add(Vacinador vacinador) {
        return this.vacinadorService.add(vacinador);
    }
    
    @PUT
    @Path("{id}") 
    public Vacinador update(@PathParam("id") Long id, Vacinador vacinador) {
        vacinador.setId(id);
        return this.vacinadorService.update(vacinador);        
    }
    
    @DELETE    
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        this.vacinadorService.remove(id);
    }
}