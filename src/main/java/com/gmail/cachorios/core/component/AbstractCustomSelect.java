package com.gmail.cachorios.core.ui.component;


import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;



public abstract class AbstractCustomSelect<T extends EntidadInterface> extends AbstractCompositeField<Div, AbstractCustomSelect<T>, T> {
    //private IVisualizable padre;
    private T valor, itemAdd;
    private String titulo;
    private Button find = new Button();
    private Button ver = new Button();
    private Button del = new Button();
    private Label lblCaption = new Label();
    private TextField codigo;
    private TextField descripcion = new TextField();
    private boolean conBuscar, conVer, conAdd;
    private FilterablePageableDataProvider<T, String> dataProvider;

    private String anchoCodigo = "15%";

    public AbstractCustomSelect(String caption, boolean conBuscar, boolean conVer, boolean conAdd) {
        super(null);

        lblCaption.setText(caption);
        lblCaption.setWidth("100%");
        codigo = new TextField();

        this.conBuscar = conBuscar;
        this.conVer = conVer;
        this.conAdd = conAdd;
        ///this.padre = padre;
        this.titulo = caption;
    }

    @Override
    protected Div initContent() {
        Div content = new Div();

        Div layout = new Div();
        layout.setWidth("100%");

        lblCaption.setClassName("lbl-caption");

        content.add(lblCaption, layout);

        codigo.setValueChangeMode(ValueChangeMode.ON_BLUR);
        //setValueChangeTimeout(Constantes.SYS_TEXTCHANGEDELAY);
        codigo.addValueChangeListener(e -> onCambioValorManual(e.getValue()));
        codigo.addClassName("codigo");
        codigo.addFocusListener(e -> setearShortcuts());
        codigo.setEnabled(false);
        codigo.setWidth(anchoCodigo);

        descripcion.setEnabled(false);

        descripcion.setWidth("59%");

        layout.add(codigo,descripcion);

        if (conBuscar) {
            layout.add( completarBuscar(find), completarBorrar(del) );
            codigo.setEnabled(true);
        }

        if (conVer) {
            layout.add(completarVer(ver));
        }

        return content;
    }



    public AbstractCustomSelect setWidth(String w){
        getContent().setWidth(w);
        return this;
    }

    private void avisarAlPadre() {
        //if (getPadre() instanceof IPresentableForm) {
     ///       ((IPresentableForm) getPadre()).marcarUnsavedChanges();
    //    }
    }

    protected Button completarBuscar(Button find) {
        ///find.getElement().setProperty("title", C.CRUD_MSG_BUSCAR);
        find.setIcon(VaadinIcon.SEARCH.create());
        find.addClickListener(e -> {
            try {
                buscarElemento();
            } catch (Exception ex) {
//
//                Sistema.getSistema()
//                       .mostrarMensaje(ENivelAplicacion.ERROR, Constantes.MSJ_ERR_CS_CANTSHOW_SELECT, ex.getMessage());
            }

        });
        find.addFocusListener(e -> setearShortcuts());
        return find;
    }

    protected Button completarBorrar(Button del) {
        ///del.getElement().setProperty("title", C.CRUD_MSG_LIMPIAR);
        del.setIcon(VaadinIcon.ERASER.create());
        del.addClickListener(e -> limpiar());
        del.addFocusListener(e -> setearShortcuts());
        return del;
    }

    protected Button completarVer(Button ver) {
        ///ver.getElement().setProperty("title", C.CRUD_MSG_VER);
        ver.setIcon(VaadinIcon.EYE.create());
        ver.addClickListener(e -> {
            try {
                if (getValue() != null) {
                    verElemento();
                } else {
//                    Sistema.getSistema()
//                           .mostrarMensaje(ENivelAplicacion.ERROR, Constantes.MSJ_ERR_CS_NOITEM, titulo.toUpperCase());
                }
            } catch (Exception ex) {
//                Sistema.getSistema()
//                       .mostrarMensaje(ENivelAplicacion.ERROR, Constantes.MSJ_ERR_CS_CANTSHOW_ITEM, ex.getMessage());
            }
        });
        ver.addFocusListener(e -> setearShortcuts());
        return ver;
    }

    protected String getCodigo() {
        try {
            return valor.getId().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    protected String getDescripcion() {
        return valor.toString();  ///toSimpleString();
    }

    public void buscarElemento() {
        ///WinCustomSelect winCustomSelect = new WinCustomSelect<>(getListTitulo(), getDataProvider(), getListaCols(), this);
        ///winCustomSelect.open();
    }

//    protected IPresentableForm<T> getFormAgregar() { return null; }

    protected void onCambioValorManual(String valor) {
        try {
            if (valor != null && valor != "") {
                T val = getElemento(valor);
                if (val != null) {
                    setValue(val);
                    this.setModelValue(val,true);
                    return;
                }
            }
        } catch (Exception ex) {
//            Sistema.getSistema()
//                   .mostrarMensaje(ENivelAplicacion.ERROR, Constantes.MSJ_ERR_CS_CANTSHOW_ITEM, C.MSJ_ERR_CS_NOITEM);
            descripcion.setValue("Valor invalido...");
            codigo.setValue(getCodigo());
        }
    }

    protected abstract T getElemento(String codigo);

    protected abstract void verElemento();

    protected abstract String getListTitulo();

    //protected abstract List<ColumnList> getListaCols();

    protected abstract FilterablePageableDataProvider<T,  String> generarDataProvider();

    private void setearShortcuts() {
        ////
        /*
        find.setClickShortcut(ShortcutAction.KeyCode.F2);
        ver.setClickShortcut(ShortcutAction.KeyCode.F3);
        del.setClickShortcut(ShortcutAction.KeyCode.F6);
        */
    }

    public void setEditable(boolean editable) {
        if (conBuscar) {
            find.setEnabled(editable);
            codigo.setEnabled(editable);
        } else {
            find.setEnabled(false);
            codigo.setEnabled(false);
        }
        ver.setEnabled(true);
    }


    public FilterablePageableDataProvider<T, String> getDataProvider() {
        if (dataProvider == null) {
            dataProvider = generarDataProvider();
        }
        return dataProvider;
    }

//    public IVisualizable getPadre() {
//        return padre;
//    }

    public boolean conAdd() { return conAdd; }

    public T getItemAdd() { return itemAdd; }

    public void setItemAdd(T itemAdd) { this.itemAdd = itemAdd; }

    public void limpiar() {
        setValue(null);
        codigo.clear();
        descripcion.clear();
        codigo.focus();
    }

    public void setValue(T t) {
        valor = t;

        if (valor != null) {
            codigo.setValue(getCodigo());
            descripcion.setValue(getDescripcion());
            ver.focus();
        }

        del.setEnabled(valor != null);
    }

    @Override
    public T getValue() {
        return valor;
    }

    @Override
    protected void setPresentationValue(T t) {
        initContent();
        setModelValue(valor, true);
    }
}