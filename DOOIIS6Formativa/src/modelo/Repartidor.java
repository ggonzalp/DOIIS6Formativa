package modelo;

import java.util.Random;

/**
 * Clase que representa un repartidor.
 */

public class Repartidor implements Runnable {

    private final ZonaDeCarga zonaDeCarga;
    private String nombreRepartidor;
    private final Random random = new Random();

    /**
     * Constructor de la clase Repartidor.
     *
     * @param nombreRepartidor Nombre del repartidor.
     * @param zonaDeCarga
     */
    public Repartidor(String nombreRepartidor, ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
        this.nombreRepartidor = nombreRepartidor;
    }

    //Metodo getter.
    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    //Metodo setter.
    public void setNombreRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
    }

    /**
     * Metodo run() recorre la ruta del repartidor y cambia el estado de entrega del pedido.
     */
    @Override
    public void run() {

        while (true) {
            Pedido pedido = zonaDeCarga.asignarPedido();

            if (pedido == null) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] No quedan más pedidos.");
                break;
            }

            if (pedido.getEstadoPedido() == EstadoPedido.CANCELADO) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido # " + pedido.getIdPedido() + " está cancelado, no sale a reparto.");
                continue;
            }

            System.out.println("[Repartidor: " + nombreRepartidor + "] Tomando pedido  #" + pedido.getIdPedido() + " (" + pedido.getTipoPedido() + ")");

            try {
                pedido.asignarRepartidor(nombreRepartidor);

                pedido.cambiarEstado(EstadoPedido.EN_REPARTO);

                //Simulación de tiempo que podría tardar un repartidor
                Thread.sleep(1000 + random.nextInt(1000));

                if (pedido instanceof Despachable despachable) {
                    despachable.despachar();
                }

                pedido.cambiarEstado(EstadoPedido.ENTREGADO);
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #" + pedido.getIdPedido() + " entregado.");

            } catch (IllegalStateException e) {
                System.out.println("[Repartidor: " + nombreRepartidor + "] Pedido #" + pedido.getIdPedido() + " no pudo entregarse: " + e.getMessage());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Repartidor: " + nombreRepartidor + "] fue interrumpido.");
                return;
            }
        }

        System.out.println("[Repartidor: " + nombreRepartidor + "] terminó su ruta de reparto.");
    }
}
