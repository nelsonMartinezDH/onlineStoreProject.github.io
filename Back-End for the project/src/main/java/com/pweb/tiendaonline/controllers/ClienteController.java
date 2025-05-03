package com.pweb.tiendaonline.controllers;

import com.pweb.tiendaonline.dtos.cliente.ClienteToSaveDto;
import com.pweb.tiendaonline.dtos.cliente.ClienteToShowDto;
import com.pweb.tiendaonline.exceptions.NotFoundException;
import com.pweb.tiendaonline.services.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteToShowDto> getClienteById(@PathVariable Long id){
        try {
            ClienteToShowDto res = clienteService.findClienteById(id);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteToShowDto>> getAllCliente(){
        try {
            List<ClienteToShowDto> res = clienteService.findAllClientes();
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteToShowDto> getClienteByEmail(@PathVariable String email){
        try {
            ClienteToShowDto res = clienteService.findClienteByEmail(email);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/city")
    public ResponseEntity<List<ClienteToShowDto>> getClientesByCityName(@RequestParam("cityName") String address){
        try {
            List<ClienteToShowDto> res = clienteService.findClienteByDireccionContainingIgnoreCase(address);
            return ResponseEntity.ok().body(res);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<ClienteToShowDto> postCliente(@RequestBody ClienteToSaveDto cliente){
        ClienteToShowDto res = clienteService.SaveCliente(cliente);
        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteToShowDto> putCliente(@PathVariable Long id, @RequestBody ClienteToSaveDto cliente){
        try{
            ClienteToShowDto res = clienteService.updateClienteById(id, cliente);
            return ResponseEntity.ok().body(res);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id){
        try{
            clienteService.deleteClienteById(id);
            return ResponseEntity.ok().body("Cliente eliminado");
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
