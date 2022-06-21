package br.com.esucri.vacineja.vacina;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "vacinas", schema = "public")
@SequenceGenerator(name = "VACINA_SEQ", sequenceName = "VACINA_SEQ")
public class Vacina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VACINA_SEQ")
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String tipo;
    
    @Column(nullable = false)
    private String laboratorio;
    
    @Column(nullable = false)
    private String lote;
    
    private LocalDate fabricacao;
    
    private LocalDate validade;
    
    public Vacina() {
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }
    
    public String getLote() {
        return lote;
    }
    
    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(LocalDate fabricacao) {
        this.fabricacao = fabricacao;
    }
    
    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

}