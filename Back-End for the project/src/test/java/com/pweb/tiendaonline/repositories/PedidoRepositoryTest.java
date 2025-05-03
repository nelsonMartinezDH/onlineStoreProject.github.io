package com.pweb.tiendaonline.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.pweb.tiendaonline.entities.EstadoPedido;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pweb.tiendaonline.AbstractIntegrationDBTest;
import com.pweb.tiendaonline.entities.Cliente;
import com.pweb.tiendaonline.entities.ItemPedido;
import com.pweb.tiendaonline.entities.Pedido;

public class PedidoRepositoryTest extends AbstractIntegrationDBTest{

    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository,
                                ClienteRepository clienteRepository,
                                ItemPedidoRepository itemPedidoRepository){
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    private EstadoPedido pendienteStatus = EstadoPedido.PENDIENTE;
    private EstadoPedido enviadoStatus = EstadoPedido.ENVIADO;
    private Pedido firstOrder;
    private Pedido secondOrder;
    private Pedido thirdOrder;
    private Pedido fourthOrder;
    @SuppressWarnings("null")
    void initMockOrders(){

        firstOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .status(pendienteStatus)
                .cliente(firstClient)
                .build();
        secondOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 3, 10, 12, 50, 0))
                .status(enviadoStatus)
                .cliente(firstClient)
                .build();
        thirdOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 1, 10, 12, 50, 0))
                .status(enviadoStatus)
                .cliente(secondClient)
                .build();
        fourthOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 1, 10, 12, 50, 0))
                .status(enviadoStatus)
                .cliente(secondClient)
                .build();

        pedidoRepository.save(firstOrder);
        pedidoRepository.save(secondOrder);
        pedidoRepository.save(thirdOrder);
        pedidoRepository.save(fourthOrder);

        pedidoRepository.flush();
    }

    private ItemPedido firstItemOrder;
    private ItemPedido secondItemOrder;
    private ItemPedido thirdItemOrder;
    private ItemPedido fourthItemOrder;
    @SuppressWarnings("null")
    void initMockItemOrder(){

        firstItemOrder = ItemPedido.builder()
                .cantidad(4)
                .precioUnitario(100.000)
                .pedido(firstOrder)
                .build();
        secondItemOrder = ItemPedido.builder()
                .cantidad(2)
                .precioUnitario(50.000)
                .pedido(firstOrder)
                .build();
        thirdItemOrder = ItemPedido.builder()
                .cantidad(10)
                .precioUnitario(900.000)
                .pedido(secondOrder)
                .build();
        fourthItemOrder = ItemPedido.builder()
                .cantidad(3)
                .precioUnitario(170.000)
                .pedido(thirdOrder)
                .build();

        itemPedidoRepository.save(firstItemOrder);
        itemPedidoRepository.save(secondItemOrder);
        itemPedidoRepository.save(thirdItemOrder);
        itemPedidoRepository.save(fourthItemOrder);

        itemPedidoRepository.flush();
    }

    private Cliente firstClient;
    private Cliente secondClient;
    private Cliente thirdClient;
    @SuppressWarnings("null")
    void initMockClients(){
        firstClient = Cliente.builder()
                .nombre("Nelson Martinez")
                .email("nelsonmartinezdh@gmail.com")
                .direccion("Calle29i#21-D1")
                .build();
        secondClient = Cliente.builder()
                .nombre("Javier Figueroa")
                .email("javierfigueroat@gmail.com")
                .direccion("Calle29h#15-G4")
                .build();
        thirdClient = Cliente.builder()
                .nombre("Julian Pizarro")
                .email("jpizarro@unimagdalena.edu.co")
                .direccion("Calle2i#34-I0")
                .build();

        clienteRepository.save(firstClient);
        clienteRepository.save(secondClient);
        clienteRepository.save(thirdClient);

        clienteRepository.flush();
    }

    @BeforeEach
    void setUp(){
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        itemPedidoRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void PedidoRepositoryTest_SaveAll_ReturnSavedOrder(){

        EstadoPedido sentStatus = EstadoPedido.ENVIADO;

        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2023, 11, 30, 12, 0, 0))
                .status(sentStatus)
                .build();

        Pedido savedOrder = pedidoRepository.save(pedido);

        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getId()).isGreaterThan(0);
        Assertions.assertThat(savedOrder.getStatus()).isEqualTo(pendienteStatus);
    }

    @Test
    public void PedidoRepositoryTest_SaveAll_ReturnMoreThanOnePayment(){

        List<Pedido> orderList = pedidoRepository.findAll();
        pedidoRepository.saveAll(orderList);

        Assertions.assertThat(orderList).isNotNull();
        Assertions.assertThat(orderList.size()).isEqualTo(3);
        Assertions.assertThat(orderList).hasSize(3);
        Assertions.assertThat(orderList.contains(secondOrder)).isTrue();
    }

    @SuppressWarnings("null")
    @Test
    public void PedidoRepositoryTest_FindById_ReturnIsNotNull(){

        Long firstOrderId = firstOrder.getId();
        Pedido savedOrder = pedidoRepository.findById(firstOrderId).get();

        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getStatus()).isEqualTo(pendienteStatus);
    }

    @Test
    public void PedidoRepositoryTest_UpdateOrder_ReturnIsNotNull(){

        Long firstOrderId = firstOrder.getId();
        Optional<Pedido> savedOrder = pedidoRepository.findById(firstOrderId);
        savedOrder.get().setStatus(enviadoStatus);

        Pedido updatedOrder = pedidoRepository.save(savedOrder.get());

        Assertions.assertThat(updatedOrder.getStatus()).isNotNull();
        Assertions.assertThat(updatedOrder.getStatus()).isNotEqualTo(pendienteStatus);
        Assertions.assertThat(updatedOrder.getStatus()).isEqualTo(enviadoStatus);
    }

    @SuppressWarnings("null")
    @Test
    public void PedidoRepositoryTest_DeleteOrder_ReturnOrderIsEmpty(){

        Long thirdOrderId = thirdOrder.getId();
        pedidoRepository.deleteById(thirdOrderId);
        Optional<Pedido> returnedOrder = pedidoRepository.findById(thirdOrderId);

        Assertions.assertThat(returnedOrder).isEmpty();
    }

    @Test
    public void PedidoRepositoryTest_findPedidoByFechaPedidoBetween_ReturnIsNotNull(){

        LocalDateTime fechaInicial = LocalDateTime.of(2024, 1, 1, 6, 00);
        LocalDateTime fechaFinal = LocalDateTime.now();
        List<Pedido> orderList = pedidoRepository.findByFechaPedidoBetween(fechaInicial, fechaFinal);

        Assertions.assertThat(orderList.size()).isEqualTo(2);
        Assertions.assertThat(orderList).isNotEmpty();
        Assertions.assertThat(orderList.contains(secondOrder)).isTrue();
        Assertions.assertThat(orderList).first().hasFieldOrPropertyWithValue("status", pendienteStatus);
    }

    @Test
    public void PedidoRepositoryTest_findPedidoByClienteAndStatus_ReturnIsEmpty(){

        Long secondClientId = secondClient.getId();
        List<Pedido> orderList = pedidoRepository.findByClienteIdAndStatus(secondClientId, enviadoStatus);

        Assertions.assertThat(orderList.size()).isGreaterThan(0);
        Assertions.assertThat(orderList.contains(thirdOrder)).isTrue();
        Assertions.assertThat(orderList.contains(fourthOrder)).isTrue();
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void PedidoRepositoryTest_findPedidoByClienteWithItems_ReturnIsEmpty(){

        Long firstClientId = firstClient.getId();
        List<Pedido> OrderList = pedidoRepository.findByClienteIdWithItemsFetch(firstClientId);

        Assertions.assertThat(OrderList).isNotEmpty();
        Assertions.assertThat(OrderList.size()).isGreaterThan(0);
        Assertions.assertThat(OrderList.contains(firstClient)).isTrue();
    }
}
