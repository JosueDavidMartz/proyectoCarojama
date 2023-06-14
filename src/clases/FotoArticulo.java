/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author WIN 10
 */
public class FotoArticulo {
    private int idFotoArticulo;
    private Image fotoArticulo;
    private int idArticulo;

    public FotoArticulo(){
        this.idFotoArticulo = 0;
        this.fotoArticulo = null;
        this.idArticulo = 0;
    }    
    
    public FotoArticulo(int idFotoArticulo, Image fotoArticulo, int idArticulo) {
        this.idFotoArticulo = idFotoArticulo;
        this.fotoArticulo = fotoArticulo;
        this.idArticulo = idArticulo;
    }

    public int getIdFotoArticulo() {
        return idFotoArticulo;
    }

    public void setIdFotoArticulo(int idFotoArticulo) {
        this.idFotoArticulo = idFotoArticulo;
    }

    public Image getFotoArticulo() {
        return fotoArticulo;
    }

    public void setFotoArticulo(Image fotoArticulo) {
        this.fotoArticulo = fotoArticulo;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }
    
    
}
