package rs.controlador;

import java.util.List;

import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.negocio.Calculo;
import rs.negocio.RedSocial;
import rs.gui.*;

/**
 * Clase coordinador. Para realizar separación entre la interfaz gráfica y el código del funcionamiento de la aplicación.
 * @author Jaime, César; Camacho, Cristian
 *
 */
public class Coordinador {
	
    private RedSocial redSocial;
	private Calculo calculo;
	private DesktopFrame desktopFrame;
	private UsuariosForm usuariosForm;
	private UsuariosList usuariosList;
	private RelacionesForm relacionesForm;
	private RelacionesList relacionesList;
	private UsuariosListResult usuariosListResult;
	private UsuariosFormConsultas usuariosFormConsultas;


	/**
	 * obtiene la red social
	 * @return red social
 	 */
	public RedSocial getRedSocial() {
		return redSocial;
	}

	/**
	 * establece red social
	 * @param redSocial
	 */
	public void setRedSocial(RedSocial redSocial) {
		this.redSocial = redSocial;
	}

	/**
	 * obtiene calculo
	 * @return calculo
	 */
	public Calculo getCalculo() {
		return calculo;
	}

	/**
	 * establece calculo
	 * @param calculo
	 */
	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	/**
	 * obtiene Desktop Frame
	 * @return desktopFrame
	 */
	public DesktopFrame getDesktopFrame() {
		return desktopFrame;
	}

	/**
	 * establece desktopFrame
	 * @param desktopFrame
	 */
	public void setDesktopFrame(DesktopFrame desktopFrame) {
		this.desktopFrame = desktopFrame;
	}

	/**
	 * obtiene Formulario de usuarios
	 * @return usuariosForm
	 */
	public UsuariosForm getUsuariosForm() {
		return usuariosForm;
	}

	/**
	 * establece formulario de usuarios
	 * @param usuariosForm
	 */
	public void setUsuariosForm(UsuariosForm usuariosForm) {
		this.usuariosForm = usuariosForm;
	}

	/**
	 * obtiene lista de usuarios
	 * @return usuariosList
	 */
	public UsuariosList getUsuariosList() {
		return usuariosList;
	}

	/**
	 * establece lista de usuarios
	 * @param usuariosList
	 */
	public void setUsuariosList(UsuariosList usuariosList) {
		this.usuariosList = usuariosList;
	}

	/**
	 * obtiene formulario de relaciones
	 * @return relacionesForm
	 */
	public RelacionesForm getRelacionesForm() {
		return relacionesForm;
	}

	/**
	 * establece formulario de relaciones
	 * @param relacionesForm
	 */
	public void setRelacionesForm(RelacionesForm relacionesForm) {
		this.relacionesForm = relacionesForm;
	}

	/**
	 * obtiene lista de relaciones
	 * @return relacionesList
	 */
	public RelacionesList getRelacionesList() {
		return relacionesList;
	}

	/**
	 * establece la lista de relaciones
	 * @param relacionesList
	 */
	public void setRelacionesList(RelacionesList relacionesList) {
		this.relacionesList = relacionesList;
	}


	/**
	 * muestra los usuarios en tabla en pantalla
	 */
	// Usuarios
	public void verUsuarios() {
		usuariosList.loadTable();
		usuariosList.setVisible(true);
	}

	/**
	 * muestra ventana para insertar usuarios
	 */
	public void mostrarInsertarUsuario() {
		usuariosForm.accion(Constantes.INSERTAR, null);
		usuariosForm.setVisible(true);
	}

	/**
	 * inserta el usuario 
	 * @param usuario
	 */
	public void insertarUsuario(Usuario usuario) {
	
		redSocial.agregarUsuario(usuario);
		usuariosForm.setVisible(false);
		usuariosList.addRow(usuario);

	}

	/**
	 * muestra ventana para modificar usuarios
	 * @param usuario
	 */
	public void mostrarModificarUsuario(Usuario usuario) {
		usuariosForm.accion(Constantes.MODIFICAR, usuario);
		usuariosForm.setVisible(true);
	}

