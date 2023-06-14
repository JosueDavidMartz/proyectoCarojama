/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casos_uso;

import clases.Categoria;
import clases.Coleccion;
import clases.Elemento;
import clases.Grupo;
import clases.Subcategoria;
import java.sql.Connection;
import java.util.List;
import clases.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author WIN 10
 */
public class HelperCollection {
    private Connection conn;
    PreparedStatement preStatement = null;
    private String[] nombres = new String[500];
    
    public HelperCollection(Connection conn){
        this.conn = conn;
    }
    
    public List<Usuario> Lista(){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try{
            String instSQLS = "SELECT * FROM usuario order by nombre";
            PreparedStatement pints = conn.prepareStatement(instSQLS);
            ResultSet rsUsuarios = pints.executeQuery();
            
            while(rsUsuarios.next()){
                
                int idUsuario = rsUsuarios.getInt("idUsuario");
                String nombre = rsUsuarios.getString("nombre");
                String edad = rsUsuarios.getString("edad");
                String correo = rsUsuarios.getString("correo");
                String descripcion = rsUsuarios.getString("descripcion");

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(idUsuario);
                usuario.setNombre(nombre);
                usuario.setEdad(edad);
                usuario.setCorreo(correo);
                usuario.setDescripcion(descripcion);
                usuarios.add(usuario);
            }
            
        } catch(Exception e){
            System.out.println("error al traer lista de usuario");
        }
        return usuarios;
    }
    
