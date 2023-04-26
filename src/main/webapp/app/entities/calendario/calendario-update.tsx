import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFacultad } from 'app/shared/model/facultad.model';
import { getEntities as getFacultads } from 'app/entities/facultad/facultad.reducer';
import { ICalendario } from 'app/shared/model/calendario.model';
import { getEntity, updateEntity, createEntity, reset } from './calendario.reducer';

export const CalendarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const facultads = useAppSelector(state => state.facultad.entities);
  const calendarioEntity = useAppSelector(state => state.calendario.entity);
  const loading = useAppSelector(state => state.calendario.loading);
  const updating = useAppSelector(state => state.calendario.updating);
  const updateSuccess = useAppSelector(state => state.calendario.updateSuccess);

  const handleClose = () => {
    navigate('/calendario');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFacultads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.publicacionOferta = convertDateTimeToServer(values.publicacionOferta);
    values.inicioMatriculas = convertDateTimeToServer(values.inicioMatriculas);
    values.finMatriculas = convertDateTimeToServer(values.finMatriculas);
    values.inicioAjustes = convertDateTimeToServer(values.inicioAjustes);
    values.finAjustes = convertDateTimeToServer(values.finAjustes);
    values.inicioClases = convertDateTimeToServer(values.inicioClases);
    values.finClases = convertDateTimeToServer(values.finClases);
    values.inicioExamenesFinales = convertDateTimeToServer(values.inicioExamenesFinales);
    values.finExamenesFinales = convertDateTimeToServer(values.finExamenesFinales);
    values.inicioValidaciones = convertDateTimeToServer(values.inicioValidaciones);
    values.finValidaciones = convertDateTimeToServer(values.finValidaciones);
    values.inicioHabilitaciones = convertDateTimeToServer(values.inicioHabilitaciones);
    values.finHabilitaciones = convertDateTimeToServer(values.finHabilitaciones);
    values.terminacionOficinal = convertDateTimeToServer(values.terminacionOficinal);

    const entity = {
      ...calendarioEntity,
      ...values,
      facultad: facultads.find(it => it.id.toString() === values.facultad.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          publicacionOferta: displayDefaultDateTime(),
          inicioMatriculas: displayDefaultDateTime(),
          finMatriculas: displayDefaultDateTime(),
          inicioAjustes: displayDefaultDateTime(),
          finAjustes: displayDefaultDateTime(),
          inicioClases: displayDefaultDateTime(),
          finClases: displayDefaultDateTime(),
          inicioExamenesFinales: displayDefaultDateTime(),
          finExamenesFinales: displayDefaultDateTime(),
          inicioValidaciones: displayDefaultDateTime(),
          finValidaciones: displayDefaultDateTime(),
          inicioHabilitaciones: displayDefaultDateTime(),
          finHabilitaciones: displayDefaultDateTime(),
          terminacionOficinal: displayDefaultDateTime(),
        }
      : {
          ...calendarioEntity,
          publicacionOferta: convertDateTimeFromServer(calendarioEntity.publicacionOferta),
          inicioMatriculas: convertDateTimeFromServer(calendarioEntity.inicioMatriculas),
          finMatriculas: convertDateTimeFromServer(calendarioEntity.finMatriculas),
          inicioAjustes: convertDateTimeFromServer(calendarioEntity.inicioAjustes),
          finAjustes: convertDateTimeFromServer(calendarioEntity.finAjustes),
          inicioClases: convertDateTimeFromServer(calendarioEntity.inicioClases),
          finClases: convertDateTimeFromServer(calendarioEntity.finClases),
          inicioExamenesFinales: convertDateTimeFromServer(calendarioEntity.inicioExamenesFinales),
          finExamenesFinales: convertDateTimeFromServer(calendarioEntity.finExamenesFinales),
          inicioValidaciones: convertDateTimeFromServer(calendarioEntity.inicioValidaciones),
          finValidaciones: convertDateTimeFromServer(calendarioEntity.finValidaciones),
          inicioHabilitaciones: convertDateTimeFromServer(calendarioEntity.inicioHabilitaciones),
          finHabilitaciones: convertDateTimeFromServer(calendarioEntity.finHabilitaciones),
          terminacionOficinal: convertDateTimeFromServer(calendarioEntity.terminacionOficinal),
          facultad: calendarioEntity?.facultad?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lab3FabricaApp.calendario.home.createOrEditLabel" data-cy="CalendarioCreateUpdateHeading">
            <Translate contentKey="lab3FabricaApp.calendario.home.createOrEditLabel">Create or edit a Calendario</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="calendario-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.semestre')}
                id="calendario-semestre"
                name="semestre"
                data-cy="semestre"
                type="text"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.publicacionOferta')}
                id="calendario-publicacionOferta"
                name="publicacionOferta"
                data-cy="publicacionOferta"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioMatriculas')}
                id="calendario-inicioMatriculas"
                name="inicioMatriculas"
                data-cy="inicioMatriculas"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finMatriculas')}
                id="calendario-finMatriculas"
                name="finMatriculas"
                data-cy="finMatriculas"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioAjustes')}
                id="calendario-inicioAjustes"
                name="inicioAjustes"
                data-cy="inicioAjustes"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finAjustes')}
                id="calendario-finAjustes"
                name="finAjustes"
                data-cy="finAjustes"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioClases')}
                id="calendario-inicioClases"
                name="inicioClases"
                data-cy="inicioClases"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finClases')}
                id="calendario-finClases"
                name="finClases"
                data-cy="finClases"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioExamenesFinales')}
                id="calendario-inicioExamenesFinales"
                name="inicioExamenesFinales"
                data-cy="inicioExamenesFinales"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finExamenesFinales')}
                id="calendario-finExamenesFinales"
                name="finExamenesFinales"
                data-cy="finExamenesFinales"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioValidaciones')}
                id="calendario-inicioValidaciones"
                name="inicioValidaciones"
                data-cy="inicioValidaciones"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finValidaciones')}
                id="calendario-finValidaciones"
                name="finValidaciones"
                data-cy="finValidaciones"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.inicioHabilitaciones')}
                id="calendario-inicioHabilitaciones"
                name="inicioHabilitaciones"
                data-cy="inicioHabilitaciones"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.finHabilitaciones')}
                id="calendario-finHabilitaciones"
                name="finHabilitaciones"
                data-cy="finHabilitaciones"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('lab3FabricaApp.calendario.terminacionOficinal')}
                id="calendario-terminacionOficinal"
                name="terminacionOficinal"
                data-cy="terminacionOficinal"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="calendario-facultad"
                name="facultad"
                data-cy="facultad"
                label={translate('lab3FabricaApp.calendario.facultad')}
                type="select"
              >
                <option value="" key="0" />
                {facultads
                  ? facultads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calendario" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CalendarioUpdate;
