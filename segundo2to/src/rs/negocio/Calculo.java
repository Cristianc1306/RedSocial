package rs.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Set;
import java.util.TreeMap;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.PositionalList;
import net.datastructures.Vertex;
import rs.controlador.Coordinador;
import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.util.Calendario;

/**
 * Clase que realiza los disintos calculos para efectuar las consultas requeridas
 */
public class Calculo implements Observer {

	final static Logger logger = Logger.getLogger(Calculo.class);
	private Graph<Usuario, Relacion> redSocial;
	private TreeMap<String, Vertex<Usuario>> vertices;
	private List<Relacion> arcos;
	private Coordinador coordinador;
	private Subject subject;
	private boolean actualizar;

/** Constructor calculo
 * 
 * @param subject
 * @param usuarios
 * @param relaciones
 * 
 * inicia el grafo que contiene la red social, cargando en él los vertices (usuarios) y arcos (relaciones)
 */

	public Calculo(Subject subject, List<Usuario> usuarios, List<Relacion> relaciones) {
		logger.debug("Cargando grafo");

		this.subject = subject;
		this.subject.attach(this);
		this.actualizar = false;

		redSocial = new AdjacencyMapGraph<>(false);

		// Cargar usuarios
		vertices = new TreeMap<String, Vertex<Usuario>>();

		for (Usuario u : usuarios)
			vertices.put(u.getId(), redSocial.insertVertex(u));

		// Cargar relaciones
		arcos = relaciones;

		for (Relacion rel : arcos)
			redSocial.insertEdge(vertices.get(rel.getUsuario1().getId()), vertices.get(rel.getUsuario2().getId()), rel);

	}

	/** grafo
	 * 
	 * @return grafo de red social
	 */
	public Graph<Usuario, Relacion> getRedSocial() {
		return redSocial;
	}

	// Consultas

	/** amigos de un usuario
	 * 
	 * @param usuario
	 * @return Muestra un listado de los amigos de un usuario
	 */
	public List<Usuario> amigosDe(Usuario u) {
		realizarCalculo();
		List<Usuario> amigos = new ArrayList<Usuario>();
		Vertex<Usuario> v = vertices.get(u.getId());

		for (Edge<Relacion> r : redSocial.incomingEdges(v)) {
			if (u.equals(r.getElement().getUsuario1())) {
				amigos.add(r.getElement().getUsuario2());
			} else {
				amigos.add(r.getElement().getUsuario1());
			}
		}
		return amigos;
	}

	/** tiempo de amistad entre dos usuarios
	 * 
	 * @param usuario 1
	 * @param usuario 2
	 * @return tiempo de amistad entre usuario1 y usuario2
	 */
	public Relacion relacionDeAmistad(Usuario u1, Usuario u2) {
		realizarCalculo();
		Edge<Relacion> arcoAmistad = redSocial.getEdge(vertices.get(u1.getId()), vertices.get(u2.getId()));
		if (arcoAmistad == null)
			return null;

		return arcoAmistad.getElement();
	}

	/** usuarios más influyentes
	 * 
	 * @return Listado de los usuarios en orden del más influyente al menos
	 */
	public List<Usuario> masInfluyentes() {
		realizarCalculo();
		List<Usuario> influyentes = new ArrayList<Usuario>(redSocial.numVertices());
		List<Usuario> l;
		TreeMap<Integer, List<Usuario>> mapInf = new TreeMap<>();

		for (Entry<String, Vertex<Usuario>> vert : vertices.entrySet()) {
			if ((l = mapInf.get(redSocial.outDegree(vert.getValue()))) != null)
				l.add(vert.getValue().getElement());
			else {
				l = new ArrayList<>();
				l.add(0, vert.getValue().getElement());
				mapInf.put(redSocial.outDegree(vert.getValue()), l);
			}
		}

		for (List<Usuario> list : mapInf.values())
			for (Usuario usuario : list)
				influyentes.add(0, usuario);

		return influyentes;
	}

	/** Usuario más influyente
	 * 
	 * @return usuario más influyente
	 */
	public Usuario masInfluyente() {
		
		realizarCalculo();
		Usuario influyente = vertices.firstEntry().getValue().getElement();
		for (Vertex<Usuario> v : vertices.values()) {
			if (this.cantidadAmigos(influyente) < this.cantidadAmigos(v.getElement()))
				influyente = v.getElement();
		}
		return influyente;
	}

