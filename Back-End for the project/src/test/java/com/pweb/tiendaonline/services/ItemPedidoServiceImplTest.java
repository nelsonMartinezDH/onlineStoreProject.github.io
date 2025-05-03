package com.pweb.tiendaonline.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import com.pweb.tiendaonline.dtos.ItemPedido.ItemPedidoToShowDto;
import com.pweb.tiendaonline.entities.EstadoPedido;
import com.pweb.tiendaonline.services.itemPedido.ItemPedidoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.pweb.tiendaonline.entities.ItemPedido;
import com.pweb.tiendaonline.entities.Pedido;
import com.pweb.tiendaonline.entities.Producto;
import com.pweb.tiendaonline.repositories.ItemPedidoRepository;
import com.pweb.tiendaonline.repositories.PedidoRepository;
import com.pweb.tiendaonline.repositories.ProductoRepository;

public class ItemPedidoServiceImplTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    private EstadoPedido pendienteStatus = EstadoPedido.PENDIENTE;
    private Pedido order;
    private Producto product;
    private ItemPedido firstOrderItem;
    private ItemPedido secondOrderItem;
    @BeforeEach
    void setUp(){
        order = Pedido.builder()
                .id(1l)
                .fechaPedido(LocalDateTime.of(2023, 12, 24, 13, 30, 0))
                .status(pendienteStatus)
                .build();
        product = Producto.builder()
                .id(2l)
                .nombre("Laptop")
                .price(100.000)
                .stock(12)
                .build();
        firstOrderItem = ItemPedido.builder()
                .id(3l)
                .cantidad(4)
                .precioUnitario(100.000)
                .pedido(order)
                .producto(product)
                .build();
        secondOrderItem = ItemPedido.builder()
                .id(4l)
                .cantidad(5)
                .precioUnitario(150.000)
                .pedido(order)
                .producto(product)
                .build();
    }

    @Test
    void givenItemOrder_WhenSaveItemOrder_ThenReturnItemOrder(){

    }

    @Test
    void givenItemOrderId_WhenfindItemPedidoById_ThenReturnItemOrder(){

        when(itemPedidoRepository.findById(4l)).thenReturn(Optional.ofNullable(secondOrderItem));

        List<ItemPedidoToShowDto> savedItemOrder = itemPedidoService.findItemPedidoToShowDtoByPedidoId(4l);

        Assertions.assertThat(savedItemOrder).isNotNull();
        Assertions.assertThat(savedItemOrder.size()).isEqualTo(1);
    }

    @Test
    void orderItemService_findAllItemPedidos_ThenReturnOrderItems(){

        List<ItemPedido> orderItems = itemPedidoRepository.findAll();

        when(itemPedidoRepository.findAll()).thenReturn(orderItems);

        List<ItemPedidoToShowDto> foundOrderItems = itemPedidoService.getAllItemPedidos();

        assertThat(foundOrderItems.size()).isGreaterThan(0);
        assertThat(foundOrderItems.contains(secondOrderItem)).isTrue();
        assertThat(foundOrderItems).isEqualTo(1);
    }

    @Test
    void orderItemService_updateItemPedido_ThenReturnOrderItem(){

    }

    @Test
    void orderItemService_deleteItemPedido_ThenReturnOrderItem(){

        given(itemPedidoRepository.findById(3l)).willReturn(Optional.ofNullable(firstOrderItem));
        doNothing().when(itemPedidoRepository).delete(firstOrderItem);

        ItemPedidoToShowDto deletedOrderItem = itemPedidoService.getItemPedidoById(3l);

        assertAll(() -> itemPedidoRepository.deleteById(3l));
        assertThat(deletedOrderItem).isNull();
    }

    @Test
    void orderItemService_findItemPedidoByPedidoId_ThenReturnOrderItem(){

        List<ItemPedido> items = itemPedidoRepository.findByPedidoId(order.getId());

        given(itemPedidoRepository.findByPedidoId(1l)).willReturn(items);

        List<ItemPedidoToShowDto> foundOrderItems = itemPedidoService.findItemPedidoToShowDtoByPedidoId(1l);

        assertThat(foundOrderItems.size()).isGreaterThan(0);
        assertThat(foundOrderItems.contains(firstOrderItem)).isTrue();
    }

    @Test
    void orderItemService_findItemPedidoByProductoId_ThenReturnOrderItem(){

        List<ItemPedido> items = itemPedidoRepository.findByProductoId(product.getId());

        given(itemPedidoRepository.findByProductoId(2l)).willReturn(items);

        List<ItemPedidoToShowDto> foundOrderItems = itemPedidoService.findItemPedidoToShowDtoByProductoId(2l);

        assertThat(foundOrderItems.size()).isGreaterThan(0);
        assertThat(foundOrderItems.contains(secondOrderItem)).isTrue();
    }

    @Test
    void orderItemService_findTotalByProductoId_ThenReturnTotal(){

        Double total = itemPedidoRepository.getTotalVentasByProductId(product.getId());

        given(itemPedidoRepository.getTotalVentasByProductId(product.getId())).willReturn(total);

        Double calculatedTotal = itemPedidoRepository.getTotalVentasByProductId(1l);

        assertThat(calculatedTotal).isNotNull();
    }

}
