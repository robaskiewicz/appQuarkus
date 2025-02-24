package crudJava17.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fornecedor")
public class Fornecedor extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true)
    public UUID id;
    @Column(nullable = false)
    public String razaoSocial;
    @Column(nullable = false, unique = true)
    public String email;
    @Column(nullable = false)
    public String telefone;
    @Column(nullable = false, unique = true)
    public String cnpj;
    public LocalDateTime dataBaixa;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public SituacaoFornecedor situacaoFornecedor;
}
