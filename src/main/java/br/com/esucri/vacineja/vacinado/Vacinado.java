package br.com.esucri.vacineja.vacinado;

import br.com.esucri.vacineja.vacina.Vacina;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vacinados", schema = "public")
public class Vacinado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(nullable = false)
    private String rg;
    
    @Column(nullable = false)
    private String cpf;
    
    @Column(nullable = false)
    private String endereco;
    
    private LocalDate nascimento;
       
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(
        name="vacina_vacinados",
        joinColumns = @JoinColumn(name = "id_vacinados"),
        inverseJoinColumns = @JoinColumn(name = "id_vacina"),
        foreignKey = @ForeignKey(name = "fk_vacinados"),
        inverseForeignKey = @ForeignKey(name = "fk_vacina")
    )
    
    private List<Vacina> vacinas;
    
    public Vacinado() {
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }

    public void setVacinas(List<Vacina> vacinas) {
        this.vacinas = vacinas;
    }
    
}