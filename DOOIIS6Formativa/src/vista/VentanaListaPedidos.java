package vista;

import modelo.Pedido;
import modelo.ZonaDeCarga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa la ventana secundaria donde se visualiza el historial de pedidos.
 */

public class VentanaListaPedidos extends  JFrame {

    private final ZonaDeCarga zonaDeCarga;

    //Modelo de la tabla.
    private DefaultTableModel tableModel;

    //Gestor que almacena y administra los pedidos.
    private JPanel panelLista;
    private JTable tablaPedidos;
    private JButton botonActualizar;

    /**
     * Constructor de la clase VentanaListaPedidos
     *
     * @param zonaDeCarga representa la zona de carga común donde se alojan los pedidos.
     */
    public VentanaListaPedidos(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;

        setContentPane(panelLista);

        configurarVentana();
        configurarTabla();
        configurarBoton();

        actualizarTabla();
    }

    //Configura la  ventana donde se aloja la tabla
    private void configurarVentana() {
        setTitle("SPEEDFAST - HISTORIAL DE PEDIDOS");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Configura la tabla que muestra el historial.
    private void configurarTabla() {
        String[] columnas = {"Tipo de Pedido", "Id Pedido", "Descripción", "N°", "Calle", "Ciudad", "Distancia (km)", "Prioridad"};

        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos.setModel(tableModel);
    }

    private void configurarBoton() {
        botonActualizar.addActionListener(e -> actualizarTabla());
    }

    //Vacía la tabla y la vuelve a llenar con los pedidos de la zondaDeCarga.
    private void actualizarTabla() {
        tableModel.setRowCount(0);

        for (Pedido pedido : zonaDeCarga.obtenerPedidos()) {
            Object[] fila = {
                    pedido.getTipoPedido(),
                    pedido.getIdPedido(),
                    pedido.getDescripcion(),
                    pedido.getDireccionEntrega().getNumero(),
                    pedido.getDireccionEntrega().getCalle(),
                    pedido.getDireccionEntrega().getCiudad(),
                    pedido.getDistanciaKm(),
                    pedido.getPrioridadPedido()
            };

            tableModel.addRow(fila);
        }
    }
}