	/**
	 * modifica el usuario
	 * @param usuario
	 */
	public void modificarUsuario(Usuario usuario) {
		
		redSocial.modificarUsuario(usuario);
		usuariosList.setAccion(Constantes.MODIFICAR);
		usuariosList.setUsuario(usuario);
		usuariosForm.setVisible(false);
	}

	/**
	 * muestra ventana para borrar usuario
	 * @param usuario
	 */
	public void mostrarBorrarUsuario(Usuario usuario) {
		usuariosForm.accion(Constantes.BORRAR, usuario);
		usuariosForm.setVisible(true);
	}

	/**
	 * borra el usuario
	 * @param usuario
	 */
	public void borrarUsuario(Usuario usuario) {
		
		redSocial.borrarUsuario(usuario);
		usuariosList.setAccion(Constantes.BORRAR);
		usuariosForm.setVisible(false);
	}

	/**
	 * cancel la accion y cierra la ventana
	 */
	public void cancelarUsuario() {
		usuariosList.setAccion(Constantes.CANCELAR);
		usuariosForm.setVisible(false);
	}

	/** 
	 * busca en la red un usuario
	 * @param usuario
	 * @return usuario
	 */
	public Usuario buscarUsuario(Usuario usuario) {
		return redSocial.buscarUsuario(usuario);
	}

	/**
	 * listado de los usuarios de la red social
	 * @return usuarios 
	 */
	public List<Usuario> listaUsuarios() {
		return redSocial.getUsuarios();
	}

	// relaciones
	/**
	 * muestra en pantalla tabla de relaciones
	 */
	public void verRelaciones() {
		relacionesList.loadTable();
		relacionesList.setVisible(true);
	}

	/**
	 * muestra ventana para insertar relaciones
	 */
	public void mostrarInsertarRelacion() {
		relacionesForm.accion(Constantes.INSERTAR, null);
		relacionesForm.setVisible(true);
	}

	/**
	 * inserta una relacion
	 * @param relacion
	 */
	public void insertarRelacion(Relacion relacion) {
		redSocial.agregarRelacion(relacion);
		relacionesForm.setVisible(false);
		relacionesList.addRow(relacion);
	}

	/**
	 * muestra ventana para modificar relaciones
	 */
	public void mostrarModifcarRelacion(Relacion relacion) {
		relacionesForm.accion(Constantes.MODIFICAR, relacion);
		relacionesForm.setVisible(true);
	}

	/**
	 * modifica la relacion
	 * @param relacion
	 */
	public void modifcarRelacion(Relacion relacion) {
		
		redSocial.modificarRelacion(relacion);
		relacionesList.setAccion(Constantes.MODIFICAR);
		relacionesList.setRelacion(relacion);
		relacionesForm.setVisible(false);
	}

	/**
	 * muestra ventana para borrar relaciones
	 */
	public void mostrarBorrarRelacion(Relacion relacion) {
		relacionesForm.accion(Constantes.BORRAR, relacion);
		relacionesForm.setVisible(true);
	}

	/**
	 * borra la eliminacion
	 * @param relacion
	 */
	public void borrarRelacion(Relacion relacion) {
		
		redSocial.borrarRelacion(relacion);
		relacionesList.setAccion(Constantes.BORRAR);
		relacionesForm.setVisible(false);
	}

	/**
	 * cancela la accion y cierra la ventana
	 */
	public void cancelarRelacion() {
		relacionesList.setAccion(Constantes.CANCELAR);
		relacionesForm.setVisible(false);
	}

	/**
	 * busca en la red una relacion
	 * @param relacion
	 * @return relacion
	 */
	public Relacion buscarRelacion(Relacion relacion) {
		return redSocial.buscarRelacion(relacion);
	}

	/**
	 * listado de relaciones
	 * @return relaciones
	 */
	public List<Relacion> listaRelaciones() {
		return redSocial.getRelaciones();
	}


