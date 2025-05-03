package com.pweb.tiendaonline.repositories;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import com.pweb.tiendaonline.entities.EstadoPedido;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pweb.tiendaonline.AbstractIntegrationDBTest;
import com.pweb.tiendaonline.entities.DetalleEnvio;
import com.pweb.tiendaonline.entities.Pedido;

public class DetalleEnvioRepositoryTest extends AbstractIntegrationDBTest {

    private DetalleEnvioRepository detalleEnvioRepository;
    private PedidoRepository pedidoRepository;

    @Autowired
    public DetalleEnvioRepositoryTest(DetalleEnvioRepository detalleEnvioRepository,
                                      PedidoRepository pedidoRepository){
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.pedidoRepository = pedidoRepository;
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

    private DetalleEnvio firstShippingDetail;
    private DetalleEnvio secondShippingDetail;
    private DetalleEnvio thirdShippingDetail;
    @SuppressWarnings("null")
    void initMockShippingDetails(){
        firstShippingDetail = DetalleEnvio.builder()
                .direccion("Calle29i#21-D1")
                .numeroGuia("SEV9876543210")
                .transportadora("Servientrega")
                .pedido(firstOrder)
                .build();
        secondShippingDetail = DetalleEnvio.builder()
                .direccion("Calle14k#23")
                .numeroGuia("SEV9876543210")
                .transportadora("Servientrega")
                .pedido(secondOrder)
                .build();
        thirdShippingDetail = DetalleEnvio.builder()
                .direccion("Calle29i#21-D1")
                .numeroGuia("IRV1234567890")
                .transportadora("Inter Rapidísimo")
                .pedido(thirdOrder)
                .build();

        detalleEnvioRepository.save(firstShippingDetail);
        detalleEnvioRepository.save(secondShippingDetail);
        detalleEnvioRepository.save(thirdShippingDetail);

        detalleEnvioRepository.flush();
    }

    @BeforeEach
    void setUp(){
        detalleEnvioRepository.deleteAll();
        pedidoRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void DetalleEnvioRepositoryTest_SaveAll_ReturnSavedDetails(){
        DetalleEnvio detalle = DetalleEnvio.builder()
                .direccion("Calle29i#21-D1")
                .numeroGuia("SEV9876543210")
                .transportadora("Servientrega")
                .build();

        DetalleEnvio savedShippingDetails = detalleEnvioRepository.save(detalle);

        Assertions.assertThat(savedShippingDetails).isNotNull();
        Assertions.assertThat(savedShippingDetails.getId()).isGreaterThan(0);
        Assertions.assertThat(savedShippingDetails.getTransportadora()).isEqualTo("Servientrega");
    }

    @SuppressWarnings("null")
    @Test
    public void DetalleEnvioRepositoryTest_SaveAll_ReturnMoreThanOneShippingDetails(){

        List<DetalleEnvio> detailShippingList = detalleEnvioRepository.findAll();
        detalleEnvioRepository.saveAll(detailShippingList);

        Assertions.assertThat(detailShippingList).isNotNull();
        Assertions.assertThat(detailShippingList.size()).isEqualTo(0);
        Assertions.assertThat(detailShippingList).first().hasFieldOrPropertyWithValue("numeroGuia", "SEV9876543210");
    }

    @SuppressWarnings("null")
    @Test
    public void DetalleEnvioRepositoryTest_FindById_ReturnIsNotNull(){

        Long firstShippingDetailId = firstShippingDetail.getId();
        DetalleEnvio ShippindDetail = detalleEnvioRepository.findById(firstShippingDetailId).get();

        Assertions.assertThat(ShippindDetail).isNotNull();
        Assertions.assertThat(secondShippingDetail.getDireccion()).isEqualTo("Calle29i#21-D1");
    }

   @Test
    public void DetalleEnvioRepositoryTest_UpdateShippingDetail_ReturnDetailNotNull(){

        Long thirdShippingDetailId = thirdShippingDetail.getId();
        Optional<DetalleEnvio> savedShippingDetail = detalleEnvioRepository.findById(thirdShippingDetailId);
        savedShippingDetail.get().setTransportadora("Inter Rapidísimo");
        savedShippingDetail.get().setDireccion("Barrio: Portal universitario, Calle2U#13");

        DetalleEnvio updatedShippingDetail = detalleEnvioRepository.save(savedShippingDetail.get());

        Assertions.assertThat(updatedShippingDetail.getDireccion()).isNotNull();
        Assertions.assertThat(updatedShippingDetail.getTransportadora()).isEqualTo("Inter Rapidísimo");
        Assertions.assertThat(updatedShippingDetail.getDireccion()).isEqualTo("Barrio: Portal universitario, Calle2U#13");
    }

    @SuppressWarnings("null")
    @Test
    public void DetalleEnvioRepositoryTest_DeleteShippingDetail_ReturnDetailIsEmpty(){

        Long secondShippingDetailId = secondShippingDetail.getId();
        detalleEnvioRepository.deleteById(secondShippingDetailId);
        Optional<DetalleEnvio> returnedShippingDetail = detalleEnvioRepository.findById(secondShippingDetailId);

        Assertions.assertThat(returnedShippingDetail).isEmpty();
    }

    @Test
    public void DetalleEnvioRepositoryTest_findDetalleEnvioByPedidoId_ReturnIsNotEmpty(){

        Long firstOrderId = firstOrder.getId();
        Optional<DetalleEnvio> ShippingDetailList =  detalleEnvioRepository.findByPedidoId(firstOrderId);

        Assertions.assertThat(ShippingDetailList).isNotEmpty();
        Assertions.assertThat(ShippingDetailList).contains(firstShippingDetail);
        Assertions.assertThat(ShippingDetailList.get().getNumeroGuia()).isEqualTo("SEV9876543210");
    }

    @Test
    public void DetalleEnvioRepositoryTest_findShippingDetailByDriver_ReturnIsNotEmpty(){

        List<DetalleEnvio> ShippingDetailList =  detalleEnvioRepository.findByTransportadoraContainingIgnoreCase("Servientrega");

        Long firstShippingId = ShippingDetailList.get(0).getId();
        Long secondShippingId = ShippingDetailList.get(1).getId();

        Assertions.assertThat(ShippingDetailList).isNotEmpty();
        Assertions.assertThat(firstShippingId).isEqualTo(firstShippingDetail.getId());
        Assertions.assertThat(secondShippingId).isEqualTo(secondShippingDetail.getId());
        Assertions.assertThat(ShippingDetailList).last().hasFieldOrPropertyWithValue("transportadora", "Servientrega");
    }

    @Test
    public void detalleEnvioRepositoryTest_findShippingDetailByState_ReturnIsNotEmpty(){

        List<DetalleEnvio> ShippingDetailList = detalleEnvioRepository.findByPedidoStatus(enviadoStatus);

        Assertions.assertThat(ShippingDetailList).isNotEmpty();
        Assertions.assertThat(ShippingDetailList).isNotNull();
        Assertions.assertThat(ShippingDetailList).startsWith(firstShippingDetail);
        Assertions.assertThat(ShippingDetailList).endsWith(thirdShippingDetail);
        Assertions.assertThat(ShippingDetailList.size()).isEqualTo(2);
        Assertions.assertThat(ShippingDetailList).first().hasFieldOrPropertyWithValue("numeroGuia", "SEV9876543210");
        Assertions.assertThat(ShippingDetailList).last().hasFieldOrPropertyWithValue("transportadora", "Inter Rapidísimo");
    }

}
