package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.pago.PagoToSaveDto;
import com.pweb.tiendaonline.dtos.pago.PagoToShowDto;
import com.pweb.tiendaonline.entities.MetodoPago;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.pago.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")

public class PagoController {

    PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoToShowDto> getPagoById(@PathVariable Long id){
        try {
            PagoToShowDto res = pagoService.findPagoById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PagoToShowDto>> getAllPago(){
        try {
            List<PagoToShowDto> res = pagoService.findAllPagos();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PagoToShowDto> getPagoByOrderId(@PathVariable Long orderId, @RequestParam("metodopago") MetodoPago status){
        try {
            PagoToShowDto res = pagoService.findPagoByPedidoIdAndMetodoPago(orderId, status);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/date-range")
    public ResponseEntity<List<PagoToShowDto>> getPagosByDateRange(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        try {
            List<PagoToShowDto> res = pagoService.findPagoByFechaPagoBetween(startDate, endDate);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<PagoToShowDto> postPago(@RequestBody PagoToSaveDto pago){
        PagoToShowDto res = pagoService.savePago(pago);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoToShowDto> putPago(@PathVariable Long id, @RequestBody PagoToSaveDto pago){
        try{
            PagoToShowDto res = pagoService.updatePagoById(id, pago);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> putPago(@PathVariable Long id){
        try{
            pagoService.deletePagoById(id);
            return ResponseEntity.ok().body("Pago eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
