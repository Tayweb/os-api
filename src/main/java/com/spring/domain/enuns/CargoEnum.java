package com.spring.domain.enuns;

import com.spring.service.exceptions.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CargoEnum {

    ASSISTENTE(1, "ASSISTENTE"),
    TECNICO(2, "TECNICO")
    ;

    private Integer cod;
    private String descricao;

    public static Integer consultarCargo(Integer cod) {
        if (Objects.isNull(cod)) {
            return null;
        }
        for (CargoEnum cargoEnum : CargoEnum.values()) {
            if (cod.equals(cargoEnum.getCod())) {
                return cargoEnum.cod;
            }
        }
        throw new NegocioException("Código de cargo inválido" + cod);
    }

}