	// UsuariosListResult
	/**
	 * muestra en una tabla el listado de usuarios
	 * @param u usuario
	 * @param id1 id de usuario 1
	 * @param id2 id de usuario 2
	 * @param opcion
	 */
	public void losMetodosUsuarioList(Usuario u, String id1, String id2, int opcion) {
		usuariosListResult= new UsuariosListResult();   //abrir varios resultados 
		usuariosListResult.setCoordinador(this);
		usuariosListResult.loadTable(u, id1, id2, opcion);
		usuariosListResult.setVisible(true);
	}

	// UsuariosFormConsultas
	/**
	 * muestra las consultas
	 * @param opcion
	 * @param nombre
	 */
	public void mostrarConsultasUsuarios(int opcion,String nombre) {
		usuariosFormConsultas=new UsuariosFormConsultas();//realizar mismas consultas o diferentes en simultaneo
		usuariosFormConsultas.setCoordinador(this);
		usuariosFormConsultas.accion(opcion,nombre);
		usuariosFormConsultas.setVisible(true);
	}

	/**
	 * cancela la operacion
	 * @param usuariosFormConsultas
	 */
	public void cancelarConsultasUsuarios(UsuariosFormConsultas usuariosFormConsultas) {
		usuariosFormConsultas.setVisible(false);
	}
	
	/**
	 * muestra en pantalla la solución a una consulta
	 * @param usuariosFormConsultas
	 */
	public void realizarConsulta(UsuariosFormConsultas usuariosFormConsultas) {
		usuariosFormConsultas.mostrarConsulta();
	}

	// consultas

	/**
	 * calcula el grado promedio
	 * @return grado promedio
	 */
	public double mostrarGradoPromedio() {
		return calculo.gradoPromedio();
	}

	/**
	 * listado de usuarios más influyentes
	 * @return lista de usuarios ordenados en influencia
	 */
	public List<Usuario> listaInfluyentes() {
		return calculo.masInfluyentes();
	}

	/**
	 * obtiene lista de camino más nuevo entre usuario 1 y usuario 2
	 * @param id1 de usuario
	 * @param id2 de usuario
	 * @return lista de usuarios
	 */
	public List<Usuario> listaCaminoMasNuevo(String id1, String id2) {
		return calculo.caminoMasNuevo(id1, id2);
	}

	/**
	 * muestra la relacion entre usuario 1 y usuario 2
	 * @param u1 usuario1
	 * @param u2 usuario2
	 * @return relacion
	 */
	public Relacion mostrarRelacionDeAmistad(Usuario u1, Usuario u2) {
		return calculo.relacionDeAmistad(u1, u2);
	}

	/**
	 * lista de amigos de usuario
	 * @param usuario
	 * @return lista de usuarios amigos de usuario 
	 */
	public List<Usuario> listaDeAmigos(Usuario u) {
		return calculo.amigosDe(u);
	}

	/**
	 * cantidad de amigos de usuario
	 * @param usuario
	 * @return cantidad de usuarios amigos de usuario
	 */
	public int mostrarCantidadAmigos(Usuario u) {
		return calculo.cantidadAmigos(u);
	}

	/**
	 * obtiene el usuario más influyente
	 * @return usuario más influyente
	 */
	public Usuario mostrarMasInfluyente() {
		return calculo.masInfluyente();
	}

	/**
	 * lista ordenada de usuarios por la cantidad de interacciones en la red social
	 * @return lista de usuarios
	 */
	public List<Usuario> usuarioMasdensos() {
		return calculo.usuariosQueMasInteractuan();
	}

	/**
	 * lista de usuarios sugeridos para tener una relación con el usuario
	 * @param usuario
	 * @return lista de usuarios sugeridos
	 */
	public List<Usuario> mostrarSugerenciasDeAmitad(Usuario u) {
		return calculo.sugerenciaNuevaAmistad(u);
	}

	/**
	 * usuario que más interactúa en la red
	 * @return usuario que más interactua en la red
	 */
	public Usuario mostrarElQueMasInteractua() {
		return calculo.usuarioQueMasIteractuaEnRedes();
	}

	/**
	 * interacciones total de un usuario
	 * @param usuario
	 * @return cantidad de interacciones total de un usuario
	 */
	public int mostrarInteraccionTotal(Usuario usuario) {
		return calculo.totalInteraccionesUsuarios(usuario);
	}

}