	/** usuarios que más interactuan
	 * 
	 * @return listado de usuarios que más interactúan en la red
	 */
	public List<Usuario> usuariosQueMasInteractuan() {
		realizarCalculo();
		TreeMap<String, Integer> usuariosIdInteracciones = new TreeMap<String, Integer>();
		for (Vertex<Usuario> usersVertices : redSocial.vertices()) {
			usuariosIdInteracciones.put(usersVertices.getElement().getId(),
					totalInteraccionesUsuarios(usersVertices.getElement()));
		}

		return ordenarUsuariosQueMasInteractuanEntreSi(usuariosIdInteracciones);
	}

	/** total de interacciones de un usuario
	 * 
	 * @param usuario u
	 * @return total de interaccion
	 */
	public int totalInteraccionesUsuarios(Usuario u) {
		realizarCalculo();
		int interacciones = 0;
		for (Edge<Relacion> arcosRelaciones : redSocial.incomingEdges(vertices.get(u.getId()))) {
			interacciones = arcosRelaciones.getElement().getInteraccion() + interacciones;
		}
		return interacciones;
	}

	/** Usuario que más interactúa en las redes sociales
	 * 
	 * @return usuario que más interactúa
	 */
	public Usuario usuarioQueMasIteractuaEnRedes() {
		realizarCalculo();
		Usuario vicio = null;
		int maximoEnRedes = 0;
		for (Vertex<Usuario> verticeVicio : redSocial.vertices()) {
			int tiempoEnRedes = 0;
			for (Edge<Relacion> relacionesDeVicio : redSocial.incomingEdges(verticeVicio)) {
				tiempoEnRedes = relacionesDeVicio.getElement().getInteraccion() + tiempoEnRedes;
			}
			if (maximoEnRedes < tiempoEnRedes) {
				maximoEnRedes = tiempoEnRedes;
				vicio = verticeVicio.getElement();
			}
		}
		return vicio;
	}

	/** Camino más nuevo entre dos usuarios
	 * 
	 * @param id de usuario 1
	 * @param id1 de usuario 2
	 * @return camino más nuevo entre dos usuarios
	 */
	public List<Usuario> caminoMasNuevo(String id, String id1) {
		realizarCalculo();
		PositionalList<Vertex<Usuario>> c = null;
		Graph<Usuario, Integer> g = new AdjacencyMapGraph<>(false);
		TreeMap<String, Vertex<Usuario>> verticesC = new TreeMap<>();

		for (Entry<String, Vertex<Usuario>> usr : vertices.entrySet())
			verticesC.put(usr.getKey(), g.insertVertex(usr.getValue().getElement()));

		for (Relacion rel : arcos)
			g.insertEdge(verticesC.get(rel.getUsuario1().getId()), verticesC.get(rel.getUsuario2().getId()),
					Calendario.getTiempo(rel.getFechaAmistad()));
		try {
			c = GraphAlgorithms.shortestPathList(g, verticesC.get(id), verticesC.get(id1));
		} catch (IllegalArgumentException e) {
			logger.error("no existe un camino entre " + id + " y " + id1);
			return null;
		}

		List<Usuario> camino = new ArrayList<>(c.size());
		for (Vertex<Usuario> v : c)
			camino.add(v.getElement());

		return camino;

	}
	/**Sugerencias de nueva amistad
	 * 
	 * @param usuario 
	 * @return lista de usuarios que sugiere como nueva amistad
	 */
	public List<Usuario> sugerenciaNuevaAmistad(Usuario usuario) {
		realizarCalculo();
		List<Usuario> sugerenciasNuevasAmistad = new ArrayList<Usuario>();
		Vertex<Usuario> verticeUsuario = vertices.get(usuario.getId());
		List<Vertex<Usuario>> sugeridos = new ArrayList<Vertex<Usuario>>();
		int amigosNecesarios = 2;
		sugeridos = noSonAmigosDe(usuario);
		for (Vertex<Usuario> verticeSugerido : sugeridos) {
			int amigosEnComun = 0;
			for (Edge<Relacion> relacionesDeSugeridos : redSocial.incomingEdges(verticeSugerido)) {
				for (Edge<Relacion> relacionesDeUsuario : redSocial.incomingEdges(verticeUsuario)) {
					Vertex<Usuario> v1 = redSocial.endVertices(relacionesDeSugeridos)[0];
					Vertex<Usuario> v2 = redSocial.endVertices(relacionesDeSugeridos)[1];
					Vertex<Usuario> v3 = redSocial.endVertices(relacionesDeUsuario)[0];
					Vertex<Usuario> v4 = redSocial.endVertices(relacionesDeUsuario)[1];
					if ((v3.equals(v1)) || (v3.equals(v2)) || (v4.equals(v1)) || (v4.equals(v2))) {
						amigosEnComun++;
					}
				}
			}
			if (amigosEnComun >= amigosNecesarios)
				sugerenciasNuevasAmistad.add(verticeSugerido.getElement());
		}
		if (sugerenciasNuevasAmistad.size() == 0) {
			for (int i = 0; i < sugeridos.size(); i++) {
				Usuario u = masInfluyentes().get(i);
				Vertex<Usuario> v = vertices.get(u.getId());
				if (sugeridos.contains(v)) {
					sugerenciasNuevasAmistad.add(u);
				}
			}
		}
		return sugerenciasNuevasAmistad;
	}

