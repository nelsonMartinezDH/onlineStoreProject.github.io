package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToSaveDto;
import com.pweb.tiendaonline.dtos.DetalleEnvio.DetalleEnvioToShowDto;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.detalleEnvio.DetalleEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shipping")
public class DetalleEnvioController {

    private final DetalleEnvioService detalleEnvioService;

    @Autowired
    public DetalleEnvioController(DetalleEnvioService detalleEnvioService) {
        this.detalleEnvioService = detalleEnvioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioToShowDto> getDetalleEnvioById(@PathVariable Long id) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioService.getDetalleEnvioById(id);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DetalleEnvioToShowDto>> getAllDetalleEnvio() {
        try {
            List<DetalleEnvioToShowDto> detallesEnvio = detalleEnvioService.getAllDetalleEnvios();
            return ResponseEntity.ok().body(detallesEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DetalleEnvioToShowDto> getDetalleEnvioByPedidoId(@PathVariable Long orderId) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioService.getDetalleEnvioByPedidoId(orderId);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carrier")
    public ResponseEntity<List<DetalleEnvioToShowDto>> getDetalleEnvioByTransportadora(@RequestParam("name") String transporter) {
        try {
            List<DetalleEnvioToShowDto> detallesEnvio = detalleEnvioService.getDetalleEnvioByTransportadora(transporter);
            return ResponseEntity.ok().body(detallesEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleEnvioToShowDto> postDetalleEnvio(@RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        DetalleEnvioToShowDto detalleEnvio = detalleEnvioService.saveDetalleEnvio(detalleEnvioToSaveDto);
        return ResponseEntity.ok().body(detalleEnvio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioToShowDto> putDetalleEnvio(@PathVariable Long id, @RequestBody DetalleEnvioToSaveDto detalleEnvioToSaveDto) {
        try {
            DetalleEnvioToShowDto detalleEnvio = detalleEnvioService.updateDetalleEnvioById(id, detalleEnvioToSaveDto);
            return ResponseEntity.ok().body(detalleEnvio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalleEnvio(@PathVariable Long id) {
        try {
            detalleEnvioService.deleteDetalleEnvioById(id);
            return ResponseEntity.ok().body("Detalle de envío eliminado");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
