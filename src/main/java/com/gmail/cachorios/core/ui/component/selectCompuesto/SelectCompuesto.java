package com.gmail.cachorios.core.ui.component.selectCompuesto;


import com.gmail.cachorios.app.Context;
import com.gmail.cachorios.core.data.EntidadInterface;
import com.gmail.cachorios.core.data.FilterableAbmService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.internal.AbstractFieldSupport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Tag("select-compuesto")
@JsModule("./src/components/select-compuesto.js")
public class SelectCompuesto<T extends EntidadInterface> extends PolymerTemplate<SelectCompuesto.Model>
        implements HasValueAndElement<AbstractField.ComponentValueChangeEvent<SelectCompuesto<T>, T>, T> {

    public interface Model extends TemplateModel{
        void setLabel(String label);
        void setLeyenda(String leyenda);
        void setWidthInput(String w);

        void setVerDisplay(String d);
        void setFindDisplay(String d);
        void setClearDisplay(String d);

    }

    @Id("input")
    private TextField input;

    private final AbstractFieldSupport<SelectCompuesto<T>,T> fieldSupport;

    private FilterableAbmService<T> service;

    private Function<T, String> obtenerVarlor;
    private Function<T, String> obtenerLeyenda;
    private BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo;



    private Boolean withFind = false;
    private Dialog dialog;
    private  Grid<T> grilla;

    public SelectCompuesto() {
        this.fieldSupport = new AbstractFieldSupport<>(this, null, Objects::deepEquals, c->{});

        obtenerVarlor = entidad -> String.valueOf(entidad.getId());
        obtenerLeyenda = entidad -> String.valueOf(entidad.toString());
        consultarCodigo = (service, string) -> service.findById( Long.valueOf(string));

        input.addValueChangeListener(e->changeInput(e.getValue()));
    }

    public SelectCompuesto(Class serviceClass) {
        this();
        this.service = (FilterableAbmService<T>) Context.getBean(serviceClass);
    }

    public SelectCompuesto(String label, FilterableAbmService service) {
        this();

        this.service = service;
        this.setLabel(label);
    }

    public SelectCompuesto(String label,Class serviceClass){
        this(serviceClass);
        this.setLabel(label);
    }


    public SelectCompuesto<T> setLabel(String label){
        this.getModel().setLabel(label);
        return this;
    }
    public SelectCompuesto<T> setWidthInput(String w){
        getModel().setWidthInput(w);
        return this;
    }

    private SelectCompuesto<T> setLeyenda(String leyenda){
        getModel().setLeyenda(leyenda);
        return this;
    }

    /**
     * Establenet ColSpan
     * @param nCol
     * @return
     */
    public SelectCompuesto<T> setColSpan(Long nCol){
        getElement().setAttribute("colspan", nCol.toString());
        return this;
    }


    @Override
    public void setValue(T value) {
        this.fieldSupport.setValue(value);
        refresh();
    }

    public T CallBack(T value){
        this.setValue(value);
        dialog.close();
        return value;
    }

    @Override
    public T getValue() {
        return fieldSupport.getValue();
    }

    public FilterableAbmService<T> getService() {
        return service;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<SelectCompuesto<T>, T>> valueChangeListener) {
        return fieldSupport.addValueChangeListener(valueChangeListener);
    }


    //-------------------find------------------//
    private SelectCompuesto<T> setFindDisplay(String titulo, String modo){
        getModel().setFindDisplay(modo);
        withFind = !modo.contains("none");
        SCFind<T> contenido = new SCFind<>(service, v -> this.CallBack(v));
        dialog = new Dialog(contenido);
        contenido.setTitulo("");
        if(withFind){
            grilla = contenido.getGrid();
            contenido.setTitulo(titulo);
        }
        dialog.setHeight("60vh");
        dialog.setWidth("650px");
        return this;
    }

    public SelectCompuesto<T> withFind(String titulo){
        setFindDisplay(titulo, "block");
        //// getContent().add(btnShow);
        return this;
    }

    @EventHandler
    private void findClick(){
        dialog.open();
    }

    ///Grilla
    public <V> SelectCompuesto<T> addColumn(final ValueProvider<T,V> columna, final String titulo){
        if(grilla != null) {
            grilla.addColumn(columna).setHeader(titulo);
        }else{
            System.out.println("Grilla no definida!");
        }

        return this;
    }

    //--------------- Show ----------------//
    private SelectCompuesto<T> setVerDisplay(String modo){
        getModel().setVerDisplay(modo);
        return this;
    }

    public SelectCompuesto<T> withVer(){
        setVerDisplay("block");
        //// getContent().add(btnShow);
        return this;
    }
    @EventHandler
    private void verClick(){
        Notification.show("Ver!!!", 1000, Notification.Position.BOTTOM_END);
    }

    //--------------- Add ----------------//
//    private SelectCompuesto<T> setAddDisplay(String modo){
//        getModel().setAddDisplay(modo);
//        return this;
//    }
//    public SelectCompuesto<T> withAdd(){
//        setAddDisplay("block");
//        //// getContent().add(btnShow);
//        return this;
//    }
//    @EventHandler
//    private void addClick(){
//        Notification.show("add!!!", 1000, Notification.Position.BOTTOM_END);
//    }

    //--------------- Clear ----------------//
    private SelectCompuesto<T> setClearDisplay(String modo){
        getModel().setClearDisplay(modo);
        return this;
    }
    public SelectCompuesto<T> withClear(){
        setClearDisplay("block");
        //// getContent().add(btnShow);
        return this;
    }
    @EventHandler
    private void clearClick(){
        Notification.show("clear!!!", 1000, Notification.Position.BOTTOM_END);
        fieldSupport.setValue(null);
        refresh();
    }



    //------------------
    public SelectCompuesto<T> withSetObtenerVarlor(Function<T, String> obtenerVarlor) {
        this.obtenerVarlor = obtenerVarlor;
        return this;
    }
    public SelectCompuesto<T> withSetObtenerLeyenda(Function<T, String> obtenerLeyenda) {
        this.obtenerLeyenda = obtenerLeyenda;
        return this;
    }
    public SelectCompuesto<T> withSetConsultarCodigo( BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo) {
        this.consultarCodigo = consultarCodigo;
        return this;
    }

    public String obtener(Function<T,String> function){
        if(function == null){
            return "";
        }

        if(this.fieldSupport.getValue() != null) {
            return (function.apply(this.fieldSupport.getValue())).toString();
        }
        return "";
    }

    public void changeInput(String valor){
        if(valor.isEmpty()){
            return;
        }
        Optional<T> aux = consultarCodigo.apply(service, valor);
        if(aux.isPresent()) {
            this.setValue(aux.get());
        }else{
            this.setValue(null);
            input.clear();
            setLeyenda("Valor invalido");
            input.focus();
        }
    }

    protected void refresh() {
        input.setValue( obtener(obtenerVarlor) );
        setLeyenda(obtener(obtenerLeyenda));
    }



    private void configureFind(){

    }


}