    public boolean Registrar(Usuario usuario){
       
        try{
            String insSQL = "insert into usuario (nombre,edad,correo,descripcion,contraseña) values (?,?,?,?,?)";
            PreparedStatement pinst = conn.prepareCall(insSQL);
            pinst.setString(1, usuario.getNombre());
            pinst.setString(2, usuario.getEdad());
            pinst.setString(3, usuario.getCorreo());
            pinst.setString(4, usuario.getDescripcion());
            pinst.setString(5, usuario.getContraseña());
            pinst.executeUpdate();
            /*int filas = pinst.executeUpdate();
            
            if(filas>=1){
               
                return true;
            }else{
               
                return false;
            }*/
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean AñadirAmigo(String nombre, int idUsuarioActual){
        Usuario nuevoAmigo = new Usuario();
        try{
            String insSQL="SELECT * FROM usuario WHERE nombre=?;";              
            preStatement = conn.prepareStatement(insSQL);                
            preStatement.setString(1,nombre);                              
            ResultSet rs=preStatement.executeQuery();                
            
            String insSQL2="insert into amigos (idAmigo,nombre,edad,correo,descripcion,contraseña,idUsuario) values (?,?,?,?,?,?,?)";              
            PreparedStatement pinst= conn.prepareCall(insSQL2);     
            if(rs.next()){ 
                nuevoAmigo.setIdUsuario(rs.getInt("idUsuario"));
                nuevoAmigo.setNombre(rs.getString("nombre"));
                nuevoAmigo.setEdad(rs.getString("edad"));
                nuevoAmigo.setCorreo(rs.getString("correo"));
                nuevoAmigo.setDescripcion(rs.getString("descripcion"));
                nuevoAmigo.setDescripcion(rs.getString("contraseña"));

                pinst.setInt(1, nuevoAmigo.getIdUsuario());
                pinst.setString(2, nuevoAmigo.getNombre());
                pinst.setString(3, nuevoAmigo.getEdad());
                pinst.setString(4, nuevoAmigo.getCorreo());
                pinst.setString(5, nuevoAmigo.getDescripcion());
                pinst.setString(6, nuevoAmigo.getContraseña());
                pinst.setInt(7, idUsuarioActual);
                pinst.executeUpdate();
                
                System.out.println("entrando 21");
                return true;
            }else{
                JOptionPane.showMessageDialog(null,"No se encontró al usuario para añadirlo como amigo");
            }               
            conn.close();
            }catch(SQLException e){                
                JOptionPane.showMessageDialog(null, "Error de conexión"+e.getMessage());                
            }  
            return false;   
    }
    public void SeguirUsuario(Usuario usuario){
        
    }
    public void EnviarMensaje(Usuario usuario){
        
    }
    
    public int iniciarSesion(String nombre, String contra){
        int idEncontrado = 0;                   
        try{
            String insSQL="SELECT idUsuario FROM usuario WHERE nombre=? and contraseña =?;";              
            preStatement = conn.prepareStatement(insSQL);                
            preStatement.setString(1,nombre);
            preStatement.setString(2,contra);                               
            ResultSet rs=preStatement.executeQuery();                
            if(rs.next()){                     
                idEncontrado=(rs.getInt("idUsuario"));  
                return idEncontrado;
            }else{
                JOptionPane.showMessageDialog(null,"El usuario no existe");
            }               
            conn.close();
            }catch(SQLException e){                
                JOptionPane.showMessageDialog(null, "Error de conexión"+e.getMessage()); //CAMBIAR A UNA ALERTA                 
            }  
            return idEncontrado;        
    }
    
    public Usuario buscarPorId(int id){
        Usuario usuarioEncontrado = new Usuario();
        try {
           
            String instSQLS = "SELECT * FROM usuario WHERE idUsuario=?";
            preStatement = conn.prepareStatement(instSQLS);
            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();
          
            if(rs.next()){
                //Usuario usuarioEncontrado = new Usuario();
                usuarioEncontrado.setNombre(rs.getString("nombre"));
                usuarioEncontrado.setEdad(rs.getString("edad"));
                usuarioEncontrado.setCorreo(rs.getString("correo"));
                usuarioEncontrado.setDescripcion(rs.getString("descripcion"));
                usuarioEncontrado.setIdUsuario(rs.getInt("idUsuario"));
                usuarioEncontrado.setContraseña(rs.getString("contraseña"));                
                return usuarioEncontrado;
            }else{
                System.out.println("Usuario no encontrado");
            }
         
        } catch (SQLException ex) {
            System.out.println("error de consulta");
        }
    
    return usuarioEncontrado;
    }
    
    public Usuario buscarUsuario(String nombre){
            Usuario usuarioEncontrado = new Usuario();
        try {
           
            String instSQLS = "SELECT * FROM usuario WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLS);
            preStatement.setString(1, nombre);
            ResultSet rs = preStatement.executeQuery();
          
            if(rs.next()){
                usuarioEncontrado.setNombre(rs.getString("nombre"));
                usuarioEncontrado.setEdad(rs.getString("edad"));
                usuarioEncontrado.setCorreo(rs.getString("correo"));
                usuarioEncontrado.setDescripcion(rs.getString("descripcion"));
                usuarioEncontrado.setIdUsuario(rs.getInt("idUsuario"));
                usuarioEncontrado.setContraseña(rs.getString("contraseña"));                
                return usuarioEncontrado;
              
            }else{
                 JOptionPane.showMessageDialog(null,"El usuario "+nombre+" no existe");
            }
         
        } catch (SQLException ex) {
            System.out.println("error de consulta");
        }
    
    
        return usuarioEncontrado;
    }
    
    public boolean amigosAgregados(Usuario amigoNuevo, int idFK){    
       
       try{
            String insSQL = "insert into usuario (idAmigo,nombre,edad,correo,descripcion,contraseña,idUsuario) values (?,?,?,?,?,?,?)";
            PreparedStatement pinst = conn.prepareCall(insSQL);
            pinst.setInt(1, amigoNuevo.getIdUsuario());  
            pinst.setString(1, amigoNuevo.getNombre());
            pinst.setString(2, amigoNuevo.getEdad());
            pinst.setString(3, amigoNuevo.getCorreo());
            pinst.setString(4, amigoNuevo.getDescripcion());
            pinst.setString(5, amigoNuevo.getContraseña());
            pinst.setString(1, amigoNuevo.getNombre());
            return true;
           
        }catch(Exception e){
        return false;

        }
    }
    
    public String[] actualizarContactos(int idUsuario){
        try {
            String instSQLS = "SELECT nombre FROM amigos WHERE idUsuario=?";
            preStatement = conn.prepareStatement(instSQLS);
            preStatement.setInt(1, idUsuario);
            ResultSet rs = preStatement.executeQuery();
            int i=0;
            while(rs.next()){              
                    nombres[i]=rs.getString("nombre");
                    i++;
            }         
            System.out.println("Se actualizó correctamente los contactos");
            return nombres;
        } catch (SQLException ex) {
            Logger.getLogger(HelperCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("No se logró actualizar los contactos");
        return nombres;
    }
    
    
    public ArrayList<String> recuperarColecciones(){
        ArrayList <String> colecciones = new ArrayList <String>();
        
        try {
           
            String instSQLS = "SELECT nombre FROM coleccion";
            preStatement = conn.prepareStatement(instSQLS);
            ResultSet rs = preStatement.executeQuery();
          
            while(rs.next()){
                colecciones.add(rs.getString("nombre"));
                  
            }
                 
            if(colecciones.size()==0){
                JOptionPane.showMessageDialog(null,"No se recuperó las colecciones");
            }else{
                System.out.println("Se recuperó la colección correctamente");
            }
         
        } catch (SQLException ex) {
            System.out.println("error de consulta");
        }
        
        return colecciones;
        
    }
    
    public ArrayList<Grupo> obtenerGrupos(String nombreColeccion){
          ArrayList<Grupo> grupos = new ArrayList <Grupo>();
        
          try{
              
            String instSQLSNOMBRECOL = "SELECT idColeccion FROM coleccion WHERE nombre = ?;";
            preStatement = conn.prepareStatement(instSQLSNOMBRECOL);
            preStatement.setString(1, nombreColeccion);
             
            ResultSet rs = preStatement.executeQuery();
              System.out.println("entrando 1");
              
            if(rs.next()){
                System.out.println("el id de la coleccion es"+rs.getInt("idColeccion"));
                
                String instSQLSGRUPOS = "SELECT nombre,descripcion FROM grupo WHERE idColeccion = ?;";
                preStatement = conn.prepareStatement(instSQLSGRUPOS);
                preStatement.setInt(1, rs.getInt("idColeccion"));           
                ResultSet rsGrupo = preStatement.executeQuery();
                
               
                while(rsGrupo.next()){
                    Grupo nuevoGrupo= new Grupo();
                    nuevoGrupo.setNombre(rsGrupo.getString("nombre"));
                    nuevoGrupo.setDescripcion(rsGrupo.getString("descripcion"));
                    grupos.add(nuevoGrupo);
                }
            }
          }catch(SQLException e){
              System.out.println("error");
          }
         
          
          return grupos;
    }
    
    public ArrayList<String> recuperarGrupos(String coleccion){
        ArrayList <String> grupos = new ArrayList <String>();
        Coleccion idColeccion = new Coleccion();
        
        try {
           
            String instSQLScol = "SELECT idColeccion FROM coleccion WHERE nombre = ?;";
            preStatement = conn.prepareStatement(instSQLScol);
            preStatement.setString(1, coleccion);
            
                
            ResultSet rs = preStatement.executeQuery();
            
            if(rs.next()){
                idColeccion.setIdColeccion(rs.getInt("idColeccion"));
            }

            String instSQLS = "SELECT nombre FROM grupo WHERE idColeccion = ?;";
            preStatement = conn.prepareStatement(instSQLS);
            preStatement.setInt(1, idColeccion.getIdColeccion() );
             
            ResultSet rs2 = preStatement.executeQuery();
          
            if(rs2.next()){
                grupos.add(rs2.getString("nombre"));
                System.out.println("Se recuperó el grupo correctamente");
                return grupos;
              
            }else{
                 JOptionPane.showMessageDialog(null,"No se recuperaron los grupos");
            }
         
        } catch (SQLException ex) {
            System.out.println("error de consulta");
        }
        
        return grupos;
        
    }
    
    public int registrarGrupo(String nombreColeccion, Grupo grupoNuevo, ArrayList<String> etiquetasGrupo){
        int resultadoGrupo =0;
        try {
            String instSQLSColecc = "SELECT idColeccion FROM coleccion WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLSColecc);
            preStatement.setString(1, nombreColeccion);
            ResultSet rs = preStatement.executeQuery();
            
            if(rs.next()){
                if(grupoNuevo!=null){
                    String SQLGRUPO = "insert into grupo (nombre, precio, costo, descripcion, idColeccion) values (?,?,?,?,?)";
                    PreparedStatement pinstGrupo = conn.prepareCall(SQLGRUPO);
                    
                    pinstGrupo.setString(1, grupoNuevo.getNombre());
                    pinstGrupo.setFloat(2, grupoNuevo.getPrecio());
                    pinstGrupo.setFloat(3, grupoNuevo.getCosto());
                    pinstGrupo.setString(4, grupoNuevo.getDescripcion());//saber si el grupo etc tambien podrán tener tipo etc          
                    pinstGrupo.setInt(5, rs.getInt("idColeccion"));
                    pinstGrupo.executeUpdate(); 
                }else{
                    System.out.println("No hay articulos que agregar");
                }
                
                if(etiquetasGrupo!=null){
                    String insSQLETIQUETAS = "insert into elementos (nombre, idColeccion, idGrupo) values (?,?,?)";
                    PreparedStatement pinstEtiquetasGrupos = conn.prepareCall(insSQLETIQUETAS);
                    for(int i=0; i<=etiquetasGrupo.size()-1; i++){
                        
                        pinstEtiquetasGrupos.setString(1, etiquetasGrupo.get(i));
                        pinstEtiquetasGrupos.setInt(2, rs.getInt("idColeccion")); 
           
                        try{
                        String instSQLSIDGRUPO = "SELECT idGrupo FROM grupo WHERE nombre=?";
                            preStatement = conn.prepareStatement(instSQLSIDGRUPO);
                            preStatement.setString(1, grupoNuevo.getNombre());
                            ResultSet rsidGrupo = preStatement.executeQuery();            
                            if(rsidGrupo.next()){  
                                  resultadoGrupo = rsidGrupo.getInt("idGrupo");
                                  pinstEtiquetasGrupos.setInt(3, rsidGrupo.getInt("idGrupo")); 
                            }else{
                                System.out.println("No entré al rsidGrupo.next");
                            }
                        pinstEtiquetasGrupos.executeUpdate(); 
                        }catch(SQLException ex){
                            System.out.println("no se recupero el id grupo");
                        }
                    }
                    
                    return resultadoGrupo;                 
                }else{
                    System.out.println("No hay etiquetas para el grupo");
                }
            }         
        } catch (SQLException ex) {
            Logger.getLogger(HelperCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
           return resultadoGrupo;
    }
    
    
    
    
    
    
    
    public boolean registrarColeccion(Coleccion coleccion, ArrayList<Elemento> elementosCol, ArrayList<Grupo> grupo, ArrayList<Elemento> elementosGrupo, ArrayList<Categoria> categoria, ArrayList<Elemento> elementosCate, ArrayList<Subcategoria> subcategoria, ArrayList<Elemento> elementoSub){
            boolean resultado = false;
         try{

            String insSQL = "insert into coleccion (nombre, tipoColeccion, condicionVenta, condicionInter, precio, descripcion) values (?,?,?,?,?,?)";
            PreparedStatement pinst = conn.prepareCall(insSQL);
            pinst.setString(1, coleccion.getNombre());
            pinst.setString(2, coleccion.getTipoColeccion());
            pinst.setString(3, coleccion.getCondicionVenta());
            pinst.setString(4, coleccion.getCondicionInter());
            pinst.setInt(5, coleccion.getPrecio());
            pinst.setString(6, coleccion.getDescripcion());
            pinst.executeUpdate();
     
            System.out.println("se registró la coleccion");
            // sql para recuperar el id de la colección que se acaba de ingresar
            String instSQLS = "SELECT idColeccion FROM coleccion WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLS);
            preStatement.setString(1, coleccion.getNombre());
            ResultSet rs = preStatement.executeQuery();            
            if(rs.next()){                
                if(elementosCol.isEmpty()){
                    System.out.println("No hay elementos que agregar a la coleccion");
                    //return false;
                }else{
                    String insSQL3 = "insert into elementos (nombre, idColeccion) values (?,?)";
                    PreparedStatement pinst3 = conn.prepareCall(insSQL3);
                    for(int i=0; i<=elementosCol.size()-1; i++){                        
                        pinst3.setString(1, elementosCol.get(i).getNombre());
                        pinst3.setInt(2, rs.getInt("idColeccion"));    
                        pinst3.executeUpdate(); 
                    }
                    System.out.println("Se guardaron los elementos de colección");  
                   
                }
            }
            resultado = true;
            
            if(!grupo.isEmpty()){            
                String SQLGRUPO = "insert into grupo (nombre, descripcion, idColeccion) values (?,?,?)";
                PreparedStatement pinstGrupo = conn.prepareCall(SQLGRUPO);
                for(int i=0; i<=grupo.size()-1;i++){
                    pinstGrupo.setString(1, grupo.get(i).getNombre());
                    pinstGrupo.setString(2, grupo.get(i).getDescripcion());//saber si el grupo etc tambien podrán tener tipo etc          
                    pinstGrupo.setInt(3, rs.getInt("idColeccion"));
                    pinstGrupo.executeUpdate();
                }
                                                
                
                if(elementosGrupo.isEmpty()){
                    System.out.println("No hay elementos que agregar al grupo");
                    //return false;
                }else{
                    String insSQLELEMENTOSGRUPO = "insert into elementos (nombre, idColeccion, idGrupo) values (?,?,?)";
                    PreparedStatement pinstElementosGrupo = conn.prepareCall(insSQLELEMENTOSGRUPO);
                    for(int i=0; i<=elementosGrupo.size()-1; i++){                        
                        pinstElementosGrupo.setString(1, elementosGrupo.get(i).getNombre());
                        pinstElementosGrupo.setInt(2, rs.getInt("idColeccion"));   
                        try{
                            String instSQLSIDGRUPO = "SELECT idGrupo, FROM grupo WHERE nombre=?";
                            preStatement = conn.prepareStatement(instSQLSIDGRUPO);
                            preStatement.setString(1, elementosGrupo.get(i).getPerteneceA());
                            ResultSet rsidGrupo = preStatement.executeQuery();            
                            if(rsidGrupo.next()){  
                                  pinstElementosGrupo.setInt(3, rsidGrupo.getInt("idGrupo")); 
                            }
                        }catch(Exception e){
                            System.out.println("No se recuperó el idGrupo para elementos");
                        }
                        pinstElementosGrupo.executeUpdate(); 
                    }
                    System.out.println("Se guardaron los elementos del grupo");  
                  //  return true;
                }
                

                 System.out.println("Se registraron los grupos");
            }
            
            if(!categoria.isEmpty()){   
                String SQLCATEGORIA = "insert into categoria (nombre, descripcion, idColeccion) values (?,?,?)";
                PreparedStatement pinstCategoria = conn.prepareCall(SQLCATEGORIA);
                for(int i=0; i<=categoria.size()-1;i++){
                    pinstCategoria.setString(1, categoria.get(i).getNombre());
                    pinstCategoria.setString(2, categoria.get(i).getDescripcion());//saber si el grupo etc tambien podrán tener tipo etc          
                    pinstCategoria.setInt(3, rs.getInt("idColeccion"));
                    pinstCategoria.executeUpdate();
                }
                
                if(elementosGrupo.isEmpty()){
                    System.out.println("No hay elementos que agregar al grupo");
                   
                }else{
                    String insSQLELEMENTOSCATEGORIA = "insert into elementos (nombre, idColeccion, idCategoria) values (?,?,?)";
                    PreparedStatement pinstElementosCate = conn.prepareCall(insSQLELEMENTOSCATEGORIA);
                    for(int i=0; i<=elementosCate.size()-1; i++){                        
                        pinstElementosCate.setString(1, elementosCate.get(i).getNombre());
                        pinstElementosCate.setInt(2, rs.getInt("idColeccion"));   
                        try{
                            String instSQLSIDCATE = "SELECT idCategoria, FROM categoria WHERE nombre=?";
                            preStatement = conn.prepareStatement(instSQLSIDCATE);
                            preStatement.setString(1, elementosCate.get(i).getPerteneceA()); //El array tiene el nombre de la categoría a la que pertenece pero en la bas de datos no
                            ResultSet rsidCate = preStatement.executeQuery();            
                            if(rsidCate.next()){  
                                  pinstElementosCate.setInt(3, rsidCate.getInt("idCategoria")); 
                            }else{
                                System.out.println("No se encontró el idCategoría de la categoria: "+elementosCate.get(i).getPerteneceA());
                            }
                        }catch(Exception e){
                            System.out.println("No se recuperó el idCategoria para elementos");
                        }
                        pinstElementosCate.executeUpdate(); 
                    }
                    System.out.println("Se guardaron los elementos de la categoria");  
                  //  return true;
                }
                

                 System.out.println("Se registraron las categorías");
            }
                
          
        }catch(Exception e){
             System.out.println("error"+ e);
        }
         
       
        return resultado;
    }
    
    public boolean buscarNombreColeccion(String nombre){
        
        try{
        String instSQLSNombreColeccion = "SELECT nombre FROM coleccion WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLSNombreColeccion);
            preStatement.setString(1, nombre);
            ResultSet rsNombreCol = preStatement.executeQuery();            
            if(rsNombreCol.next()){  
                return true;
            }
        }catch(Exception e){
            System.out.println("No se pudo recuperar los nombres de las colecciones existentes");
        }
        
        return false;
    }
    
    public boolean registrarColeccionNueva(Coleccion coleccion){
       
        try {
            String insSQL = "insert into coleccion (nombre, descripcion) values (?,?)";
            PreparedStatement pinst = conn.prepareCall(insSQL);
            pinst.setString(1, coleccion.getNombre());
            pinst.setString(2, coleccion.getDescripcion());
            pinst.executeUpdate();
            System.out.println("se registró la coleccion");
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(HelperCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("No se pudo regsitrar la nueva colección");
        return false;
    }
    
     public int buscarIdColeccion(String nombre){
        int idColeccion=0;
        try{
        String instSQLSNombreColeccion = "SELECT idColeccion FROM coleccion WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLSNombreColeccion);
            preStatement.setString(1, nombre);
            ResultSet rsIdCol = preStatement.executeQuery();            
            if(rsIdCol.next()){  
                return rsIdCol.getInt("idColeccion");
            }
        }catch(Exception e){
            System.out.println("No se pudo recuperar los nombres de las colecciones existentes");
        }
        
        return idColeccion;
    }
     
      public int buscarIdGrupo(String nombre){
        int idGrupo=0;
        try{
        String instSQLSNombreGrupo = "SELECT idGrupo FROM grupo WHERE nombre=?";
            preStatement = conn.prepareStatement(instSQLSNombreGrupo);
            preStatement.setString(1, nombre);
            ResultSet rsIdGru = preStatement.executeQuery();            
            if(rsIdGru.next()){  
                 idGrupo=rsIdGru.getInt("idGrupo");
            }
        }catch(Exception e){
            System.out.println("No se pudo recuperar los nombres de las colecciones existentes");
        }
        
        return idGrupo;
    }
    
    
    public ArrayList<String> recuperarElementoGrupo(String nombreColeccion, String nombreGrupo){
        ArrayList<String> elementosGrupo = new ArrayList();
      
        try{
            
            int idCol = buscarIdColeccion(nombreColeccion);
            int idGrupo = buscarIdGrupo(nombreGrupo);
            
            
            
            
            String instSQLRECUPERARELEMENTOSGRUPO = "SELECT nombre FROM elementos WHERE idColeccion =? AND idGrupo=?";
            preStatement = conn.prepareStatement(instSQLRECUPERARELEMENTOSGRUPO);
            preStatement.setInt(1, idCol);
            preStatement.setInt(2, idGrupo);
            ResultSet rsNombreElemetos = preStatement.executeQuery();            
            while(rsNombreElemetos.next()){  
                elementosGrupo.add(rsNombreElemetos.getString("nombre"));
            }
            
            return elementosGrupo;
        }catch(SQLException ex){
            System.out.println("No se logra recuperar elementos del grupo");
        }
        
        return elementosGrupo;
    }
    
    
    public void guardarImagenesPortada(String ruta, String coleccion, int grupo){
        try {
            
            int col = buscarIdColeccion(coleccion);
            
            System.out.println("id cole"+col);
           
            
            
            
            FileInputStream is = new FileInputStream(ruta);
            String insSQLFOTO = "insert into fotoarticulo (fotoarticulo,idColeccion,idGrupo) values(?,?,?)";
            PreparedStatement st = conn.prepareCall(insSQLFOTO);
            st.setBlob(1, is);
            st.setInt(2, col);
            st.setInt(3, grupo);
            
            st.execute();

            
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontro el archivo BD");
        } catch (SQLException ex) {
            System.out.println("No se guardó la imagen en la BD");
        }
    }
    
    /*public ImageView mostrarImagenArticulo(String nombreColeccion) throws IOException{
            ImageView imagenV = new ImageView();
            ArrayList <FotoArticulo> lista = new ArrayList();
            try {
                 int idCol = buscarIdColeccion(nombreColeccion);
                 
                
                String insSQLRECUPERARFOTO = "select fotoarticulo from fotoarticulo where idColeccion = ?";
                PreparedStatement pst = conn.prepareStatement(insSQLRECUPERARFOTO);
                pst.setInt(1, idCol);
               
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    
                    
                    

                   Image i = null;
	Blob blob = (Blob) rs.getBlob("Logo");
	i = SwingFXUtils.toFXImage(javax.imageio.ImageIO.read(blob.getBinaryStream()), null);
	//imgFotoEmpresa.setImage(i);
                }
                }catch(SQLException e){
                        
                }
            }

            
                    /*
                    FotoArticulo imagen=new FotoArticulo();
			Blob blob = (Blob) rs.getBlob("imagen");
			String nombre = rs.getObject("nombre").toString();
			byte[] data = blob.getBytes(1, (int)blob.length());
		
                        BufferedImage img = null;
			try {
				img = ImageIO.read(new ByteArrayInputStream(data));
			} catch (IOException ex) {
				
			}
			ImageView imv = new ImageView();
                        
			imagen.setFotoArticulo(img);
			
			lista.add(imagen);
		}
                        rs.close();
                } catch (SQLException ex) {
                       
                }
                return lista;
                /*
                   
                    Blob blob = (Blob) rs.getBlob(2);
                    //pasar el binario a imagen
                    byte[] data = blob.getBytes(1, (int) blob.length());
                    //lee la imagen
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (IOException ex) {
                        Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //ImageView imageView = new ImageView();
                    
                    image = SwingFXUtils.toFXImage(img, null);
                }
                
            } catch (SQLException e) {
                
                System.out.println("Error al cargar foto: ");
            }
            return image;*/
    //}

    public int recuperarCantidadEnGrupos(String nombreGrupo, String nombreColeccion) {
      int can = 0;
      try{
          
          int idGrupo = buscarIdGrupo(nombreGrupo);
          int idColecc = buscarIdColeccion(nombreColeccion);
          
          String insSQLCANTIDAD = "SELECT nombre FROM articulo WHERE idGrupo = ? AND idColeccion = ?";
          
            preStatement = conn.prepareStatement(insSQLCANTIDAD);
            preStatement.setInt(1, idGrupo);
            preStatement.setInt(2, idColecc);
            ResultSet rs = preStatement.executeQuery();            
            while(rs.next()){  
                 can++;
            }
      }catch(SQLException e){
          System.out.println("No se recuperó la cantidad de articulos en los grupos");
      }
      
      
      return can;
        
    }
}
