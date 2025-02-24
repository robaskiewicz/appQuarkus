package crudJava17.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "produto")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    public UUID id;
    @Column(nullable = false)
    public String descricao;
    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    public SituacaoProduto situacaoProduto = SituacaoProduto.ATIVO;

}
