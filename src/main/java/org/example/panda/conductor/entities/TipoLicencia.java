package org.example.panda.conductor.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "tipos_licencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoLicencia implements Serializable{

    public enum  TipoLicenciaEnum{
        A3,
        A4,
        A5,
        B1,
        B2,
        B3,
        B4,
        C1,
        C2,
        C3,
        C4,
        D1,
        D2,
        D3,
        D4
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_licencia", nullable = false)
    private TipoLicenciaEnum tipoLicencia;
}
