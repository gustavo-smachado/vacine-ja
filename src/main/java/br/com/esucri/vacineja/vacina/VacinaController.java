package br.com.esucri.vacineja.vacina;

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

@Path("vacinas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VacinaController {
    
    @Inject
    private VacinaService vacinaService;
    
    @GET
    public List<Vacina> findAll() {
        return this.vacinaService.findAll();
    }
    
    @GET
    @Path("{id}")
    public Vacina findById(@PathParam("id") Long id) {
        return this.vacinaService.findById(id);
    }
    
    @POST
    public Vacina add(Vacina vacina) {
        return this.vacinaService.add(vacina);
    }
    
    @PUT
    @Path("{id}") 
    public Vacina update(@PathParam("id") Long id, Vacina vacina) {
        vacina.setId(id);
        return this.vacinaService.update(vacina);        
    }
    
    @DELETE    
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        this.vacinaService.remove(id);
    }
}