	/** Devuelve el grado promedio 
	 * 
	 * @return grado promedio
	 */
	public double gradoPromedio() {
		realizarCalculo();
		if (redSocial.numEdges() == 0)
			return redSocial.numEdges();

		double a = redSocial.numEdges() * 2;
		double v = redSocial.numVertices();
		return a / v;
	}

	/** Cantidad de amigos de un usuario */
	public int cantidadAmigos(Usuario u) {
		realizarCalculo();
		Vertex<Usuario> v = vertices.get(u.getId());
		return redSocial.inDegree(v);
	}

	// metodos privados

	 
	private List<Vertex<Usuario>> noSonAmigosDe(Usuario usuario) {
		List<Vertex<Usuario>> sugeridos = new ArrayList<Vertex<Usuario>>();
		for (Vertex<Usuario> v : redSocial.vertices()) {
			if (!v.getElement().equals(usuario)) {
				if (!amigosDe(usuario).contains(v.getElement())) {
					sugeridos.add(v);
				}
			}
		}
		return sugeridos;
	}

	/** ordena los usuarios por interacciones
	 * 
	 * @param arbolUsuarioInteracciones
	 * @return usuarios ordenados por interacciones
	 */
	private List<Usuario> ordenarUsuariosQueMasInteractuanEntreSi(TreeMap<String, Integer> arbolUsuarioInteracciones) {
		List<Usuario> usuariosOredenadosPorInteracciones = new ArrayList<Usuario>();
		Integer data[] = new Integer[arbolUsuarioInteracciones.size()];
		int l = 0;
		// carga valores de interacciones en un array
		for (Integer valores : arbolUsuarioInteracciones.values()) {
			data[l] = valores;
			l++;
		}
		// ordena el array de mayor a menor por lo tanto los valores de las
		// interacciones
		int aux;
		for (int i = 0; i < data.length; i++) {
			for (int j = i + 1; j < data.length; j++) {
				if (data[i] < data[j]) {
					aux = data[i];
					data[i] = data[j];
					data[j] = aux;
				}
			}
		}
		// utilizando el treeMap carga en una lista ordenada por cantidad de
		// interacciones
		Entry<String, Integer> entrada;
		Set<Entry<String, Integer>> entradas = arbolUsuarioInteracciones.entrySet();
		Iterator<Entry<String, Integer>> iterator = entradas.iterator();
		for (int k = 0; k < data.length; k++) {
			boolean listado = false;
			entradas = arbolUsuarioInteracciones.entrySet();
			iterator = entradas.iterator();
			while (iterator.hasNext() && !listado) {
				entrada = iterator.next();
				if (data[k].equals(entrada.getValue())) {
					usuariosOredenadosPorInteracciones.add(vertices.get(entrada.getKey()).getElement());
					listado = true;
					arbolUsuarioInteracciones.remove(entrada.getKey());
				}
			}
		}
		return usuariosOredenadosPorInteracciones;
	}

	/** recargar el grafo
	 * 
	 * @param usuarios
	 * @param relaciones
	 */
	private void volverCargarGrafo(List<Usuario> usuarios, List<Relacion> relaciones) {
		redSocial = new AdjacencyMapGraph<>(false);
		vertices.clear();
		for (Usuario u : usuarios)
			vertices.put(u.getId(), redSocial.insertVertex(u));
		arcos = relaciones;
		for (Relacion r : arcos)
			redSocial.insertEdge(vertices.get(r.getUsuario1().getId()), vertices.get(r.getUsuario2().getId()), r);
	}

	/** vuelve a cargar el grafo si fue actualizado
	 * 
	 */
	private void realizarCalculo() {
     
		if (actualizar) {
			volverCargarGrafo(coordinador.listaUsuarios(), coordinador.listaRelaciones());
			actualizar = false;
			logger.info("Se actualizaron los datos para realizar calculos");
		} else
			logger.info("No se actualizaron los datos");
	}

	/**
	 * establece coordinador
	 * @param coordinador
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	/**
	 * actualizar
	 */
	@Override
	public void update() {
		actualizar = true;
	}

}
