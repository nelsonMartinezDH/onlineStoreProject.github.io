package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.pedido.PedidoToSaveDto;
import com.pweb.tiendaonline.dtos.pedido.PedidoToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.pedido.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public  PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoToShowDto> getPedidoById(@PathVariable Long id){
        try{
            PedidoToShowDto res = pedidoService.findPedidoById(id);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoToShowDto>> getAllPedido(){
        try{
            List<PedidoToShowDto> res = pedidoService.findAllPedidos();
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PedidoToShowDto>> getPedidoByClienteIdAndStatus(@PathVariable Long customerId, @RequestParam("status") String status){
        try{
            EstadoPedido statusPedido = EstadoPedido.fromString(status.toUpperCase());
            List<PedidoToShowDto> res = pedidoService.findPedidosByClienteIdAndStatus(customerId, statusPedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PedidoToShowDto>> getPedidoBetweenDates(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        try{
            List<PedidoToShowDto> res = pedidoService.findPedidosByFechaPedidoBetween(startDate, endDate);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidoToShowDto> postPedido(@RequestBody PedidoToSaveDto pedido){
        try{
            PedidoToShowDto res = pedidoService.savePedido(pedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoToShowDto> putPedido(@PathVariable Long id, @RequestBody PedidoToSaveDto pedido){
        try{
            PedidoToShowDto res = pedidoService.updatePedidoById(id, pedido);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> putPedido(@PathVariable Long id){
        try{
            pedidoService.deletePedidoById(id);
            return ResponseEntity.ok().body("Pedido Eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
