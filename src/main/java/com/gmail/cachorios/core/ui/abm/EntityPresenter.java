package com.gmail.cachorios.core.ui.abm;

import com.gmail.cachorios.app.HasLogger;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.core.data.EntidadInterface;
import com.gmail.cachorios.core.data.EntityUtil;
import com.gmail.cachorios.core.data.AbmService;
import com.gmail.cachorios.core.data.UserFriendlyDataException;
import com.gmail.cachorios.core.ui.util.AbmErrorMessage;
import com.gmail.cachorios.core.ui.util.Message;
import com.gmail.cachorios.core.ui.abm.interfaces.EntityView;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.function.UnaryOperator;

public class EntityPresenter<T extends EntidadInterface, V extends EntityView<T>> implements HasLogger {

    private AbmService<T> abmService;
    private Usuario currentUsuario;
    private V view;

    EntityPresenterStatus<T> status = new EntityPresenterStatus<T>();

    public EntityPresenter(AbmService<T> abmService, Usuario currentUsuario) {
        this.abmService = abmService;
        this.currentUsuario = currentUsuario;
    }

    public void setView(V view){
        this.view = view;
    }

    public V getView() {
        return view;
    }
   

    public void delete(AbmOperacionListener<T> onSuccess) {
        Message CONFIRM_DELETE = Message.CONFIRM_DELETE.createMessage();
        confirmarSiNecesitaLuegoEjecutar(true, CONFIRM_DELETE, () -> {
            if (executeOperation(() -> abmService.delete(currentUsuario, status.getEntidad()))) {
                onSuccess.execute(status.getEntidad());
            }
        }, () -> {
        });
    }

    public void save(AbmOperacionListener<T> onSuccess) {
        if (executeOperation(() -> guardarEntidad())) {
            onSuccess.execute(status.getEntidad());
        }
    }

    public boolean executeUpdate(UnaryOperator<T> updater) {
        return executeOperation(() -> {
            status.actualizarEntidad(updater.apply(getEntidad()), esNuevo());
        });
    }

    private boolean executeOperation(Runnable operation) {
        try {
            operation.run();
            return true;
        } catch (UserFriendlyDataException e) {
            // Commit failed because of application-level data constraints
            consumeError(e, e.getMessage(), true);
        } catch (DataIntegrityViolationException e) {
            // Commit failed because of validation errors

            consumeError(e, AbmErrorMessage.OPERATION_PREVENTED_BY_REFERENCES, true);
        } catch (OptimisticLockingFailureException e) {
            consumeError(e, AbmErrorMessage.CONCURRENT_UPDATE, true);
        } catch (EntityNotFoundException e) {
            consumeError(e, AbmErrorMessage.ENTITY_NOT_FOUND, false);
        } catch (ConstraintViolationException e) {
            consumeError(e, AbmErrorMessage.REQUIRED_FIELDS_MISSING, false);
        }
        return false;
    }

    private void consumeError(Exception e, String message, boolean isPersistent) {
        getLogger().debug(message, e);
        view.showError(message, isPersistent);
    }

    private void guardarEntidad() {
        status.actualizarEntidad( abmService.save(currentUsuario, status.getEntidad()), esNuevo());
    }

    
    public boolean writeEntity() {
        try {
            view.write(status.getEntidad());
            return true;
        } catch (ValidationException e) {
            view.showError(AbmErrorMessage.REQUIRED_FIELDS_MISSING, false);
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void close() {
        status.limpiar();
        view.clear();
    }

    public void cancel(Runnable onConfirmed, Runnable onCancelled) {
        confirmarSiNecesitaLuegoEjecutar(view.isDirty(), Message.UNSAVED_CHANGES.createMessage(status.getNombreEntidad()), () -> {
            view.clear();
            onConfirmed.run();
        }, onCancelled);
    }

    private void confirmarSiNecesitaLuegoEjecutar(boolean needsConfirmation, Message message, Runnable onConfirmed,
                                                  Runnable onCancelled) {
        if (needsConfirmation) {
            MostrarPedidoConfirmacion(message, onConfirmed, onCancelled);
        } else {
            onConfirmed.run();
        }
    }

    private void MostrarPedidoConfirmacion(Message message, Runnable onOk, Runnable onCancel) {
        view.getConfirmDialog().setMessage(message.getMessage());
        view.getConfirmDialog().setCaption(message.getCaption());
        view.getConfirmDialog().setCancelText(message.getCancelText());
        view.getConfirmDialog().setOkText(message.getOkText());
        view.getConfirmDialog().setOpened(true);

        final Registration okRegistration = view.getConfirmDialog()
                .addOkClickListener(e -> onOk.run());
        final Registration cancelRegistration = view.getConfirmDialog()
                .addCancelClickListener(e -> onCancel.run());
        status.actualizRegistros(okRegistration, cancelRegistration);
    }

    public boolean cargarEntidad(Long id, AbmOperacionListener<T> onSuccess ){
        return executeOperation(() -> {
            status.actualizarEntidad(abmService.load(id), false);
            onSuccess.execute(status.getEntidad());
        });
    }
    
    public boolean cargarEntidad(T entidad, AbmOperacionListener<T> onSuccess ){
        return executeOperation(() -> {
            status.actualizarEntidad(entidad, false);
            onSuccess.execute(status.getEntidad());
        });
    }

    public T crearNuevo() {
        status.actualizarEntidad(abmService.createNew(currentUsuario), true);
        return status.getEntidad();
    }

    public T getEntidad(){
        return status.getEntidad();
    }

    public boolean esNuevo(){
        return status.esNuevo();
    }

    @FunctionalInterface
    public interface AbmOperacionListener<T> {
        void execute(T entity);
    }
}


/**
 * Mantiene las variable que cambian.
 */
class EntityPresenterStatus<T extends EntidadInterface> {
    private T entidad;
    private String nombreEntidad;
    private Boolean esNuevo;
    private Registration okRegistracion;
    private Registration cancelarRegistracion;

    void actualizarEntidad(T entidad, boolean esNuevo ){
        this.entidad = entidad;
        nombreEntidad = EntityUtil.getName(this.entidad.getClass());
        this.esNuevo = esNuevo;
    }

    void actualizRegistros(Registration okRegistracion, Registration cancelarRegistracion){
        limpiarRegistro(this.okRegistracion);
        limpiarRegistro(this.cancelarRegistracion);
        this.okRegistracion = okRegistracion;
        this.cancelarRegistracion = cancelarRegistracion;
    }

    void limpiar(){
        this.entidad = null;
        this.nombreEntidad = null;
        this.esNuevo = false;
        actualizRegistros(null, null);
    }

    private void limpiarRegistro(Registration registro) {
        if(registro != null) {
            registro.remove();
        }
    }

    public T getEntidad(){
        return entidad;
    }

    public String getNombreEntidad(){
        return nombreEntidad;
    }

    public boolean esNuevo(){
        return esNuevo;
    }

}