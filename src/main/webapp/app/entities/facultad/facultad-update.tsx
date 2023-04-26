import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDecano } from 'app/shared/model/decano.model';
import { getEntities as getDecanos } from 'app/entities/decano/decano.reducer';
import { IViceDecano } from 'app/shared/model/vice-decano.model';
import { getEntities as getViceDecanos } from 'app/entities/vice-decano/vice-decano.reducer';
import { IFacultad } from 'app/shared/model/facultad.model';
import { getEntity, updateEntity, createEntity, reset } from './facultad.reducer';

export const FacultadUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const decanos = useAppSelector(state => state.decano.entities);
  const viceDecanos = useAppSelector(state => state.viceDecano.entities);
  const facultadEntity = useAppSelector(state => state.facultad.entity);
  const loading = useAppSelector(state => state.facultad.loading);
  const updating = useAppSelector(state => state.facultad.updating);
  const updateSuccess = useAppSelector(state => state.facultad.updateSuccess);

  const handleClose = () => {
    navigate('/facultad');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDecanos({}));
    dispatch(getViceDecanos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...facultadEntity,
      ...values,
      decano: decanos.find(it => it.id.toString() === values.decano.toString()),
      viceDecano: viceDecanos.find(it => it.id.toString() === values.viceDecano.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...facultadEntity,
          decano: facultadEntity?.decano?.id,
          viceDecano: facultadEntity?.viceDecano?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lab3FabricaApp.facultad.home.createOrEditLabel" data-cy="FacultadCreateUpdateHeading">
            <Translate contentKey="lab3FabricaApp.facultad.home.createOrEditLabel">Create or edit a Facultad</Translate>
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
                  id="facultad-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lab3FabricaApp.facultad.nombre')}
                id="facultad-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                id="facultad-decano"
                name="decano"
                data-cy="decano"
                label={translate('lab3FabricaApp.facultad.decano')}
                type="select"
              >
                <option value="" key="0" />
                {decanos
                  ? decanos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="facultad-viceDecano"
                name="viceDecano"
                data-cy="viceDecano"
                label={translate('lab3FabricaApp.facultad.viceDecano')}
                type="select"
              >
                <option value="" key="0" />
                {viceDecanos
                  ? viceDecanos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/facultad" replace color="info">
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

export default FacultadUpdate;
