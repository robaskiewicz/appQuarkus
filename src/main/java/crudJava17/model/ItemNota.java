package crudJava17.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "itemnota")
public class ItemNota extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    public Produto produto;
//    @JsonBackReference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nota_fiscal_id")
    public NotaFiscal notaFiscal;

    @Column(nullable = false)
    public BigDecimal valorUnitario = BigDecimal.ZERO;
    @Column(nullable = false)
    public Integer qtd;


    public BigDecimal calculaTotalItem() {
        if (valorUnitario == null || qtd == null) {
            return BigDecimal.ZERO;
        }
        return valorUnitario.multiply(BigDecimal.valueOf(qtd));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemNota itemNota = (ItemNota) o;
        return Objects.equals(id, itemNota.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
