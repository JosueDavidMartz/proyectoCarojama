/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carojamaproyecto;

import casos_uso.HelperCollection;
import clases.Coleccion;
import clases.Grupo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author WIN 10
 */
public class Principal extends Pane{
    
    String stColeccionSeleccionada, stGrupoSeleccionado; //guarda temporalmente el nombre de la colección/grupo al que le dan clic
   
    //PORTADA
    int intContador=0;
    Pane pnContenedorPortada;
    Label lbTextoPortada;
    
    //ELEMENTOS DE LA BARRA VERTICAL DERECHA
    BorderPane bpContenedorDelBotonNuevaColeccion; 
    Button btnNuevaColeccion;
    VBox vbContenedorListaDeColecciones; //la lista de la derecha
    BorderPane bpContenedorLogo; 
    

    //ELEMENTOS AL SELECCIONAR UNA COLECCIÓN DE LA LISTA
    VBox vbContenedorDeColeccion;
    HBox hbContenedorDivisorSuperiorColeccion; //inactivo
    HBox hbContenedorDeColeccion;
    TilePane tpIzquierdaSuperiorColeccion;//inactivo
    VBox vbDerechaSuperiorColeccion; //inactivo
    TilePane tpContenedorDeColeccion; 
    VBox vbDerechaColeccion; //inactivo
    Button btnCrearGrupoColeccion;
    
    //ELEMENTOS PARA CREAR UNA NUEVA COLECCIÓN
    Pane pnContenedorNuevaColeccion;
    Button btnAceptarNuevaColeccion, btnCancelarNuevaColeccion;
    TextField tfNombreNuevaColeccion;
    TextArea taDescripcionNuevaColeccion;
    Label lbDirectorioNuevaColeccion; //La ruta de navegabilidad />/>/>
    

    //BARRAS DE NAVEGACIÓN Y MENÚS
    HBox hbContenedorMenuBarSegunda; 
    MenuBar mbBarraMenuSegunda; 
    Menu mnExportar, mnImportar, mnSeleccionar, mnCrearNuevo, mnBuscar; 
    HBox hbContenedorMenuBarPrimera; 
    MenuBar mbBarraMenuPrimera;
    
    
    
   // VBox vbCrearGrupo;
    //HBox hbContenedorGrupoSuperior;
    
    //arraylist
     //ArrayList<Label> coleccionesListados = new ArrayList();
   // Label labelSeleccionado;
    //int contador=0;    
    //ArrayList<Label> arrayB = new ArrayList();
    
   // HBox nuevoh = new HBox();
    
    
    
    //CONTENEDOR PARA CREAR NUEVO GRUPO
    
    HBox hbContenedorCrearNuevoGrupo;
    VBox vbDerechaCrearNuevoGrupo, vbIzqCrearNuevoGrupo;
    HBox hbContendorSuperiorDetallesCrearNuevoGrupo;
    VBox vbContenedorInferiorAtributosCrearNuevoGrupo;
    ScrollPane spContenedorTilePaneCrearNuevoGrupo;
    TextField tfNombreCrearNuevoGrupo,tfCostoCrearNuevoGrupo,tfPrecioCrearNuevoGrupo;
    TextArea taDescripiciónCrearNuevoGrupo;
    Label lbEtiquetaAlertaCrearNuevoGrupo;
    ArrayList<String> arrayLStAtributosCrearNuevoGrupo = new ArrayList();    
    TilePane tpContenedorAtributosCrearNuevoGrupo;
    TextField tfNombreAtributoCrearNuevoGrupo;
    String stRutaArchivoFotoCrearNuevoGrupo="";
    //CONTENEDOR VERTICAL DERECHA DEL CONTENEDOR CREAR NUEVO GRUPO
    TilePane tpContenedorMisOtrosGrupos;
    GridPane gpContenedorMisOtrosGrupos;
    
    //CONTENEDOR DEL GRUPO SELECCIONADO
    TilePane tpContenedorGrupoSeleccionado;
    Button btnAgregarArticuloGrupoSeleccionado,btnCrearLoteGrupoSeleccionado;
    
