package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToSaveDto;
import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToShowDto;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.itemPedido.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order-items")
public class ItemPedidoController {

    ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService){
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoToShowDto> getItemPedidoById(@PathVariable Long id){
        try {
            ItemPedidoToShowDto res = itemPedidoService.getItemPedidoById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoToShowDto>> getAllItemPedido(){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoService.getAllItemPedidos();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemPedidoToShowDto>> getItemPedidoByOrderId(@PathVariable Long orderId){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoService.findItemPedidoToShowDtoByPedidoId(orderId);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ItemPedidoToShowDto>> getItemPedidoByProductId(@PathVariable Long productId){
        try {
            List<ItemPedidoToShowDto> res = itemPedidoService.findItemPedidoToShowDtoByProductoId(productId);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ItemPedidoToShowDto> postItemPedido(@RequestBody ItemPedidoToSaveDto itemPedido){
        try{
            ItemPedidoToShowDto res = itemPedidoService.saveItemPedido(itemPedido);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoToShowDto> putItemPedido(@PathVariable Long id, @RequestBody ItemPedidoToSaveDto itemPedido){
        try{
            ItemPedidoToShowDto res = itemPedidoService.updateItemPedido(id, itemPedido);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemPedido(@PathVariable Long id){
        try{
            itemPedidoService.deleteItemPedidoById(id);
            return ResponseEntity.ok().body("ItemPedido con ID " + id + " eliminado");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
