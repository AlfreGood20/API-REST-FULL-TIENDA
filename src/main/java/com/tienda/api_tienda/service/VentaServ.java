package com.tienda.api_tienda.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tienda.api_tienda.dtos.request.VentaRequest;
import com.tienda.api_tienda.dtos.response.DetalleVentaResponse;
import com.tienda.api_tienda.dtos.response.VentaResponse;
import com.tienda.api_tienda.exeptions.ConflictoExeption;
import com.tienda.api_tienda.exeptions.ExepcionRecursoNoEncontrado;
import com.tienda.api_tienda.mapper.DetalleVentaMapper;
import com.tienda.api_tienda.mapper.ProductoMapper;
import com.tienda.api_tienda.mapper.UsuarioMapper;
import com.tienda.api_tienda.mapper.VentaMapper;
import com.tienda.api_tienda.model.Cliente;
import com.tienda.api_tienda.model.DetalleVenta;
import com.tienda.api_tienda.model.Estado;
import com.tienda.api_tienda.model.Metodo;
import com.tienda.api_tienda.model.Producto;
import com.tienda.api_tienda.model.Usuario;
import com.tienda.api_tienda.model.Venta;
import com.tienda.api_tienda.repository.ClienteRepo;
import com.tienda.api_tienda.repository.EstadoRepo;
import com.tienda.api_tienda.repository.MetodoRepo;
import com.tienda.api_tienda.repository.ProductoRepo;
import com.tienda.api_tienda.repository.UsuarioRepo;
import com.tienda.api_tienda.repository.VentaRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VentaServ {

    private final VentaRepo ventaRepo;
    private final UsuarioRepo usuarioRepo;
    private final ClienteRepo clienteRepo;
    private final MetodoRepo metodoRepo;
    private final ProductoRepo productoRepo;
    private final EstadoRepo estadoRepo;

    private final ProductoMapper productoMapper;
    private final UsuarioMapper usuarioMapper;
    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;


    private Usuario getUsuario(long id){
        return usuarioRepo.findById(id)
        .orElseThrow(()-> new ExepcionRecursoNoEncontrado("Usuario no encontrado con id:%s".formatted(id)));
    }

    private Metodo getMetodo(long id){
        return metodoRepo.findById(id)
        .orElseThrow(()-> new ExepcionRecursoNoEncontrado("Metodo no encontrado con id:%s".formatted(id)));
    }

    private Cliente getCliente(long id){
        return clienteRepo.findById(id).orElse(null);
    }

    private Producto getProducto(long id){
        return productoRepo.findById(id)
        .orElseThrow(() -> new ExepcionRecursoNoEncontrado("Producto no encontrado con id:%s".formatted(id)));
    }

    private Estado getEstado(long id){
        return estadoRepo.findById(id)
        .orElseThrow(()-> new ExepcionRecursoNoEncontrado("Estado no encontrado con id:%s".formatted(id)));
    }


    @Transactional
    public VentaResponse nueva(VentaRequest request){
        Usuario usuario = getUsuario(request.getUsuarioId());
        Cliente cliente = getCliente(request.getClienteId());

        Metodo metodo = getMetodo(request.getMetodoId());

        Venta venta = new Venta();

        List<DetalleVenta> detalles = request.getDetalles().stream()
            .map((detalle) -> {
                    Producto producto = getProducto(detalle.getProductoId());
                    int cantidad = detalle.getCantidad();

                    if(cantidad > producto.getStock() || producto.getStock() <= 0){
                        throw new ConflictoExeption("Producto id:%s y codigo de barra:%s. Se encuentra en bajo stock.".formatted(producto.getProductoId(), producto.getCodigoBarra()));
                    }

                    producto.setStock(producto.getStock() - cantidad);
                    BigDecimal precioUnitario = producto.getPrecioUnitario();
                    BigDecimal subTotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

                    DetalleVenta detalleVenta = DetalleVenta.builder()
                        .ventaId(venta)
                        .productoId(producto)
                        .cantidad(cantidad)
                        .precioUnitario(precioUnitario)
                        .subTotal(subTotal)
                        .build();

                    return detalleVenta;
                })
            .collect(Collectors.toList());

        BigDecimal montoTotal = detalles.stream()
                .map(detalle -> detalle.getSubTotal())
                .reduce(BigDecimal.ZERO, (resultado,subTotal) -> resultado.add(subTotal));

        BigDecimal montoRecibido = request.getMontoRecibido();
        if(montoRecibido.compareTo(montoTotal) < 0){
            throw new ConflictoExeption("El monto recibido no acompleta al total. Recibido:$%s Total:$%s".formatted(montoRecibido,montoTotal));
        }

        venta.setDetalle(detalles);
        venta.setEstadoId(getEstado(1));
        venta.setUsuarioId(usuario);
        venta.setMetodoId(metodo);
        venta.setClienteId(cliente);
        venta.setMontoTotal(montoTotal);
        venta.setMontoRecibido(montoRecibido);

        BigDecimal cambio = montoRecibido.subtract(montoTotal);
        venta.setMontoCambio(cambio);

        ventaRepo.save(venta);

        List<DetalleVentaResponse> detallesResponses = detalles.stream()
            .map(detalle -> {
                Producto producto = detalle.getProductoId();
                return detalleVentaMapper.entityToDto(detalle, productoMapper.entityToDto(producto, producto.getCategoria().getNombre()));
            })
            .collect(Collectors.toList());

        return ventaMapper.entityToDto(venta, usuarioMapper.entityToDto(usuario), null, detallesResponses);
    }

}