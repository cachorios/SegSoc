package com.gmail.cachorios.core.ui.component;

import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.vaadin.flow.component.*;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;

import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;


@HtmlImport("frontend://src/components/custom-select.html")
@Tag("custom-select")
public class CSelect<T extends EntidadInterface> extends AbstractSinglePropertyField<CSelect<T>, T>{
        //extends PolymerTemplate<CSelect.Model> { //implements HasElement, HasValue<HasValue.ValueChangeEvent<T>, T> {

    public interface Model extends TemplateModel {

    }
    private T fieldValue;
    private boolean hasChanges = false;

    private Div contenido = new Div();
    @Id("label")
    private Label lblCaption;
    @Id("field")
    private TextField field = new TextField();

    private TextField leyenda = new TextField();
    private Button btnFind, btnShow, btnClear;

    private FilterableAbmService<T> service;
    private Function<T, String> obtenerVarlor;
    private Function<T, String> obtenerLeyenda;
    private BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo;

//    public CSelect(String label, Class serviceClass) {
//
//        this.service = (FilterableAbmService<T>) ApplicationContextProvider.getApplicationContext().getBean(serviceClass);
//
//        obtenerVarlor = entidad -> String.valueOf(entidad.getId());
//        obtenerLeyenda = entidd -> String.valueOf(entidd.toString());
//        consultarCodigo = (service, string) -> service.findById( Long.valueOf(string));
//
//        lblCaption.setText(label);
//
//        field.addValueChangeListener(e->changeInput(e.getValue()));
//
//        initContent();
//    }

    public CSelect(String propertyName, T defaultValue, boolean acceptNullValues, T fieldValue) {
        super(propertyName, defaultValue, acceptNullValues);
        this.fieldValue = fieldValue;
    }

    public void setValue(T valor) {
        if (valor != null){
            fieldValue = valor;
            hasChanges = false;
            setPresentationValue(valor);
        }
    }


    public T getValue() {
        return fieldValue;
    }



    protected void setPresentationValue(T t) {
        field.setValue( obtener(obtenerVarlor) );
        leyenda.setValue(obtener(obtenerLeyenda));
    }

    public String obtener(Function<T,String> function){
        if(function == null){
            return "";
        }

        if(fieldValue != null) {
            return (function.apply(fieldValue)).toString();
        }
        return "";
    }

    private void setHasChanges(boolean hasChanges) {
        this.hasChanges = hasChanges;
        if (hasChanges) {
            ///fireEvent(new com.gmail..views.sfran.events.ValueChangeEvent(this));
        }
    }


    private void initContent(){

       // contenido.getElement().setAttribute("part", "contenido");
//        add(contenido);
        setInputWidth("5rem");

//        leyenda.setReadOnly(true);
//        leyenda.setWidth("100%");
//        contenido.add(field, leyenda);
    }

    public void changeInput(String valor){
        if(valor.isEmpty()){
            return;
        }
        Optional<T> aux = consultarCodigo.apply(service, valor);
        if(aux.isPresent()) {
            fieldValue = aux.get();
        }else{
            field.clear();
            leyenda.setValue("Valor invalido");
            field.focus();
        }

    }

    public CSelect<T> withFind(){
        btnFind = new Button(VaadinIcon.SEARCH.create());
        btnFind.addClickListener(e -> find());

        ///contenido.addComponentAtIndex(1, btnFind);
//        if(lblCaption == null) {
//
//            addComponentAtIndex(1,btnFind);
//        }else{
//            addComponentAtIndex(2,btnFind);
//        }
        return this;
    }

    public CSelect<T> withSetObtenerVarlor(Function<T, String> obtenerVarlor) {
        this.obtenerVarlor = obtenerVarlor;
        return this;
    }
    public CSelect<T> withSetObtenerLeyenda(Function<T, String> obtenerLeyenda) {
        this.obtenerLeyenda = obtenerLeyenda;
        return this;
    }
    public CSelect<T> withSetConsultarCodigo( BiFunction<FilterableAbmService<T>,  String, Optional<T>> consultarCodigo) {
        this.consultarCodigo = consultarCodigo;
        return this;
    }

    private void find(){
    }
    private void show(){

    }
    private void clearImput(){
    }

    public void setInputWidth(String s){
        field.setWidth(s);
    }


    @Override
    public Element getElement() {
        return null;
    }


//
//    @Override
//    public boolean isReadOnly() {
//        return false;
//    }
//
//    @Override
//    public void setRequiredIndicatorVisible(boolean b) {
//
//    }

//    @Override
//    public boolean isRequiredIndicatorVisible() {
//        return false;
//    }
//
//    @Override
//    public Registration addAttachListener(ComponentEventListener<AttachEvent> listener) {
//        return field.addValueChangeListener((ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TextField, String>>) listener);
//    }
}