    public Principal(){
        //PORTADA
        Image imgPortada = new Image(getClass().getResourceAsStream("/imagenes/smooth-ivy.jpg"));
        ImageView imgVPortada = new ImageView(imgPortada);
        imgVPortada.setFitWidth(850);
        imgVPortada.setFitHeight(550);
        
        pnContenedorPortada = new Pane();
        pnContenedorPortada.setLayoutX(0);
        pnContenedorPortada.setLayoutY(0);
        pnContenedorPortada.setMinSize(850 , 550);
             
        lbTextoPortada = new Label("SISTEMA DE COLECCIONES CAROJAMA");
        lbTextoPortada.setLayoutX(100);
        lbTextoPortada.setLayoutY(190);
        lbTextoPortada.setTextFill(Color.WHITE);
 
        pnContenedorPortada.getChildren().addAll(imgVPortada, lbTextoPortada);
   
        //Lista de colecciones
        vbContenedorListaDeColecciones = new VBox(3);
        
        ScrollPane spContenedorListaDeColecciones = new ScrollPane(); 
        spContenedorListaDeColecciones.setHbarPolicy(ScrollBarPolicy.NEVER);
        spContenedorListaDeColecciones.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        spContenedorListaDeColecciones.setLayoutX(850);
        spContenedorListaDeColecciones.setLayoutY(110);
        spContenedorListaDeColecciones.setMinSize(230, 290);
        spContenedorListaDeColecciones.setMaxSize(230, 290);
        spContenedorListaDeColecciones.setPadding(new Insets(5));
        spContenedorListaDeColecciones.setContent(vbContenedorListaDeColecciones);
        
        
        //Contenedor de colecciones
        tpContenedorDeColeccion = new TilePane(Orientation.HORIZONTAL); //Muestra las miniaturas de grupos que hay en la coleccion
        tpContenedorDeColeccion.setAlignment(Pos.BASELINE_LEFT);
        tpContenedorDeColeccion.setPrefColumns(5);
        tpContenedorDeColeccion.setMinWidth(734); 
        ScrollPane spContenedorDeColeccion;
        // tpContenedorDeColeccion.setMinSize(600, 400);
        //tpIzquierdaInferiorColeccion.setMaxSize(520, 400);
        
        
        
        //Contenedor de nueva colección
        pnContenedorNuevaColeccion = new Pane();
        
        //Elementos de las barras de menú
        hbContenedorMenuBarPrimera = new HBox();
        hbContenedorMenuBarPrimera.setLayoutX(0);
        hbContenedorMenuBarPrimera.setLayoutY(0);
        hbContenedorMenuBarPrimera.setMinWidth(850);
        hbContenedorMenuBarPrimera.setStyle("-fx-background-color: #8C8C93");
        
        mbBarraMenuPrimera = new MenuBar();
        mbBarraMenuPrimera = new MenuBar();
        mbBarraMenuPrimera.setLayoutX(0);
        mbBarraMenuPrimera.setLayoutY(0);
        mbBarraMenuPrimera.setStyle("-fx-background-color: #8C8C93");
        
        Menu exportarInicial = new Menu("Exportar");
        Menu importarInicial = new Menu("Importar");
        //mbBarraMenuPrimera.getMenus().addAll(exportarInicial, importarInicial);
        
        hbContenedorMenuBarPrimera.getChildren().add(mbBarraMenuPrimera);
       
        mbBarraMenuSegunda = new MenuBar(); 
        mbBarraMenuSegunda.setLayoutX(0);
        mbBarraMenuSegunda.setLayoutY(0);
        mbBarraMenuSegunda.setStyle("-fx-background-color: #8C8C93");
        mnExportar = new Menu("Exportar");
        mnImportar = new Menu("Importar");
        mnSeleccionar = new Menu("Seleccionar");
        mnCrearNuevo= new Menu("Crear Nuevo");
        MenuItem crearGrupo = new MenuItem("Crear grupo");
        MenuItem crearCategoria = new MenuItem("Crear categoría");
        MenuItem crearSucategoria = new MenuItem("Crear subcategoría");
        mnBuscar = new Menu("Buscar"); //******************************************************************* Implementar
        mnCrearNuevo.getItems().addAll(crearGrupo, crearCategoria, crearSucategoria);
        //mbBarraMenuSegunda.getMenus().addAll(mnExportar, mnImportar, mnSeleccionar, mnCrearNuevo, mnBuscar);
        
        hbContenedorMenuBarSegunda = new HBox(5); 
        hbContenedorMenuBarSegunda.setMinWidth(850);
        hbContenedorMenuBarSegunda.setStyle("-fx-background-color: #8C8C93");
        hbContenedorMenuBarSegunda.getChildren().add(mbBarraMenuSegunda);

        //hbContenedorMenuBarSegunda.getChildren().add();
       
        

        //Contenedor de cada colección
        vbContenedorDeColeccion = new VBox();
        vbContenedorDeColeccion.setMinSize(850, 525);
        vbContenedorDeColeccion.setMaxSize(850, 525);
        vbContenedorDeColeccion.setLayoutY(25);
        vbContenedorDeColeccion.setStyle("-fx-background-color: #587180");
           /* hbContenedorDivisorSuperiorColeccion = new HBox();
                tpIzquierdaSuperiorColeccion = new TilePane();
                tpIzquierdaSuperiorColeccion.setOrientation(Orientation.HORIZONTAL);

                tpIzquierdaSuperiorColeccion.setPadding(new Insets(20));
                tpIzquierdaSuperiorColeccion.setVgap(15);
                tpIzquierdaSuperiorColeccion.setHgap(35);

                for(int i=0; i<=100; i++){
                    tpIzquierdaSuperiorColeccion.getChildren().add(new Button(" LOTES "));
                }
                ScrollPane spIzquierdaSupColección = new ScrollPane(tpIzquierdaSuperiorColeccion);
                spIzquierdaSupColección.setHbarPolicy(ScrollBarPolicy.NEVER);
                spIzquierdaSupColección.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
                spIzquierdaSupColección.setMinHeight(232);hbContenedorDivisorSuperiorColeccion.setHgrow(spIzquierdaSupColección, Priority.ALWAYS);
                vbDerechaSuperiorColeccion = new VBox();
                vbDerechaSuperiorColeccion.setMinWidth(120);
                    Button btnCrearLoteGrupo = new Button("+Coleccion");
                    vbDerechaSuperiorColeccion.getChildren().add(btnCrearLoteGrupo);
                vbDerechaSuperiorColeccion.setPadding(new Insets(20));
            hbContenedorDivisorSuperiorColeccion.setHgrow(spIzquierdaSupColección, Priority.ALWAYS);
            hbContenedorDivisorSuperiorColeccion.getChildren().addAll(spIzquierdaSupColección, vbDerechaSuperiorColeccion);*/
//VBox inferior       
            hbContenedorDeColeccion = new HBox();
            
                tpContenedorDeColeccion.setOrientation(Orientation.HORIZONTAL);
                tpContenedorDeColeccion.setPadding(new Insets(10));
                tpContenedorDeColeccion.setVgap(5);
                tpContenedorDeColeccion.setHgap(10);             
                spContenedorDeColeccion = new ScrollPane(tpContenedorDeColeccion);
                spContenedorDeColeccion.setHbarPolicy(ScrollBarPolicy.NEVER);
                spContenedorDeColeccion.setVbarPolicy(ScrollBarPolicy.ALWAYS);
                spContenedorDeColeccion.setMinHeight(525);
        
               /* vbDerechaColeccion = new VBox();
                vbDerechaColeccion.setPadding(new Insets(20));
                vbDerechaColeccion.setMinWidth(120);*/
                    btnCrearGrupoColeccion = new Button("Nuevo articulo"); //Se cambia la seccion de crear grupos por articulos
                    btnCrearGrupoColeccion.setVisible(false);
                    btnCrearGrupoColeccion.setLayoutX(725);
                    btnCrearGrupoColeccion.setLayoutY(460);
                    btnCrearGrupoColeccion.setOnAction(evento ->{
                        tpContenedorMisOtrosGrupos.getChildren().clear();
                        componenteMisOtrosGrupos(stGrupoSeleccionado);
                        hbContenedorCrearNuevoGrupo.setVisible(true);
                        pnContenedorNuevaColeccion.setVisible(false);
                        tpContenedorDeColeccion.setVisible(false); //tilepane
                        pnContenedorPortada.setVisible(false);
                        hbContenedorMenuBarSegunda.setVisible(false); //la de colección
                        hbContenedorMenuBarPrimera.setVisible(true);
                        vbContenedorDeColeccion.setVisible(false);
                         btnCrearGrupoColeccion.setVisible(false);


                    });
                    //vbDerechaColeccion.getChildren().addAll(btnCrearGrupoColeccion);
        
            hbContenedorDeColeccion.setHgrow(spContenedorDeColeccion, Priority.ALWAYS);
            //ELIMINAMOS EL vbDerechaColeccion PERO GUARDAMOS EL BOTÓN DE NUEVO GRUPO <<<<<<<<<<<<<<<<<<<<<<<<<<
            hbContenedorDeColeccion.getChildren().addAll(spContenedorDeColeccion);
   
        //vbContenedorPrincipalColeccion.setVgrow(hbContenedorDeColeccion, Priority.ALWAYS);
        //vbContenedorPrincipalColeccion.setVgrow(hbContenedorDivisorSuperiorColeccion, Priority.ALWAYS);
        
        vbContenedorDeColeccion.getChildren().addAll(hbContenedorDeColeccion);
 
        
        //Elementos de la barra vertical derecha con logo
        Label tituloColecciones = new Label("MIS COLECCIONES");
        tituloColecciones.setPadding(new Insets(3));
        tituloColecciones.setLayoutX(60);
        tituloColecciones.setLayoutY(20);        
        Image imgLogo = new Image(getClass().getResourceAsStream("/imagenes/carojama.png"));        
        ImageView imgViewLogo = new ImageView(imgLogo);
        imgViewLogo.setFitWidth(230);
        imgViewLogo.setFitHeight(60);              
        TextField tfBuscarFiltrarLista = new TextField();
        tfBuscarFiltrarLista.setMinWidth(179);
        Button btnBuscarLista = new Button("Buscar");
        btnBuscarLista.setOnAction(evento ->{         //Se activa el metodo que permite buscar en la lista las de colecciones
            clicBuscarListaDeColeccion();
        });
        HBox hbContenedorInferiorBuscadorLista = new HBox(); 
        bpContenedorLogo = new BorderPane();
        bpContenedorLogo.setLayoutX(850);
        bpContenedorLogo.setLayoutY(0);
        bpContenedorLogo.setMinSize(230, 110); 
        bpContenedorLogo.setAlignment(imgViewLogo, Pos.CENTER);
        bpContenedorLogo.setCenter(imgViewLogo);
        bpContenedorLogo.setAlignment(hbContenedorInferiorBuscadorLista, Pos.CENTER);      
        hbContenedorInferiorBuscadorLista.getChildren().addAll(tfBuscarFiltrarLista, btnBuscarLista);
        bpContenedorLogo.setBottom(hbContenedorInferiorBuscadorLista);
        
        
        //Elementos para crear nueva colección
        pnContenedorNuevaColeccion = new Pane();
        pnContenedorNuevaColeccion.setLayoutX(0);

        pnContenedorNuevaColeccion.setMinSize(850,550);
        pnContenedorNuevaColeccion.setStyle("-fx-background-color: white");
        
        btnAceptarNuevaColeccion = new Button("Crear");
        btnCancelarNuevaColeccion = new Button("Cancelar");
        btnAceptarNuevaColeccion.setLayoutX(760);
        btnAceptarNuevaColeccion.setLayoutY(500);
        btnAceptarNuevaColeccion.setMinWidth(70);
        btnAceptarNuevaColeccion.setOnMouseClicked(evento ->{
            if(tfNombreNuevaColeccion.getText().isEmpty()){
                mostrarAlertError(null, "Es necesario asignar nombre a la colección");
            }
            boolean ban = false;
                
            HelperCollection usuarioHelper_ = new HelperCollection(getConnection());
            ban=usuarioHelper_.buscarNombreColeccion(tfNombreNuevaColeccion.getText());
            if(ban==true){
                mostrarAlertError(null, "Ya existe una colección con el mismo nombre");
            }     
            else{
                Optional<ButtonType> action;
                action = alertaConfirmacion("Confirmación", "¿Guardar nueva colección "+tfNombreNuevaColeccion.getText()+"?");
                if (action.get() == ButtonType.OK) {
                    HelperCollection usuarioHelper = new HelperCollection(getConnection());
                    Coleccion nuevaColeccion = new Coleccion();                       
                    nuevaColeccion.setNombre(tfNombreNuevaColeccion.getText());                       
                    nuevaColeccion.setDescripcion(taDescripcionNuevaColeccion.getText());
                    boolean res=usuarioHelper.registrarColeccionNueva(nuevaColeccion);
                    if(res){
                        mostrarAlertInfo(null, "Colección creada", "Se guardó correctamente");
                        tfNombreNuevaColeccion.clear();
                        taDescripcionNuevaColeccion.clear(); 
                        vbContenedorListaDeColecciones.getChildren().clear();                   
                        recuperarListaColecciones();         
                    }else{  
                        mostrarAlertError(null, "Tuvimos problemas para crear tu colección, comunicate con soporte");
                    }
                }
            }                
        });
        btnCancelarNuevaColeccion.setLayoutX(680);
        btnCancelarNuevaColeccion.setLayoutY(500);
        btnCancelarNuevaColeccion.setMinWidth(70);
        btnCancelarNuevaColeccion.setOnMouseClicked(evento ->{
            pnContenedorPortada.setVisible(true);
            pnContenedorNuevaColeccion.setVisible(false);
            tpContenedorDeColeccion.setVisible(true);
            taDescripcionNuevaColeccion.setText(null);
            tfNombreNuevaColeccion.setText(null);
            
        });
        tfNombreNuevaColeccion = new TextField();
        tfNombreNuevaColeccion.setLayoutX(300);
        tfNombreNuevaColeccion.setLayoutY(100);
        tfNombreNuevaColeccion.setMinWidth(250);
        taDescripcionNuevaColeccion = new TextArea();
        taDescripcionNuevaColeccion.setLayoutX(270);
        taDescripcionNuevaColeccion.setLayoutY(170);
        taDescripcionNuevaColeccion.setMaxSize(310, 100);
        taDescripcionNuevaColeccion.setPromptText("*** palabras...");
        lbDirectorioNuevaColeccion = new Label("Nueva colección...");
        lbDirectorioNuevaColeccion.setLayoutX(20);
        lbDirectorioNuevaColeccion.setLayoutY(50);
        
        pnContenedorNuevaColeccion.getChildren().addAll(btnAceptarNuevaColeccion, btnCancelarNuevaColeccion, tfNombreNuevaColeccion, taDescripcionNuevaColeccion, lbDirectorioNuevaColeccion);
        
        //Contenedor el botón "nueva coleccion"
        bpContenedorDelBotonNuevaColeccion = new BorderPane();
        bpContenedorDelBotonNuevaColeccion.setLayoutX(850);
        bpContenedorDelBotonNuevaColeccion.setLayoutY(400);
        bpContenedorDelBotonNuevaColeccion.setMinSize(230, 150);
        bpContenedorDelBotonNuevaColeccion.setStyle("-fx-background-color: gray");
        btnNuevaColeccion = new Button("Crear colección");
        btnNuevaColeccion.setLayoutX(35);
        btnNuevaColeccion.setLayoutY(15);
        btnNuevaColeccion.setMinWidth(158);
        btnNuevaColeccion.setMinHeight(70);
        btnNuevaColeccion.setAlignment(Pos.CENTER);
       
        bpContenedorDelBotonNuevaColeccion.setCenter(btnNuevaColeccion);
        
        
        btnNuevaColeccion.setOnMouseClicked(evento ->{
            pnContenedorNuevaColeccion.setVisible(true);
            tpContenedorDeColeccion.setVisible(false); //tilepane
            pnContenedorPortada.setVisible(false);
            hbContenedorMenuBarSegunda.setVisible(false); //la de colección
            hbContenedorMenuBarPrimera.setVisible(true);
            vbContenedorDeColeccion.setVisible(true);
            btnCrearGrupoColeccion.setVisible(false);
            tpContenedorGrupoSeleccionado.setVisible(false);
            btnCrearLoteGrupoSeleccionado.setVisible(false);
            btnAgregarArticuloGrupoSeleccionado.setVisible(false);
        });
             

        
        //Contenedor grupo nuevo
        
        hbContenedorCrearNuevoGrupo = new HBox();
        
        hbContenedorCrearNuevoGrupo.setMinSize(850, 525);
        hbContenedorCrearNuevoGrupo.setMaxSize(850, 525);
        hbContenedorCrearNuevoGrupo.setLayoutY(25);
      
        
        vbDerechaCrearNuevoGrupo = new VBox();
        vbDerechaCrearNuevoGrupo.setMaxHeight(525);
        vbDerechaCrearNuevoGrupo.setMinWidth(200);
        vbDerechaCrearNuevoGrupo.setMaxWidth(200);
        vbDerechaCrearNuevoGrupo.setPadding(new Insets(10));
        vbDerechaCrearNuevoGrupo.setAlignment(Pos.TOP_CENTER);
        
        Label lbMisOtrosGruposCrearNuevoGrupo = new Label("Articulos existentes");
        lbMisOtrosGruposCrearNuevoGrupo.setPadding(new Insets(10));
        
        TextField tfOtrosGrupos = new TextField();       
        Button btnOtrosGrupos = new Button("<");
        HBox hbMisOtrosGruposCrearNuevoGrupo = new HBox();
        hbMisOtrosGruposCrearNuevoGrupo.setHgrow(btnOtrosGrupos, Priority.ALWAYS);
        hbMisOtrosGruposCrearNuevoGrupo.getChildren().addAll(btnOtrosGrupos, tfOtrosGrupos);
        tpContenedorMisOtrosGrupos = new TilePane(Orientation.HORIZONTAL);
        tpContenedorMisOtrosGrupos.setAlignment(Pos.CENTER_LEFT);
        tpContenedorMisOtrosGrupos.setVgap(4);
        tpContenedorMisOtrosGrupos.setHgap(4);
        tpContenedorMisOtrosGrupos.setPrefColumns(3);
        
        
        
        
        
        
        
        
        
        
      
       ScrollPane spOtrosGruposVista = new ScrollPane(tpContenedorMisOtrosGrupos);
        spOtrosGruposVista.setMinHeight(210);
        spOtrosGruposVista.setMaxHeight(210);
        
        
        gpContenedorMisOtrosGrupos = new GridPane();
     
        HBox hbOtrosGruposBotones = new HBox(8);
  
        Button btnGuardarGrupoNuevo = new Button("Guardar");
        btnGuardarGrupoNuevo.setOnMouseClicked(evento ->{
            
        Optional<ButtonType> action;
            action = alertaConfirmacion("Confirmación", "¿Guardar nuevo grupo?");
            if (action.get() == ButtonType.OK) {
                    
                HelperCollection collectionHelper = new HelperCollection(getConnection());
                Grupo grupoNuevo = new Grupo();
                grupoNuevo.setNombre(tfNombreCrearNuevoGrupo.getText());
                grupoNuevo.setDescripcion(taDescripiciónCrearNuevoGrupo.getText());
                grupoNuevo.setPrecio(Float.parseFloat(tfPrecioCrearNuevoGrupo.getText()));
                grupoNuevo.setCosto(Float.parseFloat(tfCostoCrearNuevoGrupo.getText()));
                int re = collectionHelper.registrarGrupo(stColeccionSeleccionada, grupoNuevo, arrayLStAtributosCrearNuevoGrupo);
                
                
                if(re!=0){ 
                    mostrarAlertInfo(null, "Guardado", "Se guardó el articulo correctamente");
                    tfNombreCrearNuevoGrupo.clear();
                    taDescripiciónCrearNuevoGrupo.clear();
                    tfPrecioCrearNuevoGrupo.clear();
                    tfCostoCrearNuevoGrupo.clear();
                    tfNombreAtributoCrearNuevoGrupo.clear();
                    tpContenedorAtributosCrearNuevoGrupo.getChildren().clear();
                    arrayLStAtributosCrearNuevoGrupo.clear();
                    
                if(stRutaArchivoFotoCrearNuevoGrupo!=""){
                  
                  collectionHelper.guardarImagenesPortada(stRutaArchivoFotoCrearNuevoGrupo, stColeccionSeleccionada, re);
                 }else{
                        System.out.println("La ruta de la foto es comilla");
                    }
                }   
            }
        });
       
        Button btnCancelarGrupoNuevo = new Button("Cancelar");
        btnCancelarGrupoNuevo.setOnMouseClicked(evento ->{
            Optional<ButtonType> action;
            action = alertaConfirmacion("Cancelar", "¿Desea cancelar el grupo?");
            if(action.get() == ButtonType.OK){
                tfNombreCrearNuevoGrupo.clear();
                tfPrecioCrearNuevoGrupo.clear();
                tfCostoCrearNuevoGrupo.clear();
                taDescripiciónCrearNuevoGrupo.clear();
                tfNombreAtributoCrearNuevoGrupo.clear();
                lbEtiquetaAlertaCrearNuevoGrupo.setText("");
                tpContenedorAtributosCrearNuevoGrupo.getChildren().clear();
                vbContenedorDeColeccion.setVisible(true);
                hbContenedorCrearNuevoGrupo.setVisible(false);
                iniciarPanelColeccion(stColeccionSeleccionada);  
                btnCrearGrupoColeccion.setVisible(true);
            }else{
                
            }
          
        });
        ScrollPane spOtrosGrupos = new ScrollPane(gpContenedorMisOtrosGrupos);
        //spOtrosGrupos.setMinHeight(150);
       // spOtrosGrupos.setMinHeight(350);
      
       // hbOtrosGruposBotones.setHgrow(btnGuardarGrupoNuevo, Priority.ALWAYS);
        //hbOtrosGruposBotones.setHgrow(btnCancelarGrupoNuevo, Priority.ALWAYS);
        hbOtrosGruposBotones.setAlignment(Pos.CENTER);
        hbOtrosGruposBotones.setPadding(new Insets(20));
       
        hbOtrosGruposBotones.getChildren().addAll(btnGuardarGrupoNuevo, btnCancelarGrupoNuevo);
        //vbDerechaCrearGrupo.setMinHeight(50);
        //spOtrosGruposVista.setMinHeight(190);
        vbDerechaCrearNuevoGrupo.setVgrow(spOtrosGrupos, Priority.ALWAYS);
        vbDerechaCrearNuevoGrupo.getChildren().addAll(lbMisOtrosGruposCrearNuevoGrupo, hbMisOtrosGruposCrearNuevoGrupo,spOtrosGruposVista,spOtrosGrupos, hbOtrosGruposBotones);
        
        vbIzqCrearNuevoGrupo = new VBox();
        
        vbIzqCrearNuevoGrupo.setStyle("-fx-background-color: black");
        hbContendorSuperiorDetallesCrearNuevoGrupo = new HBox(4);
        hbContendorSuperiorDetallesCrearNuevoGrupo.setPadding(new Insets(20));
        hbContendorSuperiorDetallesCrearNuevoGrupo.setMinHeight(180);
        hbContendorSuperiorDetallesCrearNuevoGrupo.setMaxHeight(180);
        hbContendorSuperiorDetallesCrearNuevoGrupo.setStyle("-fx-background-color: white");
       // hbContendorSuperiorDetallesCrearNuevoGrupo.setMinHeight(200);
        //hbSuperiorIzqGrupo.setMaxHeight(200);
        VBox vbSuperiorIzq = new VBox(4);
        tfNombreCrearNuevoGrupo = new TextField();
        tfNombreCrearNuevoGrupo.setPromptText("Nombre del grupo");
        tfNombreCrearNuevoGrupo.setMaxWidth(200);
        HBox hbCosto = new HBox();
        hbCosto.setAlignment(Pos.BOTTOM_LEFT);
        tfCostoCrearNuevoGrupo = new TextField();
        tfCostoCrearNuevoGrupo.setPromptText("$");
        tfCostoCrearNuevoGrupo.setMaxWidth(50);
        Label lbCosto = new Label();

        lbCosto.setText(" Esto me costó");
        hbCosto.getChildren().addAll(tfCostoCrearNuevoGrupo,lbCosto);
        tfPrecioCrearNuevoGrupo = new TextField();
        tfPrecioCrearNuevoGrupo.setPromptText("$");
        tfPrecioCrearNuevoGrupo.setMaxWidth(50);
        HBox hbPrecio = new HBox();
        hbPrecio.setAlignment(Pos.BOTTOM_LEFT);
        Label lbPrecio = new Label();
     
        lbPrecio.setText(" A esto lo venderé");
        hbPrecio.getChildren().addAll(tfPrecioCrearNuevoGrupo,lbPrecio);
        taDescripiciónCrearNuevoGrupo = new TextArea();
        taDescripiciónCrearNuevoGrupo.setPromptText("Descripción...");
     
        vbSuperiorIzq.getChildren().addAll(tfNombreCrearNuevoGrupo,hbCosto,hbPrecio,taDescripiciónCrearNuevoGrupo);
        Label lbPortadaNuevoGrupo = new Label("foto");
        lbPortadaNuevoGrupo.setOnMouseClicked(evento ->{
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                //    new ExtensionFilter("Text Files", "*.txt"),
                    new ExtensionFilter("Image Files", "*.png", "*.jpg"));
                 //   new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                 //   new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
              Image imagen = new Image(selectedFile.toURI().toString());
              
              stRutaArchivoFotoCrearNuevoGrupo = selectedFile.getAbsolutePath();
              ImageView imagenLabel = new ImageView();
              imagenLabel.setFitHeight(150);
              imagenLabel.setFitWidth(150);
              imagenLabel.setImage(imagen);
              lbPortadaNuevoGrupo.setGraphic(imagenLabel);
            }
        });
        lbPortadaNuevoGrupo.setMinSize(150, 150);
        lbPortadaNuevoGrupo.setMaxSize(150,150);
        lbPortadaNuevoGrupo.setStyle("-fx-background-color: #B1EDCC");
        hbContendorSuperiorDetallesCrearNuevoGrupo.setHgrow(lbPortadaNuevoGrupo, Priority.ALWAYS);
        hbContendorSuperiorDetallesCrearNuevoGrupo.getChildren().addAll(lbPortadaNuevoGrupo, vbSuperiorIzq);
        
        
        vbContenedorInferiorAtributosCrearNuevoGrupo = new VBox(4);
        
        lbEtiquetaAlertaCrearNuevoGrupo = new Label();
        vbContenedorInferiorAtributosCrearNuevoGrupo.setPadding(new Insets(20));
        vbContenedorInferiorAtributosCrearNuevoGrupo.setStyle("-fx-background-color: white");
        vbContenedorInferiorAtributosCrearNuevoGrupo.setMinHeight(350);
        HBox hbValorAtributosGrupo = new HBox(4);
        tfNombreAtributoCrearNuevoGrupo = new TextField();
        tfNombreAtributoCrearNuevoGrupo.setPromptText("Nuevo atributo...");
        Button btnNuevoAtributoGrupo = new Button("Agregar");
        btnNuevoAtributoGrupo.setOnMouseClicked(evento ->{
            btnAñadirElementoGrupo();
        });
        hbValorAtributosGrupo.setAlignment(Pos.BOTTOM_LEFT);
        hbValorAtributosGrupo.getChildren().addAll(tfNombreAtributoCrearNuevoGrupo, btnNuevoAtributoGrupo,lbEtiquetaAlertaCrearNuevoGrupo);
        tpContenedorAtributosCrearNuevoGrupo = new TilePane(Orientation.HORIZONTAL);
        
        tpContenedorAtributosCrearNuevoGrupo.setPrefColumns(7);
        tpContenedorAtributosCrearNuevoGrupo.setStyle("-fx-background-color: white");
        //tpAtributosNuevoGrupo.setMinHeight(120);
        //tpAtributosNuevoGrupo.setMaxHeight(120);
       /* for(int i=0; i<=500; i++){
            tpContenedorAtributosCrearNuevoGrupo.getChildren().add(new Button("tilepane"+i));
        }
       */
        
        spContenedorTilePaneCrearNuevoGrupo = new ScrollPane(tpContenedorAtributosCrearNuevoGrupo);
        spContenedorTilePaneCrearNuevoGrupo.setOnMouseReleased(evento ->{
          
                System.out.println("estoy dentro del scroll");
            
        });
        vbContenedorInferiorAtributosCrearNuevoGrupo.getChildren().addAll(hbValorAtributosGrupo, spContenedorTilePaneCrearNuevoGrupo);
       vbContenedorInferiorAtributosCrearNuevoGrupo.setVgrow(spContenedorTilePaneCrearNuevoGrupo, Priority.ALWAYS);
        vbIzqCrearNuevoGrupo.setVgrow(hbContendorSuperiorDetallesCrearNuevoGrupo, Priority.ALWAYS);
      
        vbIzqCrearNuevoGrupo.getChildren().addAll(hbContendorSuperiorDetallesCrearNuevoGrupo, vbContenedorInferiorAtributosCrearNuevoGrupo);

        vbDerechaCrearNuevoGrupo.setStyle("-fx-background-color: white");
       // vbDerechaCrearNuevoGrupo.setBorder(new Border(new BorderStroke(Color.BLACK, 
         //   BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        hbContenedorCrearNuevoGrupo.setHgrow(vbIzqCrearNuevoGrupo, Priority.ALWAYS);
        hbContenedorCrearNuevoGrupo.getChildren().addAll(vbIzqCrearNuevoGrupo,vbDerechaCrearNuevoGrupo);
        
        //CONTENEDOR GRUPO SELECCIONADO
        tpContenedorGrupoSeleccionado = new TilePane();
        tpContenedorGrupoSeleccionado.setVisible(false);
        tpContenedorGrupoSeleccionado.setMinSize(850, 525);
        tpContenedorGrupoSeleccionado.setMaxSize(850, 525);
        tpContenedorGrupoSeleccionado.setLayoutY(25);
        tpContenedorGrupoSeleccionado.setStyle("-fx-background-color: white");
        
        btnAgregarArticuloGrupoSeleccionado = new Button("Agregar articulo");
        btnAgregarArticuloGrupoSeleccionado.setVisible(false);
        btnAgregarArticuloGrupoSeleccionado.setLayoutX(715);
        btnAgregarArticuloGrupoSeleccionado.setLayoutY(460);
        
        btnCrearLoteGrupoSeleccionado = new Button("Crear lote");
        btnCrearLoteGrupoSeleccionado.setVisible(false);
        btnCrearLoteGrupoSeleccionado.setLayoutX(630);
        btnCrearLoteGrupoSeleccionado.setLayoutY(460);
        
        recuperarListaColecciones();
  
        getChildren().addAll(hbContenedorCrearNuevoGrupo, vbContenedorDeColeccion, spContenedorListaDeColecciones, bpContenedorDelBotonNuevaColeccion, pnContenedorNuevaColeccion, bpContenedorLogo,pnContenedorPortada, hbContenedorMenuBarSegunda, hbContenedorMenuBarPrimera,btnCrearGrupoColeccion, tpContenedorGrupoSeleccionado, btnAgregarArticuloGrupoSeleccionado, btnCrearLoteGrupoSeleccionado);

    }
    
        public void iniciarVistas(){
             hbContenedorMenuBarSegunda.setVisible(false);
             pnContenedorNuevaColeccion.setVisible(false);
             hbContenedorCrearNuevoGrupo.setVisible(false);
        }
    
          public void btnAñadirElementoGrupo(){ 
                boolean ban =true;
                if(tfNombreAtributoCrearNuevoGrupo.getText().isEmpty()){
                    lbEtiquetaAlertaCrearNuevoGrupo.setText("Crea un elemento para añadir");
                    System.out.println(tfNombreAtributoCrearNuevoGrupo.getText());
                }else{
                    
                    for(int i=0; i<=arrayLStAtributosCrearNuevoGrupo.size()-1; i++){
                        if(arrayLStAtributosCrearNuevoGrupo.get(i).equals(tfNombreAtributoCrearNuevoGrupo.getText())){
                            lbEtiquetaAlertaCrearNuevoGrupo.setText("Ya existe un elemento con el mismo nombre");
                            ban=false;
                            return;
                        }
                    }
                   // bandera = true;
         
                    if(ban==true){
                                creaComponenteBotonEtiquetaGrupo(tfNombreAtributoCrearNuevoGrupo.getText());
                          
                                arrayLStAtributosCrearNuevoGrupo.add(tfNombreAtributoCrearNuevoGrupo.getText());
                                tfNombreAtributoCrearNuevoGrupo.setText("");
                                lbEtiquetaAlertaCrearNuevoGrupo.setText("");       
                              
                          
                    } 
                    
                }
                
            
            for(int i=0; i<=arrayLStAtributosCrearNuevoGrupo.size()-1; i++){
                System.out.println("valo de etiqueta: "+arrayLStAtributosCrearNuevoGrupo.get(i));
                
            }
            System.out.println("*********************************************");
        }  
          
    public Button creaComponenteBotonEtiquetaGrupo(String nombreKey){
        Button btnEtiquetaGrupo = new Button(nombreKey);
        btnEtiquetaGrupo.setMinSize(50, 30);
        btnEtiquetaGrupo.setId(nombreKey);
        btnEtiquetaGrupo.setOnMouseClicked(evento ->{
            if(evento.getButton()==MouseButton.SECONDARY){
                System.out.println("eliminando");
            }
        });
        tpContenedorAtributosCrearNuevoGrupo.getChildren().add(btnEtiquetaGrupo);
        return btnEtiquetaGrupo;
    }
    
    public Label componenteLabel(String li){
        String key =li;
        Label nombreColeccionLista = new Label(li);
        nombreColeccionLista.setId(key);   
        nombreColeccionLista.setOnMouseClicked(evento ->{
         
            
            if(hbContenedorCrearNuevoGrupo.isVisible()){
                Optional<ButtonType> action;
                action = alertaConfirmacion("Confirmación", "Si te encuentras realizando algun cambio este se perderá al entrar a una colección nueva");
                if (action.get() == ButtonType.OK) {
                    hbContenedorCrearNuevoGrupo.setVisible(false);
                    vbContenedorDeColeccion.setVisible(true);
                    hbContenedorMenuBarSegunda.setVisible(true);
                    hbContenedorMenuBarPrimera.setVisible(false);
                    iniciarPanelColeccion(li);   
                    stColeccionSeleccionada = nombreColeccionLista.getText();
                    btnCrearGrupoColeccion.setVisible(true);
                    
                   
                }
            }else{
                hbContenedorMenuBarSegunda.setVisible(true);
                hbContenedorMenuBarPrimera.setVisible(false);
                iniciarPanelColeccion(li);   
                stColeccionSeleccionada = nombreColeccionLista.getText();
                 btnCrearGrupoColeccion.setVisible(true);
                 tpContenedorGrupoSeleccionado.setVisible(false);
            }
      
        });  
        intContador++;        
        return nombreColeccionLista; 
    }
   
    public void componenteMisOtrosGrupos(String nombre){
        HelperCollection usuarioHelper = new HelperCollection(getConnection());
        ArrayList<Grupo> gruposDeColeccion  = usuarioHelper.obtenerGrupos(stColeccionSeleccionada);      
        if(gruposDeColeccion.size()>0){         
            for(int i=0; i<=gruposDeColeccion.size()-1; i++){               
                VBox nuevoVBox = new VBox();
                nuevoVBox.setMaxWidth(10);
                nuevoVBox.setAlignment(Pos.CENTER);
                nuevoVBox.setStyle("-fx-background-color: white");
                Label[] res = componenteLabelMisOtrosGrupos(gruposDeColeccion.get(i).getNombre());
                //Agregar foto 
                Circle cir2 = new Circle(110,110,28);
               
                Image img = new Image(getClass().getResourceAsStream("/imagenes/monaLisa.jpg"));
                cir2.setFill(new ImagePattern(img));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                nuevoVBox.getChildren().add(cir2);
                nuevoVBox.getChildren().add(res[0]);
                
                tpContenedorMisOtrosGrupos.getChildren().addAll(nuevoVBox);      
            }
        }else{
            System.out.println("No se pudo finalizar inicarPanelColeccion");
        }
    }
    
    public Label[] componenteLabelMisOtrosGrupos(String nombre){ //muestra los label de los otros grupos del panel crear grupo nuevo
        Label[] arreglo = new Label[2];      
        String keyNombre =  nombre;
        Label nombreGrupo = new Label(nombre);
        nombreGrupo.setId(keyNombre);     
        nombreGrupo.setOnMouseClicked(evento ->{
           gpContenedorMisOtrosGrupos.getChildren().clear();
           HelperCollection usuarioHelper = new HelperCollection(getConnection());
           stGrupoSeleccionado = nombre;
           ArrayList<String> atributos = usuarioHelper.recuperarElementoGrupo(stColeccionSeleccionada, stGrupoSeleccionado);
         
           for(int i=0; i<=atributos.size()-1; i++){
             
                gpContenedorMisOtrosGrupos.add(componenteAtributosGrupo(atributos.get(i)), 0, i);   
           }
        });
        arreglo[0]=nombreGrupo;
        
        return arreglo;
    } 
    
    public Button componenteAtributosGrupo(String nombre){ //Crea los label de la lista de atributos de mis otros grupos del panel crear grupo nuevo
        String nombreModif=nombre;
        String firstLtr = nombreModif.substring(0, 1);
        String restLtrs = nombreModif.substring(1, nombreModif.length());
      
        firstLtr = firstLtr.toUpperCase();
        nombreModif = firstLtr + restLtrs;
        
        Button arreglo = new Button();      
        String keyNombre =  nombre;
        Button nombreAtributo = new Button("← "+nombreModif);
        nombreAtributo.setId(keyNombre);     
        
        nombreAtributo.setOnMouseClicked(evento ->{
            boolean ban = true;
            
            for(int i=0; i<=arrayLStAtributosCrearNuevoGrupo.size()-1; i++){
                if(arrayLStAtributosCrearNuevoGrupo.get(i).equals(nombre)){
                    ban=false;
                    lbEtiquetaAlertaCrearNuevoGrupo.setText("Ya existe un elemento con el mismo nombre");     
                }
            }
            if(ban){
            arrayLStAtributosCrearNuevoGrupo.add(nombre);
            tpContenedorAtributosCrearNuevoGrupo.getChildren().add(creaComponenteBotonEtiquetaGrupo(nombre));
            lbEtiquetaAlertaCrearNuevoGrupo.setText("");
            }
            ban=true;
        });
        arreglo=nombreAtributo;
        
        return arreglo;
    } 
    
    public Label[] componenteGrupo(String nombre, String desc, int cantidad){ //Componentes de los grupos que existen en cada coleccion
        Label[] arreglo = new Label[3];      
        String keyNombre =  nombre;
        Label nombreGrupo = new Label(nombre);
        nombreGrupo.setId(keyNombre);     
        nombreGrupo.setOnMouseClicked(evento ->{
            tpContenedorGrupoSeleccionado.setVisible(true);
            btnAgregarArticuloGrupoSeleccionado.setVisible(true);
            btnCrearLoteGrupoSeleccionado.setVisible(true);
        });
        arreglo[0]=nombreGrupo;
        
        String keyCantidad = "cantidad";
        Label cant = new Label(String.valueOf(cantidad));
        cant.setId(keyCantidad);
        arreglo[1]=cant;
        
        
        if(desc.length()>0){
            String keyDesc =  nombre;
            Label descGrupo = new Label(desc);
            descGrupo.setId(keyNombre);
            
            arreglo[2]=descGrupo;
        }
        return arreglo;
    } 
    
    
    public void iniciarPanelColeccion(String li){ //muestra los grupos que hay en cada coleccion
        tpContenedorDeColeccion.getChildren().clear();
        tpContenedorDeColeccion.setVisible(true);
        pnContenedorPortada.setVisible(false);
        pnContenedorNuevaColeccion.setVisible(false);
        HelperCollection usuarioHelper = new HelperCollection(getConnection());
        ArrayList<Grupo> gruposDeColeccion  = usuarioHelper.obtenerGrupos(li);      
        if(gruposDeColeccion.size()>0){
            System.out.println("iniciarPanelColeccion");
            for(int i=0; i<=gruposDeColeccion.size()-1; i++){
                
                
                
                
                
                
                
                //FALTA OBTENER LAS FOTOS DESDE LA BD
                
                
                
                
                
                
                
                
                
                VBox nuevoVBox = new VBox();
                
                Circle cir2 = new Circle(110,110,40);
               
                Image img = new Image(getClass().getResourceAsStream("/imagenes/monaLisa.jpg"));
                cir2.setFill(new ImagePattern(img));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                
                nuevoVBox.setAlignment(Pos.CENTER);
                nuevoVBox.setStyle("-fx-background-color: white");
                int cantidad = usuarioHelper.recuperarCantidadEnGrupos(gruposDeColeccion.get(i).getNombre(), stColeccionSeleccionada);
                Label[] res = componenteGrupo(gruposDeColeccion.get(i).getNombre(), gruposDeColeccion.get(i).getDescripcion(), cantidad);
                
              
                nuevoVBox.getChildren().add(cir2);
                nuevoVBox.getChildren().add(res[0]);
                nuevoVBox.getChildren().add(res[1]);
                nuevoVBox.getChildren().add(res[2]);
                
                tpContenedorDeColeccion.getChildren().addAll(nuevoVBox);
                
                
                
                
            }
        }else{
            System.out.println("No se pudo finalizar inicarPanelColeccion");
        }
    }
      
    public void recuperarListaColecciones(){ 
        HelperCollection usuarioHelper = new HelperCollection(getConnection());
         ArrayList<String> listaColecciones= usuarioHelper.recuperarColecciones();     
         if(listaColecciones.size()>0){
             listaColecciones.forEach(li -> {          
                 vbContenedorListaDeColecciones.getChildren().add(componenteLabel(li));
            });
         }else{
             System.out.println("Se actualizó la lista de colecciónes pero no se encontró ninguna");
         }

    }

    public void clicBuscarListaDeColeccion(){
        HelperCollection usuarioHelper = new HelperCollection(getConnection());
        ArrayList<String> listaColecciones= usuarioHelper.recuperarColecciones();
        if(listaColecciones.size()>0){
            listaColecciones.forEach(li -> {
                vbContenedorListaDeColecciones.getChildren().add(componenteLabel(li));
            });
        }else{
            System.out.println("Se actualizó la lista de colecciónes pero no se encontró ninguna");
        }
    }

    @FXML
    private void clicAddArticulo(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = new NuevoArticulo();
        Scene scene = new Scene(root, 550, 480);
        stage.setTitle("Nuevo Articulo");
        stage.setScene(scene);
        stage.show();    
    }
    
     public Optional alertaConfirmacion(String titulo, String mensaje){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle(titulo);
            alert.setContentText(mensaje);
            Optional<ButtonType> action = alert.showAndWait();
            return action;
    }
    
     private void mostrarAlertError(ActionEvent event, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarAlertInfo(ActionEvent event,String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static Connection getConnection(){ 
         Connection conn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/sistemacarojama?serverTimezone=UTC", "root", "cisco");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }      
}
