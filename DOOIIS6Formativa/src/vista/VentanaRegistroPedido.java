package vista;

import modelo.*;
import modelo.PrioridadPedido;

import javax.swing.*;

public class VentanaRegistroPedido extends JFrame {

    private final ZonaDeCarga zonaDeCarga;

    private JPanel panelRegistro;
    private JTextField txtIdPedido;
    private JTextField txtDescripcion;
    private JTextField txtNumero;
    private JTextField txtCalle;
    private JTextField txtCiudad;
    private JTextField txtDistancia;
    private JComboBox<String> comboTipoPedido;
    private JComboBox<String> comboPrioridad;
    private JButton botonGuardar;
    private JButton botonLimpiar;
    private JCheckBox checkValidacion;

    public VentanaRegistroPedido(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;

        setContentPane(panelRegistro);

        configurarVentana();
        configurarComponentes();
        configurarBotones();
    }

    //Configura la ventana donde se visualizará el formulario de registro.
    private void configurarVentana() {
        setTitle("SPEEDFAST - REGISTRAR PEDIDO.");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void configurarComponentes() {
        //Opciones del JComboBox
        comboTipoPedido.setModel(new DefaultComboBoxModel<>(
                new String[]{"Pedido Express", "Pedido Comida", "Encomienda"}));

        comboPrioridad.setModel((new DefaultComboBoxModel<>(
                new String[]{"ALTA", "MEDIA", "BAJA"})));
    }

    //Conecta los botones visuales con el código que se ejecutará al hacer clic.
    private void configurarBotones() {
        botonGuardar.addActionListener(e -> registrarPedido());
        botonLimpiar.addActionListener(e -> limpiar());
    }

    //REGISTRO: Registra el ingreso de un pedido.
    private void registrarPedido() {
        try {
            //Recibe información del formulario.
            String textoIdPedido = txtIdPedido.getText().trim();
            String textoNumero = txtNumero.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String calle = txtCalle.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String textoDistancia = txtDistancia.getText().trim();

            //Valida campos obligatorios
            if (textoIdPedido.isEmpty() || descripcion.isEmpty() || textoNumero.isEmpty() || calle.isEmpty() || ciudad.isEmpty() || textoDistancia.isEmpty()) {
                throw new IllegalArgumentException("Todos son campos obligatorios");
            }

            //Convierte el texto a número.
            int idPedido = Integer.parseInt(textoIdPedido);
            int numero = Integer.parseInt(textoNumero);
            int distancia = Integer.parseInt(textoDistancia);

            //Validar números.
            if (idPedido <= 0) {
                throw new IllegalArgumentException("El número de pedido ingresado no es válido.");
            }
            if (numero <= 0) {
                throw new IllegalArgumentException("El número de domicilio ingresado no es válido.");
            }
            if (distancia <= 0) {
                throw new IllegalArgumentException("La distancia ingresada no es válida.");
            }

            //Registra el pedido
            DireccionEntrega direccionEntrega = new DireccionEntrega(numero, calle, ciudad);

            //Lee los combos
            String tipoElegido = (String) comboTipoPedido.getSelectedItem();
            PrioridadPedido prioridad = PrioridadPedido.valueOf((String) comboPrioridad.getSelectedItem());


            //Lee el checkbox
            boolean validacion = checkValidacion.isSelected();

            Pedido nuevoPedido;

            switch (tipoElegido) {
                case "Pedido Express" -> nuevoPedido = new PedidoExpress(
                        tipoElegido, idPedido, descripcion, direccionEntrega, distancia, validacion, prioridad);
                case "Pedido Comida" -> nuevoPedido = new PedidoComida(
                        tipoElegido, idPedido, descripcion, direccionEntrega, distancia, validacion, prioridad);
                case "Encomienda" -> nuevoPedido = new PedidoEncomienda(
                        tipoElegido, idPedido, descripcion, direccionEntrega, distancia, validacion, prioridad);
                default -> throw new IllegalStateException("Tipo de pedido no reconocido: " + tipoElegido);
            }

            zonaDeCarga.registrarPedido(nuevoPedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente.",
                    "Registro exitoso.",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiar();

        } catch (NumberFormatException exception) {
            //Se ejecuta si el numero contiene texto no numérico.
            JOptionPane.showMessageDialog(this, "Número de domicilio debe contener solo números enteros.", "Error de formato", JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException exception) {
            //Se ejecuta para validar la entrega del pedido.
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Datos no válidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Limpia los datos en tabla.
    private void limpiar() {
        comboTipoPedido.setSelectedIndex(0);
        txtIdPedido.setText("");
        txtDescripcion.setText("");
        txtNumero.setText("");
        txtCalle.setText("");
        txtCiudad.setText("");
        txtDistancia.setText("");
        comboPrioridad.setSelectedIndex(0);
    }
}
