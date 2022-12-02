package rs.negocio;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.servicio.RelacionService;
import rs.servicio.RelacionServiceImpl;
import rs.servicio.UsuarioService;
import rs.servicio.UsuarioServiceImpl;

public class RedSocial {
	
	final static Logger logger = Logger.getLogger(RedSocial.class);
	private UsuarioService usuarioService;
	private RelacionService relacionService;
	private List<Usuario> usuarios;
	private List<Relacion> relaciones;
	private Subject subject;

	public RedSocial(Subject subject) {
		this.subject=subject;
		usuarios = new ArrayList<>();
		usuarioService = new UsuarioServiceImpl();
		usuarios.addAll(usuarioService.buscarTodos());
		relaciones = new ArrayList<>();
		relacionService = new RelacionServiceImpl();
		relaciones.addAll(relacionService.buscarTodos());
	}

	public void agregarUsuario(Usuario usuario) {
		usuarioService.insertar(usuario);
		usuarios.add(usuario);
		subject.refresh();
		logger.info("Se agrego el usuario "+ usuario.getId()+" al archivo");
	}

	public void agregarRelacion(Relacion relacion) {
		relacionService.insertar(relacion);
		relaciones.add(relacion);
		subject.refresh();
		logger.info("Se agrego la relacion "+ relacion.getUsuario1().getId() 
				+" y "+relacion.getUsuario2().getId()+" al archivo");
	}

	public void modificarUsuario(Usuario usuario) {
		usuarioService.actualizar(usuario);
		usuarios.remove(usuario);
		usuarios.add(usuario);
		subject.refresh();
		logger.info("Se modifico el usuario "+ usuario.getId()+" del archivo");
	}

	public void modificarRelacion(Relacion relacion) {
		relacionService.actualizar(relacion);
		relaciones.remove(relacion);
		relaciones.add(relacion);
		subject.refresh();
		logger.info("Se modifco la relacion "+ relacion.getUsuario1().getId() 
				+" y "+relacion.getUsuario2().getId()+" del archivo");
	}

	public void borrarUsuario(Usuario usuario) {
		usuarioService.borrar(usuario);
		
	    List<Relacion> relacionesBorrar=new ArrayList<>(relaciones);
		for(Relacion r:relacionesBorrar)
			if(r.getUsuario1().equals(usuario)|| r.getUsuario2().equals(usuario))
				borrarRelacion(r);
		
		usuarios.remove(usuario);	
		subject.refresh();
		
		logger.info("Se borro el usuario "+ usuario.getId()+" del archivo");
	}

	public void borrarRelacion(Relacion relacion) {
		relacionService.borrar(relacion);
		relaciones.remove(relacion);
		subject.refresh();
		logger.info("Se borro la relacion "+ relacion.getUsuario1().getId() 
				+" y "+relacion.getUsuario2().getId()+" del archivo");
	}
	public Usuario buscarUsuario( Usuario usuario) {
		int pos = usuarios.indexOf(usuario);
		if (pos == -1)
			return null;
		return usuarios.get(pos);
	}
	public Relacion buscarRelacion( Relacion relacion) {
		int pos = relaciones.indexOf(relacion);
		if (pos == -1)
			return null;
		return relaciones.get(pos);
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public List<Relacion> getRelaciones() {
		return relaciones;
	}


}
