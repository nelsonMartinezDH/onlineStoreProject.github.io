package com.pweb.tiendaonline.repositories;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.pweb.tiendaonline.entities.EstadoPedido;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pweb.tiendaonline.AbstractIntegrationDBTest;
import com.pweb.tiendaonline.entities.ItemPedido;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.entities.Producto;

public class ItemPedidoRepositoryTest extends AbstractIntegrationDBTest {

    private ItemPedidoRepository itemPedidoRepository;
    private PedidoRepository pedidoRepository;
    private ProductoRepository productoRepository;

    @Autowired
    public ItemPedidoRepositoryTest(ItemPedidoRepository itemPedidoRepository,
                                    PedidoRepository pedidoRepository,
                                    ProductoRepository productoRepository){
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    private EstadoPedido pendienteStatus = EstadoPedido.PENDIENTE;
    private EstadoPedido enviadoStatus = EstadoPedido.ENVIADO;
    private Pedido firstOrder;
    private Pedido secondOrder;
    private Pedido thirdOrder;
    @SuppressWarnings("null")
    void initMockOrders(){

        firstOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .status(pendienteStatus)
                .build();
        secondOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 3, 10, 12, 50, 0))
                .status(enviadoStatus)
                .build();
        thirdOrder = Pedido.builder()
                .fechaPedido(LocalDateTime.of(2024, 1, 10, 12, 50, 0))
                .status(enviadoStatus)
                .build();

        pedidoRepository.save(firstOrder);
        pedidoRepository.save(secondOrder);
        pedidoRepository.save(thirdOrder);

