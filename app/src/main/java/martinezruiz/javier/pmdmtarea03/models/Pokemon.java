package martinezruiz.javier.pmdmtarea03.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Cuando traemos datos de la API, para poder crear objetos que sean utilizables por la aplicación,
 * tenemos que asignar un tipo de dato con su valor correspondiente. Esto es lo que hacemos al
 * deserializar información que proviene de la web, convertir datos en objetos. Para este propósito
 * se ha incluido la dependencia de GSON y para que esta dependencia sepa como debe actuar, anotamos
 * los atributos de la clase con @SerializedName y entre los paréntesis, el nombre de la clave que
 * viene en el JSON. Entonces, cuando  la dependencia lea un item del JSON, al encontra la clave 'name'
 * asociará el valor de esa clave al atributo nombre del objeto que cree
 * La anotación @expose indice que se trata de un Json que puede ser serializado o deserializado
 */
public class Pokemon {


    public Pokemon() {

    }

    @SerializedName("name")
    private String nombre;
    @SerializedName("id")
    private int indice;

    @SerializedName("sprites")
    private ImgUrl imgUrl;
    @SerializedName("weight")
    private int peso;

    @SerializedName("height")
    private int altura;

    @SerializedName("types")
    private ArrayList<BigType> types;


    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public ImgUrl getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(ImgUrl imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
//
    public String getNombre() {
        return nombre;
    }
//
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
//
    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }


    public ArrayList<BigType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<BigType> types) {
        this.types = types;
    }

    String testImagen;

    public String getTestImagen() {
        return testImagen;
    }

    public void setTestImagen(String testImagen) {
        this.testImagen = testImagen;
    }

}
