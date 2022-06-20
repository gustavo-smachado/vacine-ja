package br.com.esucri.vacineja.vacinador;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

@Stateless
public class VacinadorService {

    @PersistenceContext(unitName = "VacinejaPU")
    private EntityManager entityManager;

    public List<Vacinador> findAll() {
        return entityManager
                .createQuery("SELECT v FROM Vacinador v")
                .getResultList();
    }

    public Vacinador findById(Long id) {
        Vacinador vacinador = entityManager.find(Vacinador.class, id);
        if(vacinador == null) {
            throw new NotFoundException("Vacinador com o id " + id + " não encontrado");
        }
        return vacinador;
    }

    public Vacinador add(Vacinador vacinador) {
        validaNome(vacinador);
        validaRg(vacinador);
        validaCpf(vacinador);
        validaNascimento(vacinador);
        validaExistenciaVacinador(vacinador);

        entityManager.persist(vacinador);
        return vacinador;
    }

    public Vacinador update(Vacinador vacinador) {
        Long id = vacinador.getId();
        findById(id); 
        validaNome(vacinador);  
        validaRg(vacinador);
        validaCpf(vacinador);
        validaNascimento(vacinador);
        return entityManager.merge(vacinador);
    }

    public void remove(Long id) {
        entityManager.remove(findById(id));
    }
    
    private void validaNome(Vacinador vacinador) {
        if (vacinador.getNome().length() < 3) {
            throw new BadRequestException("O nome do vacinador não pode conter menos que três caracteres");
        }
    }
    
    private void validaRg(Vacinador vacinador) {
        if (vacinador.getRg().length() < 9) {
            throw new BadRequestException("O RG do vacinador está incompleto");
        }
    }
    
    private void validaCpf(Vacinador vacinador) {
        if (vacinador.getCpf().length() < 14) {
            throw new BadRequestException("O CPF do vacinador está incompleto");
        }
    }
    
    private void validaNascimento(Vacinador vacinador) {
        if (vacinador.getNascimento().isAfter(LocalDate.now())) {
            throw new BadRequestException("A data de nascimento do vacinador é posterior a data atual");
        }
    }

    private void validaExistenciaVacinador(Vacinador vacinador) {
        List<Vacinador> resultList = entityManager
                .createQuery("SELECT v FROM Vacinador v WHERE v.cpf = :cpf", Vacinador.class)
                .setParameter("cpf", vacinador.getCpf())
                .getResultList();
        
        if (resultList != null && !resultList.isEmpty()) {
            throw new BadRequestException("O vacinador já está cadastrado em nossa base");
        }
    }

}