        pedidoRepository.flush();
    }

    private Producto firstProduct;
    private Producto secondProduct;
    private Producto thirdProduct;
    @SuppressWarnings("null")
    void initMockProducts(){

        firstProduct = Producto.builder()
                .nombre("Laptop")
                .price(100.000)
                .stock(12)
                .build();
        secondProduct = Producto.builder()
                .nombre("Mouse")
                .price(5.000)
                .stock(12)
                .build();
        thirdProduct = Producto.builder()
                .nombre("Keyboard")
                .price(20.000)
                .stock(12)
                .build();

        productoRepository.save(firstProduct);
        productoRepository.save(secondProduct);
        productoRepository.save(thirdProduct);

        productoRepository.flush();
    }

    private ItemPedido firstItemOrder;
    private ItemPedido secondItemOrder;
    private ItemPedido thirdItemOrder;
    private ItemPedido fourthOrder;
    @SuppressWarnings("null")
    void initMockItemOrder(){

        firstItemOrder = ItemPedido.builder()
                .cantidad(4)
                .precioUnitario(100.000)
                .pedido(firstOrder)
                .producto(firstProduct)
                .build();
        secondItemOrder = ItemPedido.builder()
                .cantidad(2)
                .precioUnitario(50.000)
                .pedido(firstOrder)
                .producto(firstProduct)
                .build();
        thirdItemOrder = ItemPedido.builder()
                .cantidad(10)
                .precioUnitario(900.000)
                .pedido(secondOrder)
                .producto(secondProduct)
                .build();
        fourthOrder = ItemPedido.builder()
                .cantidad(3)
                .precioUnitario(170.000)
                .pedido(thirdOrder)
                .producto(thirdProduct)
                .build();

        itemPedidoRepository.save(firstItemOrder);
        itemPedidoRepository.save(secondItemOrder);
        itemPedidoRepository.save(thirdItemOrder);
        itemPedidoRepository.save(fourthOrder);

        itemPedidoRepository.flush();
    }

    @BeforeEach
    void setUp(){
        itemPedidoRepository.deleteAll();
        pedidoRepository.deleteAll();
        productoRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void ItemPedidoRepositoryTest_SaveAll_ReturnSavedOrderItem(){
        ItemPedido item = ItemPedido.builder()
                .cantidad(4)
                .precioUnitario(100.000)
                .build();
        ItemPedido savedOrderItem = itemPedidoRepository.save(item);

        Assertions.assertThat(savedOrderItem).isNotNull();
        Assertions.assertThat(savedOrderItem.getId()).isGreaterThan(0);
        Assertions.assertThat(savedOrderItem.getCantidad()).isEqualTo(4);
    }

    @SuppressWarnings("null")
    @Test
    public void ItemPedidoRepositoryTest_SaveAll_ReturnMoreThanOneOrderItem(){

        List<ItemPedido> orderItemList = itemPedidoRepository.findAll();
        itemPedidoRepository.saveAll(orderItemList);

        Assertions.assertThat(orderItemList).isNotNull();
        Assertions.assertThat(orderItemList.size()).isEqualTo(2);
        Assertions.assertThat(orderItemList).hasSize(2);
    }

    @SuppressWarnings("null")
    @Test
    public void ItemPedidoRepositoryTest_FindById_ReturnIsNotNull(){

        Long firstItemOrderId = firstItemOrder.getId();
        ItemPedido savedOrderItem = itemPedidoRepository.findById(firstItemOrderId).get();

        Assertions.assertThat(savedOrderItem).isNotNull();
        Assertions.assertThat(savedOrderItem.getPrecioUnitario()).isEqualTo(100.000);
    }

    @Test
    public void ItemPedidoRepositoryTest_UpdateOrderItem_ReturnIsNotNull(){

        Optional<ItemPedido> savedOrder = itemPedidoRepository.findById(secondItemOrder.getId());
        savedOrder.get().setCantidad(6);
        savedOrder.get().setPrecioUnitario(150.000);

        ItemPedido updatedOrder = itemPedidoRepository.save(savedOrder.get());

        Assertions.assertThat(updatedOrder.getCantidad()).isNotNull();
        Assertions.assertThat(updatedOrder.getPrecioUnitario()).isNotNull();
        Assertions.assertThat(updatedOrder.getCantidad()).isEqualTo(6);
        Assertions.assertThat(updatedOrder.getPrecioUnitario()).isEqualTo(150.000);
    }

    @SuppressWarnings("null")
    @Test
    public void ItemPedidoRepositoryTest_DeleteOrderItem_ReturnOrderItemIsEmpty(){

        Long thirdItemOrderId = thirdItemOrder.getId();
        itemPedidoRepository.deleteById(thirdItemOrderId);
        Optional<ItemPedido> returnedOrderItem = itemPedidoRepository.findById(thirdItemOrderId);

        Assertions.assertThat(returnedOrderItem).isEmpty();
    }

    @Test
    public void ItemPedidoRepositoryTest_findItemPedidoByPedidoId_ReturnIsNotEmpty(){

        Long firstOrderId = firstOrder.getId();
        List<ItemPedido> OrderItemList = itemPedidoRepository.findByPedidoId(firstOrderId);

        Assertions.assertThat(OrderItemList).isNotEmpty();
        Assertions.assertThat(OrderItemList.size()).isGreaterThan(1);
        Assertions.assertThat(OrderItemList).contains(secondItemOrder);
    }

    @Test
    public void ItemPedidoRepositoryTest_findItemPedidoByProductoId_ReturnIsNotEmpty(){

        Long firstProductId = firstProduct.getId();
        List<ItemPedido> OrderItemList = itemPedidoRepository.findByProductoId(firstProductId);

        Assertions.assertThat(OrderItemList).isNotEmpty();
        Assertions.assertThat(OrderItemList.contains(firstItemOrder)).isTrue();
        Assertions.assertThat(OrderItemList.contains(secondItemOrder)).isTrue();
        Assertions.assertThat(OrderItemList.size()).isEqualTo(2);
    }

    @Test
    public void ItemPedidoRepositoryTest_findTotalByProductoId_ReturnIsEqualsTo(){

        Long firstProductId = firstProduct.getId();
        Double totalOrderItem = itemPedidoRepository.getTotalVentasByProductId(firstProductId);

        Assertions.assertThat(totalOrderItem).isEqualTo(400.000);
    }
}
