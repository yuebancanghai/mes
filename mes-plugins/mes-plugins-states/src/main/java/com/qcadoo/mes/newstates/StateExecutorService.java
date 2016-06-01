package com.qcadoo.mes.newstates;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.qcadoo.mes.states.StateChangeEntityDescriber;
import com.qcadoo.mes.states.StateEnum;
import com.qcadoo.mes.states.service.client.StateChangeViewClientUtil;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.api.components.GridComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class StateExecutorService {

    @Autowired
    private ApplicationContext applicationContext;

    @Transactional
    public <M extends StateService> void changeState(Class<M> serviceMarker, final ViewDefinitionState view, String[] args) {
        Optional<GridComponent> maybeGridComponent = view.tryFindComponentByReference("grid");
        if (maybeGridComponent.isPresent()) {
            maybeGridComponent.get().getSelectedEntities().forEach(entity -> {
                changeState(serviceMarker, entity, args[0]);
            });
            
        } else {
            Optional<FormComponent> maybeForm = view.tryFindComponentByReference("form");
            if (maybeForm.isPresent()) {
                FormComponent formComponent = maybeForm.get();
                Entity entity = formComponent.getPersistedEntityWithIncludedFormValues();
                changeState(serviceMarker, entity, args[0]);
                formComponent.setEntity(entity);
            }
        }
    }

    @Transactional
    public <M extends StateService> Entity changeState(Class<M> serviceMarker, Entity entity, String targetState) {
        Map<String, M> stateServices = applicationContext.getBeansOfType(serviceMarker);
        System.out.println(stateServices);

        List<M> services = Lists.newArrayList(stateServices.values());
        AnnotationAwareOrderComparator.sort(services);

        StateChangeEntityDescriber describer = services.stream().findFirst().get().getChangeEntityDescriber();

        String sourceState = entity.getStringField(describer.getSourceStateFieldName());

        if (!canChangeState(describer, entity, targetState)) {
            return entity;
        }

        System.out.println(services);
        if (!hookOnValidate(entity, services, sourceState, targetState)) {
            // TODO tu zwracamy błądne encję, czy wyjątek o niepowodzeniu?
            return entity;
        }
        changeState(entity, targetState);

        if (!hookOnBeforeSave(entity, services, sourceState, targetState)) {
            return entity;
        }
        entity = entity.getDataDefinition().save(entity);

        if (!hookOnAfterSave(entity, services, sourceState, targetState)) {
            // TODO tu trzeba wycofać transakcję i cofnąć zmiany na bazie
            // wyjątek? ręcznie?
        }

        return entity;
    }

    private <M extends StateService> boolean canChangeState(StateChangeEntityDescriber describer, Entity owner,
            String targetStateString) {
        final StateEnum sourceState = describer.parseStateEnum(owner.getStringField(describer.getOwnerStateFieldName()));
        final StateEnum targetState = describer.parseStateEnum(targetStateString);
        // TODO wrzucamy błąd do encji?
        if (sourceState != null && !sourceState.canChangeTo(targetState)) {
            return false;
        }
        return true;
    }

    private <M extends StateService> Collection<M> orderServices(Collection<M> values) {
        // TODO kolejność na podstawie StateConfig.class
        values.forEach(v -> System.out.println(v.getClass().getAnnotation(StateConfig.class).priority()));
        return values;
    }

    private <M extends StateService> boolean hookOnValidate(Entity entity, Collection<M> services, String sourceState, String targetState) {
        for (StateService service : services) {
            entity = service.onValidate(entity, sourceState, targetState);
        }

        return entity.isValid();
    }

    private void changeState(Entity entity, String targetState) {
        // TODO zawsze stan będzie w tym polu?
        entity.setField("state", targetState);
    }

    private <M extends Object & StateService> boolean hookOnBeforeSave(Entity entity, Collection<M> services, String sourceState, String targetState) {
        for (StateService service : services) {
            entity = service.onBeforeSave(entity, sourceState, targetState);
        }

        return entity.isValid();
    }

    private <M extends Object & StateService> boolean hookOnAfterSave(Entity entity, Collection<M> services, String sourceState, String targetState) {
        for (StateService service : services) {
            entity = service.onAfterSave(entity, sourceState, targetState);
        }

        return entity.isValid();
    }

    // private void invokeHook(String hook, Object[] arguments, Collection<? extends ServiceMarker> services) {
    // try {
    // MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
    //
    // for (ServiceMarker service : services) {
    // methodInvokingFactoryBean.setArguments(arguments);
    // methodInvokingFactoryBean.setTargetObject(service);
    // methodInvokingFactoryBean.setTargetMethod(hook);
    // }
    //
    // methodInvokingFactoryBean.prepare();
    // methodInvokingFactoryBean.invoke();
    // } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
    // throw new RuntimeException(ex);
    // }
    // }
}
