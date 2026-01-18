package com.tienda.api_tienda.controller.api;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.tienda.api_tienda.dtos.request.ProductoRequest;
import com.tienda.api_tienda.dtos.response.ProductoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


public interface ProductoApi {
    

    @Operation(
        summary = "Creas nuevo producto",
        description = "Crea y devuelve producto. Recibe json y multimedia."
    )
    @ApiResponse(
        description = "Producto Creado con exito", 
        responseCode = "201",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ProductoResponse.class)
        ) 
    )
    @RequestBody(
        content = @Content(
            encoding = {
                @Encoding(name = "producto", contentType = MediaType.APPLICATION_JSON_VALUE),
                @Encoding(name = "file", contentType = MediaType.IMAGE_JPEG_VALUE+" , "+MediaType.IMAGE_PNG_VALUE)
            }
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<ProductoResponse> crear(ProductoRequest request, MultipartFile file);



    @Operation(
        summary = "Buscar por id",
        description = "Buscas producto por id y devuelve producto buscado."
    )
    @ApiResponse(
        description = "Recibes producto buscado",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(
                implementation = ProductoResponse.class
            )
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<ProductoResponse> obtenerPorId(long id);



    @Operation(
        summary = "Buscar por iniciales",
        description = "Busca productos por iniciales y recibes lista de productos"
    )
    @ApiResponse(
        description = "Lista de productos buscados",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class))
        )
    )
    ResponseEntity<List<ProductoResponse>> obtenerPorNombre(String q,int pagina, int tamaño);

    
    @Operation(
        summary = "Buscar por categoria",
        description = "Busca productos por id de la categoria"
    )
    @ApiResponse(
        description = "Lista de productos buscados",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class))
        )
    )
    ResponseEntity<List<ProductoResponse>> obtenerPorCategoria(long id, int pagina, int tamaño);


    @Operation(
        summary = "Actualizar por id",
        description = "Actualizar producto por id. Envias un producto json"
    )
    @ApiResponse(
        description = "Produto Actualizado",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ProductoResponse.class)
        )
    )
    @RequestBody(
        content = @Content(
            encoding = {
                @Encoding(name = "producto", contentType = MediaType.APPLICATION_JSON_VALUE),
                @Encoding(name = "file", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
            }
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<ProductoResponse> actualizarPorId(long id, ProductoRequest request, MultipartFile file);


    @Operation(
        summary = "Obtienes productos mas vendidos",
        description = "Obtienes los productos mas vendidos por defecto 10 productos"
    )
    @ApiResponse(
        description = "Lista de productos",
        responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class))
        )
    )
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<List<ProductoResponse>> obtenerMasVendidos(int pagina, int tamano);
}