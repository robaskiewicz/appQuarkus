package crudJava17.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "notafiscal")
public class NotaFiscal extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    public UUID id;

    @Column(nullable = false)
    public String numeroNota;

    @Column(nullable = false)
    public LocalDateTime dataEHoraEmissao;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    public Fornecedor fornecedor;
    @Column(nullable = false)
    public  String endereco;
    @Column(nullable = false)
    public BigDecimal valorTotalNota = BigDecimal.ZERO;
//    @JsonManagedReference
    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ItemNota> itens = new ArrayList<>();


//    public void addItem(ItemNota item) {
//        item.notaFiscal = this;
//        this.itens.add(item);
//    }
//
//    public void removeItem(ItemNota item) {
//        item.notaFiscal = null;
//        this.itens.remove(item);
//    }

}
