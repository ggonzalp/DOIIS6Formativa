package modelo;

/**
 * Clase que representa un pedido de encomienda.
 */

public class PedidoEncomienda extends Pedido implements Registrable, Cancelable, Despachable {

    private boolean validarEmpaquetado;

    /**
     * Constructor de la clase PedidoEncomienda
     * @param tipoPedido         Tipo de pedido.
     * @param idPedido           Número de identificación del pedido.
     * @param descripcion        Descripción del pedido.
     * @param direccionEntrega   Dirección de entrega del pedido.
     * @param distanciaKm        Distancia en kilómetros del lugar de entrega del producto.
     * @param validarEmpaquetado Validar el peso y empaquetado del pedido.
     * @param prioridadPedido    Prioridad del pedido.
     */
    public PedidoEncomienda(String tipoPedido, int idPedido, String descripcion, DireccionEntrega direccionEntrega, int distanciaKm, boolean validarEmpaquetado, PrioridadPedido prioridadPedido ) {
        super(tipoPedido, idPedido, descripcion, direccionEntrega, distanciaKm, prioridadPedido);

        this.validarEmpaquetado = validarEmpaquetado;
    }

    //Metodo getter
    public boolean getValidarEmpaquetado() {
        return validarEmpaquetado;
    }

    //Metodo setter
    public void setValidarEmpaquetado(boolean validarEmpaquetado) {
        this.validarEmpaquetado = validarEmpaquetado;
    }

    /**
     * Metodo asignarRepartidor
     * @param nombreRepartidor Nombre del repartidor.
     * @throws IllegalStateException Si el paquete no cumple con el peso ni embalaje adecuado, no es posible asignar el pedido.
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        super.asignarRepartidor();
        System.out.println("\nValidando peso y embalaje... ");

        //Validador de validarEmpaquetado.
        if (validarEmpaquetado == true) {
            System.out.println("Peso y empaquetado correctos: Sí.");
            System.out.println("Peso y empaquetado validados correctamente.");
        } else {
            throw new IllegalStateException("Peso y empaquetado correctos: No.");
        }
    }

    /**
     * Metodo que calcula el tiempo de entrega del pedido.
     * @return tiempo estimado de entrega del pedido.
     */

    @Override
    public int calcularTiempoEntrega() {
       return ((int) Math.round(20 + (1.5 * getDistanciaKm())));
    }

    public synchronized EstadoPedido getEstado(){
        return null;
    }

    //Metodo que registra el pedido
    @Override
    public void registrar() {
        System.out.println("\nRegistrando Pedido Encomienda...");
    }

    //Metodo que cancela el pedido
    @Override
    public void cancelar() {
        System.out.println("\nCancelando Pedido Encomienda...");
        cambiarEstado(EstadoPedido.CANCELADO);
    }

    //Metodo que despacha el pedido
    @Override
    public void despachar() {
        System.out.println("\nDespachando Pedido Encomienda...");
    }
}

