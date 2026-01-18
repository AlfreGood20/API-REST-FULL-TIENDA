package com.tienda.api_tienda.exeptions;

import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MensajeExepcion {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Object message;
    private String path;
}