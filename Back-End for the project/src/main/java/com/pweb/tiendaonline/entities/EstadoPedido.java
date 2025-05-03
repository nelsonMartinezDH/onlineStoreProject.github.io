package com.pweb.tiendaonline.entities;

public enum EstadoPedido {
    PENDIENTE,
    ENVIADO,
    ENTREGADO;

    public static EstadoPedido fromString(String value) {
        return switch (value.toUpperCase()) {
            case "PENDIENTE" -> PENDIENTE;
            case "ENVIADO" -> ENVIADO;
            case "ENTREGADO" -> ENTREGADO;
            default -> throw new IllegalArgumentException("Estado del pedido inválido: " + value);
        };
    }

}
