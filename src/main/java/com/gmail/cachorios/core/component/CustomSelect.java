package com.gmail.cachorios.core.ui.component;

import com.gmail.cachorios.app.ApplicationContextProvider;

import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.textfield.TextField;


import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;


public class CustomSelect <T extends EntidadInterface> extends AbstractCompositeField<Div, CustomSelect<T>, T> implements HasSize {

    private Label lblCaption;
    private TextField input = new TextField();
    private TextField leyenda = new TextField();
    private Button btnFind, btnShow, btnClear;

    private T valor;

    private Div content = new Div();

    private FilterableAbmService<T> service;
    private Function<T, String> obtenerVarlor;
    private Function<T, String> obtenerLeyenda;
    private BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo;


    public CustomSelect(String label, Class serviceClass ) {
        super(null);
        this.service = (FilterableAbmService<T>) ApplicationContextProvider.getApplicationContext().getBean(serviceClass);

        obtenerVarlor = entidad -> String.valueOf(entidad.getId());
        obtenerLeyenda = entidd -> String.valueOf(entidd.toString());
        consultarCodigo = (service, string) -> service.findById( Long.valueOf(string));

        if(label != null){
            lblCaption = new Label(label);
            lblCaption.setClassName("custom-select-label");
        }

        input.addValueChangeListener(e->changeInput(e.getValue()));
    }

    @Override
    protected Div initContent() {
        Div componente = new Div();

        if(lblCaption != null){
            componente.add(lblCaption);
        }


        setInputWidth("5rem");
        content.add(input);


        return componente;
    }

    public void init(){

        if(btnFind != null){
            content.add(btnFind);
           // content.setFlexGrow(0, btnFind);
        }

        leyenda.setReadOnly(true);
        leyenda.setWidth("100%");
        content.add(leyenda);

        content.setWidth("100%");
//        content.setFlexGrow(0, input);
//        content.setFlexGrow(1, leyenda);
        getContent().add(content);
    }

    public CustomSelect<T> withFind(){
        btnFind = new Button(VaadinIcon.BROWSER.create());
        btnFind.addClickListener(e -> find());
        return this;
    }

    public CustomSelect<T> withShow(){
        btnShow = new Button(VaadinIcon.EYE.create());
        btnShow.addClickListener(e -> show());
       //// getContent().add(btnShow);
        return this;
    }

    public CustomSelect<T> withAdd(){
        return this;
    }

    public CustomSelect<T> withClear(){
        btnClear = new Button(VaadinIcon.ERASER.create());
        btnClear.addClickListener(e -> clearImput());
        getContent().add(btnClear);
        return this;
    }

    public CustomSelect<T> withSetObtenerVarlor(Function<T, String> obtenerVarlor) {
        this.obtenerVarlor = obtenerVarlor;
        return this;
    }
    public CustomSelect<T> withSetObtenerLeyenda(Function<T, String> obtenerLeyenda) {
        this.obtenerLeyenda = obtenerLeyenda;
        return this;
    }
    public CustomSelect<T> withSetConsultarCodigo( BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo) {
        this.consultarCodigo = consultarCodigo;
        return this;
    }

    private void find(){
    }
    private void show(){

    }
    private void clearImput(){
    }

    @Override
    protected void setPresentationValue(T t) {
        input.setValue( obtener(obtenerVarlor) );
        leyenda.setValue(obtener(obtenerLeyenda));
    }

    public String obtener(Function<T,String> function){
        if(function == null){
            return "";
        }

        if(this.valor != null) {
            return (function.apply(this.valor)).toString();
        }
        return "";
    }

    public void changeInput(String valor){
        if(valor.isEmpty()){
            return;
        }
        Optional<T> aux = consultarCodigo.apply(service, valor);
        if(aux.isPresent()) {
            this.valor = aux.get();
            setPresentationValue(aux.get());
        }else{
            input.clear();
            leyenda.setValue("Valor invalido");
            input.focus();
        }
    }
    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setValue(T value) {
        this.valor = value;
        super.setValue(value);

    }

    public void setInputWidth(String s){
        input.setWidth(s);
    }




}
