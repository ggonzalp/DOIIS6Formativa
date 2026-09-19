package vista;

import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.ZonaDeCarga;

import javax.swing.*;

/**
 * Clase que representa la ventana principal del programa.
 */

public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnRegistrar;
    private JButton btnVerHistorial;
    private JButton btnDespachar;

    //Gestor que almacena y administra los pedidos.
    private final ZonaDeCarga zonaDeCarga;

    public VentanaPrincipal() {
        zonaDeCarga = new ZonaDeCarga();

        setContentPane(panelPrincipal);

        configurarVentana();
        configurarBotones();
    }

    //Configuración de ventana.
    private void configurarVentana() {
        setTitle("SPEEDFAST");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Conecta los botones visuales con el código que se ejecutará al hacer clic.
    private void configurarBotones() {
        btnRegistrar.addActionListener(e -> {
            VentanaRegistroPedido ventanaRegistro = new VentanaRegistroPedido(zonaDeCarga);
            ventanaRegistro.setVisible(true);
        });

        btnVerHistorial.addActionListener(e -> {
            VentanaListaPedidos ventanaLista = new VentanaListaPedidos(zonaDeCarga);
            ventanaLista.setVisible(true);
        });

        btnDespachar.addActionListener(e -> {
            despacharPedido();
        });
    }

    //Simula el inicio de la ruta de reparto.
    private void despacharPedido() {
        String nombreRepartidor = JOptionPane.showInputDialog(this, "Nombre del repartidor:");

        if (nombreRepartidor == null || nombreRepartidor.isBlank()) {
            return;
        }
        Pedido pedido = zonaDeCarga.asignarPedido();
        if (pedido == null) {
            JOptionPane.showMessageDialog(this,
                    "No hay pedidos pendientes de despacho.",
                    "Sin pedidos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            pedido.asignarRepartidor(nombreRepartidor);
            pedido.cambiarEstado(EstadoPedido.EN_REPARTO);

            JOptionPane.showMessageDialog(this,
                    "Pedido #" + pedido.getIdPedido() + " asignado a " + nombreRepartidor + ".",
                    "Despacho exitoso", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo despachar el pedido #" + pedido.getIdPedido() +
                            ":\n" + exception.getMessage(),
                    "Despacho rechazado", JOptionPane.ERROR_MESSAGE);
        }
    }
}