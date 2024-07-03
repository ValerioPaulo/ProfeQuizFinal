package com.valerio.android.aplicacionprofequiz.Vista.modelo;

import java.io.Serializable;

public class Top  implements Serializable {
    //Esta clase representa la estructura de datos generada por el JSON
    private String cod_prof;
    private String nombre_completo;
    private String correo_prof;
    private String curso_prof;
    private String calificacion;

    public String getCod_prof() {
        return cod_prof;
    }

    public void setCod_prof(String cod_prof) {
        this.cod_prof = cod_prof;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCorreo_prof() {
        return correo_prof;
    }

    public void setCorreo_prof(String correo_prof) {
        this.correo_prof = correo_prof;
    }

    public String getCurso_prof() {
        return curso_prof;
    }

    public void setCurso_prof(String curso_prof) {
        this.curso_prof = curso_prof;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}